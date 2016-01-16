package com.JAWolfe.cookingwithtfc.crafting;

import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;

public class PressRecipe 
{
	private Item ingredient;
	private Fluid Output;
	
	public PressRecipe(Item ingredient, Fluid Output)
	{
		this.ingredient = ingredient;
		this.Output= Output;
	}
	
	public boolean matches(Item ingredient)
	{
		if(ingredient == this.ingredient)
			return true;
		else
			return false;
	}
	
	public PressRecipe getRecipe()
	{
		return this;
	}
	
	public Fluid getOutput()
	{
		return this.Output;
	}
}
