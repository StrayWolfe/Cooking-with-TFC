package straywolfe.cookingwithtfc.client.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import straywolfe.cookingwithtfc.client.model.ModelGourd;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.tileentity.TileGourd;

@SideOnly(Side.CLIENT)
public class TESRGourd extends TileEntitySpecialRenderer
{
	private ModelGourd modelGourd = new ModelGourd(0, 0, 40, 20);
	private static final ResourceLocation melonTexture = new ResourceLocation(ModInfo.ModID, "textures/blocks/Watermelon.png");
	private static final ResourceLocation pumpkinTexture = new ResourceLocation(ModInfo.ModID, "textures/blocks/Pumpkin.png");
	private static final ResourceLocation lanternTexture = new ResourceLocation(ModInfo.ModID, "textures/blocks/JackOLantern.png");
	private static final ResourceLocation lanternOnTexture = new ResourceLocation(ModInfo.ModID, "textures/blocks/JackOLanternOn.png");
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTickTime) 
	{
		if(tileentity != null && tileentity instanceof TileGourd)
			this.renderTileEntityAt((TileGourd)tileentity, (float)x, (float)y, (float)z);
	}
	
	public void renderTileEntityAt(TileGourd te, float x, float y, float z)
    {	
		int type = te.getType();
		
		if(type != -1)
		{
			ModelGourd modelgourd = modelGourd;
			float rotation = (float)(te.getRotation() * 360) / 16.0F;
			
			if(type == 1)
				bindTexture(melonTexture);
			else if(type == 2)
			{
				if(te.getHourPlaced() > -1)
					bindTexture(lanternOnTexture);
				else
					bindTexture(lanternTexture);
			}
			else
				bindTexture(pumpkinTexture);
			
			GL11.glPushMatrix();
	        GL11.glDisable(GL11.GL_CULL_FACE);
	        
	        GL11.glTranslatef(x + 0.5F, y, z + 0.5F);
	        
	        float f4 = 0.0625F;
	        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	        GL11.glScalef(-1.0F, -1.0F, 1.0F);
	        GL11.glEnable(GL11.GL_ALPHA_TEST);
	        modelgourd.render((Entity)null, 0.0F, 0.0F, 0.0F, rotation, 0.0F, f4);
	        GL11.glPopMatrix();
		}
	}
}
