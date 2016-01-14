package com.JAWolfe.cookingwithtfc.items;

import com.JAWolfe.cookingwithtfc.tileentities.TEPrepTable;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPrepTable2 extends ItemPrepTable
{

	public ItemPrepTable2(Block b) {
		super(b);
		metaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, metaNames, 0, Global.WOOD_ALL.length - 16);
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
        if (world.setBlock(x, y, z, field_150939_a, metadata&15, 3)) 
        {

        	field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
        	field_150939_a.onPostBlockPlaced(world, x, y, z, 0);
        	
        	TEPrepTable te = (TEPrepTable) world.getTileEntity(x, y, z);
			te.tableType = 16;

            return true;

        } else {

            return false;

        }
    }

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		return getUnlocalizedName();
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		String s = "";
		if(is.getItemDamage() == 0)
		{
			s += TFC_Core.translate("wood.Acacia") + " ";
		}		
		s += TFC_Core.translate(this.getUnlocalizedNameInefficiently(is) + ".name");
		return s.trim();
	}
}
