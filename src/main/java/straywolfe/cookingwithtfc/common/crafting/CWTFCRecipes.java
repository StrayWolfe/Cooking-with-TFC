package straywolfe.cookingwithtfc.common.crafting;

import java.util.List;

import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.CraftingManagerTFC;
import com.bioxx.tfc.api.Crafting.KilnCraftingManager;
import com.bioxx.tfc.api.Crafting.KilnRecipe;
import com.bioxx.tfc.api.Crafting.QuernManager;
import com.bioxx.tfc.api.Crafting.QuernRecipe;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.api.recipe.PressManager;
import straywolfe.cookingwithtfc.api.recipe.PressRecipe;
import terramisc.core.TFCMBlocks;
import terramisc.core.TFCMFluids;

public class CWTFCRecipes 
{
	public static void registerRecipes() 
	{		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCBlocks.nestBox), new ItemStack(CWTFCBlocks.nestBoxCWTFC)));
		
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
		
		registerQuernRecipes();
		registerKilnRecipes();
		registerKnappingRecipes();
		registerPressRecipes();
		CWTFCFoodRecipes.Recipes();
	}
	
	public static void registerQuernRecipes() 
	{
		QuernManager quernmanager = QuernManager.getInstance();

		quernmanager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.looseRock, 1, 5), ItemFoodTFC.createTag(new ItemStack(CWTFCItems.Salt), 8)));//Rock Salt
		
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
		
		pressmanager.addRecipe(new PressRecipe(TFCItems.olive, TFCFluids.OLIVEOIL, 1));
		
		if(Loader.isModLoaded("tfcm"))
		{
			pressmanager.addRecipe(new PressRecipe(TFCItems.cherry, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(TFCItems.plum, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(TFCItems.wintergreenBerry, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(TFCItems.blueberry, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(TFCItems.raspberry, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(TFCItems.strawberry, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(TFCItems.blackberry, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(TFCItems.bunchberry, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(TFCItems.cranberry, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(TFCItems.snowberry, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(TFCItems.elderberry, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(TFCItems.gooseberry, TFCMFluids.FRUITJUICE, 8));
			pressmanager.addRecipe(new PressRecipe(TFCItems.cloudberry, TFCMFluids.FRUITJUICE, 8));
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
