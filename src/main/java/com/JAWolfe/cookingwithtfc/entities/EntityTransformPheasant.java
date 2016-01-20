package com.JAWolfe.cookingwithtfc.entities;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Entities.Mobs.EntityPheasantTFC;
import com.bioxx.tfc.api.Entities.IAnimal;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityTransformPheasant extends EntityPheasantTFC
{

	public EntityTransformPheasant(World par1World) 
	{
		super(par1World);
		
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50 * this.getSizeMod() * this.getStrengthMod());
		this.setHealth(this.getMaxHealth());
	}
	
	public EntityTransformPheasant(World world, double posX, double posY, double posZ, NBTTagCompound genes)
	{
		super(world, posX, posY, posZ, genes);
		
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50 * this.getSizeMod() * this.getStrengthMod());
		this.setHealth(this.getMaxHealth());
	}

	@Override
	public EntityAgeable createChildTFC(EntityAgeable entityageable)
	{
		if (entityageable instanceof IAnimal)
		{
			IAnimal animal = (IAnimal) entityageable;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setFloat("m_size", animal.getSizeMod());
			nbt.setFloat("f_size", animal.getSizeMod());
			return new EntityTransformPheasant(worldObj, posX, posY, posZ, nbt);
		}

		return null;
	}
	
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		if (isAdult())
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50 * this.getSizeMod() * this.getStrengthMod());
		else
		{
			float maxBaseHealth = 50 * this.getSizeMod() * this.getStrengthMod();
			float growthMod = ((TFC_Core.getPercentGrown(this) * 0.5F) + 0.5F) * maxBaseHealth;
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(growthMod);
		}
	}
}
