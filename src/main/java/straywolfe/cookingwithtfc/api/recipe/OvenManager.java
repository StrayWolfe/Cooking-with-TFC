package straywolfe.cookingwithtfc.api.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class OvenManager 
{
	private static final OvenManager INSTANCE = new OvenManager();
	public static final OvenManager getInstance()
	{
		return INSTANCE;
	}
	
	private List<OvenRecipe> recipes;
	
	public OvenManager()
	{
		recipes = new ArrayList<OvenRecipe>();
	}
	
	public void addRecipe(OvenRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public void clearRecipes()
	{
		recipes.clear();
	}
	
	public List<OvenRecipe> getRecipeList()
	{
		return recipes;
	}
	
	public OvenRecipe findMatchingRecipe(ItemStack input)
	{
		for(OvenRecipe recipe : recipes)
		{
			if(recipe.matches(input))
				return recipe;
		}
		
		return null;
	}
}
