package straywolfe.cookingwithtfc.common.worldgen;

import java.util.Random;

import com.bioxx.tfc.Core.TFC_Core;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;

public class WorldGenShortTrees extends WorldGenerator
{
	private final int treeId;
	
	public WorldGenShortTrees(boolean flag, int id)
	{
		super(flag);
		treeId = id;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) 
	{
		//Tree height
		int treeTop = y + random.nextInt(3) + 4;
		
		//Basic world height checks and soil check
		if (y < 1 || treeTop + 1 > world.getHeight() || !(TFC_Core.isSoil(world.getBlock(x, y - 1, z))))
			return false;
		
		//Check for required space
		for (int h = y; h <= treeTop + 1; h++)
		{
			byte byte0 = 1;
			if (h == y)
				byte0 = 0;
			if (h >= treeTop - 3)
				byte0 = 2;

			for (int w = x - byte0; w <= x + byte0; w++)
			{
				for (int l = z - byte0; l <= z + byte0; l++)
				{
					Block block = world.getBlock(w, h, l);
					if (!block.isAir(world, w, h, l) && !block.canBeReplacedByLeaves(world, w, h, l))
						return false;
				}
			}
		}

		//Place leaves
		for (int k1 = treeTop - 3; k1 <= treeTop; k1++)
		{
			int j2 = k1 - treeTop;
			int i3 = 1 - j2 / 2;
			for (int k3 = x - i3; k3 <= x + i3; k3++)
			{
				int l3 = k3 - x;
				for (int i4 = z - i3; i4 <= z + i3; i4++)
				{
					int j4 = i4 - z;
					if ((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0) && world.isAirBlock(k3, k1, i4))
						setBlockAndNotifyAdequately(world, k3, k1, i4, CWTFCBlocks.customLeaves, treeId);
				}
			}
		}
		
		//Place logs
		for (int l1 = y; l1 < treeTop; l1++)
			setBlockAndNotifyAdequately(world, x, l1, z, CWTFCBlocks.naturalLog, treeId);
		
		return true;
	}
}
