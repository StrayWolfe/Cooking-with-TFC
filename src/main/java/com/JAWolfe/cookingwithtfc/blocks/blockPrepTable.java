package com.JAWolfe.cookingwithtfc.blocks;

import java.util.List;

import com.JAWolfe.cookingwithtfc.CookingWithTFC;
import com.JAWolfe.cookingwithtfc.init.CWTFCBlocks;
import com.JAWolfe.cookingwithtfc.references.GUIs;
import com.JAWolfe.cookingwithtfc.tileentities.TEPrepTable;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class blockPrepTable extends BlockTerraContainer
{		
	private String[] woodNames;
	
	public blockPrepTable()
	{
		super(Material.wood);
		this.setHardness(1F);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
		this.setBlockName("PrepTableBlock");
		woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0,16);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List metadata)
	{
		for(int i = 0; i < woodNames.length; i++)
			metadata.add(new ItemStack(this, 1, i));
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return TFCBlocks.planks.getIcon(side, meta);
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
		return CWTFCBlocks.prepTableRenderID;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			if (player.isSneaking())
			{
				return false;
			}
			else
			{
				if (world.getTileEntity(x, y, z) instanceof TEPrepTable)
				{					
					player.openGui(CookingWithTFC.instance, GUIs.PREPTABLE.ordinal(), world, x, y, z);
				}
			}
		}
		return false;
	}
	
	@Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
		if (!world.isRemote) 
		{
			if (world.getTileEntity(x, y, z) instanceof TEPrepTable)
			{
				TEPrepTable te = (TEPrepTable)world.getTileEntity(x, y, z);
			
	            if (te != null) 
	            {
	                if (te.getOppositeTE() == null)
	                	world.setBlockToAir(x, y, z);
            	}
            }
        }

        super.onNeighborBlockChange(world, x, y, z, block);
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TEPrepTable();
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
		if (world.getTileEntity(x, y, z) instanceof TEPrepTable)
		{
			TEPrepTable destTable = (TEPrepTable)world.getTileEntity(x, y, z);
			TEPrepTable oppTable = destTable.getOppositeTE();
			
            if (oppTable != null)
            {
            	for(int i = 0; i < destTable.getSizeInventory(); i++)
    			{
    				if(oppTable.getStackInSlot(i) != null)
    					destTable.setInventorySlotContents(i, oppTable.getStackInSlot(i));
    			}
            	
            	destTable.ejectItem();
    			world.removeTileEntity(x, y, z);
            }
        }
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.blockIcon = register.registerIcon("planks_oak");
	}
}
