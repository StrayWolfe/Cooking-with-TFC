package straywolfe.cookingwithtfc.common.handlers;

import com.bioxx.tfc.TerraFirmaCraft;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import straywolfe.cookingwithtfc.common.core.CWTFC_Core;
import straywolfe.cookingwithtfc.common.core.FoodRecord;
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
		}
	}
}
