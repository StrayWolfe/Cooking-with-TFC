package straywolfe.cookingwithtfc.common.registries;

import java.util.List;

import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCItems;

import net.minecraft.item.ItemStack;
import straywolfe.cookingwithtfc.api.managers.OvenManager;
import straywolfe.cookingwithtfc.api.managers.OvenRecipe;

public class HeatedItemRecipes 
{	
	public static void setupHeatedItemRecipes()
	{
		registerHeatRecipes();
		
		registerOvenRecipes();
	}
	
	private static void registerHeatRecipes()
	{
		HeatRegistry heatmanager = HeatRegistry.getInstance();
		
		removeHeatRecipe(heatmanager, new ItemStack(TFCItems.wheatDough), new ItemStack(TFCItems.wheatBread), 600f, 1f);
		removeHeatRecipe(heatmanager, new ItemStack(TFCItems.barleyDough), new ItemStack(TFCItems.barleyBread), 600f, 1f);
		removeHeatRecipe(heatmanager, new ItemStack(TFCItems.oatDough), new ItemStack(TFCItems.oatBread), 600f, 1f);
		removeHeatRecipe(heatmanager, new ItemStack(TFCItems.ryeDough), new ItemStack(TFCItems.ryeBread), 600f, 1f);
		removeHeatRecipe(heatmanager, new ItemStack(TFCItems.riceDough), new ItemStack(TFCItems.riceBread), 600f, 1f);
		removeHeatRecipe(heatmanager, new ItemStack(TFCItems.cornmealDough), new ItemStack(TFCItems.cornBread), 600f, 1f);
	}
	
	private static void removeHeatRecipe(HeatRegistry heatmanager, ItemStack inputStack, ItemStack outputStack, float meltingPoint, float specificHeat)
	{
		List<HeatIndex> heatList = heatmanager.getHeatList();
		for (int i = 0; i < heatList.size(); i++)
		{
			if (heatList.get(i) != null)
			{
				if (heatList.get(i).matches(inputStack) && heatList.get(i).getOutputItem() == outputStack.getItem()
						&& heatList.get(i).meltTemp == meltingPoint && heatList.get(i).specificHeat == specificHeat)
					heatList.remove(i--);
			}
		}
	}
	
	private static void registerOvenRecipes()
	{
		OvenManager ovenmanager = OvenManager.getInstance();
		
		ovenmanager.addRecipe(new OvenRecipe(1F, 600F, new ItemStack(TFCItems.barleyBread), new ItemStack(TFCItems.barleyDough)));
		ovenmanager.addRecipe(new OvenRecipe(1F, 600F, new ItemStack(TFCItems.cornBread), new ItemStack(TFCItems.cornmealDough)));
		ovenmanager.addRecipe(new OvenRecipe(1F, 600F, new ItemStack(TFCItems.oatBread), new ItemStack(TFCItems.oatDough)));
		ovenmanager.addRecipe(new OvenRecipe(1F, 600F, new ItemStack(TFCItems.riceBread), new ItemStack(TFCItems.riceDough)));
		ovenmanager.addRecipe(new OvenRecipe(1F, 600F, new ItemStack(TFCItems.ryeBread), new ItemStack(TFCItems.ryeDough)));
		ovenmanager.addRecipe(new OvenRecipe(1F, 600F, new ItemStack(TFCItems.wheatBread), new ItemStack(TFCItems.wheatDough)));
		
		ovenmanager.addRecipe(new OvenRecipe(1F, 1200F, null, new ItemStack(TFCItems.barleyBread)));
		ovenmanager.addRecipe(new OvenRecipe(1F, 1200F, null, new ItemStack(TFCItems.cornBread)));
		ovenmanager.addRecipe(new OvenRecipe(1F, 1200F, null, new ItemStack(TFCItems.oatBread)));
		ovenmanager.addRecipe(new OvenRecipe(1F, 1200F, null, new ItemStack(TFCItems.riceBread)));
		ovenmanager.addRecipe(new OvenRecipe(1F, 1200F, null, new ItemStack(TFCItems.ryeBread)));
		ovenmanager.addRecipe(new OvenRecipe(1F, 1200F, null, new ItemStack(TFCItems.wheatBread)));
	}
}
