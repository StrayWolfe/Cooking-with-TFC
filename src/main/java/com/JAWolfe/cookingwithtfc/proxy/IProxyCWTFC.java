package com.JAWolfe.cookingwithtfc.proxy;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

public interface IProxyCWTFC 
{
	public abstract ClientProxyCWTFC getClientProxy();
	
	public abstract EntityPlayer getPlayerEntity(MessageContext ctx);
	
	public abstract void registerTileEntities();
	
	public abstract void registerFluids();
	
	public abstract void setupFluids();
	
	public abstract void registerRenderInformation();
	
	public abstract void registerWAILA();
}
