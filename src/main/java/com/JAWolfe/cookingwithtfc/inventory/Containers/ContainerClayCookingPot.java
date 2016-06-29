package com.JAWolfe.cookingwithtfc.inventory.Containers;

import com.JAWolfe.cookingwithtfc.inventory.Containers.Slots.SlotAllFoodsIngreds;
import com.JAWolfe.cookingwithtfc.tileentities.TECookingPot;
import com.bioxx.tfc.Containers.ContainerTFC;
import com.bioxx.tfc.Containers.Slots.SlotFirepit;
import com.bioxx.tfc.Containers.Slots.SlotFirepitFuel;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.api.Interfaces.IFood;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ContainerClayCookingPot extends ContainerTFC
{
	private TECookingPot teCookingPot;
	private float firetemp;
	private int fluidLevel;
	private int fluidID;
	private int recipeID;
	private int cookTimer;
	private int cookTime;
	
	public ContainerClayCookingPot(InventoryPlayer playerinv, TECookingPot pot, World world, int x, int y, int z)
	{
		teCookingPot = pot;
		fluidLevel = -1111;
		fluidID =-1111;
		firetemp = -1111;
		recipeID = -1111;
		cookTimer = -1111;
		cookTime = -1111;
		
		addSlotToContainer(new SlotFirepitFuel(playerinv.player, teCookingPot, 0, 8, 8));
		addSlotToContainer(new SlotFirepit(playerinv.player, teCookingPot, 1, 8, 26));
		addSlotToContainer(new SlotFirepit(playerinv.player, teCookingPot, 2, 8, 44));
		addSlotToContainer(new SlotFirepit(playerinv.player, teCookingPot, 3, 8, 62));
		
		addSlotToContainer(new SlotAllFoodsIngreds(teCookingPot, 4, 83, 26));
		addSlotToContainer(new SlotAllFoodsIngreds(teCookingPot, 5, 101, 26));
		addSlotToContainer(new SlotAllFoodsIngreds(teCookingPot, 6, 83, 44));
		addSlotToContainer(new SlotAllFoodsIngreds(teCookingPot, 7, 101, 44));
		
		PlayerInventory.buildInventoryLayout(this, playerinv, 8, 102, false, true);
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		//Inventory updating
		for (int var1 = 0; var1 < inventorySlots.size(); ++var1)
		{
			ItemStack var2 = ((Slot)inventorySlots.get(var1)).getStack();
			ItemStack var3 = (ItemStack)inventoryItemStacks.get(var1);

			if (!ItemStack.areItemStacksEqual(var3, var2))
			{
				var3 = var2 == null ? null : var2.copy();
				inventoryItemStacks.set(var1, var3);

				for (int var4 = 0; var4 < crafters.size(); ++var4)
					((ICrafting)crafters.get(var4)).sendSlotContents(this, var1, var3);
			}
		}

		
		for (int var1 = 0; var1 < this.crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			
			//Update fire temperature
			if (firetemp != teCookingPot.fireTemp)
			{
				firetemp = teCookingPot.fireTemp;
				var2.sendProgressBarUpdate(this, 0, (int)firetemp);
			}
			
			//Update fluid type
			if(teCookingPot.getCookingPotFluid() != null && fluidID != teCookingPot.getCookingPotFluid().getFluidID())
			{
				fluidID = teCookingPot.getCookingPotFluid().getFluidID();
				var2.sendProgressBarUpdate(this, 1, fluidID);
			}
			
			//Update removed fluid
			if(teCookingPot.getCookingPotFluid() == null && fluidID != -1)
			{
				fluidID = -1;
				var2.sendProgressBarUpdate(this, 2, fluidID);
			}
			
			//Update fluid level
			if(fluidLevel != teCookingPot.getFluidLevel())
			{
				fluidLevel = teCookingPot.getFluidLevel();
				var2.sendProgressBarUpdate(this, 3, fluidLevel);
			}
			
			//Update RecipeID
			if(recipeID != teCookingPot.getRecipeID())
			{
				recipeID = teCookingPot.getRecipeID();
				var2.sendProgressBarUpdate(this, 4, recipeID);
			}
			
			//Update Timer
			if(cookTimer != teCookingPot.getCookTimer())
			{
				cookTimer = teCookingPot.getCookTimer();
				var2.sendProgressBarUpdate(this, 5, cookTimer);
			}
			
			//Update Time
			if(cookTime != teCookingPot.getCookTime())
			{
				cookTime = teCookingPot.getCookTime();
				var2.sendProgressBarUpdate(this, 6, cookTime);
			}
		}
	}
	
	@Override
	public void updateProgressBar(int id, int value)
	{
		if (id == 0)
			teCookingPot.fireTemp = value;
		else if(id == 1)
		{
			if(teCookingPot.getCookingPotFluid() != null)
				teCookingPot.setCookingPotFluid(new FluidStack(FluidRegistry.getFluid(value), teCookingPot.getFluidLevel()));
			else
				teCookingPot.setCookingPotFluid(new FluidStack(FluidRegistry.getFluid(value), teCookingPot.getMaxLiquid()));
		}
		else if(id == 2)
			teCookingPot.setCookingPotFluid(null);
		else if(id == 3)
			teCookingPot.setFluidLevel(value);
		else if(id == 4)
			teCookingPot.setRecipeID(value);
		else if(id == 5)
			teCookingPot.setCookTimer(value);
		else if(id == 6)
			teCookingPot.setCookTime(value);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public ItemStack slotClick(int sourceSlotID, int destSlotID, int clickType, EntityPlayer player)
	{
		ItemStack is = super.slotClick(sourceSlotID, destSlotID, clickType, player);
		
		if((sourceSlotID >= teCookingPot.INVFOODSTART && sourceSlotID < teCookingPot.INVFOODSTART + teCookingPot.INVFOODCOUNT) || 
		   (destSlotID >= teCookingPot.INVFOODSTART && destSlotID < teCookingPot.INVFOODSTART + teCookingPot.INVFOODCOUNT))
		{
			teCookingPot.recipeHandling();
		}
		
		return is;
	}
	
	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		super.transferStackInSlotTFC(player, slotNum);
		ItemStack origStack = null;
		Slot slot = (Slot)inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();
			int invSize = teCookingPot.getSizeInventory() - 1;

			//From Pot to Inventory
			if (slotNum < invSize && !this.mergeItemStack(slotStack, invSize, inventorySlots.size(), false))
				return null;
			//From Inventory to Pot
			else if(slotNum >= invSize && !this.mergeItemStack(slotStack, 0, invSize, false))
				return null;

			if (slotStack.stackSize <= 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if (slotStack.stackSize == origStack.stackSize)
				return null;

			if(slotStack.getItem() instanceof IFood)
				teCookingPot.recipeHandling();
			
			slot.onPickupFromSlot(player, slotStack);
		}

		return origStack;
	}
}
