package com.JAWolfe.cookingwithtfc.GUIs.Containers.Slots;

import com.JAWolfe.cookingwithtfc.tileentities.TEPrepTable;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCookwareOnly extends Slot
{
	TEPrepTable teTable;
	
	public SlotCookwareOnly(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition) 
	{
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
		if(inventory instanceof TEPrepTable)
		teTable = (TEPrepTable) inventory;
	}

	@Override
	public boolean isItemValid(ItemStack is)
	{
		return teTable.isCookware(is);
	}

	@Override
	public int getSlotStackLimit()
	{
		return 64;
	}
}
