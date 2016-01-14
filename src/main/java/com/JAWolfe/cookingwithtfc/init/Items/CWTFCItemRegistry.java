package com.JAWolfe.cookingwithtfc.init.Items;

import com.JAWolfe.cookingwithtfc.util.LogHelper;

import cpw.mods.fml.common.registry.ExistingSubstitutionException;
import cpw.mods.fml.common.registry.GameRegistry;

public class CWTFCItemRegistry extends CWTFCItems
{
	public static void registerItems() throws ExistingSubstitutionException
	{		
		LogHelper.info("Registering Items");
		
		//Vegetables
		GameRegistry.registerItem(tomatoCWTFC, tomatoCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(potatoCWTFC, potatoCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(onionCWTFC, onionCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(cabbageCWTFC, cabbageCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(garlicCWTFC, garlicCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(carrotCWTFC, carrotCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(greenbeansCWTFC, greenbeansCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(greenBellPepperCWTFC, greenBellPepperCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(yellowBellPepperCWTFC, yellowBellPepperCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(redBellPepperCWTFC, redBellPepperCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(squashCWTFC, squashCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(seaWeedCWTFC, seaWeedCWTFC.getUnlocalizedName());
		
		//Fruit
		GameRegistry.registerItem(redAppleCWTFC, redAppleCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(bananaCWTFC, bananaCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(orangeCWTFC, orangeCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(greenAppleCWTFC, greenAppleCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(lemonCWTFC, lemonCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(cherryCWTFC, cherryCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(peachCWTFC, peachCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(plumCWTFC, plumCWTFC.getUnlocalizedName());
		
		GameRegistry.registerItem(wintergreenBerryCWTFC, wintergreenBerryCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(blueberryCWTFC, blueberryCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(raspberryCWTFC, raspberryCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(strawberryCWTFC, strawberryCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(blackberryCWTFC, blackberryCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(bunchberryCWTFC, bunchberryCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(cranberryCWTFC, cranberryCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(snowberryCWTFC, snowberryCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(elderberryCWTFC, elderberryCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(gooseberryCWTFC, gooseberryCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(cloudberryCWTFC, cloudberryCWTFC.getUnlocalizedName());
		
		//Proteins
		GameRegistry.registerItem(porkchopRawCWTFC, porkchopRawCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(fishRawCWTFC, fishRawCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(beefRawCWTFC, beefRawCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(chickenRawCWTFC, chickenRawCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(soybeanCWTFC, soybeanCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(eggCWTFC, eggCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(eggCookedCWTFC, eggCookedCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(calamariRawCWTFC, calamariRawCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(muttonRawCWTFC, muttonRawCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(venisonRawCWTFC, venisonRawCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(horseMeatRawCWTFC, horseMeatRawCWTFC.getUnlocalizedName());
		
		//Grains
		GameRegistry.registerItem(barleyWholeCWTFC, barleyWholeCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(oatWholeCWTFC, oatWholeCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(riceWholeCWTFC, riceWholeCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(ryeWholeCWTFC, ryeWholeCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(wheatWholeCWTFC, wheatWholeCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(maizeEarCWTFC, maizeEarCWTFC.getUnlocalizedName());
		
		GameRegistry.registerItem(barleyGrainCWTFC, barleyGrainCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(oatGrainCWTFC, oatGrainCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(riceGrainCWTFC, riceGrainCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(ryeGrainCWTFC, ryeGrainCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(wheatGrainCWTFC, wheatGrainCWTFC.getUnlocalizedName());
		
		GameRegistry.registerItem(barleyGroundCWTFC, barleyGroundCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(cornmealGroundCWTFC, cornmealGroundCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(oatGroundCWTFC, oatGroundCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(riceGroundCWTFC, riceGroundCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(ryeGroundCWTFC, ryeGroundCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(wheatGroundCWTFC, wheatGroundCWTFC.getUnlocalizedName());
		
		GameRegistry.registerItem(barleyDoughCWTFC, barleyDoughCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(cornmealDoughCWTFC, cornmealDoughCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(oatDoughCWTFC, oatDoughCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(riceDoughCWTFC, riceDoughCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(ryeDoughCWTFC, ryeDoughCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(wheatDoughCWTFC, wheatDoughCWTFC.getUnlocalizedName());
		
		GameRegistry.registerItem(barleyBreadCWTFC, barleyBreadCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(cornBreadCWTFC, cornBreadCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(oatBreadCWTFC, oatBreadCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(riceBreadCWTFC, riceBreadCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(ryeBreadCWTFC, ryeBreadCWTFC.getUnlocalizedName());
		GameRegistry.registerItem(wheatBreadCWTFC, wheatBreadCWTFC.getUnlocalizedName());
		
		GameRegistry.registerItem(VeggySalad, VeggySalad.getUnlocalizedName());
		GameRegistry.registerItem(PotatoSalad, PotatoSalad.getUnlocalizedName());
		GameRegistry.registerItem(FruitSalad, FruitSalad.getUnlocalizedName());
		
		GameRegistry.registerItem(HamSandwich, HamSandwich.getUnlocalizedName());		
		GameRegistry.registerItem(ChickenSandwich, ChickenSandwich.getUnlocalizedName());
		GameRegistry.registerItem(RoastBeefSandwich, RoastBeefSandwich.getUnlocalizedName());
		GameRegistry.registerItem(SalmonSandwich, SalmonSandwich.getUnlocalizedName());
		GameRegistry.registerItem(FriedEggSandwich, FriedEggSandwich.getUnlocalizedName());
		GameRegistry.registerItem(MuttonSandwich, MuttonSandwich.getUnlocalizedName());
		GameRegistry.registerItem(VenisonSteakSandwich, VenisonSteakSandwich.getUnlocalizedName());
		
		LogHelper.info("Done Registering Items");
	}
}
