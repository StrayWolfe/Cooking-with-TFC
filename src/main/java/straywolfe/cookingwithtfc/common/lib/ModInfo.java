package straywolfe.cookingwithtfc.common.lib;

public class ModInfo 
{
	public static final String ModID = "cookingwithtfc";
	public static final String ModName = "CookingWithTFC";

	public static final String ModVersion = "@MOD_VERSION@";
	public static final String ModChannel = "CookingWithTFC";
	public static final String SERVER_PROXY_CLASS = "straywolfe.cookingwithtfc.common.CommonProxyCWTFC";
	public static final String CLIENT_PROXY_CLASS = "straywolfe.cookingwithtfc.client.ClientProxyCWTFC";

	public static final String MODID_TFC = "terrafirmacraft";
	public static final String MODNAME_TFC = "TerraFirmaCraft";
	
	public static final String ModDependencies = "required-after:terrafirmacraft" +
												 ";required-after:tfcm";
}
