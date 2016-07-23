package straywolfe.cookingwithtfc.client.render;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Render.TESR.TESRBase;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import straywolfe.cookingwithtfc.common.tileentity.TileCookingPot;

public class TESRCookingPot extends TESRBase
{
	public TESRCookingPot()
	{
		super();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity TileEntity, double x, double y, double z, float f)
	{
		if(TileEntity.getWorldObj() != null && TileEntity instanceof TileCookingPot)
		{
			TileCookingPot te = (TileCookingPot)TileEntity;
			
			if(!te.getHasLid() ||  te.getIsDone())
			{				
				for(int i = TileCookingPot.INVFOODSTART; i <= TileCookingPot.INVFOODEND; i++)
				{
					int m = i - TileCookingPot.INVFOODSTART;
					double xLoc = x + 0.3125;
					double yLoc = y + 0.102;
					double zLoc = z + 0.3125;
					
					if(m == 0 || m == 5)
					{
						xLoc = x + 0.41;
						zLoc = z + 0.41;
					}
					
					if(m % 2 == 0 && m != 0)
						xLoc += 0.195;
					
					if(m > 4)
						yLoc += 0.189;
					
					if(m == 3 || m == 4 || m == 8 || m == 9)
						zLoc += 0.195;
					
					if(te.getStackInSlot(i) != null)
					{
						GL11.glPushMatrix();
						GL11.glTranslated(xLoc, yLoc, zLoc);
						drawItem(te.getStackInSlot(i), 0, 0.18, 0, 0.18);
						GL11.glPopMatrix();
					}
				}
			}
		}
	}
	
	private void drawItem(ItemStack is, double minX, double maxX, double minZ, double maxZ)
	{
		if (!(is.getItem() instanceof ItemBlock))
		{
			TFC_Core.bindTexture(TextureMap.locationItemsTexture);
			float minU = is.getIconIndex().getMinU();
			float maxU = is.getIconIndex().getMaxU();
			float minV = is.getIconIndex().getMinV();
			float maxV = is.getIconIndex().getMaxV();
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			tessellator.addVertexWithUV(minX, 0.0F, maxZ, minU, maxV);
			tessellator.addVertexWithUV(maxX, 0.0F, maxZ, maxU, maxV);
			tessellator.addVertexWithUV(maxX, 0.0F, minZ, maxU, minV);
			tessellator.addVertexWithUV(minX, 0.0F, minZ, minU, minV);
			tessellator.draw();
		}
	}
}
