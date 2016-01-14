package com.JAWolfe.cookingwithtfc.items;

import java.util.List;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Render.Item.FoodItemRenderer;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.MinecraftForgeClient;

public class ItemTFCSandwichTransform extends ItemTFCMealTransform
{

	public ItemTFCSandwichTransform(int sw, int so, int sa, int bi, int um, float size, float maxWt, SkillRank skillRank, String RefName) 
	{
		super(sw, so, sa, bi, um, size, maxWt, skillRank, RefName);
		this.hasSubtypes = true;
		this.metaNames = new String[]{"Barley","Corn","Oat","Rice","Rye","Wheat"};
		this.metaIcons = new IIcon[6];
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < metaNames.length; i++)
		{
			list.add(createTag(new ItemStack(this, 1, i), this.getFoodMaxWeight(new ItemStack(this, 1)), 0, new ItemStack[]{}, new float[]{}));
		}
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{		
		metaIcons = new IIcon[metaNames.length];
		for(int i = 0; i < metaNames.length; i++)
		{
			metaIcons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + "Sandwich " + metaNames[i]);
		}
		
		this.itemIcon = metaIcons[0];
		MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
    {
        return getUnlocalizedName();
    }
	
	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		String s = "";
		switch(is.getItemDamage())
		{
			case 0: s += TFC_Core.translate("word.Barley") + " "; break;
			case 1: s += TFC_Core.translate("word.Corn") + " "; break;
			case 2: s += TFC_Core.translate("word.Oat") + " "; break;
			case 3: s += TFC_Core.translate("word.Rice") + " "; break;
			case 4: s += TFC_Core.translate("word.Rye") + " "; break;
			case 5: s += TFC_Core.translate("word.Wheat") + " "; break;
			default: break;
		}		
		s += TFC_Core.translate(this.getUnlocalizedNameInefficiently(is) + ".name");
		return s.trim();
	}
}
