package com.JAWolfe.cookingwithtfc.handlers;

import java.io.File;

import com.JAWolfe.cookingwithtfc.references.ConstantsCWTFC;
import com.JAWolfe.cookingwithtfc.references.DetailsCWTFC;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler 
{
	public static Configuration config;
	
	public static void init(File configFile)
	{
		if(config == null)
		{
			config = new Configuration(configFile);
			loadConfig();
		}
	}
	
	@SubscribeEvent
	public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.modID.equalsIgnoreCase(DetailsCWTFC.ModID))
		{
			loadConfig();
		}
	}
	
	private static void loadConfig()
	{			
		ConstantsCWTFC.PICKINESS = config.getInt("pickiness", Configuration.CATEGORY_GENERAL, 1, 1, 3,
				"Set the level of how picky players can be with foods: (Picky = 1, Pickier = 2, Pickiest = 3)");

		if(config.hasChanged())
		{
			config.save();
		}
	}
}
