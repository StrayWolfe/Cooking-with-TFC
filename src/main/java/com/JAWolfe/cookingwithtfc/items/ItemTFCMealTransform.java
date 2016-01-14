package com.JAWolfe.cookingwithtfc.items;

import java.util.List;

import com.JAWolfe.cookingwithtfc.core.CWTFC_Core;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Render.Item.FoodItemRenderer;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.FoodRegistry;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Util.Helper;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ItemTFCMealTransform extends ItemTFCFoodTransform implements IFood
{
	private static float[] foodAmounts;
	private float maxFoodWt;
	SkillRank rank;
	String iconFile;

	public ItemTFCMealTransform(int sw, int so, int sa, int bi, int um, float size, float maxWt, SkillRank skillRank, String RefName) 
	{
		super(EnumFoodGroup.None, sw, so, sa, bi, um, size, maxWt);
		this.setCreativeTab(TFCTabs.TFC_FOODS);
		
		rank = skillRank;
		maxFoodWt = maxWt;
		this.setUnlocalizedName(RefName);
		iconFile = this.getUnlocalizedName().replace("item.", "");
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + iconFile);
		MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
	}
	
	public static ItemStack createTag(ItemStack is, float weight, float decay, ItemStack[] fg, float[]foodPct)
	{
		if (!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		
		Food.setDecayRate(is, 2.0F);
		Food.setWeight(is, weight);
		Food.setDecay(is, decay);
		Food.setDecayTimer(is, (int) TFC_Time.getTotalHours() + 1);
		
		setFoodGroups(is, fg);
		foodAmounts = foodPct;
		
		return is;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(this.createTag(new ItemStack(this, 1), this.getFoodMaxWeight(new ItemStack(this, 1)), 0, new ItemStack[]{}, new float[]{}));
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		return false;
	}
	
	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		return CWTFC_Core.processEating(is, world, player, this.getConsumeSize(), true);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);

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
			TerraFirmaCraft.LOG.error(TFC_Core.translate("error.error") + " " + is.getDisplayName() + " " +
					TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact"));
		}
	}

	@Override
	public void addFoodInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		float ounces = Helper.roundNumber(Food.getWeight(is), 100);
		if (ounces > 0)
			arraylist.add(TFC_Core.translate("gui.food.amount") + " " + ounces + " oz / " + getFoodMaxWeight(is) + " oz");
		float decay = Food.getDecay(is);
		if (decay > 0)
			arraylist.add(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.food.decay") + " " + Helper.roundNumber(decay / ounces * 100, 10) + "%");
		if (TFCOptions.enableDebugMode)
		{
			arraylist.add(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.food.decay") + ": " + decay);
			arraylist.add(EnumChatFormatting.DARK_GRAY + "Decay Rate: " + this.getDecayRate(is));
		}

		if (TFC_Core.showCtrlInformation())
			ItemFoodTFC.addTasteInformation(is, player, arraylist);
		else
			arraylist.add(TFC_Core.translate("gui.showtaste"));
		
		if(TFC_Core.showShiftInformation() && Food.getFoodGroups(is).length > 0)
		{			
			int[] fg = Food.getFoodGroups(is);
			for (int i = 0; i < fg.length; i++)
			{
				if (fg[i] != -1)
					arraylist.add(localize(fg[i]));
			}
		}
		else
			arraylist.add(TFC_Core.translate("gui.showIngreds"));
	}
	
	protected String localize(int id)
	{
		return ItemFoodTFC.getFoodGroupColor(FoodRegistry.getInstance().getFoodGroup(id)) +
				TFC_Core.translate(FoodRegistry.getInstance().getFood(id).getUnlocalizedName() + ".name");
	}

	@Override
	public EnumFoodGroup getFoodGroup() {return null;}

	@Override
	public int getFoodID() {return 0;}

	@Override
	public float getDecayRate(ItemStack is)
	{
		return Food.getDecayRate(is);
	}

	@Override
	public int getTasteSweet(ItemStack is) {
		int base = tasteSweet;
		if (is != null && is.getTagCompound().hasKey("tasteSweet"))
			base = is.getTagCompound().getInteger("tasteSweet");
		return base + Food.getSweetMod(is);
	}

	@Override
	public int getTasteSour(ItemStack is) {
		int base = tasteSour;
		if (is != null && is.getTagCompound().hasKey("tasteSour"))
			base = is.getTagCompound().getInteger("tasteSour");
		return base + Food.getSourMod(is);
	}

	@Override
	public int getTasteSalty(ItemStack is) {
		int base = tasteSalty;
		if (is != null && is.getTagCompound().hasKey("tasteSalty"))
			base = is.getTagCompound().getInteger("tasteSalty");
		return base + Food.getSaltyMod(is);
	}

	@Override
	public int getTasteBitter(ItemStack is) {
		int base = tasteBitter;
		if (is != null && is.getTagCompound().hasKey("tasteBitter"))
			base = is.getTagCompound().getInteger("tasteBitter");
		return base + Food.getBitterMod(is);
	}

	@Override
	public int getTasteSavory(ItemStack is) {
		int base = tasteUmami;
		if (is != null && is.getTagCompound().hasKey("tasteUmami"))
			base = is.getTagCompound().getInteger("tasteUmami");
		return base + Food.getSavoryMod(is);
	}
	
	public SkillRank getReqRank() {return this.rank;}
	
	public static void setFoodGroups(ItemStack is, ItemStack[] fg) 
	{
		int[] foodgroupIDs = new int[fg.length];
		for(int i = 0; i < fg.length; i++)
		{
			foodgroupIDs[i] = ((ItemFoodTFC)fg[i].getItem()).getFoodID();
		}
		
		Food.setFoodGroups(is, foodgroupIDs);
	}
	
	public float[] getFoodPct() {return foodAmounts;}
	
	public ItemTFCMealTransform setIconPath(String path)
	{
		this.iconFile = path;
		return this;
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
