package straywolfe.cookingwithtfc.api.recipe;

import net.minecraft.item.ItemStack;
import straywolfe.cookingwithtfc.common.core.helper.Helper;

public class OvenRecipe 
{
	private Object input;
	private ItemStack output;
	private float specificHeat;
	private float cookedTemp;
	
	public OvenRecipe(double sh, double temp, ItemStack out, Object in)
	{
		specificHeat = (float)sh;
		cookedTemp = (float)temp;
		output = out;
		
		if(in instanceof String || in instanceof ItemStack)
			input = in;
		else throw new IllegalArgumentException("Invalid Input");
	}
	
	public ItemStack getOutput()
	{
		return output;
	}
	
	public Object getInput()
	{
		return input;
	}
	
	public float getSpecificHeat()
	{
		return specificHeat;
	}
	
	public float getCookedTemp()
	{
		return cookedTemp;
	}
	
	public OvenRecipe getRecipe()
	{
		return this;
	}
	
	public boolean matches(ItemStack is)
	{
		if(is != null)
		{			
			if(input instanceof String) 
			{
				if(Helper.isOre((String) input, is))
					return true;
			}
			else if(input instanceof ItemStack && ((ItemStack) input).getItem() == is.getItem())
			{
				int recipeDmg = ((ItemStack) input).getItemDamage();
				if(recipeDmg == Short.MAX_VALUE || recipeDmg == is.getItemDamage())
					return true;
			}
		}
		
		return false;
	}
}
