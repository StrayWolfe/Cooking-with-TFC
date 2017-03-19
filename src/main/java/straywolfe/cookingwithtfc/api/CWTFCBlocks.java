package straywolfe.cookingwithtfc.api;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import straywolfe.cookingwithtfc.common.block.*;
import straywolfe.cookingwithtfc.common.item.itemblock.*;

public class CWTFCBlocks 
{
	public static Block GrainsBlock;
	public static Block mixingBowl;
	public static Block hopperCWTFC;
	public static Block cookingPot;
	public static Block meatCWTFC;
	public static Block sandwichCWTFC;
	public static Block bowlCWTFC;
	public static Block clayOven;
	public static Block tableStorage;
	
	public static Block prepTableN;
	public static Block prepTable2N;
	public static Block prepTableS;
	public static Block prepTable2S;
	public static Block prepTableE;
	public static Block prepTable2E;
	public static Block prepTableW;
	public static Block prepTable2W;
	
	public static Block customGourd;
	public static Block customCrop;
	public static Block customLeaves;
	public static Block naturalLog;
	public static Block customSapling;
	public static Block woodVert;
	public static Block woodHorizNS;
	public static Block woodHorizEW;
	public static Block nutTreeLog;
	public static Block nutTreeLeaves;
	public static Block woodPlank;
	public static Block lumberConstruct;
	
	public static int mixingBowlRenderID;
	public static int prepTableRenderID;
	public static int meatRenderID;
	public static int cookingPotRenderID;
	public static int bowlRenderID;
	public static int clayOvenRenderID;
	public static int sandwichRenderID;
	public static int tableStorageRenderID;
	public static int gourdRenderID;
	public static int gourdCropRenderID;
	public static int customCropRenderID;
	public static int fruitTreeRenderID;
	public static int nutLeavesRenderID;
	public static int lumberConstructRenderID;
	
	public static void setup()
	{
		loadBlocks();
		
		registerBlocks();
		
		setupFire();
	}
	
	public static void loadBlocks()
	{
		GrainsBlock = new BlockGrains();
		mixingBowl = new BlockMixBowl();
		hopperCWTFC = new BlockHopperCWTFC();
		cookingPot = new BlockCookingPot();
		meatCWTFC = new BlockMeat();
		sandwichCWTFC = new BlockSandwich();
		bowlCWTFC = new BlockBowl();
		clayOven = new BlockClayOven();
		tableStorage = new BlockTableStorage();
		
		prepTableN = new BlockPrepTable();
		prepTable2N = new BlockPrepTable2();
		prepTableS = new BlockPrepTable();
		prepTable2S = new BlockPrepTable2();
		prepTableE = new BlockPrepTable();
		prepTable2E = new BlockPrepTable2();
		prepTableW = new BlockPrepTable();
		prepTable2W = new BlockPrepTable2();
		
		customGourd = new BlockGourd();
		customCrop = new BlockCrop();

		customLeaves = new BlockLeaves();
		naturalLog = new BlockNaturalLog();
		customSapling = new BlockCustomSapling();
		woodVert = new BlockVertLog();
		woodHorizNS = new BlockHorzNS();
		woodHorizEW = new BlockHorzEW();
		nutTreeLog = new BlockNutTree();
		nutTreeLeaves = new BlockNutLeaves();
		woodPlank = new BlockPlank();
		lumberConstruct = new BlockLumberConstruct();
	}
	
	public static void registerBlocks()
	{
		GameRegistry.registerBlock(GrainsBlock, "GrainsBlock");
		GameRegistry.registerBlock(mixingBowl, ItemMixingBowl.class, "MixingBowl");
		GameRegistry.registerBlock(hopperCWTFC, "Hopper");
		GameRegistry.registerBlock(cookingPot, "CookingPot");
		GameRegistry.registerBlock(meatCWTFC, "meatCWTFC");
		GameRegistry.registerBlock(sandwichCWTFC, "sandwichCWTFC");
		GameRegistry.registerBlock(bowlCWTFC, "bowlCWTFC");
		GameRegistry.registerBlock(clayOven, "clayOven");
		GameRegistry.registerBlock(tableStorage, "tableStorage");
		GameRegistry.registerBlock(customGourd, "customGourd");
		GameRegistry.registerBlock(customCrop, "customCrop");
		GameRegistry.registerBlock(customLeaves, ItemCustomWood.class, "customLeaves");
		GameRegistry.registerBlock(naturalLog, ItemCustomWood.class, "naturalLog");
		GameRegistry.registerBlock(woodVert, ItemCustomWood.class, "WoodVert");
		GameRegistry.registerBlock(woodHorizNS, ItemCustomWood.class, "woodHorizNS");
		GameRegistry.registerBlock(woodHorizEW, ItemCustomWood.class, "woodHorizEW");
		GameRegistry.registerBlock(customSapling, ItemSapling.class, "customSapling");
		GameRegistry.registerBlock(nutTreeLog, ItemNutTreeSapling.class, "nutTreeLog");
		GameRegistry.registerBlock(nutTreeLeaves, "nutTreeLeaves");
		GameRegistry.registerBlock(woodPlank, ItemCustomWood.class, "woodPlank");
		GameRegistry.registerBlock(lumberConstruct, "lumberConstruct");
		
		GameRegistry.registerBlock(prepTableN, ItemPrepTable.class, "PrepTableN");
		GameRegistry.registerBlock(prepTable2N, ItemPrepTable2.class, "PrepTable2N");
		GameRegistry.registerBlock(prepTableS, ItemPrepTable.class, "PrepTableS");
		GameRegistry.registerBlock(prepTable2S, ItemPrepTable2.class, "PrepTable2S");
		GameRegistry.registerBlock(prepTableE, ItemPrepTable.class, "PrepTableE");
		GameRegistry.registerBlock(prepTable2E, ItemPrepTable2.class, "PrepTable2E");
		GameRegistry.registerBlock(prepTableW, ItemPrepTable.class, "PrepTableW");
		GameRegistry.registerBlock(prepTable2W, ItemPrepTable2.class, "PrepTable2W");
	}
	
	public static void setupFire()
	{
		//Organic blocks
		Blocks.fire.setFireInfo(customLeaves, 20, 20);
		Blocks.fire.setFireInfo(GrainsBlock, 20, 20);
		Blocks.fire.setFireInfo(customCrop, 20, 20);
		Blocks.fire.setFireInfo(customGourd, 20, 20);
		Blocks.fire.setFireInfo(customSapling, 20, 20);
		Blocks.fire.setFireInfo(nutTreeLeaves, 20, 20);
		
		//Wood blocks
		Blocks.fire.setFireInfo(woodPlank, 5, 5);
		Blocks.fire.setFireInfo(lumberConstruct, 5, 5);
		Blocks.fire.setFireInfo(naturalLog, 5, 5);
		Blocks.fire.setFireInfo(woodVert, 5, 5);
		Blocks.fire.setFireInfo(woodHorizNS, 5, 5);
		Blocks.fire.setFireInfo(woodHorizEW, 5, 5);
		Blocks.fire.setFireInfo(nutTreeLog, 5, 5);
		Blocks.fire.setFireInfo(prepTableN, 5, 5);
		Blocks.fire.setFireInfo(prepTable2N, 5, 5);
		Blocks.fire.setFireInfo(prepTableS, 5, 5);
		Blocks.fire.setFireInfo(prepTable2S, 5, 5);
		Blocks.fire.setFireInfo(prepTableE, 5, 5);
		Blocks.fire.setFireInfo(prepTable2E, 5, 5);
		Blocks.fire.setFireInfo(prepTableW, 5, 5);
		Blocks.fire.setFireInfo(prepTable2W, 5, 5);
	}
}
