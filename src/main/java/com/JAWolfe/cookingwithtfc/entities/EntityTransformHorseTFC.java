package com.JAWolfe.cookingwithtfc.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.JAWolfe.cookingwithtfc.entities.EntityHorseBredSelectorCWTFC;
import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.AI.EntityAIAvoidEntityTFC;
import com.bioxx.tfc.Entities.Mobs.EntityHorseTFC;
import com.bioxx.tfc.api.Entities.IAnimal;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityTransformHorseTFC extends EntityHorseTFC
{
	private static final IEntitySelector HORSE_BREEDING_SELECTOR_CWTFC = new EntityHorseBredSelectorCWTFC();
	private static final IAttribute HORSE_JUMP_STRENGTH_CWTFC = new RangedAttribute("horse.jumpStrengthCWTFC", 0.7D, 0.0D, 2.0D).setDescription("Jump StrengthCWTFC").setShouldWatch(true);
	
	private float mateSizeModCWTFC;
	private float mateStrengthModCWTFC;
	private float mateAggroModCWTFC;
	private float mateObedModCWTFC;
	private int mateTypeCWTFC;
	private int mateVariantCWTFC;
	private double mateMaxHealthCWTFC = this.calcMaxHealth();
	private double mateJumpStrengthCWTFC = this.calcJumpStrength();
	private double mateMoveSpeedCWTFC = this.calcMoveSpeed();
	
	public EntityTransformHorseTFC(World par1World) 
	{
		super(par1World);		
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityTransformWolfTFC.class, 8f, 1.0F, 1.2F));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityTransformBear.class, 16f, 1.0F, 1.2F));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.wheatGrainCWTFC, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.ryeGrainCWTFC, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.riceGrainCWTFC, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.barleyGrainCWTFC, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.oatGrainCWTFC, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.maizeEarCWTFC, false));
		
		this.getAttributeMap().registerAttribute(HORSE_JUMP_STRENGTH_CWTFC);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1250 * getSizeMod() * getStrengthMod());//MaxHealth
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22499999403953552D * getStrengthMod() * getObedienceMod() / (getSizeMod() * getSizeMod()));
	}
	
	public EntityTransformHorseTFC(World par1World, IAnimal mother, List<Float> data, int type, int variant)
	{
		super(par1World, mother, data, type, variant);
		
		this.getAttributeMap().registerAttribute(HORSE_JUMP_STRENGTH_CWTFC);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1250 * getSizeMod() * getStrengthMod());//MaxHealth
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22499999403953552D * getStrengthMod() * getObedienceMod() / (getSizeMod() * getSizeMod()));
	}
	
	private double calcJumpStrength()
	{
		return 0.4000000059604645D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D;
	}

	private float calcMaxHealth()
	{
		return 1000 + (float)this.rand.nextInt(101) + this.rand.nextInt(151);
	}

	private double calcMoveSpeed()
	{
		return (0.44999998807907104D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D) * 0.25D;
	}
	
	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	@Override
	public boolean canMateWith(EntityAnimal animal)
	{
		if (animal == this)
			return false;
		else if (animal.getClass() != this.getClass())
			return false;
		else
		{
			EntityTransformHorseTFC entityhorse = (EntityTransformHorseTFC) animal;

			if (this.getBreedable() && entityhorse.getBreedable())
			{
				int i = this.getHorseType();
				int j = entityhorse.getHorseType();
				return i == j || i == 0 && j == 1 || i == 1 && j == 0;
			}
			else
				return false;
		}
	}
	
	@Override
	public boolean canMateWith(IAnimal animal)
	{
		return animal.getGender() != this.getGender() &&this.isAdult() && animal.isAdult() &&
				animal instanceof EntityTransformHorseTFC;
	}
	
	private boolean getBreedable()
	{
		return this.riddenByEntity == null && this.ridingEntity == null && this.isTame() && this.isAdultHorse() && 
				!this.func_110222_cv()/*Not a Mule, Zombie or Skeleton*/&& this.getHealth() >= this.getMaxHealth();
	}
	
	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData livingData)
	{
		float horseMaxHealth = getMaxHealth();
		IEntityLivingData data = super.onSpawnWithEgg(livingData);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(horseMaxHealth);
		this.heal(horseMaxHealth);
		return data;
	}
	
	@Override
	public EntityAgeable createChildTFC(EntityAgeable eAgeable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(this.mateSizeModCWTFC);
		data.add(this.mateStrengthModCWTFC);
		data.add(this.mateAggroModCWTFC);
		data.add(this.mateObedModCWTFC);
		
		int momType = this.getHorseType();
		int dadType = this.mateTypeCWTFC;
		int babyType = 0;
		int babyVariant = 0;

		if (momType == dadType)
			babyType = momType;
		else if (momType == 0 && dadType == 1 || momType == 1 && dadType == 0)
			babyType = 2; // Create Mule

		if (babyType == 0)
		{
			int l = this.rand.nextInt(9);

			if (l < 4)
				babyVariant = this.getHorseVariant() & 255;
			else if (l < 8)
				babyVariant = mateVariantCWTFC & 255;
			else
				babyVariant = this.rand.nextInt(7);

			int j1 = this.rand.nextInt(5);

			if (j1 < 4)
				babyVariant |= this.getHorseVariant() & 65280;
			else if (j1 < 8)
				babyVariant |= mateVariantCWTFC & 65280;
			else
				babyVariant |= this.rand.nextInt(5) << 8 & 65280;
		}

		EntityTransformHorseTFC baby = new EntityTransformHorseTFC(worldObj, this, data, babyType, babyVariant);

		// Average Mom + Dad + Random Max Health
		double healthSum = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + mateMaxHealthCWTFC + this.calcMaxHealth();
		baby.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthSum / 3.0D);

		// Average Mom + Dad + Random Jump Strength
		double jumpSum = this.getEntityAttribute(HORSE_JUMP_STRENGTH_CWTFC).getBaseValue() + mateJumpStrengthCWTFC + this.calcJumpStrength();
		baby.getEntityAttribute(HORSE_JUMP_STRENGTH_CWTFC).setBaseValue(jumpSum / 3.0D);

		// Average Mom + Dad + Random Move Speed
		double speedSum = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + mateMoveSpeedCWTFC + this.calcMoveSpeed();
		baby.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speedSum / 3.0D);

		baby.setHealth((float)baby.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue());
		return baby;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected EntityTransformHorseTFC getClosestHorse(Entity entity, double range)
	{
		double maxDistance = Double.MAX_VALUE;
		EntityTransformHorseTFC closestHorse = null;
		List<EntityTransformHorseTFC> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.addCoord(range, range, range), HORSE_BREEDING_SELECTOR_CWTFC);
		Iterator<EntityTransformHorseTFC> iterator = list.iterator();

		while (iterator.hasNext())
		{
			EntityTransformHorseTFC nearbyHorse = iterator.next();
			double distance = nearbyHorse.getDistanceSq(entity.posX, entity.posY, entity.posZ);
			if (distance < maxDistance)
			{
				closestHorse = nearbyHorse;
				maxDistance = distance;
			}
		}

		return closestHorse;
	}
	
	@Override
	public boolean isFood(ItemStack item) 
	{
		return item != null && (item.getItem() == CWTFCItems.wheatGrainCWTFC ||item.getItem() == CWTFCItems.oatGrainCWTFC||item.getItem() == CWTFCItems.riceGrainCWTFC||
				item.getItem() == CWTFCItems.barleyGrainCWTFC||item.getItem() == CWTFCItems.ryeGrainCWTFC||item.getItem() == CWTFCItems.maizeEarCWTFC);
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
		mateStrengthModCWTFC = otherAnimal.getStrengthMod();
		mateAggroModCWTFC = otherAnimal.getAggressionMod();
		mateObedModCWTFC = otherAnimal.getObedienceMod();
		mateTypeCWTFC = ((EntityHorse) otherAnimal).getHorseType();
		mateVariantCWTFC = ((EntityHorse) otherAnimal).getHorseVariant();
		mateMaxHealthCWTFC = ((EntityLivingBase) otherAnimal).getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue();
		mateJumpStrengthCWTFC = ((EntityLivingBase) otherAnimal).getEntityAttribute(HORSE_JUMP_STRENGTH_CWTFC).getBaseValue();
		mateMoveSpeedCWTFC = ((EntityLivingBase) otherAnimal).getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue();
	}
	
	@Override
	public void onLivingUpdate()
	{
		//Handle Hunger ticking
		if (getHunger() > 168000)
			setHunger(168000);
		if (getHunger() > 0)
			setHunger(getHunger() - 1);

		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && rand.nextInt(600) == 0 && !getFamiliarizedToday() && canFamiliarize())
			this.familiarize(((EntityPlayer)this.riddenByEntity));

		syncData();
		if(isAdult())
			setGrowingAge(0);
		else
			setGrowingAge(-1);

		this.handleFamiliarityUpdate();
		if (!this.worldObj.isRemote && isPregnant() && TFC_Time.getTotalTicks() >= getTimeOfConception() + getPregnancyRequiredTime())
		{
			EntityTransformHorseTFC baby = (EntityTransformHorseTFC) createChildTFC(this);
			baby.setLocationAndAngles (posX, posY, posZ, 0.0F, 0.0F);
			baby.rotationYawHead = baby.rotationYaw;
			baby.renderYawOffset = baby.rotationYaw;
			worldObj.spawnEntityInWorld(baby);
			baby.setAge(TFC_Time.getTotalDays());
			setPregnant(false);
		}

		/**
		 * This Cancels out the changes made to growingAge by EntityAgeable
		 * */
		TFC_Core.preventEntityDataUpdate = true;
		super.onLivingUpdate();
		TFC_Core.preventEntityDataUpdate = false;

		if (getHunger() > 144000 && rand.nextInt (100) == 0 && getHealth() < TFC_Core.getEntityMaxHealth(this) && !isDead)
			this.heal(1);
		else if(getHunger() < 144000 && super.isInLove())
			this.setInLove(false);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbttc)
	{
		super.readEntityFromNBT(nbttc);
		NBTTagCompound nbt = nbttc;
		mateSizeModCWTFC = nbt.getFloat("MateSizeCWTFC");
		mateStrengthModCWTFC = nbt.getFloat("MateStrengthCWTFC");
		mateAggroModCWTFC = nbt.getFloat("MateAggroCWTFC");
		mateObedModCWTFC = nbt.getFloat("MateObedCWTFC");
		mateTypeCWTFC = nbt.getInteger("MateTypeCWTFC");
		mateVariantCWTFC = nbt.getInteger("MateVariantCWTFC");
		mateMaxHealthCWTFC = nbt.getDouble("MateHealthCWTFC");
		mateJumpStrengthCWTFC = nbt.getDouble("MateJumpCWTFC");
		mateMoveSpeedCWTFC = nbt.getDouble("MateSpeedCWTFC");
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbttc)
	{
		super.writeEntityToNBT(nbttc);
		nbttc.setFloat("MateSizeCWTFC", mateSizeModCWTFC);
		nbttc.setFloat("MateStrengthCWTFC", mateStrengthModCWTFC);
		nbttc.setFloat("MateAggroCWTFC", mateAggroModCWTFC);
		nbttc.setFloat("MateObedCWTFC", mateObedModCWTFC);
		nbttc.setInteger("MateTypeCWTFC", mateTypeCWTFC);
		nbttc.setInteger("MateVariantCWTFC", mateVariantCWTFC);
		nbttc.setDouble("MateHealthCWTFC", mateMaxHealthCWTFC);
		nbttc.setDouble("MateJumpCWTFC", mateJumpStrengthCWTFC);
		nbttc.setDouble("MateSpeedCWTFC", mateMoveSpeedCWTFC);
	}
}
