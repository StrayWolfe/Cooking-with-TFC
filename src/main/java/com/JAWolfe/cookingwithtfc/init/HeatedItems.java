package com.JAWolfe.cookingwithtfc.init;

import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;

import net.minecraft.item.ItemStack;

public class HeatedItems 
{
	public static void setupItemHeat()
	{
		HeatRegistry heatmanager = HeatRegistry.getInstance();
		
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.wheatDoughCWTFC, 1), 1, 600, new ItemStack(CWTFCItems.wheatBreadCWTFC, 1)).setKeepNBT(true));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.barleyDoughCWTFC, 1), 1, 600, new ItemStack(CWTFCItems.barleyBreadCWTFC, 1)).setKeepNBT(true));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.oatDoughCWTFC, 1), 1, 600, new ItemStack(CWTFCItems.oatBreadCWTFC, 1)).setKeepNBT(true));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.ryeDoughCWTFC, 1), 1, 600, new ItemStack(CWTFCItems.ryeBreadCWTFC, 1)).setKeepNBT(true));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.riceDoughCWTFC, 1), 1, 600, new ItemStack(CWTFCItems.riceBreadCWTFC, 1)).setKeepNBT(true));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.cornmealDoughCWTFC, 1), 1, 600, new ItemStack(CWTFCItems.cornBreadCWTFC, 1)).setKeepNBT(true));

		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.wheatBreadCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.barleyBreadCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.oatBreadCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.ryeBreadCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.riceBreadCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.cornBreadCWTFC, 1), 1, 1200, null));
		
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.porkchopRawCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.fishRawCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.beefRawCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.chickenRawCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.soybeanCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.eggCookedCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.calamariRawCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.muttonRawCWTFC,1),1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.venisonRawCWTFC,1),1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.horseMeatRawCWTFC, 1), 1, 1200, null));
		
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.eggCWTFC, 1), 1, 600, new ItemStack(CWTFCItems.eggCookedCWTFC, 1)).setKeepNBT(true));
	}
}
