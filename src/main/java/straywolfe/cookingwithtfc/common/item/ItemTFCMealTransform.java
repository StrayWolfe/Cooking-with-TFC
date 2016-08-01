package straywolfe.cookingwithtfc.common.item;

import java.util.List;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Food.ItemFoodTFC;
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
import com.bioxx.tfc.api.Util.Helper;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import straywolfe.cookingwithtfc.common.core.CWTFC_Core;
import straywolfe.cookingwithtfc.common.lib.Settings;
import straywolfe.cookingwithtfc.common.lib.ModInfo;

public class ItemTFCMealTransform extends ItemTerra implements ICookableFood
{
	protected int tasteSweet;
	protected int tasteSour;
	protected int tasteSalty;
	protected int tasteBitter;
	protected int tasteUmami;
	private float consumeSize;
	private float maxFoodWt;
	public float decayRate = 1.0f;
	public boolean edible = true;
	public boolean canBeUsedRaw = true;
	private boolean hasBowl = false;
	private boolean customIcon = false;
	SkillRank rank;
	String iconFile;
	

	public ItemTFCMealTransform(int sw, int so, int sa, int bi, int um, float size, float maxWt, SkillRank skillRank, String RefName) 
	{
		super();
		metaNames = null;
		metaIcons = null;
		setFolder("food/");
		stackable = false;
		setCreativeTab(null);
		
		maxFoodWt = maxWt;
		consumeSize = size;
		rank = skillRank;
		setUnlocalizedName(RefName);
		iconFile = getUnlocalizedName().replace("item.", "");
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		if(customIcon)
			itemIcon = registerer.registerIcon(ModInfo.ModID + ":" + iconFile);
		else
			itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + iconFile);
		
		MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
	}
	
	@Override
	public IIcon getIconFromDamage(int i)
	{
		if(metaNames != null && i < metaNames.length)
			return metaIcons[i];
		else
			return itemIcon;
	}
	
	public ItemTFCMealTransform hasCustomIcon(boolean custom)
	{
		customIcon = custom;
		return this;
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
		setFoodPct(is, foodPct);
		
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
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		if(Settings.diminishingReturns)
			is = CWTFC_Core.processEating(is, world, player, getConsumeSize(), true);
		else
		{
			world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
			if(!world.isRemote)
			{
				if (is.hasTagCompound())
				{
					float weight = Food.getWeight(is);
					float decay = Math.max(Food.getDecay(is), 0);
					float eatAmount = getEatAmount(foodstats, weight - decay);
					float tasteFactor = foodstats.getTasteFactor(is);

					int[] fg = Food.getFoodGroups(is);
					float[] nWeights = getNutritionalWeights(fg);
					for (int i = 0; i < fg.length; i++ )
					{
						if (fg[i] != -1)
							foodstats.addNutrition(FoodRegistry.getInstance().getFoodGroup(fg[i]), eatAmount * nWeights[i] * 2.5f);
					}

					foodstats.stomachLevel += eatAmount * getFillingBoost();
					foodstats.setSatisfaction(foodstats.getSatisfaction() + ((eatAmount / 3f) * tasteFactor), fg);

					if (FoodStatsTFC.reduceFood(is, eatAmount))
					{
						is.stackSize = 0;
					}
				}
			}
			TFC_Core.setPlayerFoodStats(player, foodstats);
		}
		
		if (hasBowl && is.stackSize == 0)
		{
			if (Settings.bowlBreakFreq != -1 && world.rand.nextInt(Settings.bowlBreakFreq) == 0)
			{
				world.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f, player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
			}
			else if (!player.inventory.addItemStackToInventory(new ItemStack(TFCItems.potteryBowl, 1, 1)))
			{
				return new ItemStack(TFCItems.potteryBowl, 1, 1);
			}
		}
		
		return is;
	}
	
	protected float getEatAmount(FoodStatsTFC fs, float amount)
	{
		float eatAmount = Math.min(amount, 5);
		float stomachDiff = fs.stomachLevel+eatAmount-fs.getMaxStomach(fs.player);
		if(stomachDiff > 0)
			eatAmount-=stomachDiff;
		return eatAmount;
	}
	
	protected float getFillingBoost()
	{
		return 1.0f;
	}
	
	protected float[] getNutritionalWeights(int[] foodGroups)
	{
		float[] nw = new float[foodGroups.length];
		float[] fw = getFoodWeights();
		float totalWeight = 0;
		for(int i  = 0; i < foodGroups.length; i++)
		{
			if(foodGroups[i] != -1)
			{
				totalWeight += fw[i];
			}
		}

		for(int i  = 0; i < foodGroups.length; i++)
		{
			nw[i] = fw[i]/totalWeight;
		}
		return nw;
	}

	protected float[] getFoodWeights()
	{
		return new float[]{10f,4f,4f,2f};
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
			
			if(Settings.diminishingReturns)
			{
				if(this.isEdible(is))
					CWTFC_Core.getFoodUse(is, player, arraylist);
				else
					arraylist.add(EnumChatFormatting.DARK_GRAY + "Not currently edible");
			}
		}
	}

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
	public String getUnlocalizedName(ItemStack itemstack)
    {
        return getUnlocalizedName();
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
	
	public ItemTFCMealTransform setHasBowl(boolean bowl)
	{
		hasBowl = bowl;
		return this;
	}

	@Override
	public EnumFoodGroup getFoodGroup() {return EnumFoodGroup.None;}

	@Override
	public int getFoodID() {return 0;}

	@Override
	public float getDecayRate(ItemStack is)
	{
		return Food.getDecayRate(is);
	}
	
	@Override
	public ItemStack onDecayed(ItemStack is, World world, int i, int j, int k)
	{
		return null;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.eat;
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
			if(fg[i] != null)
			{
				if(fg[i].getItem() instanceof ItemFoodTFC)
					foodgroupIDs[i] = ((ItemFoodTFC)fg[i].getItem()).getFoodID();
				else if(fg[i].getItem() instanceof ItemTFCAdjutableFood)
					foodgroupIDs[i] = ((ItemTFCAdjutableFood)fg[i].getItem()).getFoodID();
			}
		}
		
		Food.setFoodGroups(is, foodgroupIDs);
	}
	
	public static void setFoodPct(ItemStack is, float[] foodAmounts)
	{
		NBTTagCompound nbt;
		
		if (is.hasTagCompound())
			nbt = is.getTagCompound();
		else
			nbt = new NBTTagCompound();
		
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < foodAmounts.length; i++)
		{
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setFloat("FoodPct", foodAmounts[i]);
			nbttaglist.appendTag(nbttagcompound1);
		}
		nbt.setTag("foodAmounts", nbttaglist);
	}
	
	public float[] getFoodPct(ItemStack is) 
	{
		NBTTagCompound nbt;
		
		if (is.hasTagCompound())
			nbt = is.getTagCompound();
		else
			nbt = new NBTTagCompound();
		
		if (nbt.hasKey("foodAmounts"))
		{
			
			NBTTagList nbttaglist = nbt.getTagList("foodAmounts", 10);
			float[] foodAmounts = new float[nbttaglist.tagCount()];
			for(int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				foodAmounts[i] = nbttagcompound1.getFloat("FoodPct");
			}
			
			return foodAmounts;
		}
		else
			return new float[] {};
	}
	
	public ItemTFCMealTransform setIconPath(String path)
	{
		this.iconFile = path;
		return this;
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
	public boolean renderDecay() 
	{
		return true;
	}

	@Override
	public boolean renderWeight()
	{
		return true;
	}

	@Override
	public boolean canSmoke() {return false;}

	@Override
	public float getSmokeAbsorbMultiplier() {return 0;}
}
