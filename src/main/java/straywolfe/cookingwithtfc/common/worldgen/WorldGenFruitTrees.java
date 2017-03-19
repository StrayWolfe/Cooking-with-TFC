package straywolfe.cookingwithtfc.common.worldgen;

import java.util.Random;

import com.bioxx.tfc.Core.TFC_Core;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.tileentity.TileNutTree;

public class WorldGenFruitTrees extends WorldGenerator
{
	int metadata;
	
	public WorldGenFruitTrees(boolean flag, int meta)
	{
		super(flag);
		metadata = meta;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) 
	{
		//Tree top location
		int treeTop = y + (metadata != 2 ? 4 : 6);
		
		//Basic world height checks and soil check
		if (y < 1 || treeTop > world.getHeight() || !(TFC_Core.isSoil(world.getBlock(x, y - 1, z))))
			return false;
		
		//Check for required space
		for (int h = y; h < treeTop; h++)
		{
			byte radius = 1;
			
			if(metadata == 2)
			{
				if (h <= y + 5)
					radius = 0;
				else
					radius = 2;
			}
			else
			{
				if (h <= y + 1 || h == y + 4)
					radius = 0;
				else if (h == y + 2)
					radius = 2;
				else
					radius = 1;
			}
			
			for (int w = x - radius; w <= x + radius; w++)
			{
				for (int l = z - radius; l <= z + radius; l++)
				{
					Block block = world.getBlock(w, h, l);
					if (h == y || h == y + 1)
					{
						if(!block.isAir(world, w, h, l) && !block.canBeReplacedByLeaves(world, w, h, l))
							return false;
					}
					else
					{
						if(!block.isAir(world, w, h, l))
							return false;
					}
				}
			}
		}
		
		//Place logs
		for(int i = y; i < treeTop; i++)
		{
			if(world.setBlock(x, i, z, CWTFCBlocks.nutTreeLog, metadata, 3))
				((TileNutTree)world.getTileEntity(x, i, z)).initBirth(true, i - y);
		}
		
		if(metadata == 2 && world.setBlock(x, treeTop, z, CWTFCBlocks.nutTreeLog, metadata, 3))
			((TileNutTree)world.getTileEntity(x, treeTop, z)).initBirth(true, treeTop - y);
		
		//Place branches
		if(metadata != 2)
		{
			if(world.setBlock(x + 1, y + 2, z, CWTFCBlocks.nutTreeLog, metadata, 3))
				((TileNutTree)world.getTileEntity(x + 1, y + 2, z)).initBirth(false, 2);
			
			if(world.setBlock(x - 1, y + 2, z, CWTFCBlocks.nutTreeLog, metadata, 3))
				((TileNutTree)world.getTileEntity(x - 1, y + 2, z)).initBirth(false, 2);
			
			if(world.setBlock(x, y + 2, z + 1, CWTFCBlocks.nutTreeLog, metadata, 3))
				((TileNutTree)world.getTileEntity(x, y + 2, z + 1)).initBirth(false, 2);
			
			if(world.setBlock(x, y + 2, z - 1, CWTFCBlocks.nutTreeLog, metadata, 3))
				((TileNutTree)world.getTileEntity(x, y + 2, z - 1)).initBirth(false, 2);
		}
		
		//Place leaves
		if(metadata == 2)
		{
			world.setBlock(x + 1, treeTop, z, CWTFCBlocks.nutTreeLeaves, metadata, 3);
			world.setBlock(x - 1, treeTop, z, CWTFCBlocks.nutTreeLeaves, metadata, 3);
			world.setBlock(x, treeTop, z + 1, CWTFCBlocks.nutTreeLeaves, metadata, 3);
			world.setBlock(x, treeTop, z - 1, CWTFCBlocks.nutTreeLeaves, metadata, 3);
		}
		else
		{
			surroundWithLeaves(world, x + 1, y + 2, z);
			surroundWithLeaves(world, x - 1, y + 2, z);
			surroundWithLeaves(world, x, y + 2, z + 1);
			surroundWithLeaves(world, x, y + 2, z - 1);
			surroundWithLeaves(world, x, y + 3, z);
			world.setBlock(x, treeTop, z, CWTFCBlocks.nutTreeLeaves, metadata, 3);
		}
			
		return true;
	}
	
	public void surroundWithLeaves(World world, int x, int y, int z)
	{
		for (int w = 1; w >= -1; w--)
		{
			for (int l = 1; l >= -1; l--)
			{
				if(world.getBlock(x + w, y, z + l) != CWTFCBlocks.nutTreeLog)
					world.setBlock(x + w, y, z + l, CWTFCBlocks.nutTreeLeaves, metadata, 3);
			}
		}
	}
}
