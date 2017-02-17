package straywolfe.cookingwithtfc.client.render;

import com.bioxx.tfc.Core.TFC_Core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.model.obj.Vertex;

public class CWTFCRenderer 
{	
	public int x;
	public int y;
	public int z;
	public Block block;
	public RenderBlocks renderer;
	public IIcon icon;
	public int uvRotate;
	
	public CWTFCRenderer(int x, int y, int z, Block block, RenderBlocks renderer)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.renderer = renderer;
	}
	
	private void renderFaceYNeg(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
			float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
		Tessellator tessellator = Tessellator.instance;

        if (renderer.hasOverrideBlockTexture())
        {
        	icon = renderer.overrideBlockTexture;
        }

        double uMin = (double)icon.getInterpolatedU(umin * 16.0D);
        double uMax = (double)icon.getInterpolatedU(umax * 16.0D);
        double vMin = (double)icon.getInterpolatedV(vmin * 16.0D);
        double vMax = (double)icon.getInterpolatedV(vmax * 16.0D);
        
        double uiMax = (double)icon.getInterpolatedU(uimax * 16.0D);
        double uiMin = (double)icon.getInterpolatedU(uimin * 16.0D);
        double viMin = (double)icon.getInterpolatedV(vimin * 16.0D);
        double viMax = (double)icon.getInterpolatedV(vimax * 16.0D);

        if (umin < 0.0D || umax > 1.0D)
        {
            uMin = (double)icon.getMinU();
            uMax = (double)icon.getMaxU();
        }

        if (vmin < 0.0D || vmax > 1.0D)
        {
            vMin = (double)icon.getMinV();
            vMax = (double)icon.getMaxV();
        }
        
        if (uvRotate == 2)
        {
        	uMin = (double)icon.getInterpolatedU(vmin * 16.0D);
        	vMin = (double)icon.getInterpolatedV(16.0D - umax * 16.0D);
            uMax = (double)icon.getInterpolatedU(vmax * 16.0D);
            vMax = (double)icon.getInterpolatedV(16.0D - umin * 16.0D);
            viMin = vMin;
            viMax = vMax;
            uiMin = uMin;
            uiMax = uMax;
            vMin = vMax;
            vMax = viMin;
        }
        else if (uvRotate == 1)
        {
        	uMin = (double)icon.getInterpolatedU(16.0D - vmax * 16.0D);
        	vMin = (double)icon.getInterpolatedV(umin * 16.0D);
            uMax = (double)icon.getInterpolatedU(16.0D - vmin * 16.0D);
            vMax = (double)icon.getInterpolatedV(umax * 16.0D);
            uiMin = uMax;
            uiMax = uMin;
            uMin = uMax;
            uMax = uiMax;
            viMin = vMax;
            viMax = vMin;
        }
        else if (uvRotate == 3)
        {
        	uMin = (double)icon.getInterpolatedU(16.0D - umin * 16.0D);
        	uMax = (double)icon.getInterpolatedU(16.0D - umax * 16.0D);
        	vMin = (double)icon.getInterpolatedV(16.0D - vmin * 16.0D);
        	vMax = (double)icon.getInterpolatedV(16.0D - vmax * 16.0D);
        	uiMin = uMax;
        	uiMax = uMin;
            viMin = vMin;
            viMax = vMax;
        }

    	int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float f = (float)(l >> 16 & 255) / 255.0F;
        float f1 = (float)(l >> 8 & 255) / 255.0F;
        float f2 = (float)(l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        
        f = 0.5F * f;
        f1 = 0.5F * f1;
        f2 = 0.5F * f2;
        
        int m = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
        float n = ((topRight.y + topLeft.y + bottomRight.y + bottomLeft.y) - (y * 4)) / 4;
    	tessellator.setBrightness(n > 0.0D ? m : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z));
        tessellator.setColorOpaque_F(f, f1, f2);
        
        tessellator.addVertexWithUV(bottomRight.x, bottomRight.y, topLeft.z, uiMin, viMax);
        tessellator.addVertexWithUV(topRight.x, topRight.y, bottomLeft.z, uMin, vMin);
        tessellator.addVertexWithUV(topLeft.x, topLeft.y, bottomRight.z, uiMax, viMin);
        tessellator.addVertexWithUV(bottomLeft.x, bottomLeft.y, topRight.z, uMax, vMax);
        
        renderer.clearOverrideBlockTexture();
    }
	
	public void renderQuadYNeg(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
			float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
	{
		renderFaceYNeg(new Vertex(x + bottomRight.x, y + bottomRight.y, z + bottomRight.z), 
					   new Vertex(x + bottomLeft.x, y + bottomLeft.y, z + bottomLeft.z), 
					   new Vertex(x + topRight.x, y + topRight.y, z + topRight.z), 
					   new Vertex(x + topLeft.x, y + topLeft.y, z + topLeft.z), 
					   umin, uimin, umax, uimax, vmin, vimin, vmax, vimax);
	}
	
	public void renderQuadYNeg(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
			float umin, float umax, float vmin, float vmax)
	{
		renderQuadYNeg(bottomRight, bottomLeft, topRight, topLeft, umin, umin, umax, umax, vmin, vmin, vmax, vmax);
	}
	
	public void renderQuadYNeg(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft)
	{		
		renderQuadYNeg(bottomRight, bottomLeft, topRight, topLeft, 0, 1, 0, 1);
	}
	
	public void renderSquareYNeg(double xmin, double xmax, double ymin, double zmin, double zmax,
			float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
		float xMin = x + (float)xmin;
		float xMax = x + (float)xmax;
		float yMin = y + (float)ymin;
		float zMin = z + (float)zmin;
		float zMax = z + (float)zmax;
		
		renderFaceYNeg(new Vertex(xMin, yMin, zMin), new Vertex(xMax, yMin, zMin), 
					   new Vertex(xMin, yMin, zMax), new Vertex(xMax, yMin, zMax), 
					   umin, umin, umax, umax, vmin, vmin, vmax, vmax);
    }
	
	public void renderSquareYNeg(double xmin, double xmax, double ymin, double zmin, double zmax, 
			float umin, float umax, float vmin, float vmax)
    {
		renderSquareYNeg(xmin, xmax, ymin, zmin, zmax, umin, umin, umax, umax, vmin, vmin, vmax, vmax);
    }
	
	/**
     * Renders the given texture to the bottom face of the block. 
     * Args: xmin, xmax, ymin, zmin, zmax, x, y, z, block, renderer, icon
     */
	public void renderSquareYNeg(double xmin, double xmax, double ymin, double zmin, double zmax)
    {
		renderSquareYNeg(xmin, xmax, ymin, zmin, zmax, 0, 1, 0, 1);
    }
	
	private void renderFaceYPos(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
			float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
		Tessellator tessellator = Tessellator.instance;

        if (renderer.hasOverrideBlockTexture())
        {
        	icon = renderer.overrideBlockTexture;
        }

        double uMin = (double)icon.getInterpolatedU(umin * 16.0D);
        double uMax = (double)icon.getInterpolatedU(umax * 16.0D);
        double vMin = (double)icon.getInterpolatedV(vmin * 16.0D);
        double vMax = (double)icon.getInterpolatedV(vmax * 16.0D);
        
        double uiMax = (double)icon.getInterpolatedU(uimax * 16.0D);
        double uiMin = (double)icon.getInterpolatedU(uimin * 16.0D);
        double viMin = (double)icon.getInterpolatedV(vimin * 16.0D);
        double viMax = (double)icon.getInterpolatedV(vimax * 16.0D);

        if (umin < 0.0D || umax > 1.0D)
        {
            uMin = (double)icon.getMinU();
            uMax = (double)icon.getMaxU();
        }

        if (vmin < 0.0D || vmax > 1.0D)
        {
            vMin = (double)icon.getMinV();
            vMax = (double)icon.getMaxV();
        }
        
        if (uvRotate == 1)
        {            
            uMin = (double)icon.getInterpolatedU(vmin * 16.0D);
        	vMin = (double)icon.getInterpolatedV(16.0D - umax * 16.0D);
            uMax = (double)icon.getInterpolatedU(vmax * 16.0D);
            vMax = (double)icon.getInterpolatedV(16.0D - umin * 16.0D);
            viMin = vMin;
            viMax = vMax;
            uiMax = uMin;
            uiMin = uMax;
            vMin = vMax;
            vMax = viMin;
        }
        else if (uvRotate == 2)
        {            
            uMin = (double)icon.getInterpolatedU(16.0D - vmax * 16.0D);
        	vMin = (double)icon.getInterpolatedV(umin * 16.0D);
            uMax = (double)icon.getInterpolatedU(16.0D - vmin * 16.0D);
            vMax = (double)icon.getInterpolatedV(umax * 16.0D);
            uiMax = uMax;
            uiMin = uMin;
            uMin = uMax;
            uMax = uiMin;
            viMin = vMax;
            viMax = vMin;
        }
        else if (uvRotate == 3)
        {
        	uMin = (double)icon.getInterpolatedU(16.0D - umin * 16.0D);
        	uMax = (double)icon.getInterpolatedU(16.0D - umax * 16.0D);
        	vMin = (double)icon.getInterpolatedV(16.0D - vmin * 16.0D);
        	vMax = (double)icon.getInterpolatedV(16.0D - vmax * 16.0D);
        	uiMin = uMax;
        	uiMax = uMin;
        	viMax = vMin;
        	viMin = vMax;
        }

    	int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float f = (float)(l >> 16 & 255) / 255.0F;
        float f1 = (float)(l >> 8 & 255) / 255.0F;
        float f2 = (float)(l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        
    	int m = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
    	float n = ((topRight.y + topLeft.y + bottomRight.y + bottomLeft.y) - (y * 4))/4;
    	tessellator.setBrightness(n < 1.0D ? m : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z));
        tessellator.setColorOpaque_F(f, f1, f2);
        
        tessellator.addVertexWithUV(bottomLeft.x, bottomLeft.y, topRight.z, uMax, vMax);
    	tessellator.addVertexWithUV(topLeft.x, topLeft.y, bottomRight.z, uiMax, viMin);
    	tessellator.addVertexWithUV(topRight.x, topRight.y, bottomLeft.z, uMin, vMin);
    	tessellator.addVertexWithUV(bottomRight.x, bottomRight.y, topLeft.z, uiMin, viMax);
        
        renderer.clearOverrideBlockTexture();
    }
	
	public void renderQuadYPos(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
			float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
		renderFaceYPos(new Vertex(x + bottomRight.x, y + bottomRight.y, z + bottomRight.z), 
					   new Vertex(x + bottomLeft.x, y + bottomLeft.y, z + bottomLeft.z), 
					   new Vertex(x + topRight.x, y + topRight.y, z + topRight.z), 
					   new Vertex(x + topLeft.x, y + topLeft.y, z + topLeft.z), 
					   umin, uimin, umax, uimax, vmin, vimin, vmax, vimax);
    }
	
	public void renderQuadYPos(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
			float umin, float umax, float vmin, float vmax)
    {		
		renderQuadYPos(bottomRight, bottomLeft, topRight, topLeft, umin, umin, umax, umax, vmin, vmin, vmax, vmax);
    }
	
	public void renderQuadYPos(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft)
    {		
		renderQuadYPos(bottomRight, bottomLeft, topRight, topLeft, 0, 1, 0, 1);
    }
	
	public void renderSquareYPos(double xmin, double xmax, double ymax, double zmin, double zmax, 
			float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
		double xMin = x + xmin;
		double xMax = x + xmax;
		double yMax = y + ymax;
		double zMin = z + zmin;
		double zMax = z + zmax;
		
		renderFaceYPos(new Vertex((float)xMin, (float)yMax, (float)zMin), 
					   new Vertex((float)xMax, (float)yMax, (float)zMin), 
					   new Vertex((float)xMin, (float)yMax, (float)zMax), 
					   new Vertex((float)xMax, (float)yMax, (float)zMax), 
					   umin, uimin, umax, uimax, vmin, vimin, vmax, vimax);        
    }
	
	/**
     * Renders the given texture to the top face of the block. 
     * Args: xmin, xmax, ymax, zmin, zmax, x, y, z, block, renderer, icon
     */
	public void renderSquareYPos(double xmin, double xmax, double ymax, double zmin, double zmax, 
			float umin, float umax, float vmin, float vmax)
    {
		renderSquareYPos(xmin, xmax, ymax, zmin, zmax, umin, umin, umax, umax, vmin, vmin, vmax, vmax);
    }
	
	public void renderSquareYPos(double xmin, double xmax, double ymax, double zmin, double zmax)
    {
		renderSquareYPos(xmin, xmax, ymax, zmin, zmax, 0, 1, 0, 1);
    }
	
	private void renderFaceZNeg(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
			float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
		Tessellator tessellator = Tessellator.instance;

        if (renderer.hasOverrideBlockTexture())
        {
        	icon = renderer.overrideBlockTexture;
        }

        double uMin = (double)icon.getInterpolatedU(umin * 16.0D);
        double uMax = (double)icon.getInterpolatedU(umax * 16.0D);        
        double uiMin = (double)icon.getInterpolatedU(uimin * 16.0D);
        double uiMax = (double)icon.getInterpolatedU(uimax * 16.0D);

        if (renderer.field_152631_f)
        {
            uMax = (double)icon.getInterpolatedU((1.0D - umin) * 16.0D);
            uMin = (double)icon.getInterpolatedU((1.0D - umax) * 16.0D);
            
            uiMax = (double)icon.getInterpolatedU((1.0D - uimin) * 16.0D);
            uiMin = (double)icon.getInterpolatedU((1.0D - uimax) * 16.0D);
        }

        double vMin = (double)icon.getInterpolatedV(16.0D - vmax * 16.0D);
        double vMax = (double)icon.getInterpolatedV(16.0D - vmin * 16.0D);        
        double viMin = (double)icon.getInterpolatedV(16.0D - vimax * 16.0D);
        double viMax = (double)icon.getInterpolatedV(16.0D - vimin * 16.0D);

        if (umin < 0.0D || umax > 1.0D)
        {
            uMin = (double)icon.getMinU();
            uMax = (double)icon.getMaxU();
        }

        if (vmin < 0.0D || vmax > 1.0D)
        {
            vMin = (double)icon.getMinV();
            vMax = (double)icon.getMaxV();
        }
        
        if (uvRotate == 2)
        {
        	uMin = (double)icon.getInterpolatedU(vmin * 16.0D);
        	uMax = (double)icon.getInterpolatedU(vmax * 16.0D);
        	vMin = (double)icon.getInterpolatedV(16.0D - umin * 16.0D);
        	vMax = (double)icon.getInterpolatedV(16.0D - umax * 16.0D);
        	viMin = vMin;
        	viMax = vMax;
            uiMax = uMin;
            uiMin = uMax;
            vMin = vMax;
            vMax = viMin;
        }
        else if (uvRotate == 1)
        {
        	uMin = (double)icon.getInterpolatedU(16.0D - vmax * 16.0D);
        	uMax = (double)icon.getInterpolatedU(16.0D - vmin * 16.0D);
        	vMin = (double)icon.getInterpolatedV(umax * 16.0D);
        	vMax = (double)icon.getInterpolatedV(umin * 16.0D);
        	uiMax = uMax;
        	uiMin = uMin;
            uMin = uMax;
            uMax = uiMin;
            viMin = vMax;
            viMax = vMin;
        }
        else if (uvRotate == 3)
        {
        	uMin = (double)icon.getInterpolatedU(16.0D - umin * 16.0D);
        	uMax = (double)icon.getInterpolatedU(16.0D - umax * 16.0D);
        	vMin = (double)icon.getInterpolatedV(vmax * 16.0D);
        	vMax = (double)icon.getInterpolatedV(vmin * 16.0D);
        	uiMax = uMax;
        	uiMin = uMin;
        	viMin = vMin;
        	viMax = vMax;
        }

    	int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float f = (float)(l >> 16 & 255) / 255.0F;
        float f1 = (float)(l >> 8 & 255) / 255.0F;
        float f2 = (float)(l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        
        f = 0.8F * f;
        f1 = 0.8F * f1;
        f2 = 0.8F * f2;
        
    	int m = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
    	float n = ((topRight.z + topLeft.z + bottomRight.z + bottomLeft.z) - (z * 4)) / 4;
    	tessellator.setBrightness(n > 0.0D ? m : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1));
        tessellator.setColorOpaque_F(f, f1, f2);
        
        tessellator.addVertexWithUV(topRight.x, topRight.y, bottomLeft.z, uiMax, viMax);
        tessellator.addVertexWithUV(topLeft.x, topLeft.y, bottomRight.z, uMin, vMax);
        tessellator.addVertexWithUV(bottomLeft.x, bottomLeft.y, topRight.z, uiMin, viMin);
        tessellator.addVertexWithUV(bottomRight.x, bottomRight.y, topLeft.z, uMax, vMin);
        
        renderer.clearOverrideBlockTexture();
    }
	
	public void renderQuadZNeg(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
			float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
		renderFaceZNeg(new Vertex(x + bottomRight.x, y + bottomRight.y, z + bottomRight.z), 
					   new Vertex(x + bottomLeft.x, y + bottomLeft.y, z + bottomLeft.z), 
					   new Vertex(x + topRight.x, y + topRight.y, z + topRight.z), 
					   new Vertex(x + topLeft.x, y + topLeft.y, z + topLeft.z), 
					   umin, uimin, umax, uimax, vmin, vimin, vmax, vimax);
    }
	
	public void renderQuadZNeg(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
			float umin, float umax, float vmin, float vmax)
    {		
		renderQuadZNeg(bottomRight, bottomLeft, topRight, topLeft, umin, umin, umax, umax, vmin, vmin, vmax, vmax);
    }
	
	public void renderQuadZNeg(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft)
    {		
		renderQuadZNeg(bottomRight, bottomLeft, topRight, topLeft, 0, 1, 0, 1);
    }
	
	public void renderSquareZNeg(double xmin, double xmax, double ymin, double ymax, double zmin, 
			float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
		double xMin = x + xmin;
		double xMax = x + xmax;
		double yMin = y + ymin;
		double yMax = y + ymax;
		double zMin = z + zmin;
    	
		renderFaceZNeg(new Vertex((float)xMin, (float)yMin, (float)zMin), 
					   new Vertex((float)xMax, (float)yMin, (float)zMin), 
					   new Vertex((float)xMin, (float)yMax, (float)zMin), 
					   new Vertex((float)xMax, (float)yMax, (float)zMin), 
					   umin, uimin, umax, uimax, vmin, vimin, vmax, vimax);  
    }
	
	 /**
     * Renders the given texture to the north (z-negative) face of the block.  
     * Args: xmin, xmax, ymin, ymax, zmin, x, y, z, block, renderer, icon
     */
    public void renderSquareZNeg(double xmin, double xmax, double ymin, double ymax, double zmin, 
    		float umin, float umax, float vmin, float vmax)
    {
    	renderSquareZNeg(xmin, xmax, ymin, ymax, zmin, umin, umin, umax, umax, vmin, vmin, vmax, vmax);
    }
    
    public void renderSquareZNeg(double xmin, double xmax, double ymin, double ymax, double zmin)
    {
    	renderSquareZNeg(xmin, xmax, ymin, ymax, zmin, 0, 1, 0, 1);
    }
    
    private void renderFaceZPos(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
    		float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
    	Tessellator tessellator = Tessellator.instance;

        if (renderer.hasOverrideBlockTexture())
        {
        	icon = renderer.overrideBlockTexture;
        }
        
        double uMin = (double)icon.getInterpolatedU(umin * 16.0D);
        double uMax = (double)icon.getInterpolatedU(umax * 16.0D);
        double vMin = (double)icon.getInterpolatedV(16.0D - vmin * 16.0D);
        double vMax = (double)icon.getInterpolatedV(16.0D - vmax * 16.0D);
        
        double uiMax = (double)icon.getInterpolatedU(uimax * 16.0D);
        double uiMin = (double)icon.getInterpolatedU(uimin * 16.0D);
        double viMin = (double)icon.getInterpolatedV(16.0D - vimin * 16.0D);
        double viMax = (double)icon.getInterpolatedV(16.0D - vimax * 16.0D);

        if (umin < 0.0D || umax > 1.0D)
        {
            uMin = (double)icon.getMinU();
            uMax = (double)icon.getMaxU();
        }

        if (vmax < 0.0D || vmin > 1.0D)
        {
            vMin = (double)icon.getMinV();
            vMax = (double)icon.getMaxV();
        }
        
        if (uvRotate == 1)
        {
        	uMin = (double)icon.getInterpolatedU(vmax * 16.0D);
        	vMax = (double)icon.getInterpolatedV(16.0D - umin * 16.0D);
            uMax = (double)icon.getInterpolatedU(vmin * 16.0D);
            vMin = (double)icon.getInterpolatedV(16.0D - umax * 16.0D);
            viMin = vMin;
            viMax = vMax;
            uiMax = uMin;
            uiMin = uMax;
            vMin = vMax;
            vMax = viMin;
        }
        else if (uvRotate == 2)
        {
        	uMin = (double)icon.getInterpolatedU(16.0D - vmin * 16.0D);
        	vMin = (double)icon.getInterpolatedV(umin * 16.0D);
            uMax = (double)icon.getInterpolatedU(16.0D - vmax * 16.0D);
            vMax = (double)icon.getInterpolatedV(umax * 16.0D);
            uiMax = uMax;
            uiMin = uMin;
            uMin = uMax;
            uMax = uiMin;
            viMin = vMax;
            viMax = vMin;
        }
        else if (uvRotate == 3)
        {
        	uMin = (double)icon.getInterpolatedU(16.0D - umin * 16.0D);
        	uMax = (double)icon.getInterpolatedU(16.0D - umax * 16.0D);
        	vMin = (double)icon.getInterpolatedV(vmin * 16.0D);
        	vMax = (double)icon.getInterpolatedV(vmax * 16.0D);
        	uiMax = uMax;
        	uiMin = uMin;
        	viMin = vMin;
        	viMax = vMax;
        }

    	int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float f = (float)(l >> 16 & 255) / 255.0F;
        float f1 = (float)(l >> 8 & 255) / 255.0F;
        float f2 = (float)(l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        
        f = 0.8F * f;
        f1 = 0.8F * f1;
        f2 = 0.8F * f2;
        
    	int m = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
    	float n = ((topRight.z + topLeft.z + bottomRight.z + bottomLeft.z) - (z * 4)) / 4;
    	tessellator.setBrightness(n < 1.0D ? m : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1));
        tessellator.setColorOpaque_F(f, f1, f2);
        
        tessellator.addVertexWithUV(bottomLeft.x, topRight.y, topRight.z, uMin, vMax);
        tessellator.addVertexWithUV(topLeft.x, bottomRight.y, bottomRight.z, uiMin, viMin);
        tessellator.addVertexWithUV(topRight.x, bottomLeft.y, bottomLeft.z, uMax, vMin);
        tessellator.addVertexWithUV(bottomRight.x, topLeft.y, topLeft.z, uiMax, viMax);
        
        renderer.clearOverrideBlockTexture();
    }
    
    public void renderQuadZPos(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
    		float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
    	renderFaceZPos(new Vertex(x + bottomRight.x, y + bottomRight.y, z + bottomRight.z), 
					   new Vertex(x + bottomLeft.x, y + bottomLeft.y, z + bottomLeft.z), 
					   new Vertex(x + topRight.x, y + topRight.y, z + topRight.z), 
					   new Vertex(x + topLeft.x, y + topLeft.y, z + topLeft.z), 
					   umin, uimin, umax, uimax, vmin, vimin, vmax, vimax);
    }
    
    public void renderQuadZPos(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
    		float umin, float umax, float vmin, float vmax)
    {		
    	renderQuadZPos(bottomRight, bottomLeft, topRight, topLeft, umin, umin, umax, umax, vmin, vmin, vmax, vmax);
    }
    
    public void renderQuadZPos(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft)
    {		
    	renderQuadZPos(bottomRight, bottomLeft, topRight, topLeft, 0, 1, 0, 1);
    }

    public void renderSquareZPos(double xmin, double xmax, double ymin, double ymax, double zmax, 
    		float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
    	double xMin = x + xmin;
		double xMax = x + xmax;
		double yMin = y + ymin;
		double yMax = y + ymax;
		double zMax = z + zmax;
		
		renderFaceZPos(new Vertex((float)xMax, (float)yMin, (float)zMax), 
					   new Vertex((float)xMin, (float)yMin, (float)zMax), 
					   new Vertex((float)xMax, (float)yMax, (float)zMax), 
					   new Vertex((float)xMin, (float)yMax, (float)zMax), 
					   umin, uimin, umax, uimax, vmin, vimin, vmax, vimax);
    }
    
    /**
     * Renders the given texture to the south (z-positive) face of the block.  
     * Args: xmin, xmax, ymin, ymax, zmax
     */
    public void renderSquareZPos(double xmin, double xmax, double ymin, double ymax, double zmax, 
    		float umin, float umax, float vmin, float vmax)
    {
    	renderSquareZPos(xmin, xmax, ymin, ymax, zmax, umin, umin, umax, umax, vmin, vmin, vmax, vmax);
    }
    
    public void renderSquareZPos(double xmin, double xmax, double ymin, double ymax, double zmax)
    {
    	renderSquareZPos(xmin, xmax, ymin, ymax, zmax, 0, 1, 0, 1);
    }
    
    private void renderFaceXNeg(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
    		float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
    	Tessellator tessellator = Tessellator.instance;

        if (renderer.hasOverrideBlockTexture())
        {
        	icon = renderer.overrideBlockTexture;
        }

        double uMin = (double)icon.getInterpolatedU(umin * 16.0D);
        double uMax = (double)icon.getInterpolatedU(umax * 16.0D);
        double vMin = (double)icon.getInterpolatedV(16.0D - vmin * 16.0D);
        double vMax = (double)icon.getInterpolatedV(16.0D - vmax * 16.0D);
        
        double uiMax = (double)icon.getInterpolatedU(uimax * 16.0D);
        double uiMin = (double)icon.getInterpolatedU(uimin * 16.0D);
        double viMin = (double)icon.getInterpolatedV(16.0D - vimin * 16.0D);
        double viMax = (double)icon.getInterpolatedV(16.0D - vimax * 16.0D);

        if (umin < 0.0D || umax > 1.0D)
        {
            uMin = (double)icon.getMinU();
            uMax = (double)icon.getMaxU();
        }

        if (vmax < 0.0D || vmin > 1.0D)
        {
            vMin = (double)icon.getMinV();
            vMax = (double)icon.getMaxV();
        }
        
        if (uvRotate == 1)
        {
        	uMin = (double)icon.getInterpolatedU(vmax * 16.0D);
        	vMin = (double)icon.getInterpolatedV(16.0D - umax * 16.0D);
            uMax = (double)icon.getInterpolatedU(vmin * 16.0D);
            vMax = (double)icon.getInterpolatedV(16.0D - umin * 16.0D);
            viMin = vMin;
            viMax = vMax;
            uiMax = uMin;
            uiMin = uMax;
            vMin = vMax;
            vMax = viMin;
        }
        else if (uvRotate == 2)
        {
        	uMin = (double)icon.getInterpolatedU(16.0D - vmin * 16.0D);
        	vMin = (double)icon.getInterpolatedV(umin * 16.0D);
            uMax = (double)icon.getInterpolatedU(16.0D - vmax * 16.0D);
            vMax = (double)icon.getInterpolatedV(umax * 16.0D);
            uiMax = uMax;
            uiMin = uMin;
            uMin = uMax;
            uMax = uiMin;
            viMin = vMax;
            viMax = vMin;
        }
        else if (uvRotate == 3)
        {
        	uMin = (double)icon.getInterpolatedU(16.0D - umin * 16.0D);
        	uMax = (double)icon.getInterpolatedU(16.0D - umax * 16.0D);
        	vMin = (double)icon.getInterpolatedV(vmin * 16.0D);
        	vMax = (double)icon.getInterpolatedV(vmax * 16.0D);
        	uiMax = uMax;
        	uiMin = uMin;
        	viMin = vMin;
        	viMax = vMax;
        }
        
    	int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float f = (float)(l >> 16 & 255) / 255.0F;
        float f1 = (float)(l >> 8 & 255) / 255.0F;
        float f2 = (float)(l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        
        f = 0.6F * f;
        f1 = 0.6F * f1;
        f2 = 0.6F * f2;
        
    	int m = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
    	float n = ((topRight.x + topLeft.x + bottomRight.x + bottomLeft.x) - (x * 4)) / 4;
    	tessellator.setBrightness(n > 0.0D ? m : block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z));
        tessellator.setColorOpaque_F(f, f1, f2);
        
        tessellator.addVertexWithUV(topLeft.x, topLeft.y, bottomRight.z, uiMax, viMax);
        tessellator.addVertexWithUV(topRight.x, topRight.y, bottomLeft.z, uMin, vMax);
        tessellator.addVertexWithUV(bottomRight.x, bottomRight.y, topLeft.z, uiMin, viMin);
        tessellator.addVertexWithUV(bottomLeft.x, bottomLeft.y, topRight.z, uMax, vMin);
        
        renderer.clearOverrideBlockTexture();
    }
    
    public void renderQuadXNeg(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
    		float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
    	renderFaceXNeg(new Vertex(x + bottomRight.x, y + bottomRight.y, z + bottomRight.z), 
					   new Vertex(x + bottomLeft.x, y + bottomLeft.y, z + bottomLeft.z), 
					   new Vertex(x + topRight.x, y + topRight.y, z + topRight.z), 
					   new Vertex(x + topLeft.x, y + topLeft.y, z + topLeft.z), 
					   umin, uimin, umax, uimax, vmin, vimin, vmax, vimax);
    }
    
    public void renderQuadXNeg(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
    		float umin, float umax, float vmin, float vmax)
    {		
    	renderQuadXNeg(bottomRight, bottomLeft, topRight, topLeft, umin, umin, umax, umax, vmin, vmin, vmax, vmax);
    }
    
    public void renderQuadXNeg(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft)
    {		
    	renderQuadXNeg(bottomRight, bottomLeft, topRight, topLeft, 0, 1, 0, 1);
    }
    
    public void renderSquareXNeg(double xmin, double ymin, double ymax, double zmin, double zmax, 
    		float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
    	double xMin = x + xmin;
		double yMin = y + ymin;
		double yMax = y + ymax;
		double zMin = z + zmin;
		double zMax = z + zmax;
		
		renderFaceXNeg(new Vertex((float)xMin, (float)yMin, (float)zMax), 
					   new Vertex((float)xMin, (float)yMin, (float)zMin), 
					   new Vertex((float)xMin, (float)yMax, (float)zMax), 
					   new Vertex((float)xMin, (float)yMax, (float)zMin), 
					   umin, uimin, umax, uimax, vmin, vimin, vmax, vimax);
    }

    /**
     * Renders the given texture to the west (x-negative) face of the block.  
     * Args: xmin, ymin, ymax, zmin, zmax, x, y, z, block, renderer, icon
     */
    public void renderSquareXNeg(double xmin, double ymin, double ymax, double zmin, double zmax, 
    		float umin, float umax, float vmin, float vmax)
    {
    	renderSquareXNeg(xmin, ymin, ymax, zmin, zmax, umin, umin, umax, umax, vmin, vmin, vmax, vmax);
    }
    
    public void renderSquareXNeg(double xmin, double ymin, double ymax, double zmin, double zmax)
    {
    	renderSquareXNeg(xmin, ymin, ymax, zmin, zmax, 0, 1, 0, 1);
    }
    
    private void renderFaceXPos(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
    		float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
    	Tessellator tessellator = Tessellator.instance;

        if (renderer.hasOverrideBlockTexture())
        {
        	icon = renderer.overrideBlockTexture;
        }

        double uMin = (double)icon.getInterpolatedU(umin * 16.0D);
        double uMax = (double)icon.getInterpolatedU(umax * 16.0D);
        double uiMax = (double)icon.getInterpolatedU(uimax * 16.0D);
        double uiMin = (double)icon.getInterpolatedU(uimin * 16.0D);

        if (renderer.field_152631_f)
        {
            uMax = (double)icon.getInterpolatedU((1.0D - umin) * 16.0D);
            uMin = (double)icon.getInterpolatedU((1.0D - umax) * 16.0D);
            uiMax = (double)icon.getInterpolatedU((1.0D - uimin) * 16.0D);
            uiMin = (double)icon.getInterpolatedU((1.0D - uimax) * 16.0D);
        }

        double vMin = (double)icon.getInterpolatedV(16.0D - vmin * 16.0D);
        double vMax = (double)icon.getInterpolatedV(16.0D - vmax * 16.0D);
        double viMin = (double)icon.getInterpolatedV(16.0D - vimin * 16.0D);
        double viMax = (double)icon.getInterpolatedV(16.0D - vimax * 16.0D);

        if (umin < 0.0D || umax > 1.0D)
        {
            uMin = (double)icon.getMinU();
            uMax = (double)icon.getMaxU();
        }

        if (vmax < 0.0D || vmin > 1.0D)
        {
            vMin = (double)icon.getMinV();
            vMax = (double)icon.getMaxV();
        }
        
        if (uvRotate == 2)
        {
        	uMin = (double)icon.getInterpolatedU(vmax * 16.0D);
        	vMin = (double)icon.getInterpolatedV(16.0D - umin * 16.0D);
            uMax = (double)icon.getInterpolatedU(vmin * 16.0D);
            vMax = (double)icon.getInterpolatedV(16.0D - umax * 16.0D);
            viMin = vMin;
            viMax = vMax;
            uiMax = uMin;
            uiMin = uMax;
            vMin = vMax;
            vMax = viMin;
        }
        else if (uvRotate == 1)
        {
        	uMin = (double)icon.getInterpolatedU(16.0D - vmin * 16.0D);
        	vMin = (double)icon.getInterpolatedV(umax * 16.0D);
            uMax = (double)icon.getInterpolatedU(16.0D - vmax * 16.0D);
            vMax = (double)icon.getInterpolatedV(umin * 16.0D);
            uiMax = uMax;
            uiMin = uMin;
            uMin = uMax;
            uMax = uiMin;
            viMin = vMax;
            viMax = vMin;
        }
        else if (uvRotate == 3)
        {
        	uMin = (double)icon.getInterpolatedU(16.0D - umin * 16.0D);
        	uMax = (double)icon.getInterpolatedU(16.0D - umax * 16.0D);
        	vMin = (double)icon.getInterpolatedV(vmin * 16.0D);
        	vMax = (double)icon.getInterpolatedV(vmax * 16.0D);
        	uiMax = uMax;
        	uiMin = uMin;
            viMin = vMin;
            viMax = vMax;
        }

    	int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float f = (float)(l >> 16 & 255) / 255.0F;
        float f1 = (float)(l >> 8 & 255) / 255.0F;
        float f2 = (float)(l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        
        f = 0.6F * f;
        f1 = 0.6F * f1;
        f2 = 0.6F * f2;
        
    	int m = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
    	float n = ((topRight.x + topLeft.x + bottomRight.x + bottomLeft.x) - (x * 4)) / 4;
    	tessellator.setBrightness(n < 1.0D ? m : block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z));
    	tessellator.setColorOpaque_F(f, f1, f2);
    	
    	tessellator.addVertexWithUV(bottomRight.x, bottomRight.y, topLeft.z, uiMin, viMin);
        tessellator.addVertexWithUV(bottomLeft.x, bottomLeft.y, topRight.z, uMax, vMin);
        tessellator.addVertexWithUV(topLeft.x, topLeft.y, bottomRight.z, uiMax, viMax);
        tessellator.addVertexWithUV(topRight.x, topRight.y, bottomLeft.z, uMin, vMax);
        
        renderer.clearOverrideBlockTexture();
    }
    
    public void renderQuadXPos(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
    		float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
    	renderFaceXPos(new Vertex(x + bottomRight.x, y + bottomRight.y, z + bottomRight.z),
				       new Vertex(x + bottomLeft.x, y + bottomLeft.y, z + bottomLeft.z), 
					   new Vertex(x + topRight.x, y + topRight.y, z + topRight.z), 
					   new Vertex(x + topLeft.x, y + topLeft.y, z + topLeft.z), 
					   umin, uimin, umax, uimax, vmin, vimin, vmax, vimax);
    }
    
    public void renderQuadXPos(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft, 
    		float umin, float umax, float vmin, float vmax)
    {		
    	renderQuadXPos(bottomRight, bottomLeft, topRight, topLeft, umin, umin, umax, umax, vmin, vmin, vmax, vmax);
    }

    public void renderQuadXPos(Vertex bottomRight, Vertex bottomLeft, Vertex topRight, Vertex topLeft)
    {		
    	renderQuadXPos(bottomRight, bottomLeft, topRight, topLeft, 0, 1, 0, 1);
    }
    
    public void renderSquareXPos(double xmax, double ymin, double ymax, double zmin, double zmax, 
    		float umin, float uimin, float umax, float uimax, float vmin, float vimin, float vmax, float vimax)
    {
    	double xMax = x + xmax;
		double yMin = y + ymin;
		double yMax = y + ymax;
		double zMin = z + zmin;
		double zMax = z + zmax;
		
		renderFaceXPos(new Vertex((float)xMax, (float)yMin, (float)zMin), 
					   new Vertex((float)xMax, (float)yMin, (float)zMax), 
					   new Vertex((float)xMax, (float)yMax, (float)zMin), 
					   new Vertex((float)xMax, (float)yMax, (float)zMax), 
					   umin, uimin, umax, uimax, vmin, vimin, vmax, vimax);  
    }
    
    /**
     * Renders the given texture to the east (x-positive) face of the block.  
     * Args: xmax, ymin, ymax, zmin, zmax
     */
    public void renderSquareXPos(double xmax, double ymin, double ymax, double zmin, double zmax, 
    		float umin, float umax, float vmin, float vmax)
    {
    	renderSquareXPos(xmax, ymin, ymax, zmin, zmax, umin, umin, umax, umax, vmin, vmin, vmax, vmax);
    }
    
    public void renderSquareXPos(double xmax, double ymin, double ymax, double zmin, double zmax)
    {
    	renderSquareXPos(xmax, ymin, ymax, zmin, zmax, 0, 1, 0, 1);
    }
    
    public void draw2DItem(float scale, float xAdj, float yAdj, float zAdj)
	{
    	Tessellator tessellator = Tessellator.instance;
		tessellator.draw();
		 
		tessellator.startDrawingQuads();			
		TFC_Core.bindTexture(TextureMap.locationItemsTexture);
        
        float minU = icon.getMinU();
		float maxU = icon.getMaxU();
		float minV = icon.getMinV();
		float maxV = icon.getMaxV();
		
		float xMin = x + xAdj;
		float xMax = xMin + scale;
		float yMin = y + yAdj;
		float zMin = z + zAdj;
		float zMax = zMin + scale;		
				
		setItemShading();			
		tessellator.addVertexWithUV(xMin, yMin, zMax, maxU, minV);
		tessellator.addVertexWithUV(xMax, yMin, zMax, minU, minV);
		tessellator.addVertexWithUV(xMax, yMin, zMin, minU, maxV);
		tessellator.addVertexWithUV(xMin, yMin, zMin, maxU, maxV);
		tessellator.draw();
		
		tessellator.startDrawingQuads();			
		TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
	}
    
    public void draw3DItem(float scale, float depth, float xAdj, float yAdj, float zAdj)
	{
		Tessellator tessellator = Tessellator.instance;
		tessellator.draw();
		 
		tessellator.startDrawingQuads();			
		TFC_Core.bindTexture(TextureMap.locationItemsTexture);
        
		float xMin = x + xAdj;
		float xMax = xMin + scale;
		float yMin = y + yAdj;
		float yMax = yMin + depth;
		float zMin = z + zAdj;
		float zMax = zMin + scale;
		float uMin = icon.getMinU();
		float uMax = icon.getMaxU();
		float vMin = icon.getMinV();
		float vMax = icon.getMaxV();
		float iconWidth = icon.getIconWidth();
		float iconHeight = icon.getIconHeight();
		float f5 = 0.5F * (uMax - uMin) / iconWidth;
        float f6 = 0.5F * (vMax - vMin) / iconHeight;
		
		//Draw Bottom
        setItemShading();
        tessellator.addVertexWithUV(xMin, yMin, zMin, uMax, vMax);
        tessellator.addVertexWithUV(xMax, yMin, zMin, uMin, vMax);
        tessellator.addVertexWithUV(xMax, yMin, zMax, uMin, vMin);
        tessellator.addVertexWithUV(xMin, yMin, zMax, uMax, vMin);
        
        //Draw Top
        setItemShading();
        tessellator.addVertexWithUV(xMin, yMax, zMax, uMax, vMin);
        tessellator.addVertexWithUV(xMax, yMax, zMax, uMin, vMin);
        tessellator.addVertexWithUV(xMax, yMax, zMin, uMin, vMax);
        tessellator.addVertexWithUV(xMin, yMax, zMin, uMax, vMax);
        
        //Draw X Neg Faces
        setItemShading();
        float k;
        float f7;
        float f8;

        for (k = 0; k < iconWidth; ++k)
        {
            f7 = k / iconWidth;
            f8 = uMax + (uMin - uMax) * f7 - f5;
            tessellator.addVertexWithUV((f7 * scale) + xMin, yMax, zMin, f8, vMax);
            tessellator.addVertexWithUV((f7 * scale) + xMin, yMin, zMin, f8, vMax);
            tessellator.addVertexWithUV((f7 * scale) + xMin, yMin, zMax, f8, vMin);
            tessellator.addVertexWithUV((f7 * scale) + xMin, yMax, zMax, f8, vMin);
        }
        
        //Draw X Pos Faces
        setItemShading();
        float f9;

        for (k = 0; k < iconWidth; ++k)
        {
            f7 = k / iconWidth;
            f8 = uMax + (uMin - uMax) * f7 - f5;
            f9 = f7 + 1.0F / iconWidth;
            tessellator.addVertexWithUV((f9 * scale) + xMin, yMax, zMax, f8, vMin);
            tessellator.addVertexWithUV((f9 * scale) + xMin, yMin, zMax, f8, vMin);
            tessellator.addVertexWithUV((f9 * scale) + xMin, yMin, zMin, f8, vMax);
            tessellator.addVertexWithUV((f9 * scale) + xMin, yMax, zMin, f8, vMax);
        }
        
        //Draw Z Pos Faces
        setItemShading();

        for (k = 0; k < iconHeight; ++k)
        {
            f7 = k / iconHeight;
            f8 = vMax + (vMin - vMax) * f7 - f6;
            f9 = f7 + 1.0F / iconHeight;
            tessellator.addVertexWithUV(xMin, yMin, (f9 * scale) + zMin, uMax, f8);
            tessellator.addVertexWithUV(xMax, yMin, (f9 * scale) + zMin, uMin, f8);
            tessellator.addVertexWithUV(xMax, yMax, (f9 * scale) + zMin, uMin, f8);
            tessellator.addVertexWithUV(xMin, yMax, (f9 * scale) + zMin, uMax, f8);
        }
        
        //Draw Z Neg Faces
        setItemShading();

        for (k = 0; k < iconHeight; ++k)
        {
            f7 = k / iconHeight;
            f8 = vMax + (vMin - vMax) * f7 - f6;
            tessellator.addVertexWithUV(xMax, yMin, (f7 * scale) + zMin, uMin, f8);
            tessellator.addVertexWithUV(xMin, yMin, (f7 * scale) + zMin, uMax, f8);
            tessellator.addVertexWithUV(xMin, yMax, (f7 * scale) + zMin, uMax, f8);
            tessellator.addVertexWithUV(xMax, yMax, (f7 * scale) + zMin, uMin, f8);
        }
        
        tessellator.draw();
		
		tessellator.startDrawingQuads();			
		TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
	}
    
    public void setItemShading()
	{
		Tessellator tessellator = Tessellator.instance;
		int color = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float red = (float)(color >> 16 & 255) / 255.0F;
        float green = (float)(color >> 8 & 255) / 255.0F;
        float blue = (float)(color & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float redAna = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
            float greenAna = (red * 30.0F + green * 70.0F) / 100.0F;
            float blueAna = (red * 30.0F + blue * 70.0F) / 100.0F;
            red = redAna;
            green = greenAna;
            blue = blueAna;
        }
        
        tessellator.setColorOpaque_F(red, green, blue);
    	tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
	}
    
    public void renderSquareInvYNeg(double xmin, double xmax, double ymin, double zmin, double zmax, float umin, float umax, float vmin, float vmax)
    {
    	Tessellator tessellator = Tessellator.instance;

        if (renderer.hasOverrideBlockTexture())
        	icon = renderer.overrideBlockTexture;

        double uMin = (double)icon.getInterpolatedU(umin * 16.0D);
        double uMax = (double)icon.getInterpolatedU(umax * 16.0D);
        double vMin = (double)icon.getInterpolatedV(vmin * 16.0D);
        double vMax = (double)icon.getInterpolatedV(vmax * 16.0D);
        
        double uiMax = (double)icon.getInterpolatedU(umax * 16.0D);
        double uiMin = (double)icon.getInterpolatedU(umin * 16.0D);
        double viMin = (double)icon.getInterpolatedV(vmin * 16.0D);
        double viMax = (double)icon.getInterpolatedV(vmax * 16.0D);

        if (umin < 0.0D || umax > 1.0D)
        {
            uMin = (double)icon.getMinU();
            uMax = (double)icon.getMaxU();
        }

        if (vmin < 0.0D || vmax > 1.0D)
        {
            vMin = (double)icon.getMinV();
            vMax = (double)icon.getMaxV();
        }    
        
        tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
        tessellator.addVertexWithUV(xmin, ymin, zmax, uiMin, viMax);
        tessellator.addVertexWithUV(xmin, ymin, zmin, uMin, vMin);
        tessellator.addVertexWithUV(xmax, ymin, zmin, uiMax, viMin);
        tessellator.addVertexWithUV(xmax, ymin, zmax, uMax, vMax);
        tessellator.draw();
        
        renderer.clearOverrideBlockTexture();
    }
    
    public void renderSquareInvYPos(double xmin, double xmax, double ymax, double zmin, double zmax, float umin, float umax, float vmin, float vmax)
    {
    	
    }
    
    public void renderSquareInvZNeg(double xmin, double xmax, double ymin, double ymax, double zmin, float umin, float umax, float vmin, float vmax)
    {
    	
    }
    
    public void renderSquareInvZPos(double xmin, double xmax, double ymin, double ymax, double zmax, float umin, float umax, float vmin, float vmax)
    {
    	
    }
    
    public void renderSquareInvXNeg(double xmin, double ymin, double ymax, double zmin, double zmax, float umin, float umax, float vmin, float vmax)
    {
    	
    }
    
    public void renderSquareInvXPos(double xmax, double ymin, double ymax, double zmin, double zmax, float umin, float umax, float vmin, float vmax)
    {
    	
    }
}
