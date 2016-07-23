package straywolfe.cookingwithtfc.common.handlers;

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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import straywolfe.cookingwithtfc.common.core.CWTFC_Core;
import straywolfe.cookingwithtfc.common.core.FoodRecord;
import straywolfe.cookingwithtfc.common.entity.EntityTransformBear;
import straywolfe.cookingwithtfc.common.entity.EntityTransformChickenTFC;
import straywolfe.cookingwithtfc.common.entity.EntityTransformCowTFC;
import straywolfe.cookingwithtfc.common.entity.EntityTransformDeer;
import straywolfe.cookingwithtfc.common.entity.EntityTransformHorseTFC;
import straywolfe.cookingwithtfc.common.entity.EntityTransformPheasant;
import straywolfe.cookingwithtfc.common.entity.EntityTransformPigTFC;
import straywolfe.cookingwithtfc.common.entity.EntityTransformSheepTFC;
import straywolfe.cookingwithtfc.common.entity.EntityTransformWolfTFC;
import straywolfe.cookingwithtfc.common.lib.Settings;

public class EntitySpawnHandler 
{
	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer && event.entity.getEntityData().hasKey("estPlayer"))
		{
			if(Settings.diminishingReturns)
			{
				EntityPlayer player = (EntityPlayer)event.entity;
				FoodRecord fr = CWTFC_Core.getPlayerFoodRecord(player);
				CWTFC_Core.setPlayerFoodRecord(player, fr);
				
				if(event.entity instanceof EntityPlayerMP)
					TerraFirmaCraft.PACKET_PIPELINE.sendTo(new MessageFoodRecord(player, fr), (EntityPlayerMP) event.entity);
			}
		}
		else if(!event.world.isRemote)
		{
			if(event.entity instanceof EntityPlayer && !event.entity.getEntityData().hasKey("estPlayer"))
			{
				if(Settings.diminishingReturns)
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
			else if(event.entity instanceof EntityBear)
			{
				if(event.entity instanceof EntityTransformBear)
					return;				
				else
				{
					EntityTransformBear newBear = new EntityTransformBear(event.world);				
					setupEntitySpawn(newBear, event);
					newBear.setHealthFlag(true);
				}
			}
			else if(event.entity instanceof EntityChickenTFC)
			{
				if(event.entity instanceof EntityTransformChickenTFC)
					return;
				else if(event.entity instanceof EntityPheasantTFC)
				{
					if(event.entity instanceof EntityTransformPheasant)
						return;				
					else
					{
						EntityTransformPheasant newPheasant = new EntityTransformPheasant(event.world);				
						setupEntitySpawn(newPheasant, event);
						newPheasant.setHealthFlag(true);
					}
				}
				else
				{
					EntityTransformChickenTFC newChicken = new EntityTransformChickenTFC(event.world);				
					setupEntitySpawn(newChicken, event);
					newChicken.setHealthFlag(true);
				}
			}
			else if(event.entity instanceof EntityCowTFC)
			{
				if(event.entity instanceof EntityTransformCowTFC)
					return;				
				else
				{
					EntityTransformCowTFC newCow = new EntityTransformCowTFC(event.world);				
					setupEntitySpawn(newCow, event);
					newCow.setHealthFlag(true);
				}
			}
			else if(event.entity instanceof EntityDeer)
			{
				if(event.entity instanceof EntityTransformDeer)
					return;				
				else
				{
					EntityTransformDeer newDeer = new EntityTransformDeer(event.world);				
					setupEntitySpawn(newDeer, event);
					newDeer.setHealthFlag(true);
				}
			}
			else if(event.entity instanceof EntityHorseTFC)
			{
				if(event.entity instanceof EntityTransformHorseTFC)
					return;				
				else
				{
					EntityTransformHorseTFC newHorse = new EntityTransformHorseTFC(event.world);
					setupEntitySpawn(newHorse, event);
					newHorse.setHealthFlag(true);
				}
			}
			else if(event.entity instanceof EntityPigTFC)
			{
				if(event.entity instanceof EntityTransformPigTFC)
					return;				
				else
				{
					EntityTransformPigTFC newPig = new EntityTransformPigTFC(event.world);				
					setupEntitySpawn(newPig, event);
					newPig.setHealthFlag(true);
				}
			}
			else if(event.entity instanceof EntitySheepTFC)
			{
				if(event.entity instanceof EntityTransformSheepTFC)
					return;				
				else
				{
					EntityTransformSheepTFC newSheep = new EntityTransformSheepTFC(event.world);
					setupEntitySpawn(newSheep, event);
					newSheep.setHealthFlag(true);
				}
			}
			else if(event.entity instanceof EntityWolfTFC)
			{
				if(event.entity instanceof EntityTransformWolfTFC)
					return;				
				else
				{					
					EntityTransformWolfTFC newWolf = new EntityTransformWolfTFC(event.world);
					setupEntitySpawn(newWolf, event);
					newWolf.setHealthFlag(true);
				}
			}
		}
	}
	
	private void setupEntitySpawn(Entity entity, EntityJoinWorldEvent event)
	{
		entity.copyDataFrom(event.entity, true);
		entity.copyLocationAndAnglesFrom(event.entity);
		event.world.removeEntity(event.entity);				
		event.world.spawnEntityInWorld(entity);
	}
}
