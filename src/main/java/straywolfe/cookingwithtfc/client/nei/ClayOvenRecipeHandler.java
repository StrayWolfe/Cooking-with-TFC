package straywolfe.cookingwithtfc.client.nei;

import java.awt.Rectangle;
import java.util.List;

import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.TFC_ItemHeat;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.managers.OvenManager;
import straywolfe.cookingwithtfc.api.managers.OvenRecipe;
import straywolfe.cookingwithtfc.common.core.helper.Helper;
import straywolfe.cookingwithtfc.common.lib.Textures;

public class ClayOvenRecipeHandler  extends TemplateRecipeHandler
{
	private static List<OvenRecipe> recipeList;

	@Override
	public String getRecipeName() 
	{
		return StatCollector.translateToLocal("tile.ClayOven.name");
	}

	@Override
	public String getGuiTexture() 
	{
		return Textures.Gui.CLAYOVEN.toString();
	}

	@Override
    public String getOverlayIdentifier()
    {
		return "clayoven";
	}
	
	@Override
    public void loadTransferRects()
    {
		transferRects.add(new RecipeTransferRect(new Rectangle(25, 27, 18, 10), getOverlayIdentifier()));
    }
	
	@Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
		if (outputId.equals(getOverlayIdentifier()))
        {
			for(OvenRecipe recipe : recipeList)
			{
				if(recipe.getOutput() != null)
					arecipes.add(new CachedClayOvenRecipe(recipe));
			}
        }
		else
			super.loadCraftingRecipes(outputId, results);
    }
	
	@Override
    public void loadCraftingRecipes(ItemStack result)
    {
		for(OvenRecipe recipe : recipeList)
		{
			if(recipe != null)
			{				
				ItemStack output = recipe.getOutput();
				
				if(output != null && Helper.areItemStacksEqual(result, output))
				{
					arecipes.add(new CachedClayOvenRecipe(recipe));
					break;
				}
			}
		}
    }
	
	@Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
		for(OvenRecipe recipe : recipeList)
		{
			if(recipe != null && recipe.matches(ingredient) && recipe.getOutput() != null)
				arecipes.add(new CachedClayOvenRecipe(recipe));
		}
    }
	
	@Override
    public TemplateRecipeHandler newInstance()
    {
		if (recipeList == null) recipeList = OvenManager.getInstance().getRecipeList();
		
		return super.newInstance();
    }
	
	@Override
    public void drawExtras(int recipe)
    {
        super.drawExtras(recipe);
        CachedRecipe cr = arecipes.get(recipe);
        if (cr instanceof CachedClayOvenRecipe) 
        	((CachedClayOvenRecipe) cr).drawExtras();
    }
	
	@Override
    public int recipiesPerPage()
    {
        return 2;
    }
	
	public class CachedClayOvenRecipe extends CachedRecipe
	{
		final PositionedStack ingred;
		final PositionedStack result;
		final PositionedStack heatSource;
		
		public CachedClayOvenRecipe(OvenRecipe recipe)
		{
			ingred = isFood(recipe.getInput()) ? new PositionedStack(ItemFoodTFC.createTag((ItemStack)recipe.getInput()), 25, 9) : null;

			result = isFood(recipe.getOutput()) ? new PositionedStack(ItemFoodTFC.createTag((ItemStack)recipe.getOutput()), 25, 37) : null;
			
			heatSource = new PositionedStack(new ItemStack(Item.getItemFromBlock(CWTFCBlocks.clayOven)), 50, 20);
		}
		
		private boolean isFood(Object obj)
		{
			if(obj != null && obj instanceof ItemStack && ((ItemStack)obj).getItem() instanceof ItemFoodTFC)
				return true;
			else
				return false;
		}

		@Override
		public PositionedStack getResult() 
		{
			return result;
		}
		
		@Override
		public PositionedStack getIngredient()
		{
			return ingred;
		}
		
		@Override
        public PositionedStack getOtherStack()
        {
			heatSource.setPermutationToRender(cycleticks / 24 % heatSource.items.length);
			
            return heatSource;
        }
		
		public void drawExtras()
        {
            FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
            renderer.drawString("Can be heated in: ", 54, 9, 0x000000);
            renderer.drawString("Temp: ", 54, 40, 0x000000);
            renderer.drawString(TFC_ItemHeat.getHeatColor(600, Integer.MAX_VALUE), 84, 40, 0x000000);
        }
	}
}
