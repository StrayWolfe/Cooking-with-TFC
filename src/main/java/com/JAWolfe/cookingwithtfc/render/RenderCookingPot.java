package com.JAWolfe.cookingwithtfc.render;

import org.lwjgl.opengl.GL11;

import com.JAWolfe.cookingwithtfc.init.CWTFCBlocks;
import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderCookingPot implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		Block Firepit = TFCBlocks.firepit;
		Block Pot = CWTFCBlocks.cookingPot;
		int meta = world.getBlockMetadata(x, y, z);
		
		renderer.renderAllFaces = true;
		
		renderer.setRenderBounds(0, 0, 0, 1, 0.1f, 1);
		
		if (meta == 0)
			renderer.renderBlockUsingTexture(Firepit, x, y, z, Firepit.getIcon(0, 0));
		else
			renderer.renderBlockUsingTexture(Firepit, x, y, z, Firepit.getIcon(0, 1));
		
		renderer.setRenderBounds(0.3125f, 0, 0.3125f, 0.6875f, 0.1f, 0.6875f);
		renderer.renderStandardBlock(Pot, x, y, z);
		
		renderer.setRenderBounds(0.25f, 0.1f, 0.25f, 0.75f, 0.3f, 0.75f);
		renderer.renderStandardBlock(Pot, x, y, z);
		
		renderer.setRenderBounds(0.3125f, 0.3f, 0.3125f, 0.6875f, 0.4f, 0.6875f);
		renderer.renderStandardBlock(Pot, x, y, z);
		
		renderer.setRenderBounds(0.4375f, 0.4f, 0.4375f, 0.5625f, 0.5f, 0.5625f);
		renderer.renderStandardBlock(Pot, x, y, z);
			
		return false;
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
