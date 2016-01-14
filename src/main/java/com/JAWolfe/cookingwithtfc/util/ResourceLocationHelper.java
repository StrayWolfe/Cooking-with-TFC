package com.JAWolfe.cookingwithtfc.util;

import com.JAWolfe.cookingwithtfc.references.DetailsCWTFC;

import net.minecraft.util.ResourceLocation;

public class ResourceLocationHelper 
{
	public static ResourceLocation getResourceLocation(String modId, String path)
    {
        return new ResourceLocation(modId, path);
    }

    public static ResourceLocation getResourceLocation(String path)
    {
        return getResourceLocation(DetailsCWTFC.ModID, path);
    }
}
