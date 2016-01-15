package com.JAWolfe.cookingwithtfc.items;

import java.util.List;

import com.JAWolfe.cookingwithtfc.core.CWTFC_Core;
import com.JAWolfe.cookingwithtfc.init.CWTFCBlocks;
import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.JAWolfe.cookingwithtfc.tileentities.TEGrains;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemTFCFoodTransform extends ItemFoodTFC implements IFood
{
	private float consumeSize;
	private float maxFoodWt;
	
	public ItemTFCFoodTransform(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, float size, float maxWt) 
	{
		super(fg, sw, so, sa, bi, um);

		consumeSize = size;
		maxFoodWt = maxWt;
	}
	
	public ItemTFCFoodTransform(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, float size, float maxWt, float decayrate) 
	{
		this(fg, sw, so, sa, bi, um, size, maxWt);
		this.setDecayRate(decayrate);
	}
	
	public ItemTFCFoodTransform(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, float size, float maxWt, float decayrate, boolean edible) 
	{
		super(fg, sw, so, sa, bi, um, edible);
		
		consumeSize = size;
		maxFoodWt = maxWt;
		this.setDecayRate(decayrate);
	}
	
	public ItemTFCFoodTransform(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, float size, float maxWt, float decayrate, boolean edible, boolean usable) 
	{
		super(fg, sw, so, sa, bi, um, edible, usable);
		
		consumeSize = size;
		maxFoodWt = maxWt;
		this.setDecayRate(decayrate);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(ItemFoodTFC.createTag(new ItemStack(this, 1), this.getFoodMaxWeight(new ItemStack(this, 1))));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		return CWTFC_Core.processRightClick(is, player, consumeSize, this.isEdible(is));
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		
		if(itemstack.getItem() == CWTFCItems.wheatWholeCWTFC || itemstack.getItem() == CWTFCItems.oatWholeCWTFC || itemstack.getItem() == CWTFCItems.riceWholeCWTFC ||
				itemstack.getItem() == CWTFCItems.barleyWholeCWTFC || itemstack.getItem() == CWTFCItems.ryeWholeCWTFC)
		{
			if(!world.isRemote)
			{
				Block id = world.getBlock(x, y, z);
				Material mat = id.getMaterial();
	
				if(side == 1 && id.isSideSolid(world, x, y, z, ForgeDirection.UP) &&!TFC_Core.isSoil(id) && !TFC_Core.isWater(id) && world.isAirBlock(x, y + 1, z) &&
						(mat == Material.wood || mat == Material.rock || mat == Material.iron))
				{
					world.setBlock(x, y + 1, z, CWTFCBlocks.GrainsBlock);
					TEGrains te = (TEGrains) world.getTileEntity(x, y + 1, z);
					if(te != null)
						te.setplacedGrains(itemstack);
					entityplayer.setCurrentItemOrArmor(0, null);
					return true;
				}
			}
		}
		return false;
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
	
	public float getConsumeSize()
	{
		return this.consumeSize;
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
	
	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		String s = "";
		if(Food.isPickled(is))
			s += TFC_Core.translate("word.pickled") + " ";
		else if(Food.isBrined(is) && !Food.isDried(is))
			s += TFC_Core.translate("word.brined") + " ";

		if(Food.isSalted(is))
			s += TFC_Core.translate("word.salted") + " ";
		if(Food.isCooked(is))
			s += TFC_Core.translate("word.cooked") + " ";
		else if(Food.isSmoked(is))
			s += TFC_Core.translate("word.smoked") + " ";

		if(Food.isDried(is) && !Food.isCooked(is))
			s += TFC_Core.translate("word.dried") + " ";
		
		s += TFC_Core.translate(this.getUnlocalizedNameInefficiently(is) + ".name");
		s += getCookedLevelString(is);
		return s.trim();
	}
}
