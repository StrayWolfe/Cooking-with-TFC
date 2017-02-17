package straywolfe.cookingwithtfc.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import straywolfe.cookingwithtfc.common.block.BlockCrop;
import straywolfe.cookingwithtfc.common.registries.CropRegistry;
import straywolfe.cookingwithtfc.common.tileentity.TileCrop;

public class RenderCrop implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(tileentity != null && tileentity instanceof TileCrop && block instanceof BlockCrop)
		{
			TileCrop te = (TileCrop)tileentity;
			int cropID = te.getCropID();
			if(cropID != 0 && cropID != -1)
			{
				BlockCrop blockCrop = (BlockCrop)block;
				int meta = world.getBlockMetadata(x, y, z);
				CWTFCRenderer myRenderer = new CWTFCRenderer(x, y, z, block, renderer);
				
				switch(cropID)
				{
					case CropRegistry.WATERMELON:
					case CropRegistry.PUMPKIN:
						renderGourd(blockCrop, meta, te, myRenderer); break;
					case CropRegistry.CELERY:
					case CropRegistry.LETTUCE:
						renderCrop(renderer, blockCrop, world, x, y, z); break;
					default: renderCrops(renderer, blockCrop, world, x, y, z); break;
				}
			}
		}
		return true;
	}
	
	private void renderCrop(RenderBlocks renderer, Block block, IBlockAccess world, int x, int y, int z)
	{
		Tessellator tessellator = Tessellator.instance;
		int meta = world.getBlockMetadata(x, y, z);
		IIcon iicon = block.getIcon(world, x, y, z, meta);
		
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		
		if (renderer.hasOverrideBlockTexture())
        	iicon = renderer.overrideBlockTexture;
		
		double d3 = (double)iicon.getMinU();
        double d4 = (double)iicon.getMinV();
        double d5 = (double)iicon.getMaxU();
        double d6 = (double)iicon.getMaxV();
        double d7 = 0.45D * (double)1.0;
        double d8 = x + 0.5D - d7;
        double d9 = x + 0.5D + d7;
        double d10 = z + 0.5D - d7;
        double d11 = z + 0.5D + d7;
        tessellator.addVertexWithUV(d8, y + (double)1.0, d10, d3, d4);
        tessellator.addVertexWithUV(d8, y + 0.0D, d10, d3, d6);
        tessellator.addVertexWithUV(d9, y + 0.0D, d11, d5, d6);
        tessellator.addVertexWithUV(d9, y + (double)1.0, d11, d5, d4);
        tessellator.addVertexWithUV(d9, y + (double)1.0, d11, d3, d4);
        tessellator.addVertexWithUV(d9, y + 0.0D, d11, d3, d6);
        tessellator.addVertexWithUV(d8, y + 0.0D, d10, d5, d6);
        tessellator.addVertexWithUV(d8, y + (double)1.0, d10, d5, d4);
        tessellator.addVertexWithUV(d8, y + (double)1.0, d11, d3, d4);
        tessellator.addVertexWithUV(d8, y + 0.0D, d11, d3, d6);
        tessellator.addVertexWithUV(d9, y + 0.0D, d10, d5, d6);
        tessellator.addVertexWithUV(d9, y + (double)1.0, d10, d5, d4);
        tessellator.addVertexWithUV(d9, y + (double)1.0, d10, d3, d4);
        tessellator.addVertexWithUV(d9, y + 0.0D, d10, d3, d6);
        tessellator.addVertexWithUV(d8, y + 0.0D, d11, d5, d6);
        tessellator.addVertexWithUV(d8, y + (double)1.0, d11, d5, d4);
	}
	
	private void renderCrops(RenderBlocks renderer, Block block, IBlockAccess world, int x, int y, int z)
	{
		Tessellator tessellator = Tessellator.instance;
		int meta = world.getBlockMetadata(x, y, z);
		IIcon iicon = block.getIcon(world, x, y, z, meta);
		
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        
        if (renderer.hasOverrideBlockTexture())
        	iicon = renderer.overrideBlockTexture;
        
        double d3 = (double)iicon.getMinU();
        double d4 = (double)iicon.getMinV();
        double d5 = (double)iicon.getMaxU();
        double d6 = (double)iicon.getMaxV();
        double d7 = x + 0.5D - 0.25D;
        double d8 = x + 0.5D + 0.25D;
        double d9 = z + 0.5D - 0.5D;
        double d10 = z + 0.5D + 0.5D;
        tessellator.addVertexWithUV(d7, y + 1.0D, d9, d3, d4);
        tessellator.addVertexWithUV(d7, y + 0.0D, d9, d3, d6);
        tessellator.addVertexWithUV(d7, y + 0.0D, d10, d5, d6);
        tessellator.addVertexWithUV(d7, y + 1.0D, d10, d5, d4);
        tessellator.addVertexWithUV(d7, y + 1.0D, d10, d3, d4);
        tessellator.addVertexWithUV(d7, y + 0.0D, d10, d3, d6);
        tessellator.addVertexWithUV(d7, y + 0.0D, d9, d5, d6);
        tessellator.addVertexWithUV(d7, y + 1.0D, d9, d5, d4);
        tessellator.addVertexWithUV(d8, y + 1.0D, d10, d3, d4);
        tessellator.addVertexWithUV(d8, y + 0.0D, d10, d3, d6);
        tessellator.addVertexWithUV(d8, y + 0.0D, d9, d5, d6);
        tessellator.addVertexWithUV(d8, y + 1.0D, d9, d5, d4);
        tessellator.addVertexWithUV(d8, y + 1.0D, d9, d3, d4);
        tessellator.addVertexWithUV(d8, y + 0.0D, d9, d3, d6);
        tessellator.addVertexWithUV(d8, y + 0.0D, d10, d5, d6);
        tessellator.addVertexWithUV(d8, y + 1.0D, d10, d5, d4);
        d7 = x + 0.5D - 0.5D;
        d8 = x + 0.5D + 0.5D;
        d9 = z + 0.5D - 0.25D;
        d10 = z + 0.5D + 0.25D;
        tessellator.addVertexWithUV(d7, y + 1.0D, d9, d3, d4);
        tessellator.addVertexWithUV(d7, y + 0.0D, d9, d3, d6);
        tessellator.addVertexWithUV(d8, y + 0.0D, d9, d5, d6);
        tessellator.addVertexWithUV(d8, y + 1.0D, d9, d5, d4);
        tessellator.addVertexWithUV(d8, y + 1.0D, d9, d3, d4);
        tessellator.addVertexWithUV(d8, y + 0.0D, d9, d3, d6);
        tessellator.addVertexWithUV(d7, y + 0.0D, d9, d5, d6);
        tessellator.addVertexWithUV(d7, y + 1.0D, d9, d5, d4);
        tessellator.addVertexWithUV(d8, y + 1.0D, d10, d3, d4);
        tessellator.addVertexWithUV(d8, y + 0.0D, d10, d3, d6);
        tessellator.addVertexWithUV(d7, y + 0.0D, d10, d5, d6);
        tessellator.addVertexWithUV(d7, y + 1.0D, d10, d5, d4);
        tessellator.addVertexWithUV(d7, y + 1.0D, d10, d3, d4);
        tessellator.addVertexWithUV(d7, y + 0.0D, d10, d3, d6);
        tessellator.addVertexWithUV(d8, y + 0.0D, d10, d5, d6);
        tessellator.addVertexWithUV(d8, y + 1.0D, d10, d5, d4);
	}
	
	private void renderGourd(BlockCrop blockCrop, int meta, TileCrop te, CWTFCRenderer myRenderer)
	{
		IIcon[] iconsGourd = new IIcon[4];
		
		iconsGourd[0] = blockCrop.getCropIcon("Plant_Top");
		iconsGourd[1] = blockCrop.getCropIcon("Plant_Bottom");
		
		if(te.getCropID() == CropRegistry.WATERMELON)
		{
			iconsGourd[2] = blockCrop.getCropIcon("Melon_Top");
			iconsGourd[3] = blockCrop.getCropIcon("Melon_Side");
		}
		else
		{
			iconsGourd[2] = blockCrop.getCropIcon("Pumpkin_Top");
			iconsGourd[3] = blockCrop.getCropIcon("Pumpkin_Side");
		}
		
		int stage = (int) Math.floor(te.growth);
		if(stage > 4)
			stage = 4;
		
		if(stage == 0)
		renderFirstStage(myRenderer, meta, iconsGourd);
		else if(stage == 1)
			renderSecondStage(myRenderer, meta, iconsGourd);
		else if(stage == 2)
			renderThirdStage(myRenderer, meta, iconsGourd);
		else if(stage == 3)
			renderFourthStage(myRenderer, meta, iconsGourd);
		else if(stage == 4)
			renderFinalStage(myRenderer, meta, iconsGourd);	
	}
	
	private void renderFirstStage(CWTFCRenderer myRenderer, int meta, IIcon[] iconsGourd)
	{
		if(meta == 0)
		{
			myRenderer.icon = iconsGourd[0];
			myRenderer.renderSquareYPos(0, 1, 0.05, 0, 0.375, 0F, 1F, 0F, 0.375F);
			
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 1, 0.01, 0, 0.25, 0F, 1F, 0F, 0.25F);
		}
		else if(meta == 1)
		{
			myRenderer.icon = iconsGourd[0];
			myRenderer.uvRotate = 1;
			myRenderer.renderSquareYPos(0.625, 1, 0.05, 0, 1, 0.625F, 1F, 0F, 1F);
			
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0.75, 1, 0.01, 0, 1, 0.75F, 1F, 0F, 1F);
			
			myRenderer.uvRotate = 0;
		}
		else if(meta == 2)
		{
			myRenderer.icon = iconsGourd[0];
			myRenderer.uvRotate = 2;
			myRenderer.renderSquareYPos(0F, 0.375F, 0.05, 0F, 0.375F, 0F, 0.375F, 0F, 0.375F);
			
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 0.25, 0.01, 0, 0.25, 0F, 0.25F, 0F, 0.25F);
			
			myRenderer.uvRotate = 0;
		}
		else if(meta == 3)
		{
			myRenderer.icon = iconsGourd[0];
			myRenderer.uvRotate = 3;
			myRenderer.renderSquareYPos(0.625, 1, 0.05, 0F, 0.375F, 0F, 0.375F, 0.625F, 1F);
			
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0.75, 1, 0.01, 0, 0.25, 0F, 0.25F, 0.75F, 1F);
			
			myRenderer.uvRotate = 0;
		}
	}
	private void renderSecondStage(CWTFCRenderer myRenderer, int meta, IIcon[] iconsGourd)
	{
		if(meta == 0)
		{
			myRenderer.icon = iconsGourd[0];
			myRenderer.renderSquareYPos(0, 1, 0.05, 0, 0.625, 0F, 1F, 0F, 0.625F);
			
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 1, 0.01, 0, 0.5625, 0F, 1F, 0F, 0.5625F);
		}
		else if(meta == 1)
		{
			myRenderer.icon = iconsGourd[0];
			myRenderer.uvRotate = 1;
			myRenderer.renderSquareYPos(0.375, 1, 0.05, 0, 1, 0.375F, 1F, 0F, 1F);
			
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0.4375, 1, 0.01, 0, 1, 0.4375F, 1F, 0F, 1F);
			
			myRenderer.uvRotate = 0;
		}
		else if(meta == 2)
		{
			myRenderer.icon = iconsGourd[0];
			myRenderer.uvRotate = 2;
			myRenderer.renderSquareYPos(0F, 0.625F, 0.05, 0F, 0.625F, 0F, 0.625F, 0F, 0.625F);
			
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 0.5625, 0.01, 0, 0.5625, 0F, 0.5625F, 0F, 0.5625F);
			
			myRenderer.uvRotate = 0;
		}
		else if(meta == 3)
		{
			myRenderer.icon = iconsGourd[0];
			myRenderer.uvRotate = 3;
			myRenderer.renderSquareYPos(0.375, 1, 0.05, 0F, 0.625F, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0.4375, 1, 0.01, 0, 0.5625, 0F, 0.5625F, 0.4375F, 1F);
			
			myRenderer.uvRotate = 0;
		}
	}
	
	private void renderThirdStage(CWTFCRenderer myRenderer, int meta, IIcon[] iconsGourd)
	{
		if(meta == 0)
		{
			myRenderer.icon = iconsGourd[0];
			myRenderer.renderSquareYPos(0, 1, 0.05, 0, 1);
			
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 1, 0.01, 0, 1);
		}
		else if(meta == 1)
		{
			myRenderer.icon = iconsGourd[0];
			myRenderer.uvRotate = 1;
			myRenderer.renderSquareYPos(0, 1, 0.05, 0, 1);
			
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 1, 0.01, 0, 1);
			
			myRenderer.uvRotate = 0;
		}
		else if(meta == 2)
		{
			myRenderer.icon = iconsGourd[0];
			myRenderer.uvRotate = 2;
			myRenderer.renderSquareYPos(0, 1, 0.05, 0, 1);
			
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 1, 0.01, 0, 1);
			
			myRenderer.uvRotate = 0;
		}
		else if(meta == 3)
		{
			myRenderer.icon = iconsGourd[0];
			myRenderer.uvRotate = 3;
			myRenderer.renderSquareYPos(0, 1, 0.05, 0, 1);
			
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 1, 0.01, 0, 1);
			
			myRenderer.uvRotate = 0;
		}
	}
	
	private void renderFourthStage(CWTFCRenderer myRenderer, int meta, IIcon[] iconsGourd)
	{
		if(meta == 0)
		{
			//Plant Top Layer
			myRenderer.icon = iconsGourd[0];
			myRenderer.renderSquareYPos(0, 1, 0.05, 0, 1);
			
			//Plant Bottom Layer
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 1, 0.01, 0, 1);
			
			//Gourd Side
			myRenderer.icon = iconsGourd[3];			
			myRenderer.uvRotate = 0;
			myRenderer.renderSquareYPos(0.125, 0.4375, 0.3125, 0.3125, 0.625, 0F, 0.625F, 0F, 0.625F);
			
			myRenderer.uvRotate = 1;
			myRenderer.renderSquareXPos(0.4375, 0, 0.3125, 0.3125, 0.625, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareXNeg(0.125, 0, 0.3125, 0.3125, 0.625, 0.375F, 1F, 0F, 0.625F);
			
			//Gourd Front/Back and Stem
			myRenderer.icon = iconsGourd[2];
			myRenderer.uvRotate = 0;
			myRenderer.renderSquareZNeg(0.125, 0.4375, 0, 0.3125, 0.3125, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareZPos(0.125, 0.4375, 0, 0.3125, 0.625, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareYPos(0.25, 0.3125, 0.1875, 0.625, 0.6875, 0F, 0.0625F, 0.9375F, 1F);
			
			myRenderer.renderSquareXPos(0.3125, 0, 0.1875, 0.625, 0.6875, 0.9375F, 1F, 0.75F, 1F);
			
			myRenderer.renderSquareXNeg(0.25, 0, 0.1875, 0.625, 0.6875, 0.9375F, 1F, 0.75F, 1F);
			
			myRenderer.renderSquareZPos(0.25, 0.3125, 0, 0.1875, 0.6875, 0.9375F, 1F, 0.75F, 1F);
		}
		else if(meta == 1)
		{
			//Plant top layer
			myRenderer.icon = iconsGourd[0];
			myRenderer.uvRotate = 1;
			myRenderer.renderSquareYPos(0, 1, 0.05, 0, 1);
			
			//Plant bottom layer
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 1, 0.01, 0, 1);
			
			//Gourd Sides
			myRenderer.icon = iconsGourd[3];
			myRenderer.uvRotate = 1;
			myRenderer.renderSquareYPos(0.375, 0.6875, 0.3125, 0.125, 0.4375, 0.375F, 1F, 0F, 0.625F);
			
			myRenderer.renderSquareZNeg(0.375, 0.6875, 0, 0.3125, 0.125, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareZPos(0.375, 0.6875, 0, 0.3125, 0.4375, 0.375F, 1F, 0F, 0.625F);
			
			//Gourd Front/Back and stem
			myRenderer.icon = iconsGourd[2];
			myRenderer.uvRotate = 0;
			myRenderer.renderSquareXPos(0.6875, 0, 0.3125, 0.125, 0.4375, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareXNeg(0.375, 0, 0.3125, 0.125, 0.4375, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareYPos(0.3125, 0.375, 0.1875, 0.25, 0.3125, 0F, 0.0625F, 0.9375F, 1F);
			
			myRenderer.renderSquareXNeg(0.3125, 0, 0.1875, 0.25, 0.3125, 0.9375F, 1F, 0.75F, 1F);
			
			myRenderer.renderSquareZNeg(0.3125, 0.375, 0, 0.1875, 0.25, 0.9375F, 1F, 0.75F, 1F);
			
			myRenderer.renderSquareZPos(0.3125, 0.375, 0, 0.1875, 0.3125, 0.9375F, 1F, 0.75F, 1F);
		}
		else if(meta == 2)
		{
			//Plant Top Layer
			myRenderer.icon = iconsGourd[0];
			myRenderer.uvRotate = 2;
			myRenderer.renderSquareYPos(0, 1, 0.05, 0, 1);
			
			//Plant Bottom Layer
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 1, 0.01, 0, 1);
			
			//Gourd Sides
			myRenderer.icon = iconsGourd[3];
			myRenderer.uvRotate = 1;
			myRenderer.renderSquareYPos(0.3125, 0.625, 0.3125, 0.5625, 0.875, 0.375F, 1F, 0F, 0.625F);
							
			myRenderer.renderSquareZNeg(0.3125, 0.625, 0, 0.3125, 0.5625, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareZPos(0.3125, 0.625, 0, 0.3125, 0.875, 0.375F, 1F, 0F, 0.625F);
			
			//Gourd Front/Back and Stem
			myRenderer.icon = iconsGourd[2];
			myRenderer.uvRotate = 0;
			myRenderer.renderSquareXPos(0.625, 0, 0.3125, 0.5625, 0.875, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareXNeg(0.3125, 0, 0.3125, 0.5625, 0.875, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareYPos(0.625, 0.6875, 0.1875, 0.6875, 0.75, 0F, 0.0625F, 0.9375F, 1F);
			
			myRenderer.renderSquareXPos(0.6875, 0, 0.1875, 0.6875, 0.75, 0.9375F, 1F, 0.75F, 1F);
			
			myRenderer.renderSquareZNeg(0.625, 0.6875, 0, 0.1875, 0.6875, 0.9375F, 1F, 0.75F, 1F);
			
			myRenderer.renderSquareZPos(0.625, 0.6875, 0, 0.1875, 0.75, 0.9375F, 1F, 0.75F, 1F);
		}
		else if(meta == 3)
		{
			//Plant Top Layer
			myRenderer.icon = iconsGourd[0];
			myRenderer.uvRotate = 3;
			myRenderer.renderSquareYPos(0, 1, 0.05, 0, 1);
			
			//Plant Bottom Layer
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 1, 0.01, 0, 1);
			
			//Gourd Sides
			myRenderer.icon = iconsGourd[3];
			myRenderer.uvRotate = 1;
			myRenderer.renderSquareYPos(0.375, 0.6875, 0.3125, 0.5625, 0.875, 0.375F, 1F, 0F, 0.625F);
			
			myRenderer.renderSquareZNeg(0.375, 0.6875, 0, 0.3125, 0.5625, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareZPos(0.375, 0.6875, 0, 0.3125, 0.875, 0.375F, 1F, 0F, 0.625F);
			
			//Gourd Front/Back and Stem
			myRenderer.icon = iconsGourd[2];
			myRenderer.uvRotate = 0;
			myRenderer.renderSquareXPos(0.6875, 0, 0.3125, 0.5625, 0.875, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareXNeg(0.375, 0, 0.3125, 0.5625, 0.875, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareYPos(0.3125, 0.375, 0.1875, 0.6875, 0.75, 0F, 0.0625F, 0.9375F, 1F);
			
			myRenderer.renderSquareXNeg(0.3125, 0, 0.1875, 0.6875, 0.75, 0.9375F, 1F, 0.75F, 1F);
			
			myRenderer.renderSquareZNeg(0.3125, 0.375, 0, 0.1875, 0.6875, 0.9375F, 1F, 0.75F, 1F);
			
			myRenderer.renderSquareZPos(0.3125, 0.375, 0, 0.1875, 0.75, 0.9375F, 1F, 0.75F, 1F);
		}
	}
	
	private void renderFinalStage(CWTFCRenderer myRenderer, int meta, IIcon[] iconsGourd)
	{
		if(meta == 0)
		{
			//Plant Top Layer
			myRenderer.icon = iconsGourd[0];
			myRenderer.renderSquareYPos(0, 1, 0.05, 0, 1);
			
			//Plant Bottom Layer
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 1, 0.01, 0, 1);
			
			//Gourd Sides
			myRenderer.icon = iconsGourd[3];			
			
			myRenderer.uvRotate = 1;
			myRenderer.renderSquareXPos(0.625, 0, 0.625, 0, 0.625, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareXNeg(0, 0, 0.625, 0, 0.625, 0.375F, 1F, 0F, 0.625F);
			
			myRenderer.uvRotate = 0;
			myRenderer.renderSquareYPos(0, 0.625, 0.625, 0, 0.625, 0F, 0.625F, 0F, 0.625F);
			
			//Gourd Front/Back with Stem
			myRenderer.icon = iconsGourd[2];
			myRenderer.renderSquareZNeg(0, 0.625, 0, 0.625, 0, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareZPos(0, 0.625, 0, 0.625, 0.625, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareYPos(0.25, 0.375, 0.375, 0.625, 0.75, 0F, 0.125F, 0.875F, 1F);
			
			myRenderer.renderSquareXPos(0.375, 0, 0.375, 0.625, 0.75, 0.875F, 1F, 0.625F, 1F);
			
			myRenderer.renderSquareXNeg(0.25, 0, 0.375, 0.625, 0.75, 0.875F, 1F, 0.625F, 1F);
			
			myRenderer.renderSquareZPos(0.25, 0.375, 0, 0.375, 0.75, 0.875F, 1F, 0.625F, 1F);
		}
		else if(meta == 1)
		{
			//Plant Top Layer
			myRenderer.icon = iconsGourd[0];
			myRenderer.uvRotate = 1;
			myRenderer.renderSquareYPos(0, 1, 0.05, 0, 1);
			
			//Plant Bottom Layer
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 1, 0.01, 0, 1);
			
			//Gourd Sides
			myRenderer.icon = iconsGourd[3];
			myRenderer.uvRotate = 1;

			myRenderer.renderSquareYPos(0.375, 1, 0.625, 0, 0.625, 0.375F, 1F, 0F, 0.625F);
			
			myRenderer.renderSquareZNeg(0.375, 1, 0, 0.625, 0, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareZPos(0.375, 1, 0, 0.625, 0.625, 0.375F, 1F, 0F, 0.625F);
			
			//Gourd Front/Back with Stem
			myRenderer.icon = iconsGourd[2];
			myRenderer.uvRotate = 0;
			myRenderer.renderSquareXPos(1, 0, 0.625, 0, 0.625, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareXNeg(0.375, 0, 0.625, 0, 0.625, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareYPos(0.25, 0.375, 0.375, 0.25, 0.375, 0F, 0.125F, 0.875F, 1F);
			
			myRenderer.renderSquareXNeg(0.25, 0, 0.375, 0.25, 0.375, 0.875F, 1F, 0.625F, 1F);
			
			myRenderer.renderSquareZNeg(0.25, 0.375, 0, 0.375, 0.25, 0.875F, 1F, 0.625F, 1F);
			
			myRenderer.renderSquareZPos(0.25, 0.375, 0, 0.375, 0.375, 0.875F, 1F, 0.625F, 1F);
		}
		else if(meta == 2)
		{
			//Plant Top Layer
			myRenderer.icon = iconsGourd[0];
			myRenderer.uvRotate = 2;
			myRenderer.renderSquareYPos(0, 1, 0.05, 0, 1);
			
			//Plant Bottom Layer
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 1, 0.01, 0, 1);
			
			//Gourd Sides
			myRenderer.icon = iconsGourd[3];
			myRenderer.uvRotate = 1;
			myRenderer.renderSquareYPos(0, 0.625, 0.625, 0.375, 1, 0.375F, 1F, 0F, 0.625F);
							
			myRenderer.renderSquareZNeg(0, 0.625, 0, 0.625, 0.375, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareZPos(0, 0.625, 0, 0.625, 1, 0.375F, 1F, 0F, 0.625F);
			
			//Gourd Front/Back with Stem
			myRenderer.icon = iconsGourd[2];
			myRenderer.uvRotate = 0;
			myRenderer.renderSquareXPos(0.625, 0, 0.625, 0.375, 1, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareXNeg(0, 0, 0.625, 0.375, 1, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareYPos(0.625, 0.75, 0.375, 0.625, 0.75, 0F, 0.125F, 0.875F, 1F);
			
			myRenderer.renderSquareXPos(0.75, 0, 0.375, 0.625, 0.75, 0.875F, 1F, 0.625F, 1F);
			
			myRenderer.renderSquareZNeg(0.625, 0.75, 0, 0.375, 0.625, 0.875F, 1F, 0.625F, 1F);
			
			myRenderer.renderSquareZPos(0.625, 0.75, 0, 0.375, 0.75, 0.875F, 1F, 0.625F, 1F);
		}
		else if(meta == 3)
		{
			//Plant Top Layer
			myRenderer.icon = iconsGourd[0];
			myRenderer.uvRotate = 3;
			myRenderer.renderSquareYPos(0, 1, 0.05, 0, 1);
			
			//Plant Bottom Layer
			myRenderer.icon = iconsGourd[1];
			myRenderer.renderSquareYPos(0, 1, 0.01, 0, 1);
			
			//Gourd Sides
			myRenderer.icon = iconsGourd[3];
			myRenderer.uvRotate = 2;
			myRenderer.renderSquareYPos(0.375, 1, 0.625, 0.375, 1, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareZNeg(0.375, 1, 0, 0.625, 0.375, 0.375F, 1F, 0F, 0.625F);
			
			myRenderer.renderSquareZPos(0.375, 1, 0, 0.625, 1, 0F, 0.625F, 0.375F, 1F);
			
			//Gourd Front/Back with Stem
			myRenderer.icon = iconsGourd[2];
			myRenderer.uvRotate = 0;
			myRenderer.renderSquareXPos(1, 0, 0.625, 0.375, 1, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareXNeg(0.375, 0, 0.625, 0.375, 1, 0F, 0.625F, 0.375F, 1F);
			
			myRenderer.renderSquareYPos(0.25, 0.375, 0.375, 0.625, 0.75, 0F, 0.125F, 0.875F, 1F);
			
			myRenderer.renderSquareXNeg(0.25, 0, 0.375, 0.625, 0.75, 0.875F, 1F, 0.625F, 1F);
			
			myRenderer.renderSquareZNeg(0.25, 0.375, 0, 0.375, 0.625, 0.875F, 1F, 0.625F, 1F);
			
			myRenderer.renderSquareZPos(0.25, 0.375, 0, 0.375, 0.75, 0.875F, 1F, 0.625F, 1F);
		}
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

	@Override
	public boolean shouldRender3DInInventory(int modelId) 
	{
		return false;
	}

	@Override
	public int getRenderId() 
	{
		return 0;
	}
}
