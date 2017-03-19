package straywolfe.cookingwithtfc.common.handlers;

import java.util.List;

import com.bioxx.tfc.Handlers.CraftingHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import straywolfe.cookingwithtfc.api.CWTFCItems;

public class CraftingMatrixHandler 
{
	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent e)
	{
		EntityPlayer player = e.player;
		ItemStack itemstack = e.crafting;
		Item item = itemstack.getItem();
		IInventory iinventory = e.craftMatrix;
		
		if(iinventory != null)
		{
			if(item == CWTFCItems.singlePlank)
			{
				List<ItemStack> saws = OreDictionary.getOres("itemSaw", false);
				CraftingHandler.handleItem(player, iinventory, saws);
			}
		}
	}
}
