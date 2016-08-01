package straywolfe.cookingwithtfc.api.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import straywolfe.cookingwithtfc.common.core.helper.Helper;

public class CookingPotRecipe 
{
	private List<Object> ingredItems;
	private FluidStack baseFluid;
	private FluidStack outputFluid;
	private ItemStack[] outputItems;
	private int cookTime;
	
	public CookingPotRecipe(int cookTime, FluidStack outputFluid, FluidStack inputFluid, ItemStack[] outputItems, ItemStack[] inputIngreds)
	{
		this(cookTime, outputFluid, inputFluid, inputIngreds);
		this.outputItems = outputItems;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CookingPotRecipe(int cookTime, FluidStack outputFluid, FluidStack inputFluid, ItemStack[] inputIngreds)
	{
		List<Object> inputsToSet = new ArrayList();
		
		for(Object obj : inputIngreds)
		{
			if(obj instanceof String || obj instanceof ItemStack)
				inputsToSet.add(obj);
			else throw new IllegalArgumentException("Invalid ingredient");
		}
		
		ingredItems = inputsToSet;
		baseFluid = inputFluid;
		this.outputFluid = outputFluid;
		this.cookTime = cookTime;
	}
	
	public CookingPotRecipe(int cookTime, FluidStack outputFluid, FluidStack inputFluid, ItemStack[] outputItems, Object...inputIngreds)
	{
		this(cookTime, outputFluid, inputFluid, inputIngreds);
		this.outputItems = outputItems;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CookingPotRecipe(int cookTime, FluidStack outputFluid, FluidStack inputFluid, Object...inputIngreds)
	{
		List<Object> inputsToSet = new ArrayList();
		
		for(Object obj : inputIngreds)
		{
			if(obj instanceof String || obj instanceof ItemStack)
				inputsToSet.add(obj);
			else if(obj instanceof ItemStack[])
			{
				for(ItemStack item : (ItemStack[])obj)
					inputsToSet.add(item);
			}
			else throw new IllegalArgumentException("Invalid ingredient");
		}
		
		ingredItems = inputsToSet;
		baseFluid = inputFluid;
		this.outputFluid = outputFluid;
		this.cookTime = cookTime;
	}
	
	public CookingPotRecipe(int cookTime, FluidStack inputFluid, ItemStack[] outputItems)
	{
		baseFluid = inputFluid;
		this.outputItems = outputItems;
		this.cookTime = cookTime;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean matches(FluidStack inputFluid, ItemStack[] inputIngreds)
	{	
		if(inputFluid != null && inputFluid.isFluidEqual(baseFluid))
		{			
			if(inputIngreds != null && ingredItems != null)
			{
				List<Object> recipeIngreds = new ArrayList(ingredItems);
				
				for(int i = 0; i <  inputIngreds.length; i++)
				{
					ItemStack stack = inputIngreds[i];
					int stackID = -1;
					int oreID = -1;
					
					if(stack == null)
						break;
					
					for(int j = 0; j < recipeIngreds.size(); j++)
					{
						Object recipeIngred = recipeIngreds.get(j);
						if(recipeIngred instanceof String) 
						{
							if(Helper.isOre((String) recipeIngred, stack))
							{
								oreID = j;
								break;
							}
						}
						else if(recipeIngred instanceof ItemStack && ((ItemStack) recipeIngred).getItem() == stack.getItem()) 
						{
							int recipeDmg = ((ItemStack) recipeIngred).getItemDamage();
							if(recipeDmg == Short.MAX_VALUE || recipeDmg == stack.getItemDamage())
							{
								stackID = j;
								break;
							}
						}
					}
					
					if(stackID != -1)
						recipeIngreds.remove(stackID);
					else if(oreID != -1)
						recipeIngreds.remove(oreID);
					else return false;
				}
				
				return recipeIngreds.isEmpty();
			}
			else if(ingredItems == null)
			{
				int itemcount = 0;
				for(ItemStack item : inputIngreds)
				{
					if(item != null)
						itemcount++;
				}
				if(itemcount == 0)
					return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object> getInputItems()
	{
		if(ingredItems != null)
			return new ArrayList(ingredItems);
		else
			return null;
	}
	
	public FluidStack getOutputFluid()
	{
		return outputFluid;
	}
	
	public ItemStack[] getOutputItems()
	{
		return outputItems;
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
