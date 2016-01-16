package com.JAWolfe.cookingwithtfc.crafting.Recipes;

import com.JAWolfe.cookingwithtfc.crafting.FoodManager;
import com.JAWolfe.cookingwithtfc.crafting.FoodRecipe;
import com.JAWolfe.cookingwithtfc.init.CWTFCBlocks;
import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.JAWolfe.cookingwithtfc.items.ItemTFCMeatTransform;
import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CWTFCFoodRecipes
{
	public static void Recipes()
	{
		FoodManager foodManager = FoodManager.getInstance();
		
		//Dough
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryJug, 1, 2), new ItemStack(CWTFCBlocks.mixingBowl, 1, 1)}, 
				new Item[]{CWTFCItems.barleyGroundCWTFC}, new float[]{1.0F}, new ItemStack(CWTFCItems.barleyDoughCWTFC)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryJug, 1, 2), new ItemStack(CWTFCBlocks.mixingBowl, 1, 1)}, 
				new Item[]{CWTFCItems.cornmealGroundCWTFC}, new float[]{1.0F}, new ItemStack(CWTFCItems.cornmealDoughCWTFC)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryJug, 1, 2), new ItemStack(CWTFCBlocks.mixingBowl, 1, 1)}, 
				new Item[]{CWTFCItems.oatGroundCWTFC}, new float[]{1.0F}, new ItemStack(CWTFCItems.oatDoughCWTFC)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryJug, 1, 2), new ItemStack(CWTFCBlocks.mixingBowl, 1, 1)}, 
				new Item[]{CWTFCItems.riceGroundCWTFC}, new float[]{1.0F}, new ItemStack(CWTFCItems.riceDoughCWTFC)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryJug, 1, 2), new ItemStack(CWTFCBlocks.mixingBowl, 1, 1)}, 
				new Item[]{CWTFCItems.ryeGroundCWTFC}, new float[]{1.0F}, new ItemStack(CWTFCItems.ryeDoughCWTFC)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryJug, 1, 2), new ItemStack(CWTFCBlocks.mixingBowl, 1, 1)}, 
				new Item[]{CWTFCItems.wheatGroundCWTFC}, new float[]{1.0F}, new ItemStack(CWTFCItems.wheatDoughCWTFC)));
		
		//Salads
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new Item[]{CWTFCItems.cabbageCWTFC, CWTFCItems.tomatoCWTFC, CWTFCItems.yellowBellPepperCWTFC, CWTFCItems.onionCWTFC},
				new float[]{0.5F, 0.3F, 0.1F, 0.1F}, new ItemStack(CWTFCItems.VeggySalad)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new Item[]{CWTFCItems.cabbageCWTFC, CWTFCItems.tomatoCWTFC, CWTFCItems.redBellPepperCWTFC, CWTFCItems.onionCWTFC},
				new float[]{0.5F, 0.3F, 0.1F, 0.1F}, new ItemStack(CWTFCItems.VeggySalad)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new Item[]{CWTFCItems.cabbageCWTFC, CWTFCItems.tomatoCWTFC, CWTFCItems.greenBellPepperCWTFC, CWTFCItems.onionCWTFC},
				new float[]{0.5F, 0.3F, 0.1F, 0.1F}, new ItemStack(CWTFCItems.VeggySalad)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new Item[]{CWTFCItems.seaWeedCWTFC, CWTFCItems.tomatoCWTFC, CWTFCItems.yellowBellPepperCWTFC, CWTFCItems.onionCWTFC},
				new float[]{0.5F, 0.3F, 0.1F, 0.1F}, new ItemStack(CWTFCItems.VeggySalad)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new Item[]{CWTFCItems.seaWeedCWTFC, CWTFCItems.tomatoCWTFC, CWTFCItems.redBellPepperCWTFC, CWTFCItems.onionCWTFC},
				new float[]{0.5F, 0.3F, 0.1F, 0.1F}, new ItemStack(CWTFCItems.VeggySalad)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new Item[]{CWTFCItems.seaWeedCWTFC, CWTFCItems.tomatoCWTFC, CWTFCItems.greenBellPepperCWTFC, CWTFCItems.onionCWTFC},
				new float[]{0.5F, 0.3F, 0.1F, 0.1F}, new ItemStack(CWTFCItems.VeggySalad)));		
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new Item[]{CWTFCItems.potatoCWTFC, CWTFCItems.onionCWTFC, CWTFCItems.eggCookedCWTFC},
				new float[]{0.70F, 0.2F, 0.1F}, new ItemStack(CWTFCItems.PotatoSalad)));		
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new Item[]{CWTFCItems.redAppleCWTFC, CWTFCItems.bananaCWTFC, CWTFCItems.blueberryCWTFC, CWTFCItems.strawberryCWTFC},
				new float[]{0.25F, 0.25F, 0.25F, 0.25F}, new ItemStack(CWTFCItems.FruitSalad)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new Item[]{CWTFCItems.blueberryCWTFC, CWTFCItems.raspberryCWTFC, CWTFCItems.strawberryCWTFC, CWTFCItems.blackberryCWTFC},
				new float[]{0.25F, 0.25F, 0.25F, 0.25F}, new ItemStack(CWTFCItems.FruitSalad)));
		foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.potteryBowl, 1, 1), new ItemStack(TFCItems.stoneKnife, 1)}, 
				new Item[]{CWTFCItems.greenAppleCWTFC, CWTFCItems.orangeCWTFC, CWTFCItems.cherryCWTFC, CWTFCItems.bunchberryCWTFC},
				new float[]{0.25F, 0.25F, 0.25F, 0.25F}, new ItemStack(CWTFCItems.FruitSalad)));
		
		Item[] breads = new Item[]{CWTFCItems.barleyBreadCWTFC,CWTFCItems.cornBreadCWTFC,CWTFCItems.oatBreadCWTFC,
				CWTFCItems.riceBreadCWTFC,CWTFCItems.ryeBreadCWTFC,CWTFCItems.wheatBreadCWTFC};
		
		//Ham Sandwiches
		for(int i = 0; i < breads.length; i++)
		{
			foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.stoneKnife, 1)}, 
					new Item[]{breads[i], CWTFCItems.porkchopRawCWTFC, CWTFCItems.cabbageCWTFC, CWTFCItems.tomatoCWTFC, CWTFCItems.onionCWTFC},
					new float[]{0.5F, 0.2F, 0.15F, 0.1F, 0.05F}, new ItemStack(CWTFCItems.HamSandwich, 1, i)));
		}
		
		//Chicken Sandwich
		for(int i = 0; i < breads.length; i++)
		{
			foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.stoneKnife, 1)}, 
					new Item[]{breads[i], CWTFCItems.chickenRawCWTFC, CWTFCItems.cabbageCWTFC, CWTFCItems.tomatoCWTFC, CWTFCItems.onionCWTFC},
					new float[]{0.5F, 0.2F, 0.15F, 0.1F, 0.05F}, new ItemStack(CWTFCItems.ChickenSandwich, 1, i)));
		}
		
		//Roast Beef Sandwich
		for(int i = 0; i < breads.length; i++)
		{
			foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.stoneKnife, 1)}, 
					new Item[]{breads[i], CWTFCItems.beefRawCWTFC, CWTFCItems.cabbageCWTFC, CWTFCItems.tomatoCWTFC, CWTFCItems.onionCWTFC},
					new float[]{0.5F, 0.2F, 0.15F, 0.1F, 0.05F}, new ItemStack(CWTFCItems.RoastBeefSandwich, 1, i)));
		}
		
		//Salmon Sandwich
		for(int i = 0; i < breads.length; i++)
		{
			foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.stoneKnife, 1)}, 
					new Item[]{breads[i], CWTFCItems.fishRawCWTFC, CWTFCItems.cabbageCWTFC, CWTFCItems.tomatoCWTFC, CWTFCItems.onionCWTFC},
					new float[]{0.5F, 0.2F, 0.15F, 0.1F, 0.05F}, new ItemStack(CWTFCItems.SalmonSandwich, 1, i)));
		}
		
		//Fried Egg Sandwich
		for(int i = 0; i < breads.length; i++)
		{
			foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.stoneKnife, 1)}, 
					new Item[]{breads[i], CWTFCItems.eggCookedCWTFC, CWTFCItems.cabbageCWTFC, CWTFCItems.tomatoCWTFC, CWTFCItems.onionCWTFC},
					new float[]{0.5F, 0.2F, 0.15F, 0.1F, 0.05F}, new ItemStack(CWTFCItems.FriedEggSandwich, 1, i)));
		}
		
		//Mutton Sandwich
		for(int i = 0; i < breads.length; i++)
		{
			foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.stoneKnife, 1)}, 
					new Item[]{breads[i], CWTFCItems.muttonRawCWTFC, CWTFCItems.cabbageCWTFC, CWTFCItems.tomatoCWTFC, CWTFCItems.onionCWTFC},
					new float[]{0.5F, 0.2F, 0.15F, 0.1F, 0.05F}, new ItemStack(CWTFCItems.MuttonSandwich, 1, i)));
		}
		
		//Venison Steak Sandwich
		for(int i = 0; i < breads.length; i++)
		{
			foodManager.addRecipe(new FoodRecipe(new ItemStack[]{new ItemStack(TFCItems.stoneKnife, 1)}, 
					new Item[]{breads[i], CWTFCItems.venisonRawCWTFC, CWTFCItems.cabbageCWTFC, CWTFCItems.tomatoCWTFC, CWTFCItems.onionCWTFC},
					new float[]{0.5F, 0.2F, 0.15F, 0.1F, 0.05F}, new ItemStack(CWTFCItems.VenisonSteakSandwich, 1, i)));
		}
		
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.beefRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.beefRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.chickenRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.chickenRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.horseMeatRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.horseMeatRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.fishRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.fishRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.calamariRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.calamariRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.porkchopRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.porkchopRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.muttonRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.muttonRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
		GameRegistry.addShapelessRecipe(ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.venisonRawCWTFC, 1)), ItemTFCMeatTransform.createTag(new ItemStack(CWTFCItems.venisonRawCWTFC, 1)), new ItemStack(TFCItems.powder, 1, 9));
	}
}
