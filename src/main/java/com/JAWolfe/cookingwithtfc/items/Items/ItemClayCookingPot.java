package com.JAWolfe.cookingwithtfc.items.Items;

import java.util.List;

import com.JAWolfe.cookingwithtfc.CookingWithTFC;
import com.JAWolfe.cookingwithtfc.references.GUIs;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Items.Pottery.ItemPotteryBase;
import com.bioxx.tfc.Render.Item.FoodItemRenderer;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IBag;
import com.bioxx.tfc.api.Interfaces.ICookableFood;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

public class ItemClayCookingPot extends ItemPotteryBase implements IBag, ICookableFood
{
	private FluidStack potFluid;
	
	public ItemClayCookingPot()
	{
		super();
		this.hasSubtypes = true;
		this.metaNames = new String[]{"Clay", "Ceramic"};
		setCreativeTab(TFCTabs.TFC_POTTERY);
		this.setFolder("pottery/");
		this.setUnlocalizedName("clayCookingPot");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.clayIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[0] + " Vessel");
		this.ceramicIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[1] + " Vessel");
		MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
	}
	
	@Override
	public boolean canStack()
	{
		return false;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(!entityplayer.isSneaking() && itemstack.getItemDamage() != 0)
		{
			entityplayer.openGui(CookingWithTFC.instance, GUIs.CLAYCOOKINGPOT.ordinal(), 
					entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
		}
		
		return itemstack;
	}
	
	public void setInputMode(ItemStack is, boolean inputMode)
	{
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setBoolean("inputMode", inputMode);
	}
	
	public boolean getInputMode(ItemStack is)
	{
		if(is != null && is.hasTagCompound())
			return is.getTagCompound().getBoolean("inputMode");
		else
			return false;
	}

	@Override
	public ItemStack[] loadBagInventory(ItemStack is) {
		return null;
	}
	
	public FluidStack getPotFluid()
	{
		return this.potFluid;
	}
	
	public void setPotFluid(FluidStack fs)
	{
		this.potFluid = fs;
	}
	
	public int getFluidLevel()
	{
		if(this.potFluid != null)
			return this.potFluid.amount;
		return 0;
	}
	
	public int getMaxLiquid()
	{
		return 1000;
	}
	
	public int getLiquidScaled(int i)
	{
		if(potFluid != null)
			return potFluid.amount * i/getMaxLiquid();
		return 0;
	}
	
	public boolean addLiquid(FluidStack fs)
	{
		if(fs != null)
		{
			if(potFluid == null)
			{
				potFluid = fs.copy();
				if(potFluid.amount > getMaxLiquid())
				{
					potFluid.amount = getMaxLiquid();
					fs.amount = fs.amount - getMaxLiquid();
				}
				else
					fs.amount = 0;
			}
			else
			{
				if (potFluid.amount == getMaxLiquid() || !potFluid.isFluidEqual(fs))
					return false;
				
				int remainingFS = potFluid.amount + fs.amount - getMaxLiquid();
				potFluid.amount = Math.min(potFluid.amount + fs.amount, getMaxLiquid());
				if (remainingFS > 0)
					fs.amount = remainingFS;
				else
					fs.amount = 0;
			}
			return true;
		}
		return false;
	}
	
	public ItemStack removeLiquid(ItemStack is)
	{
		if(is == null || is.stackSize > 1)
			return is;
		if(FluidContainerRegistry.isEmptyContainer(is))
		{
			ItemStack out = FluidContainerRegistry.fillFluidContainer(potFluid, is);
			if(out != null)
			{
				FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(out);
				potFluid.amount -= fs.amount;
				is = null;
				if(potFluid.amount == 0)
				{
					potFluid = null;
				}
				return out;
			}
		}
		else if(potFluid != null && is.getItem() instanceof IFluidContainerItem)
		{
			FluidStack isfs = ((IFluidContainerItem) is.getItem()).getFluid(is);
			if(isfs == null || potFluid.isFluidEqual(isfs))
			{
				potFluid.amount -= ((IFluidContainerItem) is.getItem()).fill(is, potFluid, true);
				if(potFluid.amount == 0)
					potFluid = null;
			}
		}
		return is;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{		
		ItemTerra.addSizeInformation(is, arraylist);		

		if (is.hasTagCompound())
		{
			if(Food.isCooked(is))
				arraylist.add("Cooked Soup");
				
			ItemFoodTFC.addFoodHeatInformation(is, arraylist);
		}
		
		addExtraInformation(is, player, arraylist);
	}

	@Override
	public EnumFoodGroup getFoodGroup() {return null;}

	@Override
	public int getFoodID() {return 0;}

	@Override
	public float getDecayRate(ItemStack is) {return 0;}

	@Override
	public float getFoodMaxWeight(ItemStack is) {return 0;}

	@Override
	public ItemStack onDecayed(ItemStack is, World world, int i, int j, int k) {return null;}

	@Override
	public boolean isEdible(ItemStack is) {return false;}

	@Override
	public boolean isUsable(ItemStack is) {return false;}

	@Override
	public int getTasteSweet(ItemStack is) {return 0;}

	@Override
	public int getTasteSour(ItemStack is) {return 0;}

	@Override
	public int getTasteSalty(ItemStack is) {return 0;}

	@Override
	public int getTasteBitter(ItemStack is) {return 0;}

	@Override
	public int getTasteSavory(ItemStack is) {return 0;}

	@Override
	public boolean renderDecay() {return false;}

	@Override
	public boolean renderWeight() {return false;}

	@Override
	public boolean canSmoke() {return false;}

	@Override
	public float getSmokeAbsorbMultiplier() {return 0;}
}
