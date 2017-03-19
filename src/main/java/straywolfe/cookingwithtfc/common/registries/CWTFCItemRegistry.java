package straywolfe.cookingwithtfc.common.registries;

import cpw.mods.fml.common.registry.GameRegistry;
import straywolfe.cookingwithtfc.api.CWTFCItems;

public class CWTFCItemRegistry extends CWTFCItems
{
	public static void registerItem()
	{		
		//Vegetables
		GameRegistry.registerItem(brownMushroom, brownMushroom.getUnlocalizedName());
		GameRegistry.registerItem(redMushroom, redMushroom.getUnlocalizedName());
		GameRegistry.registerItem(lettuce, lettuce.getUnlocalizedName());
		GameRegistry.registerItem(celery, celery.getUnlocalizedName());
		
		//Fruits
		GameRegistry.registerItem(watermelon, watermelon.getUnlocalizedName());
		GameRegistry.registerItem(melonBlock, melonBlock.getUnlocalizedName());
		GameRegistry.registerItem(pumpkinBlock, pumpkinBlock.getUnlocalizedName());
		GameRegistry.registerItem(jackolanternBlock, jackolanternBlock.getUnlocalizedName());
		
		//Protein
		GameRegistry.registerItem(Broth, Broth.getUnlocalizedName());
		GameRegistry.registerItem(BoiledChicken, BoiledChicken.getUnlocalizedName());
		GameRegistry.registerItem(BoiledBeef, BoiledBeef.getUnlocalizedName());
		GameRegistry.registerItem(BoiledFish, BoiledFish.getUnlocalizedName());
		GameRegistry.registerItem(BoiledPork, BoiledPork.getUnlocalizedName());
		GameRegistry.registerItem(BoiledVenison, BoiledVenison.getUnlocalizedName());
		GameRegistry.registerItem(peanut, peanut.getUnlocalizedName());
		GameRegistry.registerItem(chestnut, chestnut.getUnlocalizedName());
		GameRegistry.registerItem(pecan, pecan.getUnlocalizedName());
		GameRegistry.registerItem(acorn, acorn.getUnlocalizedName());
		GameRegistry.registerItem(pineNut, pineNut.getUnlocalizedName());
		GameRegistry.registerItem(walnut, walnut.getUnlocalizedName());
		GameRegistry.registerItem(almond, almond.getUnlocalizedName());
		GameRegistry.registerItem(cashew, cashew.getUnlocalizedName());
		GameRegistry.registerItem(coconut, coconut.getUnlocalizedName());
		GameRegistry.registerItem(hazelnut, hazelnut.getUnlocalizedName());
		GameRegistry.registerItem(macadamia, macadamia.getUnlocalizedName());
		GameRegistry.registerItem(pistachio, pistachio.getUnlocalizedName());
		
		//Grains
		
		//Dairy
		
		//None
		GameRegistry.registerItem(Salt, Salt.getUnlocalizedName());
		
		//Salads
		GameRegistry.registerItem(VeggySalad, VeggySalad.getUnlocalizedName());
		GameRegistry.registerItem(PotatoSalad, PotatoSalad.getUnlocalizedName());
		GameRegistry.registerItem(FruitSalad, FruitSalad.getUnlocalizedName());
		
		//Soups & Stews
		GameRegistry.registerItem(VegetableSoup, VegetableSoup.getUnlocalizedName());
		GameRegistry.registerItem(TomatoSoup, TomatoSoup.getUnlocalizedName());
		GameRegistry.registerItem(ChickenSoup, ChickenSoup.getUnlocalizedName());
		GameRegistry.registerItem(BeefStew, BeefStew.getUnlocalizedName());
		GameRegistry.registerItem(VenisonStew, VenisonStew.getUnlocalizedName());
		GameRegistry.registerItem(FishChowder, FishChowder.getUnlocalizedName());
		
		//Sandwiches
		GameRegistry.registerItem(HamSandwich, HamSandwich.getUnlocalizedName());		
		GameRegistry.registerItem(ChickenSandwich, ChickenSandwich.getUnlocalizedName());
		GameRegistry.registerItem(RoastBeefSandwich, RoastBeefSandwich.getUnlocalizedName());
		GameRegistry.registerItem(SalmonSandwich, SalmonSandwich.getUnlocalizedName());
		GameRegistry.registerItem(FriedEggSandwich, FriedEggSandwich.getUnlocalizedName());
		GameRegistry.registerItem(MuttonSandwich, MuttonSandwich.getUnlocalizedName());
		GameRegistry.registerItem(VenisonSteakSandwich, VenisonSteakSandwich.getUnlocalizedName());
		GameRegistry.registerItem(VegetarianSandwich, VegetarianSandwich.getUnlocalizedName());
		GameRegistry.registerItem(ToastSandwich, ToastSandwich.getUnlocalizedName());
		
		//Seeds
		GameRegistry.registerItem(seedsCelery, seedsCelery.getUnlocalizedName());
		GameRegistry.registerItem(seedsLettuce, seedsLettuce.getUnlocalizedName());
		GameRegistry.registerItem(seedsPumpkin, seedsPumpkin.getUnlocalizedName());
		GameRegistry.registerItem(seedsMelon, seedsMelon.getUnlocalizedName());
		GameRegistry.registerItem(sporesBrownMushroom, sporesBrownMushroom.getUnlocalizedName());
		GameRegistry.registerItem(sporesRedMushroom, sporesRedMushroom.getUnlocalizedName());
		GameRegistry.registerItem(seedsPeanut, seedsPeanut.getUnlocalizedName());
		
		//Clay Items
		GameRegistry.registerItem(ClayCookingPot, ClayCookingPot.getUnlocalizedName());
		GameRegistry.registerItem(ClayOvenWall, ClayOvenWall.getUnlocalizedName());
		
		//Wood
		GameRegistry.registerItem(logs, logs.getUnlocalizedName());
		GameRegistry.registerItem(singlePlank, singlePlank.getUnlocalizedName());
	}
}
