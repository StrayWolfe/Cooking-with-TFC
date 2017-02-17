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
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.api.managers.CWTFCCropIndex;
import straywolfe.cookingwithtfc.api.managers.CropManager;
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
		
		reg.registerStackProvider(new WAILAInfo(), TileGourd.class);
		reg.registerHeadProvider(new WAILAInfo(), TileGourd.class);
		
		reg.registerStackProvider(new WAILAInfo(), TileCrop.class);
		reg.registerHeadProvider(new WAILAInfo(), TileCrop.class);
		reg.registerBodyProvider(new WAILAInfo(), TileCrop.class);
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
		else if(tileEntity instanceof TileGourd)
			return gourdStack(accessor, config);
		else if(tileEntity instanceof TileCrop)
			return cropStack(accessor, config);
			
		return accessor.getStack();
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) 
	{
		Block block = accessor.getBlock();
		TileEntity tileEntity = accessor.getTileEntity();
		
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
		else if(tileEntity instanceof TileGourd)
			currenttip = gourdHead(itemStack, currenttip, accessor, config);
		else if(tileEntity instanceof TileCrop)
			currenttip = cropHead(itemStack, currenttip, accessor, config);
		
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
		else if(tileEntity instanceof TileCrop)
			currenttip = cropBody(itemStack, currenttip, accessor, config);
		
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
	
	public List<String> mixBowlHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		currenttip.set(0, EnumChatFormatting.WHITE.toString() + itemStack.getDisplayName());

		return currenttip;
	}
	
	public ItemStack prepTableStack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return new ItemStack(accessor.getBlock(), 1, accessor.getMetadata());
	}
	
	public List<String> prepTableHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		currenttip.set(0, EnumChatFormatting.WHITE.toString() + itemStack.getDisplayName());

		return currenttip;
	}
	
	public ItemStack prepTable2Stack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return new ItemStack(accessor.getBlock(), 1, accessor.getMetadata());
	}
	
	public List<String> prepTable2Head(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
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
			return ItemFoodTFC.createTag(new ItemStack(TFCItems.beefRaw), 999, -24);
	}
	
	public List<String> meatHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		ItemStack meat = ((TileMeat)accessor.getTileEntity()).getplacedMeat();
		
		if(meat == null)
			currenttip.set(0, EnumChatFormatting.WHITE.toString() + "Meat");
		else
			currenttip.set(0, EnumChatFormatting.WHITE.toString() + meat.getDisplayName());

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
	
	public List<String> bowlHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
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
		ItemStack grain = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("placedGrain"));
		
		if(grain == null)
			grain = ItemFoodTFC.createTag(new ItemStack(TFCItems.wheatWhole));
		
		return grain;
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
		
		if(TFCItems.cornBread.getUnlocalizedName().equals(breadType))
			breadMeta = 1;
		else if(TFCItems.oatBread.getUnlocalizedName().equals(breadType))
			breadMeta = 2;
		else if(TFCItems.riceBread.getUnlocalizedName().equals(breadType))
			breadMeta = 3;
		else if(TFCItems.ryeBread.getUnlocalizedName().equals(breadType))
			breadMeta = 4;
		else if(TFCItems.wheatBread.getUnlocalizedName().equals(breadType))
			breadMeta = 5;
		
		ItemStack stack = ItemTFCMealTransform.createTag(new ItemStack(CWTFCItems.ChickenSandwich, 1, breadMeta), 999, -24, new ItemStack[]{}, new float[]{});
		
		return stack;
	}
	
	public List<String> sandwichHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		NBTTagCompound tag = accessor.getNBTData();
		String breadType = tag.getString("breadType");
		String meatType = tag.getString("meatType");
		String breadName = TFC_Core.translate("word.Barley");
		String sandwichName = TFC_Core.translate("word.Sandwich");
		int topToast = tag.getInteger("topToast");
		
		if(TFCItems.cornBread.getUnlocalizedName().equals(breadType))
			breadName = TFC_Core.translate("word.Corn");
		else if(TFCItems.oatBread.getUnlocalizedName().equals(breadType))
			breadName = TFC_Core.translate("word.Oat");
		else if(TFCItems.riceBread.getUnlocalizedName().equals(breadType))
			breadName = TFC_Core.translate("word.Rice");
		else if(TFCItems.ryeBread.getUnlocalizedName().equals(breadType))
			breadName = TFC_Core.translate("word.Rye");
		else if(TFCItems.wheatBread.getUnlocalizedName().equals(breadType))
			breadName = TFC_Core.translate("word.Wheat");
		
		if(TFCItems.chickenRaw.getUnlocalizedName().equals(meatType) || CWTFCItems.BoiledChicken.getUnlocalizedName().equals(meatType))
			sandwichName = TFC_Core.translate(CWTFCItems.ChickenSandwich.getUnlocalizedName() + ".name");
		else if(TFCItems.porkchopRaw.getUnlocalizedName().equals(meatType) || CWTFCItems.BoiledPork.getUnlocalizedName().equals(meatType))
			sandwichName = TFC_Core.translate(CWTFCItems.HamSandwich.getUnlocalizedName() + ".name");
		else if(TFCItems.eggCooked.getUnlocalizedName().equals(meatType))
			sandwichName = TFC_Core.translate(CWTFCItems.FriedEggSandwich.getUnlocalizedName() + ".name");
		else if(TFCItems.muttonRaw.getUnlocalizedName().equals(meatType))
			sandwichName = TFC_Core.translate(CWTFCItems.MuttonSandwich.getUnlocalizedName() + ".name");
		else if(TFCItems.beefRaw.getUnlocalizedName().equals(meatType) || CWTFCItems.BoiledBeef.getUnlocalizedName().equals(meatType))
			sandwichName = TFC_Core.translate(CWTFCItems.RoastBeefSandwich.getUnlocalizedName() + ".name");
		else if(TFCItems.fishRaw.getUnlocalizedName().equals(meatType) || CWTFCItems.BoiledFish.getUnlocalizedName().equals(meatType) ||
				TFCItems.calamariRaw.getUnlocalizedName().equals(meatType))
			sandwichName = TFC_Core.translate(CWTFCItems.SalmonSandwich.getUnlocalizedName() + ".name");
		else if(TFCItems.venisonRaw.getUnlocalizedName().equals(meatType) || CWTFCItems.BoiledVenison.getUnlocalizedName().equals(meatType) ||
				TFCItems.horseMeatRaw.getUnlocalizedName().equals(meatType))
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
	
	public ItemStack gourdStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		int type = tag.getInteger("type");
		
		switch(type)
		{
			case 0: return new ItemStack(CWTFCItems.pumpkinBlock);
			case 1: return new ItemStack(CWTFCItems.melonBlock);
			case 2: return new ItemStack(CWTFCItems.jackolanternBlock);
			default: return new ItemStack(CWTFCItems.pumpkinBlock);
		}
	}
	
	public List<String> gourdHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		NBTTagCompound tag = accessor.getNBTData();
		
		int type = tag.getInteger("type");
		String gourdName = "";
		
		switch(type)
		{
			case 1: gourdName = StatCollector.translateToLocal("item.Watermelon.name"); break;
			case 2: gourdName = StatCollector.translateToLocal("item.JackOLantern.name"); break;
			default: gourdName = StatCollector.translateToLocal("item.Pumpkin.name"); break;
		}
		
		currenttip.set(0, EnumChatFormatting.WHITE.toString() + gourdName);

		return currenttip;
	}
	
	public ItemStack cropStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		int cropID = tag.getInteger("cropID");
		CWTFCCropIndex crop = CropManager.getInstance().getCropFromId(cropID);
		
		if(crop != null)
			return ItemFoodTFC.createTag(new ItemStack(crop.output1));
		else
			return ItemFoodTFC.createTag(new ItemStack(CWTFCItems.watermelon));
	}
	
	public List<String> cropHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		NBTTagCompound tag = accessor.getNBTData();
		
		int cropID = tag.getInteger("cropID");		
		CWTFCCropIndex crop = CropManager.getInstance().getCropFromId(cropID);
		if(crop != null)
		{
			String cropName = StatCollector.translateToLocal("item." + crop.cropName + ".name");
		
			currenttip.set(0, EnumChatFormatting.WHITE.toString() + cropName);
		}

		return currenttip;
	}
	
	public List<String> cropBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		float growth = tag.getFloat("growth");
		int cropID = tag.getInteger("cropID");
		CWTFCCropIndex crop = CropManager.getInstance().getCropFromId(cropID);
		int percentGrowth = (int) Math.min((growth / (crop.numGrowthStages - 1)) * 100, 100);
		
		if (percentGrowth < 100)
			currenttip.add(TFC_Core.translate("gui.growth") + " : " + percentGrowth + "%");
		else
			currenttip.add(TFC_Core.translate("gui.growth") + " : " + TFC_Core.translate("gui.mature"));
		
		return currenttip;
	}
}
