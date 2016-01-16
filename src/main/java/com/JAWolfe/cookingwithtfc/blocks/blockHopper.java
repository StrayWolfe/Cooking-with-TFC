package com.JAWolfe.cookingwithtfc.blocks;

import com.JAWolfe.cookingwithtfc.tileentities.TEHopperCWTFC;
import com.bioxx.tfc.Blocks.Devices.BlockHopper;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class blockHopper extends BlockHopper
{
	
	public blockHopper()
	{
		super();
		this.setHardness(2F);
		this.setCreativeTab(null);
		this.setBlockName("Hopper");
	}

	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEHopperCWTFC();
	}
}
