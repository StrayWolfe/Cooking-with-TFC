package straywolfe.cookingwithtfc.client.render;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Render.TESR.TESRBase;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import straywolfe.cookingwithtfc.common.lib.Textures;
import straywolfe.cookingwithtfc.common.tileentity.TileBowl;

public class TESRBowl extends TESRBase
{
	public TESRBowl()
	{
		super();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity TileEntity, double x, double y, double z, float f)
	{
		if(TileEntity.getWorldObj() != null && TileEntity instanceof TileBowl)
		{
			TileBowl te = (TileBowl)TileEntity;
			float xCoord = te.getBowlCoord(0);
			float zCoord = te.getBowlCoord(1);
			
			if(xCoord != -1 && zCoord != -1)
			{
				Tessellator t = Tessellator.instance;
				ResourceLocation salad = Textures.Block.VEGGYSALAD;
				
				if(te.getSaladType() == 2)
					salad = Textures.Block.POTATOSALAD;
				else if(te.getSaladType() == 1)
					salad = Textures.Block.FRUITSALAD;
				
				GL11.glPushMatrix();
				GL11.glTranslatef((float) x + xCoord - 0.1F, (float) y, (float) z + zCoord - 0.1F);
				
				if(te.getSaladContents()[3] != null)
					drawItem(t, te, (0.08F * 3/3) + 0.04F, 0.2F, salad);
				else if(te.getSaladContents()[2] != null)
					drawItem(t, te, (0.08F * 2/3) + 0.04F, 0.2F, salad);
				else if(te.getSaladContents()[1] != null)
					drawItem(t, te, (0.08F * 1/3) + 0.04F, 0.2F, salad);
				else if(te.getSaladContents()[0] != null)
					drawItem(t, te, (0.08F * 0/3) + 0.04F, 0.2F, salad);
				
				GL11.glPopMatrix();
			}
		}
	}
	
	private void drawItem(Tessellator t, TileBowl te, float level, float size, ResourceLocation salad)
	{	
		float minX = 0.02F;
		float maxX = size - 0.02F;
		float minY = level;
		float maxY = level + 0.05F;
		float minZ = 0.02F;
		float maxZ = size - 0.02F;
		
		TFC_Core.bindTexture(salad);
		t.startDrawingQuads();
		t.setNormal(0.0F, 1.0F, 0.0F);		
		t.addVertexWithUV(minX, maxY, minZ, 0,   0);
		t.addVertexWithUV(minX, maxY, maxZ, 0,   0.5);
		t.addVertexWithUV(maxX, maxY, maxZ, 0.5, 0.5);
		t.addVertexWithUV(maxX, maxY, minZ, 0.5, 0);
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(0.0F, 0.0F, 1.0F);
		
		//SOUTH
		t.addVertexWithUV(0,    minY, 0,    0,    0);
		t.addVertexWithUV(0,    minY, size, 0,    0.5);
		t.addVertexWithUV(minX, maxY, maxZ, 0.25, 0.5);
		t.addVertexWithUV(minX, maxY, minZ, 0.25, 0);	
		
		//WEST
		t.addVertexWithUV(0,    minY, size, 0,    0);
		t.addVertexWithUV(size, minY, size, 0,    0.5);
		t.addVertexWithUV(maxX, maxY, maxZ, 0.25, 0.5);
		t.addVertexWithUV(minX, maxY, maxZ, 0.25, 0);
		
		//NORTH
		t.addVertexWithUV(size, minY, size, 0,    0);
		t.addVertexWithUV(size, minY, 0,    0,    0.5);
		t.addVertexWithUV(maxX, maxY, minZ, 0.25, 0.5);
		t.addVertexWithUV(maxX, maxY, maxZ, 0.25, 0);
		
		//EAST
		t.addVertexWithUV(size, minY, 0,    0,    0);
		t.addVertexWithUV(0,    minY, 0,    0,    0.5);
		t.addVertexWithUV(minX, maxY, minZ, 0.25, 0.5);
		t.addVertexWithUV(maxX, maxY, minZ, 0.25, 0);
		
		t.draw();
	}
}
