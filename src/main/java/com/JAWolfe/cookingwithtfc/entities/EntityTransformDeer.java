package com.JAWolfe.cookingwithtfc.entities;

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.Mobs.EntityDeer;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Entities.IAnimal;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityTransformDeer extends EntityDeer
{

	public EntityTransformDeer(World par1World) 
	{
		super(par1World);

		this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityTransformWolfTFC.class, 8f, 0.7F, 1.0F));
		this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityTransformBear.class, 16f, 0.7F, 1.0F));
		
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400 * this.getSizeMod() * this.getStrengthMod());
		this.setHealth(this.getMaxHealth());
	}
	
	public EntityTransformDeer(World par1World, IAnimal mother, List<Float> data)
	{
		super(par1World, mother, data);
		
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400 * this.getSizeMod() * this.getStrengthMod());
		this.setHealth(this.getMaxHealth());
	}

	@Override
	public EntityAgeable createChildTFC(EntityAgeable eAgeable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(eAgeable.getEntityData().getFloat("MateSize"));
		data.add(eAgeable.getEntityData().getFloat("MateStrength"));
		data.add(eAgeable.getEntityData().getFloat("MateAggro"));
		data.add(eAgeable.getEntityData().getFloat("MateObed"));
		return new EntityTransformDeer(worldObj, this, data);
	}
	
	@Override
	public void onLivingUpdate()
	{
		if(super.isInLove())
		{
			super.resetInLove();
			setInLove(true);
		}

		if(this.getLeashed()&&!hasBeenRoped())
			setWasRoped(true);
		
		syncData();

		if (isAdult())
		{
			setGrowingAge(0);
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400 * this.getSizeMod() * this.getStrengthMod());
		}
		else
		{
			float maxBaseHealth = 400 * this.getSizeMod() * this.getStrengthMod();
			float growthMod = ((TFC_Core.getPercentGrown(this) * 0.5F) + 0.5F) * maxBaseHealth;
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(growthMod);
			setGrowingAge(-1);
		}
		
		this.handleFamiliarityUpdate();

		if (!this.worldObj.isRemote && isPregnant() && TFC_Time.getTotalTicks() >= getTimeOfConception() + getPregnancyRequiredTime())
		{
			EntityTransformDeer baby = (EntityTransformDeer) createChildTFC(this);
			baby.setLocationAndAngles(posX + (rand.nextFloat() - 0.5F) * 2F, posY, posZ + (rand.nextFloat() - 0.5F) * 2F, 0.0F, 0.0F);
			baby.rotationYawHead = baby.rotationYaw;
			baby.renderYawOffset = baby.rotationYaw;
			worldObj.spawnEntityInWorld(baby);
			baby.setAge(TFC_Time.getTotalDays());
			setPregnant(false);
		}

		if(getAttackedVec() != null)
		{
			Vec3 positionVec = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			if(this.getFearSource() != null && this.getDistanceSqToEntity(this.getFearSource()) > Global.SEALEVEL)
			{
				this.setFearSource(null);
			}
			else if(positionVec.distanceTo(getAttackedVec()) > 16)
			{
				this.setAttackedVec(null);
			}
		}

		if (getHunger() > 144000 && rand.nextInt (100) == 0 && getHealth() < TFC_Core.getEntityMaxHealth(this) && !isDead)
		{
			this.heal(1);
		}
		else if(getHunger() < 144000 && super.isInLove()){
			this.setInLove(false);
		}

		/**
		 * This Cancels out the changes made to growingAge by EntityAgeable
		 * */
		TFC_Core.preventEntityDataUpdate = true;
		super.onLivingUpdate();
		TFC_Core.preventEntityDataUpdate = false;
	}
}
