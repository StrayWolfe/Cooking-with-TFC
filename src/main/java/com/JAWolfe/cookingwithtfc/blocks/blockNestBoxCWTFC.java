package com.JAWolfe.cookingwithtfc.blocks;

import com.JAWolfe.cookingwithtfc.tileentities.TENestBoxCWTFC;
import com.bioxx.tfc.Blocks.Devices.BlockNestBox;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class blockNestBoxCWTFC extends BlockNestBox
{
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TENestBoxCWTFC();
	}
}
