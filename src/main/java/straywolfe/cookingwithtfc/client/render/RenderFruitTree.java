package straywolfe.cookingwithtfc.client.render;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.FloraIndex;
import com.bioxx.tfc.Food.FloraManager;
import com.bioxx.tfc.Render.TFC_CoreRender;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.block.BlockNutLeaves;
import straywolfe.cookingwithtfc.common.tileentity.TileNutTree;

public class RenderFruitTree implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		if(modelId == CWTFCBlocks.fruitTreeRenderID)
			return renderWoodTrunk(block, x, y, z, renderer, world);
		else if(modelId == CWTFCBlocks.nutLeavesRenderID)
			return renderLeaves(block, x, y, z, renderer, world);
		
		return false;
	}
	
	public boolean renderLeaves(Block block, int x, int y, int z, RenderBlocks renderblocks, IBlockAccess world)
	{		
		if(block instanceof BlockNutLeaves)
		{
			BlockNutLeaves leaves = (BlockNutLeaves)block;
			int meta = renderblocks.blockAccess.getBlockMetadata(x, y, z);
			int adjMeta = meta < 8 ? meta : meta - 8;
			FloraManager manager = FloraManager.getInstance();
			FloraIndex index = manager.findMatchingIndex(leaves.getTreeType(leaves, adjMeta));
			
			if(adjMeta == 2)
			{
				renderPalm(renderblocks, block, world, x, y, z);
				
				if(meta > 7)
					renderCoconut(renderblocks, block, world, x, y, z);
			}
			else
			{
				renderblocks.renderStandardBlock(block, x, y, z);
				if (index != null && (index.inBloom(TFC_Time.getSeasonAdjustedMonth(z)) || index.inHarvest(TFC_Time.getSeasonAdjustedMonth(z))))
				{
					if(index.inBloom(TFC_Time.getSeasonAdjustedMonth(z)))
						renderblocks.overrideBlockTexture = leaves.getFlowerIcon(adjMeta);
					else if(meta > 7)
						renderblocks.overrideBlockTexture = leaves.getFruitIcon(adjMeta);
					
					if(renderblocks.overrideBlockTexture != null)
						TFC_CoreRender.renderBlockWithCustomColorMultiplier(block, renderblocks, x, y, z, 16777215);
					renderblocks.clearOverrideBlockTexture();
				}
			}
		}
		
		return true;
	}
	
	public boolean renderWoodTrunk(Block block, int i, int j, int k, RenderBlocks renderblocks, IBlockAccess world)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;
		
		if(blockAccess.getTileEntity(i, j, k) instanceof TileNutTree)
		{
			TileNutTree te = (TileNutTree)blockAccess.getTileEntity(i, j, k);
			if(te.isSapling == 1)
				renderSapling(renderblocks, block, world, i, j, k);
			else if(te.isSapling == 0)
			{
				if((blockAccess.getBlock(i, j - 1, k) == CWTFCBlocks.nutTreeLog || blockAccess.getBlock(i, j - 1, k).isOpaqueCube()))
				{
					renderblocks.setRenderBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				if(isLeaf(blockAccess, i - 1, j, k))
				{
					renderblocks.setRenderBounds(0.0F, 0.4F, 0.4F, 0.5F, 0.6F, 0.6F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				if(isLeaf(blockAccess, i + 1, j, k))
				{
					renderblocks.setRenderBounds(0.5F, 0.4F, 0.4F, 1.0F, 0.6F, 0.6F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				if(isLeaf(blockAccess, i, j, k - 1))
				{
					renderblocks.setRenderBounds(0.4F, 0.4F, 0.0F, 0.6F, 0.6F, 0.5F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				if(isLeaf(blockAccess, i, j, k + 1))
				{
					renderblocks.setRenderBounds(0.4F, 0.4F, 0.5F, 0.6F, 0.6F, 1.0F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
		
				if(!((TileNutTree)te).isTrunk && blockAccess.getBlock(i, j-1, k) != CWTFCBlocks.nutTreeLog && !blockAccess.getBlock(i, j-1, k).isOpaqueCube())
				{
					renderblocks.setRenderBounds(0.0F, 0.4F, 0.4F, 0.5F, 0.6F, 0.6F);
					renderblocks.renderStandardBlock(block, i, j, k);
		
					renderblocks.setRenderBounds(0.5F, 0.4F, 0.4F, 1.0F, 0.6F, 0.6F);
					renderblocks.renderStandardBlock(block, i, j, k);
		
					renderblocks.setRenderBounds(0.4F, 0.4F, 0.0F, 0.6F, 0.6F, 0.5F);
					renderblocks.renderStandardBlock(block, i, j, k);
		
					renderblocks.setRenderBounds(0.4F, 0.4F, 0.5F, 0.6F, 0.6F, 1.0F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
			}
		}

		return true;
	}
	
	private boolean isLeaf(IBlockAccess blockAccess, int x, int y, int z)
	{
		return blockAccess.getBlock(x, y, z).getMaterial() == Material.leaves || blockAccess.getBlock(x, y, z) == CWTFCBlocks.nutTreeLog;
	}
	
	private void renderSapling(RenderBlocks renderer, Block block, IBlockAccess world, int x, int y, int z)
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
	
	protected void renderCoconut(RenderBlocks renderer, Block block, IBlockAccess world, int x, int y, int z)
	{
		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = ((BlockNutLeaves)block).getPalmIcon(2);
		
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		
		double i0 = (double)iicon.getMinU();
        double i1 = (double)iicon.getMinV();
        double i2 = (double)iicon.getMaxU();
        double i3 = (double)iicon.getMaxV();
        
        tessellator.addVertexWithUV(x,	   y - 0.4, z + 0.5, i2, i3);
        tessellator.addVertexWithUV(x, 	   y + 0.6, z + 0.5, i2, i1);
        tessellator.addVertexWithUV(x + 1, y + 0.6, z + 0.5, i0, i1);
        tessellator.addVertexWithUV(x + 1, y - 0.4, z + 0.5, i0, i3);        
        tessellator.addVertexWithUV(x + 1, y - 0.4, z + 0.5, i0, i3);
        tessellator.addVertexWithUV(x + 1, y + 0.6, z + 0.5, i0, i1);
        tessellator.addVertexWithUV(x, 	   y + 0.6, z + 0.5, i2, i1);
        tessellator.addVertexWithUV(x,     y - 0.4, z + 0.5, i2, i3);        
        tessellator.addVertexWithUV(x + 0.5, y - 0.4, z + 1, i0, i3);
        tessellator.addVertexWithUV(x + 0.5, y + 0.6, z + 1, i0, i1);
        tessellator.addVertexWithUV(x + 0.5, y + 0.6, z,     i2, i1);
        tessellator.addVertexWithUV(x + 0.5, y - 0.4, z,     i2, i3);        
        tessellator.addVertexWithUV(x + 0.5, y - 0.4, z,     i2, i3);
        tessellator.addVertexWithUV(x + 0.5, y + 0.6, z,     i2, i1);
        tessellator.addVertexWithUV(x + 0.5, y + 0.6, z + 1, i0, i1);
        tessellator.addVertexWithUV(x + 0.5, y - 0.4, z + 1, i0, i3);        
        tessellator.addVertexWithUV(x,     y + 0.1, z,     i2, i3);
        tessellator.addVertexWithUV(x + 1, y + 0.1, z,     i2, i1);
        tessellator.addVertexWithUV(x + 1, y + 0.1, z + 1, i0, i1);
        tessellator.addVertexWithUV(x,     y + 0.1, z + 1, i0, i3);        
        tessellator.addVertexWithUV(x,     y + 0.1, z + 1, i2, i3);
        tessellator.addVertexWithUV(x + 1, y + 0.1, z + 1, i2, i1);
        tessellator.addVertexWithUV(x + 1, y + 0.1, z,     i0, i1);
        tessellator.addVertexWithUV(x,     y + 0.1, z,     i0, i3);
	}
	
	protected void renderPalm(RenderBlocks renderer, Block block, IBlockAccess world, int x, int y, int z)
	{
		Tessellator tessellator = Tessellator.instance;
		IIcon iicon1 = ((BlockNutLeaves)block).getPalmIcon(0);
		IIcon iicon2 = ((BlockNutLeaves)block).getPalmIcon(1);
		
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		
		double i0 = (double)iicon1.getMinU();
        double i1 = (double)iicon1.getMinV();
        double i2 = (double)iicon1.getMaxU();
        double i3 = (double)iicon1.getMaxV();
        
        double i4 = (double)iicon2.getMinU();
        double i5 = (double)iicon2.getMinV();
        double i6 = (double)iicon2.getMaxU();
        double i7 = (double)iicon2.getMaxV();
        
        if(world.getBlock(x + 1, y, z) == CWTFCBlocks.nutTreeLog)
        {
        	tessellator.addVertexWithUV(x,	   y, 	    z, 		 i2, i3);
	        tessellator.addVertexWithUV(x, 	   y + 0.5, z + 0.5, i2, i1);
	        tessellator.addVertexWithUV(x + 1, y + 0.6, z + 0.5, i0, i1);
	        tessellator.addVertexWithUV(x + 1, y, 	    z, 	 	 i0, i3);	        
	        tessellator.addVertexWithUV(x, 	   y + 0.5, z + 0.5, i2, i1);
	        tessellator.addVertexWithUV(x, 	   y,		z, 		 i2, i3);
	        tessellator.addVertexWithUV(x + 1, y, 	    z, 	 	 i0, i3);
	        tessellator.addVertexWithUV(x + 1, y + 0.6, z + 0.5, i0, i1);	        
	        tessellator.addVertexWithUV(x + 1,	y,		  z + 1,  i0, i3);
	        tessellator.addVertexWithUV(x + 1,  y + 0.6, z + 0.5, i0, i1);
	        tessellator.addVertexWithUV(x, 	    y + 0.5, z + 0.5, i2, i1);
	        tessellator.addVertexWithUV(x, 		y,		  z + 1,  i2, i3);	        
	        tessellator.addVertexWithUV(x,	   y,		  z + 1, i2, i3);
	        tessellator.addVertexWithUV(x, 	   y + 0.5, z + 0.5, i2, i1);
	        tessellator.addVertexWithUV(x + 1, y + 0.6, z + 0.5, i0, i1);
	        tessellator.addVertexWithUV(x + 1, y,		  z + 1, i0, i3);	        
	        tessellator.addVertexWithUV(x, y + 0.5,  z + 0.5, i4, i5);
	        tessellator.addVertexWithUV(x,		 y, 	   z, i4, i7);
	        tessellator.addVertexWithUV(x - 1, 		 y, 	   z, i6, i7);
	        tessellator.addVertexWithUV(x - 1, y + 0.35, z + 0.5, i6, i5);	        
	        tessellator.addVertexWithUV(x - 1, y + 0.35, z + 0.5, i6, i5);
	        tessellator.addVertexWithUV(x - 1,		 y, 	   z, i6, i7);
	        tessellator.addVertexWithUV(x, 		 y, 	   z, i4, i7);
	        tessellator.addVertexWithUV(x, y + 0.5,  z + 0.5, i4, i5);	        
	        tessellator.addVertexWithUV(x - 1, y + 0.35, z + 0.5, i6, i5);
	        tessellator.addVertexWithUV(x - 1, 	 y, 	   z + 1, i6, i7);
	        tessellator.addVertexWithUV(x, 	 y, 	   z + 1, i4, i7);
	        tessellator.addVertexWithUV(x, y + 0.5,  z + 0.5, i4, i5);	        
	        tessellator.addVertexWithUV(x, y + 0.5,  z + 0.5, i4, i5);
	        tessellator.addVertexWithUV(x, 	 y, 	   z + 1, i4, i7);
	        tessellator.addVertexWithUV(x - 1, 	 y, 	   z + 1, i6, i7);
	        tessellator.addVertexWithUV(x - 1, y + 0.35, z + 0.5, i6, i5);
        }
        else if(world.getBlock(x - 1, y, z) == CWTFCBlocks.nutTreeLog)
        {
        	tessellator.addVertexWithUV(x, y, 	    z, i0, i3);
	        tessellator.addVertexWithUV(x, y + 0.6, z + 0.5, i0, i1);
	        tessellator.addVertexWithUV(x + 1, 	   y + 0.5, z + 0.5, 	 i2, i1);
	        tessellator.addVertexWithUV(x + 1,	   y, 	    z, 	 i2, i3);	        
	        tessellator.addVertexWithUV(x, y + 0.6, z + 0.5, i0, i1);
	        tessellator.addVertexWithUV(x, 		 y,		  z, i0, i3);
	        tessellator.addVertexWithUV(x + 1, 		 y, 	  z, 	 i2, i3);
	        tessellator.addVertexWithUV(x + 1, y + 0.5, z + 0.5, 	 i2, i1);	        
	        tessellator.addVertexWithUV(x + 1,	 y,		  z + 1,	 i2, i3);
	        tessellator.addVertexWithUV(x + 1, y + 0.5, z + 0.5,	 i2, i1);
	        tessellator.addVertexWithUV(x, y + 0.6, z + 0.5, i0, i1);
	        tessellator.addVertexWithUV(x, 	 y,		  z + 1, i0, i3);	        
	        tessellator.addVertexWithUV(x,	 y,		  z + 1,	 i0, i3);
	        tessellator.addVertexWithUV(x, y + 0.6, z + 0.5,	 i0, i1);
	        tessellator.addVertexWithUV(x + 1, y + 0.5, z + 0.5, i2, i1);
	        tessellator.addVertexWithUV(x + 1, 	 y,		  z + 1, i2, i3);	        
	        tessellator.addVertexWithUV(x + 1, y + 0.5,  z + 0.5, i4, i5);
	        tessellator.addVertexWithUV(x + 1,		 y, 	   z, i4, i7);
	        tessellator.addVertexWithUV(x + 2, 		 y, 	   z, i6, i7);
	        tessellator.addVertexWithUV(x + 2, y + 0.35, z + 0.5, i6, i5);	        
	        tessellator.addVertexWithUV(x + 2, y + 0.35, z + 0.5, i6, i5);
	        tessellator.addVertexWithUV(x + 2,		 y, 	   z, i6, i7);
	        tessellator.addVertexWithUV(x + 1, 		 y, 	   z, i4, i7);
	        tessellator.addVertexWithUV(x + 1, y + 0.5,  z + 0.5, i4, i5);	        
	        tessellator.addVertexWithUV(x + 2, y + 0.35, z + 0.5, i6, i5);
	        tessellator.addVertexWithUV(x + 2, 	 y, 	   z + 1, i6, i7);
	        tessellator.addVertexWithUV(x + 1, 	 y, 	   z + 1, i4, i7);
	        tessellator.addVertexWithUV(x + 1, y + 0.5,  z + 0.5, i4, i5);	        
	        tessellator.addVertexWithUV(x + 1, y + 0.5,  z + 0.5, i4, i5);
	        tessellator.addVertexWithUV(x + 1, 	 y, 	   z + 1, i4, i7);
	        tessellator.addVertexWithUV(x + 2, 	 y, 	   z + 1, i6, i7);
	        tessellator.addVertexWithUV(x + 2, y + 0.35, z + 0.5, i6, i5);
        }
        else if(world.getBlock(x, y, z + 1) == CWTFCBlocks.nutTreeLog)
        {
        	tessellator.addVertexWithUV(x,		 y, 	  z + 1, i0, i3);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.6, z + 1, i0, i1);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5, z, 	 i2, i1);
	        tessellator.addVertexWithUV(x,		 y, 	  z, 	 i2, i3);	        
	        tessellator.addVertexWithUV(x + 0.5, y + 0.6, z + 1, i0, i1);
	        tessellator.addVertexWithUV(x, 		 y,		  z + 1, i0, i3);
	        tessellator.addVertexWithUV(x, 		 y, 	  z, 	 i2, i3);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5, z, 	 i2, i1);	        
	        tessellator.addVertexWithUV(x + 1,	 y,		  z,	 i2, i3);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5, z,	 i2, i1);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.6, z + 1, i0, i1);
	        tessellator.addVertexWithUV(x + 1, 	 y,		  z + 1, i0, i3);	        
	        tessellator.addVertexWithUV(x + 1,	 y,		  z + 1, i0, i3);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.6, z + 1, i0, i1);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5, z,	 i2, i1);
	        tessellator.addVertexWithUV(x + 1, 	 y,		  z,	 i2, i3);	        
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5,  z, i4, i5);
	        tessellator.addVertexWithUV(x,		 y, 	   z, i4, i7);
	        tessellator.addVertexWithUV(x, 		 y, 	   z - 1, i6, i7);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.35, z - 1, i6, i5);		        
	        tessellator.addVertexWithUV(x + 0.5, y + 0.35, z - 1, i6, i5);
	        tessellator.addVertexWithUV(x,		 y, 	   z - 1, i6, i7);
	        tessellator.addVertexWithUV(x, 		 y, 	   z, i4, i7);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5,  z, i4, i5);	  	        
	        tessellator.addVertexWithUV(x + 0.5, y + 0.35, z - 1, i6, i5);
	        tessellator.addVertexWithUV(x + 1, 	 y, 	   z - 1, i6, i7);
	        tessellator.addVertexWithUV(x + 1, 	 y, 	   z, i4, i7);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5,  z, i4, i5);	        
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5,  z, i4, i5);
	        tessellator.addVertexWithUV(x + 1, 	 y, 	   z, i4, i7);
	        tessellator.addVertexWithUV(x + 1, 	 y, 	   z - 1, i6, i7);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.35, z - 1, i6, i5);
        }
        else if(world.getBlock(x, y, z - 1) == CWTFCBlocks.nutTreeLog)
        {        	
	        tessellator.addVertexWithUV(x + 0.5, y + 0.6, z, 	 i0, i1);
	        tessellator.addVertexWithUV(x, 		 y, 	  z, 	 i0, i3);
	        tessellator.addVertexWithUV(x, 		 y, 	  z + 1, i2, i3);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5, z + 1, i2, i1);	        
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5, z + 1, i2, i1);
	        tessellator.addVertexWithUV(x, 		 y,		  z + 1, i2, i3);
	        tessellator.addVertexWithUV(x, 		 y, 	  z, 	 i0, i3);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.6, z, 	 i0, i1);	        
	        tessellator.addVertexWithUV(x + 1,	 y,		  z,	 i0, i3);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.6, z,	 i0, i1);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5, z + 1, i2, i1);
	        tessellator.addVertexWithUV(x + 1, 	 y,		  z + 1, i2, i3);	        
	        tessellator.addVertexWithUV(x + 1,	 y,		  z + 1, i2, i3);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5, z + 1, i2, i1);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.6, z,	 i0, i1);
	        tessellator.addVertexWithUV(x + 1, 	 y,		  z,	 i0, i3); 	        
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5,  z + 1, i4, i5);
	        tessellator.addVertexWithUV(x,		 y, 	   z + 1, i4, i7);
	        tessellator.addVertexWithUV(x, 		 y, 	   z + 2, i6, i7);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.35, z + 2, i6, i5);	        
	        tessellator.addVertexWithUV(x + 0.5, y + 0.35,  z + 2, i6, i5);
	        tessellator.addVertexWithUV(x,		 y, 	   z + 2, i6, i7);
	        tessellator.addVertexWithUV(x, 		 y, 	   z + 1, i4, i7);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5, z + 1, i4, i5);	        
	        tessellator.addVertexWithUV(x + 0.5, y + 0.35, z + 2, i6, i5);
	        tessellator.addVertexWithUV(x + 1, 	 y, 	   z + 2, i6, i7);
	        tessellator.addVertexWithUV(x + 1, 	 y, 	   z + 1, i4, i7);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5,  z + 1, i4, i5);		        
	        tessellator.addVertexWithUV(x + 0.5, y + 0.5, z + 1, i4, i5);
	        tessellator.addVertexWithUV(x + 1, 	 y, 	   z + 1, i4, i7);
	        tessellator.addVertexWithUV(x + 1, 	 y, 	   z + 2, i6, i7);
	        tessellator.addVertexWithUV(x + 0.5, y + 0.35,  z + 2, i6, i5);
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
