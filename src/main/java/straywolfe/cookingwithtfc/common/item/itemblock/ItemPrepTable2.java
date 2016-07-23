package straywolfe.cookingwithtfc.common.item.itemblock;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;

public class ItemPrepTable2 extends ItemPrepTable
{

	public ItemPrepTable2(Block b) 
	{
		super(b);
		metaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, metaNames, 0, Global.WOOD_ALL.length - 16);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if (!world.isRemote && side == 1)
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
            	World.doesBlockHaveSolidTopSurface(world, x_offset, y - 1, z_offset))
            {
            	if(dir == ForgeDirection.SOUTH)
            	{
	            	world.setBlock(x, y, z, CWTFCBlocks.prepTable2N, meta, 3);
	            	world.setBlock(x_offset, y, z_offset, CWTFCBlocks.prepTable2S, meta, 3);
            	}
            	else if(dir == ForgeDirection.NORTH)
            	{
	            	world.setBlock(x, y, z, CWTFCBlocks.prepTable2S, meta, 3);
	            	world.setBlock(x_offset, y, z_offset, CWTFCBlocks.prepTable2N, meta, 3);
            	}
            	else if(dir == ForgeDirection.WEST)
            	{
	            	world.setBlock(x, y, z, CWTFCBlocks.prepTable2E, meta, 3);
	            	world.setBlock(x_offset, y, z_offset, CWTFCBlocks.prepTable2W, meta, 3);
            	}
            	else if(dir == ForgeDirection.EAST)
            	{
	            	world.setBlock(x, y, z, CWTFCBlocks.prepTable2W, meta, 3);
	            	world.setBlock(x_offset, y, z_offset, CWTFCBlocks.prepTable2E, meta, 3);
            	}
            	
                stack.stackSize--;                    
                return true;
            }
		}
		
		return false;
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
			s += TFC_Core.translate("wood.Acacia") + " ";
		
		s += TFC_Core.translate(this.getUnlocalizedNameInefficiently(is) + ".name");
		return s.trim();
	}
}
