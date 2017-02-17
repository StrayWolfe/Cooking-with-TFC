package straywolfe.cookingwithtfc.client.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import straywolfe.cookingwithtfc.common.tileentity.TileTableStorage;

public class RenderTableStorage implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(tileentity != null && tileentity instanceof TileTableStorage)
		{
			TileTableStorage te = (TileTableStorage)tileentity;
			CWTFCRenderer myRenderer = new CWTFCRenderer(x, y, z, block, renderer);
			
			GL11.glPushMatrix();
			
			if(Minecraft.isFancyGraphicsEnabled())
			{
				if(te.getStackInSlot(0) != null)
				{
					myRenderer.icon = te.getStackInSlot(0).getIconIndex();
					myRenderer.draw3DItem(0.5F, 0.0625F, 0F, 0F, 0F);
				}
				
				if(te.getStackInSlot(1) != null)
				{
					myRenderer.icon = te.getStackInSlot(1).getIconIndex();
					myRenderer.draw3DItem(0.5F, 0.0625F, 0.5F, 0F, 0F);
				}
				
				if(te.getStackInSlot(2) != null)
				{
					myRenderer.icon = te.getStackInSlot(2).getIconIndex();
					myRenderer.draw3DItem(0.5F, 0.0625F, 0F, 0F, 0.5F);
				}
				
				if(te.getStackInSlot(3) != null)
				{
					myRenderer.icon = te.getStackInSlot(3).getIconIndex();
					myRenderer.draw3DItem(0.5F, 0.0625F, 0.5F, 0F, 0.5F);
				}
			}
			else
			{
				if(te.getStackInSlot(0) != null)
				{
					myRenderer.icon = te.getStackInSlot(0).getIconIndex();
					myRenderer.draw2DItem(0.5F, 0F, 0.001F, 0F);
				}
				
				if(te.getStackInSlot(1) != null)
				{
					myRenderer.icon = te.getStackInSlot(1).getIconIndex();
					myRenderer.draw2DItem(0.5F, 0.5F, 0.001F, 0F);
				}
				
				if(te.getStackInSlot(2) != null)
				{
					myRenderer.icon = te.getStackInSlot(2).getIconIndex();
					myRenderer.draw2DItem(0.5F, 0F, 0.001F, 0.5F);
				}
				
				if(te.getStackInSlot(3) != null)
				{
					myRenderer.icon = te.getStackInSlot(3).getIconIndex();
					myRenderer.draw2DItem(0.5F, 0.5F, 0.001F, 0.5F);
				}
			}
			
			GL11.glPopMatrix();			
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) 
	{
		return false;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

	@Override
	public int getRenderId() 
	{
		return 0;
	}

}
