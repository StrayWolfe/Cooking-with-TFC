package com.JAWolfe.cookingwithtfc.handlers;

import com.JAWolfe.cookingwithtfc.core.CWTFC_Core;
import com.JAWolfe.cookingwithtfc.core.FoodRecord;
import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class PlayerHandler 
{	
	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event)
	{
		EntityItem item = event.item;
		ItemStack is = item.getEntityItem();
		EntityPlayer player = event.entityPlayer;
		Item droppedItem = is.getItem();

		if(droppedItem instanceof ItemFoodTFC)
		{						
			if(droppedItem == TFCItems.redApple)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.redAppleCWTFC);
			else if(droppedItem == TFCItems.banana)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.bananaCWTFC);
			else if(droppedItem == TFCItems.orange)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.orangeCWTFC);
			else if(droppedItem == TFCItems.greenApple)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.greenAppleCWTFC);
			else if(droppedItem == TFCItems.lemon)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.lemonCWTFC);
			else if(droppedItem == TFCItems.cherry)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.cherryCWTFC);
			else if(droppedItem == TFCItems.peach)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.peachCWTFC);
			else if(droppedItem == TFCItems.plum)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.plumCWTFC);
			else if(droppedItem == TFCItems.wintergreenBerry)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.wintergreenBerryCWTFC);
			else if(droppedItem == TFCItems.blueberry)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.blueberryCWTFC);
			else if(droppedItem == TFCItems.raspberry)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.raspberryCWTFC);
			else if(droppedItem == TFCItems.strawberry)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.strawberryCWTFC);
			else if(droppedItem == TFCItems.blackberry)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.blackberryCWTFC);
			else if(droppedItem == TFCItems.bunchberry)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.bunchberryCWTFC);
			else if(droppedItem == TFCItems.cranberry)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.cranberryCWTFC);
			else if(droppedItem == TFCItems.snowberry)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.snowberryCWTFC);
			else if(droppedItem == TFCItems.elderberry)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.elderberryCWTFC);
			else if(droppedItem == TFCItems.gooseberry)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.gooseberryCWTFC);
			else if(droppedItem == TFCItems.cloudberry)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.cloudberryCWTFC);
			else if(droppedItem == TFCItems.tomato)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.tomatoCWTFC);
			else if(droppedItem == TFCItems.potato)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.potatoCWTFC);
			else if(droppedItem == TFCItems.onion)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.onionCWTFC);
			else if(droppedItem == TFCItems.cabbage)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.cabbageCWTFC);
			else if(droppedItem == TFCItems.garlic)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.garlicCWTFC);
			else if(droppedItem == TFCItems.carrot)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.carrotCWTFC);
			else if(droppedItem == TFCItems.greenbeans)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.greenbeansCWTFC);
			else if(droppedItem == TFCItems.greenBellPepper)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.greenBellPepperCWTFC);
			else if(droppedItem == TFCItems.yellowBellPepper)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.yellowBellPepperCWTFC);
			else if(droppedItem == TFCItems.redBellPepper)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.redBellPepperCWTFC);
			else if(droppedItem == TFCItems.squash)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.squashCWTFC);
			else if(droppedItem == TFCItems.seaWeed)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.seaWeedCWTFC);
			else if(droppedItem == TFCItems.porkchopRaw)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.porkchopRawCWTFC);
			else if(droppedItem == TFCItems.fishRaw)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.fishRawCWTFC);
			else if(droppedItem == TFCItems.beefRaw)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.beefRawCWTFC);
			else if(droppedItem == TFCItems.chickenRaw)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.chickenRawCWTFC);
			else if(droppedItem == TFCItems.soybean)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.soybeanCWTFC);
			else if(droppedItem == TFCItems.eggCooked)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.eggCookedCWTFC);
			else if(droppedItem == TFCItems.calamariRaw)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.calamariRawCWTFC);
			else if(droppedItem == TFCItems.muttonRaw)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.muttonRawCWTFC);
			else if(droppedItem == TFCItems.venisonRaw)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.venisonRawCWTFC);
			else if(droppedItem == TFCItems.horseMeatRaw)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.horseMeatRawCWTFC);
			else if(droppedItem == TFCItems.barleyWhole)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.barleyWholeCWTFC);
			else if(droppedItem == TFCItems.oatWhole)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.oatWholeCWTFC);
			else if(droppedItem == TFCItems.riceWhole)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.riceWholeCWTFC);
			else if(droppedItem == TFCItems.ryeWhole)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.ryeWholeCWTFC);
			else if(droppedItem == TFCItems.wheatWhole)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.wheatWholeCWTFC);
			else if(droppedItem == TFCItems.maizeEar)
				CWTFC_Core.handleFoodItemPickups(item, player, is, CWTFCItems.maizeEarCWTFC);
		}
	}
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.entityPlayer.worldObj.isRemote)
			return;

		ItemStack itemInHand = event.entityPlayer.getCurrentEquippedItem();
		
		if(itemInHand == null)
			return;

		boolean validAction = event.action == Action.RIGHT_CLICK_BLOCK;

		if(validAction && event.getResult() != Result.DENY && itemInHand.getItem() instanceof ItemKnife)
		{
			Block id = event.world.getBlock(event.x, event.y, event.z);
			if(!event.world.isRemote && id != TFCBlocks.toolRack)
			{
				Material mat = id.getMaterial();
				if(event.face == 1 && id.isSideSolid(event.world, event.x, event.y, event.z, ForgeDirection.UP) &&!TFC_Core.isSoil(id) && !TFC_Core.isWater(id) && event.world.isAirBlock(event.x, event.y + 1, event.z) &&
						(mat == Material.wood || mat == Material.rock || mat == Material.iron))
				{
					event.setCanceled(true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event)
	{
		EntityLivingBase entity = event.entityLiving;

		if (entity instanceof EntityPlayer)
		{
			FoodRecord fr = CWTFC_Core.getPlayerFoodRecord((EntityPlayer)entity);
			for(int i = 0; i < fr.FoodsEaten.length; i++)
			{
				fr.FoodsEaten[i] = null;
			}
			fr.FoodListRef = 0;
			CWTFC_Core.setPlayerFoodRecord((EntityPlayer)entity, fr);
		}
	}
}
