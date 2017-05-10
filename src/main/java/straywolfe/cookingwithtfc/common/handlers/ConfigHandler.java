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
		Settings.pickiness = config.getInt("pickiness", Configuration.CATEGORY_GENERAL, 1, 1, 3,
				"Set the level of how picky players can be with foods: (Picky = 1, Pickier = 2, Pickiest = 3)");
		
		Settings.bowlBreakFreq = config.getInt("bowlBreakFreq", Configuration.CATEGORY_GENERAL, 50, -1, 1000,
				"Set the rate at which bowls break after eating (0 causes it to always break, -1 disables bowl breaking: ");
		
		Settings.diminishingReturns = config.getBoolean("DiminishingReturns", Configuration.CATEGORY_GENERAL, false,
				"Toggle whether repeated consumption of food gives less hunger: ");
		
		Settings.disablePumpkins = config.getBoolean("disablePumpkins", "Crops", false, "Disable CWTFC Pumpkin seeds from dropping from pumpkins: ");
		
		Settings.disableMelons = config.getBoolean("disableMelons", "Crops", false, "Disable Watermelon world generation: ");
		
		Settings.disableMushrooms = config.getBoolean("disableMushrooms", "Crops", false, "Disable mushroom spore crafting recipes: ");
		
		Settings.disableCelery = config.getBoolean("disableCelery", "Crops", false, "Disable Celery world generation: ");
		
		Settings.disableLettuce = config.getBoolean("disableLettuce", "Crops", false, "Disable Lettuce world generation: ");
		
		Settings.disablePeanut = config.getBoolean("disablePeanut", "Crops", false, "Disable Peanut world generation: ");
		
		Settings.tfcJackOLantern = config.getBoolean("tfcJackOLantern", "Crops", false, "Enable TFC Jack O' Lantern drop from carving a pumpkin: ");
		
		Settings.vanillaMelons = config.getBoolean("vanillaMelons", "Crops", false, "Enable vanilla melon drops from watermelon crops: ");
		
		Settings.vanillaMushrooms = config.getBoolean("vanillaMushrooms", "Crops", false, "Enable vanilla mushroom drops from mushroom crops: ");
		
		Settings.lanternLifespan = config.getInt("JackOLantern lifespan", "Crops", 72, 0, 1000,
				"Set the length of time in hours the lantern will remain lit. Setting the lifespan to 0 means it will never go out: ");
		
		Settings.vanillaMelonRecipe = config.getBoolean("vanillaMelonRecipe", "Crops", false, "Enable recipe to convert watermelons to vanilla melons: ");
		
		Settings.tfcPumpkinRecipe = config.getBoolean("tfcPumpkinRecipe", "Crops", false, "Enable recipe to convert CWTFC pumpkin to TFC pumpkin: ");

		if(config.hasChanged())
			config.save();
	}
}
