package straywolfe.cookingwithtfc.api.managers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;

public class PressManager 
{
	private static final PressManager INSTANCE = new PressManager();
	public static final PressManager getInstance()
	{
		return INSTANCE;
	}
	
	private List<PressRecipe> recipes;
	
	private PressManager()
	{
		recipes = new ArrayList<PressRecipe>();
	}
	
	public void addRecipe(PressRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public void clearRecipes()
	{
		recipes.clear();
	}
	
	public List<PressRecipe> getRecipeList()
	{
		return recipes;
	}
	
	public PressRecipe getMatchingRecipe(Item inputItem)
	{
		for(int i = 0; i < recipes.size(); i++)
		{
			PressRecipe irecipe = recipes.get(i);
			if(irecipe != null && irecipe.matches(inputItem))
				return irecipe.getRecipe();
		}
		return null;
	}
}
