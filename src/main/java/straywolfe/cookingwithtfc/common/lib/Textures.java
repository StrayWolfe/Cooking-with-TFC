package straywolfe.cookingwithtfc.common.lib;

import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import straywolfe.cookingwithtfc.common.core.helper.ResourceLocationHelper;

public final class Textures 
{
	public static IIcon White_BG;
	
	public static final String RESOURCE_PREFIX = ModInfo.ModID + ":";
			
	public static final class Gui
	{
		private static final String GUI_TEXTURE_LOCATION = "textures/gui/";
		
		public static final ResourceLocation CLAYCOOKINGPOT = ResourceLocationHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "gui_claycookingpot.png");
		public static final ResourceLocation CLAYOVEN = ResourceLocationHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "gui_clayoven.png");
	}
}
