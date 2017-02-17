package straywolfe.cookingwithtfc.api.managers;

import java.util.Random;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import straywolfe.cookingwithtfc.common.tileentity.TileCrop;

public class CWTFCCropIndex extends CropIndex
{	
	public boolean harvestSeed = true;
	
	public CWTFCCropIndex(int id, String name, int type, int growth, int stages, float minGTemp, float minATemp, Item seed)
	{
		super(id, name, type, growth, stages, minGTemp, minATemp, seed);
		this.cropName = name;
	}
	
	public CWTFCCropIndex(int id, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier, Item seed)
	{
		super(id, name, type, growth, stages, minGTemp, minATemp, nutrientUsageMultiplier, seed);
		this.cropName = name;
	}
	
	public CWTFCCropIndex(int id, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier, Item seed, int[] nutriRestore)
	{
		super(id, name, type, growth, stages, minGTemp, minATemp, nutrientUsageMultiplier, seed, nutriRestore);
		this.cropName = name;
	}
	
	public CWTFCCropIndex setOutput1(Item o, float oAvg)
	{
		output1 = o;
		output1Avg = oAvg;
		return this;
	}
	
	public CWTFCCropIndex setOutput2(Item o, float oAvg)
	{
		output2 = o;
		output2Avg = oAvg;
		return this;
	}
	
	public CWTFCCropIndex setOutput1Chance(Item o, float oAvg, int chance)
	{
		output1 = o;
		output1Avg = oAvg;
		chanceForOutput1 = chance;
		return this;
	}
	
	public CWTFCCropIndex setOutput2Chance(Item o, float oAvg, int chance)
	{
		output2 = o;
		output2Avg = oAvg;
		chanceForOutput2 = chance;
		return this;
	}
	
	public ItemStack getOutput1(TileCrop crop)
	{
		if (output1 != null)
		{
			ItemStack is = new ItemStack(output1);
			Random r = new Random();
			if(r.nextInt(100) < chanceForOutput1)
			{
				ItemFoodTFC.createTag(is, getWeight(output1Avg, r));
				addFlavorProfile(crop, is);
				return is;
			}
		}
		return null;
	}
	public ItemStack getOutput2(TileCrop crop)
	{
		if (output2 != null)
		{
			ItemStack is = new ItemStack(output2);
			Random r = new Random();
			if(r.nextInt(100) < chanceForOutput2)
			{
				ItemFoodTFC.createTag(is, getWeight(output2Avg, r));
				addFlavorProfile(crop, is);
				return is;
			}
		}
		return null;
	}

	private Random getGrowthRand(TileCrop te)
	{
		Block farmBlock = te.getWorldObj().getBlock(te.xCoord, te.yCoord - 1, te.zCoord);
		
		if(!TFC_Core.isSoil(farmBlock))
		{
			int soilType1 = (farmBlock == TFCBlocks.tilledSoil ? te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord - 1, te.zCoord) : 
				te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord - 1, te.zCoord) + 16);
			int soilType2 = (farmBlock == TFCBlocks.dirt ? te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord - 2, te.zCoord) * 2 : 
				farmBlock == TFCBlocks.dirt2 ? (te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord - 2, te.zCoord) + 16) * 2 : 0);

			int ph = TFC_Climate.getCacheManager(te.getWorldObj()).getPHLayerAt(te.xCoord, te.zCoord).data1 *100;
			int drainage = 0;

			for(int y = 2; y < 8; y++)
			{
				if(TFC_Core.isGravel(te.getWorldObj().getBlock(te.xCoord, te.yCoord - y, te.zCoord)))
				{
					drainage++;
				}
			}
			drainage *= 1000;

			return new Random(soilType1 + soilType2 + ph + drainage);
		}
		return null;
	}

	private void addFlavorProfile(TileCrop te, ItemStack outFood)
	{
		Random r = getGrowthRand(te);
		if(r != null)
		{
			Food.adjustFlavor(outFood, r);
		}
	}
	
	public CWTFCCropIndex setNeedsSunlight(boolean b)
	{
		needsSunlight = b;
		return this;
	}

	public CWTFCCropIndex setWaterUsage(float m)
	{
		waterUsageMult = m;
		return this;
	}

	public CWTFCCropIndex setGoesDormant(boolean b)
	{
		dormantInFrost = b;
		return this;
	}
	
	public CWTFCCropIndex setHarvestSeed(boolean b)
	{
		harvestSeed = b;
		return this;
	}
}
