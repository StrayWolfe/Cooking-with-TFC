package straywolfe.cookingwithtfc.client.handlers;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Blocks.BlockFarmland;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Items.Tools.ItemCustomHoe;
import com.bioxx.tfc.TileEntities.TEFarmland;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.managers.CWTFCCropIndex;
import straywolfe.cookingwithtfc.api.managers.CropManager;
import straywolfe.cookingwithtfc.common.tileentity.TileCrop;

public class FarmlandHighlighter 
{
	@SubscribeEvent
	public void drawHighlighter(DrawBlockHighlightEvent event)
	{
		World world = event.player.worldObj;
		double var8 = event.player.lastTickPosX + (event.player.posX - event.player.lastTickPosX) * event.partialTicks;
		double var10 = event.player.lastTickPosY + (event.player.posY - event.player.lastTickPosY) * event.partialTicks;
		double var12 = event.player.lastTickPosZ + (event.player.posZ - event.player.lastTickPosZ) * event.partialTicks;
		
		boolean isMetalHoe = false;

		if(event.currentItem != null &&
				event.currentItem.getItem() != TFCItems.igInHoe &&
				event.currentItem.getItem() != TFCItems.igExHoe &&
				event.currentItem.getItem() != TFCItems.sedHoe &&
				event.currentItem.getItem() != TFCItems.mMHoe)
		{
			isMetalHoe = true;
		}
		
		PlayerManagerTFC manager = PlayerManagerTFC.getInstance();
		PlayerInfo playerInfo = manager != null ? manager.getClientPlayer() : null;
		int hoeMode = playerInfo != null ? playerInfo.hoeMode : -1;
		
		if (event.currentItem != null && event.currentItem.getItem() instanceof ItemCustomHoe && isMetalHoe && hoeMode == 1)
		{
			if (TFC_Core.getSkillStats(event.player) != null)
			{
				SkillRank sr = TFC_Core.getSkillStats(event.player).getSkillRank(Global.SKILL_AGRICULTURE);
				if (sr != SkillRank.Expert && sr != SkillRank.Master)
					return;
			}
			
			Block b = world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
			int crop = 0;
			if(b == CWTFCBlocks.customCrop && (
					world.getBlock(event.target.blockX, event.target.blockY - 1, event.target.blockZ) == TFCBlocks.tilledSoil ||
					world.getBlock(event.target.blockX, event.target.blockY - 1, event.target.blockZ) == TFCBlocks.tilledSoil2))
			{
				b = TFCBlocks.tilledSoil;
				crop = 1;
			}
			
			if(b == TFCBlocks.tilledSoil || b == TFCBlocks.tilledSoil2)
			{
				TEFarmland te = (TEFarmland) world.getTileEntity(event.target.blockX, event.target.blockY - crop, event.target.blockZ);
				te.requestNutrientData();
				
				float timeMultiplier = TFC_Time.daysInYear / 360f;
				int soilMax = (int) (25000 * timeMultiplier);
				
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDepthMask(false);
				
				double offset = 0;
				double fertilizer = 1.02 + ((double)te.nutrients[3] / (double)soilMax)*0.5;
				GL11.glColor4ub(TFCOptions.cropFertilizerColor[0], TFCOptions.cropFertilizerColor[1], TFCOptions.cropFertilizerColor[2], TFCOptions.cropFertilizerColor[3]);
				drawBox(AxisAlignedBB.getBoundingBox(
						event.target.blockX + offset,
						event.target.blockY + 1.01 - crop,
						event.target.blockZ,
						event.target.blockX + 1,
						event.target.blockY + fertilizer - crop,
						event.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));
				
				double nutrient = 1.02 + ((double)te.nutrients[0] / (double)soilMax) * 0.5;
				GL11.glColor4ub(TFCOptions.cropNutrientAColor[0], TFCOptions.cropNutrientAColor[1], TFCOptions.cropNutrientAColor[2], TFCOptions.cropNutrientAColor[3]);
				drawBox(AxisAlignedBB.getBoundingBox(
						event.target.blockX + offset,
						event.target.blockY + 1.01 - crop + fertilizer - 1.02,
						event.target.blockZ,
						event.target.blockX + offset + 0.3333,
						event.target.blockY + nutrient - crop + fertilizer - 1.02,
						event.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				offset = 0.3333;
				nutrient = 1.02 + ((double)te.nutrients[1] / (double)soilMax) * 0.5;
				GL11.glColor4ub(TFCOptions.cropNutrientBColor[0], TFCOptions.cropNutrientBColor[1], TFCOptions.cropNutrientBColor[2], TFCOptions.cropNutrientBColor[3]);
				drawBox(AxisAlignedBB.getBoundingBox(
						event.target.blockX + offset,
						event.target.blockY + 1.01 - crop + fertilizer - 1.02,
						event.target.blockZ,
						event.target.blockX + offset + 0.3333,
						event.target.blockY + nutrient - crop + fertilizer - 1.02,
						event.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				offset = 0.6666;
				nutrient = 1.02 + ((double)te.nutrients[2] / (double)soilMax) * 0.5;
				GL11.glColor4ub(TFCOptions.cropNutrientCColor[0], TFCOptions.cropNutrientCColor[1], TFCOptions.cropNutrientCColor[2], TFCOptions.cropNutrientCColor[3]);
				drawBox(AxisAlignedBB.getBoundingBox(
						event.target.blockX + offset,
						event.target.blockY + 1.01 - crop + fertilizer - 1.02,
						event.target.blockZ,
						event.target.blockX + offset + 0.3333,
						event.target.blockY + nutrient - crop + fertilizer - 1.02,
						event.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				GL11.glEnable(GL11.GL_CULL_FACE);
				
				GL11.glColor4f(0.1F, 0.1F, 0.1F, 1.0F);
				GL11.glLineWidth(3.0F);
				GL11.glDepthMask(false);

				offset = 0;

				drawOutlinedBoundingBox(AxisAlignedBB.getBoundingBox(
						event.target.blockX + offset,
						event.target.blockY + 1.01 - crop,
						event.target.blockZ,
						event.target.blockX + 1,
						event.target.blockY + fertilizer - crop,
						event.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				nutrient = 1.02 + ((double)te.nutrients[0] / (double)soilMax) * 0.5;
				drawOutlinedBoundingBox(AxisAlignedBB.getBoundingBox(
						event.target.blockX + offset,
						event.target.blockY + 1.01 - crop + fertilizer - 1.02,
						event.target.blockZ,
						event.target.blockX + offset + 0.3333,
						event.target.blockY + nutrient - crop + fertilizer - 1.02,
						event.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				offset = 0.3333;
				nutrient = 1.02 + ((double)te.nutrients[1] / (double)soilMax) * 0.5;
				drawOutlinedBoundingBox(AxisAlignedBB.getBoundingBox(
						event.target.blockX + offset,
						event.target.blockY + 1.01 - crop + fertilizer - 1.02,
						event.target.blockZ,
						event.target.blockX + offset + 0.3333,
						event.target.blockY + nutrient - crop + fertilizer - 1.02,
						event.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				offset = 0.6666;
				nutrient = 1.02 + ((double)te.nutrients[2] / (double)soilMax) * 0.5;
				drawOutlinedBoundingBox(AxisAlignedBB.getBoundingBox(
						event.target.blockX + offset,
						event.target.blockY + 1.01 - crop + fertilizer - 1.02,
						event.target.blockZ,
						event.target.blockX + offset + 0.3333,
						event.target.blockY + nutrient - crop + fertilizer - 1.02,
						event.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));
			}
		}
		else if(event.currentItem != null && event.currentItem.getItem() instanceof ItemCustomHoe && hoeMode == 2)
		{
			Block b = world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
			int crop = 0;
			if(b == CWTFCBlocks.customCrop && (
					world.getBlock(event.target.blockX, event.target.blockY-1, event.target.blockZ) == TFCBlocks.tilledSoil ||
					world.getBlock(event.target.blockX, event.target.blockY-1, event.target.blockZ) == TFCBlocks.tilledSoil2))
			{
				b = TFCBlocks.tilledSoil;
				crop = 1;
			}
			
			if(b == TFCBlocks.tilledSoil || b == TFCBlocks.tilledSoil2)
			{
				boolean water = BlockFarmland.isFreshWaterNearby(world, event.target.blockX, event.target.blockY-crop, event.target.blockZ);

				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				if(water)
					GL11.glColor4ub((byte)14, (byte)23, (byte)212, (byte)200);
				else
					GL11.glColor4ub((byte)0, (byte)0, (byte)0, (byte)200);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDepthMask(false);

				drawFace(AxisAlignedBB.getBoundingBox(
						event.target.blockX,
						event.target.blockY + 1.01 - crop,
						event.target.blockZ,
						event.target.blockX+1,
						event.target.blockY + 1.02 - crop,
						event.target.blockZ+1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				GL11.glEnable(GL11.GL_CULL_FACE);
			}
		}
		else if (event.currentItem != null && event.currentItem.getItem() instanceof ItemCustomHoe && hoeMode == 3)
		{
			Block b = world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
			if(b == CWTFCBlocks.customCrop && (
					world.getBlock(event.target.blockX, event.target.blockY-1, event.target.blockZ) == TFCBlocks.tilledSoil ||
					world.getBlock(event.target.blockX, event.target.blockY-1, event.target.blockZ) == TFCBlocks.tilledSoil2))
			{
				TileCrop te = (TileCrop)world.getTileEntity(event.target.blockX, event.target.blockY, event.target.blockZ);
				CWTFCCropIndex crop = CropManager.getInstance().getCropFromId(te.getCropID());
				boolean fullyGrown = te.growth >= crop.numGrowthStages - 1;
				
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				if(fullyGrown)
					GL11.glColor4ub((byte)64, (byte)200, (byte)37, (byte)200);
				else
					GL11.glColor4ub((byte)200, (byte)37, (byte)37, (byte)200);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDepthMask(false);

				drawFace(AxisAlignedBB.getBoundingBox(
						event.target.blockX,
						event.target.blockY + 0.01,
						event.target.blockZ,
						event.target.blockX+1,
						event.target.blockY + 0.02,
						event.target.blockZ+1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				GL11.glEnable(GL11.GL_CULL_FACE);
			}
		}
	}
	
	public void drawFace(AxisAlignedBB par1AxisAlignedBB)
	{
		Tessellator var2 = Tessellator.instance;

		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.draw();
	}

	public void drawBox(AxisAlignedBB par1AxisAlignedBB)
	{
		Tessellator var2 = Tessellator.instance;

		//Top
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.draw();

		//-x
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.draw();

		//+x
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();

		//-z
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();

		//+z
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.draw();
	}

	public void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB)
	{
		Tessellator var2 = Tessellator.instance;
		var2.startDrawing(3);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();
		var2.startDrawing(3);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.draw();
		var2.startDrawing(GL11.GL_LINES);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.draw();
	}
}
