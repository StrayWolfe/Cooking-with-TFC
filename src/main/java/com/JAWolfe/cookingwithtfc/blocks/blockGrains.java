package com.JAWolfe.cookingwithtfc.blocks;


import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.JAWolfe.cookingwithtfc.references.DetailsCWTFC;
import com.JAWolfe.cookingwithtfc.tileentities.TEGrains;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Textures;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class blockGrains extends BlockTerraContainer
{			
	private IIcon[] barleyIcon = new IIcon[4];
	private IIcon[] oatIcon = new IIcon[4];
	private IIcon[] riceIcon = new IIcon[4];
	private IIcon[] ryeIcon = new IIcon[4];
	private IIcon[] wheatIcon = new IIcon[4];
	
	public blockGrains()
	{
		super();
		this.setStepSound(soundTypeGrass);
		this.setHardness(0.5F);
		this.setCreativeTab(null);
		this.setBlockName("GrainsBlock");
		this.setBlockBounds(0, 0, 0, 1, 0.05f, 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int meta)
	{
		if(access.getTileEntity(x, y, z) instanceof TEGrains)
		{
			TEGrains te = (TEGrains) access.getTileEntity(x, y, z);
				
			if(te.getplacedGrains() != null)
			{
				if(te.getplacedGrains().getItem() == CWTFCItems.barleyWholeCWTFC)
					return barleyIcon[te.stage];
				else if(te.getplacedGrains().getItem() == CWTFCItems.oatWholeCWTFC)
					return oatIcon[te.stage];
				else if(te.getplacedGrains().getItem() == CWTFCItems.riceWholeCWTFC)
					return riceIcon[te.stage];
				else if(te.getplacedGrains().getItem() == CWTFCItems.ryeWholeCWTFC)
					return ryeIcon[te.stage];
				else if(te.getplacedGrains().getItem() == CWTFCItems.wheatWholeCWTFC)
					return wheatIcon[te.stage];
			}
		}
		return TFC_Textures.invisibleTexture;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 1; i < 5; i++)
		{
			barleyIcon[i - 1] = iconRegisterer.registerIcon(DetailsCWTFC.ModID + ":" + "Barley (" + i + ")");
			oatIcon[i - 1] = iconRegisterer.registerIcon(DetailsCWTFC.ModID + ":" + "Oat (" + i + ")");
			riceIcon[i - 1] = iconRegisterer.registerIcon(DetailsCWTFC.ModID + ":" + "Rice (" + i + ")");
			ryeIcon[i - 1] = iconRegisterer.registerIcon(DetailsCWTFC.ModID + ":" + "Rye (" + i + ")");
			wheatIcon[i - 1] = iconRegisterer.registerIcon(DetailsCWTFC.ModID + ":" + "Wheat (" + i + ")");
		}
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
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+0.05, z+1);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TEGrains();
	}
	
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityplayer)
	{
		if(!world.isRemote && world.getTileEntity(x, y, z) instanceof TEGrains)
		{
			TEGrains te = (TEGrains)world.getTileEntity(x, y, z);
			if(te.processGrains())
			{
				eject(world, x, y, z);
				world.setBlockToAir(x, y, z);
			}
		}
	}
	
	public void eject(World world, int x, int y, int z)
	{
		if (world.getTileEntity(x, y, z) instanceof TEGrains)
		{
			TEGrains te = (TEGrains)world.getTileEntity(x, y, z);
			te.ejectItem();
			world.removeTileEntity(x, y, z);
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return true;
	}
}
