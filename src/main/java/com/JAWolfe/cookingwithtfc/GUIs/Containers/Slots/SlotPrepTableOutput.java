package com.JAWolfe.cookingwithtfc.GUIs.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPrepTableOutput extends Slot
{

	public SlotPrepTableOutput(EntityPlayer entityplayer, IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition) 
	{
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
	}

	@Override
	public boolean isItemValid(ItemStack is)
	{
		return false;
	}
	
	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
}
