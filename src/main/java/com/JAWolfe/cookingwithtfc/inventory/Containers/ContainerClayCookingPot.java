package com.JAWolfe.cookingwithtfc.inventory.Containers;

import java.util.ArrayList;

import com.JAWolfe.cookingwithtfc.crafting.CookingPotManager;
import com.JAWolfe.cookingwithtfc.crafting.CookingPotRecipe;
import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.JAWolfe.cookingwithtfc.inventory.Containers.Slots.SlotAllFoodsIngreds;
import com.JAWolfe.cookingwithtfc.inventory.Containers.Slots.SlotFluidContainersOnly;
import com.JAWolfe.cookingwithtfc.items.Items.ItemClayCookingPot;
import com.JAWolfe.cookingwithtfc.items.Items.ItemTFCFoodTransform;
import com.bioxx.tfc.Containers.ContainerTFC;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Items.Tools.ItemCustomBucketMilk;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Interfaces.IFood;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

public class ContainerClayCookingPot extends ContainerTFC
{
	private InventoryCrafting fluidContainerInv = new InventoryCrafting(this, 1, 1);
	private InventoryCrafting ingredContainerInv = new InventoryCrafting(this, 2, 2);
	private ItemClayCookingPot cookingPot;
	
	public ContainerClayCookingPot(InventoryPlayer playerinv, World world, int x, int y, int z)
	{
		this.player = playerinv.player;
		bagsSlotNum = player.inventory.currentItem;
		cookingPot = (ItemClayCookingPot)playerinv.player.getCurrentEquippedItem().getItem();
		
		this.addSlotToContainer(new SlotFluidContainersOnly(fluidContainerInv, 0, 50, 32));
		
		this.addSlotToContainer(new SlotAllFoodsIngreds(ingredContainerInv, 0, 103, 23));
		this.addSlotToContainer(new SlotAllFoodsIngreds(ingredContainerInv, 1, 121, 23));
		this.addSlotToContainer(new SlotAllFoodsIngreds(ingredContainerInv, 2, 103, 41));
		this.addSlotToContainer(new SlotAllFoodsIngreds(ingredContainerInv, 3, 121, 41));
		
		PlayerInventory.buildInventoryLayout(this, playerinv, 8, 89, true, true);
		
		loadInventory();
	}	
	
	public void loadInventory()
	{
		ItemStack is = player.inventory.getStackInSlot(bagsSlotNum);
		if(is != null && is.hasTagCompound())
		{
			NBTTagList nbttaglist = player.inventory.getStackInSlot(bagsSlotNum).getTagCompound().getTagList("Items", 10);
			
			if(Food.isCooked(is))
			{
				ArrayList<Item> ingredsList = new ArrayList<Item>();
				this.isLoading = true;
				
				NBTTagCompound nbt = (NBTTagCompound)is.getTagCompound().getTag("Processing Tag");
				nbt.removeTag("Cooked");
			
				for(int i = 0; i < nbttaglist.tagCount(); i++)
				{
					NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
					byte byte0 = nbttagcompound1.getByte("Slot");
					if(byte0 >= 0 && byte0 < 5)
					{
						ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound1);
						ingredsList.add(itemstack.getItem());
					}
				}
				Item[] ingreds = new Item[ingredsList.size()];
				ingredsList.toArray(ingreds);
				
				CookingPotRecipe irecipe = CookingPotManager.getInstance().findMatchingRecipe(cookingPot.getPotFluid(is), ingreds);
				if(irecipe != null)
				{
					if(irecipe.getOutputFluid() != null)
						cookingPot.setPotFluid(is, irecipe.getOutputFluid());
					else
						cookingPot.emptyFluid(is);
						
					Item[] recipeOutput = irecipe.getOutputItems();
					if(recipeOutput != null)
					{
						for(int i = 0; i < recipeOutput.length; i++)
						{
							if(recipeOutput[i] instanceof ItemTFCFoodTransform)
								this.ingredContainerInv.setInventorySlotContents(i, ItemTFCFoodTransform.createTag(new ItemStack(recipeOutput[i]), irecipe.getOutputAmounts()[i]));	
						}
					}		
				}
				else
					cookingPot.emptyFluid(is);
			}
			else
			{
				this.isLoading = true;
				for(int i = 0; i < nbttaglist.tagCount(); i++)
				{
					NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
					byte byte0 = nbttagcompound1.getByte("Slot");
					if(byte0 >= 0 && byte0 < 5)
					{
						ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound1);
						this.ingredContainerInv.setInventorySlotContents(byte0, itemstack);
					}
				}
			}
		}
	}
	
	@Override
	public void saveContents(ItemStack slotItem)
	{
		NBTTagList nbttaglist = new NBTTagList();
		
		for(int i = 0; i < ingredContainerInv.getSizeInventory(); i++)
		{
			ItemStack contentStack = ingredContainerInv.getStackInSlot(i);
			if (contentStack != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				contentStack.writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		
		ItemStack is = player.inventory.getStackInSlot(bagsSlotNum);
		if(is != null)
		{
			if(!is.hasTagCompound())
				is.setTagCompound(new NBTTagCompound());
			is.getTagCompound().setTag("Items", nbttaglist);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void detectAndSendChanges()
	{
		transferFluids(((Slot)this.inventorySlots.get(0)).getStack());
		
		for (int var1 = 0; var1 < this.inventorySlots.size(); ++var1)
		{
			ItemStack var2 = ((Slot)this.inventorySlots.get(var1)).getStack();
			ItemStack var3 = (ItemStack)this.inventoryItemStacks.get(var1);

			if (!ItemStack.areItemStacksEqual(var3, var2))
			{
				var3 = var2 == null ? null : var2.copy();
				this.inventoryItemStacks.set(var1, var3);

				for (int var4 = 0; var4 < this.crafters.size(); ++var4)
					((ICrafting)this.crafters.get(var4)).sendSlotContents(this, var1, var3);
			}
		}
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player)
    {
		super.onContainerClosed(player);
		
		player.dropPlayerItemWithRandomChoice(((Slot)this.inventorySlots.get(0)).getStack(), false);
		
		ArrayList<Item> ingredsList = new ArrayList<Item>();
		for(int i = 0; i < ingredContainerInv.getSizeInventory(); i++) 
		{
			if(ingredContainerInv.getStackInSlot(i) != null)
				ingredsList.add(ingredContainerInv.getStackInSlot(i).getItem());
		}
		
		Item[] ingreds = new Item[ingredsList.size()];
		ingredsList.toArray(ingreds);
		
		CookingPotRecipe irecipe = CookingPotManager.getInstance().findMatchingRecipe(cookingPot.getPotFluid(player.inventory.getStackInSlot(bagsSlotNum)), ingreds);
		if(irecipe != null)
		{
			boolean dropAll = false;
			float[] amountDropped = new float[4];
			for(int i = 0; i < 4; i++)
			{
				if(ingredContainerInv.getStackInSlot(i) != null)
				{
					for(int j = 0; j < irecipe.getInputItems().length; j++)
					{
						if(ingredContainerInv.getStackInSlot(i).getItem() instanceof IFood &&
							ingredContainerInv.getStackInSlot(i).getItem() == irecipe.getInputItems()[j])
						{
							amountDropped[i] = Food.getWeight(ingredContainerInv.getStackInSlot(i)) - irecipe.getInputAmounts()[j];
							if(amountDropped[i] < 0) dropAll = true;
						}
					}
				}
			}
			
			ItemStack is = player.inventory.getStackInSlot(bagsSlotNum);
			if(dropAll)
			{
				for(int i = 0; i < ingredContainerInv.getSizeInventory(); i++)
					player.dropPlayerItemWithRandomChoice(ingredContainerInv.getStackInSlot(i), false);
				
				is.getTagCompound().removeTag("Items");
			}
			else
			{
				for(int i = 0; i < 4; i++)
				{
					if(ingredContainerInv.getStackInSlot(i) != null && 
						ingredContainerInv.getStackInSlot(i).getItem() instanceof IFood)
					{
						ItemStack foodItem = ItemTFCFoodTransform.createTag(ingredContainerInv.getStackInSlot(i), amountDropped[i]);
						player.dropPlayerItemWithRandomChoice(foodItem, false);
					}
				}
				
				NBTTagList nbttaglist = new NBTTagList();
				for(int i = 0; i < irecipe.getInputItems().length; i++)
				{
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setByte("Slot", (byte)i);
					ItemStack foodItem = ItemTFCFoodTransform.createTag(new ItemStack(irecipe.getInputItems()[i]), irecipe.getInputAmounts()[i]);
					foodItem.writeToNBT(nbttagcompound1);
					nbttaglist.appendTag(nbttagcompound1);
				}
				
				if(is != null)
				{
					if(!is.hasTagCompound())
						is.setTagCompound(new NBTTagCompound());
					is.getTagCompound().setTag("Items", nbttaglist);
				}
			}
		}
		else
		{
			ItemStack is = player.inventory.getStackInSlot(bagsSlotNum);
			
			for(int i = 0; i < ingredContainerInv.getSizeInventory(); i++)
				player.dropPlayerItemWithRandomChoice(ingredContainerInv.getStackInSlot(i), false);
			
			if(is != null && is.hasTagCompound() && is.getTagCompound().hasKey("Items"))
				is.getTagCompound().removeTag("Items");
		}
    }
	
	private void transferFluids(ItemStack container)
	{
		ItemStack cookingPotStack = player.getCurrentEquippedItem();
		if (container != null)
		{		
			if((cookingPot).getInputMode(player.getCurrentEquippedItem()))
			{
				if(FluidContainerRegistry.isFilledContainer(container) && cookingPot.addLiquid(cookingPotStack, FluidContainerRegistry.getFluidForFilledItem(container)))
					((Slot)this.inventorySlots.get(0)).putStack(FluidContainerRegistry.drainFluidContainer(container));
				else if(container.getItem() instanceof IFluidContainerItem && cookingPot.addLiquid(cookingPotStack, ((IFluidContainerItem) container.getItem()).getFluid(container)))
					((Slot)this.inventorySlots.get(0)).putStack(FluidContainerRegistry.drainFluidContainer(container));
			}
			else
			{
				if(cookingPot.getPotFluid(cookingPotStack) != null)
				{
					if(container.getItem() instanceof IFluidContainerItem)
					{
						FluidStack isfs = ((IFluidContainerItem)container.getItem()).getFluid(container);
						if(isfs == null || cookingPot.getPotFluid(cookingPotStack).isFluidEqual(isfs))
						{
							cookingPot.setFluidLevel(cookingPotStack, cookingPot.getFluidLevel(cookingPotStack) 
									- ((IFluidContainerItem) container.getItem()).fill(container, cookingPot.getPotFluid(cookingPotStack), true));
							if(cookingPot.getPotFluid(cookingPotStack).amount == 0)
								cookingPot.emptyFluid(cookingPotStack);
						}
					}
					else if(FluidContainerRegistry.isEmptyContainer(container))
					{
						ItemStack fullContainer = cookingPot.removeLiquid(container, cookingPotStack);
						if (fullContainer.getItem() == CWTFCItems.woodenBucketMilkCWTFC)
							ItemCustomBucketMilk.createTag(fullContainer, 20F);

						if(((Slot)this.inventorySlots.get(0)).getStack() != fullContainer)
							((Slot)this.inventorySlots.get(0)).putStack(fullContainer);
					}
					else if(container.getItem() == TFCItems.potteryBowl)
					{
						ItemStack fullBowl = cookingPot.removeLiquid(container, cookingPotStack);
						
						if(((Slot)this.inventorySlots.get(0)).getStack() != fullBowl)
							((Slot)this.inventorySlots.get(0)).putStack(fullBowl);
					}
				}
			}
		}
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

			//From Pot to Inventory
			if (slotNum < 5 && !this.mergeItemStack(slotStack, 5, inventorySlots.size(), false))
				return null;
			//From Inventory to Pot
			else if(slotNum >= 5 && !this.mergeItemStack(slotStack, 0, 5, false))
				return null;

			if (slotStack.stackSize <= 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if (slotStack.stackSize == origStack.stackSize)
				return null;

			slot.onPickupFromSlot(player, slotStack);
		}

		return origStack;
	}
}
