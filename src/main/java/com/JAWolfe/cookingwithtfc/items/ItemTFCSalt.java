package com.JAWolfe.cookingwithtfc.items;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Render.Item.FoodItemRenderer;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraftforge.client.MinecraftForgeClient;

public class ItemTFCSalt extends ItemTFCFoodTransform
{
	public ItemTFCSalt(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, float size, float decayrate, boolean edible, boolean usable)
	{
		super(fg, sw, so, sa, bi, um, size, decayrate, edible, usable);
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{		
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + getUnlocalizedName().replace("item.", ""));
		MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
	}
}
