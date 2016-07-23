package straywolfe.cookingwithtfc.client.render;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Blocks.Flora.BlockLogNatural;
import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.tileentity.TileCookingPot;

public class RenderCookingPot implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		Block Firepit = TFCBlocks.firepit;
		Block Pot = CWTFCBlocks.cookingPot;
		TileCookingPot te = (TileCookingPot)world.getTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		
		renderer.renderAllFaces = true;
		
		renderer.setRenderBounds(0, 0, 0, 1, 0.1f, 1);
		
		//Render fire
		if (meta == 0)
			renderer.renderBlockUsingTexture(Firepit, x, y, z, Firepit.getIcon(0, 0));
		else
			renderer.renderBlockUsingTexture(Firepit, x, y, z, Firepit.getIcon(0, 1));
		
		//Render Logs
		if(te.getStackInSlot(0) != null)
		{
			renderer.setRenderBounds(0.2, 0, 0.25f, 0.8f, 0.09f, 0.3125f);
			renderBlock(renderer, x, y, z, ((BlockLogNatural)TFCBlocks.logNatural).rotatedSideIcons[te.getStackInSlot(0).getItemDamage()]);
		}
		
		if(te.getStackInSlot(1) != null)
		{
			renderer.setRenderBounds(0.2, 0, 0.390625f, 0.8f, 0.09f, 0.453125f);
			renderBlock(renderer, x, y, z, ((BlockLogNatural)TFCBlocks.logNatural).rotatedSideIcons[te.getStackInSlot(1).getItemDamage()]);
		}
		
		if(te.getStackInSlot(2) != null)
		{
			renderer.setRenderBounds(0.2, 0, 0.546875f, 0.8f, 0.09f, 0.609375f);
			renderBlock(renderer, x, y, z, ((BlockLogNatural)TFCBlocks.logNatural).rotatedSideIcons[te.getStackInSlot(2).getItemDamage()]);
		}
		
		if(te.getStackInSlot(3) != null)
		{
			renderer.setRenderBounds(0.2, 0, 0.6875f, 0.8f, 0.09, 0.75f);
			renderBlock(renderer, x, y, z, ((BlockLogNatural)TFCBlocks.logNatural).rotatedSideIcons[te.getStackInSlot(3).getItemDamage()]);
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
				float yLoc = Math.max(0.11f, 0.3f * te.getCookingPotFluid().amount/(float)te.getMaxLiquid());
				renderer.setRenderBounds(0.3125f, yLoc - 0.01f, 0.3125f, 0.6875f, yLoc, 0.6875f);
				if(renderer.overrideBlockTexture == null)
				{
					int color = te.getCookingPotFluid().getFluid().getColor();
					renderer.setOverrideBlockTexture(te.getCookingPotFluid().getFluid().getStillIcon());
					renderer.renderStandardBlockWithColorMultiplier(Blocks.snow, x, y, z, 
						(color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F);
					renderer.clearOverrideBlockTexture();
				}
			}
		}
			
		return false;
	}
	
	private void renderBlock(RenderBlocks renderer, int x, int y, int z, IIcon tex)
	{
		if(renderer.overrideBlockTexture == null)
		{
			renderer.setOverrideBlockTexture(tex);
			renderer.renderStandardBlock(Blocks.snow, x, y, z);
			renderer.clearOverrideBlockTexture();
		}
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
