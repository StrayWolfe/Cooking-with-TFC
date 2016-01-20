package com.JAWolfe.cookingwithtfc.entities;

import java.util.ArrayList;
import java.util.List;

import com.JAWolfe.cookingwithtfc.entities.EntityTransformChickenTFC;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformPheasant;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformPigTFC;
import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformCowTFC;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformDeer;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformHorseTFC;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_MobData;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.AI.EntityAITargetNonTamedTFC;
import com.bioxx.tfc.Entities.Mobs.EntityWolfTFC;
import com.bioxx.tfc.api.Entities.IAnimal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;

public class EntityTransformWolfTFC extends EntityWolfTFC
{	
	private float mateSizeModCWTFC;

	public EntityTransformWolfTFC(World par1World) 
	{
		super(par1World);

		mateSizeModCWTFC = 1f;
		
		this.targetChicken = new EntityAITargetNonTamedTFC(this, EntityTransformChickenTFC.class, 200, false);
		this.targetPheasant = new EntityAITargetNonTamedTFC(this, EntityTransformPheasant.class, 200, false);
		this.targetPig = new EntityAITargetNonTamedTFC(this, EntityTransformPigTFC.class, 200, false);
		this.targetCow = new EntityAITargetNonTamedTFC(this, EntityTransformCowTFC.class, 200, false);
		this.targetDeer = new EntityAITargetNonTamedTFC(this, EntityTransformDeer.class, 200, false);
		this.targetHorse = new EntityAITargetNonTamedTFC(this, EntityTransformHorseTFC.class, 200, false);
		
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.WOLF_HEALTH * this.getSizeMod() * this.getStrengthMod());
		this.setHealth(this.getMaxHealth());
	}
	
	public EntityTransformWolfTFC(World par1World, IAnimal mother, List<Float> data)
	{
		super(par1World, mother, data);
		float growthMod = 0.5F * (TFC_MobData.WOLF_HEALTH * this.getSizeMod() * this.getStrengthMod());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(growthMod);
		this.setHealth(this.getMaxHealth());
	}
	
	@Override
	public void mate(IAnimal otherAnimal)
	{
		if (getGender() == GenderEnum.MALE)
		{
			otherAnimal.mate(this);
			setInLove(false);
			resetInLove();
			return;
		}
		setTimeOfConception(TFC_Time.getTotalTicks());
		setPregnant(true);
		resetInLove();
		setInLove(false);
		otherAnimal.setInLove(false);
		mateSizeModCWTFC = otherAnimal.getSizeMod();
	}
	
	@Override
	public boolean canMateWith(IAnimal animal)
	{
		return animal.getGender() != this.getGender() &&this.isAdult() && animal.isAdult() &&
				animal instanceof EntityTransformWolfTFC;
	}
	
	@Override
	public EntityWolf createChild(EntityAgeable entityageable)
	{
		return (EntityWolf) createChildTFC(entityageable);
	}

	@Override
	public EntityAgeable createChildTFC(EntityAgeable eAgeable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(eAgeable.getEntityData().getFloat("MateSize"));
		data.add(eAgeable.getEntityData().getFloat("MateStrength"));
		data.add(eAgeable.getEntityData().getFloat("MateAggro"));
		data.add(eAgeable.getEntityData().getFloat("MateObed"));
		return new EntityTransformWolfTFC(worldObj, this, data);
	}
	
	@Override
	public boolean isFood(ItemStack item)
	{
		return item != null &&
				(item.getItem() == CWTFCItems.beefRawCWTFC ||item.getItem() == CWTFCItems.chickenRawCWTFC || item.getItem() == CWTFCItems.fishRawCWTFC ||
					item.getItem() == CWTFCItems.horseMeatRawCWTFC || item.getItem() == CWTFCItems.muttonRawCWTFC || item.getItem() == CWTFCItems.porkchopRawCWTFC ||
					item.getItem() == CWTFCItems.venisonRawCWTFC);
	}
	
	@Override
	public void onLivingUpdate()
	{
		//Handle Hunger ticking
		if (getHunger() > 168000)
			setHunger(168000);
		if (getHunger() > 0)
			setHunger(getHunger() - 1);

		if (this.getLeashed())
		{
			Entity leashedTo = getLeashedToEntity();
			// Wolves who have been given a bone, are tied to a fence, and are not angry/targeting another animal must sit
			if (leashedTo instanceof EntityLeashKnot && getFamiliarity() >= 5 && !this.isAngry())
			{
				this.aiSit.setSitting(true);
				this.setSitting(true);
				this.isJumping = false;
				// Set everything to null to prevent butt scooching
				this.setPathToEntity((PathEntity) null);
				this.setTarget((Entity) null);
				this.setAttackTarget((EntityLivingBase) null);
			}
			// Animals who are tied to a player, or are tied to a fence and are angry/targeting another animal are allowed to stand up and move about
			else if (leashedTo instanceof EntityPlayer || leashedTo instanceof EntityLeashKnot && this.isAngry())
			{
				this.aiSit.setSitting(false);
				this.setSitting(false);
			}

			if (!hasBeenRoped())
				setWasRoped(true);
		}
		// Unleashed, but still sitting, and angry/targeting another animal
		else if (this.isAngry() && this.isSitting())
		{
			this.aiSit.setSitting(false);
			this.setSitting(false);
		}

		if (super.isInLove())
			setInLove(true);

		if (isAdult())
		{
			setGrowingAge(0);
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.WOLF_HEALTH * this.getSizeMod() * this.getStrengthMod());
		}
		else
		{
			float maxBaseHealth = TFC_MobData.WOLF_HEALTH * this.getSizeMod() * this.getStrengthMod();
			float growthMod = ((TFC_Core.getPercentGrown(this) * 0.5F) + 0.5F) * maxBaseHealth;
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(growthMod);
			setGrowingAge(-1);
		}

		this.handleFamiliarityUpdate();

		if (getHappyTicks() > 0)
			setHappyTicks(getHappyTicks() - 1);

		syncData();

		if (!this.worldObj.isRemote && isPregnant() && TFC_Time.getTotalTicks() >= getTimeOfConception() + getPregnancyRequiredTime())
		{
			int i = rand.nextInt(2) + 1;
			for (int x = 0; x < i; x++)
			{
				ArrayList<Float> data = new ArrayList<Float>();
				data.add(mateSizeModCWTFC);
				EntityTransformWolfTFC baby = (EntityTransformWolfTFC) this.createChildTFC(this);
				baby.setLocationAndAngles(posX , posY, posZ, 0.0F, 0.0F);
				baby.rotationYawHead = baby.rotationYaw;
				baby.renderYawOffset = baby.rotationYaw;
				baby.setAge(TFC_Time.getTotalDays());
				worldObj.spawnEntityInWorld(baby);
			}
			setPregnant(false);
		}

		/**
		 * This Cancels out the changes made to growingAge by EntityAgeable
		 * */
		TFC_Core.preventEntityDataUpdate = true;
		super.onLivingUpdate();
		TFC_Core.preventEntityDataUpdate = false;

		if (getHunger() > 144000 && rand.nextInt(100) == 0 && getHealth() < TFC_Core.getEntityMaxHealth(this) && !isDead)
			this.heal(1);
		else if (getHunger() < 144000 && super.isInLove())
			this.setInLove(false);

		// Owners can leash a dog to themselves to calm it down
		if (this.getLeashed() && this.isAngry() && getLeashedToEntity() == this.getOwner())
		{
			this.setAngry(false);
			this.setPathToEntity((PathEntity) null);
			this.setTarget((Entity) null);
			this.setAttackTarget((EntityLivingBase) null);
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		mateSizeModCWTFC = nbt.getFloat("MateSizeCWTFC");
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setFloat("MateSizeCWTFC", mateSizeModCWTFC);
	}
}
