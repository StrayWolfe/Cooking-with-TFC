package com.JAWolfe.cookingwithtfc.crafting;

import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Interfaces.IFood;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CookingPotRecipe 
{
	private ItemStack[] ingredItems;
	private FluidStack baseFluid;
	private float[] ingredAmounts;
	private FluidStack outputFluid;
	private ItemStack[] outputItems;
	private float[] outputAmounts;
	private int cookTime;
	
	public CookingPotRecipe(ItemStack[] inputIngreds, FluidStack inputFluid, float[] ingredAmounts, FluidStack outputFluid, ItemStack[] outputItems, float[] outputAmounts, int cookTime)
	{
		ingredItems = inputIngreds;
		baseFluid = inputFluid;
		this.ingredAmounts = ingredAmounts;
		this.outputFluid = outputFluid;
		this.outputItems = outputItems;
		this.outputAmounts = outputAmounts;
		this.cookTime = cookTime;
	}
	
	public boolean matches(FluidStack inputFluid, ItemStack[] inputIngreds)
	{	
		if(inputFluid != null && inputFluid.isFluidEqual(baseFluid))
		{			
			if(inputIngreds != null)
			{
				int ItemCount = 0;
				for(int i = 0; i < inputIngreds.length; i++)
				{
					if(inputIngreds[i] != null)
					{
						if(ingredItems == null)
							return false;
						
						if(!ListHasItem(inputIngreds[i]))
							return false;
						
						ItemCount++;
					}
				}
				
				if(ItemCount == 0 && ingredItems != null)
					return false;
				else if(ingredItems != null && ingredItems.length != ItemCount)
					return false;
				
				return true;
			}
		}
		
		return false;
	}
	
	private boolean ListHasItem(ItemStack item)
	{
		for(int x = 0; x < ingredItems.length; x++)
		{
			if(item.getItem() == ingredItems[x].getItem() && 
				item.getItemDamage() == ingredItems[x].getItemDamage())
			{
				if(item.getItem() instanceof IFood)
				{
					float weight = Food.getWeight(item);
					if(weight <= ingredAmounts[x] + 2 && weight >= ingredAmounts[x])
							return true;
				}
			}
		}
		return false;
	}
	
	public ItemStack[] getInputItems()
	{
		return ingredItems;
	}
	
	public float[] getInputAmounts()
	{
		return ingredAmounts;
	}
	
	public FluidStack getOutputFluid()
	{
		return outputFluid;
	}
	
	public ItemStack[] getOutputItems()
	{
		return outputItems;
	}
	
	public float[] getOutputAmounts()
	{
		return outputAmounts;
	}
	
	public CookingPotRecipe getRecipe()
	{
		return this;
	}
	
	public int getCookTime()
	{
		return cookTime;
	}
	
	public FluidStack getInputFluid()
	{
		return baseFluid;
	}
}
