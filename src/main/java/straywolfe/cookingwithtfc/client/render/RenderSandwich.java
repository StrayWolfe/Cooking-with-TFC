package straywolfe.cookingwithtfc.client.render;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import straywolfe.cookingwithtfc.common.block.BlockSandwich;
import straywolfe.cookingwithtfc.common.core.helper.Helper;
import straywolfe.cookingwithtfc.common.tileentity.TileSandwich;

public class RenderSandwich implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(tileentity != null && tileentity instanceof TileSandwich && block instanceof BlockSandwich)
		{
			TileSandwich te = (TileSandwich)tileentity;
			int topToastLevel = te.getTopToast();
			ItemStack[] contents = te.getSandwichContents();
			BlockSandwich sandwich = (BlockSandwich)block;
			CWTFCRenderer myRenderer = new CWTFCRenderer(x, y, z, sandwich, renderer);
			float xCoord = te.getSandwichCoord(0);
			float zCoord = te.getSandwichCoord(1);
			
			IIcon bottomBreadHorz = sandwich.getSandwichIcon("WheatTop");
			IIcon bottomBreadVert = sandwich.getSandwichIcon("WheatSide");
			IIcon topBreadHorz = sandwich.getSandwichIcon("WheatTop");
			IIcon topBreadVert = sandwich.getSandwichIcon("WheatSide");
			IIcon foodGroup1 = sandwich.getSandwichIcon("Vegetable");
			IIcon foodGroup2 = sandwich.getSandwichIcon("Vegetable");
			IIcon foodGroup3 = sandwich.getSandwichIcon("Vegetable");
			IIcon foodGroup4 = sandwich.getSandwichIcon("Vegetable");
			
			if(contents[0] != null)
			{
				Item item = contents[0].getItem();
				if(item == TFCItems.barleyBread)
				{
					bottomBreadVert = sandwich.getSandwichIcon("BarleySide");
					bottomBreadHorz = sandwich.getSandwichIcon("BarleyTop");
				}
				else if(item == TFCItems.cornBread)
				{
					bottomBreadVert = sandwich.getSandwichIcon("CornSide");
					bottomBreadHorz = sandwich.getSandwichIcon("CornTop");
				}
				else if(item == TFCItems.oatBread)
				{
					bottomBreadVert = sandwich.getSandwichIcon("OatSide");
					bottomBreadHorz = sandwich.getSandwichIcon("OatTop");
				}
				else if(item == TFCItems.riceBread)
				{
					bottomBreadVert = sandwich.getSandwichIcon("RiceSide");
					bottomBreadHorz = sandwich.getSandwichIcon("RiceTop");
				}
				else if(item == TFCItems.ryeBread)
				{
					bottomBreadVert = sandwich.getSandwichIcon("RyeSide");
					bottomBreadHorz = sandwich.getSandwichIcon("RyeTop");
				}
			}
				
			
			if(contents[topToastLevel] != null)
			{
				Item item = contents[topToastLevel].getItem();
				if(item == TFCItems.barleyBread)
				{
					topBreadVert = sandwich.getSandwichIcon("BarleySide");
					topBreadHorz = sandwich.getSandwichIcon("BarleyTop");
				}
				else if(item == TFCItems.cornBread)
				{
					topBreadVert = sandwich.getSandwichIcon("CornSide");
					topBreadHorz = sandwich.getSandwichIcon("CornTop");
				}
				else if(item == TFCItems.oatBread)
				{
					topBreadVert = sandwich.getSandwichIcon("OatSide");
					topBreadHorz = sandwich.getSandwichIcon("OatTop");
				}
				else if(item == TFCItems.riceBread)
				{
					topBreadVert = sandwich.getSandwichIcon("RiceSide");
					topBreadHorz = sandwich.getSandwichIcon("RiceTop");
				}
				else if(item == TFCItems.ryeBread)
				{
					topBreadVert = sandwich.getSandwichIcon("RyeSide");
					topBreadHorz = sandwich.getSandwichIcon("RyeTop");
				}
			}
			
			GL11.glPushMatrix();
			
			if(Minecraft.isFancyGraphicsEnabled())
			{
				if(contents[0] != null)
					setTop(0F, 0.05F, 0.4F, 0, xCoord, zCoord, bottomBreadVert, bottomBreadHorz, myRenderer);
				
				if(contents[1] != null && !Helper.isOre("itemBread", contents[1]))
					setup3dRendering(contents[1], myRenderer, 0.4F, 0.02875F, xCoord, 0.05F, zCoord);
				
				if(contents[2] != null && !Helper.isOre("itemBread", contents[2]))
					setup3dRendering(contents[2], myRenderer, 0.4F, 0.02875F, xCoord, 0.02875F + 0.05F, zCoord);
				
				if(contents[3] != null && !Helper.isOre("itemBread", contents[3]))
					setup3dRendering(contents[3], myRenderer, 0.4F, 0.02875F, xCoord, (0.02875F * 2) + 0.05F, zCoord);
				
				if(contents[4] != null && !Helper.isOre("itemBread", contents[4]))
					setup3dRendering(contents[4], myRenderer, 0.4F, 0.02875F, xCoord, (0.02875F * 3) + 0.05F, zCoord);
				
				if(contents[topToastLevel] != null)
				{
					if(topToastLevel == 1)
						setTop(0.05F, 0.05F, 0.4F, 0, xCoord, zCoord, topBreadVert, topBreadHorz, myRenderer);
					else
						setTop(topToastLevel * 0.0329F, 0.05F, 0.4F, 0, xCoord, zCoord, topBreadVert, topBreadHorz, myRenderer);
				}
			}
			else
			{
				if(contents[0] != null)
					setTop(0F, 0.05F, 0.4F, 0, xCoord, zCoord, bottomBreadVert, bottomBreadHorz, myRenderer);
				
				if(contents[1] != null && !Helper.isOre("itemBread", contents[1]))
				{
					EnumFoodGroup fg = ((ItemFoodTFC)contents[1].getItem()).getFoodGroup();
					if(fg == EnumFoodGroup.Dairy)
						foodGroup1 = sandwich.getSandwichIcon("Dairy");
					else if(fg == EnumFoodGroup.Fruit)
						foodGroup1 = sandwich.getSandwichIcon("Fruit");
					else if(fg == EnumFoodGroup.Protein)
						foodGroup1 = sandwich.getSandwichIcon("Protein");
					
					setTop(0.05F, 0.02875F, 0.4F, 0.01F, xCoord, zCoord, foodGroup1, foodGroup1, myRenderer);
				}
				
				if(contents[2] != null && !Helper.isOre("itemBread", contents[2]))
				{
					EnumFoodGroup fg = ((ItemFoodTFC)contents[2].getItem()).getFoodGroup();
					if(fg == EnumFoodGroup.Dairy)
						foodGroup2 = sandwich.getSandwichIcon("Dairy");
					else if(fg == EnumFoodGroup.Fruit)
						foodGroup2 = sandwich.getSandwichIcon("Fruit");
					else if(fg == EnumFoodGroup.Protein)
						foodGroup2 = sandwich.getSandwichIcon("Protein");
					
					setTop(0.02875F + 0.05F, 0.02875F, 0.4F, 0.03F, xCoord, zCoord, foodGroup2, foodGroup2, myRenderer);
				}
				
				if(contents[3] != null && !Helper.isOre("itemBread", contents[3]))
				{
					EnumFoodGroup fg = ((ItemFoodTFC)contents[3].getItem()).getFoodGroup();
					if(fg == EnumFoodGroup.Dairy)
						foodGroup3 = sandwich.getSandwichIcon("Dairy");
					else if(fg == EnumFoodGroup.Fruit)
						foodGroup3 = sandwich.getSandwichIcon("Fruit");
					else if(fg == EnumFoodGroup.Protein)
						foodGroup3 = sandwich.getSandwichIcon("Protein");
					
					setTop((2 * 0.02875F) + 0.05F, 0.02875F, 0.4F, 0.01F, xCoord, zCoord, foodGroup3, foodGroup3, myRenderer);
				}
				
				if(contents[4] != null && !Helper.isOre("itemBread", contents[4]))
				{
					EnumFoodGroup fg = ((ItemFoodTFC)contents[4].getItem()).getFoodGroup();
					if(fg == EnumFoodGroup.Dairy)
						foodGroup4 = sandwich.getSandwichIcon("Dairy");
					else if(fg == EnumFoodGroup.Fruit)
						foodGroup4 = sandwich.getSandwichIcon("Fruit");
					else if(fg == EnumFoodGroup.Protein)
						foodGroup4 = sandwich.getSandwichIcon("Protein");
					
					setTop((3 * 0.02875F) + 0.05F, 0.02875F, 0.4F, 0.02F, xCoord, zCoord, foodGroup4, foodGroup4, myRenderer);
				}
				
				if(contents[topToastLevel] != null)
					setTop(((topToastLevel - 1) * 0.02875F) + 0.05F, 0.05F, 0.4F, 0, xCoord, zCoord, topBreadVert, topBreadHorz, myRenderer);
			}	
			
			GL11.glPopMatrix();
		}
		return false;
	}
	
	private void setup3dRendering(ItemStack is, CWTFCRenderer myRenderer, float scale, float depth, float xAdj, float yAdj, float zAdj)
	{
		if(is.getItem() instanceof ItemFoodTFC)
		{
			ItemFoodTFC food = (ItemFoodTFC)is.getItem();
			
			if(Food.isCooked(is))
			{
				int color = Food.getCookedColorMultiplier(is);
				GL11.glColor4f(((color & 0xFF0000)>>16)/255f, ((color & 0x00ff00)>>8)/255f, (color & 0x0000ff)/255f, 1);				
			}
			
			myRenderer.icon = food.getIcon(is, 0);
			myRenderer.draw3DItem(scale, depth, xAdj, yAdj, zAdj);
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	private void setTop(float level, float height, float size, float shrink, float xAdj, float zAdj, IIcon texSide, IIcon texTop, CWTFCRenderer myRenderer)
	{		
		float xMin = xAdj + shrink;
		float xMax = xAdj + size - shrink;
		float yMin = level;
		float yMax = yMin + height;
		float zMin = zAdj + shrink;
		float zMax = zAdj + size - shrink;
		
		myRenderer.icon = texSide;
		
		myRenderer.renderSquareXNeg(xMin, yMin, yMax, zMin, zMax, 0, 1, 0.000F, 0.125F);
		
		myRenderer.renderSquareXPos(xMax, yMin, yMax, zMin, zMax, 0, 1, 0.125F, 0.250F);
		
		myRenderer.renderSquareZNeg(xMin, xMax, yMin, yMax, zMin, 0, 1, 0.250F, 0.375F);
		
		myRenderer.renderSquareZPos(xMin, xMax, yMin, yMax, zMax, 0, 1, 0.375F, 0.500F);
		
		myRenderer.icon = texTop;
		
		myRenderer.renderSquareYNeg(xMin, xMax, yMin, zMin, zMax);
		
		myRenderer.renderSquareYPos(xMin, xMax, yMax, zMin, zMax);
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

	@Override
	public boolean shouldRender3DInInventory(int modelId) 
	{
		return false;
	}

	@Override
	public int getRenderId() 
	{
		return 0;
	}

}
