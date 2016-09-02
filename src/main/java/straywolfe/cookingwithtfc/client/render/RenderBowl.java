package straywolfe.cookingwithtfc.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.Vertex;
import straywolfe.cookingwithtfc.common.block.BlockBowl;
import straywolfe.cookingwithtfc.common.tileentity.TileBowl;

public class RenderBowl implements ISimpleBlockRenderingHandler
{	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		if(world.getTileEntity(x, y, z) instanceof TileBowl)
		{
			TileBowl te = (TileBowl)world.getTileEntity(x, y, z);
			BlockBowl bowl = (BlockBowl)block;
			CWTFCRenderer myRenderer = new CWTFCRenderer(x, y, z, bowl, renderer);
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
				
				if(te.getSaladContents()[3] != null)
					renderSalad(te, (0.08F * 3/3) + 0.04F, bowl, myRenderer);
				else if(te.getSaladContents()[2] != null)
					renderSalad(te, (0.08F * 2/3) + 0.04F, bowl, myRenderer);
				else if(te.getSaladContents()[1] != null)
					renderSalad(te, (0.08F * 1/3) + 0.04F, bowl, myRenderer);
				else if(te.getSaladContents()[0] != null)
					renderSalad(te, (0.08F * 0/3) + 0.04F, bowl, myRenderer);
			}
		}
		
		return false;
	}
	
	private void renderSalad(TileBowl te, float level, BlockBowl bowl, CWTFCRenderer myRenderer)
	{
		IIcon salad = bowl.getBowlIcon("VeggySalad");
		float xCoord = te.getBowlCoord(0);
		float zCoord = te.getBowlCoord(1);
		
		if(te.getSaladType() == 2)
			salad = bowl.getBowlIcon("PotatoSalad");
		else if(te.getSaladType() == 1)
			salad = bowl.getBowlIcon("FruitSalad");
		
		float xStart = xCoord - 0.1F;
		float zStart = zCoord - 0.1F;
		float minX = 0.02F + xCoord - 0.1F;
		float maxX = 0.2F - 0.02F + xCoord - 0.1F;
		float minY = level;
		float maxY = level + 0.05F;
		float minZ = 0.02F + zCoord - 0.1F;
		float maxZ = 0.2F - 0.02F + zCoord - 0.1F;
		
		myRenderer.icon = salad;
		
		myRenderer.renderSquareYPos(minX, maxX, maxY, minZ, maxZ, 0F, 0.5F, 0F, 0.5F);
		
		myRenderer.renderQuadXNeg(new Vertex(xStart, minY, maxZ), new Vertex(xStart, minY, minZ), 
				new Vertex(minX, maxY, zStart + 0.2F), new Vertex(minX, maxY, zStart), 0F, 0.5F, 0F, 0.25F);
		
		myRenderer.renderQuadZPos(new Vertex(maxX, minY, zStart + 0.2F), new Vertex(minX, minY, zStart + 0.2F), 
				new Vertex(xStart + 0.2F, maxY, maxZ), new Vertex(xStart, maxY, maxZ), 0F, 0.5F, 0.25F, 0.5F);
		
		myRenderer.renderQuadXPos(new Vertex(xStart + 0.2F, minY, minZ), new Vertex(xStart + 0.2F, minY, maxZ), 
				new Vertex(maxX, maxY, zStart), new Vertex(maxX, maxY, zStart + 0.2F), 0F, 0.5F, 0.5F, 0.75F);
		
		myRenderer.renderQuadZNeg(new Vertex(xStart, minY, minZ), new Vertex(xStart + 0.2F, minY, minZ), 
				new Vertex(minX, maxY, zStart), new Vertex(maxX, maxY, zStart), 0F, 0.5F, 0.75F, 1F);
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
