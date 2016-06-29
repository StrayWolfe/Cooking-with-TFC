package com.JAWolfe.cookingwithtfc.handlers;

import java.util.Random;

import com.JAWolfe.cookingwithtfc.core.CWTFC_Core;
import com.JAWolfe.cookingwithtfc.core.FoodRecord;
import com.JAWolfe.cookingwithtfc.init.CWTFCBlocks;
import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.JAWolfe.cookingwithtfc.items.ItemTFCFoodTransform;
import com.bioxx.tfc.Blocks.Devices.BlockFoodPrep;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
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
		Item droppedItem = event.item.getEntityItem().getItem();

		if(droppedItem != null && droppedItem instanceof ItemFoodTFC && !(droppedItem instanceof ItemTFCFoodTransform))
		{						
			if(droppedItem == TFCItems.redApple) droppedItem = CWTFCItems.redAppleCWTFC;
			else if(droppedItem == TFCItems.banana) droppedItem = CWTFCItems.bananaCWTFC;
			else if(droppedItem == TFCItems.orange) droppedItem = CWTFCItems.orangeCWTFC;
			else if(droppedItem == TFCItems.greenApple) droppedItem = CWTFCItems.greenAppleCWTFC;
			else if(droppedItem == TFCItems.lemon) droppedItem = CWTFCItems.lemonCWTFC;
			else if(droppedItem == TFCItems.olive) droppedItem = CWTFCItems.oliveCWTFC;
			else if(droppedItem == TFCItems.cherry) droppedItem = CWTFCItems.cherryCWTFC;
			else if(droppedItem == TFCItems.peach) droppedItem = CWTFCItems.peachCWTFC;
			else if(droppedItem == TFCItems.plum) droppedItem = CWTFCItems.plumCWTFC;
			else if(droppedItem == TFCItems.wintergreenBerry) droppedItem = CWTFCItems.wintergreenBerryCWTFC;
			else if(droppedItem == TFCItems.blueberry) droppedItem = CWTFCItems.blueberryCWTFC;
			else if(droppedItem == TFCItems.raspberry) droppedItem = CWTFCItems.raspberryCWTFC;
			else if(droppedItem == TFCItems.strawberry) droppedItem = CWTFCItems.strawberryCWTFC;
			else if(droppedItem == TFCItems.blackberry) droppedItem = CWTFCItems.blackberryCWTFC;
			else if(droppedItem == TFCItems.bunchberry) droppedItem = CWTFCItems.bunchberryCWTFC;
			else if(droppedItem == TFCItems.cranberry) droppedItem = CWTFCItems.cranberryCWTFC;
			else if(droppedItem == TFCItems.snowberry) droppedItem = CWTFCItems.snowberryCWTFC;
			else if(droppedItem == TFCItems.elderberry) droppedItem = CWTFCItems.elderberryCWTFC;
			else if(droppedItem == TFCItems.gooseberry) droppedItem = CWTFCItems.gooseberryCWTFC;
			else if(droppedItem == TFCItems.cloudberry) droppedItem = CWTFCItems.cloudberryCWTFC;
			else if(droppedItem == TFCItems.tomato) droppedItem = CWTFCItems.tomatoCWTFC;
			else if(droppedItem == TFCItems.potato) droppedItem = CWTFCItems.potatoCWTFC;
			else if(droppedItem == TFCItems.onion) droppedItem = CWTFCItems.onionCWTFC;
			else if(droppedItem == TFCItems.cabbage) droppedItem = CWTFCItems.cabbageCWTFC;
			else if(droppedItem == TFCItems.garlic) droppedItem = CWTFCItems.garlicCWTFC;
			else if(droppedItem == TFCItems.carrot) droppedItem = CWTFCItems.carrotCWTFC;
			else if(droppedItem == TFCItems.greenbeans) droppedItem = CWTFCItems.greenbeansCWTFC;
			else if(droppedItem == TFCItems.greenBellPepper) droppedItem = CWTFCItems.greenBellPepperCWTFC;
			else if(droppedItem == TFCItems.yellowBellPepper) droppedItem = CWTFCItems.yellowBellPepperCWTFC;
			else if(droppedItem == TFCItems.redBellPepper) droppedItem = CWTFCItems.redBellPepperCWTFC;
			else if(droppedItem == TFCItems.squash) droppedItem = CWTFCItems.squashCWTFC;
			else if(droppedItem == TFCItems.seaWeed) droppedItem = CWTFCItems.seaWeedCWTFC;
			else if(droppedItem == TFCItems.porkchopRaw) droppedItem = CWTFCItems.porkchopRawCWTFC;
			else if(droppedItem == TFCItems.fishRaw) droppedItem = CWTFCItems.fishRawCWTFC;
			else if(droppedItem == TFCItems.beefRaw) droppedItem = CWTFCItems.beefRawCWTFC;
			else if(droppedItem == TFCItems.chickenRaw) droppedItem = CWTFCItems.chickenRawCWTFC;
			else if(droppedItem == TFCItems.soybean) droppedItem = CWTFCItems.soybeanCWTFC;
			else if(droppedItem == TFCItems.egg) droppedItem = CWTFCItems.eggCWTFC;
			else if(droppedItem == TFCItems.eggCooked) droppedItem = CWTFCItems.eggCookedCWTFC;
			else if(droppedItem == TFCItems.calamariRaw) droppedItem = CWTFCItems.calamariRawCWTFC;
			else if(droppedItem == TFCItems.muttonRaw) droppedItem = CWTFCItems.muttonRawCWTFC;
			else if(droppedItem == TFCItems.venisonRaw) droppedItem = CWTFCItems.venisonRawCWTFC;
			else if(droppedItem == TFCItems.horseMeatRaw) droppedItem = CWTFCItems.horseMeatRawCWTFC;
			else if(droppedItem == TFCItems.cheese) droppedItem = CWTFCItems.cheeseCWTFC;
			else if(droppedItem == TFCItems.barleyWhole) droppedItem = CWTFCItems.barleyWholeCWTFC;
			else if(droppedItem == TFCItems.barleyGrain) droppedItem = CWTFCItems.barleyGrainCWTFC;
			else if(droppedItem == TFCItems.barleyGround) droppedItem = CWTFCItems.barleyGroundCWTFC;
			else if(droppedItem == TFCItems.barleyDough) droppedItem = CWTFCItems.barleyDoughCWTFC;
			else if(droppedItem == TFCItems.barleyBread) droppedItem = CWTFCItems.barleyBreadCWTFC;
			else if(droppedItem == TFCItems.oatWhole) droppedItem = CWTFCItems.oatWholeCWTFC;
			else if(droppedItem == TFCItems.oatGrain) droppedItem = CWTFCItems.oatGrainCWTFC;
			else if(droppedItem == TFCItems.oatGround) droppedItem = CWTFCItems.oatGroundCWTFC;
			else if(droppedItem == TFCItems.oatDough) droppedItem = CWTFCItems.oatDoughCWTFC;
			else if(droppedItem == TFCItems.oatBread) droppedItem = CWTFCItems.oatBreadCWTFC;
			else if(droppedItem == TFCItems.riceWhole) droppedItem = CWTFCItems.riceWholeCWTFC;
			else if(droppedItem == TFCItems.riceGrain) droppedItem = CWTFCItems.riceGrainCWTFC;
			else if(droppedItem == TFCItems.riceGround) droppedItem = CWTFCItems.riceGroundCWTFC;
			else if(droppedItem == TFCItems.riceDough) droppedItem = CWTFCItems.riceDoughCWTFC;
			else if(droppedItem == TFCItems.riceBread) droppedItem = CWTFCItems.riceBreadCWTFC;
			else if(droppedItem == TFCItems.ryeWhole) droppedItem = CWTFCItems.ryeWholeCWTFC;
			else if(droppedItem == TFCItems.ryeGrain) droppedItem = CWTFCItems.ryeGrainCWTFC;
			else if(droppedItem == TFCItems.ryeGround) droppedItem = CWTFCItems.ryeGroundCWTFC;
			else if(droppedItem == TFCItems.ryeDough) droppedItem = CWTFCItems.ryeDoughCWTFC;
			else if(droppedItem == TFCItems.ryeBread) droppedItem = CWTFCItems.ryeBreadCWTFC;
			else if(droppedItem == TFCItems.wheatWhole) droppedItem = CWTFCItems.wheatWholeCWTFC;
			else if(droppedItem == TFCItems.wheatGrain) droppedItem = CWTFCItems.wheatGrainCWTFC;
			else if(droppedItem == TFCItems.wheatGround) droppedItem = CWTFCItems.wheatGroundCWTFC;
			else if(droppedItem == TFCItems.wheatDough) droppedItem = CWTFCItems.wheatDoughCWTFC;
			else if(droppedItem == TFCItems.wheatBread) droppedItem = CWTFCItems.wheatBreadCWTFC;
			else if(droppedItem == TFCItems.maizeEar) droppedItem = CWTFCItems.maizeEarCWTFC;
			else if(droppedItem == TFCItems.cornmealGround) droppedItem = CWTFCItems.cornmealGroundCWTFC;
			else if(droppedItem == TFCItems.cornmealDough) droppedItem = CWTFCItems.cornmealDoughCWTFC;
			else if(droppedItem == TFCItems.cornBread) droppedItem = CWTFCItems.cornBreadCWTFC;
			else if(droppedItem == TFCItems.sugarcane) droppedItem = CWTFCItems.sugarcaneCWTFC;
			
			EntityPlayer player = event.entityPlayer;
			event.item.delayBeforeCanPickup = 100;
			event.item.setDead();
			event.item.setInvisible(true);
			Random rand = player.worldObj.rand;
			player.worldObj.playSoundAtEntity(player, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			
			ItemStack is = event.item.getEntityItem();
			ItemStack food = ItemFoodTFC.createTag(new ItemStack(droppedItem, 1, is.getItemDamage()), Food.getWeight(is), Food.getDecay(is));
			Food.setSweetMod(food, Food.getSweetMod(is));
			Food.setSourMod(food, Food.getSourMod(is));
			Food.setSaltyMod(food, Food.getSaltyMod(is));
			Food.setBitterMod(food, Food.getBitterMod(is));
			Food.setSavoryMod(food, Food.getSavoryMod(is));
			
			if(Food.isBrined(is))
				Food.setBrined(food, true);
			if(Food.isPickled(is))
				Food.setPickled(food, true);
			if(Food.isSalted(is))
				Food.setSalted(food, true);
			if(Food.getCooked(is) != 0.0)
				Food.setCooked(food, Food.getCooked(is));
			if(!Food.getCookedProfile(is).equals(new int[] {0,0,0,0,0}))
				Food.setCookedProfile(food, Food.getCookedProfile(is));
			if(!Food.getFuelProfile(is).equals(new int[] {0,0,0,0,0}))
				Food.setFuelProfile(food, Food.getFuelProfile(is));
			if(Food.getDried(is) != 0)
				Food.setDried(food, Food.getDried(is));
			if(Food.isInfused(is))
				Food.setInfusion(food, Food.getInfusion(is));
			if(Food.isSmoked(is))
				Food.setSmokeCounter(food, Food.getSmokeCounter(is));
			
			player.inventory.addItemStackToInventory(food);
		}
	}
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.entityPlayer.worldObj.isRemote)
			return;

		ItemStack itemInHand = event.entityPlayer.getCurrentEquippedItem();
		
		//Remove existing FoodPrep surfaces
		if(event.action == Action.RIGHT_CLICK_BLOCK && event.world.getBlock(event.x, event.y, event.z) instanceof BlockFoodPrep)
		{
			Block block = event.world.getBlock(event.x, event.y, event.z);
			int meta = event.world.getBlockMetadata(event.x, event.y, event.z);
			block.breakBlock(event.world, event.x, event.y, event.z, block, meta);
			event.world.setBlockToAir(event.x, event.y, event.z);
			event.setCanceled(true);			
		}
		
		if(itemInHand == null)
			return;

		//Prevent new Foodprep surfaces from being made
		if(event.action == Action.RIGHT_CLICK_BLOCK && event.getResult() != Result.DENY 
				&& itemInHand.getItem() instanceof ItemKnife)
		{
			Block id = event.world.getBlock(event.x, event.y, event.z);
			if(!event.world.isRemote && id != TFCBlocks.toolRack)
			{
				Material mat = id.getMaterial();
				if(event.face == 1 && id.isSideSolid(event.world, event.x, event.y, event.z, ForgeDirection.UP) &&!TFC_Core.isSoil(id) && !TFC_Core.isWater(id) && event.world.isAirBlock(event.x, event.y + 1, event.z) &&
						(mat == Material.wood || mat == Material.rock || mat == Material.iron))
					event.setCanceled(true);
			}
		}
		
		//Replace TFC hopper with CWTFC hopper
		if(event.action == Action.RIGHT_CLICK_BLOCK && event.getResult() != Result.DENY &&
				event.entityPlayer.getCurrentEquippedItem().getUnlocalizedName().equals(TFCBlocks.hopper.getUnlocalizedName()))
		{
			event.setCanceled(true);
			switch(event.face)
			{
				case 0: event.world.setBlock(event.x, event.y - 1, event.z, CWTFCBlocks.hopperCWTFC, 0, 3); break;
				case 1: event.world.setBlock(event.x, event.y + 1, event.z, CWTFCBlocks.hopperCWTFC, 0, 3); break;
				case 2: event.world.setBlock(event.x, event.y, event.z - 1, CWTFCBlocks.hopperCWTFC, 0, 3); break;
				case 3: event.world.setBlock(event.x, event.y, event.z + 1, CWTFCBlocks.hopperCWTFC, 0, 3); break;
				case 4: event.world.setBlock(event.x - 1, event.y, event.z, CWTFCBlocks.hopperCWTFC, 0, 3); break;
				case 5: event.world.setBlock(event.x + 1, event.y, event.z, CWTFCBlocks.hopperCWTFC, 0, 3); break;
				default: break;
			}			
			
			event.entityPlayer.setCurrentItemOrArmor(0, null);
		}
	}
	
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event)
	{
		EntityLivingBase entity = event.entityLiving;

		//Reset eaten foods after death
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
