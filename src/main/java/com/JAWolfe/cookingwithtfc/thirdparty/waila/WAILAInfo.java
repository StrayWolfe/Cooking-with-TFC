package com.JAWolfe.cookingwithtfc.thirdparty.waila;

import java.util.List;

import com.JAWolfe.cookingwithtfc.blocks.blockMixBowl;
import com.JAWolfe.cookingwithtfc.blocks.blockPrepTable;
import com.JAWolfe.cookingwithtfc.blocks.blockPrepTable2;
import com.JAWolfe.cookingwithtfc.init.CWTFCBlocks;
import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.JAWolfe.cookingwithtfc.tileentities.TEGrains;
import com.JAWolfe.cookingwithtfc.tileentities.TEMixBowl;
import com.JAWolfe.cookingwithtfc.tileentities.TENestBoxCWTFC;
import com.JAWolfe.cookingwithtfc.tileentities.TEPrepTable;
import com.bioxx.tfc.Core.TFC_Core;

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

public class WAILAInfo implements IWailaDataProvider
{
	public static void callbackRegister(IWailaRegistrar reg)
	{
		reg.registerStackProvider(new WAILAInfo(), TEGrains.class);
		reg.registerBodyProvider(new WAILAInfo(), TEGrains.class);
		reg.registerNBTProvider(new WAILAInfo(), TEGrains.class);
		
		reg.registerStackProvider(new WAILAInfo(), blockMixBowl.class);
		reg.registerHeadProvider(new WAILAInfo(), blockMixBowl.class);
		reg.registerNBTProvider(new WAILAInfo(), TEMixBowl.class);
		
		reg.registerStackProvider(new WAILAInfo(), blockPrepTable.class);
		reg.registerHeadProvider(new WAILAInfo(), blockPrepTable.class);
		reg.registerNBTProvider(new WAILAInfo(), TEPrepTable.class);
		
		reg.registerStackProvider(new WAILAInfo(), blockPrepTable2.class);
		reg.registerHeadProvider(new WAILAInfo(), blockPrepTable2.class);
		reg.registerNBTProvider(new WAILAInfo(), TEPrepTable.class);
		
		reg.registerBodyProvider(new WAILAInfo(), TENestBoxCWTFC.class);
		reg.registerNBTProvider(new WAILAInfo(), TENestBoxCWTFC.class);
	}
	
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		Block block = accessor.getBlock();
		TileEntity tileEntity = accessor.getTileEntity();
		
		if(tileEntity instanceof TEGrains)
			return grainStack(accessor, config);
		else if(block instanceof blockMixBowl)
			return mixBowlStack(accessor, config);
		else if(block instanceof blockPrepTable)
			return prepTableStack(accessor, config);
		else if(block instanceof blockPrepTable2)
			return prepTable2Stack(accessor, config);
			
		return accessor.getStack();
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) 
	{
		Block block = accessor.getBlock();
		
		if(block instanceof blockMixBowl)
			currenttip = mixBowlHead(itemStack, currenttip, accessor, config);
		else if(block instanceof blockPrepTable)
			currenttip = prepTableHead(itemStack, currenttip, accessor, config);
		else if(block instanceof blockPrepTable2)
			currenttip = prepTable2Head(itemStack, currenttip, accessor, config);
		
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		TileEntity tileEntity = accessor.getTileEntity();
		
		if (tileEntity instanceof TEGrains)
			currenttip = grainBody(itemStack, currenttip, accessor, config);
		else if (tileEntity instanceof TENestBoxCWTFC)
			currenttip = nestBoxBody(itemStack, currenttip, accessor, config);
		
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
	
	public ItemStack grainStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		return ItemStack.loadItemStackFromNBT(tag.getCompoundTag("placedGrain"));
	}
	
	public ItemStack mixBowlStack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return new ItemStack(CWTFCBlocks.mixingBowl, 1, 1);
	}
	
	public ItemStack prepTableStack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return new ItemStack(accessor.getBlock(), 1, accessor.getMetadata());
	}
	
	public ItemStack prepTable2Stack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return new ItemStack(accessor.getBlock(), 1, accessor.getMetadata());
	}
	
	private List<String> mixBowlHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		currenttip.set(0, EnumChatFormatting.WHITE.toString() + itemStack.getDisplayName());

		return currenttip;
	}
	
	private List<String> prepTableHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		currenttip.set(0, EnumChatFormatting.WHITE.toString() + itemStack.getDisplayName());

		return currenttip;
	}
	
	private List<String> prepTable2Head(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		currenttip.set(0, EnumChatFormatting.WHITE.toString() + itemStack.getDisplayName());

		return currenttip;
	}

	public List<String> grainBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		float progressCount = tag.getInteger("WorkCounter");
		
		int progress = (int) Math.min((progressCount / 20) * 100, 100);
		currenttip.add(TFC_Core.translate("gui.progress") + ": " + progress + "%");

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
}
