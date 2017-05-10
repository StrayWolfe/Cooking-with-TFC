package straywolfe.cookingwithtfc.common.item.itemblock;

import java.util.List;

import com.bioxx.tfc.Core.TFCTabs;
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
import straywolfe.cookingwithtfc.api.CWTFCBlocks;

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
	            	world.setBlock(x, y, z, CWTFCBlocks.prepTableN, meta, 3);
	            	world.setBlock(x_offset, y, z_offset, CWTFCBlocks.prepTableS, meta, 3);
            	}
            	else if(dir == ForgeDirection.NORTH)
            	{
	            	world.setBlock(x, y, z, CWTFCBlocks.prepTableS, meta, 3);
	            	world.setBlock(x_offset, y, z_offset, CWTFCBlocks.prepTableN, meta, 3);
            	}
            	else if(dir == ForgeDirection.WEST)
            	{
	            	world.setBlock(x, y, z, CWTFCBlocks.prepTableE, meta, 3);
	            	world.setBlock(x_offset, y, z_offset, CWTFCBlocks.prepTableW, meta, 3);
            	}
            	else if(dir == ForgeDirection.EAST)
            	{
	            	world.setBlock(x, y, z, CWTFCBlocks.prepTableW, meta, 3);
	            	world.setBlock(x_offset, y, z_offset, CWTFCBlocks.prepTableE, meta, 3);
            	}
            	
                stack.stackSize--;                    
                return true;
            }
		}
		
		return false;
    }
}
