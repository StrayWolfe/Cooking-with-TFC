package com.JAWolfe.cookingwithtfc.inventory.Containers.Slots;

import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.bioxx.tfc.api.TFCItems;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFluidContainersOnly extends Slot
{

	public SlotFluidContainersOnly(IInventory iinventory, int slotIndex, int xDisplayPosition, int yDisplayPosition) 
	{
		super(iinventory, slotIndex, xDisplayPosition, yDisplayPosition);
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
	
	@Override
	public boolean isItemValid(ItemStack is)
	{
		if((is.getItem() == TFCItems.potteryJug || is.getItem() == TFCItems.potteryBowl) && is.getItemDamage() != 0)
			return true;
		else if(is.getItem() == TFCItems.woodenBucketEmpty || is.getItem() == TFCItems.woodenBucketWater ||
				is.getItem() == TFCItems.woodenBucketSaltWater || is.getItem() == CWTFCItems.woodenBucketMilkCWTFC ||
				is.getItem() == TFCItems.redSteelBucketEmpty || is.getItem() == TFCItems.redSteelBucketWater ||
				is.getItem() == TFCItems.redSteelBucketSaltWater)
			return true;
		
		return false;
	}
}
