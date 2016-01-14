package com.JAWolfe.cookingwithtfc.GUIs.Containers.Slots;

import com.JAWolfe.cookingwithtfc.tileentities.TEPrepTable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCookwareInput extends Slot
{
	TEPrepTable teTable;
	
	public SlotCookwareInput(EntityPlayer entityplayer, IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition) 
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
