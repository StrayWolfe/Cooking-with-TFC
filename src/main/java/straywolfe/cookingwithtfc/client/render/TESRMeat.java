package straywolfe.cookingwithtfc.client.render;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Render.TESR.TESRBase;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import straywolfe.cookingwithtfc.common.tileentity.TileMeat;

public class TESRMeat extends TESRBase
{
	public TESRMeat()
	{
		super();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity TileEntity, double x, double y, double z, float f)
	{
		if(TileEntity.getWorldObj() != null && TileEntity instanceof TileMeat)
		{
			TileMeat te = (TileMeat)TileEntity;			
			EntityItem customitem = new EntityItem(field_147501_a.field_147550_f);
			customitem.hoverStart = 0f;
			float blockScale = 1F;
			float xCoord = te.getMeatCoord(0);
			float zCoord = te.getMeatCoord(1);
			
			if(xCoord == -1 && zCoord == -1)
				return;
			
			if(RenderManager.instance.options.fancyGraphics)
			{
				if(te.getplacedMeat() != null)
				{
					GL11.glPushMatrix();
					GL11.glTranslatef((float)x + 0.25F + te.getMeatCoord(0), (float)y + 0.06F, (float)z + 0.475F + te.getMeatCoord(1));
					GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
					GL11.glScalef(blockScale, blockScale, blockScale * 2.75F);
					customitem.setEntityItemStack(te.getplacedMeat());
					itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
					GL11.glPopMatrix();
				}
			}
			else
			{
				GL11.glPushMatrix();
				GL11.glTranslated(x + te.getMeatCoord(0), y + 0.001, z + te.getMeatCoord(1));
				drawItem(te, 0, 0.5, 0, 0.5);
				GL11.glPopMatrix();
			}
		}
	}
	
	private void drawItem(TileMeat te, double minX, double maxX, double minZ, double maxZ)
	{
		if (te.getplacedMeat() != null && !(te.getplacedMeat().getItem() instanceof ItemBlock))
		{
			TFC_Core.bindTexture(TextureMap.locationItemsTexture);
			float minU = te.getplacedMeat().getIconIndex().getMinU();
			float maxU = te.getplacedMeat().getIconIndex().getMaxU();
			float minV = te.getplacedMeat().getIconIndex().getMinV();
			float maxV = te.getplacedMeat().getIconIndex().getMaxV();
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
