package com.JAWolfe.cookingwithtfc.blocks;

import java.util.List;
import java.util.Random;

import com.JAWolfe.cookingwithtfc.init.CWTFCBlocks;
import com.JAWolfe.cookingwithtfc.tileentities.TEMixBowl;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class blockMixBowl extends BlockTerraContainer
{
	private IIcon[] clayIcons;
	private IIcon[] ceramicIcons;
	
	public blockMixBowl()
	{
		super();
		
		this.setStepSound(soundTypeGlass);
		this.setHardness(0.5F);
		this.setBlockBounds(0.25f, 0, 0.25f, 0.75f, 0.3f, 0.75f);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
		setBlockName("MixingBowl");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List metadata) 
	{
		metadata.add(new ItemStack(this, 1, 0));
		metadata.add(new ItemStack(this, 1, 1));
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		ceramicIcons = new IIcon[3];
		clayIcons = new IIcon[3];
		ceramicIcons[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Ceramic Vessel Top");
		ceramicIcons[1] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Ceramic Vessel Side");
		ceramicIcons[2] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Ceramic Vessel Bottom");
		clayIcons[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Clay Vessel Top");
		clayIcons[1] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Clay Vessel Side");
		clayIcons[2] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Clay Vessel Bottom");
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(meta == 1)
		{
			if(side == 1)
				return ceramicIcons[0];
			else if(side == 0)
				return ceramicIcons[2];
			else
				return ceramicIcons[1];
		}
		if(side == 1)
			return clayIcons[0];
		else if(side == 0)
			return clayIcons[2];
		else
			return clayIcons[1];
	}
	
	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		int meta= access.getBlockMetadata(x, y, z);
		if(meta == 1)
		{
			if(side == 1)
				return ceramicIcons[0];
			else if(side == 0)
				return ceramicIcons[2];
			else
				return ceramicIcons[1];
		}
		if(side == 1)
			return clayIcons[0];
		else if(side == 0)
			return clayIcons[2];
		else
			return clayIcons[1];
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		return false;
	}
	
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TEMixBowl();
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.isRemote && !world.isSideSolid(x, y - 1, z, ForgeDirection.UP))
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
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion ex)
	{
		eject(world, x, y, z);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metaData)
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
		if (world.getTileEntity(x, y, z) instanceof TEMixBowl)
		{			
			float f3 = 0.05F;
			EntityItem entityitem;
			Random rand = new Random();
			float f = rand.nextFloat() * 0.8F + 0.1F;
			float f1 = rand.nextFloat() * 2.0F + 0.4F;
			float f2 = rand.nextFloat() * 0.8F + 0.1F;
			entityitem = new EntityItem(world, x + f, y + f1, z + f2, 
					new ItemStack(CWTFCBlocks.mixingBowl, 1, 1));
			
			entityitem.motionX = (float)rand.nextGaussian() * f3;
			entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.05F;
			entityitem.motionZ = (float)rand.nextGaussian() * f3;
			world.spawnEntityInWorld(entityitem);
			
    		world.removeTileEntity(x, y, z);
        }
	}
}
