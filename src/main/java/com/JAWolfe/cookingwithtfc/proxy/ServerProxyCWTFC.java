package com.JAWolfe.cookingwithtfc.proxy;

public class ServerProxyCWTFC extends CommonProxyCWTFC
{	
	@Override
	public ClientProxyCWTFC getClientProxy() {
		return null;
	}

	@Override
	public void registerTileEntities(Boolean b) {
		super.registerTileEntities(false);		
	}
}