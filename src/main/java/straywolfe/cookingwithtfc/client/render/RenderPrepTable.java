package straywolfe.cookingwithtfc.client.render;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.block.BlockPrepTable2;

public class RenderPrepTable implements ISimpleBlockRenderingHandler
{
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) 
	{
		Block tableTop;
		Block tableLeg;		
		
		renderer.renderAllFaces = true;
		
			
		if(block instanceof BlockPrepTable2)
		{
			tableTop = TFCBlocks.planks2;
			tableLeg = TFCBlocks.woodSupportH2;
		}
		else 
		{
			tableTop = TFCBlocks.planks;
			tableLeg = TFCBlocks.woodSupportH;
		}
		
		if(block == CWTFCBlocks.prepTableN || block == CWTFCBlocks.prepTable2N)
		{				
			//Back-Left Leg
			renderer.setRenderBounds(0, 0, 0.76F, 0.24F, 0.88F, 1);
			renderer.renderStandardBlock(tableLeg, x, y, z);
			
			//Back-Right Leg
			renderer.setRenderBounds(0.76F, 0, 0.76F, 1, 0.88F, 1);
			renderer.renderStandardBlock(tableLeg, x, y, z);
			
			//Top
			renderer.setRenderBounds(0, 0.88F, 0, 1, 1, 1);
			rotate(renderer, true);
			renderer.renderStandardBlock(tableTop, x, y, z);
		}
		if(block == CWTFCBlocks.prepTableE || block == CWTFCBlocks.prepTable2E)
		{
			//Back-Left Leg
			renderer.setRenderBounds(0, 0, 0.76F, 0.24F, 0.88F, 1);
			renderer.renderStandardBlock(tableLeg, x, y, z);
			
			//Front-Left Leg
			renderer.setRenderBounds(0, 0, 0, 0.24F, 0.88F, 0.24F);
			renderer.renderStandardBlock(tableLeg, x, y, z);
			
			//Top
			renderer.setRenderBounds(0, 0.88F, 0, 1, 1, 1);
			rotate(renderer, false);
			renderer.renderStandardBlock(tableTop, x, y, z);
		}
		if(block == CWTFCBlocks.prepTableS || block == CWTFCBlocks.prepTable2S)
		{
			//Front-Left Leg
			renderer.setRenderBounds(0, 0, 0, 0.24F, 0.88F, 0.24F);
			renderer.renderStandardBlock(tableLeg, x, y, z);
			
			//Front-Right Leg
			renderer.setRenderBounds(0.76F, 0, 0, 1, 0.88F, 0.24F);
			renderer.renderStandardBlock(tableLeg, x, y, z);
			
			//Top
			renderer.setRenderBounds(0, 0.88F, 0, 1, 1, 1);
			rotate(renderer, true);
			renderer.renderStandardBlock(tableTop, x, y, z);
		}
		if(block == CWTFCBlocks.prepTableW || block == CWTFCBlocks.prepTable2W)
		{				
			//Front-Right Leg
			renderer.setRenderBounds(0.76F, 0, 0, 1, 0.88F, 0.24F);
			renderer.renderStandardBlock(tableLeg, x, y, z);
			
			//Back-Right Leg
			renderer.setRenderBounds(0.76F, 0, 0.76F, 1, 0.88F, 1);
			renderer.renderStandardBlock(tableLeg, x, y, z);
			
			//Top
			renderer.setRenderBounds(0, 0.88F, 0, 1, 1, 1);
			rotate(renderer, false);
			renderer.renderStandardBlock(tableTop, x, y, z);
		}
		
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		renderer.uvRotateWest = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateSouth = 0;
		
		renderer.renderAllFaces = false;
		
		return true;
	}
	
	public void rotate(RenderBlocks renderer, boolean b)
	{
		int r = 0;
		if(b)
			r = 1;
			
		renderer.uvRotateTop = r;
		renderer.uvRotateBottom = r;
		
		if(r == 0)
		{
			renderer.uvRotateWest = 0;
			renderer.uvRotateEast = 0;
			renderer.uvRotateNorth = 2;
			renderer.uvRotateSouth = 1;
		}
		else
		{
			renderer.uvRotateWest = 1;
			renderer.uvRotateEast = 2;
			renderer.uvRotateNorth = 0;
			renderer.uvRotateSouth = 0;
		}
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return 0;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) 
	{	
		Block TableLeg;
		Block tableTop;
		
		if(block instanceof BlockPrepTable2)
		{
			TableLeg = TFCBlocks.woodSupportH2;
			tableTop = TFCBlocks.planks2;
		}
		else
		{
			TableLeg = TFCBlocks.woodSupportH;
			tableTop = TFCBlocks.planks;
		}
		
		//Back-Left Leg
		renderer.setRenderBounds(0, 0, 0.76F, 0.24F, 0.88F, 1);
		renderInvBlock(TableLeg, metadata, renderer);
		
		//Front-Left Leg
		renderer.setRenderBounds(0, 0, 0, 0.24F, 0.88F, 0.24F);
		renderInvBlock(TableLeg, metadata, renderer);
		
		//Front-Right Leg
		renderer.setRenderBounds(0.76F, 0, 0, 1, 0.88F, 0.24F);
		renderInvBlock(TableLeg, metadata, renderer);
		
		//Back-Right Leg
		renderer.setRenderBounds(0.76F, 0, 0.76F, 1, 0.88F, 1);
		renderInvBlock(TableLeg, metadata, renderer);
		
		//Top
		renderer.setRenderBounds(0, 0.88F, 0, 1, 1, 1);
		renderInvBlock(tableTop, metadata, renderer);
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

}
