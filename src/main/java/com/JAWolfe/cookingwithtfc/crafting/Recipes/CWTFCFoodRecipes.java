package com.JAWolfe.cookingwithtfc.crafting.Recipes;

import com.JAWolfe.cookingwithtfc.crafting.CookingPotManager;
import com.JAWolfe.cookingwithtfc.crafting.CookingPotRecipe;
import com.JAWolfe.cookingwithtfc.crafting.FoodManager;
import com.JAWolfe.cookingwithtfc.crafting.FoodRecipe;
import com.JAWolfe.cookingwithtfc.init.CWTFCBlocks;
import com.JAWolfe.cookingwithtfc.init.CWTFCFluids;
import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.JAWolfe.cookingwithtfc.items.ItemTFCMeatTransform;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CWTFCFoodRecipes
{	
	public static void Recipes()
	{
		CraftingGridRecipes();
		FoodPrepRecipes();
		CookingPotRecipes();
	}
	
	public static void CraftingGridRecipes()
	{
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.beefRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.beefRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.chickenRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.chickenRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.horseMeatRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.horseMeatRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.fishRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.fishRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.calamariRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.calamariRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.porkchopRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.porkchopRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.muttonRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.muttonRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.venisonRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.venisonRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
		
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
	
	
	
	public static void FoodPrepRecipes()
	{
		FoodManager foodManager = FoodManager.getInstance();
		
		//Salted Meats
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.venisonRawCWTFC), new ItemStack(CWTFCItems.SeaSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.venisonRawCWTFC)).setSalted(true));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.beefRawCWTFC), new ItemStack(CWTFCItems.SeaSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.beefRawCWTFC)).setSalted(true));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.chickenRawCWTFC), new ItemStack(CWTFCItems.SeaSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.chickenRawCWTFC)).setSalted(true));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.porkchopRawCWTFC), new ItemStack(CWTFCItems.SeaSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.porkchopRawCWTFC)).setSalted(true));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.fishRawCWTFC), new ItemStack(CWTFCItems.SeaSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.fishRawCWTFC)).setSalted(true));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.calamariRawCWTFC), new ItemStack(CWTFCItems.SeaSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.calamariRawCWTFC)).setSalted(true));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.muttonRawCWTFC), new ItemStack(CWTFCItems.SeaSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.muttonRawCWTFC)).setSalted(true));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.horseMeatRawCWTFC), new ItemStack(CWTFCItems.SeaSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.horseMeatRawCWTFC)).setSalted(true));
		
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.venisonRawCWTFC), new ItemStack(CWTFCItems.RockSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.venisonRawCWTFC)).setSalted(true));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.beefRawCWTFC), new ItemStack(CWTFCItems.RockSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.beefRawCWTFC)).setSalted(true));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.chickenRawCWTFC), new ItemStack(CWTFCItems.RockSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.chickenRawCWTFC)).setSalted(true));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.porkchopRawCWTFC), new ItemStack(CWTFCItems.RockSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.porkchopRawCWTFC)).setSalted(true));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.fishRawCWTFC), new ItemStack(CWTFCItems.RockSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.fishRawCWTFC)).setSalted(true));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.calamariRawCWTFC), new ItemStack(CWTFCItems.RockSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.calamariRawCWTFC)).setSalted(true));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.muttonRawCWTFC), new ItemStack(CWTFCItems.RockSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.muttonRawCWTFC)).setSalted(true));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{}, new ItemStack[]{new ItemStack(CWTFCItems.horseMeatRawCWTFC), new ItemStack(CWTFCItems.RockSalt)}, 
				new float[]{1.0F, 0.04F}, new ItemStack(CWTFCItems.horseMeatRawCWTFC)).setSalted(true));
		
		//Dough
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryJug, 1, 2), new ItemStack(CWTFCBlocks.mixingBowl, 1, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.barleyGroundCWTFC)}, new float[]{1.0F}, new ItemStack(CWTFCItems.barleyDoughCWTFC)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryJug, 1, 2), new ItemStack(CWTFCBlocks.mixingBowl, 1, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.cornmealGroundCWTFC)}, new float[]{1.0F}, new ItemStack(CWTFCItems.cornmealDoughCWTFC)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryJug, 1, 2), new ItemStack(CWTFCBlocks.mixingBowl, 1, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.oatGroundCWTFC)}, new float[]{1.0F}, new ItemStack(CWTFCItems.oatDoughCWTFC)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryJug, 1, 2), new ItemStack(CWTFCBlocks.mixingBowl, 1, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.riceGroundCWTFC)}, new float[]{1.0F}, new ItemStack(CWTFCItems.riceDoughCWTFC)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryJug, 1, 2), new ItemStack(CWTFCBlocks.mixingBowl, 1, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.ryeGroundCWTFC)}, new float[]{1.0F}, new ItemStack(CWTFCItems.ryeDoughCWTFC)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryJug, 1, 2), new ItemStack(CWTFCBlocks.mixingBowl, 1, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.wheatGroundCWTFC)}, new float[]{1.0F}, new ItemStack(CWTFCItems.wheatDoughCWTFC)));
		
		//Salads
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.cabbageCWTFC), new ItemStack(CWTFCItems.tomatoCWTFC), 
						new ItemStack(CWTFCItems.yellowBellPepperCWTFC), new ItemStack(CWTFCItems.onionCWTFC)},
				new float[]{0.5F, 0.3F, 0.1F, 0.1F}, new ItemStack(CWTFCItems.VeggySalad)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.cabbageCWTFC), new ItemStack(CWTFCItems.tomatoCWTFC), 
						new ItemStack(CWTFCItems.redBellPepperCWTFC), new ItemStack(CWTFCItems.onionCWTFC)},
				new float[]{0.5F, 0.3F, 0.1F, 0.1F}, new ItemStack(CWTFCItems.VeggySalad)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.cabbageCWTFC), new ItemStack(CWTFCItems.tomatoCWTFC),
						new ItemStack(CWTFCItems.greenBellPepperCWTFC), new ItemStack(CWTFCItems.onionCWTFC)},
				new float[]{0.5F, 0.3F, 0.1F, 0.1F}, new ItemStack(CWTFCItems.VeggySalad)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.seaWeedCWTFC), new ItemStack(CWTFCItems.tomatoCWTFC), 
						new ItemStack(CWTFCItems.yellowBellPepperCWTFC), new ItemStack(CWTFCItems.onionCWTFC)},
				new float[]{0.5F, 0.3F, 0.1F, 0.1F}, new ItemStack(CWTFCItems.VeggySalad)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.seaWeedCWTFC), new ItemStack(CWTFCItems.tomatoCWTFC), 
						new ItemStack(CWTFCItems.redBellPepperCWTFC), new ItemStack(CWTFCItems.onionCWTFC)},
				new float[]{0.5F, 0.3F, 0.1F, 0.1F}, new ItemStack(CWTFCItems.VeggySalad)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.seaWeedCWTFC), new ItemStack(CWTFCItems.tomatoCWTFC), 
						new ItemStack(CWTFCItems.greenBellPepperCWTFC), new ItemStack(CWTFCItems.onionCWTFC)},
				new float[]{0.5F, 0.3F, 0.1F, 0.1F}, new ItemStack(CWTFCItems.VeggySalad)));		
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.potatoCWTFC), new ItemStack(CWTFCItems.onionCWTFC), new ItemStack(CWTFCItems.eggCookedCWTFC)},
				new float[]{0.70F, 0.2F, 0.1F}, new ItemStack(CWTFCItems.PotatoSalad)));		
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.redAppleCWTFC), new ItemStack(CWTFCItems.bananaCWTFC), 
						new ItemStack(CWTFCItems.blueberryCWTFC), new ItemStack(CWTFCItems.strawberryCWTFC)},
				new float[]{0.25F, 0.25F, 0.25F, 0.25F}, new ItemStack(CWTFCItems.FruitSalad)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.blueberryCWTFC), new ItemStack(CWTFCItems.raspberryCWTFC), 
						new ItemStack(CWTFCItems.strawberryCWTFC), new ItemStack(CWTFCItems.blackberryCWTFC)},
				new float[]{0.25F, 0.25F, 0.25F, 0.25F}, new ItemStack(CWTFCItems.FruitSalad)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new ItemStack[]{new ItemStack(CWTFCItems.greenAppleCWTFC), new ItemStack(CWTFCItems.orangeCWTFC), 
				new ItemStack(CWTFCItems.cherryCWTFC), new ItemStack(CWTFCItems.bunchberryCWTFC)},
				new float[]{0.25F, 0.25F, 0.25F, 0.25F}, new ItemStack(CWTFCItems.FruitSalad)));
		
		//Sandwiches
		Item[] breads = new Item[]{CWTFCItems.barleyBreadCWTFC,CWTFCItems.cornBreadCWTFC,CWTFCItems.oatBreadCWTFC,
				CWTFCItems.riceBreadCWTFC,CWTFCItems.ryeBreadCWTFC,CWTFCItems.wheatBreadCWTFC};
		
		//Ham Sandwiches
		for(int i = 0; i < breads.length; i++)
		{
			foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.stoneKnife, 1)}, 
					new ItemStack[]{new ItemStack(breads[i]), new ItemStack(CWTFCItems.porkchopCookedCWTFC), new ItemStack(CWTFCItems.cabbageCWTFC),
							new ItemStack(CWTFCItems.tomatoCWTFC), new ItemStack(CWTFCItems.onionCWTFC)},
					new float[]{0.5F, 0.2F, 0.15F, 0.1F, 0.05F}, new ItemStack(CWTFCItems.HamSandwich, 1, i)));
		}
		
		//Chicken Sandwich
		for(int i = 0; i < breads.length; i++)
		{
			foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.stoneKnife, 1)}, 
					new ItemStack[]{new ItemStack(breads[i]), new ItemStack(CWTFCItems.chickenCookedCWTFC), new ItemStack(CWTFCItems.cabbageCWTFC), 
							new ItemStack(CWTFCItems.tomatoCWTFC), new ItemStack(CWTFCItems.onionCWTFC)},
					new float[]{0.5F, 0.2F, 0.15F, 0.1F, 0.05F}, new ItemStack(CWTFCItems.ChickenSandwich, 1, i)));
		}
		
		//Roast Beef Sandwich
		for(int i = 0; i < breads.length; i++)
		{
			foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.stoneKnife, 1)}, 
					new ItemStack[]{new ItemStack(breads[i]), new ItemStack(CWTFCItems.beefCookedCWTFC), new ItemStack(CWTFCItems.cabbageCWTFC), 
							new ItemStack(CWTFCItems.tomatoCWTFC), new ItemStack(CWTFCItems.onionCWTFC)},
					new float[]{0.5F, 0.2F, 0.15F, 0.1F, 0.05F}, new ItemStack(CWTFCItems.RoastBeefSandwich, 1, i)));
		}
		
		//Salmon Sandwich
		for(int i = 0; i < breads.length; i++)
		{
			foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.stoneKnife, 1)}, 
					new ItemStack[]{new ItemStack(breads[i]), new ItemStack(CWTFCItems.fishCookedCWTFC), new ItemStack(CWTFCItems.cabbageCWTFC), 
							new ItemStack(CWTFCItems.tomatoCWTFC), new ItemStack(CWTFCItems.onionCWTFC)},
					new float[]{0.5F, 0.2F, 0.15F, 0.1F, 0.05F}, new ItemStack(CWTFCItems.SalmonSandwich, 1, i)));
		}
		
		//Fried Egg Sandwich
		for(int i = 0; i < breads.length; i++)
		{
			foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.stoneKnife, 1)}, 
					new ItemStack[]{new ItemStack(breads[i]), new ItemStack(CWTFCItems.eggCookedCWTFC), new ItemStack(CWTFCItems.cabbageCWTFC), 
							new ItemStack(CWTFCItems.tomatoCWTFC), new ItemStack(CWTFCItems.onionCWTFC)},
					new float[]{0.5F, 0.2F, 0.15F, 0.1F, 0.05F}, new ItemStack(CWTFCItems.FriedEggSandwich, 1, i)));
		}
		
		//Mutton Sandwich
		for(int i = 0; i < breads.length; i++)
		{
			foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.stoneKnife, 1)}, 
					new ItemStack[]{new ItemStack(breads[i]), new ItemStack(CWTFCItems.muttonCookedCWTFC), new ItemStack(CWTFCItems.cabbageCWTFC), 
							new ItemStack(CWTFCItems.tomatoCWTFC), new ItemStack(CWTFCItems.onionCWTFC)},
					new float[]{0.5F, 0.2F, 0.15F, 0.1F, 0.05F}, new ItemStack(CWTFCItems.MuttonSandwich, 1, i)));
		}
		
		//Venison Steak Sandwich
		for(int i = 0; i < breads.length; i++)
		{
			foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.stoneKnife, 1)}, 
					new ItemStack[]{new ItemStack(breads[i]), new ItemStack(CWTFCItems.venisonCookedCWTFC), new ItemStack(CWTFCItems.cabbageCWTFC), 
							new ItemStack(CWTFCItems.tomatoCWTFC), new ItemStack(CWTFCItems.onionCWTFC)},
					new float[]{0.5F, 0.2F, 0.15F, 0.1F, 0.05F}, new ItemStack(CWTFCItems.VenisonSteakSandwich, 1, i)));
		}
	}
	
	public static void CookingPotRecipes()
	{
		CookingPotManager cookingpotManager = CookingPotManager.getInstance();
		
		cookingpotManager.addRecipe(new CookingPotRecipe(null, new FluidStack(TFCFluids.SALTWATER, 1000), 
				null, null, new ItemStack[]{new ItemStack(CWTFCItems.SeaSalt)}, new float[]{1F}, 120));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(new ItemStack[]{new ItemStack(CWTFCItems.chickenRawCWTFC)}, 
				new FluidStack(TFCFluids.FRESHWATER, 1000), new float[]{8F}, new FluidStack(CWTFCFluids.BROTH, 1000), 
				new ItemStack[]{new ItemStack(CWTFCItems.BoiledChicken)}, new float[]{8F}, 60));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(new ItemStack[]{new ItemStack(CWTFCItems.beefRawCWTFC)}, 
				new FluidStack(TFCFluids.FRESHWATER, 1000), new float[]{8F}, new FluidStack(CWTFCFluids.BROTH, 1000), 
				new ItemStack[]{new ItemStack(CWTFCItems.BoiledBeef)}, new float[]{8F}, 60));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(new ItemStack[]{new ItemStack(CWTFCItems.fishRawCWTFC)}, 
				new FluidStack(TFCFluids.FRESHWATER, 1000), new float[]{8F}, new FluidStack(CWTFCFluids.BROTH, 1000), 
				new ItemStack[]{new ItemStack(CWTFCItems.BoiledFish)}, new float[]{8F}, 60));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(new ItemStack[]{new ItemStack(CWTFCItems.porkchopRawCWTFC)}, 
				new FluidStack(TFCFluids.FRESHWATER, 1000), new float[]{8F}, new FluidStack(CWTFCFluids.BROTH, 1000), 
				new ItemStack[]{new ItemStack(CWTFCItems.BoiledPork)}, new float[]{8F}, 60));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(new ItemStack[]{new ItemStack(CWTFCItems.venisonRawCWTFC)}, 
				new FluidStack(TFCFluids.FRESHWATER, 1000), new float[]{8F}, new FluidStack(CWTFCFluids.BROTH, 1000), 
				new ItemStack[]{new ItemStack(CWTFCItems.BoiledVenison)}, new float[]{8F}, 60));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(new ItemStack[]{new ItemStack(CWTFCItems.onionCWTFC), new ItemStack(CWTFCItems.carrotCWTFC), new ItemStack(CWTFCItems.cabbageCWTFC), new ItemStack(CWTFCItems.garlicCWTFC)}, 
				new FluidStack(TFCFluids.FRESHWATER, 1000), new float[]{2F, 2F, 2F, 2F}, new FluidStack(CWTFCFluids.BROTH, 1000), 
				new ItemStack[]{new ItemStack(CWTFCItems.onionCWTFC), new ItemStack(CWTFCItems.carrotCWTFC), new ItemStack(CWTFCItems.cabbageCWTFC), new ItemStack(CWTFCItems.garlicCWTFC)}, new float[]{1F, 1F, 1F, 1F}, 60));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(new ItemStack[]{new ItemStack(CWTFCItems.tomatoCWTFC), new ItemStack(CWTFCItems.carrotCWTFC), new ItemStack(CWTFCItems.maizeEarCWTFC), new ItemStack(CWTFCItems.greenbeansCWTFC)}, 
				new FluidStack(CWTFCFluids.BROTH, 1000), new float[]{11F, 3F, 3F, 3F}, new FluidStack(CWTFCFluids.VEGETABLESOUP, 1000), 
				new ItemStack[]{}, new float[]{}, 60));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(new ItemStack[]{new ItemStack(CWTFCItems.tomatoCWTFC), new ItemStack(CWTFCItems.SeaSalt)}, 
				new FluidStack(CWTFCFluids.BROTH, 1000), new float[]{20F, 1F}, new FluidStack(CWTFCFluids.TOMATOSOUP, 1000), 
				new ItemStack[]{}, new float[]{}, 40));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(new ItemStack[]{new ItemStack(CWTFCItems.BoiledChicken), new ItemStack(CWTFCItems.carrotCWTFC), new ItemStack(CWTFCItems.onionCWTFC)}, 
				new FluidStack(CWTFCFluids.BROTH, 1000), new float[]{15F, 4F, 1F}, new FluidStack(CWTFCFluids.CHICKENSOUP, 1000), 
				new ItemStack[]{}, new float[]{}, 55));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(new ItemStack[]{new ItemStack(CWTFCItems.BoiledBeef), new ItemStack(CWTFCItems.carrotCWTFC), new ItemStack(CWTFCItems.potatoCWTFC), new ItemStack(CWTFCItems.onionCWTFC)}, 
				new FluidStack(CWTFCFluids.BROTH, 1000), new float[]{10F, 4F, 4F, 2F}, new FluidStack(CWTFCFluids.BEEFSTEW, 1000), 
				new ItemStack[]{}, new float[]{}, 120));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(new ItemStack[]{new ItemStack(CWTFCItems.BoiledVenison), new ItemStack(CWTFCItems.carrotCWTFC), new ItemStack(CWTFCItems.potatoCWTFC), new ItemStack(CWTFCItems.onionCWTFC)}, 
				new FluidStack(TFCFluids.FRESHWATER, 1000), new float[]{8F, 4F, 4F, 4F}, new FluidStack(CWTFCFluids.VENISONSTEW, 1000), 
				new ItemStack[]{}, new float[]{}, 90));
		
		cookingpotManager.addRecipe(new CookingPotRecipe(new ItemStack[]{new ItemStack(CWTFCItems.BoiledFish), new ItemStack(CWTFCItems.potatoCWTFC), new ItemStack(CWTFCItems.onionCWTFC), new ItemStack(CWTFCItems.wheatGroundCWTFC)}, 
				new FluidStack(CWTFCFluids.BROTH, 1000), new float[]{10F, 7F, 2F, 1F}, new FluidStack(CWTFCFluids.FISHCHOWDER, 1000), 
				new ItemStack[]{}, new float[]{}, 40));
	}
}
