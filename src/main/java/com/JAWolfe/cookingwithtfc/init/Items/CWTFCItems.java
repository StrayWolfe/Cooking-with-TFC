package com.JAWolfe.cookingwithtfc.init.Items;

import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.common.registry.ExistingSubstitutionException;
import net.minecraft.item.Item;

public class CWTFCItems 
{	
	//Fruits
	public static Item redAppleCWTFC;
	public static Item bananaCWTFC;
	public static Item orangeCWTFC;
	public static Item greenAppleCWTFC;
	public static Item lemonCWTFC;
	public static Item cherryCWTFC;
	public static Item peachCWTFC;
	public static Item plumCWTFC;
	
	public static Item wintergreenBerryCWTFC;
	public static Item blueberryCWTFC;
	public static Item raspberryCWTFC;
	public static Item strawberryCWTFC;
	public static Item blackberryCWTFC;
	public static Item bunchberryCWTFC;
	public static Item cranberryCWTFC;
	public static Item snowberryCWTFC;
	public static Item elderberryCWTFC;
	public static Item gooseberryCWTFC;
	public static Item cloudberryCWTFC;
	
	//Vegetables
	public static Item tomatoCWTFC;
	public static Item potatoCWTFC;
	public static Item onionCWTFC;
	public static Item cabbageCWTFC;
	public static Item garlicCWTFC;
	public static Item carrotCWTFC;
	public static Item greenbeansCWTFC;
	public static Item greenBellPepperCWTFC;
	public static Item yellowBellPepperCWTFC;
	public static Item redBellPepperCWTFC;
	public static Item squashCWTFC;
	public static Item seaWeedCWTFC;
	
	//Proteins
	public static Item porkchopRawCWTFC;
	public static Item fishRawCWTFC;
	public static Item beefRawCWTFC;
	public static Item chickenRawCWTFC;
	public static Item soybeanCWTFC;
	public static Item eggCWTFC;
	public static Item eggCookedCWTFC;
	public static Item calamariRawCWTFC;
	public static Item muttonRawCWTFC;
	public static Item venisonRawCWTFC;
	public static Item horseMeatRawCWTFC;
	
	//Grains
	public static Item barleyWholeCWTFC;
	public static Item oatWholeCWTFC;
	public static Item riceWholeCWTFC;
	public static Item ryeWholeCWTFC;
	public static Item wheatWholeCWTFC;
	public static Item maizeEarCWTFC;
	
	public static Item barleyGrainCWTFC;
	public static Item oatGrainCWTFC;
	public static Item riceGrainCWTFC;
	public static Item ryeGrainCWTFC;
	public static Item wheatGrainCWTFC;
	
	public static Item barleyGroundCWTFC;
	public static Item cornmealGroundCWTFC;
	public static Item oatGroundCWTFC;
	public static Item riceGroundCWTFC;
	public static Item ryeGroundCWTFC;
	public static Item wheatGroundCWTFC;
	
	public static Item barleyDoughCWTFC;
	public static Item cornmealDoughCWTFC;
	public static Item oatDoughCWTFC;
	public static Item riceDoughCWTFC;
	public static Item ryeDoughCWTFC;
	public static Item wheatDoughCWTFC;
	
	public static Item barleyBreadCWTFC;
	public static Item cornBreadCWTFC;
	public static Item oatBreadCWTFC;
	public static Item riceBreadCWTFC;
	public static Item ryeBreadCWTFC;
	public static Item wheatBreadCWTFC;	
	
	public static Item VeggySalad;
	public static Item PotatoSalad;
	public static Item FruitSalad;
	
	public static Item HamSandwich;
	public static Item ChickenSandwich;
	public static Item RoastBeefSandwich;
	public static Item SalmonSandwich;
	public static Item FriedEggSandwich;
	public static Item MuttonSandwich;
	public static Item VenisonSteakSandwich;
	
	public static void initialise() throws ExistingSubstitutionException
	{
		/*///////////////////////////////////////////////
		 * Remove Items being replaced from Creative Tabs
		 *///////////////////////////////////////////////
		
		//Fruit
		TFCItems.redApple.setCreativeTab(null);
		TFCItems.banana.setCreativeTab(null);
		TFCItems.orange.setCreativeTab(null);
		TFCItems.greenApple.setCreativeTab(null);
		TFCItems.lemon.setCreativeTab(null);
		TFCItems.cherry.setCreativeTab(null);
		TFCItems.peach.setCreativeTab(null);
		TFCItems.plum.setCreativeTab(null);
		
		TFCItems.wintergreenBerry.setCreativeTab(null);
		TFCItems.blueberry.setCreativeTab(null);
		TFCItems.raspberry.setCreativeTab(null);
		TFCItems.strawberry.setCreativeTab(null);
		TFCItems.blackberry.setCreativeTab(null);
		TFCItems.bunchberry.setCreativeTab(null);
		TFCItems.cranberry.setCreativeTab(null);
		TFCItems.snowberry.setCreativeTab(null);
		TFCItems.elderberry.setCreativeTab(null);
		TFCItems.gooseberry.setCreativeTab(null);
		TFCItems.cloudberry.setCreativeTab(null);
		
		//Vegetables
		TFCItems.tomato.setCreativeTab(null);
		TFCItems.potato.setCreativeTab(null);
		TFCItems.onion.setCreativeTab(null);
		TFCItems.cabbage.setCreativeTab(null);
		TFCItems.garlic.setCreativeTab(null);
		TFCItems.carrot.setCreativeTab(null);
		TFCItems.greenbeans.setCreativeTab(null);
		TFCItems.greenBellPepper.setCreativeTab(null);
		TFCItems.yellowBellPepper.setCreativeTab(null);
		TFCItems.redBellPepper.setCreativeTab(null);
		TFCItems.squash.setCreativeTab(null);
		TFCItems.seaWeed.setCreativeTab(null);
		
		//Proteins
		TFCItems.porkchopRaw.setCreativeTab(null);
		TFCItems.fishRaw.setCreativeTab(null);
		TFCItems.beefRaw.setCreativeTab(null);
		TFCItems.chickenRaw.setCreativeTab(null);
		TFCItems.soybean.setCreativeTab(null);
		TFCItems.egg.setCreativeTab(null);
		TFCItems.eggCooked.setCreativeTab(null);
		TFCItems.calamariRaw.setCreativeTab(null);
		TFCItems.muttonRaw.setCreativeTab(null);
		TFCItems.venisonRaw.setCreativeTab(null);
		TFCItems.horseMeatRaw.setCreativeTab(null);
		
		//Grains
		TFCItems.barleyWhole.setCreativeTab(null);
		TFCItems.oatWhole.setCreativeTab(null);
		TFCItems.riceWhole.setCreativeTab(null);
		TFCItems.ryeWhole.setCreativeTab(null);
		TFCItems.wheatWhole.setCreativeTab(null);
		TFCItems.maizeEar.setCreativeTab(null);
		
		TFCItems.barleyGrain.setCreativeTab(null);
		TFCItems.oatGrain.setCreativeTab(null);
		TFCItems.riceGrain.setCreativeTab(null);
		TFCItems.ryeGrain.setCreativeTab(null);
		TFCItems.wheatGrain.setCreativeTab(null);
		
		TFCItems.barleyGround.setCreativeTab(null);
		TFCItems.cornmealGround.setCreativeTab(null);
		TFCItems.oatGround.setCreativeTab(null);
		TFCItems.riceGround.setCreativeTab(null);
		TFCItems.ryeGround.setCreativeTab(null);
		TFCItems.wheatGround.setCreativeTab(null);
		
		TFCItems.barleyDough.setCreativeTab(null);
		TFCItems.cornmealDough.setCreativeTab(null);
		TFCItems.oatDough.setCreativeTab(null);
		TFCItems.riceDough.setCreativeTab(null);
		TFCItems.ryeDough.setCreativeTab(null);
		TFCItems.wheatDough.setCreativeTab(null);
		
		TFCItems.barleyBread.setCreativeTab(null);
		TFCItems.cornBread.setCreativeTab(null);
		TFCItems.oatBread.setCreativeTab(null);
		TFCItems.riceBread.setCreativeTab(null);
		TFCItems.ryeBread.setCreativeTab(null);
		TFCItems.wheatBread.setCreativeTab(null);
		
		//Foods not intended to be eaten
		((ItemFoodTFC)TFCItems.olive).edible = false;
		((ItemFoodTFC)TFCItems.sugarcane).edible = false;
		((ItemFoodTFC)TFCItems.sugar).edible = false;
		
		//Setup Items
		CWTFCItemSetup.ItemSetup();
		
		//Register Items
		CWTFCItemRegistry.registerItems();
	}
}
