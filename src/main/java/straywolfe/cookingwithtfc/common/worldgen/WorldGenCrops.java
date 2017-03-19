package straywolfe.cookingwithtfc.common.worldgen;

import java.util.Random;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.managers.CWTFCCropIndex;
import straywolfe.cookingwithtfc.api.managers.CropManager;
import straywolfe.cookingwithtfc.common.tileentity.TileCrop;

public class WorldGenCrops implements IWorldGenerator
{
	public WorldGenCrops()
	{
	}
	
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;
		CropManager manager = CropManager.getInstance();
		int spawnSize = manager.wildcrops.size();

		if(spawnSize > 0 && rand.nextInt(Math.max((19/spawnSize) * 20, 20)) == 0)
		{
			BiomeGenBase biome = world.getBiomeGenForCoords(chunkX, chunkZ);
			if(biome instanceof TFCBiome && biome != TFCBiome.OCEAN && biome != TFCBiome.DEEP_OCEAN)
			{
				CWTFCCropIndex crop = manager.wildcrops.get((rand.nextInt(spawnSize)));
				if(crop != null)
				{
					int num = 2 + rand.nextInt(8);
					int xCoord = chunkX + rand.nextInt(16) + 8;
					int zCoord = chunkZ + rand.nextInt(16) + 8;
					for(int i = 0; i < num; i++)
					{
						generate(world, rand, xCoord, zCoord, 1, crop);
					}
				}
			}
		}
	}
	
	public void generate(World world, Random rand, int x, int z, int numToGen, CWTFCCropIndex crop)
	{
		int i = x, j = 150, k = z;
		for(int c = 0; c < numToGen; c++)
		{
			i = x - 8 + rand.nextInt(16);
			k = z - 8 + rand.nextInt(16);
			j = world.getTopSolidOrLiquidBlock(i, k);
			float temp = TFC_Climate.getHeightAdjustedTempSpecificDay(world, TFC_Time.getTotalDays(), i, j, k);
			int month = TFC_Time.getSeasonAdjustedMonth(k);
			
			if(temp > crop.minAliveTemp && month > 0 && month <= 6)
			{
				Block b = world.getBlock(i, j, k);				
				if(CWTFCBlocks.customCrop.canBlockStay(world, i, j, k) && (b.isAir(world, i, j, k) || b == TFCBlocks.tallGrass))
				{
					if(world.setBlock(i, j, k, CWTFCBlocks.customCrop, rand.nextInt(4), 0x2))
					{
						TileCrop te = (TileCrop)world.getTileEntity(i, j, k);
						if(te != null)
						{
							te.setCropID(crop.cropId);
							float gt = Math.max(crop.growthTime / TFC_Time.daysInMonth, 0.01f);
							float mg = Math.min(month / gt, 1.0f) * (0.75f + (rand.nextFloat() * 0.25f));
							float growth = Math.min(crop.numGrowthStages * mg, crop.numGrowthStages);
							te.growth = growth;
						}
					}
				}
			}
		}
	}

}
