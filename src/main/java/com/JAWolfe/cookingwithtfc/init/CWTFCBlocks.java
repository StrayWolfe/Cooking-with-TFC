package com.JAWolfe.cookingwithtfc.init;

import com.JAWolfe.cookingwithtfc.blocks.blockCookingPot;
import com.JAWolfe.cookingwithtfc.blocks.blockGrains;
import com.JAWolfe.cookingwithtfc.blocks.blockHopper;
import com.JAWolfe.cookingwithtfc.blocks.blockMixBowl;
import com.JAWolfe.cookingwithtfc.blocks.blockNestBoxCWTFC;
import com.JAWolfe.cookingwithtfc.blocks.blockPrepTable;
import com.JAWolfe.cookingwithtfc.blocks.blockPrepTable2;
import com.JAWolfe.cookingwithtfc.items.ItemBlocks.ItemMixingBowl;
import com.JAWolfe.cookingwithtfc.items.ItemBlocks.ItemPrepTable;
import com.JAWolfe.cookingwithtfc.items.ItemBlocks.ItemPrepTable2;
import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class CWTFCBlocks 
{
	public static Block nestBoxCWTFC;
	public static Block GrainsBlock;
	public static Block mixingBowl;
	public static Block prepTable;
	public static Block prepTable2;
	public static Block hopperCWTFC;
	public static Block cookingPot;
	
	public static int mixingBowlRenderID;
	public static int prepTableRenderID;
	public static int cookingPotRenderID;
	
	public static void initialise()
	{
		loadBlocks();
		
		registerBlocks();
	}
	
	public static void loadBlocks()
	{
		TFCBlocks.nestBox.setCreativeTab(null);
		
		nestBoxCWTFC = new blockNestBoxCWTFC();
		GrainsBlock = new blockGrains();
		mixingBowl = new blockMixBowl();
		prepTable = new blockPrepTable();
		prepTable2 = new blockPrepTable2();
		hopperCWTFC = new blockHopper();
		cookingPot = new blockCookingPot();
	}
	
	public static void registerBlocks()
	{
		GameRegistry.registerBlock(nestBoxCWTFC, ItemTerraBlock.class, "NestBox");
		GameRegistry.registerBlock(GrainsBlock, "GrainsBlock");
		GameRegistry.registerBlock(mixingBowl, ItemMixingBowl.class, "MixingBowl");
		GameRegistry.registerBlock(prepTable, ItemPrepTable.class, "PrepTable");
		GameRegistry.registerBlock(prepTable2, ItemPrepTable2.class, "PrepTable2");
		GameRegistry.registerBlock(hopperCWTFC, "Hopper");
		GameRegistry.registerBlock(cookingPot, "CookingPot");
	}
}
