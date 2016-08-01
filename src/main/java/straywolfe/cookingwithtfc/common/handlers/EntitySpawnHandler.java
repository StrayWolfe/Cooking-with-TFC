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
import straywolfe.cookingwithtfc.common.entity.*;
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
			else if(event.entity instanceof EntityTransformBear)
			{
				EntityBear newBear = new EntityBear(event.world);				
				setupEntitySpawn(newBear, event);
			}
			else if(event.entity instanceof EntityTransformChicken)
			{
				if(event.entity instanceof EntityTransformPheasant)
				{
					EntityPheasantTFC newPheasant = new EntityPheasantTFC(event.world);				
					setupEntitySpawn(newPheasant, event);
				}
				else
				{
					EntityChickenTFC newChicken = new EntityChickenTFC(event.world);				
					setupEntitySpawn(newChicken, event);
				}
			}
			else if(event.entity instanceof EntityTransformCowTFC)
			{
				EntityCowTFC newCow = new EntityCowTFC(event.world);				
				setupEntitySpawn(newCow, event);
			}
			else if(event.entity instanceof EntityTransformDeer)
			{
				EntityDeer newDeer = new EntityDeer(event.world);				
				setupEntitySpawn(newDeer, event);
			}
			else if(event.entity instanceof EntityTransformHorseTFC)
			{
				EntityHorseTFC newHorse = new EntityHorseTFC(event.world);
				setupEntitySpawn(newHorse, event);
			}
			else if(event.entity instanceof EntityTransformPigTFC)
			{
				EntityPigTFC newPig = new EntityPigTFC(event.world);				
				setupEntitySpawn(newPig, event);
			}
			else if(event.entity instanceof EntityTransformSheepTFC)
			{
				EntitySheepTFC newSheep = new EntitySheepTFC(event.world);
				setupEntitySpawn(newSheep, event);
			}
			else if(event.entity instanceof EntityTransformWolfTFC)
			{					
				EntityWolfTFC newWolf = new EntityWolfTFC(event.world);
				setupEntitySpawn(newWolf, event);
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
