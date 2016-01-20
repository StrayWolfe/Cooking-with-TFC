package com.JAWolfe.cookingwithtfc.thirdparty.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.JAWolfe.cookingwithtfc.crafting.FoodManager;
import com.JAWolfe.cookingwithtfc.crafting.FoodRecipe;
import com.JAWolfe.cookingwithtfc.items.Items.ItemTFCFoodTransform;
import com.JAWolfe.cookingwithtfc.references.Textures;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.opengl.GL11;

public class PrepTableRecipeHandler extends TemplateRecipeHandler
{
	private static List<FoodRecipe> recipeList;

	/**
	 * Assigns the name of the type of recipe on the header
	 * 
	 * @return Display Name
	 */
	@Override
	public String getRecipeName() 
	{
		return "Food Preparation";
	}

	/**
     * @return The filepath to the texture to use when drawing this recipe
     */
	@Override
	public String getGuiTexture() 
	{
		return Textures.Gui.PREPTABLE.toString();
	}

	/**
     * Simply works with the {@link DefaultOverlayRenderer}
     * If the current container has been registered with this identifier, the question mark appears and an overlay guide can be drawn.
     *
     * @return The overlay identifier of this recipe type.
     */
	@Override
    public String getOverlayIdentifier()
    {
        return "preptable";
    }
	
	/**
     * A list of transferRects that when it is clicked or R is pressed will open a new recipe.
     */
	@Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(105, 63, 56, 18), "preptable"));
    }
	
	/**
     * In this function you need to fill up the empty recipe array with recipes.
     * The default passes it to a cleaner handler if outputId is an item
     *
     * @param outputId A String identifier representing the type of output produced. Eg. {"item", "fuel"}
     * @param results  Objects representing the results that matching recipes must produce.
     */
	@Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
		if (outputId.equals("preptable") && getClass() == PrepTableRecipeHandler.class)
        {
			for (FoodRecipe recipe : recipeList) arecipes.add(new CachedPrepTableRecipe(recipe));
        }
		else super.loadCraftingRecipes(outputId, results);
    }
	
	/**
     * Simplified wrapper, implement this and fill the empty recipe array with recipes
     *
     * @param result The result the recipes must output.
     */
	@Override
    public void loadCraftingRecipes(ItemStack result)
    {
		for (FoodRecipe recipe : recipeList)
            if (areItemStacksEqual(result, recipe.getResult())) arecipes.add(new CachedPrepTableRecipe(recipe));
    }
    
	@Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
		for (FoodRecipe recipe : recipeList)
		{
			for(int i = 0; i < recipe.getReqIngred().length; i++)
			{
				if(areItemStacksEqual(ingredient, recipe.getReqIngred()[i])) 
					arecipes.add(new CachedPrepTableRecipe(recipe));
			}
			
			for(int i = 0; i < recipe.getReqCookware().length; i++)
			{
				if (areItemStacksEqual(ingredient, recipe.getReqCookware()[i])) 
					arecipes.add(new CachedPrepTableRecipe(recipe));
			}
		}
    }
	
	@Override
    public TemplateRecipeHandler newInstance()
    {
        if (recipeList == null) recipeList = FoodManager.getInstance().getRecipeList();
        return super.newInstance();
    }
	
	public static boolean areItemStacksEqual(ItemStack input, ItemStack target)
    {
        return input == target || OreDictionary.itemMatches(target, input, false);
    }
	
	@Override
    public int recipiesPerPage()
    {
        return 1;
    }

    @Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 4, 4, 171 - 4, 81 - 4);
    }
	
	public class CachedPrepTableRecipe extends CachedRecipe
    {

		private final ArrayList<PositionedStack> Ingredients = new ArrayList<PositionedStack>();
		private final ArrayList<PositionedStack> Cookware = new ArrayList<PositionedStack>();
        final PositionedStack result;

        public CachedPrepTableRecipe(ItemStack[] ingred, ItemStack[] cookware, float[] pctList, ItemStack result)
        {
        	float resultWt = ((ItemTFCFoodTransform)result.getItem()).getMaxFoodWt();
        	
        	this.result = new PositionedStack(ItemTFCFoodTransform.createTag(new ItemStack(result.getItem()), resultWt), 121, 41);
        	
        	for(int i = 0; i < ingred.length; i++)
        	{
        		int x = 4;
        		int y = 4;
        		
        		if(i < 4) x = x + (18 * i);
        		else if(i < 8) {x = x + (18 * (i - 4)); y = y + (18 * 1);}
        		else if(i < 12) {x = x + (18 * (i - 8)); y = y + (18 * 2);}
        		else if(i < 16) {x = x + (18 * (i - 12)); y = y + (18 * 3);}
        		
        		Ingredients.add(new PositionedStack(ItemTFCFoodTransform.createTag(
        				new ItemStack(ingred[i].getItem()), pctList[i] * resultWt), x, y));
        	}
        	
        	for(int i = 0; i < cookware.length; i++)
        	{
        		int x = 94;
        		int y = 4;
        		
        		if(i < 4) x = x + (18 * i);
        		else {x = x + (18 * (i - 4)); y = y + 18;}
        		
        		Cookware.add(new PositionedStack(new ItemStack(cookware[i].getItem(), 1, 
        				cookware[i].getItemDamage()), x, y));
        	}
        }

        public CachedPrepTableRecipe(FoodRecipe recipe)
        {
            this(recipe.getReqIngred(), recipe.getReqCookware(), recipe.getPctList(), recipe.getResult());
        }

        @Override
        public PositionedStack getResult()
        {
            return result;
        }

        @Override
        public List<PositionedStack> getIngredients()
        {
        	for (PositionedStack positionedStack : Ingredients) positionedStack.setPermutationToRender(cycleticks / 24 % positionedStack.items.length);
            return Ingredients;
        }

        @Override
        public List<PositionedStack> getOtherStacks()
        {
        	for (PositionedStack positionedStack : Cookware) positionedStack.setPermutationToRender(cycleticks / 24 % positionedStack.items.length);
            return Cookware;
        }
		
    }
}
