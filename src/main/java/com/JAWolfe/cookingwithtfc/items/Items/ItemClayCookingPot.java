package com.JAWolfe.cookingwithtfc.items.Items;

import java.util.List;

import com.JAWolfe.cookingwithtfc.CookingWithTFC;
import com.JAWolfe.cookingwithtfc.init.CWTFCFluids;
import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.JAWolfe.cookingwithtfc.references.DetailsCWTFC;
import com.JAWolfe.cookingwithtfc.references.GUIs;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Items.Pottery.ItemPotteryBase;
import com.bioxx.tfc.Render.Item.FoodItemRenderer;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IBag;
import com.bioxx.tfc.api.Interfaces.ICookableFood;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

public class ItemClayCookingPot extends ItemPotteryBase implements IBag, ICookableFood
{	
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
		this.clayIcon = registerer.registerIcon(DetailsCWTFC.ModID + ":" + "Clay Cooking Pot");
		this.ceramicIcon = registerer.registerIcon(DetailsCWTFC.ModID + ":" + "Ceramic Cooking Pot");
		MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
	}
	
	@Override
	public boolean canStack()
	{
		return false;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		if(!player.isSneaking() && is.getItemDamage() != 0)
		{
			boolean isEmpty = getPotFluid(is) == null;
			MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, isEmpty);
			
			if (mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
			{
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;

				if (!world.canMineBlock(player, x, y, z))
					return is;

				if (isEmpty)
				{
					if (TFC_Core.isFreshWater(world.getBlock(x, y, z)))
					{
						world.setBlockToAir(x, y, z);
						if (player.capabilities.isCreativeMode)
							return is;
						addLiquid(is, new FluidStack(TFCFluids.FRESHWATER, 1000));
						return is;
					}
					else if (TFC_Core.isSaltWater(world.getBlock(x, y, z)))
					{
						world.setBlockToAir(x, y, z);
						if (player.capabilities.isCreativeMode)
							return is;
						addLiquid(is, new FluidStack(TFCFluids.SALTWATER, 1000));
						return is;
					}
					
					// Handle flowing water
					int flowX = x;
					int flowY = y;
					int flowZ = z;
					switch(mop.sideHit)
					{
					case 0:
						flowY = y - 1;
						break;
					case 1:
						flowY = y + 1;
						break;
					case 2:
						flowZ = z - 1;
						break;
					case 3:
						flowZ = z + 1;
						break;
					case 4:
						flowX = x - 1;
						break;
					case 5:
						flowX = x + 1;
						break;
					}
					
					if (TFC_Core.isFreshWater(world.getBlock(flowX, flowY, flowZ)))
					{
						world.setBlockToAir(flowX, flowY, flowZ);
						if (player.capabilities.isCreativeMode)
							return is;
						addLiquid(is, new FluidStack(TFCFluids.FRESHWATER, 1000));
						return is;
					}
					else if (TFC_Core.isSaltWater(world.getBlock(flowX, flowY, flowZ)))
					{
						world.setBlockToAir(flowX, flowY, flowZ);
						if (player.capabilities.isCreativeMode)
							return is;
						addLiquid(is, new FluidStack(TFCFluids.SALTWATER, 1000));
						return is;
					}
				}
			}
			
			player.openGui(CookingWithTFC.instance, GUIs.CLAYCOOKINGPOT.ordinal(), 
					player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
		
		return is;
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
	
	public FluidStack getPotFluid(ItemStack is)
	{
		if(is.hasTagCompound())
		{
			NBTTagCompound nbttagfluid = is.getTagCompound().getCompoundTag("PotFluid");
			return FluidStack.loadFluidStackFromNBT(nbttagfluid);
		}
		return null;
	}
	
	public void setPotFluid(ItemStack is, FluidStack fs)
	{
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		
		NBTTagCompound nbt = is.getTagCompound();
		
		NBTTagCompound nbttagfluid = new NBTTagCompound();
		fs.writeToNBT(nbttagfluid);
		nbt.setTag("PotFluid", nbttagfluid);
	}
	
	public int getFluidLevel(ItemStack is)
	{
		FluidStack fs = getPotFluid(is);
		if(fs != null)
			return fs.amount;
		return 0;
	}
	
	public void setFluidLevel(ItemStack is, int amount)
	{
		FluidStack fs = getPotFluid(is);
		if(fs != null)
		{
			fs.amount = amount;
			setPotFluid(is, fs);
		}
	}
	
	public void emptyFluid(ItemStack is)
	{
		if(is.hasTagCompound() && is.getTagCompound().hasKey("PotFluid"))
			is.getTagCompound().removeTag("PotFluid");
	}
	
	public int getMaxLiquid()
	{
		return 1000;
	}
	
	public int getLiquidScaled(ItemStack is, int i)
	{
		FluidStack fs = getPotFluid(is);
		if(fs != null)
			return fs.amount * i/getMaxLiquid();
		return 0;
	}
	
	public boolean addLiquid(ItemStack is, FluidStack fs)
	{
		if(fs != null)
		{
			FluidStack fsPot = getPotFluid(is);
			if(fsPot == null)
			{
				setPotFluid(is, fs.copy());
				fsPot = getPotFluid(is);
				if(fsPot.amount > getMaxLiquid())
				{
					setFluidLevel(is, getMaxLiquid());
					fsPot = getPotFluid(is);
					fs.amount = fs.amount - getMaxLiquid();
				}
				else
					fs.amount = 0;
			}
			else
			{
				if (fsPot.amount == getMaxLiquid() || !fsPot.isFluidEqual(fs))
					return false;
				
				int remainingFS = fsPot.amount + fs.amount - getMaxLiquid();
				setFluidLevel(is, Math.min(fsPot.amount + fs.amount, getMaxLiquid()));
				if (remainingFS > 0)
					fs.amount = remainingFS;
				else
					fs.amount = 0;
			}
			return true;
		}
		return false;
	}
	
	public ItemStack removeLiquid(ItemStack container, ItemStack cookingPot)
	{
		if(container == null || container.stackSize > 1)
			return container;
		
		FluidStack fsPot = getPotFluid(cookingPot);
		if(fsPot.getFluid().equals(TFCFluids.FRESHWATER) && (container.getItem() == TFCItems.potteryJug || 
				container.getItem() == TFCItems.woodenBucketEmpty || container.getItem() == TFCItems.redSteelBucketEmpty))
		{
			return fillContainer(container, cookingPot);
		}
		if(fsPot.getFluid().equals(TFCFluids.SALTWATER) && (container.getItem() == TFCItems.woodenBucketEmpty || 
				container.getItem() == TFCItems.redSteelBucketEmpty))
		{
			return fillContainer(container, cookingPot);
		}
		if(fsPot.getFluid().equals(CWTFCFluids.MILKCWTFC) && container.getItem() == TFCItems.woodenBucketEmpty)
		{
			return fillContainer(container, cookingPot);
		}
		if(fsPot.getFluid().equals(CWTFCFluids.CHICKENSTOCK) && container.getItem() == TFCItems.potteryBowl)
		{
			this.emptyFluid(cookingPot);
			return ItemTFCFoodTransform.createTag(new ItemStack(CWTFCItems.ChickenStock, 1), ((ItemTFCFoodTransform)CWTFCItems.ChickenStock).getMaxFoodWt(), 0F);
		}
		
		return container;
	}
	
	private ItemStack fillContainer(ItemStack container, ItemStack cookingPot)
	{
		FluidStack fsPot = getPotFluid(cookingPot);
		if(FluidContainerRegistry.isEmptyContainer(container))
		{
			ItemStack out = FluidContainerRegistry.fillFluidContainer(fsPot, container);
			if(out != null)
			{
				FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(out);
				setFluidLevel(cookingPot, getFluidLevel(cookingPot) - fs.amount);
				fsPot = getPotFluid(cookingPot);
				container = null;
				if(getFluidLevel(cookingPot) == 0)
				{
					emptyFluid(cookingPot);
					fsPot = getPotFluid(cookingPot);
				}
				return out;
			}
		}
		else if(fsPot != null && container.getItem() instanceof IFluidContainerItem)
		{
			FluidStack isfs = ((IFluidContainerItem) container.getItem()).getFluid(container);
			if(isfs == null || fsPot.isFluidEqual(isfs))
			{
				setFluidLevel(cookingPot, getFluidLevel(cookingPot) - ((IFluidContainerItem) container.getItem()).fill(container, fsPot, true));
				fsPot = getPotFluid(cookingPot);
				if(getFluidLevel(cookingPot) == 0)
				{
					emptyFluid(cookingPot);
					fsPot = getPotFluid(cookingPot);
				}
			}
		}
		return container;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{		
		ItemTerra.addSizeInformation(is, arraylist);		

		if (is.hasTagCompound())
		{
			if (TFC_Core.showCtrlInformation()) 
			{
				arraylist.add(TFC_Core.translate("gui.ShowContents"));
				
				if(getPotFluid(is) != null)
					arraylist.add(EnumChatFormatting.DARK_AQUA + getPotFluid(is).getLocalizedName());
				
				NBTTagList nbttaglist = is.getTagCompound().getTagList("Items", 10);
				
				if(nbttaglist != null)
				{
					for(int i = 0; i < nbttaglist.tagCount(); i++)
					{
						NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
						byte byte0 = nbttagcompound1.getByte("Slot");
						if(byte0 >= 0 && byte0 < 5)
						{
							ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound1);
							arraylist.add(EnumChatFormatting.YELLOW + TFC_Core.translate(itemstack.getItem().getUnlocalizedName() + ".name"));
						}
					}
				}
			}
			else
				arraylist.add(TFC_Core.translate("gui.PotContents"));
				
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
