package straywolfe.cookingwithtfc.common.worldgen;

import java.util.Random;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import straywolfe.cookingwithtfc.common.lib.Constants;

public class WorldGenTrees implements IWorldGenerator
{
	private float evt;
	private float rainfall;
	private float temperature = 20f;
	private int treeType0;
	private int treeType1;
	private int treeType2;
	
	public WorldGenTrees()
	{		
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;
		
		//Biome check
		BiomeGenBase biome = world.getBiomeGenForCoords(chunkX, chunkZ);
		if(biome instanceof TFCBiome && biome != TFCBiome.OCEAN && 
			biome != TFCBiome.DEEP_OCEAN && biome != TFCBiome.BEACH && biome != TFCBiome.GRAVEL_BEACH)
		{
			treeType0 = TFC_Climate.getTreeLayer(world, chunkX, Global.SEALEVEL, chunkZ, 0);
			treeType1 = TFC_Climate.getTreeLayer(world, chunkX, Global.SEALEVEL, chunkZ, 1);
			treeType2 = TFC_Climate.getTreeLayer(world, chunkX, Global.SEALEVEL, chunkZ, 2);
			
			//Must be in a tree layer
			if (treeType0 != -1 && treeType1 != -1 && treeType2 != -1)
			{
				rainfall = TFC_Climate.getRainfall(world, chunkX, 0, chunkZ);
				evt = TFC_Climate.getCacheManager(world).getEVTLayerAt(chunkX + 8, chunkZ + 8).floatdata1;
				
				generateTrees(random, chunkX, chunkZ, world);
				
				generateFruitTrees(random, chunkX, chunkZ, world);
			}
		}		
	}
	
	private void generateFruitTrees(Random random, int chunkX, int chunkZ, World world)
	{
		int xCoord = chunkX + random.nextInt(16);
		int zCoord = chunkZ + random.nextInt(16);
		int yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
		int meta = random.nextInt(Constants.NUTTREETYPES.length);
		temperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, yCoord, zCoord);
		
		WorldGenerator worldGen = new WorldGenFruitTrees(false, meta);

		if(meta == 1 || meta == 2)
		{
			if(shouldtreeSpawn(random, 0.25f, 2, 500f, 1200f, 25, 45, 175))
				worldGen.generate(world, random, xCoord, yCoord, zCoord);
		}
		else if(shouldtreeSpawn(random, 0.25f, 2, 500f, 1200f, 5, 25, 175))
			worldGen.generate(world, random, xCoord, yCoord, zCoord);
	}
	
	private void generateTrees(Random random, int chunkX, int chunkZ, World world)
	{
		int xCoord = chunkX;
		int yCoord = Global.SEALEVEL + 1;
		int zCoord = chunkZ;

		WorldGenerator worldGen = new WorldGenShortTrees(false, 0);
		int numTrees = 1;
		
		if (random.nextInt(20) == 0)
			numTrees += random.nextInt(3);
		
		for (int var2 = 0; var2 < numTrees; ++var2) 
		{
			xCoord = chunkX + random.nextInt(16);
			zCoord = chunkZ + random.nextInt(16);
			yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);			
			temperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, yCoord, zCoord);
			
			if (shouldtreeSpawn(random, 0.25f, 2, 250f, 1200f, 5, 25, 100))
				worldGen.generate(world, random, xCoord, yCoord, zCoord);
		}
	}

	private boolean shouldtreeSpawn(Random rand, float treeEVTMin, float treeEVTMax, float treeRainMin, float treeRainMax, float treeTempMin, float treeTempMax, int chance)
	{
		int out = 0;
		
		if(temperature >= treeTempMin && temperature <= treeTempMax && 
				evt >= treeEVTMin && evt <= treeEVTMax && 
				rainfall >= treeRainMin && rainfall <= treeRainMax)
		{
			out += optimizer(treeRainMax, treeRainMin, rainfall, 10);
			out += optimizer(treeEVTMax, treeEVTMin, evt, 10);
			out += optimizer(treeTempMax, treeTempMin, temperature, 10);
		}
		else
			out += 1;
		
		return rand.nextInt(chance) < out;
	}
	
	private int optimizer(float max, float min, float value, float multi)
	{
		float range = (max - min)/2;
		float diff = Math.abs((range + min) - value);
		return Math.round(multi * (range - diff)/range);
	}
}
