package com.JAWolfe.cookingwithtfc.tileentities;

import java.util.Random;

import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.JAWolfe.cookingwithtfc.items.ItemTFCFoodTransform;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.NetworkTileEntity;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCItems;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TEGrains extends NetworkTileEntity
{
	private ItemStack placedGrain;
	private int workcounter = 0;
	public int stage = 0;
	private int strawCount;
	
	public void setplacedGrains(ItemStack is)
	{
		placedGrain = is;
		if(placedGrain.getItem() instanceof ItemTFCFoodTransform)
			strawCount = Math.round(Food.getWeight(placedGrain) * 10/((ItemTFCFoodTransform)placedGrain.getItem()).getFoodMaxWeight(placedGrain));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public ItemStack getplacedGrains()
	{
		return placedGrain;
	}
	
	@Override
	public void updateEntity()
	{		
		if(!worldObj.isRemote)
		{
			if(placedGrain != null)
			{
				placedGrain = TFC_Core.tickDecay(placedGrain, worldObj,  xCoord, yCoord, zCoord, 1f, 1f);
				if(placedGrain == null)
					worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
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
				Item foodtype = placedGrain.getItem();
				
				if(placedGrain.getItem() == CWTFCItems.barleyWholeCWTFC)
					foodtype = CWTFCItems.barleyGrainCWTFC;
				else if(placedGrain.getItem() == CWTFCItems.oatWholeCWTFC)
					foodtype = CWTFCItems.oatGrainCWTFC;
				else if(placedGrain.getItem() == CWTFCItems.riceWholeCWTFC)
					foodtype = CWTFCItems.riceGrainCWTFC;
				else if(placedGrain.getItem() == CWTFCItems.ryeWholeCWTFC)
					foodtype = CWTFCItems.ryeGrainCWTFC;
				else if(placedGrain.getItem() == CWTFCItems.wheatWholeCWTFC)
					foodtype = CWTFCItems.wheatGrainCWTFC;
					
				ItemStack food = ItemTFCFoodTransform.createTag(new ItemStack(foodtype), Food.getWeight(placedGrain), Food.getDecay(placedGrain));
				Food.setSweetMod(food, Food.getSweetMod(placedGrain));
				Food.setSourMod(food, Food.getSourMod(placedGrain));
				Food.setSaltyMod(food, Food.getSaltyMod(placedGrain));
				Food.setBitterMod(food, Food.getBitterMod(placedGrain));
				Food.setSavoryMod(food, Food.getSavoryMod(placedGrain));
				placedGrain = food;
				
				if(strawCount > 0)
					ejectItem(new ItemStack(TFCItems.straw, strawCount));
				return true;
			}
			return false;
		}
	}
	
	public void ejectItem()
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		if(this.placedGrain != null)
		{
			entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, this.placedGrain);
			entityitem.motionX = (float)rand.nextGaussian() * f3;
			entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.05F;
			entityitem.motionZ = (float)rand.nextGaussian() * f3;
			worldObj.spawnEntityInWorld(entityitem);
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

		if(this.placedGrain != null)
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
		this.placedGrain = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("placedGrain"));
		this.workcounter = nbt.getInteger("WorkCounter");
		this.stage = nbt.getInteger("Stage");
		this.strawCount = nbt.getInteger("StrawCount");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);		
		NBTTagCompound grainNBT = new NBTTagCompound();
        this.placedGrain.writeToNBT(grainNBT);
        nbt.setTag("placedGrain", grainNBT);
        nbt.setInteger("StrawCount", this.strawCount);
        nbt.setInteger("WorkCounter", this.workcounter);
        nbt.setInteger("Stage", this.stage);
	}
	
	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		this.stage = nbt.getInteger("stage");
		this.workcounter = nbt.getInteger("WorkCounter");
		this.strawCount = nbt.getInteger("StrawCount");
		this.placedGrain = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("placedGrain"));
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) 
	{
		this.stage = nbt.getInteger("stage");
		this.workcounter = nbt.getInteger("WorkCounter");
		this.strawCount = nbt.getInteger("StrawCount");
		this.placedGrain = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("placedGrain"));
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt) {
		nbt.setInteger("stage", this.stage);
		nbt.setInteger("StrawCount", this.strawCount);
        nbt.setInteger("WorkCounter", this.workcounter);
		NBTTagCompound grainNBT = new NBTTagCompound();
        this.placedGrain.writeToNBT(grainNBT);
        nbt.setTag("placedGrain", grainNBT);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
		nbt.setInteger("stage", this.stage);
		nbt.setInteger("StrawCount", this.strawCount);
        nbt.setInteger("WorkCounter", this.workcounter);
		NBTTagCompound grainNBT = new NBTTagCompound();
        this.placedGrain.writeToNBT(grainNBT);
        nbt.setTag("placedGrain", grainNBT);
	}
}
