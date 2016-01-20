package com.JAWolfe.cookingwithtfc.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CookingPotManager 
{
	private static final CookingPotManager INSTANCE = new CookingPotManager();
	public static final CookingPotManager getInstance()
	{
		return INSTANCE;
	}
	
	private List<CookingPotRecipe> recipes;
	
	public CookingPotManager()
	{
		recipes = new ArrayList<CookingPotRecipe>();
	}
	
	public void addRecipe(CookingPotRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public void clearRecipes()
	{
		recipes.clear();
	}
	
	public CookingPotRecipe findMatchingRecipe(FluidStack inputFluid, Item[] inputIngreds, float[] ingredAmounts)
	{
		return null;
	}
}
