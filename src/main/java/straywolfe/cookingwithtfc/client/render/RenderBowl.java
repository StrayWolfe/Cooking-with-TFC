package straywolfe.cookingwithtfc.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import straywolfe.cookingwithtfc.common.tileentity.TileBowl;

public class RenderBowl implements ISimpleBlockRenderingHandler
{	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		if(world.getTileEntity(x, y, z) instanceof TileBowl)
		{
			TileBowl te = (TileBowl)world.getTileEntity(x, y, z);
			
			float xCoord = te.getBowlCoord(0);
			float zCoord = te.getBowlCoord(1);
			
			if(xCoord != -1 && zCoord != -1)
			{
				float thickness =  0.03F;
				float wallHt = 0.12F;
				float radius = 0.1F;
				float xMIN = xCoord - radius;
				float xMAX = xCoord + radius;
				float zMIN = zCoord - radius;
				float zMAX = zCoord + radius;
				
				renderer.setRenderBounds(xMIN, 0, zMIN, xMAX, thickness, zMAX);
				renderer.renderStandardBlock(block, x, y, z);
				
				renderer.setRenderBounds(xMIN - thickness, thickness, zMIN, xMIN, wallHt, zMAX);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(xMAX, thickness, zMIN, xMAX + thickness, wallHt, zMAX);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(xMIN, thickness, zMIN - thickness, xMAX, wallHt, zMIN);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(xMIN, thickness, zMAX, xMAX, wallHt, zMAX + thickness);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		
		return false;
	}
	
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
	
	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer)
	{
		
	}
}
