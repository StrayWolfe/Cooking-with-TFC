package straywolfe.cookingwithtfc.client.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import straywolfe.cookingwithtfc.common.tileentity.TileMeat;

public class RenderMeat implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(tileentity != null && tileentity instanceof TileMeat)
		{
			TileMeat te = (TileMeat)tileentity;
			
			float xCoord = te.getMeatCoord(0);
			float zCoord = te.getMeatCoord(1);
			CWTFCRenderer myRenderer = new CWTFCRenderer(x, y, z, block, renderer);
			
			if(xCoord == -1 && zCoord == -1)
				return false;
			
			if(te.getplacedMeat() == null)
				return false;
			
			GL11.glPushMatrix();
			
			if(Minecraft.isFancyGraphicsEnabled())
			{				
				ItemStack is = te.getplacedMeat();
				if(is != null)
				{
					myRenderer.icon = is.getIconIndex();
					myRenderer.draw3DItem(0.5F, 0.0625F, xCoord, 0F, zCoord);
				}
			}
			else
			{
				ItemStack is = te.getplacedMeat();
				if(is != null)
				{
					myRenderer.icon = is.getIconIndex();
					myRenderer.draw2DItem(0.5F, xCoord, 0.001F, zCoord);
				}
			}
			
			GL11.glPopMatrix();
		}
		return false;
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
