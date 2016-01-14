package com.JAWolfe.cookingwithtfc.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderMixingBowl implements ISimpleBlockRenderingHandler
{
	private static final float MIN = 0.25F;
	private static final float MAX = 0.75F;
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		renderer.setRenderBounds(MIN + 0.12F, 0, MIN + 0.12F, MAX - 0.12F, 0.06F, MAX - 0.12F);
		renderer.renderStandardBlock(block, x, y, z);
		
		renderer.setRenderBounds(MIN + 0.06F, 0.06F, MIN + 0.12F, MIN + 0.12F, 0.12F, MAX - 0.12F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(MAX - 0.12F, 0.06F, MIN + 0.12F, MAX - 0.06F, 0.12F, MAX - 0.12F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(MIN + 0.12F, 0.06F, MIN + 0.06F, MAX - 0.12F, 0.12F, MIN + 0.12F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(MIN + 0.12F, 0.06F, MAX - 0.12F, MAX - 0.12F, 0.12F, MAX - 0.06F);
		renderer.renderStandardBlock(block, x, y, z);
		
		renderer.setRenderBounds(MIN, 0.12F, MIN + 0.12F, MIN + 0.06F, 0.30F, MAX - 0.12F);
		renderer.renderStandardBlock(block, x, y, z);	
		renderer.setRenderBounds(MAX - 0.06F, 0.12F, MIN + 0.12F, MAX, 0.30F, MAX - 0.12F);
		renderer.renderStandardBlock(block, x, y, z);		
		renderer.setRenderBounds(MIN + 0.12F, 0.12F, MIN, MAX - 0.12F, 0.30F, MIN + 0.06F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(MIN + 0.12F, 0.12F, MAX - 0.06F, MAX - 0.12F, 0.30F, MAX);
		renderer.renderStandardBlock(block, x, y, z);
		
		renderer.setRenderBounds(MIN + 0.06F, 0.12F, MIN + 0.06F, MIN + 0.12F, 	0.30F, MIN + 0.12F);
		renderer.renderStandardBlock(block, x, y, z);	
		renderer.setRenderBounds(MAX - 0.12F, 0.12F, MIN + 0.06F, MAX - 0.06F, 	0.30F, MIN + 0.12F);
		renderer.renderStandardBlock(block, x, y, z);		
		renderer.setRenderBounds(MIN + 0.06F, 0.12F, MAX - 0.12F, MIN + 0.12F, 	0.30F, MAX - 0.06F);
		renderer.renderStandardBlock(block, x, y, z);	
		renderer.setRenderBounds(MAX - 0.12F, 0.12F, MAX - 0.12F, MAX - 0.06F, 	0.30F, MAX - 0.06F);
		renderer.renderStandardBlock(block, x, y, z);
		
		
		return false;
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
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer)
	{
		float unitSize = 0.12F;
		float MinX = 0F;
		float MinZ = 0F;
		float MaxX = 1F;
		float MaxZ = 1F;
		float MinY = 0F;
		float MaxY = 0.52F;
		
		renderer.setRenderBounds(MinX + unitSize*2, MinY, MinZ + unitSize*2, 
								MaxX - unitSize*2, unitSize + MinY, MaxZ - unitSize*2);
		renderInvBlock(block, meta, renderer);
		
		renderer.setRenderBounds(MinX + unitSize, unitSize + MinY, MinZ + unitSize*2, 
								MinX + unitSize*2, unitSize*2 + MinY, MaxZ - unitSize*2);
		renderInvBlock(block, meta, renderer);
		renderer.setRenderBounds(MaxX - unitSize*2, unitSize + MinY, MinZ + unitSize*2, 
								MaxX - unitSize, unitSize*2 + MinY, MaxZ - unitSize*2);
		renderInvBlock(block, meta, renderer);
		renderer.setRenderBounds(MinX + unitSize*2, unitSize + MinY, MinZ + unitSize, 
								MaxX - unitSize*2, unitSize*2 + MinY, MinZ + unitSize*2);
		renderInvBlock(block, meta, renderer);
		renderer.setRenderBounds(MinX + unitSize*2, unitSize + MinY, MaxZ - unitSize*2, 
								MaxX - unitSize*2, unitSize*2 + MinY, MaxZ - unitSize);
		renderInvBlock(block, meta, renderer);
		
		renderer.setRenderBounds(MinX, unitSize*2 + MinY, MinZ + unitSize*2, 
								MinX + unitSize, MaxY, MaxZ - unitSize*2);
		renderInvBlock(block, meta, renderer);	
		renderer.setRenderBounds(MaxX - unitSize, unitSize*2 + MinY, MinZ + unitSize*2, 
								MaxX, MaxY, MaxZ - unitSize*2);
		renderInvBlock(block, meta, renderer);	
		renderer.setRenderBounds(MinX + unitSize*2, unitSize*2 + MinY, MinZ, 
								MaxX - unitSize*2, MaxY, MinZ + unitSize);
		renderInvBlock(block, meta, renderer);
		renderer.setRenderBounds(MinX + unitSize*2, unitSize*2 + MinY, MaxZ - unitSize, 
								MaxX - unitSize*2, MaxY, MaxZ);
		renderInvBlock(block, meta, renderer);
		
		renderer.setRenderBounds(MinX + unitSize, unitSize*2 + MinY, MinZ + unitSize, 
								MinX + unitSize*2, 	MaxY, MinZ + unitSize*2);
		renderInvBlock(block, meta, renderer);	
		renderer.setRenderBounds(MaxX - unitSize*2, unitSize*2 + MinY, MinZ + unitSize, 
								MaxX - unitSize, 	MaxY, MinZ + unitSize*2);
		renderInvBlock(block, meta, renderer);		
		renderer.setRenderBounds(MinX + unitSize, unitSize*2 + MinY, MaxZ - unitSize*2, 
								MinX + unitSize*2, 	MaxY, MaxZ - unitSize);
		renderInvBlock(block, meta, renderer);	
		renderer.setRenderBounds(MaxX - unitSize*2, unitSize*2 + MinY, MaxZ - unitSize*2, 
								MaxX - unitSize, 	MaxY, MaxZ - unitSize);
		renderInvBlock(block, meta, renderer);
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
