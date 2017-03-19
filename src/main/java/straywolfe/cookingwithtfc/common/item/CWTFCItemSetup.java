package straywolfe.cookingwithtfc.common.item;

import java.util.ArrayList;

import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.item.itemblock.ItemGourdBlock;
import straywolfe.cookingwithtfc.common.item.itemblock.ItemLog;
import straywolfe.cookingwithtfc.common.lib.Settings;
import straywolfe.cookingwithtfc.common.registries.PlantRegistry;

public class CWTFCItemSetup extends CWTFCItems
{
	public static void setup()
	{
		subfoodList = new ArrayList<ItemTerra>();
		
		float snackSize = Settings.SNACK_SIZE;
		float mealSize = Settings.MEAL_SIZE;
				
		//Fruits
		watermelon = new ItemTFCFoodTransform(EnumFoodGroup.Fruit, 30, 5, 0, 0, 0, snackSize).setDecayRate(2.0f).setUnlocalizedName("SlicedWatermelon");
				
		//Vegetables
		lettuce = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 0, 0, 0, 10, 30, snackSize).setUnlocalizedName("Lettuce");
		celery = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, snackSize).setUnlocalizedName("Celery");
		brownMushroom = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 0, 0, 0, 0, 50, snackSize).setDecayRate(2.0f).setUnlocalizedName("BrownMushroom");
		redMushroom = new ItemTFCFoodTransform(EnumFoodGroup.Vegetable, 0, 0, 0, 0, 50, snackSize).setDecayRate(2.0f).setUnlocalizedName("RedMushroom");

		//Protein
		Broth = new ItemTFCAdjutableFood(EnumFoodGroup.Protein, 0, 0, 0, 0, 30, snackSize, 20, 1F, false, false).setIsJug(true).setUnlocalizedName("Broth");
		BoiledChicken = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, mealSize).setUnlocalizedName("BoiledChicken");
		BoiledBeef = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 50, mealSize).setUnlocalizedName("BoiledBeef");
		BoiledFish = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, mealSize).setUnlocalizedName("BoiledFish");
		BoiledPork = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, mealSize).setUnlocalizedName("BoiledPork");
		BoiledVenison = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 5, 0, 0, 0, 50, mealSize).setUnlocalizedName("BoiledVenison");
		peanut = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 10, 0, 0, 20, 40, snackSize).setDecayRate(0.5f).setUnlocalizedName("Peanut");
		chestnut = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 20, 0, 0, 25, 30, snackSize).setDecayRate(0.5f).setUnlocalizedName("Chestnut");
		pecan = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 20, 0, 0, 15, 40, snackSize).setDecayRate(0.5f).setUnlocalizedName("Pecan");
		acorn = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 25, 30, snackSize).setDecayRate(0.5f).setUnlocalizedName("Acorn");
		pineNut = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 0, 0, 0, 35, 40, snackSize).setDecayRate(0.5f).setUnlocalizedName("PineNut");
		walnut = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 20, 0, 0, 15, 40, snackSize).setDecayRate(0.5f).setUnlocalizedName("Walnut");
		almond = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 25, 0, 0, 20, 40, snackSize).setDecayRate(0.5f).setUnlocalizedName("Almond");
		cashew = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 30, 0, 0, 10, 20, snackSize).setDecayRate(0.5f).setUnlocalizedName("Cashew");
		hazelnut = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 20, 0, 0, 10, 30, snackSize).setDecayRate(0.5f).setUnlocalizedName("Hazelnut");
		coconut = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 15, 0, 0, 15, 20, snackSize).setDecayRate(0.5f).setUnlocalizedName("Coconut");
		macadamia = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 20, 0, 5, 15, 30, snackSize).setDecayRate(0.5f).setUnlocalizedName("Macadamia Nut");
		pistachio = new ItemTFCFoodTransform(EnumFoodGroup.Protein, 20, 0, 0, 0, 40, snackSize).setDecayRate(0.5f).setUnlocalizedName("Pistachio");
		
		//None
		Salt = new ItemTFCSalt(EnumFoodGroup.None, 0, 0, 40, 0, 0, snackSize, 0.001F, false, false).setUnlocalizedName("Salt");
				
		//Salads
		VeggySalad = new ItemTFCMealTransform(17, 5, 0, 0, 34, mealSize, 20, SkillRank.Novice, "VeggySalad").setHasBowl(true).hasCustomIcon(false).setIconPath("Salad0");
		PotatoSalad = new ItemTFCMealTransform(2, 5, 7, 11, 21, mealSize, 20, SkillRank.Novice, "PotatoSalad").setHasBowl(true).hasCustomIcon(false).setIconPath("Salad2");
		FruitSalad = new ItemTFCMealTransform(31, 11, 0, 5, 0, mealSize, 20, SkillRank.Novice, "FruitSalad").setHasBowl(true).hasCustomIcon(false).setIconPath("Salad3");
		
		//Soups & Stews
		VegetableSoup = new ItemTFCMealTransform(95, 5, 0, 5, 140, mealSize, 10, SkillRank.Novice, "VegetableSoup").setHasBowl(true);
		TomatoSoup = new ItemTFCMealTransform(30, 5, 40, 0, 80, mealSize, 10, SkillRank.Novice, "TomatoSoup").setHasBowl(true);
		ChickenSoup = new ItemTFCMealTransform(30, 25, 0, 0, 110, mealSize, 10, SkillRank.Novice, "ChickenSoup").setHasBowl(true);
		BeefStew = new ItemTFCMealTransform(30, 25, 10, 15, 110, mealSize, 10, SkillRank.Novice, "BeefStew").setHasBowl(true);
		VenisonStew = new ItemTFCMealTransform(35, 25, 10, 15, 110, mealSize, 10, SkillRank.Novice, "VenisonStew").setHasBowl(true);
		FishChowder = new ItemTFCMealTransform(20, 25, 10, 15, 100, mealSize, 10, SkillRank.Novice, "FishChowder").setHasBowl(true);
		
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
		
		//Seeds
		seedsPumpkin = new ItemCustomSeeds(PlantRegistry.PUMPKIN).setUnlocalizedName("PumpkinSeeds");
		seedsMelon = new ItemCustomSeeds(PlantRegistry.WATERMELON).setUnlocalizedName("MelonSeeds");
		seedsCelery = new ItemCustomSeeds(PlantRegistry.CELERY).setUnlocalizedName("CelerySeeds");
		seedsLettuce = new ItemCustomSeeds(PlantRegistry.LETTUCE).setUnlocalizedName("LettuceSeeds");
		sporesBrownMushroom = new ItemCustomSeeds(PlantRegistry.BROWNMUSHROOM).setUnlocalizedName("BrownMushroomSpores");
		sporesRedMushroom = new ItemCustomSeeds(PlantRegistry.REDMUSHROOM).setUnlocalizedName("RedMushroomSpores");
		seedsPeanut = new ItemCustomSeeds(PlantRegistry.PEANUT).setUnlocalizedName("PeanutSeeds");
				
		//Miscellaneous
		ClayCookingPot = new ItemClayCookingPot();
		ClayOvenWall = new ItemClayOvenWall();
		pumpkinBlock = new ItemGourdBlock(0).setUnlocalizedName("Pumpkin");
		melonBlock = new ItemGourdBlock(1).setUnlocalizedName("Watermelon");
		jackolanternBlock = new ItemGourdBlock(2).setUnlocalizedName("JackOLantern");
		logs = new ItemLog();
		singlePlank = new ItemLumber();
	}
}
