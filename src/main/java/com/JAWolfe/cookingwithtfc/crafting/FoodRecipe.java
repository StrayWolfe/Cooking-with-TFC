package com.JAWolfe.cookingwithtfc.crafting;

import com.bioxx.tfc.Items.Tools.ItemKnife;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FoodRecipe 
{
	private ItemStack result;
	private ItemStack[] cookware;
	private ItemStack[] ingredients;
	private float[] pctIngedients;
	
	public FoodRecipe(ItemStack[] cookware, Item[] ingredients, float[] pctIngreds, ItemStack result)
	{
		this.cookware = cookware;
		this.result = result;	
		
		this.ingredients = new ItemStack[ingredients.length];
		for(int i = 0; i < ingredients.length; i++)
		{
			this.ingredients[i] = new ItemStack(ingredients[i], 1);
		}
		
		this.pctIngedients = pctIngreds;
	}
	
	/**
	 * Used to check if a recipe matches current crafting inventory
	 */    
	public boolean matches(FoodRecipe recipe)
	{
		if(areItemListsEqual(this.cookware, recipe.cookware) &&
			areItemListsEqual(this.ingredients, recipe.ingredients))
		{
			return true;
		}		
		return false;
	}
	
	public boolean matches(ItemStack[] cookware, ItemStack[] ingredients)
	{
		if(areItemListsEqual(this.cookware, cookware) &&
				areItemListsEqual(this.ingredients, ingredients))
			{
				return true;
			}		
			return false;
	}
	
	private boolean areItemListsEqual(ItemStack[] il1, ItemStack[] il2)
	{
		if(il1 != null && il2 != null)
		{

			for(int x = 0; x < il1.length; x++)
			{
				if(!ListHasItem(il1[x], il2))
					return false;
			}
		}
		else if(il1 == null && il2 != null || il1 != null && il2 == null)
			return false;
		
		return true;
	}
	
	private boolean ListHasItem(ItemStack is, ItemStack[] il)
	{
		for(int x = 0; x < il.length; x++)
		{
			if(is != null && il[x] != null)
			{
				if(is.getItem() == il[x].getItem())
				{
					if(is.getItemDamage() != 0)
					{
						if(is.getItemDamage() == il[x].getItemDamage())
							return true;
					}
					else
						return true;
				}
				
				if(is.getItem() instanceof ItemKnife && il[x].getItem() instanceof ItemKnife)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getResult()
	{
		return result;
	}
	
	public ItemStack[] getReqCookware()
	{
		return this.cookware;
	}
	
	public ItemStack[] getReqIngred()
	{
		return this.ingredients;
	}
	
	public float[] getPctList()
	{
		return this.pctIngedients;
	}
	
	public float getPctIngred(int index)
	{
		return this.pctIngedients[index];
	}
}
