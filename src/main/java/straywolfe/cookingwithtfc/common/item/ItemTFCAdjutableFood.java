package straywolfe.cookingwithtfc.common.item;

import java.util.List;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Food.ItemMeal;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Render.Item.FoodItemRenderer;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.FoodRegistry;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ICookableFood;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import straywolfe.cookingwithtfc.common.core.CWTFC_Core;
import straywolfe.cookingwithtfc.common.core.Tabs;
import straywolfe.cookingwithtfc.common.lib.Settings;
import straywolfe.cookingwithtfc.common.lib.ModInfo;

public class ItemTFCAdjutableFood extends ItemMeal implements ICookableFood
{
	private EnumFoodGroup foodgroup;
	public int foodID;
	private float consumeSize;
	private float maxFoodWt;
	protected int tasteSweet;
	protected int tasteSour;
	protected int tasteSalty;
	protected int tasteBitter;
	protected int tasteUmami;
	public float decayRate = 1.0f;
	public boolean edible = true;
	public boolean canBeUsedRaw = true;
	private boolean isBowl = false;
	private boolean isJug = false;
	
	public ItemTFCAdjutableFood(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, float size, float maxWt)
	{
		foodgroup = fg;
		tasteSweet = sw;
		tasteSour = so;
		tasteSalty = sa;
		tasteBitter = bi;
		tasteUmami = um;
		consumeSize = size;
		maxFoodWt = maxWt;
		foodID = FoodRegistry.getInstance().registerFood(fg, this);
		setCreativeTab(Tabs.MAINTAB);
	}
	
	public ItemTFCAdjutableFood(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, float size, float maxWt, float decay)
	{
		this(fg, sw, so, sa, bi, um, size, maxWt);
		decayRate = decay;
	}
	
	public ItemTFCAdjutableFood(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, float size, float maxWt, float decay, boolean edible, boolean usableRaw)
	{
		this(fg, sw, so, sa, bi, um, size, maxWt, decay);
		this.edible = edible;
		canBeUsedRaw = usableRaw;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		itemIcon = registerer.registerIcon(ModInfo.ModID + ":" + getUnlocalizedName().replace("item.", ""));
		MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
	}
	
	@Override
	public IIcon getIconFromDamage(int i)
	{
		return this.itemIcon;
	}

	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		if(Settings.diminishingReturns)
			return CWTFC_Core.processRightClick(is, player, consumeSize, isEdible(is));
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
		if(isEdible(is))
		{
			is = CWTFC_Core.processEating(is, world, player, consumeSize, false);

			if (is.stackSize == 0)
			{
				if (Settings.bowlBreakFreq != -1 && world.rand.nextInt(Settings.bowlBreakFreq) == 0)
				{
					world.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f, player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
				}
				else if(isJug && !player.inventory.addItemStackToInventory(new ItemStack(TFCItems.potteryJug, 1, 1)))
					return new ItemStack(TFCItems.potteryJug, 1, 1);
				else if (isBowl && !player.inventory.addItemStackToInventory(new ItemStack(TFCItems.potteryBowl, 1, 1)))
					return new ItemStack(TFCItems.potteryBowl, 1, 1);
			}
			
		}
		
		return is;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(ItemFoodTFC.createTag(new ItemStack(this, 1), getFoodMaxWeight(new ItemStack(this, 1))));
	}
	
	public static ItemStack createTag(ItemStack is)
	{
		if (!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());

		Food.setWeight(is, 0);
		Food.setDecay(is, 0);
		Food.setDecayTimer(is, (int) TFC_Time.getTotalHours() + 1);
		return is;
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
		else
		{
			arraylist.add(TFC_Core.translate("gui.badnbt"));
			TerraFirmaCraft.LOG.error(TFC_Core.translate("error.error") + " " + is.getUnlocalizedName() + " " +
					TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact"));
		}
	}
	
	@Override
	public void addFoodInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		float ounces = roundNumber(Food.getWeight(is), 100);
		if (ounces > 0 && ounces <= maxFoodWt)
			arraylist.add(TFC_Core.translate("gui.food.amount") + " " + ounces + " oz / " + maxFoodWt + " oz");

		float decay = Food.getDecay(is);
		if (decay > 0)
			arraylist.add(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.food.decay") + " " + roundNumber(decay / ounces * 100, 10) + "%");
		if (TFCOptions.enableDebugMode)
		{
			arraylist.add(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.food.decay") + ": " + decay);
			arraylist.add(EnumChatFormatting.DARK_GRAY + "Decay Rate: " + roundNumber(this.getDecayRate(is), 100));
		}

		if (TFC_Core.showCtrlInformation())
			ItemFoodTFC.addTasteInformation(is, player, arraylist);
		else
			arraylist.add(TFC_Core.translate("gui.showtaste"));
	}
	
	private float roundNumber(float input, float rounding)
	{
		return com.bioxx.tfc.api.Util.Helper.roundNumber(input, rounding);
	}
	
	@Override
	public EnumFoodGroup getFoodGroup()
	{
		return foodgroup;
	}
	
	@Override
	public int getFoodID()
	{
		return foodID;
	}
	
	@Override
	public boolean isEdible(ItemStack is)
	{
		return edible;
	}

	@Override
	public boolean isUsable(ItemStack is)
	{
		return canBeUsedRaw;
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
	
	public float getConsumeSize()
	{
		return this.consumeSize;
	}
	
	public ItemTFCAdjutableFood setIsBowl(boolean bowl)
	{
		isBowl = bowl;
		return this;
	}
	
	public ItemTFCAdjutableFood setIsJug(boolean jug)
	{
		isJug = jug;
		return this;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		if(isBowl || isJug)
			return EnumAction.drink;
		else
			return EnumAction.eat;
	}
	
	@Override
	public EnumSize getSize(ItemStack is)
	{
		float weight = Food.getWeight(is);
		if(weight <= 20)
			return EnumSize.TINY;
		else if(weight <= 40)
			return EnumSize.VERYSMALL;
		else if(weight <= 80)
			return EnumSize.SMALL;
		else
			return EnumSize.MEDIUM;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		float weight = Food.getWeight(is);
		if(weight < 80)
			return EnumWeight.LIGHT;
		else if(weight < 160)
			return EnumWeight.MEDIUM;
		else
			return EnumWeight.HEAVY;
	}

	@Override
	public boolean canSmoke() {return false;}

	@Override
	public float getSmokeAbsorbMultiplier() {return 0;}
}
