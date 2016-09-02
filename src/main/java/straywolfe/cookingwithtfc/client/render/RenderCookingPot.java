package straywolfe.cookingwithtfc.client.render;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Blocks.Flora.BlockLogNatural;
import com.bioxx.tfc.Blocks.Flora.BlockLogNatural2;
import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.Vertex;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.tileentity.TileCookingPot;

public class RenderCookingPot implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(tileentity != null && tileentity instanceof TileCookingPot)
		{
			Block Firepit = TFCBlocks.firepit;
			Block Pot = CWTFCBlocks.cookingPot;
			TileCookingPot te = (TileCookingPot)tileentity;
			int meta = world.getBlockMetadata(x, y, z);
			CWTFCRenderer myRenderer = new CWTFCRenderer(x, y, z, Pot, renderer);
			
			renderer.setRenderBounds(0, 0, 0, 1, 0.1f, 1);
			
			//Render fire
			if (meta == 0)
				renderer.renderBlockUsingTexture(Firepit, x, y, z, Firepit.getIcon(0, 0));
			else
				renderer.renderBlockUsingTexture(Firepit, x, y, z, Firepit.getIcon(0, 1));
			
			//Render Logs
			if(te.getStackInSlot(0) != null)
			{
				int itemMeta = te.getStackInSlot(0).getItemDamage();
				if(itemMeta < 16)
					renderBlock(0.2F, 0.8F, 0F, 0.09F, 0.25F, 0.3125F, (BlockLogNatural)TFCBlocks.logNatural, itemMeta, myRenderer);
				else
					renderBlock(0.2F, 0.8F, 0F, 0.09F, 0.25F, 0.3125F, (BlockLogNatural2)TFCBlocks.logNatural2, itemMeta - 16, myRenderer);
			}
			
			if(te.getStackInSlot(1) != null)
			{
				int itemMeta = te.getStackInSlot(1).getItemDamage();
				if(itemMeta < 16)
					renderBlock(0.2F, 0.8F, 0F, 0.09F, 0.390625F, 0.453125F, (BlockLogNatural)TFCBlocks.logNatural, itemMeta, myRenderer);
				else
					renderBlock(0.2F, 0.8F, 0F, 0.09F, 0.390625F, 0.453125F, (BlockLogNatural2)TFCBlocks.logNatural2, itemMeta - 16, myRenderer);
			}
			
			if(te.getStackInSlot(2) != null)
			{
				int itemMeta = te.getStackInSlot(2).getItemDamage();
				if(itemMeta < 16)
					renderBlock(0.2F, 0.8F, 0F, 0.09F, 0.546875F, 0.609375F, (BlockLogNatural)TFCBlocks.logNatural, itemMeta, myRenderer);
				else
					renderBlock(0.2F, 0.8F, 0F, 0.09F, 0.546875F, 0.609375F, (BlockLogNatural2)TFCBlocks.logNatural2, itemMeta - 16, myRenderer);
			}
			
			if(te.getStackInSlot(3) != null)
			{
				int itemMeta = te.getStackInSlot(3).getItemDamage();
				renderer.setRenderBounds(0.2, 0, 0.6875f, 0.8f, 0.09, 0.75f);
				if(itemMeta < 16)
					renderBlock(0.2F, 0.8F, 0F, 0.09F, 0.6875F, 0.75F, (BlockLogNatural)TFCBlocks.logNatural, itemMeta, myRenderer);
				else
					renderBlock(0.2F, 0.8F, 0F, 0.09F, 0.6875F, 0.75F, (BlockLogNatural2)TFCBlocks.logNatural2, itemMeta - 16, myRenderer);
			}
			
			//Bottom Layer
			renderer.setRenderBounds(0.3125f, 0, 0.3125f, 0.6875f, 0.1f, 0.6875f);
			renderer.renderStandardBlock(Pot, x, y, z);
			
			//Middle
			renderer.setRenderBounds(0.3125f, 0.1f, 0.25f, 0.6875f, 0.35f, 0.3125f);
			renderer.renderStandardBlock(Pot, x, y, z);
			
			renderer.setRenderBounds(0.3125f, 0.1f, 0.6875f, 0.6875f, 0.35f, 0.75f);
			renderer.renderStandardBlock(Pot, x, y, z);
			
			renderer.setRenderBounds(0.6875f, 0.1f, 0.3125f, 0.75f, 0.35f, 0.6875f);
			renderer.renderStandardBlock(Pot, x, y, z);
			
			renderer.setRenderBounds(0.25f, 0.1f, 0.3125f, 0.3125f, 0.35f, 0.6875f);
			renderer.renderStandardBlock(Pot, x, y, z);
			
			if(te.getHasLid())
			{
				float xLid = 0.3125f;
				float zLid = 0.3125f;
				float xKnob = 0.4375f;
				float zKnob = 0.4375f;
				
				if(te.getIsDone())
				{
					xLid += 0.05f;
					zLid += 0.05f;
					xKnob += 0.05f;
					zKnob += 0.05f;
				}
				
				//Lid
				renderer.setRenderBounds(xLid, 0.35f, zLid, xLid + 0.375f, 0.4f, zLid + 0.375f);
				renderer.renderStandardBlock(Pot, x, y, z);
				
				//Top Knob
				renderer.setRenderBounds(xKnob, 0.4f, zKnob, xKnob + 0.125f, 0.5f, zKnob + 0.125f);
				renderer.renderStandardBlock(Pot, x, y, z);
			}
			
			if(!te.getHasLid() ||  te.getIsDone())
			{
				//Render Liquid
				if(te.getCookingPotFluid() != null)
				{
					double yLoc = Math.max(0.11f, 0.3f * te.getCookingPotFluid().amount/(float)te.getMaxLiquid());				
					int color = te.getCookingPotFluid().getFluid().getColor();
					IIcon icon = te.getCookingPotFluid().getFluid().getIcon();
					renderFluid(0.3125, 0.6875, yLoc, 0.3125, 0.6875, x, y, z, block, renderer, icon, color);
				}
				
				for(int i = TileCookingPot.INVFOODSTART; i <= TileCookingPot.INVFOODEND; i++)
				{
					int m = i - TileCookingPot.INVFOODSTART;
					float xLoc = 0.3125F;
					float yLoc = 0.102F;
					float zLoc = 0.3125F;
					
					if(m == 0 || m == 5)
					{
						xLoc = 0.41F;
						zLoc = 0.41F;
					}
					
					if(m % 2 == 0 && m != 0)
						xLoc += 0.195F;
					
					if(m > 4)
						yLoc += 0.189F;
					
					if(m == 3 || m == 4 || m == 8 || m == 9)
						zLoc += 0.195F;
					
					if(te.getStackInSlot(i) != null)
					{					 
						GL11.glPushMatrix();
						
						ItemStack is = te.getStackInSlot(i);
						if(is != null)
						{
							myRenderer.icon = is.getIconIndex();
							myRenderer.draw2DItem(0.18F, xLoc, yLoc, zLoc);
						}
						
						GL11.glPopMatrix();
					}
				}
			}
		}
			
		return false;
	}
	
	private void renderBlock(float xMin, float xMax, float yMin, float yMax, float zMin, float zMax, BlockLogNatural log, int meta, CWTFCRenderer myRenderer)
	{
		myRenderer.icon = log.innerIcons[meta];
		
		myRenderer.renderSquareXNeg(xMin, yMin, yMax, zMin, zMax);
		
		myRenderer.renderSquareXPos(xMax, yMin, yMax, zMin, zMax);
		
		myRenderer.icon = log.sideIcons[meta];
		
		myRenderer.renderSquareYPos(xMin, xMax, yMax, zMin, zMax, 0, 1, 0, 0.25F);
		
		myRenderer.renderSquareZNeg(xMin, xMax, yMin, yMax, zMin, 0, 1, 0, 0.25F);
		
		myRenderer.renderSquareZPos(xMin, xMax, yMin, yMax, zMax, 0, 1, 0, 0.25F);
	}
	
	private static void renderFluid(double xmin, double xmax, double ymax, double zmin, double zmax, int x, int y, int z, Block block, RenderBlocks renderer, IIcon icon, int color)
    {
		double xMin = x + xmin;
		double xMax = x + xmax;
		double yMax = y + ymax;
		double zMin = z + zmin;
		double zMax = z + zmax;
		
		Vertex bottomRight = new Vertex((float)xMin, (float)yMax, (float)zMin);
		Vertex bottomLeft = new Vertex((float)xMax, (float)yMax, (float)zMin);
		Vertex topRight = new Vertex((float)xMin, (float)yMax, (float)zMax);
		Vertex topLeft = new Vertex((float)xMax, (float)yMax, (float)zMax);
		
		Tessellator tessellator = Tessellator.instance;

        if (renderer.hasOverrideBlockTexture())
        {
        	icon = renderer.overrideBlockTexture;
        }

        double uMin = (double)icon.getInterpolatedU(0);
        double uMax = (double)icon.getInterpolatedU(16);
        double vMin = (double)icon.getInterpolatedV(0);
        double vMax = (double)icon.getInterpolatedV(16);

        double uiMax = uMax;
        double uiMin = uMin;
        double viMin = vMin;
        double viMax = vMax;
        
		float red = (float)(color >> 16 & 255) / 255.0F;
        float green = (float)(color >> 8 & 255) / 255.0F;
        float blue = (float)(color & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float f3 = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
            float f4 = (red * 30.0F + green * 70.0F) / 100.0F;
            float f5 = (red * 30.0F + blue * 70.0F) / 100.0F;
            red = f3;
            green = f4;
            blue = f5;
        }
        
        tessellator.setColorOpaque_F(red, green, blue);        
    	tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        
        tessellator.addVertexWithUV(topLeft.x, topLeft.y, topLeft.z, uMax, vMax);
        tessellator.addVertexWithUV(bottomLeft.x, bottomLeft.y, bottomLeft.z, uiMax, viMin);
        tessellator.addVertexWithUV(bottomRight.x, bottomRight.y, bottomRight.z, uMin, vMin);
        tessellator.addVertexWithUV(topRight.x, topRight.y, topRight.z, uiMin, viMax);
        
        renderer.clearOverrideBlockTexture();
    }

	@Override
	public boolean shouldRender3DInInventory(int modelId) 
	{
		return true;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) 
	{
		Block Firepit = TFCBlocks.firepit;
		Block Pot = CWTFCBlocks.cookingPot;
		
		renderer.setRenderBounds(0, 0, 0, 1, 0.1f, 1);
		renderInvBlock(Firepit, 0, renderer);
		
		renderer.setRenderBounds(0.3125f, 0, 0.3125f, 0.6875f, 0.1f, 0.6875f);
		renderInvBlock(Pot, 0, renderer);
		
		renderer.setRenderBounds(0.25f, 0.1f, 0.25f, 0.75f, 0.3f, 0.75f);
		renderInvBlock(Pot, 0, renderer);
		
		renderer.setRenderBounds(0.3125f, 0.3f, 0.3125f, 0.6875f, 0.4f, 0.6875f);
		renderInvBlock(Pot, 0, renderer);
		
		renderer.setRenderBounds(0.4375f, 0.4f, 0.4375f, 0.5625f, 0.5f, 0.5625f);
		renderInvBlock(Pot, 0, renderer);
	}
	
	public static void renderInvBlock(Block block, int m, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, m));
		var14.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public int getRenderId() 
	{
		return 0;
	}

}
