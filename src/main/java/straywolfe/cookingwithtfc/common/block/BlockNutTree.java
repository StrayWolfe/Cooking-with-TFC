package straywolfe.cookingwithtfc.common.block;

import java.util.Random;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.Flora.BlockFruitWood;
import com.bioxx.tfc.Core.TFC_Core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.lib.Constants;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.tileentity.TileNutTree;

public class BlockNutTree extends BlockFruitWood
{
	protected IIcon[] icons;
	protected IIcon[] saplingIcons;
	
	public BlockNutTree()
	{
		saplingIcons = new IIcon[Constants.NUTTREETYPES.length];
		icons = new IIcon[2];
		setBlockName("NutTreeLog");
		setResistance(2F);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer)
	{
		icons[0] = registerer.registerIcon(Reference.MOD_ID + ":wood/fruit trees/Olive Wood");
		icons[1] = registerer.registerIcon(Reference.MOD_ID + ":wood/fruit trees/Banana Wood");
		
		for(int i = 0; i < saplingIcons.length; i++)
			saplingIcons[i] = registerer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/" + Constants.NUTTREETYPES[i] + " Sapling");
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		//Need a method that does not require an item
	}
	
	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int l, EntityPlayer player)
	{
		boolean isAxeorSaw = false;
		ItemStack equip = player.getCurrentEquippedItem();
		int meta = world.getBlockMetadata(x, y, z);
		
		if (equip != null)
		{
			int[] equipIDs = OreDictionary.getOreIDs(equip);
			for (int id : equipIDs)
			{
				String name = OreDictionary.getOreName(id);
				if (name.startsWith("itemAxe") || name.startsWith("itemSaw"))
				{
					isAxeorSaw = true;
					break;
				}
			}
		}
		
		if(isSapling(world, x, y, z))
		{
			dropBlockAsItem(world, x, y, z, new ItemStack(this, 1, meta));
		}
		else if(isAxeorSaw)
		{
			int i = x;
			int j = 0;
			int k = z;
			
			if(world.getBlock(x, y + 1, z) == this || world.getBlock(x, y - 1, z) == this)
			{
				boolean checkArray[][][] = new boolean[11][50][11];
				if(TFC_Core.isGrass(world.getBlock(x, y + j - 1, z)) || TFC_Core.isDirt(world.getBlock(x, y + j - 1, z)))
				{
					boolean reachedTop = false;
					while(!reachedTop)
					{
						if (world.isAirBlock(i, y + j + 1, k))
							reachedTop = true;
						
						scanLogs(world, x, y + j, z, meta, checkArray, 6, j, 6);
						j++;
					}
				}
			}
			else if(world.getBlock(x + 1, y, z) == this || world.getBlock(x - 1, y, z) == this ||
					world.getBlock(x, y, z + 1) == this || world.getBlock(x, y, z - 1) == this)
			{
				Random r = new Random();
				if(r.nextInt(100) > 50 && isAxeorSaw)
					dropBlockAsItem(world, x, y, z, new ItemStack(this, 1, meta));
			}
		}
	}
	
	protected void scanLogs(World world, int i, int j, int k, int l, boolean[][][] checkArray,int x, int y, int z)
	{
		if(y >= 0)
		{
			checkArray[x][y][z] = true;
			int offsetX = 0;
			int offsetY = 0;
			int offsetZ = 0;

			for (offsetY = 0; offsetY <= 1; offsetY++)
			{
				for (offsetX = -1; offsetX <= 1; offsetX++)
				{
					for (offsetZ = -1; offsetZ <= 1; offsetZ++)
					{
						if(x + offsetX < 11 && x + offsetX >= 0 && z + offsetZ < 11 && z + offsetZ >= 0 && y + offsetY < 50 && y + offsetY >= 0)
						{
							if(checkOut(world, i + offsetX, j + offsetY, k + offsetZ, l) && !checkArray[x + offsetX][y + offsetY][z + offsetZ])
								scanLogs(world,i + offsetX, j + offsetY, k + offsetZ, l, checkArray, x + offsetX, y + offsetY, z + offsetZ);
						}
					}
				}
			}
			world.setBlockToAir(i, j, k);
		}
	}
	
	protected boolean checkOut(World world, int i, int j, int k, int l)
	{
		return world.getBlock(i, j, k) == this && world.getBlockMetadata(i, j, k) == l;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		switch(meta)
		{
			case 2: return icons[1];
			default: return icons[0];
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		int meta = world.getBlockMetadata(x, y, z);
		
		if(isSapling(world, x, y, z))
			return saplingIcons[meta];
		
		return getIcon(side, meta);
	}
	
	public String getTreeType(Block block, int meta)
	{		
		if(meta < Constants.NUTTREETYPES.length)
			return Constants.NUTTREETYPES[meta];
		else		
			return "";
	}
	
	@Override
	public int getRenderType()
	{
		return CWTFCBlocks.fruitTreeRenderID;
	}
	
	@Override
	public Item getItemDropped(int i, Random random, int j)
	{
		return CWTFCItems.logs;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileNutTree();
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		if(isSapling(world, x, y, z))
			return null;
		else if(world.getBlock(x,  y - 1, z) == this || world.getBlock(x, y - 1, z).isOpaqueCube())
			return AxisAlignedBB.getBoundingBox(x + 0.3, y, z + 0.3, x + 0.7, y + 1, z + 0.7);
		
		return AxisAlignedBB.getBoundingBox(x, y + 0.4, z, x + 1, y + 0.6, z + 1);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		if(isSapling(world, x, y, z))
			return AxisAlignedBB.getBoundingBox(x + 0.1, y, z + 0.1, x + 0.9, y + 0.8, z + 0.9);
		else if(world.getBlock(x, y - 1, z) == this || world.getBlock(x, y - 1, z).isOpaqueCube())
			return AxisAlignedBB.getBoundingBox(x + 0.3, y, z + 0.3, x + 0.7, y + 1, z + 0.7);
		
		return AxisAlignedBB.getBoundingBox(x, y + 0.4, z, x + 1, y + 0.6, z + 1);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		if(isSapling(world, x, y, z))
			setBlockBounds(0.1f, 0, 0.1f, 0.9f, 0.8f, 0.9f);
		else if (world.getBlock(x, y - 1, z) == this || world.getBlock(x, y - 1, z).isOpaqueCube())
			setBlockBounds(0.3f, 0, 0.3f, 0.7f, 1, 0.7f);
		else
			setBlockBounds(0, 0.4f, 0, 1, 0.6f, 1);
	}
	
	protected boolean isSapling(IBlockAccess world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		return te instanceof TileNutTree && ((TileNutTree)te).isSapling == 1;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		if(!world.isRemote && checkOut(world,x,y-1,z,metadata) && world.getTileEntity(x, y-1, z) != null)
			((TileNutTree)world.getTileEntity(x, y-1, z)).initBirth();
		world.removeTileEntity(x, y, z);
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z)
    {
		if(isSapling(world, x, y, z))
			return 0.1f;
		
		return 5.5f;
    }
}
