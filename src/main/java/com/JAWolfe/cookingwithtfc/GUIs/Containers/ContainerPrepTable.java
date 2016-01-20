package com.JAWolfe.cookingwithtfc.GUIs.Containers;

import com.JAWolfe.cookingwithtfc.GUIs.Containers.Slots.SlotCookwareOnly;
import com.JAWolfe.cookingwithtfc.GUIs.Containers.Slots.SlotFoodsOnly;
import com.JAWolfe.cookingwithtfc.GUIs.Containers.Slots.SlotOutputOnly;
import com.JAWolfe.cookingwithtfc.tileentities.TEPrepTable;
import com.bioxx.tfc.Containers.ContainerTFC;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Food.ItemFoodTFC;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPrepTable extends ContainerTFC 
{
	private TEPrepTable tePrepTable;
	
	public ContainerPrepTable(InventoryPlayer inventoryplayer, TEPrepTable PrepTable)
	{
		this.tePrepTable = PrepTable;
		tePrepTable.openInventory();
		
		addSlotToContainer(new SlotOutputOnly(inventoryplayer.player, PrepTable, 0, 125, 45));
		
		int id = 1;
		
		for(int row = 0; row < 4; row++)
		{
			for(int col = 0; col < 4; col++)
			{	
				addSlotToContainer(new SlotFoodsOnly(PrepTable, id++, 8 + (18 * col), 8 + (18 * row)));
			}
		}
		
		for(int row = 0; row < 2; row++)
		{
			for(int col = 0; col < 4; col++)
			{
				addSlotToContainer(new SlotCookwareOnly(PrepTable, id++, 98 + (18 * col), 8 + (18 * row)));
			}
		}
		
		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 90, false, true);
	}
	
	@Override
    public ItemStack transferStackInSlotTFC(EntityPlayer entityPlayer, int slotIndex)
    {
		super.transferStackInSlotTFC(entityPlayer, slotIndex);
        ItemStack origStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);
       
        if (slot != null && slot.getHasStack())
        {
            ItemStack slotStack = slot.getStack();
            origStack = slotStack.copy();
            int TableSlotCount = tePrepTable.getSizeInventory();

            //From Table to Inventory
            if (slotIndex < TableSlotCount && !this.mergeItemStack(slotStack, TableSlotCount, this.inventorySlots.size(), false))
            	return null;
            //From Inventory to Food
            else if(slotStack.getItem() instanceof ItemFoodTFC && 
            		!this.mergeItemStack(slotStack, TEPrepTable.FOOD_INPUT_START, TEPrepTable.COOKWARE_INPUT_START, false))
                return null;
            //From Inventory to Cookware
            else if(tePrepTable.isCookware(slotStack) && 
            		!this.mergeItemStack(slotStack, TEPrepTable.COOKWARE_INPUT_START, TableSlotCount, false))
                return null;
            
            if(slotStack.stackSize <= 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if (slotStack.stackSize == origStack.stackSize)
				return null;

			slot.onPickupFromSlot(player, slotStack);
        }
        
        return origStack;
    }
	
	@Override
	public ItemStack slotClick(int sourceSlotID, int destSlotID, int clickType, EntityPlayer player)
	{
		ItemStack is = super.slotClick(sourceSlotID, destSlotID, clickType, player);
		if(sourceSlotID == 0 && is != null)
		{
			this.tePrepTable.consumeIngredients(is, player);
			this.tePrepTable.clearRecipeList();
		}
		else if(sourceSlotID > 0)
		{
			this.tePrepTable.setInventorySlotContents(0, null);
			this.tePrepTable.clearRecipeList();
		}
		return is;
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
	}
	
	
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		this.tePrepTable.closeInventory();
		this.tePrepTable.updateOppositeTable();
		this.tePrepTable.clearRecipeList();
	}
}
