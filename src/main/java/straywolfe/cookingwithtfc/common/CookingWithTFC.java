package straywolfe.cookingwithtfc.common;

import net.minecraftforge.common.MinecraftForge;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.handlers.*;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.proxy.CommonProxyCWTFC;
import straywolfe.cookingwithtfc.common.registries.CWTFCRecipes;
import straywolfe.cookingwithtfc.common.registries.HeatedItemRecipes;
import straywolfe.cookingwithtfc.common.worldgen.WorldGenCrops;

import com.bioxx.tfc.TerraFirmaCraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ModInfo.ModID, name = ModInfo.ModName, version = ModInfo.ModVersion, dependencies = ModInfo.ModDependencies)
public class CookingWithTFC
{
	@Instance(ModInfo.ModID)
	public static CookingWithTFC instance;
	
	@SidedProxy(serverSide = ModInfo.SERVER_PROXY_CLASS, clientSide = ModInfo.CLIENT_PROXY_CLASS)
	public static CommonProxyCWTFC proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{				
		//Handle Configs
		ConfigHandler.init(event.getModConfigurationDirectory());
		FMLCommonHandler.instance().bus().register(new ConfigHandler());
		
		//Initialize Fluids
		proxy.registerFluids();
		
		//Initialize Items
		CWTFCItems.initialise();
		
		//Initialize Blocks
		CWTFCBlocks.initialise();
		
		//Register Tile Entities
		proxy.registerTileEntities(true);
		
		//Register Farmland Highlighter
		proxy.registerHandlers();
		
		GameRegistry.registerWorldGenerator(new WorldGenCrops(), 9);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{		
		//Register packets
		TerraFirmaCraft.PACKET_PIPELINE.registerPacket(MessageFoodRecord.class);
        
        //Handler Player Info
		MinecraftForge.EVENT_BUS.register(new PlayerHandler());
		
		//Handle Entity Spawns
		MinecraftForge.EVENT_BUS.register(new EntitySpawnHandler());
		
		//Handle Chunk Loading
		MinecraftForge.EVENT_BUS.register(new ChunkHandler());
		
		//Setup Fluids
		proxy.setupFluids();
		
		//Register Crafting Recipes
		CWTFCRecipes.registerRecipes();
		
		//Setup Item Heating
		HeatedItemRecipes.setupHeatedItemRecipes();
		
		//Register Renderers
		proxy.registerRenderInformation();
		
		//Configure WAILA
		proxy.registerWAILA();
	}
}
