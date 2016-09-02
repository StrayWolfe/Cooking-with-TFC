package straywolfe.cookingwithtfc.common.block;

import java.util.List;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.core.helper.Helper;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.lib.Textures;
import straywolfe.cookingwithtfc.common.tileentity.TileMixBowl;

public class BlockMixBowl extends BlockTerraContainer
{
	@SideOnly(Side.CLIENT)
	private IIcon clayIcons;
	@SideOnly(Side.CLIENT)
	private IIcon ceramicIcons;
	
	public BlockMixBowl()
	{
		super();
		
		setStepSound(soundTypeGlass);
		setHardness(0.5F);
		setCreativeTab(TFCTabs.TFC_DEVICES);
		setBlockName("MixingBowl");
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if(!player.isSneaking() && world.getTileEntity(x, y, z) instanceof TileMixBowl)
			{
				TileMixBowl te = (TileMixBowl)world.getTileEntity(x, y, z);
				ItemStack item = player.getCurrentEquippedItem();
				
				if(item == null)
				{
					if(!te.getCompleted())
					{
						ItemStack contents = te.getContents();
						
						if(contents != null)
						{
							player.setCurrentItemOrArmor(0, contents);
							
							te.setContents(null);
						}
						else
						{
							player.setCurrentItemOrArmor(0, new ItemStack(CWTFCBlocks.mixingBowl, 1, 1));
							world.removeTileEntity(x, y, z);
							world.setBlockToAir(x, y, z);
						}
					}
					else
					{
						player.setCurrentItemOrArmor(0, te.getOutput());
								
						te.setContents(null);
						te.setCompleted(false);
					}
				}
				else if(Helper.isOre("itemFlour", item))
				{
					if(te.getContents() == null)
					{
						te.setContents(item);
						player.setCurrentItemOrArmor(0, null);
					}
				}
				else if(item.getItem() == TFCItems.potteryJug && item.getItemDamage() == 2)
				{
					if(te.getContents() != null)
					{
						te.setCompleted(true);
						player.setCurrentItemOrArmor(0, new ItemStack(TFCItems.potteryJug, 1, 1));
					}
				}
			}
			world.markBlockForUpdate(x, y, z);
		}
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubBlocks(Item item, CreativeTabs tab, List metadata) 
	{
		metadata.add(new ItemStack(this, 1, 0));
		metadata.add(new ItemStack(this, 1, 1));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer)
	{
		ceramicIcons = registerer.registerIcon(Reference.MOD_ID + ":" + "clay/Ceramic");
		clayIcons = registerer.registerIcon(Reference.MOD_ID + ":" + "clay/Clay");
		Textures.White_BG = registerer.registerIcon(ModInfo.ModID + ":" + "White_BG");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if(meta == 1)
			return ceramicIcons;
		
		return clayIcons;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		int meta= access.getBlockMetadata(x, y, z);
		
		if(meta == 1)
			return ceramicIcons;
		
		return clayIcons;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return CWTFCBlocks.mixingBowlRenderID;
	}	
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileMixBowl();
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.isRemote && world.isAirBlock(x, y - 1, z))
		{
			eject(world, x, y, z);
			world.setBlockToAir(x, y, z);
			return;
		}
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int l)
	{
		eject(world, x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		eject(world, x, y, z);
	}
	
	public void eject(World world, int x, int y, int z)
	{
		if (world.getTileEntity(x, y, z) instanceof TileMixBowl)
		{			
			((TileMixBowl)world.getTileEntity(x, y, z)).ejectItem();
    		world.removeTileEntity(x, y, z);
        }
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
		TileMixBowl te = (TileMixBowl)world.getTileEntity(x, y, z);
		float xCoord = te.getBowlCoord(0);
		float zCoord = te.getBowlCoord(1);
		
		if(xCoord != -1 && zCoord != -1)
		{
			float minX = xCoord;
			float maxX = xCoord + 0.5F;
			float minY = 0.0F;
			float maxY = 0.3F;
			float minZ = zCoord;
			float maxZ = zCoord + 0.5F;

			setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
		else
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
    }
    
	@Override
	@SuppressWarnings("rawtypes")
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
	{
    	TileMixBowl te = (TileMixBowl)world.getTileEntity(x, y, z);
		float xCoord = te.getBowlCoord(0);
		float zCoord = te.getBowlCoord(1);
		
		if(xCoord != -1 && zCoord != -1)
		{
			float minX = xCoord;
			float maxX = xCoord + 0.5F;
			float minY = 0.0F;
			float maxY = 0.3F;
			float minZ = zCoord;
			float maxZ = zCoord + 0.5F;
			
			setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
			super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
			setBlockBoundsBasedOnState(world, x, y, z);
		}
	}
}
