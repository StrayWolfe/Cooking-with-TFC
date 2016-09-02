package straywolfe.cookingwithtfc.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.Vertex;
import straywolfe.cookingwithtfc.common.block.BlockClayOven;
import straywolfe.cookingwithtfc.common.lib.ClayOvenStages;
import straywolfe.cookingwithtfc.common.tileentity.TileClayOven;

public class RenderClayOven implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{	
		if(world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileClayOven)
		{
			TileClayOven te = (TileClayOven)world.getTileEntity(x, y, z);
			int buildStage = te.getBuildStage();
			
			if(buildStage != 0)
			{
				BlockClayOven oven = (BlockClayOven)block;
				int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
				IIcon iconOven;
				IIcon iconChimney;
				CWTFCRenderer myRenderer = new CWTFCRenderer(x, y, z, block, renderer);
				
				if(buildStage <= ClayOvenStages.CHIMNEY && te.getCuringStage() == 2)
				{
					iconOven = oven.getOvenIcon("ClayOven2");
					iconChimney = oven.getOvenIcon("ClayOvenChimney2");
				}
				else if(buildStage <= ClayOvenStages.CHIMNEY && te.getCuringStage() >= 3)
				{
						iconOven = oven.getOvenIcon("ClayOven3");
						iconChimney = oven.getOvenIcon("ClayOvenChimney3");
				}
				else if(buildStage == ClayOvenStages.INTERIOR && (te.getCuringStage() == 1 || te.getCuringStage() == 4))
				{
						iconOven = oven.getOvenIcon("ClayOven3");
						iconChimney = oven.getOvenIcon("ClayOvenChimney3");
				}
				else if(buildStage == ClayOvenStages.INTERIOR && te.getCuringStage() == 2)
				{
						iconOven = oven.getOvenIcon("ClayOven4");
						iconChimney = oven.getOvenIcon("ClayOvenChimney4");
				}
				else if(buildStage == ClayOvenStages.CURED)
				{
						iconOven = oven.getOvenIcon("ClayOven5");
						iconChimney = oven.getOvenIcon("ClayOvenChimney5");
				}
				else
				{
					iconOven = oven.getOvenIcon("ClayOven1");
					iconChimney = oven.getOvenIcon("ClayOvenChimney1");
				}
	
				if(buildStage >= ClayOvenStages.PLATFORM)
				{
					myRenderer.icon = iconOven;
					renderPlatform(myRenderer);
				}
				
				if(buildStage > ClayOvenStages.PLATFORM && te.getStackInSlot(6) != null)
					renderSand(myRenderer, meta, te);
				
				if(buildStage > ClayOvenStages.SAND)
				{
					myRenderer.icon = iconOven;
					renderBack(myRenderer, meta, buildStage);
				}
				
				myRenderer.icon = iconOven;
				renderBarrel(myRenderer, meta, buildStage);
				
				if(buildStage > ClayOvenStages.ROOF)
				{
					myRenderer.icon = iconOven;
					renderOpening(myRenderer, meta);
				}
				
				if(buildStage > ClayOvenStages.OPENING)
				{
					myRenderer.icon = iconChimney;
					renderChimney(myRenderer, meta);
				}
				
				if(buildStage >= ClayOvenStages.INTERIOR)
				{
					if(buildStage == ClayOvenStages.CURED)
					{
						myRenderer.icon = oven.getOvenIcon("OvenInterior");
						renderInterior(myRenderer, meta, oven.getOvenIcon("OvenInterior"));
					}
					else
					{
						myRenderer.icon = iconOven;
						renderInterior(myRenderer, meta, iconOven);
					}
					
					myRenderer.icon = iconOven;
					renderInnerOpening(myRenderer, meta, iconOven);
				}
				
				if(te.hasFuel())
					renderFire(te, oven, myRenderer, meta);
			}
		}
		
		return true;
	}
	
	private void renderPlatform(CWTFCRenderer myRenderer)
	{
		myRenderer.renderSquareYPos(0, 1, 0.1, 0, 1);
		
		myRenderer.renderSquareZPos(0, 1, 0, 0.1, 1, 0F, 1F, 0.875F, 1.0F);
		
		myRenderer.renderSquareZNeg(0, 1, 0, 0.1, 0, 0F, 1F, 0.75F, 0.875F);
		
		myRenderer.renderSquareXPos(1, 0, 0.1, 0, 1, 0F, 1F, 0.625F, 0.75F);
		
		myRenderer.renderSquareXNeg(0, 0, 0.1, 0, 1, 0F, 1F, 0.5F, 0.625F);
	}
	
	private void renderSand(CWTFCRenderer myRenderer, int meta, TileClayOven te)
	{
		myRenderer.icon = Block.getBlockFromItem(te.getStackInSlot(6).getItem()).getIcon(0, te.getStackInSlot(6).getItemDamage());
		int buildStage = te.getBuildStage();
		
		if(meta == 0)
		{
			if(buildStage < ClayOvenStages.BACKWALL)
			{
				//Back Bottom
				myRenderer.renderSquareZPos(0.1, 0.9, 0.1, 0.25, 0.9, 0F, 1, 0.25F, 0.5F);
				//Back Top
				myRenderer.renderQuadZPos(new Vertex(0.1F, 0.5F, 0.9F), new Vertex(0.9F, 0.5F, 0.9F), 
					new Vertex(0.3F, 0.25F, 0.9F), new Vertex(0.7F, 0.25F, 0.9F), 0F, 0.25F, 0.75F, 1F, 0F, 0F, 0.5F, 0.5F);
			}
			
			//Left Bottom
			if(buildStage < ClayOvenStages.LBWALL)
				myRenderer.renderSquareXPos(0.9, 0.1, 0.25, 0.2, 0.9, 0F, 1, 0F, 0.25F);
			
			//Right Bottom
			if(buildStage < ClayOvenStages.RBWALL)
				myRenderer.renderSquareXNeg(0.1, 0.1, 0.25, 0.2, 0.9, 0F, 1, 0F, 0.25F);
			
			//Left Top
			if(buildStage < ClayOvenStages.LTWALL)
				myRenderer.renderQuadYPos(new Vertex(0.9F, 0.25F, 0.2F), new Vertex(0.9F, 0.25F, 0.9F), 
						new Vertex(0.7F, 0.5F, 0.2F), new Vertex(0.7F, 0.5F, 0.9F), 0F, 1, 0F, 0.5F);
			
			//Right Top
			if(buildStage < ClayOvenStages.RTWALL)
				myRenderer.renderQuadYPos(new Vertex(0.1F, 0.25F, 0.9F), new Vertex(0.1F, 0.25F, 0.2F), 
						new Vertex(0.3F, 0.5F, 0.9F), new Vertex(0.3F, 0.5F, 0.2F), 0F, 1, 0F, 0.5F);
			
			//Roof
			if(buildStage < ClayOvenStages.ROOF)
				myRenderer.renderQuadYPos(new Vertex(0.3F, 0.5F, 0.9F), new Vertex(0.3F, 0.5F, 0.2F), 
						new Vertex(0.7F, 0.5F, 0.9F), new Vertex(0.7F, 0.5F, 0.2F), 0F, 1, 0F, 0.5F);
			
			if(buildStage < ClayOvenStages.OPENING)
			{
				//Front Top
				myRenderer.renderQuadZNeg(new Vertex(0.1F, 0.25F, 0.2F), new Vertex(0.9F, 0.25F, 0.2F), 
						new Vertex(0.3F, 0.5F, 0.2F), new Vertex(0.7F, 0.5F, 0.2F), 0.25F, 0F, 1F, 0.75F, 0F, 0F, 0.5F, 0.5F);
				
				//Front Bottom
				myRenderer.renderSquareZNeg(0.1, 0.9, 0.1, 0.25, 0.2, 0F, 1, 0F, 0.25F);
				
				//Middle Top Interior
				myRenderer.renderSquareYPos(0.35, 0.65, 0.4, 0, 0.2, 0F, 0.375F, 0F, 0.25F);
				
				//Left Top Interior
				myRenderer.renderQuadYPos(new Vertex(0.8F, 0.25F, 0F), new Vertex(0.8F, 0.25F, 0.2F), 
						new Vertex(0.65F, 0.4F, 0F), new Vertex(0.65F, 0.4F, 0.2F), 0F, 0.25F, 0F, 0.25F);
				
				//Right Top Interior
				myRenderer.renderQuadYPos(new Vertex(0.2F, 0.25F, 0.2F), new Vertex(0.2F, 0.25F, 0F), 
						new Vertex(0.35F, 0.4F, 0.2F), new Vertex(0.35F, 0.4F, 0F), 0F, 0.25F, 0F, 0.25F);
				
				//Left Bottom Interior
				myRenderer.renderSquareXPos(0.8, 0.1, 0.25, 0, 0.2, 0F, 0.25F, 0F, 0.25F);
				
				//Right Bottom Interior
				myRenderer.renderSquareXNeg(0.2, 0.1, 0.25, 0, 0.2, 0F, 0.25F, 0F, 0.25F);
			}
			
			//Front Top
			myRenderer.renderQuadZNeg(new Vertex(0.2F, 0.25F, 0F), new Vertex(0.8F, 0.25F, 0F), 
					new Vertex(0.35F, 0.4F, 0F), new Vertex(0.65F, 0.4F, 0F), 0.19F, 0F, 0.75F, 0.56F, 0.25F, 0.25F, 0.5F, 0.5F);
			
			//Front Bottom
			myRenderer.renderSquareZNeg(0.2, 0.8, 0.1, 0.25, 0, 0F, 0.75F, 0F, 0.25F);
		}
		else if(meta == 1)
		{
			if(buildStage < ClayOvenStages.BACKWALL)
			{
				//Back Bottom
				myRenderer.renderSquareXNeg(0.1, 0.1, 0.25, 0.1, 0.9, 0F, 1, 0.25F, 0.5F);
				//Back Top
				myRenderer.renderQuadXNeg(new Vertex(0.1F, 0.25F, 0.7F), new Vertex(0.1F, 0.25F, 0.3F), 
						new Vertex(0.1F, 0.5F, 0.9F), new Vertex(0.1F, 0.5F, 0.1F), 0.25F, 0F, 1F, 0.75F, 0F, 0F, 0.5F, 0.5F);
			}
			
			//Left Bottom
			if(buildStage < ClayOvenStages.LBWALL)
				myRenderer.renderSquareZPos(0.1, 0.8, 0.1, 0.25, 0.9, 0F, 1, 0F, 0.25F);
			
			//Right Bottom
			if(buildStage < ClayOvenStages.RBWALL)
				myRenderer.renderSquareZNeg(0.1, 0.8, 0.1, 0.25, 0.1, 0F, 1, 0F, 0.25F);
			
			//Left Top
			if(buildStage < ClayOvenStages.LTWALL)
				myRenderer.renderQuadYPos(new Vertex(0.1F, 0.25F, 0.7F), new Vertex(0.8F, 0.25F, 0.7F), 
						new Vertex(0.1F, 0.5F, 0.9F), new Vertex(0.8F, 0.5F, 0.9F), 0F, 1, 0F, 0.5F);
			
			//Right Top
			if(buildStage < ClayOvenStages.RTWALL)
				myRenderer.renderQuadYPos(new Vertex(0.8F, 0.25F, 0.3F), new Vertex(0.1F, 0.25F, 0.3F), 
						new Vertex(0.8F, 0.5F, 0.1F), new Vertex(0.1F, 0.5F, 0.1F), 0F, 1, 0F, 0.5F);
			
			//Roof
			if(buildStage < ClayOvenStages.ROOF)
				myRenderer.renderQuadYPos(new Vertex(0.1F, 0.5F, 0.3F), new Vertex(0.8F, 0.5F, 0.3F), 
						new Vertex(0.1F, 0.5F, 0.7F), new Vertex(0.8F, 0.5F, 0.7F), 0F, 1, 0F, 0.5F);
			
			if(buildStage < ClayOvenStages.OPENING)
			{
				//Front Top
				myRenderer.renderQuadXPos(new Vertex(0.8F, 0.25F, 0.3F), new Vertex(0.8F, 0.25F, 0.7F), 
						new Vertex(0.8F, 0.5F, 0.1F), new Vertex(0.8F, 0.5F, 0.9F), 0.25F, 0F, 1F, 0.75F, 0F, 0F, 0.5F, 0.5F);
				
				//Front Bottom
				myRenderer.renderSquareXPos(0.8, 0.1, 0.25, 0.1, 0.9, 0F, 1, 0.25F, 0.5F);
				
				//Middle Top Interior
				myRenderer.renderSquareYPos(0.8, 1, 0.4, 0.35, 0.65, 0F, 0.25F, 0F, 0.375F);
				
				//Left Top Interior
				myRenderer.renderQuadYPos(new Vertex(0.8F, 0.25F, 0.65F), new Vertex(1F, 0.25F, 0.65F), 
						new Vertex(0.8F, 0.4F, 0.8F), new Vertex(1F, 0.4F, 0.8F), 0F, 0.25F, 0F, 0.25F);
				
				//Right Top Interior
				myRenderer.renderQuadYPos(new Vertex(1F, 0.25F, 0.35F), new Vertex(0.8F, 0.25F, 0.35F), 
						new Vertex(1F, 0.4F, 0.2F), new Vertex(0.8F, 0.4F, 0.2F), 0F, 0.25F, 0F, 0.25F);
				
				//Left Bottom Interior
				myRenderer.renderSquareZPos(0.8, 1, 0.1, 0.25, 0.8, 0F, 0.25F, 0F, 0.25F);
				
				//Right Bottom Interior
				myRenderer.renderSquareZNeg(0.8, 1, 0.1, 0.25, 0.2, 0F, 0.25F, 0F, 0.25F);
			}
			
			//Front Top
			myRenderer.renderQuadXPos(new Vertex(1F, 0.25F, 0.35F), new Vertex(1F, 0.25F, 0.65F), 
					new Vertex(1F, 0.4F, 0.2F), new Vertex(1F, 0.4F, 0.8F), 0.19F, 0F, 0.75F, 0.56F, 0.25F, 0.25F, 0.5F, 0.5F);
			
			//Front Bottom
			myRenderer.renderSquareXPos(1, 0.1, 0.25, 0.2, 0.8, 0F, 0.75F, 0F, 0.25F);
		}
		else if(meta == 2)
		{
			if(buildStage < ClayOvenStages.BACKWALL)
			{
				//Back Bottom
				myRenderer.renderSquareZNeg(0.1, 0.9, 0.1, 0.25, 0.1, 0F, 1, 0.25F, 0.5F);
				//Back Top
				myRenderer.renderQuadZNeg(new Vertex(0.1F, 0.25F, 0.1F), new Vertex(0.9F, 0.25F, 0.1F), 
						new Vertex(0.3F, 0.5F, 0.1F), new Vertex(0.7F, 0.5F, 0.1F), 0.25F, 0F, 1F, 0.75F, 0F, 0F, 0.5F, 0.5F);
			}
			
			//Left Bottom
			if(buildStage < ClayOvenStages.LBWALL)
				myRenderer.renderSquareXNeg(0.1, 0.1, 0.25, 0.1, 0.8, 0F, 1, 0F, 0.25F);
			
			//Right Bottom
			if(buildStage < ClayOvenStages.RBWALL)
				myRenderer.renderSquareXPos(0.9, 0.1, 0.25, 0.1, 0.8, 0F, 1, 0F, 0.25F);
			
			//Left Top
			if(buildStage < ClayOvenStages.LTWALL)
				myRenderer.renderQuadYPos(new Vertex(0.1F, 0.25F, 0.8F), new Vertex(0.1F, 0.25F, 0.1F), 
						new Vertex(0.3F, 0.5F, 0.8F), new Vertex(0.3F, 0.5F, 0.1F), 0F, 1, 0F, 0.5F);
			
			//Right Top
			if(buildStage < ClayOvenStages.RTWALL)
				myRenderer.renderQuadYPos(new Vertex(0.9F, 0.25F, 0.1F), new Vertex(0.9F, 0.25F, 0.8F), 
						new Vertex(0.7F, 0.5F, 0.1F), new Vertex(0.7F, 0.5F, 0.8F), 0F, 1, 0F, 0.5F);
			
			//Roof
			if(buildStage < ClayOvenStages.ROOF)
				myRenderer.renderQuadYPos(new Vertex(0.3F, 0.5F, 0.8F), new Vertex(0.3F, 0.5F, 0.1F), 
						new Vertex(0.7F, 0.5F, 0.8F), new Vertex(0.7F, 0.5F, 0.1F), 0F, 1, 0F, 0.5F);
			
			if(buildStage < ClayOvenStages.OPENING)
			{
				//Front Top
				myRenderer.renderQuadZPos(new Vertex(0.7F, 0.25F, 0.8F), new Vertex(0.3F, 0.25F, 0.8F), 
						new Vertex(0.9F, 0.5F, 0.8F), new Vertex(0.1F, 0.5F, 0.8F), 0.25F, 0F, 1F, 0.75F, 0F, 0F, 0.5F, 0.5F);
				
				//Front Bottom
				myRenderer.renderSquareZPos(0.1, 0.9, 0.1, 0.25, 0.8, 0F, 1, 0F, 0.25F);
				
				//Middle Top Interior
				myRenderer.renderSquareYPos(0.35, 0.65, 0.4, 0.8, 1, 0F, 0.375F, 0F, 0.25F);
				
				//Left Top Interior
				myRenderer.renderQuadYPos(new Vertex(0.2F, 0.25F, 1F), new Vertex(0.2F, 0.25F, 0.8F), 
						new Vertex(0.35F, 0.4F, 1F), new Vertex(0.35F, 0.4F, 0.8F), 0F, 0.25F, 0F, 0.25F);
				
				//Right Top Interior
				myRenderer.renderQuadYPos(new Vertex(0.8F, 0.25F, 0.8F), new Vertex(0.8F, 0.25F, 1F), 
						new Vertex(0.65F, 0.4F, 0.8F), new Vertex(0.65F, 0.4F, 1F), 0F, 0.25F, 0F, 0.25F);
				
				//Left Bottom Interior
				myRenderer.renderSquareXNeg(0.2, 0.1, 0.25, 0.8, 1, 0F, 0.25F, 0F, 0.25F);
				
				//Right Bottom Interior
				myRenderer.renderSquareXPos(0.8, 0.1, 0.25, 0.8, 1, 0F, 0.25F, 0F, 0.25F);
			}
			
			//Front Top
			myRenderer.renderQuadZPos(new Vertex(0.65F, 0.25F, 1F), new Vertex(0.35F, 0.25F, 1F), 
					new Vertex(0.8F, 0.4F, 1F), new Vertex(0.2F, 0.4F, 1F), 0.19F, 0F, 0.75F, 0.56F, 0.25F, 0.25F, 0.5F, 0.5F);
			
			//Front Bottom
			myRenderer.renderSquareZPos(0.2, 0.8, 0.1, 0.25, 1, 0F, 0.75F, 0F, 0.25F);
		}
		else if(meta == 3)
		{
			if(buildStage < ClayOvenStages.BACKWALL)
			{
				//Back Bottom
				myRenderer.renderSquareXPos(0.9, 0.1, 0.25, 0.1, 0.9, 0F, 1, 0.25F, 0.5F);
				//Back Top
				myRenderer.renderQuadXPos(new Vertex(0.9F, 0.25F, 0.3F), new Vertex(0.9F, 0.25F, 0.7F), 
						new Vertex(0.9F, 0.5F, 0.1F), new Vertex(0.9F, 0.5F, 0.9F), 0.25F, 0F, 1F, 0.75F, 0F, 0F, 0.5F, 0.5F);
			}
			
			//Left Bottom
			if(buildStage < ClayOvenStages.LBWALL)
				myRenderer.renderSquareZNeg(0.2, 0.9, 0.1, 0.25, 0.1, 0F, 1, 0F, 0.25F);
			
			//Right Bottom
			if(buildStage < ClayOvenStages.RBWALL)
				myRenderer.renderSquareZPos(0.2, 0.9, 0.1, 0.25, 0.9, 0F, 1, 0F, 0.25F);
			
			//Left Top
			if(buildStage < ClayOvenStages.LTWALL)
				myRenderer.renderQuadYPos(new Vertex(0.9F, 0.25F, 0.3F), new Vertex(0.2F, 0.25F, 0.3F), 
						new Vertex(0.9F, 0.5F, 0.1F), new Vertex(0.2F, 0.5F, 0.1F), 0F, 1, 0F, 0.5F);
			
			//Right Top
			if(buildStage < ClayOvenStages.RTWALL)
				myRenderer.renderQuadYPos(new Vertex(0.2F, 0.25F, 0.7F), new Vertex(0.9F, 0.25F, 0.7F), 
						new Vertex(0.2F, 0.5F, 0.9F), new Vertex(0.9F, 0.5F, 0.9F), 0F, 1, 0F, 0.5F);
			
			//Roof
			if(buildStage < ClayOvenStages.ROOF)
				myRenderer.renderQuadYPos(new Vertex(0.2F, 0.5F, 0.3F), new Vertex(0.9F, 0.5F, 0.3F), 
						new Vertex(0.2F, 0.5F, 0.7F), new Vertex(0.9F, 0.5F, 0.7F), 0F, 1, 0F, 0.5F);
			
			if(buildStage < ClayOvenStages.OPENING)
			{
				//Front Top
				myRenderer.renderQuadXNeg(new Vertex(0.2F, 0.25F, 0.7F), new Vertex(0.2F, 0.25F, 0.3F), 
						new Vertex(0.2F, 0.5F, 0.9F), new Vertex(0.2F, 0.5F, 0.1F), 0.25F, 0F, 1F, 0.75F, 0F, 0F, 0.5F, 0.5F);
				
				//Front Bottom
				myRenderer.renderSquareXNeg(0.2, 0.1, 0.25, 0.1, 0.9, 0F, 1, 0F, 0.25F);
				
				//Middle Top Interior
				myRenderer.renderSquareYPos(0, 0.2, 0.4, 0.35, 0.65, 0F, 0.25F, 0F, 0.375F);
				
				//Left Top Interior
				myRenderer.renderQuadYPos(new Vertex(0.2F, 0.25F, 0.35F), new Vertex(0F, 0.25F, 0.35F), 
						new Vertex(0.2F, 0.4F, 0.2F), new Vertex(0F, 0.4F, 0.2F), 0F, 0.25F, 0F, 0.25F);
				
				//Right Top Interior
				myRenderer.renderQuadYPos(new Vertex(0F, 0.25F, 0.65F), new Vertex(0.2F, 0.25F, 0.65F), 
						new Vertex(0F, 0.4F, 0.8F), new Vertex(0.2F, 0.4F, 0.8F), 0F, 0.25F, 0F, 0.25F);
				
				//Left Bottom Interior
				myRenderer.renderSquareZNeg(0, 0.2, 0.1, 0.25, 0.2, 0F, 0.25F, 0F, 0.25F);
				
				//Right Bottom Interior
				myRenderer.renderSquareZPos(0, 0.2, 0.1, 0.25, 0.8, 0F, 0.25F, 0F, 0.25F);
			}
			
			//Front Top
			myRenderer.renderQuadXNeg(new Vertex(0F, 0.25F, 0.65F), new Vertex(0F, 0.25F, 0.35F), 
					new Vertex(0F, 0.4F, 0.8F), new Vertex(0F, 0.4F, 0.2F), 0.19F, 0F, 0.75F, 0.56F, 0.25F, 0.25F, 0.5F, 0.5F);
			
			//Front Bottom
			myRenderer.renderSquareXNeg(0, 0.1, 0.25, 0.2, 0.8, 0F, 0.75F, 0F, 0.25F);
		}
	}
	
	private void renderBack(CWTFCRenderer myRenderer, int meta, int buildStage)
	{
		if(meta == 0)
		{
			//Left Bottom Barrel
			if(buildStage < ClayOvenStages.LBWALL)
				myRenderer.renderSquareXPos(1, 0.1, 0.25, 0.9, 1, 0F, 0.125F, 0F, 0.1875F);
				
			//Right Bottom Barrel
			if(buildStage < ClayOvenStages.RBWALL)
				myRenderer.renderSquareXNeg(0, 0.1, 0.25, 0.9, 1, 0F, 0.125F, 0.1875F, 0.375F);
			
			//Left Top Barrel
			if(buildStage < ClayOvenStages.LTWALL)
				myRenderer.renderQuadYPos(new Vertex(1F, 0.25F, 0.9F), new Vertex(1F, 0.25F, 1F), 
						new Vertex(0.75F, 0.6F, 0.9F), new Vertex(0.75F, 0.6F, 1F), 0F, 0.125F, 0F, 0.5625F);
			
			//Right Top Barrel
			if(buildStage < ClayOvenStages.RTWALL)
				myRenderer.renderQuadYPos(new Vertex(0F, 0.25F, 1F), new Vertex(0F, 0.25F, 0.9F), 
						new Vertex(0.25F, 0.6F, 1F), new Vertex(0.25F, 0.6F, 0.9F), 0F, 0.125F, 0F, 0.5625F);
			
			if(buildStage < ClayOvenStages.ROOF)
			{
				//Top
				myRenderer.renderSquareYPos(0.25, 0.75, 0.6, 0.9, 1, 0F, 0.5F, 0F, 0.125F);
				
				//Top Front
				myRenderer.renderQuadZNeg(new Vertex(0F, 0.25F, 0.9F), new Vertex(1F, 0.25F, 0.9F), 
						new Vertex(0.25F, 0.6F, 0.9F), new Vertex(0.75F, 0.6F, 0.9F), 0.25F, 0F, 1F, 0.75F, 0F, 0F, 0.5625F, 0.5625F);
				
				//Bottom Front
				myRenderer.renderSquareZNeg(0, 1, 0.1, 0.25, 0.9, 0F, 1F, 0F, 0.1875F);
			}
			
			//Top Back
			myRenderer.renderQuadZPos(new Vertex(0.75F, 0.25F, 1F), new Vertex(0.25F, 0.25F, 1F), 
					new Vertex(1F, 0.6F, 1F), new Vertex(0F, 0.6F, 1F), 0.25F, 0F, 1F, 0.75F, 0F, 0F, 0.5625F, 0.5625F);
			
			//Bottom Back
			myRenderer.renderSquareZPos(0, 1, 0.1, 0.25, 1, 0F, 1F, 0F, 0.1875F);
		}
		else if(meta == 1)
		{
			//Left Bottom Barrel
			if(buildStage < ClayOvenStages.LBWALL)
				myRenderer.renderSquareZPos(0, 0.1, 0.1, 0.25, 1, 0F, 0.125F, 0F, 0.1875F);
			
			//Right Bottom Barrel
			if(buildStage < ClayOvenStages.RBWALL)
				myRenderer.renderSquareZNeg(0, 0.1, 0.1, 0.25, 0, 0F, 0.125F, 0.1875F, 0.375F);
			
			//Left Top Barrel
			if(buildStage < ClayOvenStages.LTWALL)
				myRenderer.renderQuadYPos(new Vertex(0F, 0.25F, 0.75F), new Vertex(0.1F, 0.25F, 0.75F), 
						new Vertex(0F, 0.6F, 1F), new Vertex(0.1F, 0.6F, 1F), 0F, 0.125F, 0F, 0.5625F);
			
			//Right Top Barrel
			if(buildStage < ClayOvenStages.RTWALL)
				myRenderer.renderQuadYPos(new Vertex(0.1F, 0.25F, 0.25F), new Vertex(0F, 0.25F, 0.25F), 
						new Vertex(0.1F, 0.6F, 0F), new Vertex(0F, 0.6F, 0F), 0F, 0.125F, 0F, 0.5625F);
			
			if(buildStage < ClayOvenStages.ROOF)
			{
				//Top
				myRenderer.renderSquareYPos(0, 0.1, 0.6, 0.25, 0.75, 0F, 0.125F, 0F, 0.5F);
				
				//Top Front
				myRenderer.renderQuadXPos(new Vertex(0.1F, 0.25F, 0.25F), new Vertex(0.1F, 0.25F, 0.75F), 
						new Vertex(0.1F, 0.6F, 0F), new Vertex(0.1F, 0.6F, 1F), 0.25F, 0F, 1F, 0.75F, 0F, 0F, 0.5625F, 0.5625F);
				
				//Bottom Front
				myRenderer.renderSquareXPos(0.1, 0.1, 0.25, 0, 1, 0F, 1F, 0F, 0.1875F);
			}
			
			//Top Back
			myRenderer.renderQuadXNeg(new Vertex(0F, 0.25F, 0.75F), new Vertex(0F, 0.25F, 0.25F), 
					new Vertex(0F, 0.6F, 1F), new Vertex(0F, 0.6F, 0F), 0.25F, 0F, 1F, 0.75F, 0F, 0F, 0.5625F, 0.5625F);
			
			//Bottom Back
			myRenderer.renderSquareXNeg(0, 0.1, 0.25, 0, 1, 0F, 1F, 0F, 0.1875F);
		}
		else if(meta == 2)
		{
			//Left Bottom Barrel
			if(buildStage < ClayOvenStages.LBWALL)
				myRenderer.renderSquareXNeg(0, 0.1, 0.25, 0, 0.1, 0F, 0.125F, 0F, 0.1875F);
			
			//Right Bottom Barrel
			if(buildStage < ClayOvenStages.RBWALL)
				myRenderer.renderSquareXPos(1, 0.1, 0.25, 0, 0.1, 0F, 0.125F, 0.1875F, 0.375F);
			
			//Left Top Barrel
			if(buildStage < ClayOvenStages.LTWALL)
				myRenderer.renderQuadYPos(new Vertex(0F, 0.25F, 0.1F), new Vertex(0F, 0.25F, 0F), 
						new Vertex(0.25F, 0.6F, 0.1F), new Vertex(0.25F, 0.6F, 0F), 0F, 0.125F, 0F, 0.5625F);
			
			//Right Top Barrel
			if(buildStage < ClayOvenStages.RTWALL)
				myRenderer.renderQuadYPos(new Vertex(1F, 0.25F, 0F), new Vertex(1F, 0.25F, 0.1F), 
						new Vertex(0.75F, 0.6F, 0F), new Vertex(0.75F, 0.6F, 0.1F), 0F, 0.125F, 0F, 0.5625F);
			
			if(buildStage < ClayOvenStages.ROOF)
			{
				//Top
				myRenderer.renderSquareYPos(0.25, 0.75, 0.6, 0, 0.1, 0F, 0.5F, 0F, 0.125F);
				
				//Top Front
				myRenderer.renderQuadZPos(new Vertex(0.75F, 0.25F, 0.1F), new Vertex(0.25F, 0.25F, 0.1F), 
						new Vertex(1F, 0.6F, 0.1F), new Vertex(0F, 0.6F, 0.1F), 0.25F, 0F, 1F, 0.75F, 0F, 0F, 0.5625F, 0.5625F);
				
				//Bottom Front
				myRenderer.renderSquareZPos(0, 1, 0.1, 0.25, 0.1, 0F, 1F, 0F, 0.1875F);
			}
			
			//Top Back
			myRenderer.renderQuadZNeg(new Vertex(0F, 0.25F, 0F), new Vertex(1F, 0.25F, 0F), 
					new Vertex(0.25F, 0.6F, 0F), new Vertex(0.75F, 0.6F, 0F), 0.25F, 0F, 1F, 0.75F, 0F, 0F, 0.5625F, 0.5625F);
			
			//Bottom Back
			myRenderer.renderSquareZNeg(0, 1, 0.1, 0.25, 0, 0F, 1F, 0F, 0.1875F);
		}
		else if(meta == 3)
		{
			//Left Bottom Barrel
			if(buildStage < ClayOvenStages.LBWALL)
				myRenderer.renderSquareZNeg(0.9, 1, 0.1, 0.25, 0, 0F, 0.125F, 0F, 0.1875F);
			
			//Right Bottom Barrel
			if(buildStage < ClayOvenStages.RBWALL)
				myRenderer.renderSquareZPos(0.9, 1, 0.1, 0.25, 1, 0F, 0.125F, 0.1875F, 0.375F);
			
			//Left Top Barrel
			if(buildStage < ClayOvenStages.LTWALL)
				myRenderer.renderQuadYPos(new Vertex(1F, 0.25F, 0.25F), new Vertex(0.9F, 0.25F, 0.25F), 
						new Vertex(1F, 0.6F, 0F), new Vertex(0.9F, 0.6F, 0F), 0F, 0.125F, 0F, 0.5625F);
			
			//Right Top Barrel
			if(buildStage < ClayOvenStages.RTWALL)
				myRenderer.renderQuadYPos(new Vertex(0.9F, 0.25F, 0.75F), new Vertex(1F, 0.25F, 0.75F), 
						new Vertex(0.9F, 0.6F, 1F), new Vertex(1F, 0.6F, 1F), 0F, 0.125F, 0F, 0.5625F);
			
			if(buildStage < ClayOvenStages.ROOF)
			{
				//Top
				myRenderer.renderSquareYPos(0.9, 1, 0.6, 0.25, 0.75, 0F, 0.125F, 0F, 0.5F);
				
				//Top Front
				myRenderer.renderQuadXNeg(new Vertex(0.9F, 0.25F, 0.75F), new Vertex(0.9F, 0.25F, 0.25F), 
						new Vertex(0.9F, 0.6F, 1F), new Vertex(0.9F, 0.6F, 0F), 0.25F, 0F, 1F, 0.75F, 0F, 0F, 0.5625F, 0.5625F);
				
				//Bottom Front
				myRenderer.renderSquareXNeg(0.9, 0.1, 0.25, 0, 1, 0F, 1F, 0F, 0.1875F);
			}
			
			//Top Back
			myRenderer.renderQuadXPos(new Vertex(1F, 0.25F, 0.25F), new Vertex(1F, 0.25F, 0.75F), 
					new Vertex(1F, 0.6F, 0F), new Vertex(1F, 0.6F, 1F), 0.25F, 0F, 1F, 0.75F, 0F, 0F, 0.5625F, 0.5625F);
			
			//Bottom Back
			myRenderer.renderSquareXPos(1, 0.1, 0.25, 0, 1, 0F, 1F, 0F, 0.1875F);
		}
	}
	
	private void renderBarrel(CWTFCRenderer myRenderer, int meta, int buildStage)
	{
		if(meta == 0)
		{
			//Left Bottom Wall
			if(buildStage > ClayOvenStages.BACKWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.LTWALL)
					myRenderer.renderSquareYPos(0.9, 1, 0.25, 0.2, 1, 0F, 0.125F, 0F, 1F);
				
				//Front
				myRenderer.renderSquareZNeg(0.9, 1, 0.1, 0.25, 0.2, 0F, 0.125F, 0F, 0.1875F);
				
				//Side
				myRenderer.renderSquareXPos(1, 0.1, 0.25, 0.2, 1, 0F, 0.8F, 0F, 0.1875F);
			}
			
			//Right Bottom Wall
			if(buildStage > ClayOvenStages.LBWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.RTWALL)
					myRenderer.renderSquareYPos(0, 0.1, 0.25, 0.2, 1, 0F, 0.125F, 0F, 1F);
				
				//Front
				myRenderer.renderSquareZNeg(0, 0.1, 0.1, 0.25, 0.2, 0F, 0.125F, 0F, 0.1875F);
				
				//Side
				myRenderer.renderSquareXNeg(0, 0.1, 0.25, 0.2, 1, 0.2F, 1F, 0F, 0.1875F);
			}
			
			//Left Top Wall
			if(buildStage > ClayOvenStages.RBWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.ROOF)
					myRenderer.renderQuadYPos(new Vertex(0.7F, 0.5F, 0.9F), new Vertex(0.7F, 0.5F, 0.2F),
							new Vertex(0.75F, 0.6F, 0.9F), new Vertex(0.75F, 0.6F, 0.2F), 0F, 1F, 0F, 0.125F);
				
				//Front
				myRenderer.renderQuadZNeg(new Vertex(0.9F, 0.25F, 0.2F), new Vertex(1F, 0.25F, 0.2F),
						new Vertex(0.7F, 0.5F, 0.2F), new Vertex(0.75F, 0.6F, 0.2F), 0F, 0F, 0.125F, 0.125F, 0F, 0F, 0.45F, 0.55F);
				
				//Side
				myRenderer.renderQuadYPos(new Vertex(1F, 0.25F, 0.2F), new Vertex(1F, 0.25F, 1F),
						new Vertex(0.75F, 0.6F, 0.2F), new Vertex(0.75F, 0.6F, 1F), 0F, 0.8F, 0F, 0.5625F);
			}
			
			//Right Top Wall
			if(buildStage > ClayOvenStages.LTWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.ROOF)
					myRenderer.renderQuadYPos(new Vertex(0.3F, 0.5F, 0.2F), new Vertex(0.3F, 0.5F, 1F),
							new Vertex(0.25F, 0.6F, 0.2F), new Vertex(0.25F, 0.6F, 1F), 0F, 1F, 0F, 0.125F);
				
				//Front
				myRenderer.renderQuadZNeg(new Vertex(0F, 0.25F, 0.2F), new Vertex(0.1F, 0.25F, 0.2F),
						new Vertex(0.25F, 0.6F, 0.2F), new Vertex(0.3F, 0.5F, 0.2F), 0F, 0F, 0.125F, 0.125F, 0F, 0F, 0.55F, 0.45F);
				
				//Side
				myRenderer.renderQuadYPos(new Vertex(0F, 0.25F, 1F), new Vertex(0F, 0.25F, 0.2F),
						new Vertex(0.25F, 0.6F, 1F), new Vertex(0.25F, 0.6F, 0.2F), 0.2F, 1F, 0F, 0.5625F);
			}
			
			//Top Wall
			if(buildStage > ClayOvenStages.RTWALL)
			{
				//Front
				myRenderer.renderQuadZNeg(new Vertex(0.3F, 0.5F, 0.2F), new Vertex(0.7F, 0.5F, 0.2F),
						new Vertex(0.25F, 0.6F, 0.2F), new Vertex(0.75F, 0.6F, 0.2F), 0F, 0.05F, 0.45F, 0.5F, 0F, 0F, 0.125F, 0.125F);
				
				//Top
				myRenderer.renderSquareYPos(0.25, 0.75, 0.6, 0.2, 1, 0F, 0.5F, 0.2F, 1F);
			}
		}
		else if(meta == 1)
		{
			//Left Bottom Wall
			if(buildStage > ClayOvenStages.BACKWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.LTWALL)
					myRenderer.renderSquareYPos(0, 0.8, 0.25, 0.9, 1, 0F, 1F, 0F, 0.125F);
				
				//Front
				myRenderer.renderSquareXPos(0.8, 0.1, 0.25, 0.9, 1, 0F, 0.125F, 0F, 0.1875F);
				
				//Side
				myRenderer.renderSquareZPos(0, 0.8, 0.1, 0.25, 1, 0F, 0.8F, 0F, 0.1875F);
			}
			
			//Right Bottom Wall
			if(buildStage > ClayOvenStages.LBWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.RTWALL)
					myRenderer.renderSquareYPos(0, 0.8, 0.25, 0, 0.1, 0F, 1F, 0F, 0.125F);
				
				//Front
				myRenderer.renderSquareXPos(0.8, 0.1, 0.25, 0, 0.1, 0F, 0.125F, 0F, 0.1875F);
				
				//Side
				myRenderer.renderSquareZNeg(0, 0.8, 0.1, 0.25, 0, 0.2F, 1F, 0F, 0.1875F);
			}
			
			//Left Top Wall
			if(buildStage > ClayOvenStages.RBWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.ROOF)
					myRenderer.renderQuadYPos(new Vertex(0.8F, 0.5F, 0.75F), new Vertex(0.1F, 0.5F, 0.75F),
							new Vertex(0.8F, 0.6F, 0.7F), new Vertex(0.1F, 0.6F, 0.7F), 0F, 1F, 0F, 0.125F);
				
				//Front
				myRenderer.renderQuadXPos(new Vertex(0.8F, 0.25F, 0.7F), new Vertex(0.8F, 0.25F, 0.75F),
						new Vertex(0.8F, 0.6F, 0.9F), new Vertex(0.8F, 0.5F, 1F), 0F, 0F, 0.125F, 0.125F, 0.55F, 0.45F, 1F, 1F);
				
				//Side
				myRenderer.renderQuadYPos(new Vertex(0F, 0.25F, 0.75F), new Vertex(0.8F, 0.25F, 0.75F),
						new Vertex(0F, 0.6F, 1F), new Vertex(0.8F, 0.6F, 1F), 0F, 0.8F, 0F, 0.5625F);
			}
			
			//Right Top Wall
			if(buildStage > ClayOvenStages.LTWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.ROOF)
					myRenderer.renderQuadYPos(new Vertex(0.1F, 0.5F, 0.25F), new Vertex(0.8F, 0.5F, 0.25F),
							new Vertex(0.1F, 0.6F, 0.3F), new Vertex(0.8F, 0.6F, 0.3F), 0F, 1F, 0F, 0.125F);
				
				//Front
				myRenderer.renderQuadXPos(new Vertex(0.8F, 0.25F, 0.25F), new Vertex(0.8F, 0.25F, 0.3F),
						new Vertex(0.8F, 0.5F, 0F), new Vertex(0.8F, 0.6F, 0.1F), 0F, 0F, 0.125F, 0.125F, 0.45F, 0.55F, 1F, 1F);
				
				//Side
				myRenderer.renderQuadYPos(new Vertex(0.8F, 0.25F, 0.25F), new Vertex(0F, 0.25F, 0.25F),
						new Vertex(0.8F, 0.6F, 0F), new Vertex(0F, 0.6F, 0F), 0.2F, 1F, 0F, 0.5625F);
			}
			
			//Top Wall
			if(buildStage > ClayOvenStages.RTWALL)
			{
				//Front
				myRenderer.renderQuadXPos(new Vertex(0.8F, 0.5F, 0.25F), new Vertex(0.8F, 0.5F, 0.75F),
						new Vertex(0.8F, 0.6F, 0.3F), new Vertex(0.8F, 0.6F, 0.7F), 0F, 0.05F, 0.45F, 0.5F, 0F, 0F, 0.125F, 0.125F);
				
				//Top
				myRenderer.renderSquareYPos(0, 0.8, 0.6, 0.25, 0.75, 0F, 0.8F, 0F, 0.5F);
			}
		}
		else if(meta == 2)
		{
			//Left Bottom Wall
			if(buildStage > ClayOvenStages.BACKWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.LTWALL)
					myRenderer.renderSquareYPos(0, 0.1, 0.25, 0, 0.8, 0F, 0.125F, 0F, 1F);
				
				//Front
				myRenderer.renderSquareZPos(0, 0.1, 0.1, 0.25, 0.8, 0F, 0.125F, 0F, 0.1875F);
				
				//Side
				myRenderer.renderSquareXNeg(0, 0.1, 0.25, 0, 0.8, 0F, 0.8F, 0F, 0.1875F);
			}
			
			//Right Bottom Wall
			if(buildStage > ClayOvenStages.LBWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.RTWALL)
					myRenderer.renderSquareYPos(0.9, 1, 0.25, 0, 0.8, 0F, 0.125F, 0F, 1F);
				
				//Front
				myRenderer.renderSquareZPos(0.9, 1, 0.1, 0.25, 0.8, 0F, 0.125F, 0F, 0.1875F);
				
				//Side
				myRenderer.renderSquareXPos(1, 0.1, 0.25, 0, 0.8, 0.2F, 1F, 0F, 0.1875F);
			}
			
			//Left Top Wall
			if(buildStage > ClayOvenStages.RBWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.ROOF)
					myRenderer.renderQuadYPos(new Vertex(0.3F, 0.5F, 0.1F), new Vertex(0.3F, 0.5F, 0.8F),
							new Vertex(0.25F, 0.6F, 0.1F), new Vertex(0.25F, 0.6F, 0.8F), 0F, 1F, 0F, 0.125F);
				
				//Front
				myRenderer.renderQuadZPos(new Vertex(0.3F, 0.25F, 0.8F), new Vertex(0.25F, 0.25F, 0.8F),
						new Vertex(0.1F, 0.6F, 0.8F), new Vertex(0F, 0.5F, 0.8F), 0F, 0F, 0.125F, 0.125F, 0.55F, 0.45F, 1F, 1F);
				
				//Side
				myRenderer.renderQuadYPos(new Vertex(0F, 0.25F, 0.8F), new Vertex(0F, 0.25F, 0F),
						new Vertex(0.25F, 0.6F, 0.8F), new Vertex(0.25F, 0.6F, 0F), 0F, 0.8F, 0F, 0.5625F);
			}
			
			//Right Top Wall
			if(buildStage > ClayOvenStages.LTWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.ROOF)
					myRenderer.renderQuadYPos(new Vertex(0.7F, 0.5F, 0.8F), new Vertex(0.7F, 0.5F, 0.1F),
							new Vertex(0.75F, 0.6F, 0.8F), new Vertex(0.75F, 0.6F, 0.1F), 0F, 1F, 0F, 0.125F);
				
				//Front
				myRenderer.renderQuadZPos(new Vertex(0.75F, 0.25F, 0.8F), new Vertex(0.7F, 0.25F, 0.8F),
						new Vertex(1F, 0.5F, 0.8F), new Vertex(0.9F, 0.6F, 0.8F), 0F, 0F, 0.125F, 0.125F, 0.45F, 0.55F, 1F, 1F);
				
				//Side
				myRenderer.renderQuadYPos(new Vertex(1F, 0.25F, 0F), new Vertex(1F, 0.25F, 0.8F),
						new Vertex(0.75F, 0.6F, 0F), new Vertex(0.75F, 0.6F, 0.8F), 0.2F, 1F, 0F, 0.5625F);
			}
			
			//Top Wall
			if(buildStage > ClayOvenStages.RTWALL)
			{
				//Front
				myRenderer.renderQuadZPos(new Vertex(0.75F, 0.5F, 0.8F), new Vertex(0.25F, 0.5F, 0.8F),
						new Vertex(0.7F, 0.6F, 0.8F), new Vertex(0.3F, 0.6F, 0.8F), 0F, 0.05F, 0.45F, 0.5F, 0F, 0F, 0.125F, 0.125F);
				
				//Top
				myRenderer.renderSquareYPos(0.25, 0.75, 0.6, 0, 0.8, 0F, 0.5F, 0F, 0.8F);
			}
		}
		else if(meta == 3)
		{
			//Left Bottom Wall
			if(buildStage > ClayOvenStages.BACKWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.LTWALL)
					myRenderer.renderSquareYPos(0.2, 1, 0.25, 0, 0.1, 0F, 1F, 0F, 0.125F);
				
				//Front
				myRenderer.renderSquareXNeg(0.2, 0.1, 0.25, 0, 0.1, 0F, 0.125F, 0F, 0.1875F);
				
				//Side
				myRenderer.renderSquareZNeg(0.2, 1, 0.1, 0.25, 0, 0F, 0.8F, 0F, 0.1875F);
			}
			
			//Right Bottom Wall
			if(buildStage > ClayOvenStages.LBWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.RTWALL)
					myRenderer.renderSquareYPos(0.2, 1, 0.25, 0.9, 1, 0F, 1F, 0F, 0.125F);
				
				//Front
				myRenderer.renderSquareXNeg(0.2, 0.1, 0.25, 0.9, 1, 0F, 0.125F, 0F, 0.1875F);
				
				//Side
				myRenderer.renderSquareZPos(0.2, 1, 0.1, 0.25, 1, 0.2F, 1F, 0F, 0.1875F);
			}
			
			//Left Top Wall
			if(buildStage > ClayOvenStages.RBWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.ROOF)
					myRenderer.renderQuadYPos(new Vertex(0.2F, 0.5F, 0.25F), new Vertex(0.9F, 0.5F, 0.25F),
							new Vertex(0.2F, 0.6F, 0.3F), new Vertex(0.9F, 0.6F, 0.3F), 0F, 1F, 0F, 0.125F);
				
				//Front
				myRenderer.renderQuadXNeg(new Vertex(0.2F, 0.25F, 0.3F), new Vertex(0.2F, 0.25F, 0.25F),
						new Vertex(0.2F, 0.6F, 0.1F), new Vertex(0.2F, 0.5F, 0F), 0F, 0F, 0.125F, 0.125F, 0.55F, 0.45F, 1F, 1F);
				
				//Side
				myRenderer.renderQuadYPos(new Vertex(1F, 0.25F, 0.25F), new Vertex(0.2F, 0.25F, 0.25F),
						new Vertex(1F, 0.6F, 0F), new Vertex(0.2F, 0.6F, 0F), 0F, 0.8F, 0F, 0.5625F);
			}
			
			//Right Top Wall
			if(buildStage > ClayOvenStages.LTWALL)
			{
				//Top
				if(buildStage < ClayOvenStages.ROOF)
					myRenderer.renderQuadYPos(new Vertex(0.9F, 0.5F, 0.75F), new Vertex(0.2F, 0.5F, 0.75F),
							new Vertex(0.9F, 0.6F, 0.7F), new Vertex(0.2F, 0.6F, 0.7F), 0F, 1F, 0F, 0.125F);
				
				//Front
				myRenderer.renderQuadXNeg(new Vertex(0.2F, 0.25F, 0.75F), new Vertex(0.2F, 0.25F, 0.7F),
						new Vertex(0.2F, 0.5F, 1F), new Vertex(0.2F, 0.6F, 0.9F), 0F, 0F, 0.125F, 0.125F, 0.45F, 0.55F, 1F, 1F);
				
				//Side
				myRenderer.renderQuadYPos(new Vertex(0.2F, 0.25F, 0.75F), new Vertex(1F, 0.25F, 0.75F),
						new Vertex(0.2F, 0.6F, 1F), new Vertex(1F, 0.6F, 1F), 0.2F, 1F, 0F, 0.5625F);
			}
			
			//Top Wall
			if(buildStage > ClayOvenStages.RTWALL)
			{
				//Front
				myRenderer.renderQuadXNeg(new Vertex(0.2F, 0.5F, 0.75F), new Vertex(0.2F, 0.5F, 0.25F),
						new Vertex(0.2F, 0.6F, 0.7F), new Vertex(0.2F, 0.6F, 0.3F), 0F, 0.05F, 0.45F, 0.5F, 0F, 0F, 0.125F, 0.125F);
				
				//Top
				myRenderer.renderSquareYPos(0.2, 1, 0.6, 0.25, 0.75, 0.2F, 1F, 0F, 0.5F);
			}
		}
	}
	
	private void renderOpening(CWTFCRenderer myRenderer, int meta)
	{
		if(meta == 0)
		{
			//Middle Top
			myRenderer.renderSquareYPos(0.3, 0.7, 0.5, 0, 0.2, 0F, 0.375F, 0F, 0.25F);
			
			//Middle Front
			myRenderer.renderQuadZNeg(new Vertex(0.35F, 0.4F, 0F), new Vertex(0.65F, 0.4F, 0F), 
					new Vertex(0.3F, 0.5F, 0F), new Vertex(0.7F, 0.5F, 0F), 0F, 0.0625F, 0.4375F, 0.5F, 0.125F, 0.125F, 0.25F, 0.25F);
			
			//Left Top
			myRenderer.renderQuadYPos(new Vertex(0.9F, 0.25F, 0F), new Vertex(0.9F, 0.25F, 0.2F), 
					new Vertex(0.7F, 0.5F, 0F), new Vertex(0.7F, 0.5F, 0.2F), 0F, 0.25F, 0F, 0.5F);
			
			//Left Top Front
			myRenderer.renderQuadZNeg(new Vertex(0.8F, 0.25F, 0F), new Vertex(0.9F, 0.25F, 0F), 
					new Vertex(0.65F, 0.4F, 0F), new Vertex(0.7F, 0.5F, 0F), 0.25F, 0.25F, 0.375F, 0.375F, 0F, 0.06F, 0.39F, 0.5F);
			
			//Right Top
			myRenderer.renderQuadYPos(new Vertex(0.1F, 0.25F, 0.2F), new Vertex(0.1F, 0.25F, 0F), 
					new Vertex(0.3F, 0.5F, 0.2F), new Vertex(0.3F, 0.5F, 0F), 0F, 0.25F, 0F, 0.5F);
			
			//Right Top Front
			myRenderer.renderQuadZNeg(new Vertex(0.1F, 0.25F, 0F), new Vertex(0.2F, 0.25F, 0F), 
					new Vertex(0.3F, 0.5F, 0F), new Vertex(0.35F, 0.4F, 0F), 0.25F, 0.25F, 0.375F, 0.375F, 0.06F, 0F, 0.5F, 0.39F);
			
			//Left Bottom Side
			myRenderer.renderSquareXPos(0.9, 0.1, 0.25, 0, 0.2, 0F, 0.25F, 0F, 0.25F);
			
			//Left Bottom Front
			myRenderer.renderSquareZNeg(0.8, 0.9, 0.1, 0.25, 0, 0F, 0.125F, 0F, 0.25F);
			
			//Right Bottom Side
			myRenderer.renderSquareXNeg(0.1, 0.1, 0.25, 0, 0.2, 0F, 0.25F, 0F, 0.25F);
			
			//Right Bottom Front
			myRenderer.renderSquareZNeg(0.1, 0.2, 0.1, 0.25, 0, 0F, 0.125F, 0F, 0.25F);
		}
		else if(meta == 1)
		{
			//Middle Top
			myRenderer.renderSquareYPos(0.8, 1, 0.5, 0.3, 0.7, 0F, 0.25F, 0F, 0.375F);
			
			//Middle Front
			myRenderer.renderQuadXPos(new Vertex(1F, 0.4F, 0.3F), new Vertex(1F, 0.4F, 0.7F), 
					new Vertex(1F, 0.5F, 0.35F), new Vertex(1F, 0.5F, 0.65F), 0F, 0.0625F, 0.4375F, 0.5F, 0.125F, 0.125F, 0.25F, 0.25F);
			
			//Left Top
			myRenderer.renderQuadYPos(new Vertex(0.8F, 0.25F, 0.7F), new Vertex(1F, 0.25F, 0.7F), 
					new Vertex(0.8F, 0.5F, 0.9F), new Vertex(1F, 0.5F, 0.9F), 0F, 0.25F, 0F, 0.5F);
			
			//Left Top Front
			myRenderer.renderQuadXPos(new Vertex(1F, 0.25F, 0.65F), new Vertex(1F, 0.25F, 0.7F), 
					new Vertex(1F, 0.5F, 0.8F), new Vertex(1F, 0.4F, 0.9F), 0.25F, 0.25F, 0.375F, 0.375F, 0.06F, 0F, 0.5F, 0.39F);
			
			//Right Top
			myRenderer.renderQuadYPos(new Vertex(1F, 0.25F, 0.3F), new Vertex(0.8F, 0.25F, 0.3F), 
					new Vertex(1F, 0.5F, 0.1F), new Vertex(0.8F, 0.5F, 0.1F), 0F, 0.25F, 0F, 0.5F);
			
			//Right Top Front
			myRenderer.renderQuadXPos(new Vertex(1F, 0.25F, 0.3F), new Vertex(1F, 0.25F, 0.35F), 
					new Vertex(1F, 0.4F, 0.1F), new Vertex(1F, 0.5F, 0.2F), 0.25F, 0.25F, 0.375F, 0.375F, 0F, 0.06F, 0.39F, 0.5F);
			
			//Left Bottom Side
			myRenderer.renderSquareZPos(0.8, 1, 0.1, 0.25, 0.9, 0F, 0.25F, 0F, 0.25F);
			
			//Left Bottom Front
			myRenderer.renderSquareXPos(1, 0.1, 0.25, 0.8, 0.9, 0F, 0.125F, 0F, 0.25F);
			
			//Right Bottom Side
			myRenderer.renderSquareZNeg(0.8, 1, 0.1, 0.25, 0.1, 0F, 0.25F, 0F, 0.25F);
			
			//Right Bottom Front
			myRenderer.renderSquareXPos(1, 0.1, 0.25, 0.1, 0.2, 0F, 0.125F, 0F, 0.25F);
		}
		else if(meta == 2)
		{
			//Middle Top
			myRenderer.renderSquareYPos(0.3, 0.7, 0.5, 0.8, 1, 0F, 0.375F, 0F, 0.25F);
			
			//Middle Front
			myRenderer.renderQuadZPos(new Vertex(0.7F, 0.4F, 1F), new Vertex(0.3F, 0.4F, 1F), 
					new Vertex(0.65F, 0.5F, 1F), new Vertex(0.35F, 0.5F, 1F), 0F, 0.0625F, 0.4375F, 0.5F, 0.125F, 0.125F, 0.25F, 0.25F);
			
			//Left Top
			myRenderer.renderQuadYPos(new Vertex(0.1F, 0.25F, 1F), new Vertex(0.1F, 0.25F, 0.8F), 
					new Vertex(0.3F, 0.5F, 1F), new Vertex(0.3F, 0.5F, 0.8F), 0F, 0.25F, 0F, 0.5F);
			
			//Left Top Front
			myRenderer.renderQuadZPos(new Vertex(0.35F, 0.25F, 1F), new Vertex(0.3F, 0.25F, 1F), 
					new Vertex(0.2F, 0.5F, 1F), new Vertex(0.1F, 0.4F, 1F), 0.25F, 0.25F, 0.375F, 0.375F, 0.06F, 0F, 0.5F, 0.39F);
			
			//Right Top
			myRenderer.renderQuadYPos(new Vertex(0.9F, 0.25F, 0.8F), new Vertex(0.9F, 0.25F, 1F), 
					new Vertex(0.7F, 0.5F, 0.8F), new Vertex(0.7F, 0.5F, 1F), 0F, 0.25F, 0F, 0.5F);
			
			//Right Top Front
			myRenderer.renderQuadZPos(new Vertex(0.7F, 0.25F, 1F), new Vertex(0.65F, 0.25F, 1F), 
					new Vertex(0.9F, 0.4F, 1F), new Vertex(0.8F, 0.5F, 1F), 0.25F, 0.25F, 0.375F, 0.375F, 0F, 0.06F, 0.39F, 0.5F);
			
			//Left Bottom Side
			myRenderer.renderSquareXNeg(0.1, 0.1, 0.25, 0.8, 1, 0F, 0.25F, 0F, 0.25F);
			
			//Left Bottom Front
			myRenderer.renderSquareZPos(0.1, 0.2, 0.1, 0.25, 1, 0F, 0.125F, 0F, 0.25F);
			
			//Right Bottom Side
			myRenderer.renderSquareXPos(0.9, 0.1, 0.25, 0.8, 1, 0F, 0.25F, 0F, 0.25F);
			
			//Right Bottom Front
			myRenderer.renderSquareZPos(0.8, 0.9, 0.1, 0.25, 1, 0F, 0.125F, 0F, 0.25F);
		}
		else if(meta == 3)
		{
			//Middle Top
			myRenderer.renderSquareYPos(0, 0.2, 0.5, 0.3, 0.7, 0F, 0.25F, 0F, 0.375F);
			
			//Middle Front
			myRenderer.renderQuadXNeg(new Vertex(0F, 0.4F, 0.7F), new Vertex(0F, 0.4F, 0.3F), 
					new Vertex(0F, 0.5F, 0.65F), new Vertex(0F, 0.5F, 0.35F), 0F, 0.0625F, 0.4375F, 0.5F, 0.125F, 0.125F, 0.25F, 0.25F);
			
			//Left Top
			myRenderer.renderQuadYPos(new Vertex(0.2F, 0.25F, 0.3F), new Vertex(0F, 0.25F, 0.3F), 
					new Vertex(0.2F, 0.5F, 0.1F), new Vertex(0F, 0.5F, 0.1F), 0F, 0.25F, 0F, 0.5F);
			
			//Left Top Front
			myRenderer.renderQuadXNeg(new Vertex(0F, 0.25F, 0.7F), new Vertex(0F, 0.25F, 0.65F), 
					new Vertex(0F, 0.4F, 0.9F), new Vertex(0F, 0.5F, 0.8F), 0.25F, 0.25F, 0.375F, 0.375F, 0F, 0.06F, 0.39F, 0.5F);
			
			//Right Top
			myRenderer.renderQuadYPos(new Vertex(0F, 0.25F, 0.7F), new Vertex(0.2F, 0.25F, 0.7F), 
					new Vertex(0F, 0.5F, 0.9F), new Vertex(0.2F, 0.5F, 0.9F), 0F, 0.25F, 0F, 0.5F);
			
			//Right Top Front
			myRenderer.renderQuadXNeg(new Vertex(0F, 0.25F, 0.35F), new Vertex(0F, 0.25F, 0.3F), 
					new Vertex(0F, 0.5F, 0.2F), new Vertex(0F, 0.4F, 0.1F), 0.25F, 0.25F, 0.375F, 0.375F, 0.06F, 0F, 0.5F, 0.39F);
			
			//Left Bottom Side
			myRenderer.renderSquareZNeg(0, 0.2, 0.1, 0.25, 0.1, 0F, 0.25F, 0F, 0.25F);
			
			//Left Bottom Front
			myRenderer.renderSquareXNeg(0, 0.1, 0.25, 0.1, 0.2, 0F, 0.125F, 0F, 0.25F);
			
			//Right Bottom Side
			myRenderer.renderSquareZPos(0, 0.2, 0.1, 0.25, 0.9, 0F, 0.25F, 0F, 0.25F);
			
			//Right Bottom Front
			myRenderer.renderSquareXNeg(0, 0.1, 0.25, 0.8, 0.9, 0F, 0.125F, 0F, 0.25F);
		}
	}
	
	private void renderChimney(CWTFCRenderer myRenderer, int meta)
	{
		if(meta == 0)
		{
			//Front
			myRenderer.renderSquareZNeg(0.3, 0.7, 0.6, 0.9, 0.25, 0, 0.4375F, 0F, 0.4375F);
			
			//Back
			myRenderer.renderSquareZPos(0.3, 0.7, 0.6, 0.9, 0.65, 0, 0.4375F, 0F, 0.4375F);
			
			//Left
			myRenderer.renderSquareXPos(0.7, 0.6, 0.9, 0.25, 0.65, 0, 0.4375F, 0F, 0.4375F);
			
			//Right
			myRenderer.renderSquareXNeg(0.3, 0.6, 0.9, 0.25, 0.65, 0, 0.4375F, 0F, 0.4375F);
			
			//Top
			myRenderer.renderSquareYPos(0.3, 0.7, 0.9, 0.25, 0.65, 0.5625F, 1, 0.5625F, 1);
		}
		else if(meta == 1)
		{
			//Front
			myRenderer.renderSquareXPos(0.75, 0.6, 0.9, 0.3, 0.7, 0, 0.4375F, 0F, 0.4375F);
			
			//Back
			myRenderer.renderSquareXNeg(0.35, 0.6, 0.9, 0.3, 0.7, 0, 0.4375F, 0F, 0.4375F);
			
			//Left
			myRenderer.renderSquareZPos(0.35, 0.75, 0.6, 0.9, 0.7, 0, 0.4375F, 0F, 0.4375F);
			
			//Right
			myRenderer.renderSquareZNeg(0.35, 0.75, 0.6, 0.9, 0.3, 0, 0.4375F, 0F, 0.4375F);
			
			//Top
			myRenderer.renderSquareYPos(0.35, 0.75, 0.9, 0.3, 0.7, 0.5625F, 1, 0.5625F, 1);
		}
		else if(meta == 2)
		{
			//Front
			myRenderer.renderSquareZPos(0.3, 0.7, 0.6, 0.9, 0.75, 0, 0.4375F, 0F, 0.4375F);
			
			//Back
			myRenderer.renderSquareZNeg(0.3, 0.7, 0.6, 0.9, 0.35, 0, 0.4375F, 0F, 0.4375F);
			
			//Left
			myRenderer.renderSquareXNeg(0.3, 0.6, 0.9, 0.35, 0.75, 0, 0.4375F, 0F, 0.4375F);
			
			//Right
			myRenderer.renderSquareXPos(0.7, 0.6, 0.9, 0.35, 0.75, 0, 0.4375F, 0F, 0.4375F);
			
			//Top
			myRenderer.renderSquareYPos(0.3, 0.7, 0.9, 0.35, 0.75, 0.5625F, 1, 0.5625F, 1);
		}
		else if(meta == 3)
		{
			//Front
			myRenderer.renderSquareXNeg(0.25, 0.6, 0.9, 0.3, 0.7, 0, 0.4375F, 0F, 0.4375F);
			
			//Back
			myRenderer.renderSquareXPos(0.65, 0.6, 0.9, 0.3, 0.7, 0, 0.4375F, 0F, 0.4375F);
			
			//Left
			myRenderer.renderSquareZNeg(0.25, 0.65, 0.6, 0.9, 0.3, 0, 0.4375F, 0F, 0.4375F);
			
			//Right
			myRenderer.renderSquareZPos(0.25, 0.65, 0.6, 0.9, 0.7, 0, 0.4375F, 0F, 0.4375F);
			
			//Top
			myRenderer.renderSquareYPos(0.25, 0.65, 0.9, 0.3, 0.7, 0.5625F, 1, 0.5625F, 1);
		}
	}
	
	private void renderInterior(CWTFCRenderer myRenderer, int meta, IIcon icon)
	{		
		if(meta == 0)
		{
			//Top
			myRenderer.renderSquareYNeg(0.3, 0.7, 0.5, 0.2, 0.9, 0F, 0.5F, 0F, 1);
			
			//Left Top
			myRenderer.renderQuadYNeg(new Vertex(0.9F, 0.25F, 0.2F), new Vertex(0.9F, 0.25F, 0.9F),
							new Vertex(0.7F, 0.5F, 0.2F), new Vertex(0.7F, 0.5F, 0.9F), 0F, 1, 0F, 0.5F);
			
			//Right Top
			myRenderer.renderQuadYNeg(new Vertex(0.1F, 0.25F, 0.9F), new Vertex(0.1F, 0.25F, 0.2F),
					new Vertex(0.3F, 0.5F, 0.9F), new Vertex(0.3F, 0.5F, 0.2F), 0F, 1, 0F, 0.5F);
			
			//Left Bottom
			myRenderer.renderQuadYNeg(new Vertex(0.9F, 0.1F, 0.2F), new Vertex(0.9F, 0.1F, 0.9F),
					new Vertex(0.9F, 0.25F, 0.2F), new Vertex(0.9F, 0.25F, 0.9F), 0F, 1, 0F, 0.25F);
			
			//Right Bottom
			myRenderer.renderQuadYNeg(new Vertex(0.1F, 0.1F, 0.9F), new Vertex(0.1F, 0.1F, 0.2F),
					new Vertex(0.1F, 0.25F, 0.9F), new Vertex(0.1F, 0.25F, 0.2F), 0F, 1, 0F, 0.25F);
			
			//Back Top
			myRenderer.renderQuadYNeg(new Vertex(0.1F, 0.25F, 0.9F), new Vertex(0.9F, 0.25F, 0.9F),
					new Vertex(0.25F, 0.5F, 0.9F), new Vertex(0.75F, 0.5F, 0.9F), 0.175F, 0F, 1F, 0.825F, 0F, 0F, 0.5F, 0.5F);
			
			//Back Bottom
			myRenderer.renderQuadYNeg(new Vertex(0.1F, 0.1F, 0.9F), new Vertex(0.9F, 0.1F, 0.9F),
					new Vertex(0.1F, 0.25F, 0.9F), new Vertex(0.9F, 0.25F, 0.9F), 0F, 1, 0.25F, 0.5F);
			
			//Floor
			myRenderer.renderQuadZNeg(new Vertex(0.1F, 0.101F, 0F), new Vertex(0.1F, 0.101F, 0.9F),
					new Vertex(0.9F, 0.101F, 0F), new Vertex(0.9F, 0.101F, 0.9F));
		}
		else if(meta == 1)
		{
			//Top
			myRenderer.renderSquareYNeg(0.1, 0.8, 0.5, 0.3, 0.7, 0F, 1, 0F, 0.5F);
			
			//Left Top
			myRenderer.renderQuadYNeg(new Vertex(0.8F, 0.25F, 0.3F), new Vertex(0.1F, 0.25F, 0.3F),
					new Vertex(0.8F, 0.5F, 0.1F), new Vertex(0.1F, 0.5F, 0.1F), 0F, 1, 0F, 0.5F);
			
			//Right Top
			myRenderer.renderQuadYNeg(new Vertex(0.1F, 0.25F, 0.7F), new Vertex(0.8F, 0.25F, 0.7F),
							new Vertex(0.1F, 0.5F, 0.9F), new Vertex(0.8F, 0.5F, 0.9F), 0F, 1, 0F, 0.5F);
			
			//Left Bottom
			myRenderer.renderQuadYNeg(new Vertex(0.8F, 0.1F, 0.1F), new Vertex(0.1F, 0.1F, 0.1F),
					new Vertex(0.8F, 0.25F, 0.1F), new Vertex(0.1F, 0.25F, 0.1F), 0F, 1, 0F, 0.25F);
			
			//Right Bottom
			myRenderer.renderQuadYNeg(new Vertex(0.1F, 0.1F, 0.9F), new Vertex(0.8F, 0.1F, 0.9F),
					new Vertex(0.1F, 0.25F, 0.9F), new Vertex(0.8F, 0.25F, 0.9F), 0F, 1, 0F, 0.25F);
			
			//Back Top
			myRenderer.renderQuadYNeg(new Vertex(0.1F, 0.25F, 0.75F), new Vertex(0.1F, 0.25F, 0.25F),
					new Vertex(0.1F, 0.5F, 0.9F), new Vertex(0.1F, 0.5F, 0.1F), 0.175F, 0F, 1F, 0.825F, 0F, 0F, 0.5F, 0.5F);
			
			//Back Bottom
			myRenderer.renderQuadYNeg(new Vertex(0.1F, 0.1F, 0.9F), new Vertex(0.1F, 0.1F, 0.1F),
					new Vertex(0.1F, 0.25F, 0.9F), new Vertex(0.1F, 0.25F, 0.1F), 0F, 1, 0.25F, 0.5F);
			
			//Floor
			myRenderer.renderQuadXPos(new Vertex(0.1F, 0.101F, 0.1F), new Vertex(1F, 0.101F, 0.1F),
					new Vertex(0.1F, 0.101F, 0.9F), new Vertex(1F, 0.101F, 0.9F));
		}
		else if(meta == 2)
		{
			//Top
			myRenderer.renderSquareYNeg(0.3, 0.7, 0.5, 0.1, 0.8, 0F, 0.5F, 0F, 1);
			
			//Left Top
			myRenderer.renderQuadYNeg(new Vertex(0.1F, 0.25F, 0.8F), new Vertex(0.1F, 0.25F, 0.1F),
					new Vertex(0.3F, 0.5F, 0.8F), new Vertex(0.3F, 0.5F, 0.1F), 0F, 1, 0F, 0.5F);
			
			//Right Top
			myRenderer.renderQuadYNeg(new Vertex(0.9F, 0.25F, 0.1F), new Vertex(0.9F, 0.25F, 0.8F),
					new Vertex(0.7F, 0.5F, 0.1F), new Vertex(0.7F, 0.5F, 0.8F), 0F, 1, 0F, 0.5F);
			
			//Left Bottom
			myRenderer.renderQuadYNeg(new Vertex(0.1F, 0.1F, 0.8F), new Vertex(0.1F, 0.1F, 0.1F),
					new Vertex(0.1F, 0.25F, 0.8F), new Vertex(0.1F, 0.25F, 0.1F), 0F, 1, 0F, 0.25F);
			
			//Right Bottom
			myRenderer.renderQuadYNeg(new Vertex(0.9F, 0.1F, 0.1F), new Vertex(0.9F, 0.1F, 0.8F),
					new Vertex(0.9F, 0.25F, 0.1F), new Vertex(0.9F, 0.25F, 0.8F), 0F, 1, 0F, 0.25F);
			
			//Back Top
			myRenderer.renderQuadYNeg(new Vertex(0.9F, 0.25F, 0.1F), new Vertex(0.1F, 0.25F, 0.1F),
					new Vertex(0.75F, 0.5F, 0.1F), new Vertex(0.25F, 0.5F, 0.1F), 0.175F, 0F, 1F, 0.825F, 0F, 0F, 0.5F, 0.5F);
			
			//Back Bottom
			myRenderer.renderQuadYNeg(new Vertex(0.9F, 0.1F, 0.1F), new Vertex(0.1F, 0.1F, 0.1F),
					new Vertex(0.9F, 0.25F, 0.1F), new Vertex(0.1F, 0.25F, 0.1F), 0F, 1, 0.25F, 0.5F);
			
			//Floor
			myRenderer.renderQuadZPos(new Vertex(0.9F, 0.101F, 0.1F), new Vertex(0.9F, 0.101F, 1F),
					new Vertex(0.1F, 0.101F, 0.1F), new Vertex(0.1F, 0.101F, 1F));
		}
		else if(meta == 3)
		{
			//Top
			myRenderer.renderSquareYNeg(0.2, 0.9, 0.5, 0.3, 0.7, 0F, 1, 0F, 0.5F);
			
			//Left Top
			myRenderer.renderQuadYNeg(new Vertex(0.9F, 0.25F, 0.3F), new Vertex(0.2F, 0.25F, 0.3F),
					new Vertex(0.9F, 0.5F, 0.1F), new Vertex(0.2F, 0.5F, 0.1F), 0F, 1, 0F, 0.5F);
			
			//Right Top
			myRenderer.renderQuadYNeg(new Vertex(0.2F, 0.25F, 0.7F), new Vertex(0.9F, 0.25F, 0.7F),
							new Vertex(0.2F, 0.5F, 0.9F), new Vertex(0.9F, 0.5F, 0.9F), 0F, 1, 0F, 0.5F);
			
			//Left Bottom
			myRenderer.renderQuadYNeg(new Vertex(0.9F, 0.1F, 0.1F), new Vertex(0.2F, 0.1F, 0.1F),
					new Vertex(0.9F, 0.25F, 0.1F), new Vertex(0.2F, 0.25F, 0.1F), 0F, 1, 0F, 0.25F);
			
			//Right Bottom
			myRenderer.renderQuadYNeg(new Vertex(0.2F, 0.1F, 0.9F), new Vertex(0.9F, 0.1F, 0.9F),
					new Vertex(0.2F, 0.25F, 0.9F), new Vertex(0.9F, 0.25F, 0.9F), 0F, 1, 0F, 0.25F);
			
			//Back Top
			myRenderer.renderQuadYNeg(new Vertex(0.9F, 0.25F, 0.25F), new Vertex(0.9F, 0.25F, 0.75F),
					new Vertex(0.9F, 0.5F, 0.1F), new Vertex(0.9F, 0.5F, 0.9F), 0.175F, 0F, 1F, 0.825F, 0F, 0F, 0.5F, 0.5F);
			
			//Back Bottom
			myRenderer.renderQuadYNeg(new Vertex(0.9F, 0.1F, 0.1F), new Vertex(0.9F, 0.1F, 0.9F),
					new Vertex(0.9F, 0.25F, 0.1F), new Vertex(0.9F, 0.25F, 0.9F), 0F, 1, 0.25F, 0.5F);
			
			//Floor
			myRenderer.renderQuadXPos(new Vertex(0F, 0.101F, 0.1F), new Vertex(0.9F, 0.101F, 0.1F),
					new Vertex(0F, 0.101F, 0.9F), new Vertex(0.9F, 0.101F, 0.9F));
		}
	}
	
	private void renderInnerOpening(CWTFCRenderer myRenderer, int meta, IIcon icon)
	{
		if(meta == 0)
		{
			//Top
			myRenderer.renderQuadYNeg(new Vertex(0.35F, 0.4F, 0.2F), new Vertex(0.35F, 0.4F, 0F),
						   new Vertex(0.65F, 0.4F, 0.2F), new Vertex(0.65F, 0.4F, 0F), 0F, 0.25F, 0F, 0.375F);
			
			//Top Left
			myRenderer.renderQuadYNeg(new Vertex(0.8F, 0.25F, 0F), new Vertex(0.8F, 0.25F, 0.2F),
					   new Vertex(0.65F, 0.4F, 0F), new Vertex(0.65F, 0.4F, 0.2F), 0F, 0.25F, 0F, 0.25F);
			
			//Top Right
			myRenderer.renderQuadYNeg(new Vertex(0.2F, 0.25F, 0.2F), new Vertex(0.2F, 0.25F, 0F),
					   new Vertex(0.35F, 0.4F, 0.2F), new Vertex(0.35F, 0.4F, 0F), 0F, 0.25F, 0F, 0.25F);
			
			//Bottom Left
			myRenderer.renderQuadYNeg(new Vertex(0.8F, 0.1F, 0F), new Vertex(0.8F, 0.1F, 0.2F),
					   new Vertex(0.8F, 0.25F, 0F), new Vertex(0.8F, 0.25F, 0.2F), 0F, 0.25F, 0F, 0.25F);
			
			//Bottom Right
			myRenderer.renderQuadYNeg(new Vertex(0.2F, 0.1F, 0.2F), new Vertex(0.2F, 0.1F, 0F),
					   new Vertex(0.2F, 0.25F, 0.2F), new Vertex(0.2F, 0.25F, 0F), 0F, 0.25F, 0F, 0.25F);
		}
		else if(meta == 1)
		{
			//Top
			myRenderer.renderQuadYNeg(new Vertex(0.8F, 0.4F, 0.35F), new Vertex(1F, 0.4F, 0.35F),
						   new Vertex(0.8F, 0.4F, 0.65F), new Vertex(1F, 0.4F, 0.65F), 0F, 0.25F, 0F, 0.375F);
			
			//Top Left
			myRenderer.renderQuadYNeg(new Vertex(0.8F, 0.25F, 0.65F), new Vertex(1F, 0.25F, 0.65F),
					   new Vertex(0.8F, 0.4F, 0.8F), new Vertex(1F, 0.4F, 0.8F), 0F, 0.25F, 0F, 0.25F);
			
			//Top Right
			myRenderer.renderQuadYNeg(new Vertex(1F, 0.25F, 0.35F), new Vertex(0.8F, 0.25F, 0.35F),
					   new Vertex(1F, 0.4F, 0.2F), new Vertex(0.8F, 0.4F, 0.2F), 0F, 0.25F, 0F, 0.25F);
			
			//Bottom Left
			myRenderer.renderQuadYNeg(new Vertex(0.8F, 0.1F, 0.8F), new Vertex(1F, 0.1F, 0.8F),
					   new Vertex(0.8F, 0.25F, 0.8F), new Vertex(1F, 0.25F, 0.8F), 0F, 0.25F, 0F, 0.25F);
			
			//Bottom Right
			myRenderer.renderQuadYNeg(new Vertex(1F, 0.1F, 0.2F), new Vertex(0.8F, 0.1F, 0.2F),
					   new Vertex(1F, 0.25F, 0.2F), new Vertex(0.8F, 0.25F, 0.2F), 0F, 0.25F, 0F, 0.25F);
		}
		else if(meta == 2)
		{
			//Top
			myRenderer.renderQuadYNeg(new Vertex(0.65F, 0.4F, 0.8F), new Vertex(0.65F, 0.4F, 1F),
						   new Vertex(0.35F, 0.4F, 0.8F), new Vertex(0.35F, 0.4F, 1F), 0F, 0.25F, 0F, 0.375F);
			
			//Top Left
			myRenderer.renderQuadYNeg(new Vertex(0.2F, 0.25F, 1F), new Vertex(0.2F, 0.25F, 0.8F),
					   new Vertex(0.35F, 0.4F, 1F), new Vertex(0.35F, 0.4F, 0.8F), 0F, 0.25F, 0F, 0.25F);
			
			//Top Right
			myRenderer.renderQuadYNeg(new Vertex(0.8F, 0.25F, 0.8F), new Vertex(0.8F, 0.25F, 1F),
					   new Vertex(0.65F, 0.4F, 0.8F), new Vertex(0.65F, 0.4F, 1F), 0F, 0.25F, 0F, 0.25F);
			
			//Bottom Left
			myRenderer.renderQuadYNeg(new Vertex(0.2F, 0.1F, 1F), new Vertex(0.2F, 0.1F, 0.8F),
					   new Vertex(0.2F, 0.25F, 1F), new Vertex(0.2F, 0.25F, 0.8F), 0F, 0.25F, 0F, 0.25F);
			
			//Bottom Right
			myRenderer.renderQuadYNeg(new Vertex(0.8F, 0.1F, 0.8F), new Vertex(0.8F, 0.1F, 1F),
					   new Vertex(0.8F, 0.25F, 0.8F), new Vertex(0.8F, 0.25F, 1F), 0F, 0.25F, 0F, 0.25F);
		}
		else if(meta == 3)
		{
			//Top
			myRenderer.renderQuadYNeg(new Vertex(0F, 0.4F, 0.35F), new Vertex(0.2F, 0.4F, 0.35F),
						   new Vertex(0F, 0.4F, 0.65F), new Vertex(0.2F, 0.4F, 0.65F), 0F, 0.25F, 0F, 0.375F);
			
			//Top Left
			myRenderer.renderQuadYNeg(new Vertex(0.2F, 0.25F, 0.35F), new Vertex(0F, 0.25F, 0.35F),
					   new Vertex(0.2F, 0.4F, 0.2F), new Vertex(0F, 0.4F, 0.2F), 0F, 0.25F, 0F, 0.25F);
			
			//Top Right
			myRenderer.renderQuadYNeg(new Vertex(0F, 0.25F, 0.65F), new Vertex(0.2F, 0.25F, 0.65F),
					   new Vertex(0F, 0.4F, 0.8F), new Vertex(0.2F, 0.4F, 0.8F), 0F, 0.25F, 0F, 0.25F);
			
			//Bottom Left
			myRenderer.renderQuadYNeg(new Vertex(0.2F, 0.1F, 0.2F), new Vertex(0F, 0.1F, 0.2F),
					   new Vertex(0.2F, 0.25F, 0.2F), new Vertex(0F, 0.25F, 0.2F), 0F, 0.25F, 0F, 0.25F);
			
			//Bottom Right
			myRenderer.renderQuadYNeg(new Vertex(0F, 0.1F, 0.8F), new Vertex(0.2F, 0.1F, 0.8F),
					   new Vertex(0F, 0.25F, 0.8F), new Vertex(0.2F, 0.25F, 0.8F), 0F, 0.25F, 0F, 0.25F);
		}
	}
	
	private void renderFire(TileClayOven te, BlockClayOven oven, CWTFCRenderer myRenderer, int meta)
	{
		float slope = 0.1F;
		
		for(int i = 0; i < 4; i++)
		{
			if(te.getStackInSlot(i) != null)
				slope += 0.05f;
		}
		
		if(te.getFireState())
			myRenderer.icon = oven.getOvenIcon("OvenFireOn");
		else
			myRenderer.icon = oven.getOvenIcon("OvenFireOff");
		
		if(meta == 0)
		{
			myRenderer.renderQuadZNeg(new Vertex(0.9F, slope, 0.5F), new Vertex(0.1F, slope, 0.5F),
						   new Vertex(0.9F, 0.1F, 0.9F), new Vertex(0.1F, 0.1F, 0.9F));
		}
		else if(meta == 1)
		{
			myRenderer.renderQuadXPos(new Vertex(0.5F, 0.1F, 0.1F), new Vertex(0.5F, 0.1F, 0.9F),
					   new Vertex(0.1F, slope, 0.1F), new Vertex(0.1F, slope, 0.9F));
		}
		else if(meta == 2)
		{
			myRenderer.renderQuadZPos(new Vertex(0.9F, 0.1F, 0.5F), new Vertex(0.1F, 0.1F, 0.5F),
					   new Vertex(0.9F, slope, 0.1F), new Vertex(0.1F, slope, 0.1F));
		}
		else if(meta == 3)
		{
			myRenderer.renderQuadXNeg(new Vertex(0.5F, 0.1F, 0.9F), new Vertex(0.5F, 0.1F, 0.1F),
					   new Vertex(0.9F, slope, 0.9F), new Vertex(0.9F, slope, 0.1F));
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
