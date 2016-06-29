package com.JAWolfe.cookingwithtfc.crafting;

import java.util.ArrayList;
import java.util.List;

import com.JAWolfe.cookingwithtfc.items.ItemTFCMealTransform;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class FoodManager 
{
	private static final FoodManager INSTANCE = new FoodManager();
	public static final FoodManager getInstance()
	{
		return INSTANCE;
	}
	
	private List<FoodRecipe> recipes;
	
	private FoodManager()
	{
		recipes = new ArrayList<FoodRecipe>();
	}
	
	public void addRecipe(FoodRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public void clearRecipes()
	{
		recipes.clear();
	}
	
	public FoodRecipe findMatchingRecipe(FoodRecipe recipe)
	{
		for (int i = 0; i < recipes.size(); i++)
		{
			FoodRecipe irecipe = recipes.get(i);
			if (irecipe != null && irecipe.matches(recipe))
				return irecipe;
		}
		return null;
	}
	
	public ItemStack findMatchingRecipe(ItemStack[] cookware, ItemStack[] ingredients)
	{
		for (int i = 0; i < recipes.size(); i++)
		{
			FoodRecipe irecipe = recipes.get(i);
			if (irecipe != null && irecipe.matches(cookware, ingredients))
				return irecipe.getResult();
		}
		
		return null;
	}
	
	public List<FoodRecipe> findMatchingRecipes(ItemStack[] cookware, ItemStack[] ingredients, EntityPlayer player)
	{
		List<FoodRecipe> recipesList = new ArrayList<FoodRecipe>();
		SkillRank sr = TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_COOKING);
		
		for (int i = 0; i < recipes.size(); i++)
		{
			FoodRecipe irecipe = recipes.get(i);
			if (irecipe != null && irecipe.matches(cookware, ingredients))
			{
				if(irecipe.getResult().getItem() instanceof ItemTFCMealTransform)
				{
					ItemTFCMealTransform food = (ItemTFCMealTransform)irecipe.getResult().getItem();
					if(sr.ordinal() >= food.getReqRank().ordinal())
						recipesList.add(irecipe);
				}
				else
				{
					recipesList.add(irecipe);
				}
			}
		}
		
		return recipesList;
	}
	
	public List<FoodRecipe> getRecipeList()
	{
		return recipes;
	}
}
