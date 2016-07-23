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
	}
	
	public static final class Block
	{
		private static final String BLOCK_TEXTURE_LOCATION = "textures/blocks/";
		
		public static final ResourceLocation BARLEYTOASTTOP = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Barley Toast Top.png");
		public static final ResourceLocation BARLEYTOASTSIDE = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Barley Toast Side.png");
		public static final ResourceLocation CORNTOASTTOP = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Corn Toast Top.png");
		public static final ResourceLocation CORNTOASTSIDE = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Corn Toast Side.png");
		public static final ResourceLocation OATTOASTTOP = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Oat Toast Top.png");
		public static final ResourceLocation OATTOASTSIDE = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Oat Toast Side.png");
		public static final ResourceLocation RICETOASTTOP = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Rice Toast Top.png");
		public static final ResourceLocation RICETOASTSIDE = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Rice Toast Side.png");
		public static final ResourceLocation RYETOASTTOP = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Rye Toast Top.png");
		public static final ResourceLocation RYETOASTSIDE = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Rye Toast Side.png");
		public static final ResourceLocation WHEATTOASTTOP = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Wheat Toast Top.png");
		public static final ResourceLocation WHEATTOASTSIDE = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Wheat Toast Side.png");
		
		public static final ResourceLocation DAIRY = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Dairy.png");
		public static final ResourceLocation FRUIT = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Fruit.png");
		public static final ResourceLocation PROTEIN = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Protein.png");
		public static final ResourceLocation VEGETABLE = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "Vegetable.png");
		
		public static final ResourceLocation VEGGYSALAD = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "VeggySalad.png");
		public static final ResourceLocation FRUITSALAD = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "FruitSalad.png");
		public static final ResourceLocation POTATOSALAD = ResourceLocationHelper.getResourceLocation(BLOCK_TEXTURE_LOCATION + "PotatoSalad.png");
		
	}
}
