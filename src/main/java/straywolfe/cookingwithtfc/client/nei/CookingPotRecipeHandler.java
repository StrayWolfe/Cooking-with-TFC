package straywolfe.cookingwithtfc.client.nei;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemTerra;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import straywolfe.cookingwithtfc.api.recipe.CookingPotManager;
import straywolfe.cookingwithtfc.api.recipe.CookingPotRecipe;
import straywolfe.cookingwithtfc.common.core.helper.Helper;

import straywolfe.cookingwithtfc.common.item.ItemTFCMealTransform;
import straywolfe.cookingwithtfc.common.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;

public class CookingPotRecipeHandler extends TemplateRecipeHandler
{
	private static List<CookingPotRecipe> recipeList;
	
	/**
	 * Assigns the name of the type of recipe on the header
	 * 
	 * @return Display Name
	 */
	@Override
	public String getRecipeName() 
	{
		return StatCollector.translateToLocal("tile.CookingPot.name");
	}
	
	/**
     * @return The filepath to the texture to use when drawing this recipe
     */
	@Override
	public String getGuiTexture() 
	{
		return Textures.Gui.CLAYCOOKINGPOT.toString();
	}
	
	public String getRecipeID() 
	{
		return "cookingpot";
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
        return getRecipeID();
    }
	
	/**
     * A list of transferRects that when it is clicked or R is pressed will open a new recipe.
     */
	@Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(73, 49, 17, 7), getRecipeID()));
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
		if (outputId.equals(getRecipeID()))
        {
			for (CookingPotRecipe recipe : recipeList) 
				arecipes.add(new CachedCookingPotRecipe(recipe));
        }
		else
			super.loadCraftingRecipes(outputId, results);
    }
	
	/**
     * Simplified wrapper, implement this and fill the empty recipe array with recipes
     *
     * @param result The result the recipes must output.
     */
	@Override
    public void loadCraftingRecipes(ItemStack result)
    {
		for (CookingPotRecipe recipe : recipeList)
		{
			CachedCookingPotRecipe crecipe = new CachedCookingPotRecipe(recipe);
			
			ItemStack[] outputFoods = recipe.getOutputItems();
			
			if(outputFoods != null)
			{
				for(ItemStack outputFood : outputFoods)
				{
					if(outputFood != null && Helper.areItemStacksEqual(result, outputFood))
					{
						arecipes.add(crecipe);
						break;
					}
				}
			}
			
			FluidStack outputFluid = recipe.getOutputFluid();
			if (outputFluid != null && outputFluid.isFluidEqual(result)) 
				arecipes.add(crecipe);
		}
    }
	
	@Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
		for (CookingPotRecipe recipe : recipeList)
		{			
			if(recipe != null)
			{
				CachedCookingPotRecipe crecipe = new CachedCookingPotRecipe(recipe);
				if(crecipe.contains(crecipe.Ingredients, ingredient))
					arecipes.add(crecipe);

				FluidStack inputFluid = recipe.getInputFluid();
				FluidStack fluidstack = FluidContainerRegistry.getFluidForFilledItem(ingredient);
				
				if (inputFluid != null && inputFluid.isFluidEqual(fluidstack)) 
					arecipes.add(crecipe);
			}
		}
    }
	
	@Override
    public void drawExtras(int recipe)
    {
		if (arecipes.get(recipe) instanceof CachedCookingPotRecipe)
		{
			CachedCookingPotRecipe crecipe = (CachedCookingPotRecipe)arecipes.get(recipe);
			
			if(crecipe.getInputFluid() != null)
		        drawFluidRect(crecipe.getInputFluid().getFluid(), crecipe.getInputFluidRect());
			
			if(crecipe.getOutputFluid() != null)
            	drawFluidRect(crecipe.getOutputFluid().getFluid(), crecipe.getOutputFluidRect());
		}
    }
	
	@Override
    public List<String> handleItemTooltip(GuiRecipe gui, ItemStack stack, List<String> currenttip, int recipe)
    {
        if (arecipes.get(recipe) instanceof CachedCookingPotRecipe)
        {
        	CachedCookingPotRecipe crecipe = (CachedCookingPotRecipe)arecipes.get(recipe);
            Point mousePosition = GuiDraw.getMousePosition();
            Point guiOffset = gui.getRecipePosition(recipe);
            int guiLeft = (gui.width - 158) / 2;
            int guiTop = (gui.height - 92) / 2;
            Point pointMouse = new Point(mousePosition.x - guiLeft - guiOffset.x, mousePosition.y - guiTop - guiOffset.y);
            
            if (crecipe.getInputFluidRect().contains(pointMouse) && (crecipe.getInputFluid() != null))
            {
            	currenttip.add(crecipe.getInputFluid().getLocalizedName());
            	currenttip.add("(" + crecipe.getInputFluid().amount + "mB)");
            }
            
            if (crecipe.getOutputFluidRect().contains(pointMouse) && (crecipe.getOutputFluid() != null))
            {
            	currenttip.add(crecipe.getOutputFluid().getLocalizedName());
            	currenttip.add("(" + crecipe.getInputFluid().amount + "mB)");
            }
        }
        return currenttip;
    }

    @Override
    public boolean keyTyped(GuiRecipe gui, char keyChar, int keyCode, int recipe)
    {
        if (keyCode == NEIClientConfig.getKeyBinding("gui.recipe"))
        {
            if (transferFluid(gui, recipe, false))
            	return true;
        }
        else if (keyCode == NEIClientConfig.getKeyBinding("gui.usage"))
        {
            if (transferFluid(gui, recipe, true))
            	return true;
        }

        return super.keyTyped(gui, keyChar, keyCode, recipe);
    }

    @Override
    public boolean mouseClicked(GuiRecipe gui, int button, int recipe)
    {
        if (button == 0)
        {
            if (transferFluid(gui, recipe, false)) 
            	return true;
        }
        else if (button == 1)
        {
            if (transferFluid(gui, recipe, true))
            	return true;
        }

        return super.mouseClicked(gui, button, recipe);
    }

    private boolean transferFluid(GuiRecipe gui, int recipe, boolean usage)
    {
        if (arecipes.get(recipe) instanceof CachedCookingPotRecipe)
        {
        	CachedCookingPotRecipe crecipe = (CachedCookingPotRecipe)arecipes.get(recipe);
        	
        	ItemStack itemFluid = null;
        	Point mousePosition = GuiDraw.getMousePosition();
            Point guiOffset = gui.getRecipePosition(recipe);
            int guiLeft = (gui.width - 158) / 2;
            int guiTop = (gui.height - 92) / 2;
            Point pointMouse = new Point(mousePosition.x - guiLeft - guiOffset.x, mousePosition.y - guiTop - guiOffset.y);
            
            if (crecipe.getInputFluidRect().contains(pointMouse) && (crecipe.getInputFluid() != null))
            	itemFluid = Helper.getItemStackForFluid(crecipe.getInputFluid());
            
            if (crecipe.getOutputFluidRect().contains(pointMouse) && (crecipe.getOutputFluid() != null)) 
            	itemFluid = Helper.getItemStackForFluid(crecipe.getOutputFluid());
            
            if (itemFluid != null)
            {
            	if(usage ? GuiUsageRecipe.openRecipeGui("item", itemFluid) : GuiCraftingRecipe.openRecipeGui("item", itemFluid))
            		return true;
            }
        }
        return false;
    }
    
	public static void drawFluidRect(Fluid fluid, Rectangle rect)
    {
		IIcon fluidIcon = fluid.getIcon();
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		int color = fluid.getColor();
		GL11.glColor4ub((byte) ((color >> 16) & 255), (byte) ((color >> 8) & 255), (byte) (color & 255), (byte) (0xaa & 255));
		GuiDraw.gui.drawTexturedModelRectFromIcon(rect.x + 9, rect.y + 37, fluidIcon, rect.width, rect.height);
	}
	
	@Override
    public TemplateRecipeHandler newInstance()
    {
        if (recipeList == null) recipeList = CookingPotManager.getInstance().getRecipeList();
        
        return super.newInstance();
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
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(3, 7, 0, 0, 158, 92);
    }
    
    public class CachedCookingPotRecipe extends CachedRecipe
    {

		private final ArrayList<PositionedStack> Ingredients = new ArrayList<PositionedStack>();
		private final ArrayList<PositionedStack> OutputItems = new ArrayList<PositionedStack>();
        final FluidStack inputFluid;
        final FluidStack outputFluid;

        public CachedCookingPotRecipe(CookingPotRecipe recipe)
        {
        	inputFluid = recipe.getInputFluid();
        	outputFluid = recipe.getOutputFluid();
        	
        	setIngredients(recipe.getInputItems());
        	setOutputItems(recipe.getOutputItems());        	
        }
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
		public void setIngredients(List<Object> inputs)
        {
        	if(inputs != null)
        	{	        	
	        	for(int i = 0; i < inputs.size(); i++)
	        	{
	        		int x = 5;
		        	int y = 9;
		        	
	        		if(i < 2) 
	        			x = x + (18 * i);
	        		else if(i >= 2 && i < 4)
	        		{
	        			x = x + (18 * (i - 2)); 
	        			y = y + 18;
	        		}
	        		else if(i >= 4 && i < 6)
	        		{
	        			x = x + (18 * (i - 4)); 
	        			y = y + 36;
	        		}
	        		else if(i >= 6 && i < 8)
	        		{
	        			x = x + (18 * (i - 6)); 
	        			y = y + 54;
	        		}
	        		else if(i >= 8 && i < 10)
	        		{
	        			x = x + (18 * (i - 8)); 
	        			y = y + 72;
	        		}
	        		
	        		if(inputs.get(i) != null)
	        		{
	        			if(inputs.get(i) instanceof ItemStack)
	        			{
	        				ItemStack inputitem = (ItemStack)inputs.get(i);
	      
	        				if(inputitem.getItemDamage() == Short.MAX_VALUE && inputitem.getItem() instanceof ItemTerra)
	        				{
	        					ArrayList<ItemStack> tfcItems = new ArrayList();
	        					
	        					for(int meta = 0; meta < ((ItemTerra)inputitem.getItem()).metaNames.length; meta++)
	        					{
	        						if(inputitem.getItem() instanceof ItemFoodTFC)
					        		{
	        							tfcItems.add(ItemFoodTFC.createTag(new ItemStack(inputitem.getItem(), 1, meta), 2));
					        		}
					        		else
					        			tfcItems.add(ItemTFCMealTransform.createTag(new ItemStack(inputitem.getItem(), 1, meta), 2, 0, new ItemStack[]{}, new float[]{}));
	        					}
	        					
	        					Ingredients.add(new PositionedStack(tfcItems, x, y));
	        				}
	        				else
	        				{
				        		if(inputitem.getItem() instanceof ItemFoodTFC)
				        		{
				        			Ingredients.add(new PositionedStack(ItemFoodTFC.createTag(new ItemStack(inputitem.getItem(), 
				        					1, inputitem.getItemDamage()), 2), x, y));
				        		}
				        		else
				        			Ingredients.add(new PositionedStack(ItemTFCMealTransform.createTag(new ItemStack(inputitem.getItem(), 
				        					1, inputitem.getItemDamage()), 2, 0, new ItemStack[]{}, new float[]{}), x, y));
	        				}
	        			}
	        			else if(inputs.get(i) instanceof String)
	        			{
	        				ArrayList<ItemStack> tfcItems = new ArrayList();
	        				
	        				for(ItemStack oitem : OreDictionary.getOres((String) inputs.get(i)))
	        				{
	        					if(oitem.getItem() instanceof ItemFoodTFC)
	        						tfcItems.add(ItemFoodTFC.createTag(new ItemStack(oitem.getItem(), 1, oitem.getItemDamage()), 2));
				        		else
				        			tfcItems.add(ItemTFCMealTransform.createTag(new ItemStack(oitem.getItem(), 1, oitem.getItemDamage()), 2, 0, new ItemStack[]{}, new float[]{}));
	        				}
	        				
	        				Ingredients.add(new PositionedStack(tfcItems, x, y));
	        			}
	        		}
	        	}
        	}
        }
        
        public void setOutputItems(ItemStack[] outputItems)
        {
        	if(outputItems != null)
        	{
	        	for(int i = 0; i < outputItems.length; i++)
	        	{
	        		int x = 102;
		        	int y = 9;
	        		
	        		if(i < 2) 
	        			x = x + (18 * i);
	        		else if(i >= 2 && i < 4)
	        		{
	        			x = x + (18 * (i - 2)); 
	        			y = y + 18;
	        		}
	        		else if(i >= 4 && i < 6)
	        		{
	        			x = x + (18 * (i - 4)); 
	        			y = y + 36;
	        		}
	        		else if(i >= 6 && i < 8)
	        		{
	        			x = x + (18 * (i - 6)); 
	        			y = y + 54;
	        		}
	        		else if(i >= 8 && i < 10)
	        		{
	        			x = x + (18 * (i - 8)); 
	        			y = y + 72;
	        		}
	        		
	        		if(outputItems[i].getItem() instanceof ItemFoodTFC)
	        			OutputItems.add(new PositionedStack(ItemFoodTFC.createTag(
	        				new ItemStack(outputItems[i].getItem(), 1, outputItems[i].getItemDamage()), 2), x, y));
	        		else
	        			OutputItems.add(new PositionedStack(ItemTFCMealTransform.createTag(new ItemStack(outputItems[i].getItem(), 1, outputItems[i].getItemDamage()), 
	        					2, 0, new ItemStack[]{}, new float[]{}), x, y));
	        	}
        	}
        }
        
        public Rectangle getInputFluidRect()
        {
        	return new Rectangle(41, -9, 8, 50);
        }
        
        public Rectangle getOutputFluidRect()
        {
        	return new Rectangle(138, -9, 8, 50);
        }

        @Override
        public PositionedStack getResult()
        {
            return null;
        }

        @Override
        public List<PositionedStack> getIngredients()
        {
        	for (PositionedStack positionedStack : Ingredients) 
        		positionedStack.setPermutationToRender(cycleticks / 24 % positionedStack.items.length);
            
        	return Ingredients;
        }

        @Override
        public List<PositionedStack> getOtherStacks()
        {
        	for (PositionedStack positionedStack : OutputItems) 
        		positionedStack.setPermutationToRender(cycleticks / 24 % positionedStack.items.length);
            
        	return OutputItems;
        }
        
        public FluidStack getInputFluid()
        {
            return inputFluid;
        }

        public FluidStack getOutputFluid()
        {
            return outputFluid;
        }
		
    }
}
