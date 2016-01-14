package com.JAWolfe.cookingwithtfc.handlers;

import com.JAWolfe.cookingwithtfc.core.CWTFC_Core;
import com.JAWolfe.cookingwithtfc.core.FoodRecord;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformBear;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformChickenTFC;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformCowTFC;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformDeer;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformHorseTFC;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformPheasant;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformPigTFC;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformSheepTFC;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformWolfTFC;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Entities.Mobs.EntityBear;
import com.bioxx.tfc.Entities.Mobs.EntityChickenTFC;
import com.bioxx.tfc.Entities.Mobs.EntityCowTFC;
import com.bioxx.tfc.Entities.Mobs.EntityDeer;
import com.bioxx.tfc.Entities.Mobs.EntityHorseTFC;
import com.bioxx.tfc.Entities.Mobs.EntityPheasantTFC;
import com.bioxx.tfc.Entities.Mobs.EntityPigTFC;
import com.bioxx.tfc.Entities.Mobs.EntitySheepTFC;
import com.bioxx.tfc.Entities.Mobs.EntityWolfTFC;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EntitySpawnHandler 
{
	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer && event.entity.getEntityData().hasKey("estPlayer"))
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			FoodRecord fr = CWTFC_Core.getPlayerFoodRecord(player);
			CWTFC_Core.setPlayerFoodRecord(player, fr);
			
			if(event.entity instanceof EntityPlayerMP)
				TerraFirmaCraft.PACKET_PIPELINE.sendTo(new MessageFoodRecord(player, fr), (EntityPlayerMP) event.entity);
		}
		else if(event.entity instanceof EntityPlayer && !event.entity.getEntityData().hasKey("estPlayer"))
		{
			if(!event.world.isRemote)
			{
				EntityPlayer player = (EntityPlayer)event.entity;
				int randSize = event.world.rand.nextInt(15) + 5;
				FoodRecord fr = new FoodRecord(player, randSize);
				CWTFC_Core.setPlayerFoodRecord(player, fr);
				event.entity.getEntityData().setBoolean("estPlayer", true);
				
				if(event.entity instanceof EntityPlayerMP)
					TerraFirmaCraft.PACKET_PIPELINE.sendTo(new MessageFoodRecord(player, fr), (EntityPlayerMP) event.entity);
			}
		}
		else if(event.entity instanceof EntityBear && !event.world.isRemote)
		{
			if(event.entity instanceof EntityTransformBear)
				return;				
			else
			{
				EntityTransformBear newBear = new EntityTransformBear(event.world);				
				newBear.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.rotationYaw, event.entity.prevRotationPitch);
				event.world.spawnEntityInWorld(newBear);
				event.setCanceled(true);
			}
		}
		else if(event.entity instanceof EntityChickenTFC && !event.world.isRemote)
		{
			if(event.entity instanceof EntityTransformChickenTFC)
				return;				
			else
			{
				EntityTransformChickenTFC newChicken = new EntityTransformChickenTFC(event.world);				
				newChicken.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.rotationYaw, event.entity.prevRotationPitch);
				event.world.spawnEntityInWorld(newChicken);
				event.setCanceled(true);
			}
		}
		else if(event.entity instanceof EntityCowTFC && !event.world.isRemote)
		{
			if(event.entity instanceof EntityTransformCowTFC)
				return;				
			else
			{
				EntityTransformCowTFC newCow = new EntityTransformCowTFC(event.world);				
				newCow.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.rotationYaw, event.entity.prevRotationPitch);
				event.world.spawnEntityInWorld(newCow);
				event.setCanceled(true);
			}
		}
		else if(event.entity instanceof EntityDeer && !event.world.isRemote)
		{
			if(event.entity instanceof EntityTransformDeer)
				return;				
			else
			{
				EntityTransformDeer newDeer = new EntityTransformDeer(event.world);				
				newDeer.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.rotationYaw, event.entity.prevRotationPitch);
				event.world.spawnEntityInWorld(newDeer);
				event.setCanceled(true);
			}
		}
		else if(event.entity instanceof EntityHorseTFC && !event.world.isRemote)
		{
			if(event.entity instanceof EntityTransformHorseTFC)
				return;				
			else
			{
				IEntityLivingData entitylivingdata = null;
				EntityTransformHorseTFC newHorse = new EntityTransformHorseTFC(event.world);
				newHorse.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.rotationYaw, event.entity.prevRotationPitch);
				entitylivingdata = newHorse.onSpawnWithEgg(entitylivingdata);
				event.world.spawnEntityInWorld(newHorse);
				event.setCanceled(true);
			}
		}
		else if(event.entity instanceof EntityPheasantTFC && !event.world.isRemote)
		{
			if(event.entity instanceof EntityTransformPheasant)
				return;				
			else
			{
				EntityTransformPheasant newPheasant = new EntityTransformPheasant(event.world);				
				newPheasant.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.rotationYaw, event.entity.prevRotationPitch);
				event.world.spawnEntityInWorld(newPheasant);
				event.setCanceled(true);
			}
		}
		else if(event.entity instanceof EntityPigTFC && !event.world.isRemote)
		{
			if(event.entity instanceof EntityTransformPigTFC)
				return;				
			else
			{
				EntityTransformPigTFC newPig = new EntityTransformPigTFC(event.world);				
				newPig.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.rotationYaw, event.entity.prevRotationPitch);
				event.world.spawnEntityInWorld(newPig);
				event.setCanceled(true);
			}
		}
		else if(event.entity instanceof EntitySheepTFC && !event.world.isRemote)
		{
			if(event.entity instanceof EntityTransformSheepTFC)
				return;				
			else
			{
				IEntityLivingData entitylivingdata = null;
				EntityTransformSheepTFC newSheep = new EntityTransformSheepTFC(event.world);				
				newSheep.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.rotationYaw, event.entity.prevRotationPitch);
				entitylivingdata = newSheep.onSpawnWithEgg(entitylivingdata);
				event.world.spawnEntityInWorld(newSheep);
				event.setCanceled(true);
			}
		}
		else if(event.entity instanceof EntityWolfTFC && !event.world.isRemote)
		{
			if(event.entity instanceof EntityTransformWolfTFC)
				return;				
			else
			{
				EntityTransformWolfTFC newWolf = new EntityTransformWolfTFC(event.world);
				newWolf.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.rotationYaw, event.entity.prevRotationPitch);
				event.world.spawnEntityInWorld(newWolf);
				event.setCanceled(true);
			}
		}
	}
}
