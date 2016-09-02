package straywolfe.cookingwithtfc.client.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.bioxx.tfc.Render.TESR.TESRBase;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Interfaces.IFood;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import straywolfe.cookingwithtfc.common.tileentity.TileClayOven;

public class TESRClayOven extends TESRBase
{
	float frame;
	
	public TESRClayOven()
	{
		super();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
	{
		if (tileentity.getWorldObj() != null && tileentity instanceof TileClayOven && ((TileClayOven)tileentity).hasFood())
		{
			TileClayOven te = (TileClayOven)tileentity;
			int meta = te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord, te.zCoord);
			float[] loc = getLocation(meta);
			
			Tessellator t = Tessellator.instance;
			GL11.glPushMatrix();
			GL11.glTranslatef((float)x + loc[0], (float)y + loc[1], (float)z + loc[2]);
			GL11.glRotatef(loc[3], 0.0F, 1.0F, 0.0F);
			
			drawFood(t, te);
			
			GL11.glPopMatrix();
		}
	}
	
	private void drawFood(Tessellator t, TileClayOven te)
	{		
		if(RenderManager.instance.options.fancyGraphics)
		{
			if(te.getStackInSlot(4) != null)
				draw3DItem(te.getStackInSlot(4), 0.475F, 0.4F);
			
			if(te.getStackInSlot(5) != null)
				draw3DItem(te.getStackInSlot(5), 0.775F, 0.4F);
		}
		else
		{
			if(te.getStackInSlot(4) != null)
				drawItem(t, te.getStackInSlot(4), 0.5, 0.2, 0.45, 0.15);
			
			if(te.getStackInSlot(5) != null)
				drawItem(t, te.getStackInSlot(5), 0.8, 0.5, 0.45, 0.15);
		}
	}
	
	private void drawItem(Tessellator t, ItemStack is, double minX, double maxX, double minZ, double maxZ)
	{
		if (is != null && !(is.getItem() instanceof ItemBlock))
		{
			bindTexture(TextureMap.locationItemsTexture);
			
			float minU = is.getIconIndex().getMinU();
			float maxU = is.getIconIndex().getMaxU();
			float minV = is.getIconIndex().getMinV();
			float maxV = is.getIconIndex().getMaxV();
			
			if(is.getItem() instanceof IFood && Food.isCooked(is))
			{
				int color = Food.getCookedColorMultiplier(is);
				GL11.glColor4f(((color & 0xFF0000)>>16)/255f, ((color & 0x00ff00)>>8)/255f, (color & 0x0000ff)/255f, 1);				
			}
			
			t.startDrawingQuads();
			t.setNormal(0.0F, 1.0F, 0.0F);
			t.addVertexWithUV(minX, 0.102F, maxZ, minU, maxV);
			t.addVertexWithUV(maxX, 0.102F, maxZ, maxU, maxV);
			t.addVertexWithUV(maxX, 0.102F, minZ, maxU, minV);
			t.addVertexWithUV(minX, 0.102F, minZ, minU, minV);
			t.draw();
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	private void draw3DItem(ItemStack is, float itemX, float itemZ)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(itemX, 0.1F, itemZ);
		GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
		GL11.glScalef(0.25F, 0.25F, 1F);
		
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
    	bindTexture(texturemanager.getResourceLocation(is.getItemSpriteNumber()));
        TextureUtil.func_152777_a(false, false, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        IIcon icon = is.getIconIndex();

        int color = is.getItem().getColorFromItemStack(is, 0);
        float red = ((color & 0xFF0000)>>16)/255f;
        float green = ((color & 0x00ff00)>>8)/255f;
        float blue = (color & 0x0000ff)/255f;
        
        if(is.getItem() instanceof IFood && Food.isCooked(is))
		{
			color = Food.getCookedColorMultiplier(is);
			red = (((color & 0xFF0000)>>16)/255f) - 0.2F;
            green = (((color & 0x00ff00)>>8)/255f) - 0.2F;
            blue = ((color & 0x0000ff)/255f) - 0.2F;
		}

        bindTexture(TextureMap.locationItemsTexture);

        GL11.glColor4f(red, green, blue, 1.0F);
        
        ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(),
        		icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        bindTexture(texturemanager.getResourceLocation(is.getItemSpriteNumber()));
        TextureUtil.func_147945_b();
		
		GL11.glPopMatrix();
	}
	
	public float[] getLocation(int dir)
	{
		float[] loc = new float[4];
		
		//South
		if(dir == 0 || dir == 4)
		{
			loc[0] = 0f;
			loc[1] = 0f;
			loc[2] = 0f;
			loc[3] = 0f;
		}
		//West
		else if(dir == 1 || dir == 5)
		{
			loc[0] = 1f;
			loc[1] = 0f;
			loc[2] = 0f;
			loc[3] = 270f;
		}
		//North
		else if(dir == 2 || dir == 6)
		{
			loc[0] = 1f;
			loc[1] = 0f;
			loc[2] = 1f;
			loc[3] = 180f;
		}
		//East
		else if(dir == 3 || dir == 7)
		{
			loc[0] = 0f;
			loc[1] = 0f;
			loc[2] = 1f;
			loc[3] = 90f;
		}
		
		return loc;
	}
}
