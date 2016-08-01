package straywolfe.cookingwithtfc.client.nei;

import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Food.ItemSalad;
import com.bioxx.tfc.Food.ItemSandwich;
import com.bioxx.tfc.Items.Tools.ItemCustomBucketMilk;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.client.nei.NEIGUIHandler;
import straywolfe.cookingwithtfc.common.lib.ModInfo;

public class NEIcookingwithtfcConfig implements IConfigureNEI
{
	@Override
	public String getName() 
	{
		return ModInfo.ModName;
	}

	@Override
	public String getVersion() 
	{
		return ModInfo.ModVersion;
	}

	@Override
	public void loadConfig() 
	{
		CookingPotRecipeHandler cookingpotRecipeHandler = new CookingPotRecipeHandler();
		
		API.registerNEIGuiHandler(new NEIGUIHandler());
        
        API.registerRecipeHandler(cookingpotRecipeHandler);
        API.registerUsageHandler(cookingpotRecipeHandler);
		
		API.hideItem(new ItemStack(CWTFCBlocks.GrainsBlock));
		API.hideItem(new ItemStack(CWTFCBlocks.hopperCWTFC));
		API.hideItem(new ItemStack(CWTFCBlocks.cookingPot));
		API.hideItem(new ItemStack(CWTFCBlocks.meatCWTFC));
		API.hideItem(new ItemStack(CWTFCBlocks.sandwichCWTFC));
		API.hideItem(new ItemStack(CWTFCBlocks.bowlCWTFC));
		
		API.hideItem(new ItemStack(CWTFCBlocks.prepTable2E, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(CWTFCBlocks.prepTable2S, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(CWTFCBlocks.prepTable2W, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(CWTFCBlocks.prepTableE, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(CWTFCBlocks.prepTableS, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(CWTFCBlocks.prepTableW, 1, OreDictionary.WILDCARD_VALUE));
		
		API.hideItem(new ItemStack(CWTFCBlocks.nestBoxCWTFC));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.redAppleCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.bananaCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.orangeCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.greenAppleCWTFC), Global.FOOD_MAX_WEIGHT));	
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.lemonCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.oliveCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.cherryCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.peachCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.plumCWTFC), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.wintergreenBerryCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.blueberryCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.raspberryCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.strawberryCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.blackberryCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.bunchberryCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.cranberryCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.snowberryCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.elderberryCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.gooseberryCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.cloudberryCWTFC), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.tomatoCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.potatoCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.onionCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.cabbageCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.garlicCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.carrotCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.greenbeansCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.greenBellPepperCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.yellowBellPepperCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.redBellPepperCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.squashCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.seaWeedCWTFC), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.cheeseCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemCustomBucketMilk.createTag(new ItemStack(CWTFCItems.woodenBucketMilkCWTFC, 1), 20));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.sugarCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.sugarcaneCWTFC), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.porkchopRawCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.porkchopCookedCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.fishRawCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.fishCookedCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.beefRawCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.beefCookedCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.chickenRawCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.chickenCookedCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.soybeanCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.eggCookedCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.eggCWTFC), 2));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.calamariRawCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.calamariCookedCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.muttonRawCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.muttonCookedCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.venisonRawCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.venisonCookedCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.horseMeatRawCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.horseMeatCookedCWTFC), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.barleyWholeCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.oatWholeCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.riceWholeCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.ryeWholeCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.wheatWholeCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.maizeEarCWTFC), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.barleyGrainCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.oatGrainCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.riceGrainCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.ryeGrainCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.wheatGrainCWTFC), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.barleyGroundCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.cornmealGroundCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.oatGroundCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.riceGroundCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.ryeGroundCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.wheatGroundCWTFC), Global.FOOD_MAX_WEIGHT));

		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.barleyDoughCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.cornmealDoughCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.oatDoughCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.riceDoughCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.ryeDoughCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.wheatDoughCWTFC), Global.FOOD_MAX_WEIGHT));
		
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.barleyBreadCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.cornBreadCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.oatBreadCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.riceBreadCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.ryeBreadCWTFC), Global.FOOD_MAX_WEIGHT));
		API.hideItem(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.wheatBreadCWTFC), Global.FOOD_MAX_WEIGHT));

		API.hideItem(ItemSalad.createTag(new ItemStack(TFCItems.salad, 1, OreDictionary.WILDCARD_VALUE)));
		API.hideItem(ItemSandwich.createTag(new ItemStack(TFCItems.sandwich, 1, OreDictionary.WILDCARD_VALUE)));
	}
}
