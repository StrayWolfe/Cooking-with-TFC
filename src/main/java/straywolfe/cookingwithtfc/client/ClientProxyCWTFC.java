package straywolfe.cookingwithtfc.client;

import straywolfe.cookingwithtfc.common.CommonProxyCWTFC;
import straywolfe.cookingwithtfc.common.tileentity.*;

import com.bioxx.tfc.Core.ColorizerFoliageTFC;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Time;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.client.handlers.FarmlandHighlighter;
import straywolfe.cookingwithtfc.client.render.*;

public class ClientProxyCWTFC extends CommonProxyCWTFC
{
	@Override
	public void registerTileEntities(boolean value) 
	{
		super.registerTileEntities(false);
		ClientRegistry.registerTileEntity(TileClayOven.class, "TileClayOven", new TESRClayOven());
		ClientRegistry.registerTileEntity(TileGourd.class, "TilePumpkin", new TESRGourd());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderInformation()
	{
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.mixingBowlRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderMixingBowl());
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.prepTableRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderPrepTable());
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.cookingPotRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderCookingPot());
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.meatRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderMeat());
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.bowlRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderBowl());
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.clayOvenRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderClayOven());
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.sandwichRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderSandwich());
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.tableStorageRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderTableStorage());
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.customCropRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderCrop());
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.fruitTreeRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderFruitTree());
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.nutLeavesRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderFruitTree());
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.lumberConstructRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderLumberConstruct());
	}
	
	@Override
	public int foliageColorMultiplier(IBlockAccess par1IBlockAccess, int i, int j, int k)
	{
		int[] rgb = {0, 0, 0};
		float temperature = TFC_Climate.getHeightAdjustedTempSpecificDay(getCurrentWorld(),TFC_Time.getDayOfYear(),i, j, k);
		
		if(par1IBlockAccess.getBlock(i, j, k) == CWTFCBlocks.nutTreeLeaves)
		{			
			int meta = par1IBlockAccess.getBlockMetadata(i, j, k);
			for (int var8 = -1; var8 <= 1; ++var8)
			{
				for (int var9 = -1; var9 <= 1; ++var9)
				{
					if(meta == 1 || meta == 9 || meta == 4 || meta == 12)
						rgb = applyColor(TFC_Climate.getFoliageColorEvergreen(getCurrentWorld(), i + var8, j, k + var9), rgb);
					else
						rgb = applyColor(TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j, k + var9), rgb);
				}
			}
			return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
		}
		else if (temperature <= 10 && TFC_Time.getSeasonAdjustedMonth(k) >= 6 && TFC_Time.getSeasonAdjustedMonth(k) < 9)
		{
			for (int var8 = -1; var8 <= 1; ++var8)
			{
				for (int var9 = -1; var9 <= 1; ++var9)
				{
					int var10 = ColorizerFoliageTFC.getFoliageOrange();
					rgb = applyColor(var10, rgb);
				}
			}
			return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
		}
		else if (temperature <= 8 && TFC_Time.getSeasonAdjustedMonth(k) >= 6)
		{
			for (int var8 = -1; var8 <= 1; ++var8)
			{
				for (int var9 = -1; var9 <= 1; ++var9)
				{
					int var10 = ColorizerFoliageTFC.getFoliageDead();
					rgb = applyColor(var10, rgb);
				}
			}
			return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
		}
		else
		{
			for (int var8 = -1; var8 <= 1; ++var8)
			{
				for (int var9 = -1; var9 <= 1; ++var9)
				{
					int var10 = TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j, k + var9);
					rgb = applyColor(var10, rgb);
				}
			}
			return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
		}
	}
	
	private int[] applyColor(int c, int[] rgb)
	{
		rgb[0] += (c & 16711680) >> 16;
		rgb[1] += (c & 65280) >> 8;
		rgb[2] += c & 255;
		return rgb;
	}
	
	@Override
	public void registerClientHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new FarmlandHighlighter());
	}
	
	@Override
	public World getCurrentWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}
}
