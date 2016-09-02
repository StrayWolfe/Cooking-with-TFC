package straywolfe.cookingwithtfc.common.tileentity;

import java.util.Random;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.TileEntities.NetworkTileEntity;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCItems;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileGrains extends NetworkTileEntity
{
	private ItemStack placedGrain;
	private int workcounter = 0;
	public int stage = 0;
	private int strawCount;
	
	public void setplacedGrains(ItemStack is)
	{
		placedGrain = is;
		if(placedGrain.getItem() instanceof ItemFoodTFC)
			strawCount = Math.round(Food.getWeight(placedGrain) * 10/((ItemFoodTFC)placedGrain.getItem()).getFoodMaxWeight(placedGrain));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public ItemStack getplacedGrains()
	{
		return placedGrain;
	}
	
	@Override
	public void updateEntity()
	{		
		if(!worldObj.isRemote && placedGrain != null)
		{
			placedGrain = TFC_Core.tickDecay(placedGrain, worldObj,  xCoord, yCoord, zCoord, 1f, 1f);
			if(placedGrain == null)
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
	}
	
	public boolean processGrains()
	{
		if(workcounter < 19)
		{
			if(workcounter == 4 || workcounter == 9 || workcounter == 14)
			{
				stage++;
				if(strawCount > 0)
					ejectItem(new ItemStack(TFCItems.straw, strawCount));
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
				
			workcounter++;
			return false;
		}
		else
		{
			if(placedGrain != null)
			{
				placedGrain = getGrains();
				
				if(strawCount > 0)
					ejectItem(new ItemStack(TFCItems.straw, strawCount));
				return true;
			}
			return false;
		}
	}
	
	public ItemStack getGrains()
	{
		Item foodtype = placedGrain.getItem();
		
		if(placedGrain.getItem() == TFCItems.barleyWhole)
			foodtype = TFCItems.barleyGrain;
		else if(placedGrain.getItem() == TFCItems.oatWhole)
			foodtype = TFCItems.oatGrain;
		else if(placedGrain.getItem() == TFCItems.riceWhole)
			foodtype = TFCItems.riceGrain;
		else if(placedGrain.getItem() == TFCItems.ryeWhole)
			foodtype = TFCItems.ryeGrain;
		else if(placedGrain.getItem() == TFCItems.wheatWhole)
			foodtype = TFCItems.wheatGrain;
			
		ItemStack food = new ItemStack(foodtype);
		
		if (placedGrain.stackTagCompound != null)
			food.stackTagCompound = (NBTTagCompound)placedGrain.stackTagCompound.copy();
		
		return food;
	}
	
	public void ejectItem()
	{
		if(stage == 0)
			ejectItem(placedGrain);
		else
		{
			ejectItem(getGrains());
		}
	}
	
	public void ejectItem(ItemStack is)
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		if(is != null)
		{
			entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, is);
			entityitem.motionX = (float)rand.nextGaussian() * f3;
			entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.05F;
			entityitem.motionZ = (float)rand.nextGaussian() * f3;
			worldObj.spawnEntityInWorld(entityitem);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		placedGrain = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("placedGrain"));
		workcounter = nbt.getInteger("WorkCounter");
		stage = nbt.getInteger("Stage");
		strawCount = nbt.getInteger("StrawCount");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);		
		NBTTagCompound grainNBT = new NBTTagCompound();
        placedGrain.writeToNBT(grainNBT);
        nbt.setTag("placedGrain", grainNBT);
        nbt.setInteger("StrawCount", strawCount);
        nbt.setInteger("WorkCounter", workcounter);
        nbt.setInteger("Stage", stage);
	}
	
	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		stage = nbt.getInteger("stage");
		workcounter = nbt.getInteger("WorkCounter");
		strawCount = nbt.getInteger("StrawCount");
		placedGrain = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("placedGrain"));
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) 
	{
		stage = nbt.getInteger("stage");
		workcounter = nbt.getInteger("WorkCounter");
		strawCount = nbt.getInteger("StrawCount");
		placedGrain = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("placedGrain"));
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt) 
	{
		nbt.setInteger("stage", stage);
		nbt.setInteger("StrawCount", strawCount);
        nbt.setInteger("WorkCounter", workcounter);
		NBTTagCompound grainNBT = new NBTTagCompound();
        placedGrain.writeToNBT(grainNBT);
        nbt.setTag("placedGrain", grainNBT);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		nbt.setInteger("stage", stage);
		nbt.setInteger("StrawCount", strawCount);
        nbt.setInteger("WorkCounter", workcounter);
		NBTTagCompound grainNBT = new NBTTagCompound();
        placedGrain.writeToNBT(grainNBT);
        nbt.setTag("placedGrain", grainNBT);
	}
}
