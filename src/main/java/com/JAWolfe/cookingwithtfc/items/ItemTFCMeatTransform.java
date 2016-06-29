package com.JAWolfe.cookingwithtfc.items;

import java.util.List;

import com.JAWolfe.cookingwithtfc.core.CWTFC_Core;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Render.Item.FoodItemRenderer;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ItemTFCMeatTransform extends ItemTFCFoodTransform
{
	private float consumeSize = 5F;
		
	public ItemTFCMeatTransform(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, boolean edible, boolean usable, float size) 
	{
		super(fg, sw, so, sa, bi, um, size, 2.0F, edible, usable);
		
		consumeSize = size;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		String name = this.getUnlocalizedName().replace(" Raw", "");

		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + name.replace("item.", ""));
		
		if(hasCookedIcon)
		{			
			this.cookedIcon = registerer.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + name.replace("item.", ""));
		}
		
		MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		return CWTFC_Core.processRightClick(is, player, consumeSize, this.isEdible(is));
	}
	
	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		return CWTFC_Core.processEating(is, world, player, consumeSize, false);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{		
		ItemTerra.addSizeInformation(is, arraylist);
		arraylist.add(ItemFoodTFC.getFoodGroupName(this.getFoodGroup()));
		

		if (is.hasTagCompound())
		{
			ItemFoodTFC.addFoodHeatInformation(is, arraylist);
			addFoodInformation(is, player, arraylist);
			
			if(this.isEdible(is))
				CWTFC_Core.getFoodUse(is, player, arraylist);
			else
				arraylist.add(EnumChatFormatting.DARK_GRAY + "Not currently edible");
		}
		else
		{
			arraylist.add(TFC_Core.translate("gui.badnbt"));
			TerraFirmaCraft.LOG.error(TFC_Core.translate("error.error") + " " + is.getUnlocalizedName() + " " +
					TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact"));
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		StringBuilder name = new StringBuilder();
		if (is.hasTagCompound()) // Circular reference avoidance
		{
			if(Food.isPickled(is))
				name.append(TFC_Core.translate("word.pickled")).append(' ');
			else if(Food.isBrined(is) && !Food.isDried(is))
				name.append(TFC_Core.translate("word.brined")).append(' ');

			if(Food.isSalted(is))
				name.append(TFC_Core.translate("word.salted")).append(' ');
			
			if(!Food.isCooked(is) && Food.isSmoked(is))
				name.append(TFC_Core.translate("word.smoked")).append(' ');

			if(Food.isDried(is) && !Food.isCooked(is))
				name.append(TFC_Core.translate("word.dried")).append(' ');
			
			if(Food.isInfused(is))
				name.append(TFC_Core.translate(Food.getInfusion(is) + ".name")).append(' ');
		}

		return name.append(TFC_Core.translate(this.getUnlocalizedName(is) + ".name")).append(getCookedLevelString(is)).toString();
	}
	
	@Override
	protected String getCookedLevelString(ItemStack is)
	{
		String s = "";
		if(Food.isCooked(is))
		{
			s+= " (";
			int cookedLevel = ((int)Food.getCooked(is)-600)/120;
			switch(cookedLevel)
			{
			case 0: s+=TFC_Core.translate("food.cooked.rare");break;
			case 1: s+=TFC_Core.translate("food.cooked.medrare");break;
			case 2: s+=TFC_Core.translate("food.cooked.med");break;
			case 3: s+=TFC_Core.translate("food.cooked.medwell");break;
			default: s+=TFC_Core.translate("food.cooked.well");break;
			}
			s+= ")";
		}
		return s;
	}
}