package straywolfe.cookingwithtfc.api.recipe;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<CookingPotRecipe> getRecipeList()
	{
		return recipes;
	}
	
	public int getRecipeID(CookingPotRecipe recipe)
	{
		return recipes.indexOf(recipe);
	}
	
	public CookingPotRecipe getRecipe(int recipeID)
	{
		return recipes.get(recipeID);
	}
	
	public CookingPotRecipe findMatchingRecipe(FluidStack inputFluid, ItemStack[] inputInv)
	{
		for (int i = 0; i < recipes.size(); i++)
		{
			CookingPotRecipe irecipe = recipes.get(i);
			if (irecipe != null && irecipe.matches(inputFluid, inputInv))
				return irecipe.getRecipe();
		}
		return null;
	}
}
