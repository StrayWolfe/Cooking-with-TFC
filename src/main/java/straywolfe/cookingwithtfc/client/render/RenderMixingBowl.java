package straywolfe.cookingwithtfc.client.render;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import straywolfe.cookingwithtfc.common.lib.Textures;
import straywolfe.cookingwithtfc.common.tileentity.TileMixBowl;

public class RenderMixingBowl implements ISimpleBlockRenderingHandler
{	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{		
		TileMixBowl te = (TileMixBowl)world.getTileEntity(x, y, z);
		
		float xCoord = te.getBowlCoord(0);
		float zCoord = te.getBowlCoord(1);
		
		float radius = 0.5F;
		float minX = xCoord;
		float maxX = xCoord + radius;
		float minZ = zCoord;
		float maxZ = zCoord + radius;
		
		if(te.getCompleted())
		{
			ItemStack contents = te.getContents();
			int color = 0xFFFFFF;
			
			if(contents.getItem() == TFCItems.barleyGround)
				color = 0xE4D8C2;
			else if(contents.getItem() == TFCItems.cornmealGround)
				color = 0xC9A858;
			else if(contents.getItem() == TFCItems.oatGround)
				color = 0xBEA57A;
			else if(contents.getItem() == TFCItems.riceGround)
				color = 0xE7DEDD;
			else if(contents.getItem() == TFCItems.ryeGround)
				color = 0xB2C3A7;
			else if(contents.getItem() == TFCItems.wheatGround)
				color = 0xD4C1A0;
			
			renderer.setRenderBounds(minX + 0.065F, 0.25F, minZ + 0.065F, maxX - 0.065F, 0.26F, maxZ - 0.065F);			
			renderBlock(renderer, Blocks.snow, color, x, y, z);
		}
		else if(te.getContents() != null)
		{
			ItemStack contents = te.getContents();
			int color = 0xFFFFFF;
				
			if(contents.getItem() == TFCItems.barleyGround)
				color = 0xD3D09D;
			else if(contents.getItem() == TFCItems.cornmealGround)
				color = 0xC09540;
			else if(contents.getItem() == TFCItems.oatGround)
				color = 0x79582A;
			else if(contents.getItem() == TFCItems.riceGround)
				color = 0xD6CAC5;
			else if(contents.getItem() == TFCItems.ryeGround)
				color = 0xBAC9B6;
			else if(contents.getItem() == TFCItems.wheatGround)
				color = 0xB49A6A;
			
			renderer.setRenderBounds(minX + 0.065F, 0.15F, minZ + 0.065F, maxX - 0.065F, 0.16F, maxZ - 0.065F);			
			renderBlock(renderer, Blocks.snow, color, x, y, z);
		}
		
		//Bottom layer
		renderer.setRenderBounds(minX + 0.12F, 0, minZ + 0.12F, maxX - 0.12F, 0.06F, maxZ - 0.12F);
		renderer.renderStandardBlock(block, x, y, z);
		
		//2nd layer
		renderer.setRenderBounds(minX + 0.06F, 0.06F, minZ + 0.12F, minX + 0.12F, 0.12F, maxZ - 0.12F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(maxX - 0.12F, 0.06F, minZ + 0.12F, maxX - 0.06F, 0.12F, maxZ - 0.12F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(minX + 0.12F, 0.06F, minZ + 0.06F, maxX - 0.12F, 0.12F, minZ + 0.12F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(minX + 0.12F, 0.06F, maxZ - 0.12F, maxX - 0.12F, 0.12F, maxZ - 0.06F);
		renderer.renderStandardBlock(block, x, y, z);
		
		//Top layer
		renderer.setRenderBounds(minX, 0.12F, minZ + 0.12F, minX + 0.06F, 0.30F, maxZ - 0.12F);
		renderer.renderStandardBlock(block, x, y, z);	
		renderer.setRenderBounds(maxX - 0.06F, 0.12F, minZ + 0.12F, maxX, 0.30F, maxZ - 0.12F);
		renderer.renderStandardBlock(block, x, y, z);		
		renderer.setRenderBounds(minX + 0.12F, 0.12F, minZ, maxX - 0.12F, 0.30F, minZ + 0.06F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(minX + 0.12F, 0.12F, maxZ - 0.06F, maxX - 0.12F, 0.30F, maxZ);
		renderer.renderStandardBlock(block, x, y, z);		
		renderer.setRenderBounds(minX + 0.06F, 0.12F, minZ + 0.06F, minX + 0.12F, 0.30F, minZ + 0.12F);
		renderer.renderStandardBlock(block, x, y, z);	
		renderer.setRenderBounds(maxX - 0.12F, 0.12F, minZ + 0.06F, maxX - 0.06F, 0.30F, minZ + 0.12F);
		renderer.renderStandardBlock(block, x, y, z);		
		renderer.setRenderBounds(minX + 0.06F, 0.12F, maxZ - 0.12F, minX + 0.12F, 0.30F, maxZ - 0.06F);
		renderer.renderStandardBlock(block, x, y, z);	
		renderer.setRenderBounds(maxX - 0.12F, 0.12F, maxZ - 0.12F, maxX - 0.06F, 0.30F, maxZ - 0.06F);
		renderer.renderStandardBlock(block, x, y, z);		
		
		return false;
	}
	
	private void renderBlock(RenderBlocks renderer, Block content, int color, int x, int y, int z)
	{
		if(renderer.overrideBlockTexture == null)
		{
			renderer.setOverrideBlockTexture(Textures.White_BG);
			renderer.renderStandardBlockWithColorMultiplier(content, x, y, z, 
					(color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F);
			renderer.clearOverrideBlockTexture();
		}
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) 
	{
		return true;
	}
	
	@Override
	public int getRenderId() 
	{
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
		
		renderer.setRenderBounds(MinX + unitSize*2, MinY, MinZ + unitSize*2, MaxX - unitSize*2, unitSize + MinY, MaxZ - unitSize*2);
		renderInvBlock(block, meta, renderer);
		
		renderer.setRenderBounds(MinX + unitSize, unitSize + MinY, MinZ + unitSize*2, MinX + unitSize*2, unitSize*2 + MinY, MaxZ - unitSize*2);
		renderInvBlock(block, meta, renderer);
		
		renderer.setRenderBounds(MaxX - unitSize*2, unitSize + MinY, MinZ + unitSize*2, MaxX - unitSize, unitSize*2 + MinY, MaxZ - unitSize*2);
		renderInvBlock(block, meta, renderer);
		
		renderer.setRenderBounds(MinX + unitSize*2, unitSize + MinY, MinZ + unitSize, MaxX - unitSize*2, unitSize*2 + MinY, MinZ + unitSize*2);
		renderInvBlock(block, meta, renderer);
		
		renderer.setRenderBounds(MinX + unitSize*2, unitSize + MinY, MaxZ - unitSize*2, MaxX - unitSize*2, unitSize*2 + MinY, MaxZ - unitSize);
		renderInvBlock(block, meta, renderer);
		
		renderer.setRenderBounds(MinX, unitSize*2 + MinY, MinZ + unitSize*2, MinX + unitSize, MaxY, MaxZ - unitSize*2);
		renderInvBlock(block, meta, renderer);	
		
		renderer.setRenderBounds(MaxX - unitSize, unitSize*2 + MinY, MinZ + unitSize*2, MaxX, MaxY, MaxZ - unitSize*2);
		renderInvBlock(block, meta, renderer);	
		
		renderer.setRenderBounds(MinX + unitSize*2, unitSize*2 + MinY, MinZ, MaxX - unitSize*2, MaxY, MinZ + unitSize);
		renderInvBlock(block, meta, renderer);
		renderer.setRenderBounds(MinX + unitSize*2, unitSize*2 + MinY, MaxZ - unitSize, MaxX - unitSize*2, MaxY, MaxZ);
		renderInvBlock(block, meta, renderer);
		
		renderer.setRenderBounds(MinX + unitSize, unitSize*2 + MinY, MinZ + unitSize, MinX + unitSize*2, 	MaxY, MinZ + unitSize*2);
		renderInvBlock(block, meta, renderer);
		
		renderer.setRenderBounds(MaxX - unitSize*2, unitSize*2 + MinY, MinZ + unitSize, MaxX - unitSize, 	MaxY, MinZ + unitSize*2);
		renderInvBlock(block, meta, renderer);
		
		renderer.setRenderBounds(MinX + unitSize, unitSize*2 + MinY, MaxZ - unitSize*2, MinX + unitSize*2, 	MaxY, MaxZ - unitSize);
		renderInvBlock(block, meta, renderer);
		
		renderer.setRenderBounds(MaxX - unitSize*2, unitSize*2 + MinY, MaxZ - unitSize*2, MaxX - unitSize, 	MaxY, MaxZ - unitSize);
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
