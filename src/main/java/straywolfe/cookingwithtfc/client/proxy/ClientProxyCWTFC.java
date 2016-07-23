package straywolfe.cookingwithtfc.client.proxy;

import straywolfe.cookingwithtfc.common.proxy.CommonProxyCWTFC;
import straywolfe.cookingwithtfc.common.tileentity.*;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.client.render.*;

public class ClientProxyCWTFC extends CommonProxyCWTFC
{
	@Override
	public void registerTileEntities(boolean value) 
	{
		super.registerTileEntities(false);
		ClientRegistry.registerTileEntity(TileMeat.class, "TEMeat", new TESRMeat());
		ClientRegistry.registerTileEntity(TileSandwich.class, "TESandwich", new TESRSandwich());
		ClientRegistry.registerTileEntity(TileBowl.class, "TEBowl", new TESRBowl());
		ClientRegistry.registerTileEntity(TileCookingPot.class, "TECookingPot", new TESRCookingPot());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderInformation()
	{
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.mixingBowlRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderMixingBowl());
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.prepTableRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderPrepTable());
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.cookingPotRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderCookingPot());
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.bowlRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderBowl());
	}
}
