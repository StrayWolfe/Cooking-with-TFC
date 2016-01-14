package com.JAWolfe.cookingwithtfc.init;

import com.JAWolfe.cookingwithtfc.blocks.blockGrains;
import com.JAWolfe.cookingwithtfc.blocks.blockMixBowl;
import com.JAWolfe.cookingwithtfc.blocks.blockNestBoxCWTFC;
import com.JAWolfe.cookingwithtfc.blocks.blockPrepTable;
import com.JAWolfe.cookingwithtfc.blocks.blockPrepTable2;
import com.JAWolfe.cookingwithtfc.items.ItemMixingBowl;
import com.JAWolfe.cookingwithtfc.items.ItemPrepTable;
import com.JAWolfe.cookingwithtfc.items.ItemPrepTable2;
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
	
	public static int mixingBowlRenderID;
	public static int prepTableRenderID;
	
	public static void initialise()
	{
		loadBlocks();
		
		registerBlocks();
	}
	
	public static void loadBlocks()
	{
		TFCBlocks.nestBox.setCreativeTab(null);
		
		nestBoxCWTFC = new blockNestBoxCWTFC().setBlockName("NestBox").setHardness(1);
		GrainsBlock = new blockGrains();
		mixingBowl = new blockMixBowl();
		prepTable = new blockPrepTable();
		prepTable2 = new blockPrepTable2();
	}
	
	public static void registerBlocks()
	{
		GameRegistry.registerBlock(nestBoxCWTFC, ItemTerraBlock.class, "NestBox");
		GameRegistry.registerBlock(GrainsBlock, "GrainsBlock");
		GameRegistry.registerBlock(mixingBowl, ItemMixingBowl.class, "MixingBowl");
		GameRegistry.registerBlock(prepTable, ItemPrepTable.class, "PrepTable");
		GameRegistry.registerBlock(prepTable2, ItemPrepTable2.class, "PrepTable2");
	}
}
