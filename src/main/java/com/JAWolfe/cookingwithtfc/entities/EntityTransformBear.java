package com.JAWolfe.cookingwithtfc.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_MobData;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.AI.EntityAITargetNonTamedTFC;
import com.bioxx.tfc.Entities.Mobs.EntityBear;
import com.bioxx.tfc.api.Entities.IAnimal;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityTransformBear extends EntityBear 
{
	private boolean flagHealth;
	private boolean isWet;
	private Random rand;

	public EntityTransformBear(World par1World) 
	{
		super(par1World);	
		setupAI();
	}
	
	public EntityTransformBear(World par1World, IAnimal mother, List<Float> data)
	{
		super(par1World, mother, data);
		setupAI();
	}
	
	private void setupAI()
	{
		this.targetSheep = new EntityAITargetNonTamedTFC(this, EntityTransformSheepTFC.class, 200, false);
		this.targetDeer = new EntityAITargetNonTamedTFC(this, EntityTransformDeer.class, 200, false);
		this.targetPig = new EntityAITargetNonTamedTFC(this, EntityTransformPigTFC.class, 200, false);
		this.targetHorse = new EntityAITargetNonTamedTFC(this, EntityTransformHorseTFC.class, 200, false);
	}
	
	public void setHealthFlag(boolean flag)
	{
		this.flagHealth = flag;
	}
	
	@Override
	public boolean canMateWith (EntityAnimal par1EntityAnimal)
	{
		if (par1EntityAnimal == this)
			return false;
		if (!(par1EntityAnimal instanceof EntityTransformBear))
			return false;
		EntityTransformBear entitybear = (EntityTransformBear) par1EntityAnimal;
		return getInLove () && entitybear.getInLove ();
	}

	@Override
	public boolean canMateWith(IAnimal animal) 
	{
		return animal.getGender() != this.getGender() &&this.isAdult() && animal.isAdult() &&
				animal instanceof EntityTransformBear;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) 
	{
		return createChildTFC(entityageable);
	}

	@Override
	public EntityAgeable createChildTFC(EntityAgeable eAgeable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(eAgeable.getEntityData().getFloat("MateSize"));
		data.add(eAgeable.getEntityData().getFloat("MateStrength"));
		data.add(eAgeable.getEntityData().getFloat("MateAggro"));
		data.add(eAgeable.getEntityData().getFloat("MateObed"));
		return new EntityTransformBear(worldObj, this, data);
	}
	
	@Override
	public boolean isFood(ItemStack item) {
		return  item != null && item.getItem().equals(CWTFCItems.fishRawCWTFC);
	}
	
	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		TFC_Core.preventEntityDataUpdate = true;
		super.onLivingUpdate();
		TFC_Core.preventEntityDataUpdate = false;

		if (!worldObj.isRemote)
		{
			if (!isWet && !hasPath() && onGround)
			{
				isWet = true;
				worldObj.setEntityState(this, (byte) 8);
			}
			
			if(flagHealth)
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.BEAR_HEALTH * this.getSizeMod() * this.getStrengthMod());
				this.setHealth(this.getMaxHealth());
				flagHealth = false;
			}

			if (isPregnant() && TFC_Time.getTotalTicks() >= getTimeOfConception() + getPregnancyRequiredTime())
			{
				int i = rand.nextInt(3) + 1;
				for (int x = 0; x < i; x++)
				{
					EntityTransformBear baby = (EntityTransformBear) createChildTFC(this);
					worldObj.spawnEntityInWorld(baby);
				}
				setPregnant(false);
			}
		}

		if (this.getLeashed() && !isWasRoped())
			setWasRoped(true);

		this.handleFamiliarityUpdate();
		this.syncData();
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		flagHealth = nbt.getBoolean("flagHealth");
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("flagHealth", flagHealth);
	}
}
