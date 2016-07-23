package straywolfe.cookingwithtfc.common.crafting;

import java.util.List;

import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.BarrelAlcoholRecipe;
import com.bioxx.tfc.api.Crafting.BarrelLiquidToLiquidRecipe;
import com.bioxx.tfc.api.Crafting.BarrelManager;
import com.bioxx.tfc.api.Crafting.BarrelMultiItemRecipe;
import com.bioxx.tfc.api.Crafting.BarrelRecipe;
import com.bioxx.tfc.api.Crafting.CraftingManagerTFC;
import com.bioxx.tfc.api.Crafting.KilnCraftingManager;
import com.bioxx.tfc.api.Crafting.KilnRecipe;
import com.bioxx.tfc.api.Crafting.QuernManager;
import com.bioxx.tfc.api.Crafting.QuernRecipe;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCFluids;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.api.recipe.PressManager;
import straywolfe.cookingwithtfc.api.recipe.PressRecipe;
import straywolfe.cookingwithtfc.common.item.ItemTFCFoodTransform;
import terramisc.core.TFCMBlocks;
import terramisc.core.TFCMFluids;

public class CWTFCRecipes 
{
	public static void registerRecipes() 
	{		
		Recipes.removeRecipe(new ItemStack(TFCBlocks.nestBox,1));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CWTFCBlocks.nestBoxCWTFC,1), "S S","PSP","PPP", 'S', new ItemStack(TFCItems.straw,1), 'P', "woodLumber"));
		
		for(int i = 0; i < Global.WOOD_ALL.length; i++)
		{
			int l = i%16;
			if(i==l)
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CWTFCBlocks.prepTableN, 1, i), "   ", "PPP", "L L", 'P', new ItemStack(TFCItems.singlePlank, 1, i), 'L', new ItemStack(TFCBlocks.woodSupportV, 1, i)));
			}
			else if(i/16 == 1)
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CWTFCBlocks.prepTable2N, 1, 0), "   ", "PPP", "L L", 'P', new ItemStack(TFCItems.singlePlank, 1, i), 'L', new ItemStack(TFCBlocks.woodSupportV2, 1, 0)));
			}
		}
		
		if(Loader.isModLoaded("tfcm"))
			Recipes.removeRecipe(new ItemStack(TFCMBlocks.blockFruitPress,1));
		
		registerBarrelRecipes();
		registerQuernRecipes();
		registerKilnRecipes();
		registerKnappingRecipes();
		registerPressRecipes();
		CWTFCFoodRecipes.Recipes();
	}
	
	public static void registerBarrelRecipes() 
	{
		BarrelManager barrelmanager = BarrelManager.getInstance();
		
		barrelmanager.addRecipe(new BarrelAlcoholRecipe(ItemTFCFoodTransform.createTag(new ItemStack(CWTFCItems.redAppleCWTFC), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.CIDER, 10000)));
		barrelmanager.addRecipe(new BarrelAlcoholRecipe(ItemTFCFoodTransform.createTag(new ItemStack(CWTFCItems.greenAppleCWTFC), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.CIDER, 10000)));
		barrelmanager.addRecipe(new BarrelAlcoholRecipe(ItemTFCFoodTransform.createTag(new ItemStack(CWTFCItems.potatoCWTFC), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.VODKA, 10000)));
		barrelmanager.addRecipe(new BarrelAlcoholRecipe(ItemTFCFoodTransform.createTag(new ItemStack(CWTFCItems.barleyGroundCWTFC), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.BEER, 10000)));
		barrelmanager.addRecipe(new BarrelAlcoholRecipe(ItemTFCFoodTransform.createTag(new ItemStack(CWTFCItems.cornmealGroundCWTFC), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.CORNWHISKEY, 10000)));
		barrelmanager.addRecipe(new BarrelAlcoholRecipe(ItemTFCFoodTransform.createTag(new ItemStack(CWTFCItems.riceGroundCWTFC), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.SAKE, 10000)));
		barrelmanager.addRecipe(new BarrelAlcoholRecipe(ItemTFCFoodTransform.createTag(new ItemStack(CWTFCItems.ryeGroundCWTFC), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.RYEWHISKEY, 10000)));
		barrelmanager.addRecipe(new BarrelAlcoholRecipe(ItemTFCFoodTransform.createTag(new ItemStack(CWTFCItems.wheatGroundCWTFC), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.WHISKEY, 10000)));
		barrelmanager.addRecipe(new BarrelAlcoholRecipe(ItemTFCFoodTransform.createTag(new ItemStack(CWTFCItems.sugarCWTFC), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.RUM, 10000)));
		
		barrelmanager.addRecipe(new BarrelMultiItemRecipe(ItemTFCFoodTransform.createTag(new ItemStack(CWTFCItems.sugarcaneCWTFC), 1), new FluidStack(TFCFluids.FRESHWATER, 60), ItemTFCFoodTransform.createTag(new ItemStack(CWTFCItems.sugarCWTFC), 0.1f), new FluidStack(TFCFluids.FRESHWATER, 60)).setMinTechLevel(0));
		barrelmanager.addRecipe(new BarrelMultiItemRecipe(ItemTFCFoodTransform.createTag(new ItemStack(CWTFCItems.Salt, 1, OreDictionary.WILDCARD_VALUE), 1),new FluidStack(CWTFCFluids.MILKCURDLEDCWTFC, 1000), ItemTFCFoodTransform.createTag(new ItemStack(CWTFCItems.cheeseCWTFC), 16), new FluidStack(CWTFCFluids.MILKCURDLEDCWTFC, 1000)).setSealedRecipe(true).setMinTechLevel(0).setRemovesLiquid(true));
		barrelmanager.addRecipe(new BarrelRecipe(null, new FluidStack(CWTFCFluids.MILKVINEGARCWTFC, 10000), null, new FluidStack(CWTFCFluids.MILKCURDLEDCWTFC, 10000)).setMinTechLevel(0).setRemovesLiquid(false));
		barrelmanager.addRecipe(new BarrelLiquidToLiquidRecipe(new FluidStack(CWTFCFluids.MILKCWTFC, 9000), new FluidStack(TFCFluids.VINEGAR, 1000), new FluidStack(CWTFCFluids.MILKVINEGARCWTFC, 10000)).setSealedRecipe(false).setMinTechLevel(0).setRemovesLiquid(false));
		barrelmanager.addRecipe(new BarrelLiquidToLiquidRecipe(new FluidStack(CWTFCFluids.MILKVINEGARCWTFC, 9000), new FluidStack(CWTFCFluids.MILKCWTFC, 1000), new FluidStack(CWTFCFluids.MILKVINEGARCWTFC, 10000)).setSealedRecipe(false).setMinTechLevel(0).setRemovesLiquid(false));
	}
	
	public static void registerQuernRecipes() 
	{
		QuernManager quernmanager = QuernManager.getInstance();
		
		quernmanager.addRecipe(new QuernRecipe(new ItemStack(CWTFCItems.wheatGrainCWTFC, 1), new ItemStack(CWTFCItems.wheatGroundCWTFC, 1)));//Wheat Flour
		quernmanager.addRecipe(new QuernRecipe(new ItemStack(CWTFCItems.ryeGrainCWTFC, 1), new ItemStack(CWTFCItems.ryeGroundCWTFC, 1)));//Rye Flour
		quernmanager.addRecipe(new QuernRecipe(new ItemStack(CWTFCItems.oatGrainCWTFC, 1), new ItemStack(CWTFCItems.oatGroundCWTFC, 1)));//Oat Flour
		quernmanager.addRecipe(new QuernRecipe(new ItemStack(CWTFCItems.barleyGrainCWTFC, 1), new ItemStack(CWTFCItems.barleyGroundCWTFC, 1)));//Barley Flour
		quernmanager.addRecipe(new QuernRecipe(new ItemStack(CWTFCItems.riceGrainCWTFC, 1), new ItemStack(CWTFCItems.riceGroundCWTFC, 1)));//Rice Flour
		quernmanager.addRecipe(new QuernRecipe(new ItemStack(CWTFCItems.maizeEarCWTFC, 1), new ItemStack(CWTFCItems.cornmealGroundCWTFC, 1)));//Cornmeal
		quernmanager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.looseRock, 1, 5), ItemFoodTFC.createTag(new ItemStack(CWTFCItems.Salt, 1, 1), 8)));//Rock Salt
		
		removeQuernRecipe(new ItemStack(TFCItems.looseRock, 1, 5), new ItemStack(TFCItems.powder, 4, 9));
	}
	
	public static void registerKilnRecipes()
	{
		KilnCraftingManager kilnmanager = KilnCraftingManager.getInstance();
		
		kilnmanager.addRecipe(new KilnRecipe(new ItemStack(CWTFCBlocks.mixingBowl,1,0), 0, new ItemStack(CWTFCBlocks.mixingBowl,1,1)));
		kilnmanager.addRecipe(new KilnRecipe(new ItemStack(CWTFCItems.ClayCookingPot,1,0), 0, new ItemStack(CWTFCItems.ClayCookingPot,1,1)));
	}
	
	public static void registerKnappingRecipes()
	{
		CraftingManagerTFC craftingmanager = CraftingManagerTFC.getInstance();
		
		craftingmanager.addRecipe(new ItemStack(CWTFCBlocks.mixingBowl, 1, 0), new Object[] { 
				"#####",
				"#####",
				"     ",
				"     ",
				"#   #", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		
		craftingmanager.addRecipe(new ItemStack(CWTFCItems.ClayCookingPot, 1, 0), new Object[] { 
				"## ##",
				"# # #",
				"     ",
				"     ",
				"#   #", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
	}
	
	public static void registerPressRecipes()
	{
		PressManager pressmanager = PressManager.getInstance();
		
		pressmanager.addRecipe(new PressRecipe(CWTFCItems.oliveCWTFC, TFCFluids.OLIVEOIL, 1));
		
		if(Loader.isModLoaded("tfcm"))
		{
			pressmanager.addRecipe(new PressRecipe(CWTFCItems.cherryCWTFC, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(CWTFCItems.plumCWTFC, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(CWTFCItems.wintergreenBerryCWTFC, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(CWTFCItems.blueberryCWTFC, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(CWTFCItems.raspberryCWTFC, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(CWTFCItems.strawberryCWTFC, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(CWTFCItems.blackberryCWTFC, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(CWTFCItems.bunchberryCWTFC, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(CWTFCItems.cranberryCWTFC, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(CWTFCItems.snowberryCWTFC, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(CWTFCItems.elderberryCWTFC, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(CWTFCItems.gooseberryCWTFC, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(CWTFCItems.cloudberryCWTFC, TFCMFluids.FRUITJUICE, 8));
		}
	}
	
	public static void removeQuernRecipe(ItemStack inputStack, ItemStack outputStack)
	{
		List<QuernRecipe> quernList = QuernManager.getInstance().getRecipes();
		for (int i = 0; i < quernList.size(); i++)
		{
			if (quernList.get(i) != null)
			{
				if (quernList.get(i).isInItem(inputStack) && ItemStack.areItemStacksEqual(quernList.get(i).getResult(), outputStack))
					quernList.remove(i--);
			}
		}
		
		if(QuernManager.getInstance().findMatchingRecipe(inputStack) == null && QuernManager.getInstance().isValidItem(inputStack))
		{
			List<ItemStack> validItemsList = QuernManager.getInstance().getValidItems();
			for (int i = 0; i < validItemsList.size(); i++)
			{
				if (validItemsList.get(i) != null)
				{
					if (ItemStack.areItemStacksEqual(validItemsList.get(i), inputStack))
						validItemsList.remove(i--);
				}
			}
		}
	}
 }
