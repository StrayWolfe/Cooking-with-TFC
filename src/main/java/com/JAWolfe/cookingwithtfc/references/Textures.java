package com.JAWolfe.cookingwithtfc.references;

import com.JAWolfe.cookingwithtfc.util.ResourceLocationHelper;

import net.minecraft.util.ResourceLocation;

public final class Textures 
{
	public static final String RESOURCE_PREFIX = DetailsCWTFC.ModID + ":";
			
	public static final class Gui
	{
		private static final String GUI_TEXTURE_LOCATION = "textures/gui/";
		
		public static final ResourceLocation PREPTABLE1 = ResourceLocationHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "gui_prepTable_1.png");
		public static final ResourceLocation PREPTABLE2 = ResourceLocationHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "gui_prepTable_2.png");
		public static final ResourceLocation PREPTABLE3 = ResourceLocationHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "gui_prepTable_3.png");
		public static final ResourceLocation PREPTABLE4 = ResourceLocationHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "gui_prepTable_4.png");
		public static final ResourceLocation PREPTABLE5 = ResourceLocationHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "gui_prepTable_5.png");
		public static final ResourceLocation PREPTABLE6 = ResourceLocationHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "gui_prepTable_6.png");
		public static final ResourceLocation PREPTABLE7 = ResourceLocationHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "gui_prepTable_7.png");
		public static final ResourceLocation PREPTABLE8 = ResourceLocationHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "gui_prepTable_8.png");
		public static final ResourceLocation PREPTABLE9 = ResourceLocationHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "gui_prepTable_9.png");
		
		public static final ResourceLocation CLAYCOOKINGPOT = ResourceLocationHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "gui_claycookingpot.png");
	}
}
