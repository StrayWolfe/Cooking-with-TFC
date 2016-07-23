package straywolfe.cookingwithtfc.common.crafting;

import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;

import net.minecraft.item.ItemStack;
import straywolfe.cookingwithtfc.api.CWTFCItems;

public class HeatedItemRecipes 
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
		
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.porkchopCookedCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.fishCookedCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.beefCookedCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.chickenCookedCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.calamariCookedCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.muttonCookedCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.venisonCookedCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.horseMeatCookedCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.soybeanCWTFC, 1), 1, 1200, null));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.eggCookedCWTFC, 1), 1, 1200, null));
		
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.porkchopRawCWTFC, 1), 1, 600, new ItemStack(CWTFCItems.porkchopCookedCWTFC, 1)).setKeepNBT(true));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.fishRawCWTFC, 1), 1, 600, new ItemStack(CWTFCItems.fishCookedCWTFC, 1)).setKeepNBT(true));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.beefRawCWTFC, 1), 1, 600, new ItemStack(CWTFCItems.beefCookedCWTFC, 1)).setKeepNBT(true));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.chickenRawCWTFC, 1), 1, 600, new ItemStack(CWTFCItems.chickenCookedCWTFC, 1)).setKeepNBT(true));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.calamariRawCWTFC, 1), 1, 600, new ItemStack(CWTFCItems.calamariCookedCWTFC, 1)).setKeepNBT(true));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.muttonRawCWTFC,1),1, 600, new ItemStack(CWTFCItems.muttonCookedCWTFC, 1)).setKeepNBT(true));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.venisonRawCWTFC,1),1, 600, new ItemStack(CWTFCItems.venisonCookedCWTFC, 1)).setKeepNBT(true));
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.horseMeatRawCWTFC, 1), 1, 600, new ItemStack(CWTFCItems.horseMeatCookedCWTFC, 1)).setKeepNBT(true));
		
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.eggCWTFC, 1), 1, 600, new ItemStack(CWTFCItems.eggCookedCWTFC, 1)).setKeepNBT(true));
		
		heatmanager.addIndex(new HeatIndex(new ItemStack(CWTFCItems.ClayCookingPot, 1, 1), 1.31, 1600, null));
	}
}
