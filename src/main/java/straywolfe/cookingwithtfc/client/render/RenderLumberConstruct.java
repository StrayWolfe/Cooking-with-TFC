package straywolfe.cookingwithtfc.client.render;

import com.bioxx.tfc.TileEntities.TEWoodConstruct;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;

public class RenderLumberConstruct implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		TEWoodConstruct te = (TEWoodConstruct) renderer.blockAccess.getTileEntity(x, y, z);
		
		int d = TEWoodConstruct.plankDetailLevel;
		int dd = d * d;
		int dd2 = dd * 2;
		float div = 1f / d;
		
		boolean breaking = false;
		if(renderer.overrideBlockTexture != null)
			breaking = true;
		
		float minX = 0;
		float maxX = 1;
		float minY = 0;
		float maxY = 1;
		float minZ = 0;
		float maxZ = 1;
		boolean render = false;
		for(int index = 0; index < dd;)
		{
			int in3 = index >> 3;
			if(te.solidCheck[in3])
			{
				minX = 0;
				maxX = 1;
				minY = 0;
				maxY = 1;
				minZ = div * in3;
				maxZ = minZ + div;
				if(!breaking)
					renderer.overrideBlockTexture = CWTFCBlocks.lumberConstruct.getIcon(0, te.woodTypes[index]);
				index++;
				render = true;
			}
			else if(te.solidCheck[in3+24])
			{
				minX = 0;
				maxX = 1;
				minY = div * ((index & 7) + in3);
				maxY = minY + div;
				minZ = 0;
				maxZ = 1;
				if(!breaking)
					renderer.overrideBlockTexture = CWTFCBlocks.lumberConstruct.getIcon(0, te.woodTypes[index]);
				index+=8;
				render = true;
			}
			else if(te.data.get(index))
			{
				minX = 0;
				maxX = 1;
				minY = div * (index & 7);
				maxY = minY + div;
				minZ = div * in3;
				maxZ = minZ + div;
				if(!breaking)
					renderer.overrideBlockTexture = CWTFCBlocks.lumberConstruct.getIcon(0, te.woodTypes[index]);
				index++;
				render = true;
			}
			else
			{
				index++;
				render = false;
			}
			
			if(render)
			{
				renderer.uvRotateTop = 3;
				renderer.uvRotateBottom = 3;
				renderer.setRenderBounds(minX + 0.00003f, minY + 0.00003f, minZ + 0.00003f, maxX - 0.00003f, maxY - 0.00003f, maxZ - 0.00003f);
				renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1, 1, 1);
			}
		}
		
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		for(int index = 0; index < dd; )
		{
			if(te.solidCheck[(dd+index) >> 3])
			{
				minX = 0;
				maxX = 1;
				minY = 0;
				maxY = 1;
				minZ = div * (index >> 3);
				maxZ = minZ + div;
				if(!breaking)
					renderer.overrideBlockTexture = CWTFCBlocks.lumberConstruct.getIcon(0, te.woodTypes[index + dd]);
				index+=8;
				render = true;
			}
			else if(te.data.get(index + dd))
			{
				minX = div * (index & 7);
				maxX = minX + div;
				minY = 0;
				maxY = 1;
				minZ = div * (index >> 3);
				maxZ = minZ + div;
				if(!breaking)
					renderer.overrideBlockTexture = CWTFCBlocks.lumberConstruct.getIcon(0, te.woodTypes[index + dd]);
				index++;
				render = true;
			}
			else
			{
				index++;
				render = false;
			}
			
			if(render)
			{
				renderer.uvRotateNorth = 1;
				renderer.uvRotateSouth = 1;
				renderer.uvRotateEast = 1;
				renderer.uvRotateWest = 1;
				renderer.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
				renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1, 1, 1);
			}
		}
		
		renderer.uvRotateNorth = 0;
		renderer.uvRotateSouth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;

		for(int index = 0; index < dd;)
		{
			if(te.solidCheck[(dd2+index) >> 3])
			{
				minX = 0;
				maxX = 1;
				minY = div * (index >> 3);
				maxY = minY + div;
				minZ = 0;
				maxZ = 1;
				if(!breaking)
					renderer.overrideBlockTexture = CWTFCBlocks.lumberConstruct.getIcon(0, te.woodTypes[index + dd2]);
				index+=8;
				render = true;
			}
			else if(te.data.get(index+dd2))
			{
				minX = div * (index & 7);
				maxX = minX + div;
				minY = div * (index >> 3);
				maxY = minY + div;
				minZ = 0;
				maxZ = 1;
				if(!breaking)
					renderer.overrideBlockTexture = CWTFCBlocks.lumberConstruct.getIcon(0, te.woodTypes[index + dd2]);
				index++;
				render = true;
			}
			else
			{
				index++;
				render = false;
			}
			
			if(render)
			{
				renderer.uvRotateTop = 1;
				renderer.uvRotateBottom = 1;
				renderer.setRenderBounds(minX+0.00001f, minY+0.00001f, minZ+0.00001f, maxX-0.00001f, maxY-0.00001f, maxZ-0.00001f);
				renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1, 1, 1);
			}
		}
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		renderer.clearOverrideBlockTexture();
		
		return true;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

	@Override
	public boolean shouldRender3DInInventory(int modelId) { return false; }

	@Override
	public int getRenderId() { return 0; }

}
