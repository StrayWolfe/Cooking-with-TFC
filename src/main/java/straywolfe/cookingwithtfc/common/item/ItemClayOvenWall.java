package straywolfe.cookingwithtfc.common.item;

import java.util.List;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.core.Tabs;
import straywolfe.cookingwithtfc.common.core.helper.Helper;
import straywolfe.cookingwithtfc.common.lib.Constants;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.tileentity.TileClayOven;

public class ItemClayOvenWall extends ItemTerra implements ISize
{
	public ItemClayOvenWall()
	{
		super();
		setCreativeTab(Tabs.MAINTAB);
		setUnlocalizedName("clayOvenWall");
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if (!world.isRemote)
		{
			super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
			
			Block blockHit = world.getBlock(x, y, z);
			
			if(side == 1 && Helper.isStone(blockHit) && world.isSideSolid(x, y, z, ForgeDirection.UP))
			{
				y++;
				int rot = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
				ForgeDirection dir = ForgeDirection.getOrientation(Direction.directionToFacing[rot]).getOpposite();
	            
	            if(player.canPlayerEdit(x, y, z, side, stack) && world.isAirBlock(x, y, z))
	    		{
	            	if(dir == ForgeDirection.NORTH)
	            	{
	            		world.setBlock(x, y, z, CWTFCBlocks.clayOven, 0, 3);
	            	}
	            	else if(dir == ForgeDirection.EAST)
	            	{
	            		world.setBlock(x, y, z, CWTFCBlocks.clayOven, 1, 3);
	            	}
	            	else if(dir == ForgeDirection.SOUTH)
	            	{
	            		world.setBlock(x, y, z, CWTFCBlocks.clayOven, 2, 3);
	            	}
	            	else if(dir == ForgeDirection.WEST)
	            	{
	            		world.setBlock(x, y, z, CWTFCBlocks.clayOven, 3, 3);
	            	}

	            	stack.stackSize--; 
	            	
	            	TileEntity tileentity = world.getTileEntity(x, y, z);
	            	if(tileentity != null && tileentity instanceof TileClayOven)
	            	{
	            		TileClayOven te = (TileClayOven)world.getTileEntity(x, y, z);
	            		te.setBuildStage(Constants.PLATFORM);
	            		te.setCuringTime(TFC_Time.getTotalTicks());
	            	}
	            		
	            	return true;
	    		}
			}
		}
		return false;
    }
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(ModInfo.ModID + ":" + "ClayOvenWall");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(this));
	}
	
	@Override
	public boolean canStack()
	{
		return true;
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{		
		ItemTerra.addSizeInformation(is, arraylist);		
		
		addExtraInformation(is, player, arraylist);
	}
}
