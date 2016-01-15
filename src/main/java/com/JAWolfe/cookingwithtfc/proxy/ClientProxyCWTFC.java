package com.JAWolfe.cookingwithtfc.proxy;

import com.JAWolfe.cookingwithtfc.init.CWTFCBlocks;
import com.JAWolfe.cookingwithtfc.render.RenderMixingBowl;
import com.JAWolfe.cookingwithtfc.render.RenderPrepTable;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxyCWTFC extends CommonProxyCWTFC
{
	@Override
	public ClientProxyCWTFC getClientProxy() {
		return this;
	}

	@Override
	public void registerTileEntities(Boolean b) 
	{
		super.registerTileEntities(false);
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderInformation()
	{
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.mixingBowlRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderMixingBowl());
		RenderingRegistry.registerBlockHandler(CWTFCBlocks.prepTableRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderPrepTable());
	}
}
