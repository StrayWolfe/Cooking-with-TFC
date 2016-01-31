package com.JAWolfe.cookingwithtfc.crafting;

import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidStack;

public class CookingPotRecipe 
{
	private Item[] ingredItems;
	private FluidStack baseFluid;
	private float[] ingredAmounts;
	private FluidStack outputFluid;
	private Item[] outputItems;
	private float[] outputAmounts;
	
	public CookingPotRecipe(Item[] inputIngreds, FluidStack inputFluid, float[] ingredAmounts, FluidStack outputFluid, Item[] outputItems, float[] outputAmounts)
	{
		this.ingredItems = inputIngreds;
		this.baseFluid = inputFluid;
		this.ingredAmounts = ingredAmounts;
		this.outputFluid = outputFluid;
		this.outputItems = outputItems;
		this.outputAmounts = outputAmounts;
	}
	
	public boolean matches(FluidStack inputFluid, Item[] inputIngreds)
	{		
		if(inputFluid != null && inputIngreds != null &&
				inputFluid.isFluidEqual(this.baseFluid) && areIngredsEqual(inputIngreds, this.ingredItems))
			return true;
		
		return false;
	}
	
	private boolean areIngredsEqual(Item[] inputIngreds, Item[] recipeIngreds)
	{		
		
		if(inputIngreds.length == recipeIngreds.length && recipeIngreds.length != 0)
		{
			for(int i = 0; i < inputIngreds.length; i++)
			{
				if(recipeIngreds[i] != null && !ListHasItem(recipeIngreds[i], inputIngreds))
					return false;
			}
		}
		else if(inputIngreds.length != recipeIngreds.length)
			return false;
		
		return true;
	}
	
	private boolean ListHasItem(Item item, Item[] itemList)
	{
		for(int x = 0; x < itemList.length; x++)
		{
			if(item != null && itemList[x] != null && item == itemList[x])
				return true;
		}
		return false;
	}
	
	public Item[] getInputItems()
	{
		return this.ingredItems;
	}
	
	public float[] getInputAmounts()
	{
		return this.ingredAmounts;
	}
	
	public FluidStack getOutputFluid()
	{
		return this.outputFluid;
	}
	
	public Item[] getOutputItems()
	{
		return this.outputItems;
	}
	
	public float[] getOutputAmounts()
	{
		return this.outputAmounts;
	}
	
	public CookingPotRecipe getRecipe()
	{
		return this;
	}
}
