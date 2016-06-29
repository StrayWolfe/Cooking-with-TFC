package com.JAWolfe.cookingwithtfc.entities;


import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.Mobs.EntityChickenTFC;
import com.bioxx.tfc.api.Entities.IAnimal;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityTransformChickenTFC extends EntityChickenTFC
{	
	private boolean flagHealth;
	private static final int EGG_TIME = TFC_Time.DAY_LENGTH;
	
	public EntityTransformChickenTFC(World par1World) 
	{
		super(par1World);
		setupAI();
	}
	
	// Chickens hatching from a nestbox
	public EntityTransformChickenTFC(World world, double posX, double posY, double posZ, NBTTagCompound genes)
	{
		super(world, posX, posY, posZ, genes);
		setupAI();
	}

	private void setupAI()
	{
		this.tasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityTransformChickenTFC.class, 6.0F));
		this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityTransformWolfTFC.class, 8.0F, 1.0D, 1.2D));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.tasks.addTask(6, new EntityAIEatGrass(this));
		
		if(getSex()==0)
		{
			this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		}
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.wheatGrainCWTFC, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.ryeGrainCWTFC, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.riceGrainCWTFC, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.barleyGrainCWTFC, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.oatGrainCWTFC, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.maizeEarCWTFC, false));
		this.tasks.addTask(3, new AIFindNest(this, 1.2F));
	}
	
	public void setHealthFlag(boolean flag)
	{
		this.flagHealth = flag;
	}
	
	@Override
	public EntityChicken createChild(EntityAgeable entityAgeable)
	{
		return (EntityChicken) createChildTFC(entityAgeable);
	}

	// This should only be called when spawning baby chickens with a spawn egg, so both size values come from the animal clicked on.
	@Override
	public EntityAgeable createChildTFC(EntityAgeable entityageable)
	{
		if (entityageable instanceof IAnimal)
		{
			IAnimal animal = (IAnimal) entityageable;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setFloat("m_size", animal.getSizeMod());
			nbt.setFloat("f_size", animal.getSizeMod());
			return new EntityTransformChickenTFC(worldObj, posX, posY, posZ, nbt);
		}

		return null;
	}
	
	@Override
	public boolean isFood(ItemStack item) {
		return item != null && (item.getItem() == CWTFCItems.wheatGrainCWTFC || item.getItem() == CWTFCItems.oatGrainCWTFC || item.getItem() == CWTFCItems.riceGrainCWTFC ||
				item.getItem() == CWTFCItems.barleyGrainCWTFC || item.getItem() == CWTFCItems.ryeGrainCWTFC || item.getItem() == CWTFCItems.maizeEarCWTFC);
	}
	
	@Override
	public ItemStack getEggs()
	{
		if(TFC_Time.getTotalTicks() >= this.getNextEgg())
		{
			this.setNextEgg(TFC_Time.getTotalTicks() + EGG_TIME);
			return new ItemStack(CWTFCItems.eggCWTFC, 1);
		}
		return null;
	}
	
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		if (isAdult())
		{
			setGrowingAge(0);
			
			if(flagHealth)
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50 * this.getSizeMod() * this.getStrengthMod());
				this.setHealth(this.getMaxHealth());
				flagHealth = false;
			}
		}
		else
		{
			float maxBaseHealth = 50 * this.getSizeMod() * this.getStrengthMod();
			float growthMod = ((TFC_Core.getPercentGrown(this) * 0.5F) + 0.5F) * maxBaseHealth;
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(growthMod);
			setGrowingAge(-1);
		}
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
