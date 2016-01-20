package com.JAWolfe.cookingwithtfc.GUIs.Containers;

import com.JAWolfe.cookingwithtfc.GUIs.Containers.Slots.SlotFluidContainersOnly;
import com.JAWolfe.cookingwithtfc.GUIs.Containers.Slots.SlotFoodsOnly;
import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.JAWolfe.cookingwithtfc.items.Items.ItemClayCookingPot;
import com.bioxx.tfc.Containers.ContainerTFC;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Items.Tools.ItemCustomBucketMilk;
import com.bioxx.tfc.api.Food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
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
		
		this.addSlotToContainer(new SlotFoodsOnly(ingredContainerInv, 0, 103, 23));
		this.addSlotToContainer(new SlotFoodsOnly(ingredContainerInv, 1, 121, 23));
		this.addSlotToContainer(new SlotFoodsOnly(ingredContainerInv, 2, 103, 41));
		this.addSlotToContainer(new SlotFoodsOnly(ingredContainerInv, 3, 121, 41));
		
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
				ItemStack[] ingredsList = new ItemStack[4];
				this.isLoading = true;
				for(int i = 0; i < nbttaglist.tagCount(); i++)
				{
					NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
					byte byte0 = nbttagcompound1.getByte("Slot");
					if(byte0 >= 0 && byte0 < 5)
					{
						ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound1);
						ingredsList[byte0] = itemstack;
					}
				}
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
		
		ItemStack is = ((Slot)this.inventorySlots.get(0)).getStack();
		if (is != null)
		{			
			if((cookingPot).getInputMode(player.getCurrentEquippedItem()))
			{
				if(FluidContainerRegistry.isFilledContainer(is) && cookingPot.addLiquid(FluidContainerRegistry.getFluidForFilledItem(is)))
					((Slot)this.inventorySlots.get(0)).putStack(FluidContainerRegistry.drainFluidContainer(is));
				else if(is.getItem() instanceof IFluidContainerItem && cookingPot.addLiquid(((IFluidContainerItem) is.getItem()).getFluid(is)))
					((Slot)this.inventorySlots.get(0)).putStack(FluidContainerRegistry.drainFluidContainer(is));
			}
			else
			{
				if(cookingPot.getPotFluid() != null)
				{
					if(is.getItem() instanceof IFluidContainerItem)
					{
						FluidStack isfs = ((IFluidContainerItem)is.getItem()).getFluid(is);
						if(isfs == null || cookingPot.getPotFluid().isFluidEqual(isfs))
						{
							cookingPot.getPotFluid().amount -= ((IFluidContainerItem) is.getItem()).fill(is, cookingPot.getPotFluid(), true);
							if(cookingPot.getPotFluid().amount == 0)
								cookingPot.setPotFluid(null);
						}
					}
					else if(FluidContainerRegistry.isEmptyContainer(is))
					{
						ItemStack fullContainer = cookingPot.removeLiquid(is);
						if (fullContainer.getItem() == CWTFCItems.woodenBucketMilkCWTFC)
							ItemCustomBucketMilk.createTag(fullContainer, 20F);

						((Slot)this.inventorySlots.get(0)).putStack(fullContainer);
					}
				}
			}
		}
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player)
    {
		super.onContainerClosed(player);
		player.dropPlayerItemWithRandomChoice(((Slot)this.inventorySlots.get(0)).getStack(), false);
    }
	
	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			if (slotNum < 5 && !this.mergeItemStack(slotStack, 5, inventorySlots.size(), true))
				return null;
			else if (!this.mergeItemStack(slotStack, 0, 5, false))
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
