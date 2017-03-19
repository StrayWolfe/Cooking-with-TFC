package straywolfe.cookingwithtfc.common;

import net.minecraftforge.common.MinecraftForge;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.handlers.*;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.registries.CWTFCRegistries;
import straywolfe.cookingwithtfc.common.registries.HeatedItemRecipes;

import com.bioxx.tfc.TerraFirmaCraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

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
		CWTFCItems.setup();
		
		//Initialize Blocks
		CWTFCBlocks.setup();
		
		//Register Tile Entities
		proxy.registerTileEntities(true);
		
		//Setup Fluids
		proxy.setupFluids();
		
		//Register Client Handlers
		proxy.registerClientHandlers();
		
		//Register World Generators
		proxy.registerWorldGen();
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
		
		//Handle Crafting
		FMLCommonHandler.instance().bus().register(new CraftingMatrixHandler());
		
		//Register Crafting Recipes
		CWTFCRegistries.setupRegistries();
		
		//Setup Item Heating
		HeatedItemRecipes.setupHeatedItemRecipes();
		
		//Register Renderers
		proxy.registerRenderInformation();
		
		//Configure WAILA
		proxy.registerWAILA();
	}
}
