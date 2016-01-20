package com.JAWolfe.cookingwithtfc.items.Items;

import java.util.List;

import com.JAWolfe.cookingwithtfc.core.CWTFC_Core;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodMeat;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemTFCMeatTransform extends ItemFoodMeat
{
	private float consumeSize = 5F;
	private float maxFoodWt;
		
	public ItemTFCMeatTransform(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, boolean edible, boolean usable, float size, float maxWt) 
	{
		super(fg, sw, so, sa, bi, um, edible, usable);
		
		consumeSize = size;
		maxFoodWt = maxWt;
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
	public float getFoodMaxWeight(ItemStack is) 
	{
		return maxFoodWt;
	}
	
	public float getMaxFoodWt()
	{
		return maxFoodWt;
	}
}