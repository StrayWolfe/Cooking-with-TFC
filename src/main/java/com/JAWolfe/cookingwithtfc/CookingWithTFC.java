package com.JAWolfe.cookingwithtfc;

import net.minecraftforge.common.MinecraftForge;

import com.JAWolfe.cookingwithtfc.crafting.Recipes.CWTFCRecipes;
import com.JAWolfe.cookingwithtfc.crafting.Recipes.HeatedItems;
import com.JAWolfe.cookingwithtfc.handlers.*;
import com.JAWolfe.cookingwithtfc.handlers.messages.*;
import com.JAWolfe.cookingwithtfc.init.CWTFCBlocks;
import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.JAWolfe.cookingwithtfc.proxy.IProxyCWTFC;
import com.JAWolfe.cookingwithtfc.references.DetailsCWTFC;
import com.bioxx.tfc.TerraFirmaCraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = DetailsCWTFC.ModID, name = DetailsCWTFC.ModName, version = DetailsCWTFC.ModVersion, dependencies = DetailsCWTFC.ModDependencies)
public class CookingWithTFC
{
	@Instance(DetailsCWTFC.ModID)
	public static CookingWithTFC instance;

	@SidedProxy(clientSide = DetailsCWTFC.CLIENT_PROXY_CLASS, serverSide = DetailsCWTFC.SERVER_PROXY_CLASS)
	public static IProxyCWTFC proxy;
	
	@EventHandler
	public void preInitialize(FMLPreInitializationEvent event)
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
		proxy.registerTileEntities();
	}

	@EventHandler
	public void initialize(FMLInitializationEvent event)
	{		
		//Register packets
		TerraFirmaCraft.PACKET_PIPELINE.registerPacket(MessageFoodRecord.class);
		TerraFirmaCraft.PACKET_PIPELINE.registerPacket(CreateRecipePacket.class);
		
		//Setup Fluids
		proxy.setupFluids();
		
		// Register the GUI Handler
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());
        
        //Handler Player Info
		MinecraftForge.EVENT_BUS.register(new PlayerHandler());
		
		//Handle Entity Spawns
		MinecraftForge.EVENT_BUS.register(new EntitySpawnHandler());
		
		//Register Crafting Recipes
		CWTFCRecipes.registerRecipes();
		
		//Setup Item Heating
		HeatedItems.setupItemHeat();
		
		//Register Renderers
		proxy.registerRenderInformation();
		
		//Configure WAILA
		proxy.registerWAILA();
	}
}
