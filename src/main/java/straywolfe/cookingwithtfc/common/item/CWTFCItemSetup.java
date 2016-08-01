package straywolfe.cookingwithtfc.common.item;

import java.util.ArrayList;

import com.bioxx.tfc.Reference;
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
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.lib.Settings;

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
		
		float snackSize = Settings.SNACK_SIZE;
		float mealSize = Settings.MEAL_SIZE;
		
		eggCWTFC = new ItemEgg().setSize(EnumSize.SMALL).setUnlocalizedName("egg").setTextureName("egg").setCreativeTab(null);
		
		//Dairy
		cheeseCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Dairy, 0, 10, 20, 0, 35, snackSize).setDecayRate(0.5f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName(TFCItems.cheese.getUnlocalizedName().substring(5)).setCreativeTab(null);
		woodenBucketMilkCWTFC = new ItemCustomBucketMilk().setUnlocalizedName("Wooden Bucket Milk").setContainerItem(TFCItems.woodenBucketEmpty).setCreativeTab(null);
		
		//Proteins
		porkchopRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Porkchop Raw").setCreativeTab(null);
		porkchopCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, true, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Porkchop Cooked").setCreativeTab(null);
		fishRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, true, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Fish Raw").setCreativeTab(null);
		fishCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, true, true, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Fish Cooked").setCreativeTab(null);
		beefRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 50, false, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Beef Raw").setCreativeTab(null);
		beefCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 50, true, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Beef Cooked").setCreativeTab(null);
		chickenRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Chicken Raw").setCreativeTab(null);
		chickenCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, true, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Chicken Cooked").setCreativeTab(null);
		soybeanCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 10, 0, 0, 0, 40, snackSize).setUnlocalizedName(TFCItems.soybean.getUnlocalizedName().substring(5)).setCreativeTab(null);
		eggCookedCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 25, mealSize).setDecayRate(2.5f).setUnlocalizedName(TFCItems.eggCooked.getUnlocalizedName().substring(5)).setCreativeTab(null);
		calamariRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 20, 0, 35, false, false, mealSize).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setDecayRate(4.0f).setUnlocalizedName("Calamari Raw").setCreativeTab(null);
		calamariCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 20, 0, 35, true, false, mealSize).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setDecayRate(4.0f).setUnlocalizedName("Calamari Cooked").setCreativeTab(null);
		muttonRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Mutton Raw").setCreativeTab(null);
		muttonCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, true, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Mutton Cooked").setCreativeTab(null);
		venisonRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 5, 0, 0, 0, 50, false, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Venison Raw").setCreativeTab(null);
		venisonCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 5, 0, 0, 0, 50, true, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Venison Cooked").setCreativeTab(null);
		horseMeatRawCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("HorseMeat Raw").setCreativeTab(null);
		horseMeatCookedCWTFC = new ItemTFCMeatTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, true, false, mealSize).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("HorseMeat Cooked").setCreativeTab(null);
				
		//Vegetables
		tomatoCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 30, 5, 0, 0, 50, snackSize).setUnlocalizedName(TFCItems.tomato.getUnlocalizedName().substring(5)).setCreativeTab(null);
		potatoCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 0, 0, 10, 15, 20, snackSize).setUnlocalizedName(TFCItems.potato.getUnlocalizedName().substring(5)).setCreativeTab(null);
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
		}.setUnlocalizedName(TFCOptions.onionsAreGross ? "Rutabaga" : TFCItems.onion.getUnlocalizedName().substring(5)).setCreativeTab(null);
		cabbageCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 30, snackSize).setUnlocalizedName(TFCItems.cabbage.getUnlocalizedName().substring(5)).setCreativeTab(null);
		garlicCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 0, 0, 0, 10, 20, snackSize).setUnlocalizedName(TFCItems.garlic.getUnlocalizedName().substring(5)).setCreativeTab(null);
		carrotCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, snackSize).setUnlocalizedName(TFCItems.carrot.getUnlocalizedName().substring(5)).setCreativeTab(null);
		greenbeansCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, snackSize).setUnlocalizedName(TFCItems.greenbeans.getUnlocalizedName().substring(5)).setCreativeTab(null);
		greenBellPepperCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 20, snackSize).setUnlocalizedName(TFCItems.greenBellPepper.getUnlocalizedName().substring(5)).setCreativeTab(null);
		yellowBellPepperCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 15, 0, 0, 0, 20, snackSize).setUnlocalizedName(TFCItems.yellowBellPepper.getUnlocalizedName().substring(5)).setCreativeTab(null);
		redBellPepperCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, snackSize).setUnlocalizedName(TFCItems.redBellPepper.getUnlocalizedName().substring(5)).setCreativeTab(null);
		squashCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, snackSize).setUnlocalizedName(TFCItems.squash.getUnlocalizedName().substring(5)).setCreativeTab(null);
		seaWeedCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 0, 0, 10, 10, 10, snackSize).setUnlocalizedName(TFCItems.seaWeed.getUnlocalizedName().substring(5)).setCreativeTab(null);
		
		//Fruit
		redAppleCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 25, 5, 0, 10, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.redApple.getUnlocalizedName().substring(5)).setCreativeTab(null);
		bananaCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 5, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.banana.getUnlocalizedName().substring(5)).setCreativeTab(null);
		orangeCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 50, 30, 0, 10, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.orange.getUnlocalizedName().substring(5)).setCreativeTab(null);
		greenAppleCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 15, 0, 10, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.greenApple.getUnlocalizedName().substring(5)).setCreativeTab(null);
		lemonCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 50, 0, 10, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.lemon.getUnlocalizedName().substring(5)).setCreativeTab(null);
		oliveCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 10, 0, 3, 10, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.olive.getUnlocalizedName().substring(5)).setCreativeTab(null);
		cherryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 5, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.cherry.getUnlocalizedName().substring(5)).setCreativeTab(null);
		peachCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 25, 10, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.peach.getUnlocalizedName().substring(5)).setCreativeTab(null);
		plumCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 20, 15, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.plum.getUnlocalizedName().substring(5)).setCreativeTab(null);

		wintergreenBerryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 0, 0, 20, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.wintergreenBerry.getUnlocalizedName().substring(5)).setCreativeTab(null);
		blueberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 20, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.blueberry.getUnlocalizedName().substring(5)).setCreativeTab(null);
		raspberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 35, 15, 0, 5, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.raspberry.getUnlocalizedName().substring(5)).setCreativeTab(null);
		strawberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 5, 0, 5, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.strawberry.getUnlocalizedName().substring(5)).setCreativeTab(null);
		blackberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 30, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.blackberry.getUnlocalizedName().substring(5)).setCreativeTab(null);
		bunchberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 20, 5, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.bunchberry.getUnlocalizedName().substring(5)).setCreativeTab(null);
		cranberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 5, 0, 45, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.cranberry.getUnlocalizedName().substring(5)).setCreativeTab(null);
		snowberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 10, 0, 0, 90, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.snowberry.getUnlocalizedName().substring(5)).setCreativeTab(null);
		elderberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 20, 40, 0, 10, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.elderberry.getUnlocalizedName().substring(5)).setCreativeTab(null);
		gooseberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 20, 40, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.gooseberry.getUnlocalizedName().substring(5)).setCreativeTab(null);
		cloudberryCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 40, 40, 0, 30, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName(TFCItems.cloudberry.getUnlocalizedName().substring(5)).setCreativeTab(null);
		
		//Grains
		wheatGrainCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 0.5f, false, true).setUnlocalizedName("Wheat Grain").setCreativeTab(null);
		barleyGrainCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 10, 20, snackSize, 0.5f, false, true).setUnlocalizedName("Barley Grain").setCreativeTab(null);
		oatGrainCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 0.5f, false, true).setUnlocalizedName("Oat Grain").setCreativeTab(null);
		ryeGrainCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 15, 0, 5, 20, snackSize, 0.5f, false, true).setUnlocalizedName("Rye Grain").setCreativeTab(null);
		riceGrainCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 0.5f, false, true).setUnlocalizedName("Rice Grain").setCreativeTab(null);
		maizeEarCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 25, 0, 0, 5, 20, snackSize).setUnlocalizedName("Maize Ear").setCreativeTab(null);

		wheatWholeCWTFC = new ItemWholeGrain(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Wheat Whole").setCreativeTab(null);
		barleyWholeCWTFC = new ItemWholeGrain(EnumFoodGroup.Grain, 10, 0, 0, 10, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Barley Whole").setCreativeTab(null);
		oatWholeCWTFC = new ItemWholeGrain(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Oat Whole").setCreativeTab(null);
		ryeWholeCWTFC = new ItemWholeGrain(EnumFoodGroup.Grain, 10, 15, 0, 5, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Rye Whole").setCreativeTab(null);
		riceWholeCWTFC = new ItemWholeGrain(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Rice Whole").setCreativeTab(null);

		wheatGroundCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Wheat Ground").setCreativeTab(null);
		barleyGroundCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Barley Ground").setCreativeTab(null);
		oatGroundCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Oat Ground").setCreativeTab(null);
		ryeGroundCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 15, 0, 0, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Rye Ground").setCreativeTab(null);
		riceGroundCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Rice Ground").setCreativeTab(null);
		cornmealGroundCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 25, 0, 0, 0, 20, snackSize, 1.0F, false, true).setFolder("food/").setUnlocalizedName("Cornmeal Ground").setCreativeTab(null);

		wheatDoughCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, snackSize, 1.0F, false, true).setUnlocalizedName("Wheat Dough").setCreativeTab(null);
		barleyDoughCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, snackSize, 1.0F, false, true).setUnlocalizedName("Barley Dough").setCreativeTab(null);
		oatDoughCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, snackSize, 1.0F, false, true).setUnlocalizedName("Oat Dough").setCreativeTab(null);
		ryeDoughCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 15, 0, 0, 20, snackSize, 1.0F, false, true).setUnlocalizedName("Rye Dough").setCreativeTab(null);
		riceDoughCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, snackSize, 1.0F, false, true).setUnlocalizedName("Rice Dough").setCreativeTab(null);
		cornmealDoughCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.Grain, 25, 0, 0, 0, 20, snackSize, 1.0F, false, true).setUnlocalizedName("Cornmeal Dough").setCreativeTab(null);

		wheatBreadCWTFC = new ItemBread(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, mealSize).setUnlocalizedName("Wheat Bread").setCreativeTab(null);
		barleyBreadCWTFC = new ItemBread(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, mealSize).setUnlocalizedName("Barley Bread").setCreativeTab(null);
		oatBreadCWTFC = new ItemBread(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, mealSize).setUnlocalizedName("Oat Bread").setCreativeTab(null);
		ryeBreadCWTFC = new ItemBread(EnumFoodGroup.Grain, 10, 15, 0, 0, 20, mealSize).setUnlocalizedName("Rye Bread").setCreativeTab(null);
		riceBreadCWTFC = new ItemBread(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, mealSize).setUnlocalizedName("Rice Bread").setCreativeTab(null);
		cornBreadCWTFC = new ItemBread(EnumFoodGroup.Grain, 25, 0, 0, 0, 20, mealSize).setUnlocalizedName("Corn Bread").setCreativeTab(null);
		
		sugarcaneCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.None, 30, 0, 0, 0, 0, snackSize, 0.01F, false, false).setFolder("plants/").setUnlocalizedName("Sugarcane").setCreativeTab(null);
		sugarCWTFC = new ItemTFCFoodTransform(EnumFoodGroup.None, 30, 0, 0, 0, 0, snackSize, 0.01F, false, false).setUnlocalizedName("Sugar").setCreativeTab(null);
		
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
		VegetarianSandwich = new ItemTFCSandwichTransform(11, 3, 0, 0, 31, mealSize, 10, SkillRank.Novice, "VegetarianSandwich");
		ToastSandwich = new ItemTFCSandwichTransform(11, 3, 0, 0, 31, mealSize, 10, SkillRank.Novice, "ToastSandwich");
		
		Broth = new ItemTFCAdjutableFood(EnumFoodGroup.Protein, 0, 0, 0, 0, 30, snackSize, 20, 1F, false, false).setIsJug(true).setUnlocalizedName("Broth");
		BoiledChicken = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, mealSize).setCustomIcon(true).setUnlocalizedName("BoiledChicken");
		BoiledBeef = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 50, mealSize).setCustomIcon(true).setUnlocalizedName("BoiledBeef");
		BoiledFish = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, mealSize).setCustomIcon(true).setUnlocalizedName("BoiledFish");
		BoiledPork = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, mealSize).setCustomIcon(true).setUnlocalizedName("BoiledPork");
		BoiledVenison = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 5, 0, 0, 0, 50, mealSize).setCustomIcon(true).setUnlocalizedName("BoiledVenison");

		Salt = new ItemTFCSalt(EnumFoodGroup.None, 0, 0, 40, 0, 0, snackSize, 0.001F, false, false).setUnlocalizedName("Salt");
	}
	
	public static void RegularItemSetup()
	{
		ClayCookingPot = new ItemClayCookingPot();
	}
}
