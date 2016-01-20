package com.JAWolfe.cookingwithtfc.items;

import java.util.List;

import com.JAWolfe.cookingwithtfc.tileentities.TEPrepTable;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemPrepTable extends ItemTerraBlock
{
	public ItemPrepTable(Block b) {
		super(b);
		setHasSubtypes(true);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
		metaNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, metaNames, 0, 16);
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
		switch(is.getItemDamage())
		{
			case 0: s += TFC_Core.translate("wood.Oak") + " "; break;
			case 1: s += TFC_Core.translate("wood.Aspen") + " "; break;
			case 2: s += TFC_Core.translate("wood.Birch") + " "; break;
			case 3: s += TFC_Core.translate("wood.Chestnut") + " "; break;
			case 4: s += TFC_Core.translate("wood.Douglas Fir") + " "; break;
			case 5: s += TFC_Core.translate("wood.Hickory") + " "; break;
			case 6: s += TFC_Core.translate("wood.Maple") + " "; break;
			case 7: s += TFC_Core.translate("wood.Ash") + " "; break;
			case 8: s += TFC_Core.translate("wood.Pine") + " "; break;
			case 9: s += TFC_Core.translate("wood.Sequoia") + " "; break;
			case 10: s += TFC_Core.translate("wood.Spruce") + " "; break;
			case 11: s += TFC_Core.translate("wood.Sycamore") + " "; break;
			case 12: s += TFC_Core.translate("wood.White Cedar") + " "; break;
			case 13: s += TFC_Core.translate("wood.White Elm") + " "; break;
			case 14: s += TFC_Core.translate("wood.Willow") + " "; break;
			case 15: s += TFC_Core.translate("wood.Kapok") + " "; break;
			default: break;
		}		
		s += TFC_Core.translate(this.getUnlocalizedNameInefficiently(is) + ".name");
		return s.trim();
	}
	
	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.LARGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.HEAVY;
	}

	@Override
	public int getItemStackLimit(ItemStack is)
	{
		return 1;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < metaNames.length; i++)
			list.add(new ItemStack(this,1,i));
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if (side == 1)
		{
			y++;
			int rot = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			ForgeDirection dir = ForgeDirection.getOrientation(Direction.directionToFacing[rot]).getOpposite();
            
            int x_offset = x - dir.offsetX;
            int z_offset = z - dir.offsetZ;
            int meta = this.getMetadata(stack.getItemDamage());
            
            if (player.canPlayerEdit(x, y, z, side, stack) && 
            	player.canPlayerEdit(x_offset, y, z_offset, side, stack) &&
            	world.isAirBlock(x, y, z) && 
            	world.isAirBlock(x_offset, y, z_offset) && 
            	World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && 
            	World.doesBlockHaveSolidTopSurface(world, x_offset, y - 1, z_offset) &&
            	placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, meta) &&
            	placeBlockAt(stack, player, world, x_offset, y, z_offset, side, hitX, hitY, hitZ, meta))
            {
            	TEPrepTable headtable = (TEPrepTable)world.getTileEntity(x, y, z);
            	headtable.setDirection(dir.getOpposite());
            	
            	TEPrepTable foottable = (TEPrepTable)world.getTileEntity(x_offset, y, z_offset);
            	foottable.setDirection(dir);
            	
                stack.stackSize--;                    
                return true;
            }
		}
		
		return false;
    }
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
        if (world.setBlock(x, y, z, field_150939_a, metadata&15, 3)) 
        {
        	field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
        	field_150939_a.onPostBlockPlaced(world, x, y, z, 0);
        	
        	TEPrepTable te = (TEPrepTable) world.getTileEntity(x, y, z);
			te.tableType = metadata;

            return true;

        } 
        else
            return false;
    }
}
