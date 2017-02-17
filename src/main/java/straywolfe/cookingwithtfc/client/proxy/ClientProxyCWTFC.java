package straywolfe.cookingwithtfc.client.proxy;

import straywolfe.cookingwithtfc.common.proxy.CommonProxyCWTFC;
import straywolfe.cookingwithtfc.common.tileentity.*;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
	}
	
	@Override
	public void registerHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new FarmlandHighlighter());
	}
}
