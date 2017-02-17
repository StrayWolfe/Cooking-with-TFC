package straywolfe.cookingwithtfc.common.tileentity;

import java.util.Random;

import com.bioxx.tfc.Blocks.BlockFarmland;
import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.NetworkTileEntity;
import com.bioxx.tfc.TileEntities.TEFarmland;
import com.bioxx.tfc.TileEntities.TEWorldItem;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import straywolfe.cookingwithtfc.api.managers.CWTFCCropIndex;
import straywolfe.cookingwithtfc.api.managers.CropManager;

public class TileCrop extends NetworkTileEntity
{
	public float growth;
	private long growthTimer;
	private long plantedTime;
	private byte sunLevel;
	public int tendingLevel;
	private int killLevel;
	private int cropID;
	
	public TileCrop()
	{
		growth = 0.1f;
		plantedTime = TFC_Time.getTotalTicks();
		growthTimer = TFC_Time.getTotalTicks();
		sunLevel = 1;
		cropID = -1;
	}
	
	@Override
	public void updateEntity()
	{
		Random r = new Random();
		if(!worldObj.isRemote)
		{
			float timeMultiplier = 360f / TFC_Time.daysInYear;
			long time = TFC_Time.getTotalTicks();
			CWTFCCropIndex crop = CropManager.getInstance().getCropFromId(cropID);
			
			if(crop != null && growthTimer < time && ((crop.needsSunlight && sunLevel > 0) || !crop.needsSunlight))
			{
				sunLevel--;
				if(crop.needsSunlight && hasSunlight(worldObj, xCoord, yCoord, zCoord))
				{
					sunLevel++;
					if(sunLevel > 30)
						sunLevel = 30;
				}

				TEFarmland tef = null;
				TileEntity te = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
				if (te instanceof TEFarmland)
					tef = (TEFarmland) te;

				float ambientTemp = TFC_Climate.getHeightAdjustedTempSpecificDay(worldObj, TFC_Time.getDayOfYearFromTick(growthTimer), xCoord, yCoord, zCoord);
				float tempAdded = 0;
				boolean isDormant = false;
				
				if(!crop.dormantInFrost && ambientTemp < crop.minGrowthTemp)
					tempAdded = -0.03f * (crop.minGrowthTemp - ambientTemp);
				else if(crop.dormantInFrost && ambientTemp < crop.minGrowthTemp)
				{
					if(growth > 1)
						tempAdded = -0.03f * (crop.minGrowthTemp - ambientTemp);
					isDormant = true;
				}
				else if(ambientTemp < 28)
					tempAdded = ambientTemp * 0.00035f;
				else if(ambientTemp < 37)
					tempAdded = (28 - (ambientTemp - 28)) * 0.0003f;

				if(!crop.dormantInFrost && ambientTemp < crop.minAliveTemp)
				{
					int baseKillChance = 6;
					if(worldObj.rand.nextInt(baseKillChance - killLevel) == 0)
						killCrop(crop);
					else
					{
						if(killLevel < baseKillChance - 1)
							killLevel++;
					}
				}
				else if(crop.dormantInFrost && ambientTemp < crop.minAliveTemp)
				{
					if(growth > 1)
					{
						int baseKillChance = 6;
						if(worldObj.rand.nextInt(baseKillChance - killLevel) == 0)
							killCrop(crop);
						else
						{
							if(killLevel < baseKillChance-1)
								killLevel++;
						}
					}
				}
				else
					killLevel = 0;

				int nutriType = crop.cycleType;
				int nutri = tef != null ? tef.nutrients[nutriType] : 18000;
				int fert = tef != null ? tef.nutrients[3] : 0;
				int soilMax = tef != null ? tef.getSoilMax() : 18000;
				float waterBoost = BlockFarmland.isFreshWaterNearby(worldObj, xCoord, yCoord - 1, zCoord) ? 0.1f : 0;

				nutri = Math.min(nutri + fert, (int)(soilMax * 1.25f));

				float nutriMult = 0.2f + ((float) nutri / (float) soilMax) * 0.5f + waterBoost;

				if(tef != null && !isDormant)
				{
					if(tef.nutrients[nutriType] > 0)
						tef.drainNutrients(nutriType, crop.nutrientUsageMult);
					
					if(tef.nutrients[3] > 0)
						tef.drainNutrients(3, crop.nutrientUsageMult);
				}

				float growthRate = Math.max(0.0f, (((crop.numGrowthStages / (crop.growthTime * TFC_Time.timeRatio96) + tempAdded) * nutriMult) * timeMultiplier) * TFCOptions.cropGrowthMultiplier);
				if(tef!= null && tef.isInfested)
					growthRate /= 2;
				int oldGrowth = (int) Math.floor(growth);

				if(!isDormant)
					growth += growthRate;

				if(oldGrowth < (int) Math.floor(growth))
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

				if ((TFCOptions.enableCropsDie || !TFC_Core.isFarmland(worldObj.getBlock(xCoord, yCoord - 1, zCoord))) &&
					(crop.maxLifespan == -1 && growth > crop.numGrowthStages + ((float) crop.numGrowthStages / 2)) || growth < 0)
				{
					killCrop(crop);
				}

				growthTimer += (r.nextInt(2) + 23) * TFC_Time.HOUR_LENGTH;
			}
			else if(crop != null && crop.needsSunlight && sunLevel <= 0)
			{
				killCrop(crop);
			}
			
			if (TFC_Core.isExposedToRain(worldObj, xCoord, yCoord, zCoord) && TFC_Climate.getHeightAdjustedTemp(worldObj, xCoord, yCoord, zCoord) < 0)
			{
				if(crop != null && !crop.dormantInFrost || growth > 1)
				{
					killCrop(crop);
				}
			}
		}
	}
	
	public static boolean hasSunlight(World world, int x, int y, int z)
	{
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		int skylight = chunk.getSavedLightValue(EnumSkyBlock.Sky, x & 15, y, z & 15);
		boolean sky = world.canBlockSeeTheSky(x, y + 1, z);
		return sky || skylight > 13;
	}
	
	public float getEstimatedGrowth(CWTFCCropIndex crop)
	{
		return (growth/ ((float)crop.numGrowthStages - 1)) ;
	}
	
	public void onHarvest(World world, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			CWTFCCropIndex crop = CropManager.getInstance().getCropFromId(cropID);
			
			if(crop != null)
			{
				if (growth >= crop.numGrowthStages - 1)
				{					
					ItemStack is1 = crop.getOutput1(this);
					ItemStack is2 = crop.getOutput2(this);
					ItemStack seedStack = crop.getSeed();
					
					if(is1 != null)
						world.spawnEntityInWorld(new EntityItem(world, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, is1));
					
					if(is2 != null)
						world.spawnEntityInWorld(new EntityItem(world, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, is2));
					
					if(crop.harvestSeed)
					{
						int skill = 20 - (int) (20 * TFC_Core.getSkillStats(player).getSkillMultiplier(Global.SKILL_AGRICULTURE));
						seedStack.stackSize = 1 + (world.rand.nextInt(1 + skill) == 0 ? 1 : 0);
						world.spawnEntityInWorld(new EntityItem(world, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, seedStack));
					}
					
					TFC_Core.getSkillStats(player).increaseSkill(Global.SKILL_AGRICULTURE, 1);
					
					if (TFC_Core.isSoil(world.getBlock(xCoord, yCoord - 1, zCoord)))
						player.addStat(TFC_Achievements.achWildVegetable, 1);
				}
				else
				{
					ItemStack is = crop.getSeed();
					is.stackSize = 1;
					world.spawnEntityInWorld(new EntityItem(world, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, is));
				}
			}
		}
	}
	
	public void killCrop(CWTFCCropIndex crop)
	{
		ItemStack is = crop.getSeed();
		is.stackSize = 1;
		
		if (TFC_Core.isFarmland(worldObj.getBlock(xCoord, yCoord - 1, zCoord)) && TFCOptions.enableSeedDrops)
		{
			if(worldObj.setBlock(xCoord, yCoord, zCoord, TFCBlocks.worldItem))
			{
				TEWorldItem te = (TEWorldItem) worldObj.getTileEntity(xCoord, yCoord, zCoord);
				te.storage[0] = is;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}				
		}
		else
		{
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
	}
	
	public void setCropID(int ID)
	{
		cropID = ID;
	}
	
	public int getCropID()
	{
		return cropID;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		growth = nbt.getFloat("growth");
		growthTimer = nbt.getLong("growthTimer");
		plantedTime = nbt.getLong("plantedTime");
		killLevel = nbt.getInteger("killLevel");
		sunLevel = nbt.getByte("sunLevel");
		cropID= nbt.getInteger("cropID");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setFloat("growth", growth);
		nbt.setLong("growthTimer", growthTimer);
		nbt.setLong("plantedTime", plantedTime);
		nbt.setInteger("killLevel", killLevel);
		nbt.setByte("sunLevel", sunLevel);
		nbt.setInteger("cropID", cropID);
	}
	
	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		growth = nbt.getFloat("growth");
		cropID= nbt.getInteger("cropID");
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}
	
	@Override
	public void handleDataPacket(NBTTagCompound nbt) 
	{
		growth = nbt.getFloat("growth");
		cropID= nbt.getInteger("cropID");
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt) 
	{
		nbt.setFloat("growth", growth);
		nbt.setInteger("cropID", cropID);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		nbt.setFloat("growth", growth);
		nbt.setInteger("cropID", cropID);
	}
}
