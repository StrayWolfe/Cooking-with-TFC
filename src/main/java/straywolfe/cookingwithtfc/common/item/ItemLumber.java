package straywolfe.cookingwithtfc.common.item;

import java.util.List;

import com.bioxx.tfc.Items.ItemPlank;
import com.bioxx.tfc.TileEntities.TEWoodConstruct;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.core.Tabs;
import straywolfe.cookingwithtfc.common.lib.Constants;
import straywolfe.cookingwithtfc.common.lib.ModInfo;

public class ItemLumber extends ItemPlank
{
	protected IIcon[] icons;
	
	public ItemLumber()
	{
		super();
		setCreativeTab(Tabs.MAINTAB);
		metaNames = Constants.WOODTYPES;
		icons = new IIcon[metaNames.length];
		setUnlocalizedName("SinglePlank");
	}
	
	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			boolean isConstruct = world.getBlock(i, j, k) == CWTFCBlocks.lumberConstruct;
			int offset = !isConstruct ? 1 : 0;
			boolean isAir = world.isAirBlock(i, j, k);
			
			int d = TEWoodConstruct.plankDetailLevel;
			int dd = d*d;
			int dd2 = dd*2;

			float div = 1f / d;

			int x = (int) (hitX / div);
			int y = (int) (hitY / div);
			int z = (int) (hitZ / div);

			hitX = Math.round(hitX*100)/100.0f;
			hitY = Math.round(hitY*100)/100.0f;
			hitZ = Math.round(hitZ*100)/100.0f;

			boolean isEdge = false;

			if(hitX == 0 || hitX == 1 || hitY == 0 || hitY == 1 || hitZ == 0 || hitZ == 1)
			{
				isEdge = true;
				isConstruct = true;
				offset = 1;
			}
			
			if(side == 0)
			{
				if (!isConstruct && isAir || isConstruct && isEdge && world.isAirBlock(i, j - offset, k))
					world.setBlock(i, j - 1, k, CWTFCBlocks.lumberConstruct);
				
				TileEntity tile = world.getTileEntity(i, j-offset, k);

				if(setupTile(dd+(x+(z*d)), is, tile))
					return false;
			}
			else if(side == 1)
			{
				if (!isConstruct && isAir || isConstruct && isEdge && world.isAirBlock(i, j + offset, k))
					world.setBlock(i, j+1, k, CWTFCBlocks.lumberConstruct);
				
				TileEntity tile = world.getTileEntity(i, j+offset, k);
				
				if(setupTile(dd+(x+(z*d)), is, tile))
					return false;
			}
			else if(side == 2)
			{
				if (!isConstruct && isAir || isConstruct && isEdge && world.isAirBlock(i, j, k - offset))
					world.setBlock(i, j, k-1, CWTFCBlocks.lumberConstruct);
				
				TileEntity tile = world.getTileEntity(i, j, k-offset);

				if(setupTile(dd2+(x+(y*d)), is, tile))
					return false;
			}
			else if(side == 3)
			{
				if (!isConstruct && isAir || isConstruct && isEdge && world.isAirBlock(i, j, k + offset))
					world.setBlock(i, j, k+1, CWTFCBlocks.lumberConstruct);
				
				TileEntity tile = world.getTileEntity(i, j, k+offset);

				if(setupTile(dd2+(x+(y*d)), is, tile))
					return false;
			}
			else if(side == 4)
			{
				if (!isConstruct && isAir || isConstruct && isEdge && world.isAirBlock(i - offset, j, k))
					world.setBlock(i-1, j, k, CWTFCBlocks.lumberConstruct);
				
				TileEntity tile = world.getTileEntity(i-offset, j, k);
				
				if(setupTile((y+(z*d)), is, tile))
					return false;
			}
			else if(side == 5)
			{
				if (!isConstruct && isAir || isConstruct && isEdge && world.isAirBlock(i + offset, j, k))
					world.setBlock(i+1, j, k, CWTFCBlocks.lumberConstruct);
				
				TileEntity tile = world.getTileEntity(i+offset, j, k);

				if(setupTile((y+(z*d)), is, tile))
					return false;
			}
			
			is.stackSize--;
			return true;
		}
		
		return false;
	}
	
	protected boolean setupTile(int index, ItemStack is, TileEntity tile)
	{
		if (!(tile instanceof TEWoodConstruct))
			return true;

		TEWoodConstruct te = (TEWoodConstruct)tile;
		te.data.set(index);
		te.woodTypes[index] = (byte) is.getItemDamage();
		
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("index", index);
		nbt.setByte("meta", (byte) is.getItemDamage());
		te.broadcastPacketInRange(te.createDataPacket(nbt));
		
		return false;
	}
	
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < metaNames.length; i++)
			icons[i] = registerer.registerIcon(ModInfo.ModID + ":wood/" + metaNames[i] + " Singleplank");
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < metaNames.length; i++)
			list.add(new ItemStack(this,1,i));
	}
}
