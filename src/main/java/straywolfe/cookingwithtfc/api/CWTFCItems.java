package straywolfe.cookingwithtfc.api;

import java.util.ArrayList;

import com.bioxx.tfc.Items.ItemTerra;

import net.minecraft.item.Item;
import straywolfe.cookingwithtfc.common.item.CWTFCItemSetup;
import straywolfe.cookingwithtfc.common.registries.CWTFCItemRegistry;

public class CWTFCItems 
{	
	public static ArrayList<ItemTerra> subfoodList;
	
	//Fruits
	public static Item watermelon;
	public static Item pumpkinBlock;
	public static Item melonBlock;
	
	//Vegetables
	public static Item brownMushroom;
	public static Item redMushroom;
	public static Item lettuce;
	public static Item celery;
	
	//Protein
	public static Item Broth;
	public static Item BoiledChicken;
	public static Item BoiledBeef;
	public static Item BoiledFish;
	public static Item BoiledPork;
	public static Item BoiledVenison;	
	public static Item peanut;
	public static Item chestnut;
	public static Item pecan;
	public static Item acorn;
	public static Item pineNut;
	public static Item walnut;
	public static Item almond;
	public static Item cashew;
	public static Item coconut;
	public static Item hazelnut;
	public static Item macadamia;
	public static Item pistachio;
	
	//Grains
	
	//Dairy
	
	//None
	public static Item Salt;
	
	//Salads
	public static Item VeggySalad;
	public static Item PotatoSalad;
	public static Item FruitSalad;
	
	//Sandwiches
	public static Item HamSandwich;
	public static Item ChickenSandwich;
	public static Item RoastBeefSandwich;
	public static Item SalmonSandwich;
	public static Item FriedEggSandwich;
	public static Item MuttonSandwich;
	public static Item VenisonSteakSandwich;
	public static Item VegetarianSandwich;
	public static Item ToastSandwich;
	
	//Soups
	public static Item VegetableSoup;
	public static Item TomatoSoup;
	public static Item ChickenSoup;
	public static Item BeefStew;
	public static Item VenisonStew;
	public static Item FishChowder;
	
	//Seeds
	public static Item seedsCelery;
	public static Item seedsLettuce;
	public static Item seedsPumpkin;
	public static Item seedsMelon;
	public static Item sporesBrownMushroom;
	public static Item sporesRedMushroom;
	public static Item seedsPeanut;
	
	//Miscellaneous
	public static Item jackolanternBlock;	
	public static Item ClayCookingPot;
	public static Item ClayOvenWall;
	public static Item logs;
	public static Item singlePlank;
	
	public static void setup()
	{		
		//Setup Items
		CWTFCItemSetup.setup();
		
		//Register Items
		CWTFCItemRegistry.registerItem();
	}
}
