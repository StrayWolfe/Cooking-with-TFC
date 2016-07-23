package straywolfe.cookingwithtfc.common.handlers;

import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import straywolfe.cookingwithtfc.common.lib.Settings;
import straywolfe.cookingwithtfc.common.lib.ModInfo;

public class ConfigHandler 
{
	public static Configuration config;
	
	public static void init(File configFile)
	{
		if(config == null)
		{
			config = new Configuration(new File(configFile, ModInfo.ModName + ".cfg"));
			loadConfig();
		}
	}
	
	@SubscribeEvent
	public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.modID.equalsIgnoreCase(ModInfo.ModID))
			loadConfig();
	}
	
	private static void loadConfig()
	{			
		Settings.PICKINESS = config.getInt("pickiness", Configuration.CATEGORY_GENERAL, 1, 1, 3,
				"Set the level of how picky players can be with foods: (Picky = 1, Pickier = 2, Pickiest = 3)");
		
		Settings.diminishingReturns = config.getBoolean("DiminishingReturns", Configuration.CATEGORY_GENERAL, true,
				"Toggle whether repeated consumption of food gives less hunger: ");
		
		Settings.hideTFCFoods = config.getBoolean("HideTFCFoods", Configuration.CATEGORY_GENERAL, true, 
				"Hide TFC Foods from the creative menu and NEI: ");
		
		Settings.enableTFCFoodConversion = config.getBoolean("EnableTFCFoodConversion", Configuration.CATEGORY_GENERAL, true, 
				"Enable conversion of TFC food to Cooking with TFC food when picked up: ");

		if(config.hasChanged())
			config.save();
	}
}
