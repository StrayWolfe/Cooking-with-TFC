package com.JAWolfe.cookingwithtfc.init.Items;

import java.util.ArrayList;

import com.JAWolfe.cookingwithtfc.items.*;
import com.JAWolfe.cookingwithtfc.references.ConstantsCWTFC;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Food.ItemEgg;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Items.Tools.ItemCustomBucketMilk;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Enums.EnumSize;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class CWTFCItemSetup extends CWTFCItems
{
	public static void ItemSetup()
	{
		FoodItemSetup();
		
		RegularItemSetup();
	}
	
	public static void FoodItemSetup()
	{
		subfoodList = new ArrayList<ItemTerra>();
		
		float snackSize = ConstantsCWTFC.SNACK_SIZE;
		float mealSize = ConstantsCWTFC.MEAL_SIZE;
		
		eggCWTFC = new ItemEgg().setSize(EnumSize.SMALL).setUnlocalizedName("egg").setTextureName("egg").setCreativeTab(TFCTabs.TFC_FOODS);
		
		//Dairy
		cheeseCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Dairy, 0, 10, 20, 0, 35, snackSize).setDecayRate(0.5f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName(TFCItems.cheese.getUnlocalizedName().substring(5));
		woodenBucketMilkCWTFC = new ItemCustomBucketMilk().setUnlocalizedName("Wooden Bucket Milk").setContainerItem(TFCItems.woodenBucketEmpty).setCreativeTab(TFCTabs.TFC_FOODS);
		
		//Proteins
		porkchopRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Porkchop Raw");
		porkchopCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, true, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Porkchop Cooked");
		fishRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, true, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Fish Raw");
		fishCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, true, true, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Fish Cooked");
		beefRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 50, false, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Beef Raw");
		beefCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 50, true, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Beef Cooked");
		chickenRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Chicken Raw");
		chickenCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, true, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Chicken Cooked");
		soybeanCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 10, 0, 0, 0, 40, snackSize).setUnlocalizedName(TFCItems.soybean.getUnlocalizedName().substring(5));
		eggCookedCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 25, mealSize).setDecayRate(2.5f).setUnlocalizedName(TFCItems.eggCooked.getUnlocalizedName().substring(5));
		calamariRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 20, 0, 35, false, false, mealSize).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setDecayRate(4.0f).setUnlocalizedName("Calamari Raw");
		calamariCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 20, 0, 35, true, false, mealSize).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setDecayRate(4.0f).setUnlocalizedName("Calamari Cooked");
		muttonRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Mutton Raw");
		muttonCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, true, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Mutton Cooked");
		venisonRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 5, 0, 0, 0, 50, false, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Venison Raw");
		venisonCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 5, 0, 0, 0, 50, true, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Venison Cooked");
		horseMeatRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("HorseMeat Raw");
		horseMeatCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, true, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("HorseMeat Cooked");
				
		//Vegetables
		tomatoCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 30, 5, 0, 0, 50, snackSize).setUnlocalizedName(TFCItems.tomato.getUnlocalizedName().substring(5));
		potatoCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 0, 0, 10, 15, 20, snackSize).setUnlocalizedName(TFCItems.potato.getUnlocalizedName().substring(5));
		onionCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 10, 25, 0, 0, 20, snackSize)
		{
			@Override
			public void registerIcons(IIconRegister registerer)
			{
				super.registerIcons(registerer);
				this.hasSubtypes = true;
				this.metaIcons = new IIcon[2];
				this.metaIcons[0] = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + TFCItems.onion.getUnlocalizedName().replace("item.", ""));
				this.metaIcons[1] = registerer.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + "Rutabaga");
			}

			@Override
			public IIcon getIconFromDamage(int i)
			{
				if(i == 1)
					return this.metaIcons[1];
				return super.getIconFromDamage(i);
			}
		}.setUnlocalizedName(TFCOptions.onionsAreGross ? "Rutabaga" : TFCItems.onion.getUnlocalizedName().substring(5));
		cabbageCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 30, snackSize).setUnlocalizedName(TFCItems.cabbage.getUnlocalizedName().substring(5));
		garlicCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 0, 0, 0, 10, 20, snackSize).setUnlocalizedName(TFCItems.garlic.getUnlocalizedName().substring(5));
		carrotCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, snackSize).setUnlocalizedName(TFCItems.carrot.getUnlocalizedName().substring(5));
		greenbeansCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, snackSize).setUnlocalizedName(TFCItems.greenbeans.getUnlocalizedName().substring(5));
		greenBellPepperCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 20, snackSize).setUnlocalizedName(TFCItems.greenBellPepper.getUnlocalizedName().substring(5));
		yellowBellPepperCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 15, 0, 0, 0, 20, snackSize).setUnlocalizedName(TFCItems.yellowBellPepper.getUnlocalizedName().substring(5));
		redBellPepperCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, snackSize).setUnlocalizedName(TFCItems.redBellPepper.getUnlocalizedName().substring(5));
		squashCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, snackSize).setUnlocalizedName(TFCItems.squash.getUnlocalizedName().substring(5));
		seaWeedCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 0, 0, 10, 10, 10, snackSize).setUnlocalizedName(TFCItems.seaWeed.getUnlocalizedName().substring(5));
		
		//Fruit
		redAppleCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 25, 5, 0, 10, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.redApple.getUnlocalizedName().substring(5));
		bananaCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 5, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.banana.getUnlocalizedName().substring(5));
		orangeCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 50, 30, 0, 10, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.orange.getUnlocalizedName().substring(5));
		greenAppleCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 15, 0, 10, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.greenApple.getUnlocalizedName().substring(5));
		lemonCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 50, 0, 10, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.lemon.getUnlocalizedName().substring(5));
		oliveCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 10, 0, 3, 10, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.olive.getUnlocalizedName().substring(5));
		cherryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 5, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.cherry.getUnlocalizedName().substring(5));
		peachCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 25, 10, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.peach.getUnlocalizedName().substring(5));
		plumCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 20, 15, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.plum.getUnlocalizedName().substring(5));

		wintergreenBerryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 0, 0, 20, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.wintergreenBerry.getUnlocalizedName().substring(5));
		blueberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 20, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.blueberry.getUnlocalizedName().substring(5));
		raspberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 35, 15, 0, 5, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.raspberry.getUnlocalizedName().substring(5));
		strawberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 5, 0, 5, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.strawberry.getUnlocalizedName().substring(5));
		blackberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 30, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.blackberry.getUnlocalizedName().substring(5));
		bunchberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 20, 5, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.bunchberry.getUnlocalizedName().substring(5));
		cranberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 5, 0, 45, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.cranberry.getUnlocalizedName().substring(5));
		snowberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 10, 0, 0, 90, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.snowberry.getUnlocalizedName().substring(5));
		elderberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 20, 40, 0, 10, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.elderberry.getUnlocalizedName().substring(5));
		gooseberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 20, 40, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.gooseberry.getUnlocalizedName().substring(5));
		cloudberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 40, 40, 0, 30, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.cloudberry.getUnlocalizedName().substring(5));
		
		//Grains
		wheatGrainCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 0.5f, false, true).setUnlocalizedName("Wheat Grain");
		barleyGrainCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 10, 20, snackSize, 0.5f, false, true).setUnlocalizedName("Barley Grain");
		oatGrainCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 0.5f, false, true).setUnlocalizedName("Oat Grain");
		ryeGrainCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 15, 0, 5, 20, snackSize, 0.5f, false, true).setUnlocalizedName("Rye Grain");
		riceGrainCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 0.5f, false, true).setUnlocalizedName("Rice Grain");
		maizeEarCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 25, 0, 0, 5, 20, snackSize).setUnlocalizedName("Maize Ear");

		wheatWholeCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Wheat Whole");
		barleyWholeCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 10, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Barley Whole");
		oatWholeCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Oat Whole");
		ryeWholeCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 15, 0, 5, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Rye Whole");
		riceWholeCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Rice Whole");

		wheatGroundCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Wheat Ground");
		barleyGroundCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Barley Ground");
		oatGroundCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Oat Ground");
		ryeGroundCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 15, 0, 0, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Rye Ground");
		riceGroundCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Rice Ground");
		cornmealGroundCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 25, 0, 0, 0, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Cornmeal Ground");

		wheatDoughCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, snackSize, 1.0F, false, true).setUnlocalizedName("Wheat Dough");
		barleyDoughCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 1.0F, false, true).setUnlocalizedName("Barley Dough");
		oatDoughCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, snackSize, 1.0F, false, true).setUnlocalizedName("Oat Dough");
		ryeDoughCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 15, 0, 0, 20, snackSize, 1.0F, false, true).setUnlocalizedName("Rye Dough");
		riceDoughCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, snackSize, 1.0F, false, true).setUnlocalizedName("Rice Dough");
		cornmealDoughCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 25, 0, 0, 0, 20, snackSize, 1.0F, false, true).setUnlocalizedName("Cornmeal Dough");

		wheatBreadCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, mealSize).setUnlocalizedName("Wheat Bread");
		barleyBreadCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, mealSize).setUnlocalizedName("Barley Bread");
		oatBreadCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, mealSize).setUnlocalizedName("Oat Bread");
		ryeBreadCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 15, 0, 0, 20, mealSize).setUnlocalizedName("Rye Bread");
		riceBreadCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, mealSize).setUnlocalizedName("Rice Bread");
		cornBreadCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 25, 0, 0, 0, 20, mealSize).setUnlocalizedName("Corn Bread");
		
		sugarcaneCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.None, 30, 0, 0, 0, 0, snackSize, 0.75F, false, false).setFolder("plants/").setUnlocalizedName("Sugarcane");
		sugarCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.None, 30, 0, 0, 0, 0, snackSize).setDecayRate(0.01F).setUnlocalizedName("Sugar");
		
		//Salads
		VeggySalad = new ItemTFCMealTransform(17, 5, 0, 0, 34, mealSize, 20, SkillRank.Novice, "VeggySalad").setIconPath("Salad0");
		PotatoSalad = new ItemTFCMealTransform(2, 5, 7, 11, 21, mealSize, 20, SkillRank.Novice, "PotatoSalad").setIconPath("Salad2");
		FruitSalad = new ItemTFCMealTransform(31, 11, 0, 5, 0, mealSize, 20, SkillRank.Novice, "FruitSalad").setIconPath("Salad3");
		
		//Soups & Stews
		VegetableSoup = new ItemTFCMealTransform(95, 5, 0, 5, 140, mealSize, 10, SkillRank.Novice, "VegetableSoup").hasCustomIcon(true).setHasBowl(true);
		TomatoSoup = new ItemTFCMealTransform(30, 5, 40, 0, 80, mealSize, 10, SkillRank.Novice, "TomatoSoup").hasCustomIcon(true).setHasBowl(true);
		ChickenSoup = new ItemTFCMealTransform(30, 25, 0, 0, 110, mealSize, 10, SkillRank.Novice, "ChickenSoup").hasCustomIcon(true).setHasBowl(true);
		BeefStew = new ItemTFCMealTransform(30, 25, 10, 15, 110, mealSize, 10, SkillRank.Novice, "BeefStew").hasCustomIcon(true).setHasBowl(true);
		VenisonStew = new ItemTFCMealTransform(35, 25, 10, 15, 110, mealSize, 10, SkillRank.Novice, "VenisonStew").hasCustomIcon(true).setHasBowl(true);
		FishChowder = new ItemTFCMealTransform(20, 25, 10, 15, 100, mealSize, 10, SkillRank.Novice, "FishChowder").hasCustomIcon(true).setHasBowl(true);
		
		//Sandwiches
		HamSandwich = new ItemTFCSandwichTransform(11, 3, 0, 0, 29, mealSize, 10, SkillRank.Novice, "HamSandwich");		
		ChickenSandwich = new ItemTFCSandwichTransform(11, 3, 0, 0, 29, mealSize, 10, SkillRank.Novice, "ChickenSandwich");
		RoastBeefSandwich = new ItemTFCSandwichTransform(11, 3, 0, 0, 31, mealSize, 10, SkillRank.Novice, "RoastBeefSandwich");
		SalmonSandwich = new ItemTFCSandwichTransform(11, 3, 0, 0, 29, mealSize, 10, SkillRank.Novice, "SalmonSandwich");
		FriedEggSandwich = new ItemTFCSandwichTransform(11, 3, 0, 0, 26, mealSize, 10, SkillRank.Novice, "FriedEggSandwich");
		MuttonSandwich = new ItemTFCSandwichTransform(11, 3, 0, 0, 29, mealSize, 10, SkillRank.Novice, "MuttonSandwich");
		VenisonSteakSandwich = new ItemTFCSandwichTransform(11, 3, 0, 0, 31, mealSize, 10, SkillRank.Novice, "VenisonSteakSandwich");
		
		Broth = new ItemTFCAdjutableFood(EnumFoodGroup.Protein, 0, 0, 0, 0, 30, snackSize, 20).setIsJug(true).setUnlocalizedName("Broth");
		BoiledChicken = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, mealSize).setCustomIcon(true).setUnlocalizedName("BoiledChicken");
		BoiledBeef = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 50, mealSize).setCustomIcon(true).setUnlocalizedName("BoiledBeef");
		BoiledFish = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, mealSize).setCustomIcon(true).setUnlocalizedName("BoiledFish");
		BoiledPork = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, mealSize).setCustomIcon(true).setUnlocalizedName("BoiledPork");
		BoiledVenison = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 5, 0, 0, 0, 50, mealSize).setCustomIcon(true).setUnlocalizedName("BoiledVenison");
	
		SeaSalt = new ItemTFCFoodTransform(EnumFoodGroup.None, 0, 0, 40, 0, 0, snackSize, 0.001F, false, false).setCustomIcon(true).setUnlocalizedName("SeaSalt");
		RockSalt = new ItemTFCSalt(EnumFoodGroup.None, 0, 0, 40, 0, 0, snackSize, 0.001F, false, false).setUnlocalizedName("Salt");
	}
	
	public static void RegularItemSetup()
	{
		ClayCookingPot = new ItemClayCookingPot();
	}
}
