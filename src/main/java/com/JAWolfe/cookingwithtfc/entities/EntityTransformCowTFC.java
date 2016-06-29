package com.JAWolfe.cookingwithtfc.entities;

import java.util.ArrayList;
import java.util.List;

import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.Mobs.EntityCowTFC;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemCustomNameTag;
import com.bioxx.tfc.Items.Tools.ItemCustomBucketMilk;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Entities.IAnimal;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class EntityTransformCowTFC extends EntityCowTFC
{
	private boolean flagHealth;
	private static final int FAMILIARITY_CAP = 35;

	public EntityTransformCowTFC(World par1World) 
	{
		super(par1World);
		setupAI();
	}
	
	public EntityTransformCowTFC(World par1World, IAnimal mother, List<Float> data)
	{
		super(par1World, mother, data);
		setupAI();
	}
	
	private void setupAI()
	{
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.wheatGrainCWTFC, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.ryeGrainCWTFC, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.riceGrainCWTFC, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.barleyGrainCWTFC, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.oatGrainCWTFC, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, CWTFCItems.maizeEarCWTFC, false));
		this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityTransformWolfTFC.class, 8f, 0.7F, 1.0F));
	}
	
	public void setHealthFlag(boolean flag)
	{
		this.flagHealth = flag;
	}
	
	@Override
	public boolean canMateWith(IAnimal animal)
	{
		return animal.getGender() != this.getGender() &&this.isAdult() && animal.isAdult() &&
				animal instanceof EntityTransformCowTFC;
	}

	@Override
	public EntityAgeable createChildTFC(EntityAgeable eAgeable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(eAgeable.getEntityData().getFloat("MateSize"));
		data.add(eAgeable.getEntityData().getFloat("MateStrength"));
		data.add(eAgeable.getEntityData().getFloat("MateAggro"));
		data.add(eAgeable.getEntityData().getFloat("MateObed"));
		return new EntityTransformCowTFC(worldObj, this, data);
	}
	
	@Override
	public boolean isFood(ItemStack item)
	{
		return item != null && (item.getItem() == CWTFCItems.wheatGrainCWTFC ||item.getItem() == CWTFCItems.oatGrainCWTFC || item.getItem() == CWTFCItems.riceGrainCWTFC ||
								item.getItem() == CWTFCItems.barleyGrainCWTFC || item.getItem() == CWTFCItems.ryeGrainCWTFC || item.getItem() == CWTFCItems.maizeEarCWTFC);
	}
	
	@Override
	public void onLivingUpdate()
	{
		//Handle Hunger ticking
		if (getHunger() > 168000)
		{
			setHunger(168000);
		}
		if (getHunger() > 0)
		{
			setHunger(getHunger() - 1);
		}

		if (super.isInLove())
		{
			super.resetInLove();
			setInLove(true);
		}

		this.handleFamiliarityUpdate();

		if (getGender() == GenderEnum.FEMALE && isAdult() && getHasMilkTime() < TFC_Time.getTotalTicks() && this.checkFamiliarity(InteractionEnum.MILK, null))
			setCanMilk(true);
		else
			setCanMilk(false);

		syncData();
		
		if (isAdult())
		{
			setGrowingAge(0);
			if(flagHealth)
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500 * this.getSizeMod() * this.getStrengthMod());
				this.setHealth(this.getMaxHealth());
				flagHealth = false;
			}
		}
		else
		{
			float maxBaseHealth = 500 * this.getSizeMod() * this.getStrengthMod();
			float growthMod = ((TFC_Core.getPercentGrown(this) * 0.5F) + 0.5F) * maxBaseHealth;
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(growthMod);
			setGrowingAge(-1);
		}
		
		if (!this.worldObj.isRemote && isPregnant() && TFC_Time.getTotalTicks() >= getTimeOfConception() + getPregnancyRequiredTime())
		{
			EntityTransformCowTFC baby = (EntityTransformCowTFC) createChildTFC(this);
			baby.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
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

		if (getHunger() > 144000 && rand.nextInt(100) == 0 && getHealth() < TFC_Core.getEntityMaxHealth(this) && !isDead)
		{
			this.heal(1);
		}
		else if (getHunger() < 144000 && super.isInLove())
		{
			this.setInLove(false);
		}
	}
	
	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer player)
	{
		if(!worldObj.isRemote)
		{
			if (player.isSneaking() && !getFamiliarizedToday() && canFamiliarize())
			{
				this.familiarize(player);
				return true;
			}

			if (getGender() == GenderEnum.FEMALE && isPregnant())
			{
				TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.pregnant"));
			}

			if (getGender() == GenderEnum.FEMALE && isAdult() && getHasMilkTime() < TFC_Time.getTotalTicks() && this.checkFamiliarity(InteractionEnum.MILK, player))
			{
				ItemStack heldItem = player.inventory.getCurrentItem();
				if (heldItem != null && heldItem.getItem() == TFCItems.woodenBucketEmpty)
				{
					if (!getFamiliarizedToday() && getFamiliarity() <= FAMILIARITY_CAP)
					{
						setFamiliarizedToday(true);
						this.getLookHelper().setLookPositionWithEntity(player, 0, 0);
						this.playLivingSound();
					}

					ItemStack milkBucket = new ItemStack(CWTFCItems.woodenBucketMilkCWTFC);
					ItemCustomBucketMilk.createTag(milkBucket, 20f);
					player.inventory.setInventorySlotContents(player.inventory.currentItem, milkBucket);
					setHasMilkTime(TFC_Time.getTotalTicks() + TFC_Time.DAY_LENGTH);//Can be milked once every day
					return true;
				}
			}
		}

		ItemStack itemstack = player.inventory.getCurrentItem();
		if (itemstack != null && this.isBreedingItemTFC(itemstack) && this.checkFamiliarity(InteractionEnum.BREED, player) && this.getGrowingAge() == 0 && !super.isInLove() &&
			(getFamiliarizedToday() || !canFamiliarize()))
		{
			if (!player.capabilities.isCreativeMode)
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, ((ItemFoodTFC) itemstack.getItem()).onConsumedByEntity(player.getHeldItem(), worldObj, this));
			}
			setHunger(getHunger() + 24000);
			this.func_146082_f(player);
			return true;
		}
		else if(itemstack != null && itemstack.getItem() instanceof ItemCustomNameTag && itemstack.hasTagCompound() && itemstack.stackTagCompound.hasKey("ItemName"))
		{
			if(this.trySetName(itemstack.stackTagCompound.getString("ItemName"), player))
				itemstack.stackSize--;

			return true;
		}
		else
			return super.interact(player);
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
