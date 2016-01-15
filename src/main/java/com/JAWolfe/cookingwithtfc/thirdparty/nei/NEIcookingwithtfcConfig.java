package com.JAWolfe.cookingwithtfc.thirdparty.nei;

import com.JAWolfe.cookingwithtfc.init.CWTFCBlocks;
import com.JAWolfe.cookingwithtfc.references.DetailsCWTFC;
import com.JAWolfe.cookingwithtfc.thirdparty.nei.NEIGUIHandler;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Food.ItemSalad;
import com.bioxx.tfc.Food.ItemSandwich;
import com.bioxx.tfc.Items.Tools.ItemCustomBucketMilk;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class NEIcookingwithtfcConfig implements IConfigureNEI
{
	@Override
	public String getName() {
		return DetailsCWTFC.ModName;
	}

	@Override
	public String getVersion() {
		return DetailsCWTFC.ModVersion;
	}

	@Override
	public void loadConfig() 
	{
		PrepTableRecipeHandler preptableRecipeHandler = new PrepTableRecipeHandler();
		
		API.registerNEIGuiHandler(new NEIGUIHandler());
		
		API.registerRecipeHandler(preptableRecipeHandler);
        API.registerUsageHandler(preptableRecipeHandler);
		
		API.hideItem(new ItemStack(CWTFCBlocks.GrainsBlock, 1));
		API.hideItem(new ItemStack(TFCBlocks.nestBox, 1));
				
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.redApple, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.banana, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.orange, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.greenApple, 1), Global.FOOD_MAX_WEIGHT));	
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.lemon, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.cherry, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.peach, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.plum, 1), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.wintergreenBerry, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.blueberry, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.raspberry, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.strawberry, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.blackberry, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.bunchberry, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.cranberry, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.snowberry, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.elderberry, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.gooseberry, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.cloudberry, 1), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.tomato, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.potato, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.onion, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.cabbage, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.garlic, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.carrot, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.greenbeans, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.greenBellPepper, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.yellowBellPepper, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.redBellPepper, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.squash, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.seaWeed, 1), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.cheese, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemCustomBucketMilk.createTag(new ItemStack(TFCItems.woodenBucketMilk, 1), 20));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.porkchopRaw, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.fishRaw, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.beefRaw, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.chickenRaw, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.soybean, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.eggCooked, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.calamariRaw, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.muttonRaw, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.venisonRaw, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.horseMeatRaw, 1), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.barleyWhole, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.oatWhole, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.riceWhole, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.ryeWhole, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.wheatWhole, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.maizeEar, 1), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.barleyGrain, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.oatGrain, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.riceGrain, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.ryeGrain, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.wheatGrain, 1), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.barleyGround, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.cornmealGround, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.oatGround, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.riceGround, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.ryeGround, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.wheatGround, 1), Global.FOOD_MAX_WEIGHT));

		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.barleyDough, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.cornmealDough, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.oatDough, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.riceDough, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.ryeDough, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.wheatDough, 1), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.barleyBread, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.cornBread, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.oatBread, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.riceBread, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.ryeBread, 1), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(TFCItems.wheatBread, 1), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemSalad.createTag(new ItemStack(TFCItems.salad, 1, OreDictionary.WILDCARD_VALUE)));
		API.hideItem(ItemSandwich.createTag(new ItemStack(TFCItems.sandwich, 1, OreDictionary.WILDCARD_VALUE)));
	}
}
