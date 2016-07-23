package straywolfe.cookingwithtfc.common.crafting;

import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.TFCFluids;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import straywolfe.cookingwithtfc.api.CWTFCFluids;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.api.recipe.CookingPotManager;
import straywolfe.cookingwithtfc.api.recipe.CookingPotRecipe;

public class CWTFCFoodRecipes
{	
	public static void Recipes()
	{
		CraftingGridRecipes();
		CookingPotRecipes();
	}
	
	public static void CraftingGridRecipes()
	{
		for(ItemTerra i : CWTFCItems.subfoodList)
		{
			for(int j = 1; j < i.metaNames.length; j++)
			{
				addSubFoodMergeRecipe(i, j);
				GameRegistry.addRecipe(new ShapelessOreRecipe(ItemFoodTFC.createTag(new ItemStack(i, 1, j)), ItemFoodTFC.createTag(new ItemStack(i, 1, j)), "itemKnife"));
				GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(i, 1, j)), ItemFoodTFC.createTag(new ItemStack(i, 1, j)));
			}
		}
	}
	
	public static void addSubFoodMergeRecipe(Item food, int subItem)
	{
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)));
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)));
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)));
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)));
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)));
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)));
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)));
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)), ItemFoodTFC.createTag(new ItemStack(food, 1, subItem)));
	}
	
	public static void CookingPotRecipes()
	{
		CookingPotManager cookingpotManager = CookingPotManager.getInstance();
		
		cookingpotManager.addRecipe(new CookingPotRecipe(120, new FluidStack(TFCFluids.SALTWATER, 1000), 
				new ItemStack[]{new ItemStack(CWTFCItems.Salt, 1, 1)}));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(60, new FluidStack(CWTFCFluids.BROTH, 1000), new FluidStack(TFCFluids.FRESHWATER, 1000),
				populateStackList(new int[]{10}, new ItemStack[]{new ItemStack(CWTFCItems.BoiledChicken)}),
				populateStackList(new int[]{10}, new ItemStack[]{new ItemStack(CWTFCItems.chickenRawCWTFC)})));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(60, new FluidStack(CWTFCFluids.BROTH, 1000), new FluidStack(TFCFluids.FRESHWATER, 1000),
				populateStackList(new int[]{10}, new ItemStack[]{new ItemStack(CWTFCItems.BoiledBeef)}),
				populateStackList(new int[]{10}, new ItemStack[]{new ItemStack(CWTFCItems.beefRawCWTFC)})));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(60, new FluidStack(CWTFCFluids.BROTH, 1000), new FluidStack(TFCFluids.FRESHWATER, 1000),
				populateStackList(new int[]{10}, new ItemStack[]{new ItemStack(CWTFCItems.BoiledFish)}),
				populateStackList(new int[]{10}, new ItemStack[]{new ItemStack(CWTFCItems.fishRawCWTFC)})));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(60, new FluidStack(CWTFCFluids.BROTH, 1000), new FluidStack(TFCFluids.FRESHWATER, 1000),
				populateStackList(new int[]{10}, new ItemStack[]{new ItemStack(CWTFCItems.BoiledPork)}),
				populateStackList(new int[]{10}, new ItemStack[]{new ItemStack(CWTFCItems.porkchopRawCWTFC)})));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(60, new FluidStack(CWTFCFluids.BROTH, 1000), new FluidStack(TFCFluids.FRESHWATER, 1000),
				populateStackList(new int[]{10}, new ItemStack[]{new ItemStack(CWTFCItems.BoiledVenison)}),
				populateStackList(new int[]{10}, new ItemStack[]{new ItemStack(CWTFCItems.venisonRawCWTFC)})));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(60, new FluidStack(CWTFCFluids.BROTH, 1000), new FluidStack(TFCFluids.FRESHWATER, 1000),
				new ItemStack[]{new ItemStack(CWTFCItems.onionCWTFC), new ItemStack(CWTFCItems.carrotCWTFC), new ItemStack(CWTFCItems.cabbageCWTFC), new ItemStack(CWTFCItems.garlicCWTFC)},
				populateStackList(new int[]{2,2,2,2}, new ItemStack[]{new ItemStack(CWTFCItems.onionCWTFC), new ItemStack(CWTFCItems.carrotCWTFC), new ItemStack(CWTFCItems.cabbageCWTFC), new ItemStack(CWTFCItems.garlicCWTFC)})));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(60, new FluidStack(CWTFCFluids.VEGETABLESOUP, 1000), new FluidStack(CWTFCFluids.BROTH, 1000),
				populateStackList(new int[]{2,2,2,2}, new ItemStack[]{new ItemStack(CWTFCItems.tomatoCWTFC), new ItemStack(CWTFCItems.carrotCWTFC), new ItemStack(CWTFCItems.maizeEarCWTFC), new ItemStack(CWTFCItems.greenbeansCWTFC)})));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(40, new FluidStack(CWTFCFluids.TOMATOSOUP, 1000), new FluidStack(CWTFCFluids.BROTH, 1000),
				populateStackList(new int[]{1,1,1,7}, new ItemStack[]{new ItemStack(CWTFCItems.onionCWTFC), new ItemStack(CWTFCItems.garlicCWTFC), new ItemStack(CWTFCItems.Salt, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(CWTFCItems.tomatoCWTFC)})));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(55, new FluidStack(CWTFCFluids.CHICKENSOUP, 1000), new FluidStack(CWTFCFluids.BROTH, 1000),
				populateStackList(new int[]{6,2,1,1}, new ItemStack[]{new ItemStack(CWTFCItems.BoiledChicken), new ItemStack(CWTFCItems.carrotCWTFC), new ItemStack(CWTFCItems.onionCWTFC), new ItemStack(CWTFCItems.Salt, 1, OreDictionary.WILDCARD_VALUE)})));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(90, new FluidStack(CWTFCFluids.BEEFSTEW, 1000), new FluidStack(CWTFCFluids.BROTH, 1000),
				populateStackList(new int[]{2,2,1,5}, new ItemStack[]{new ItemStack(CWTFCItems.carrotCWTFC), new ItemStack(CWTFCItems.potatoCWTFC), new ItemStack(CWTFCItems.onionCWTFC), new ItemStack(CWTFCItems.BoiledBeef)})));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(90, new FluidStack(CWTFCFluids.VENISONSTEW, 1000), new FluidStack(TFCFluids.FRESHWATER, 1000),
				populateStackList(new int[]{2,2,1,5}, new ItemStack[]{new ItemStack(CWTFCItems.carrotCWTFC), new ItemStack(CWTFCItems.potatoCWTFC), new ItemStack(CWTFCItems.onionCWTFC), new ItemStack(CWTFCItems.BoiledVenison)})));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(40, new FluidStack(CWTFCFluids.FISHCHOWDER, 1000), new FluidStack(CWTFCFluids.BROTH, 1000),
				"itemFlour", populateStackList(new int[]{1,2,6}, new ItemStack[]{new ItemStack(CWTFCItems.onionCWTFC), new ItemStack(CWTFCItems.potatoCWTFC), new ItemStack(CWTFCItems.BoiledFish)})));
	}
	
	private static ItemStack[] populateStackList(int[] amount, ItemStack[] is)
	{
		int count = 0;
		int tracker = 0;
		
		for(int n : amount)
		{
			count += n;
		}
		
		ItemStack[] list = new ItemStack[count];
		
		for(int i = 0; i < amount.length; i++)
		{
			for(int j = 0; j < amount[i]; j++)
			{
				list[tracker] = is[i];
				tracker++;
			}
		}
		
		return list;
	}
}
