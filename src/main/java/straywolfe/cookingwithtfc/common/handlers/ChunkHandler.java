package straywolfe.cookingwithtfc.common.handlers;

import java.util.Random;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.WorldGen.TFCBiome;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import straywolfe.cookingwithtfc.api.managers.CWTFCCropIndex;
import straywolfe.cookingwithtfc.api.managers.ChunkData;
import straywolfe.cookingwithtfc.api.managers.CropManager;
import straywolfe.cookingwithtfc.common.core.CWTFC_Core;
import straywolfe.cookingwithtfc.common.worldgen.WorldGenCrops;

public class ChunkHandler 
{
	@SubscribeEvent
	public void onLoad(ChunkEvent.Load event)
	{
		if (!event.world.isRemote && CWTFC_Core.getCDM(event.world) != null && event.getChunk() != null)
		{
			ChunkData cd = CWTFC_Core.getCDM(event.world).getData(event.getChunk().xPosition, event.getChunk().zPosition);
			if (cd == null)
				return;
			
			int month = TFC_Time.getSeasonAdjustedMonth(event.getChunk().zPosition << 4);
			
			if (TFC_Time.getYear() > cd.lastSpringTime && month > 1 && month < 6)
			{
				int chunkX = event.getChunk().xPosition;
				int chunkZ = event.getChunk().zPosition;	
				CropManager manager = CropManager.getInstance();
				int spawnSize = manager.wildcrops.size();
				Random rand = new Random(event.world.getSeed() + ((chunkX >> 7) - (chunkZ >> 7)) * (chunkZ >> 7));
				cd.lastSpringTime = TFC_Time.getYear();
				
				if (spawnSize > 0 && rand.nextInt(Math.max((19/spawnSize) * 25, 25)) == 0)
				{
					BiomeGenBase biome = event.world.getBiomeGenForCoords(event.getChunk().xPosition, event.getChunk().zPosition);
					if(biome != TFCBiome.OCEAN && biome != TFCBiome.DEEP_OCEAN)
					{
						CWTFCCropIndex crop = manager.wildcrops.get((rand.nextInt(spawnSize)));
						if(crop != null)
						{
							int num = 1 + rand.nextInt(5);
							WorldGenCrops genCrops = new WorldGenCrops();
							int x = (chunkX << 4) + rand.nextInt(16) + 8;
							int z = (chunkZ << 4) + rand.nextInt(16) + 8;
							genCrops.generate(event.world, rand, x, z, num, crop);
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onUnload(ChunkEvent.Unload event)
	{
		if(CWTFC_Core.getCDM(event.world) != null && 
		   CWTFC_Core.getCDM(event.world).getData(event.getChunk().xPosition, event.getChunk().zPosition) != null)
			CWTFC_Core.getCDM(event.world).getData(event.getChunk().xPosition, event.getChunk().zPosition).isUnloaded = true;
	}
	
	@SubscribeEvent
	public void onDataLoad(ChunkDataEvent.Load event)
	{
		if(!event.world.isRemote)
		{
			NBTTagCompound eventTag = event.getData();
			
			if(eventTag.hasKey("CWTFCData"))
			{
				NBTTagCompound CWTFCData = eventTag.getCompoundTag("CWTFCData");
				if(CWTFC_Core.getCDM(event.world) != null)
					CWTFC_Core.getCDM(event.world).addData(event.getChunk(), new ChunkData(CWTFCData));
			}
			else
			{
				if(CWTFC_Core.getCDM(event.world) != null)
					CWTFC_Core.getCDM(event.world).addData(event.getChunk(), new ChunkData());
			}
		}
	}
	
	@SubscribeEvent
	public void onDataSave(ChunkDataEvent.Save event)
	{
		if (!event.world.isRemote && CWTFC_Core.getCDM(event.world) != null)
		{
			NBTTagCompound levelTag = event.getData().getCompoundTag("Level");
			int x = levelTag.getInteger("xPos");
			int z = levelTag.getInteger("zPos");
			ChunkData data = CWTFC_Core.getCDM(event.world).getData(x, z);
			
			if(data != null)
			{
				NBTTagCompound CWTFCTag = data.getTag();
				event.getData().setTag("CWTFCData", CWTFCTag);
				if(data.isUnloaded)
					CWTFC_Core.getCDM(event.world).removeData(x, z);
			}
		}
	}
	
	@SubscribeEvent
	public void onLoadWorld(WorldEvent.Load event)
	{
		CWTFC_Core.addCDM(event.world);
	}
	
	@SubscribeEvent
	public void onUnloadWorld(WorldEvent.Unload event)
	{
		CWTFC_Core.removeCDM(event.world);
	}
}
