package straywolfe.cookingwithtfc.client.render;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Render.TESR.TESRBase;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.item.ItemBread;
import straywolfe.cookingwithtfc.common.item.ItemTFCFoodTransform;
import straywolfe.cookingwithtfc.common.lib.Textures;
import straywolfe.cookingwithtfc.common.tileentity.TileSandwich;

public class TESRSandwich extends TESRBase
{
	public TESRSandwich()
	{
		super();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity TileEntity, double x, double y, double z, float f)
	{
		if(TileEntity.getWorldObj() != null && TileEntity instanceof TileSandwich)
		{
			ResourceLocation bottomBreadHorz = null;
			ResourceLocation bottomBreadVert = null;
			ResourceLocation foodGroup1 = null;
			ResourceLocation foodGroup2 = null;
			ResourceLocation foodGroup3 = null;
			ResourceLocation foodGroup4 = null;
			ResourceLocation topBreadHorz = null;
			ResourceLocation topBreadVert = null;
			
			TileSandwich te = (TileSandwich)TileEntity;
			int topToastLevel = te.getTopToast();
			ItemStack[] contents = te.getSandwichContents();
			Tessellator t = Tessellator.instance;
			EntityItem customitem = new EntityItem(field_147501_a.field_147550_f);
			customitem.hoverStart = 0f;
			
			if(contents[0] != null)
			{
				Item item = contents[0].getItem();
				if(item == CWTFCItems.barleyBreadCWTFC)
				{
					bottomBreadVert = Textures.Block.BARLEYTOASTSIDE;
					bottomBreadHorz = Textures.Block.BARLEYTOASTTOP;
				}
				else if(item == CWTFCItems.cornBreadCWTFC)
				{
					bottomBreadVert = Textures.Block.CORNTOASTSIDE;
					bottomBreadHorz = Textures.Block.CORNTOASTTOP;
				}
				else if(item == CWTFCItems.oatBreadCWTFC)
				{
					bottomBreadVert = Textures.Block.OATTOASTSIDE;
					bottomBreadHorz = Textures.Block.OATTOASTTOP;
				}
				else if(item == CWTFCItems.riceBreadCWTFC)
				{
					bottomBreadVert = Textures.Block.RICETOASTSIDE;
					bottomBreadHorz = Textures.Block.RICETOASTTOP;
				}
				else if(item == CWTFCItems.ryeBreadCWTFC)
				{
					bottomBreadVert = Textures.Block.RYETOASTSIDE;
					bottomBreadHorz = Textures.Block.RYETOASTTOP;
				}
				else
				{
					bottomBreadVert = Textures.Block.WHEATTOASTSIDE;
					bottomBreadHorz = Textures.Block.WHEATTOASTTOP;
				}
			}
				
			
			if(contents[topToastLevel] != null)
			{
				Item item = contents[topToastLevel].getItem();
				if(item == CWTFCItems.barleyBreadCWTFC)
				{
					topBreadVert = Textures.Block.BARLEYTOASTSIDE;
					topBreadHorz = Textures.Block.BARLEYTOASTTOP;
				}
				else if(item == CWTFCItems.cornBreadCWTFC)
				{
					topBreadVert = Textures.Block.CORNTOASTSIDE;
					topBreadHorz = Textures.Block.CORNTOASTTOP;
				}
				else if(item == CWTFCItems.oatBreadCWTFC)
				{
					topBreadVert = Textures.Block.OATTOASTSIDE;
					topBreadHorz = Textures.Block.OATTOASTTOP;
				}
				else if(item == CWTFCItems.riceBreadCWTFC)
				{
					topBreadVert = Textures.Block.RICETOASTSIDE;
					topBreadHorz = Textures.Block.RICETOASTTOP;
				}
				else if(item == CWTFCItems.ryeBreadCWTFC)
				{
					topBreadVert = Textures.Block.RYETOASTSIDE;
					topBreadHorz = Textures.Block.RYETOASTTOP;
				}
				else
				{
					topBreadVert = Textures.Block.WHEATTOASTSIDE;
					topBreadHorz = Textures.Block.WHEATTOASTTOP;
				}
			}
			
			if(RenderManager.instance.options.fancyGraphics)
			{
				GL11.glPushMatrix();
				GL11.glTranslatef((float) x + te.getSandwichCoord(0), (float) y, (float) z + te.getSandwichCoord(1));
				
				if(contents[0] != null)
					setTop(t, 0F, 0.05F, 0.4F, 0, bottomBreadVert, bottomBreadHorz);
				
				GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);				
				GL11.glScalef(0.79F, 0.79F, 0.875F);
				
				if(contents[1] != null && !(contents[1].getItem() instanceof ItemBread))
				{
					customitem.setEntityItemStack(contents[1]);
					itemRenderer.doRender(customitem, -0.25, -0.47, -0.08, 0, 0);
				}
				
				if(contents[2] != null && !(contents[2].getItem() instanceof ItemBread))
				{
					customitem.setEntityItemStack(contents[2]);
					itemRenderer.doRender(customitem, -0.25, -0.47, -0.08 - 0.03, 0, 0);
				}
				
				if(contents[3] != null && !(contents[3].getItem() instanceof ItemBread))
				{
					customitem.setEntityItemStack(contents[3]);
					itemRenderer.doRender(customitem, -0.25, -0.47, -0.08 + (- 0.03 * 2), 0, 0);
				}
				
				if(contents[4] != null && !(contents[4].getItem() instanceof ItemBread))
				{
					customitem.setEntityItemStack(contents[4]);
					itemRenderer.doRender(customitem, -0.25, -0.47, -0.08 + (- 0.03 * 3), 0, 0);
				}
				
				GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(270, 1.0F, 0.0F, 0.0F);
				GL11.glScalef(1.27F, 1.15F, 1.27F);
				
				if(contents[topToastLevel] != null)
					setTop(t, topToastLevel * 0.0329F, 0.05F, 0.4F, 0, topBreadVert, topBreadHorz);
				
				GL11.glPopMatrix();
			}
			else
			{
				GL11.glPushMatrix();
				GL11.glTranslatef((float) x + te.getSandwichCoord(0), (float) y, (float) z + te.getSandwichCoord(1));
				
				if(contents[0] != null)
					setTop(t, 0F, 0.05F, 0.4F, 0, bottomBreadVert, bottomBreadHorz);
				
				if(contents[1] != null && !(contents[1].getItem() instanceof ItemBread))
				{
					EnumFoodGroup fg = ((ItemTFCFoodTransform)contents[1].getItem()).getFoodGroup();
					if(fg == EnumFoodGroup.Dairy)
						foodGroup1 = Textures.Block.DAIRY;
					else if(fg == EnumFoodGroup.Fruit)
						foodGroup1 = Textures.Block.FRUIT;
					else if(fg == EnumFoodGroup.Protein)
						foodGroup1 = Textures.Block.PROTEIN;
					else if(fg == EnumFoodGroup.Vegetable)
						foodGroup1 = Textures.Block.VEGETABLE;
					
					setTop(t, 0.05F, 0.02875F, 0.4F, 0.01F, foodGroup1, foodGroup1);
				}
				
				if(contents[2] != null && !(contents[2].getItem() instanceof ItemBread))
				{
					EnumFoodGroup fg = ((ItemTFCFoodTransform)contents[2].getItem()).getFoodGroup();
					if(fg == EnumFoodGroup.Dairy)
						foodGroup2 = Textures.Block.DAIRY;
					else if(fg == EnumFoodGroup.Fruit)
						foodGroup2 = Textures.Block.FRUIT;
					else if(fg == EnumFoodGroup.Protein)
						foodGroup2 = Textures.Block.PROTEIN;
					else if(fg == EnumFoodGroup.Vegetable)
						foodGroup2 = Textures.Block.VEGETABLE;
					
					setTop(t, 0.02875F + 0.05F, 0.02875F, 0.4F, 0.03F, foodGroup2, foodGroup2);
				}
				
				if(contents[3] != null && !(contents[3].getItem() instanceof ItemBread))
				{
					EnumFoodGroup fg = ((ItemTFCFoodTransform)contents[3].getItem()).getFoodGroup();
					if(fg == EnumFoodGroup.Dairy)
						foodGroup3 = Textures.Block.DAIRY;
					else if(fg == EnumFoodGroup.Fruit)
						foodGroup3 = Textures.Block.FRUIT;
					else if(fg == EnumFoodGroup.Protein)
						foodGroup3 = Textures.Block.PROTEIN;
					else if(fg == EnumFoodGroup.Vegetable)
						foodGroup3 = Textures.Block.VEGETABLE;
					
					setTop(t, (2 * 0.02875F) + 0.05F, 0.02875F, 0.4F, 0.01F, foodGroup3, foodGroup3);
				}
				
				if(contents[4] != null && !(contents[4].getItem() instanceof ItemBread))
				{
					EnumFoodGroup fg = ((ItemTFCFoodTransform)contents[4].getItem()).getFoodGroup();
					if(fg == EnumFoodGroup.Dairy)
						foodGroup4 = Textures.Block.DAIRY;
					else if(fg == EnumFoodGroup.Fruit)
						foodGroup4 = Textures.Block.FRUIT;
					else if(fg == EnumFoodGroup.Protein)
						foodGroup4 = Textures.Block.PROTEIN;
					else if(fg == EnumFoodGroup.Vegetable)
						foodGroup4 = Textures.Block.VEGETABLE;
					
					setTop(t, (3 * 0.02875F) + 0.05F, 0.02875F, 0.4F, 0.02F, foodGroup4, foodGroup4);
				}
				
				if(contents[topToastLevel] != null)
					setTop(t, ((topToastLevel - 1) * 0.02875F) + 0.05F, 0.05F, 0.4F, 0, topBreadVert, topBreadHorz);
				
				GL11.glPopMatrix();
			}
		}
	}
	
	private void setTop(Tessellator t, float level, float height, float size, float shrink, ResourceLocation texSide, ResourceLocation texTop)
	{	
		float xMin = shrink;
		float xMax = size - shrink;
		float yMin = level;
		float yMax = yMin + height;
		float zMin = shrink;
		float zMax = size - shrink;
		float side = 0.125F;
		
		//Vertical Faces
		if(texSide != null)
			bindTexture(texSide);
		
		t.startDrawingQuads();
		t.setNormal(0.0F, 0.0F, 1.0F);
		
		//SOUTH
		t.addVertexWithUV(xMin, yMin, zMin, 0, 0);
		t.addVertexWithUV(xMin, yMin, zMax, 0, 1);
		t.addVertexWithUV(xMin, yMax, zMax, side, 1);
		t.addVertexWithUV(xMin, yMax, zMin, side, 0);
		//WEST
		t.addVertexWithUV(xMin, yMin, zMax, 0, 0);
		t.addVertexWithUV(xMax, yMin, zMax, 0, 1);
		t.addVertexWithUV(xMax, yMax, zMax, side, 1);
		t.addVertexWithUV(xMin, yMax, zMax, side, 0);
		//NORTH
		t.addVertexWithUV(xMax, yMin, zMax, 0, 0);
		t.addVertexWithUV(xMax, yMin, zMin, 0, 1);
		t.addVertexWithUV(xMax, yMax, zMin, side, 1);
		t.addVertexWithUV(xMax, yMax, zMax, side, 0);
		//EAST
		t.addVertexWithUV(xMax, yMin, zMin, 0, 0);
		t.addVertexWithUV(xMin, yMin, zMin, 0, 1);
		t.addVertexWithUV(xMin, yMax, zMin, side, 1);
		t.addVertexWithUV(xMax, yMax, zMin, side, 0);
		t.draw();
		
		//Horizontal Faces
		if(texTop != null)
			bindTexture(texTop);
		
		t.startDrawingQuads();
		t.setNormal(0.0F, 1.0F, 0.0F);
		
		//Top
		t.addVertexWithUV(xMin, yMax, zMin, 0, 0);
		t.addVertexWithUV(xMin, yMax, zMax, 0, 1);
		t.addVertexWithUV(xMax, yMax, zMax, 1, 1);
		t.addVertexWithUV(xMax, yMax, zMin, 1, 0);
		
		//Bottom
		t.addVertexWithUV(xMin, yMin, zMin, 0, 1);
		t.addVertexWithUV(xMax, yMin, zMin, 1, 1);
		t.addVertexWithUV(xMax, yMin, zMax, 1, 0);
		t.addVertexWithUV(xMin, yMin, zMax, 0, 0);
		t.draw();
	}
}
