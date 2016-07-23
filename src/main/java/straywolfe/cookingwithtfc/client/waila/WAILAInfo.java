package straywolfe.cookingwithtfc.client.waila;

import java.util.List;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.TFCItems;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.block.*;
import straywolfe.cookingwithtfc.common.item.ItemTFCMealTransform;
import straywolfe.cookingwithtfc.common.tileentity.*;

public class WAILAInfo implements IWailaDataProvider
{
	public static void callbackRegister(IWailaRegistrar reg)
	{
		reg.registerStackProvider(new WAILAInfo(), TileGrains.class);
		reg.registerBodyProvider(new WAILAInfo(), TileGrains.class);
		reg.registerNBTProvider(new WAILAInfo(), TileGrains.class);
		
		reg.registerStackProvider(new WAILAInfo(), BlockMixBowl.class);
		reg.registerHeadProvider(new WAILAInfo(), BlockMixBowl.class);
		reg.registerNBTProvider(new WAILAInfo(), TileMixBowl.class);
		
		reg.registerStackProvider(new WAILAInfo(), BlockPrepTable.class);
		reg.registerHeadProvider(new WAILAInfo(), BlockPrepTable.class);
		
		reg.registerStackProvider(new WAILAInfo(), BlockPrepTable2.class);
		reg.registerHeadProvider(new WAILAInfo(), BlockPrepTable2.class);
		
		reg.registerBodyProvider(new WAILAInfo(), TileNestBoxCWTFC.class);
		reg.registerNBTProvider(new WAILAInfo(), TileNestBoxCWTFC.class);
		
		reg.registerBodyProvider(new WAILAInfo(), TileCookingPot.class);
		reg.registerNBTProvider(new WAILAInfo(), TileCookingPot.class);
		
		reg.registerStackProvider(new WAILAInfo(), BlockMeat.class);
		reg.registerHeadProvider(new WAILAInfo(), BlockMeat.class);
		
		reg.registerStackProvider(new WAILAInfo(), BlockSandwich.class);
		reg.registerHeadProvider(new WAILAInfo(), BlockSandwich.class);
		
		reg.registerStackProvider(new WAILAInfo(), BlockBowl.class);
		reg.registerHeadProvider(new WAILAInfo(), BlockBowl.class);
	}
	
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		Block block = accessor.getBlock();
		TileEntity tileEntity = accessor.getTileEntity();
		
		if(tileEntity instanceof TileGrains)
			return grainStack(accessor, config);
		else if(block instanceof BlockMixBowl)
			return mixBowlStack(accessor, config);
		else if(block instanceof BlockPrepTable)
			return prepTableStack(accessor, config);
		else if(block instanceof BlockPrepTable2)
			return prepTable2Stack(accessor, config);
		else if(block instanceof BlockMeat)
			return meatStack(accessor, config);
		else if(block instanceof BlockSandwich)
			return sandwichStack(accessor, config);
		else if(block instanceof BlockBowl)
			return bowlStack(accessor, config);
			
		return accessor.getStack();
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) 
	{
		Block block = accessor.getBlock();
		
		if(block instanceof BlockMixBowl)
			currenttip = mixBowlHead(itemStack, currenttip, accessor, config);
		else if(block instanceof BlockPrepTable)
			currenttip = prepTableHead(itemStack, currenttip, accessor, config);
		else if(block instanceof BlockPrepTable2)
			currenttip = prepTable2Head(itemStack, currenttip, accessor, config);
		else if(block instanceof BlockMeat)
			currenttip = meatHead(itemStack, currenttip, accessor, config);
		else if(block instanceof BlockSandwich)
			currenttip = sandwichHead(itemStack, currenttip, accessor, config);
		else if(block instanceof BlockBowl)
			currenttip = bowlHead(itemStack, currenttip, accessor, config);
		
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		TileEntity tileEntity = accessor.getTileEntity();
		
		if (tileEntity instanceof TileGrains)
			currenttip = grainBody(itemStack, currenttip, accessor, config);
		else if (tileEntity instanceof TileNestBoxCWTFC)
			currenttip = nestBoxBody(itemStack, currenttip, accessor, config);
		else if(tileEntity instanceof TileCookingPot)
			currenttip = cookingPotBody(itemStack, currenttip, accessor, config);
		
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) 
	{
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) 
	{
		if (te != null)
			te.writeToNBT(tag);
		return tag;
	}
	
	public ItemStack mixBowlStack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return new ItemStack(CWTFCBlocks.mixingBowl, 1, 1);
	}
	
	private List<String> mixBowlHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		currenttip.set(0, EnumChatFormatting.WHITE.toString() + itemStack.getDisplayName());

		return currenttip;
	}
	
	public ItemStack prepTableStack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return new ItemStack(accessor.getBlock(), 1, accessor.getMetadata());
	}
	
	private List<String> prepTableHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		currenttip.set(0, EnumChatFormatting.WHITE.toString() + itemStack.getDisplayName());

		return currenttip;
	}
	
	public ItemStack prepTable2Stack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return new ItemStack(accessor.getBlock(), 1, accessor.getMetadata());
	}
	
	private List<String> prepTable2Head(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		currenttip.set(0, EnumChatFormatting.WHITE.toString() + itemStack.getDisplayName());

		return currenttip;
	}
	
	public ItemStack meatStack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		TileMeat te = (TileMeat)accessor.getTileEntity();
		
		if(te.getplacedMeat() != null)
			return ItemFoodTFC.createTag(new ItemStack(te.getplacedMeat().getItem()), 999, -24);
		else
			return null;
	}
	
	private List<String> meatHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		currenttip.set(0, EnumChatFormatting.WHITE.toString() + ((TileMeat)accessor.getTileEntity()).getplacedMeat().getDisplayName());

		return currenttip;
	}
	
	public ItemStack bowlStack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		TileBowl te = (TileBowl)accessor.getTileEntity();
		
		if(te.getSaladContents()[0] == null)
			return new ItemStack(TFCItems.potteryBowl, 1, 1);
		else if(te.getSaladType() == 2)
			return ItemFoodTFC.createTag(new ItemStack(CWTFCItems.PotatoSalad), 999, -24);
		else if(te.getSaladType() == 1)
			return ItemFoodTFC.createTag(new ItemStack(CWTFCItems.FruitSalad), 999, -24);
		else
			return ItemFoodTFC.createTag(new ItemStack(CWTFCItems.VeggySalad), 999, -24);
	}
	
	private List<String> bowlHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		TileBowl te = (TileBowl)accessor.getTileEntity();
		
		if(te.getSaladContents()[0] == null)
			currenttip.set(0, EnumChatFormatting.WHITE.toString() + TFC_Core.translate(TFCItems.potteryBowl.getUnlocalizedName() + ".Ceramic Bowl.name"));
		else if(te.getSaladType() == 2)
			currenttip.set(0, EnumChatFormatting.WHITE.toString() + TFC_Core.translate(CWTFCItems.PotatoSalad.getUnlocalizedName() + ".name"));
		else if(te.getSaladType() == 1)
			currenttip.set(0, EnumChatFormatting.WHITE.toString() + TFC_Core.translate(CWTFCItems.FruitSalad.getUnlocalizedName() + ".name"));
		else
			currenttip.set(0, EnumChatFormatting.WHITE.toString() + TFC_Core.translate(CWTFCItems.VeggySalad.getUnlocalizedName() + ".name"));

		return currenttip;
	}
	
	public ItemStack grainStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		return ItemStack.loadItemStackFromNBT(tag.getCompoundTag("placedGrain"));
	}

	public List<String> grainBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		float progressCount = tag.getInteger("WorkCounter");
		
		int progress = (int) Math.min((progressCount / 20) * 100, 100);
		currenttip.add(TFC_Core.translate("gui.progress") + ": " + progress + "%");

		return currenttip;
	}
	
	public ItemStack sandwichStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		String breadType = tag.getString("breadType");
		int breadMeta = 0;
		
		if(CWTFCItems.cornBreadCWTFC.getUnlocalizedName().equals(breadType))
			breadMeta = 1;
		else if(CWTFCItems.oatBreadCWTFC.getUnlocalizedName().equals(breadType))
			breadMeta = 2;
		else if(CWTFCItems.riceBreadCWTFC.getUnlocalizedName().equals(breadType))
			breadMeta = 3;
		else if(CWTFCItems.ryeBreadCWTFC.getUnlocalizedName().equals(breadType))
			breadMeta = 4;
		else if(CWTFCItems.wheatBreadCWTFC.getUnlocalizedName().equals(breadType))
			breadMeta = 5;
		
		ItemStack stack = ItemTFCMealTransform.createTag(new ItemStack(CWTFCItems.ChickenSandwich, 1, breadMeta), 999, -24, new ItemStack[]{}, new float[]{});
		
		return stack;
	}
	
	private List<String> sandwichHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		NBTTagCompound tag = accessor.getNBTData();
		String breadType = tag.getString("breadType");
		String meatType = tag.getString("meatType");
		String breadName = TFC_Core.translate("word.Barley");
		String sandwichName = TFC_Core.translate("word.Sandwich");
		int topToast = tag.getInteger("topToast");
		
		if(CWTFCItems.cornBreadCWTFC.getUnlocalizedName().equals(breadType))
			breadName = TFC_Core.translate("word.Corn");
		else if(CWTFCItems.oatBreadCWTFC.getUnlocalizedName().equals(breadType))
			breadName = TFC_Core.translate("word.Oat");
		else if(CWTFCItems.riceBreadCWTFC.getUnlocalizedName().equals(breadType))
			breadName = TFC_Core.translate("word.Rice");
		else if(CWTFCItems.ryeBreadCWTFC.getUnlocalizedName().equals(breadType))
			breadName = TFC_Core.translate("word.Rye");
		else if(CWTFCItems.wheatBreadCWTFC.getUnlocalizedName().equals(breadType))
			breadName = TFC_Core.translate("word.Wheat");
		
		if(CWTFCItems.chickenCookedCWTFC.getUnlocalizedName().equals(meatType) || CWTFCItems.BoiledChicken.getUnlocalizedName().equals(meatType))
			sandwichName = TFC_Core.translate(CWTFCItems.ChickenSandwich.getUnlocalizedName() + ".name");
		else if(CWTFCItems.porkchopCookedCWTFC.getUnlocalizedName().equals(meatType) || CWTFCItems.BoiledPork.getUnlocalizedName().equals(meatType))
			sandwichName = TFC_Core.translate(CWTFCItems.HamSandwich.getUnlocalizedName() + ".name");
		else if(CWTFCItems.eggCookedCWTFC.getUnlocalizedName().equals(meatType))
			sandwichName = TFC_Core.translate(CWTFCItems.FriedEggSandwich.getUnlocalizedName() + ".name");
		else if(CWTFCItems.muttonCookedCWTFC.getUnlocalizedName().equals(meatType))
			sandwichName = TFC_Core.translate(CWTFCItems.MuttonSandwich.getUnlocalizedName() + ".name");
		else if(CWTFCItems.beefCookedCWTFC.getUnlocalizedName().equals(meatType) || CWTFCItems.BoiledBeef.getUnlocalizedName().equals(meatType))
			sandwichName = TFC_Core.translate(CWTFCItems.RoastBeefSandwich.getUnlocalizedName() + ".name");
		else if(CWTFCItems.fishCookedCWTFC.getUnlocalizedName().equals(meatType) || CWTFCItems.BoiledFish.getUnlocalizedName().equals(meatType) ||
				CWTFCItems.calamariCookedCWTFC.getUnlocalizedName().equals(meatType))
			sandwichName = TFC_Core.translate(CWTFCItems.SalmonSandwich.getUnlocalizedName() + ".name");
		else if(CWTFCItems.venisonCookedCWTFC.getUnlocalizedName().equals(meatType) || CWTFCItems.BoiledVenison.getUnlocalizedName().equals(meatType) ||
				CWTFCItems.horseMeatCookedCWTFC.getUnlocalizedName().equals(meatType))
			sandwichName = TFC_Core.translate(CWTFCItems.VenisonSteakSandwich.getUnlocalizedName() + ".name");
		else if(topToast == 1)
			sandwichName = TFC_Core.translate(CWTFCItems.ToastSandwich.getUnlocalizedName() + ".name");
		
		currenttip.set(0, EnumChatFormatting.WHITE.toString() + breadName + " " + sandwichName);

		return currenttip;
	}
	
	public List<String> nestBoxBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		ItemStack storage[] = getStorage(tag, accessor.getTileEntity());
		int eggCount = 0, fertEggCount = 0;

		for (ItemStack is : storage)
		{
			if (is != null && is.getItem() == CWTFCItems.eggCWTFC)
			{
				if (is.hasTagCompound() && is.getTagCompound().hasKey("Fertilized"))
					fertEggCount++;
				else
					eggCount++;
			}
		}

		if (eggCount > 0)
			currenttip.add(TFC_Core.translate("gui.eggs") + " : " + eggCount);
		if (fertEggCount > 0)
			currenttip.add(TFC_Core.translate("gui.fertEggs") + " : " + fertEggCount);

		return currenttip;
	}
	
	private ItemStack[] getStorage(NBTTagCompound tag, TileEntity te)
	{
		if (te instanceof IInventory)
		{
			IInventory inv = (IInventory) te;
			NBTTagList tagList = tag.getTagList("Items", 10);
			ItemStack storage[] = new ItemStack[inv.getSizeInventory()];
			for (int i = 0; i < tagList.tagCount(); i++)
			{
				NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
				byte slot = itemTag.getByte("Slot");
				if (slot >= 0 && slot < storage.length)
					storage[slot] = ItemStack.loadItemStackFromNBT(itemTag);
			}

			return storage;
		}

		return null;
	}
	
	public List<String> cookingPotBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();		
		
		int time = (int)((tag.getInteger("cookTimer")/(float)TFC_Time.HOUR_LENGTH) * 60);
		
		if(time > 0)
			currenttip.add(TFC_Core.translate("gui.cookingTime") + ": " + time + " minutes");

		return currenttip;
	}
}
