package com.JAWolfe.cookingwithtfc.blocks;

import java.util.List;

import com.JAWolfe.cookingwithtfc.tileentities.TEPrepTable;
import com.JAWolfe.cookingwithtfc.util.LogHelper;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class blockPrepTable2 extends blockPrepTable
{
	private String[] woodNames;
	
	public blockPrepTable2()
	{
		super();
		this.setHardness(1F);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
		this.setBlockName("PrepTableBlock2");
		woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0,Global.WOOD_ALL.length - 16);
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
		return TFCBlocks.planks2.getIcon(side, meta-16);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.blockIcon = register.registerIcon("planks_oak");
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
	                {
	                	LogHelper.info("Destroy block");
	                	world.setBlockToAir(x, y, z);
	                }
            	}
            }
        }

        super.onNeighborBlockChange(world, x, y, z, block);
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
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TEPrepTable();
	}
}
