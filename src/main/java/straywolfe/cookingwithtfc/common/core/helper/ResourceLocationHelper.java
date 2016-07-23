package straywolfe.cookingwithtfc.common.core.helper;

import net.minecraft.util.ResourceLocation;
import straywolfe.cookingwithtfc.common.lib.ModInfo;

public class ResourceLocationHelper 
{
	public static ResourceLocation getResourceLocation(String modId, String path)
    {
        return new ResourceLocation(modId, path);
    }

    public static ResourceLocation getResourceLocation(String path)
    {
        return getResourceLocation(ModInfo.ModID, path);
    }
}
