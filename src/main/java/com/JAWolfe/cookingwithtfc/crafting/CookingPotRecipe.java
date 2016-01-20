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
	
	public CookingPotRecipe(Item[] inputIngreds, FluidStack inputFluid, float[] ingredAmounts, FluidStack outputFluid, Item[] outputItems)
	{
		this.ingredItems = inputIngreds;
		this.baseFluid = inputFluid;
		this.ingredAmounts = ingredAmounts;
		this.outputFluid = outputFluid;
		this.outputItems = outputItems;
	}
	
	public FluidStack getOutputFluid()
	{
		return this.outputFluid;
	}
	
	public Item[] getOutputItems()
	{
		return this.outputItems;
	}
}
