package com.JAWolfe.cookingwithtfc.inventory.Containers.Slots;

import com.bioxx.tfc.Food.ItemFoodTFC;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAllFoodsIngreds  extends Slot
{

	public SlotAllFoodsIngreds(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition) 
	{
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack is)
	{
		if(is.getItem() instanceof ItemFoodTFC)
			return true;
		else
			return false;
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
	
	@Override
	public void putStack(ItemStack is)
	{
		if (is != null)
			is.stackSize = 1;
		if (this.inventory != null)
			super.putStack(is);
	}
}