package straywolfe.cookingwithtfc.common.item;

import java.util.List;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Render.Item.FoodItemRenderer;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Util.Helper;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import straywolfe.cookingwithtfc.common.core.CWTFC_Core;
import straywolfe.cookingwithtfc.common.lib.Settings;
import straywolfe.cookingwithtfc.common.lib.ModInfo;

public class ItemTFCFoodTransform extends ItemFoodTFC
{
	private float consumeSize;
	private boolean hasCustomIcon = false;
	
	public ItemTFCFoodTransform(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, float size) 
	{
		super(fg, sw, so, sa, bi, um);

		consumeSize = size;
	}
	
	public ItemTFCFoodTransform(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, float size, float decayrate, boolean edible, boolean usable) 
	{
		super(fg, sw, so, sa, bi, um, edible, usable);
		
		consumeSize = size;
		this.setDecayRate(decayrate);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(ItemFoodTFC.createTag(new ItemStack(this), getFoodMaxWeight(new ItemStack(this))));
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		if(hasCustomIcon)
		{
			String name = this.getUnlocalizedName();
			this.itemIcon = registerer.registerIcon(ModInfo.ModID + ":" + name.replace("item.", ""));
			MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
		}
		else
			super.registerIcons(registerer);
	}
	
	public ItemTFCFoodTransform setCustomIcon(boolean hasIcon)
	{
		this.hasCustomIcon = hasIcon;
		return this;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		if(Settings.diminishingReturns)
			return CWTFC_Core.processRightClick(is, player, consumeSize, this.isEdible(is));
		else
		{
			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
			if (foodstats.needFood() && this.isEdible(is))
				player.setItemInUse(is, 32);

			return is;
		}
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		return false;
	}
	
	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		if(Settings.diminishingReturns)
			return CWTFC_Core.processEating(is, world, player, consumeSize, false);
		else
		{
			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
			if(!world.isRemote && this.isEdible(is))
			{
				if(is.hasTagCompound())
				{
					float weight = Food.getWeight(is);
					float decay = Math.max(Food.getDecay(is), 0);

					float eatAmount = Math.min(weight - decay, 5f);
					float stomachDiff = foodstats.stomachLevel+eatAmount-foodstats.getMaxStomach(foodstats.player);
					if(stomachDiff > 0)
						eatAmount-=stomachDiff;

					float tasteFactor = foodstats.getTasteFactor(is);
					foodstats.addNutrition(((IFood)(is.getItem())).getFoodGroup(), eatAmount*tasteFactor);
					foodstats.stomachLevel += eatAmount*tasteFactor;
					if(FoodStatsTFC.reduceFood(is, eatAmount))
						is.stackSize = 0;
				}
				else
				{
					foodstats.addNutrition(((IFood)(is.getItem())).getFoodGroup(), 1f);

					String error = TFC_Core.translate("error.error") + " " + is.getUnlocalizedName() + " " +
									TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact");
					TerraFirmaCraft.LOG.error(error);
					TFC_Core.sendInfoMessage(player, new ChatComponentText(error));
				}
			}

			world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			TFC_Core.setPlayerFoodStats(player, foodstats);
			
			return is;
		}
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
			
			if(Settings.diminishingReturns)
			{
				if(this.isEdible(is))
					CWTFC_Core.getFoodUse(is, player, arraylist);
				else
					arraylist.add(EnumChatFormatting.DARK_GRAY + "Not currently edible");
			}
		}
	}
	
	@Override
	public void addFoodInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		float ounces = Helper.roundNumber(Food.getWeight(is), 100);
		if (ounces > 0 && ounces <= getMaxFoodWt())
			arraylist.add(TFC_Core.translate("gui.food.amount") + " " + ounces + " oz / " + getMaxFoodWt() + " oz");

		float decay = Food.getDecay(is);
		if (decay > 0)
			arraylist.add(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.food.decay") + " " + Helper.roundNumber(decay / ounces * 100, 10) + "%");
		if (TFCOptions.enableDebugMode)
		{
			arraylist.add(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.food.decay") + ": " + decay);
			arraylist.add(EnumChatFormatting.DARK_GRAY + "Decay Rate: " + Helper.roundNumber(this.getDecayRate(is), 100));
		}

		if (TFC_Core.showCtrlInformation())
			ItemFoodTFC.addTasteInformation(is, player, arraylist);
		else
			arraylist.add(TFC_Core.translate("gui.showtaste"));
	}
	
	public float getConsumeSize()
	{
		return this.consumeSize;
	}
	
	@Override
	public ItemTFCFoodTransform setDecayRate(float f)
	{
		this.decayRate = f;
		return this;
	}
	
	@Override
	public float getFoodMaxWeight(ItemStack is) 
	{
		return getMaxFoodWt();
	}
	
	public float getMaxFoodWt()
	{
		return Global.FOOD_MAX_WEIGHT;
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
