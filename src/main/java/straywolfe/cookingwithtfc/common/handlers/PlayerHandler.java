package straywolfe.cookingwithtfc.common.handlers;

import java.util.ArrayList;
import java.util.Random;

import com.bioxx.tfc.Blocks.Devices.BlockFoodPrep;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.core.CWTFC_Core;
import straywolfe.cookingwithtfc.common.core.FoodRecord;
import straywolfe.cookingwithtfc.common.item.ItemTFCFoodTransform;
import straywolfe.cookingwithtfc.common.item.ItemTFCMeatTransform;
import straywolfe.cookingwithtfc.common.item.ItemTFCSalt;
import straywolfe.cookingwithtfc.common.lib.Settings;
import straywolfe.cookingwithtfc.common.tileentity.TileGrains;

public class PlayerHandler 
{	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event)
	{
		EntityPlayer player = event.entityPlayer;

		if(player.inventory.getFirstEmptyStack() != -1)
		{
			ItemStack is = event.item.getEntityItem();
			Item droppedItem = is.getItem();
	
			if(droppedItem != null && (droppedItem instanceof ItemTFCFoodTransform || droppedItem == TFCItems.powder ||
					droppedItem == CWTFCItems.eggCWTFC || droppedItem == CWTFCItems.woodenBucketMilkCWTFC)
					&& !(droppedItem instanceof ItemTFCSalt))
			{						
				if(droppedItem == CWTFCItems.redAppleCWTFC) droppedItem = TFCItems.redApple;
				else if(droppedItem == CWTFCItems.bananaCWTFC) droppedItem = TFCItems.banana;
				else if(droppedItem == CWTFCItems.orangeCWTFC) droppedItem = TFCItems.orange;
				else if(droppedItem == CWTFCItems.greenAppleCWTFC) droppedItem = TFCItems.greenApple;
				else if(droppedItem == CWTFCItems.lemonCWTFC) droppedItem = TFCItems.lemon;
				else if(droppedItem == CWTFCItems.oliveCWTFC) droppedItem = TFCItems.olive;
				else if(droppedItem == CWTFCItems.cherryCWTFC) droppedItem = TFCItems.cherry;
				else if(droppedItem == CWTFCItems.peachCWTFC) droppedItem = TFCItems.peach;
				else if(droppedItem == CWTFCItems.plumCWTFC) droppedItem = TFCItems.plum;
				else if(droppedItem == CWTFCItems.wintergreenBerryCWTFC) droppedItem = TFCItems.wintergreenBerry;
				else if(droppedItem == CWTFCItems.blueberryCWTFC) droppedItem = TFCItems.blueberry;
				else if(droppedItem == CWTFCItems.raspberryCWTFC) droppedItem = TFCItems.raspberry;
				else if(droppedItem == CWTFCItems.strawberryCWTFC) droppedItem = TFCItems.strawberry;
				else if(droppedItem == CWTFCItems.blackberryCWTFC) droppedItem = TFCItems.blackberry;
				else if(droppedItem == CWTFCItems.bunchberryCWTFC) droppedItem = TFCItems.bunchberry;
				else if(droppedItem == CWTFCItems.cranberryCWTFC) droppedItem = TFCItems.cranberry;
				else if(droppedItem == CWTFCItems.snowberryCWTFC) droppedItem = TFCItems.snowberry;
				else if(droppedItem == CWTFCItems.elderberryCWTFC) droppedItem = TFCItems.elderberry;
				else if(droppedItem == CWTFCItems.gooseberryCWTFC) droppedItem = TFCItems.gooseberry;
				else if(droppedItem == CWTFCItems.cloudberryCWTFC) droppedItem = TFCItems.cloudberry;
				else if(droppedItem == CWTFCItems.tomatoCWTFC) droppedItem = TFCItems.tomato;
				else if(droppedItem == CWTFCItems.potatoCWTFC) droppedItem = TFCItems.potato;
				else if(droppedItem == CWTFCItems.onionCWTFC) droppedItem = TFCItems.onion;
				else if(droppedItem == CWTFCItems.cabbageCWTFC) droppedItem = TFCItems.cabbage;
				else if(droppedItem == CWTFCItems.garlicCWTFC) droppedItem = TFCItems.garlic;
				else if(droppedItem == CWTFCItems.carrotCWTFC) droppedItem = TFCItems.carrot;
				else if(droppedItem == CWTFCItems.greenbeansCWTFC) droppedItem = TFCItems.greenbeans;
				else if(droppedItem == CWTFCItems.greenBellPepperCWTFC) droppedItem = TFCItems.greenBellPepper;
				else if(droppedItem == CWTFCItems.yellowBellPepperCWTFC) droppedItem = TFCItems.yellowBellPepper;
				else if(droppedItem == CWTFCItems.redBellPepperCWTFC) droppedItem = TFCItems.redBellPepper;
				else if(droppedItem == CWTFCItems.squashCWTFC) droppedItem = TFCItems.squash;
				else if(droppedItem == CWTFCItems.seaWeedCWTFC) droppedItem = TFCItems.seaWeed;				
				else if(droppedItem == CWTFCItems.cheeseCWTFC) droppedItem = TFCItems.cheese;
				else if(droppedItem == CWTFCItems.barleyWholeCWTFC) droppedItem = TFCItems.barleyWhole;
				else if(droppedItem == CWTFCItems.barleyGrainCWTFC) droppedItem = TFCItems.barleyGrain;
				else if(droppedItem == CWTFCItems.barleyGroundCWTFC) droppedItem = TFCItems.barleyGround;
				else if(droppedItem == CWTFCItems.barleyDoughCWTFC) droppedItem = TFCItems.barleyDough;
				else if(droppedItem == CWTFCItems.barleyBreadCWTFC) droppedItem = TFCItems.barleyBread;
				else if(droppedItem == CWTFCItems.oatWholeCWTFC) droppedItem = TFCItems.oatWhole;
				else if(droppedItem == CWTFCItems.oatGrainCWTFC) droppedItem = TFCItems.oatGrain;
				else if(droppedItem == CWTFCItems.oatGroundCWTFC) droppedItem = TFCItems.oatGround;
				else if(droppedItem == CWTFCItems.oatDoughCWTFC) droppedItem = TFCItems.oatDough;
				else if(droppedItem == CWTFCItems.oatBreadCWTFC) droppedItem = TFCItems.oatBread;
				else if(droppedItem == CWTFCItems.riceWholeCWTFC) droppedItem = TFCItems.riceWhole;
				else if(droppedItem == CWTFCItems.riceGrainCWTFC) droppedItem = TFCItems.riceGrain;
				else if(droppedItem == CWTFCItems.riceGroundCWTFC) droppedItem = TFCItems.riceGround;
				else if(droppedItem == CWTFCItems.riceDoughCWTFC) droppedItem = TFCItems.riceDough;
				else if(droppedItem == CWTFCItems.riceBreadCWTFC) droppedItem = TFCItems.riceBread;
				else if(droppedItem == CWTFCItems.ryeWholeCWTFC) droppedItem = TFCItems.ryeWhole;
				else if(droppedItem == CWTFCItems.ryeGrainCWTFC) droppedItem = TFCItems.ryeGrain;
				else if(droppedItem == CWTFCItems.ryeGroundCWTFC) droppedItem = TFCItems.ryeGround;
				else if(droppedItem == CWTFCItems.ryeDoughCWTFC) droppedItem = TFCItems.ryeDough;
				else if(droppedItem == CWTFCItems.ryeBreadCWTFC) droppedItem = TFCItems.ryeBread;
				else if(droppedItem == CWTFCItems.wheatWholeCWTFC) droppedItem = TFCItems.wheatWhole;
				else if(droppedItem == CWTFCItems.wheatGrainCWTFC) droppedItem = TFCItems.wheatGrain;
				else if(droppedItem == CWTFCItems.wheatGroundCWTFC) droppedItem = TFCItems.wheatGround;
				else if(droppedItem == CWTFCItems.wheatDoughCWTFC) droppedItem = TFCItems.wheatDough;
				else if(droppedItem == CWTFCItems.wheatBreadCWTFC) droppedItem = TFCItems.wheatBread;
				else if(droppedItem == CWTFCItems.maizeEarCWTFC) droppedItem = TFCItems.maizeEar;
				else if(droppedItem == CWTFCItems.cornmealGroundCWTFC) droppedItem = TFCItems.cornmealGround;
				else if(droppedItem == CWTFCItems.cornmealDoughCWTFC) droppedItem = TFCItems.cornmealDough;
				else if(droppedItem == CWTFCItems.cornBreadCWTFC) droppedItem = TFCItems.cornBread;
				else if(droppedItem == CWTFCItems.sugarcaneCWTFC) droppedItem = TFCItems.sugarcane;
				else if(droppedItem == CWTFCItems.sugarCWTFC) droppedItem = TFCItems.sugar;
				else if(droppedItem == CWTFCItems.soybeanCWTFC) droppedItem = TFCItems.soybean;
				else if(droppedItem == CWTFCItems.eggCWTFC) droppedItem = TFCItems.egg;
				else if(droppedItem == CWTFCItems.eggCookedCWTFC) droppedItem = TFCItems.eggCooked;
				else if(droppedItem == CWTFCItems.woodenBucketMilkCWTFC) droppedItem = TFCItems.woodenBucketMilk;
				else if(droppedItem == TFCItems.powder && is.getItemDamage() == 9) droppedItem = CWTFCItems.Salt;
				else if(droppedItem instanceof ItemTFCMeatTransform)
				{
					if(Food.isCooked(is))
					{
						if(droppedItem == CWTFCItems.porkchopCookedCWTFC) droppedItem = TFCItems.porkchopRaw;
						else if(droppedItem == CWTFCItems.fishCookedCWTFC) droppedItem = TFCItems.fishRaw;
						else if(droppedItem == CWTFCItems.beefCookedCWTFC) droppedItem = TFCItems.beefRaw;
						else if(droppedItem == CWTFCItems.chickenCookedCWTFC) droppedItem = TFCItems.chickenRaw;
						else if(droppedItem == CWTFCItems.calamariCookedCWTFC) droppedItem = TFCItems.calamariRaw;
						else if(droppedItem == CWTFCItems.muttonCookedCWTFC) droppedItem = TFCItems.muttonRaw;
						else if(droppedItem == CWTFCItems.venisonCookedCWTFC) droppedItem = TFCItems.venisonRaw;
						else if(droppedItem == CWTFCItems.horseMeatCookedCWTFC) droppedItem = TFCItems.horseMeatRaw;
					}
					else
					{
						if(droppedItem == CWTFCItems.porkchopRawCWTFC) droppedItem = TFCItems.porkchopRaw;
						else if(droppedItem == CWTFCItems.fishRawCWTFC) droppedItem = TFCItems.fishRaw;
						else if(droppedItem == CWTFCItems.beefRawCWTFC) droppedItem = TFCItems.beefRaw;
						else if(droppedItem == CWTFCItems.chickenRawCWTFC) droppedItem = TFCItems.chickenRaw;
						else if(droppedItem == CWTFCItems.calamariRawCWTFC) droppedItem = TFCItems.calamariRaw;
						else if(droppedItem == CWTFCItems.muttonRawCWTFC) droppedItem = TFCItems.muttonRaw;
						else if(droppedItem == CWTFCItems.venisonRawCWTFC) droppedItem = TFCItems.venisonRaw;
						else if(droppedItem == CWTFCItems.horseMeatRawCWTFC) droppedItem = TFCItems.horseMeatRaw;
					}
				}
				
				ItemStack food;
				boolean flag = false;
				
				if(droppedItem == CWTFCItems.Salt)
				{
					ArrayList<ItemStack> salts = new ArrayList();
					float wt = 8 * is.stackSize;
					
					while(wt > 0)
					{
						if(wt > Global.FOOD_MAX_WEIGHT)
						{
							salts.add(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.Salt), 160));
							wt -= 160;
						}
						else
						{
							salts.add(ItemFoodTFC.createTag(new ItemStack(CWTFCItems.Salt), wt));
							wt = 0;
						}
					}
					
					for(ItemStack salt : salts)
					{
						if(!player.inventory.addItemStackToInventory(salt))
						{
							EntityItem entityitem;
							if(salt != null)
							{
								entityitem = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, salt);
								entityitem.motionX = 0;
								entityitem.motionY = 0;
								entityitem.motionZ = 0;
								player.worldObj.spawnEntityInWorld(entityitem);
							}
						}
					}
					
					flag = true;
				}
				else
				{
					food = new ItemStack(droppedItem);
				
					if (is.stackTagCompound != null)
						food.stackTagCompound = (NBTTagCompound)is.stackTagCompound.copy();
					
					if(player.inventory.addItemStackToInventory(food))
						flag = true;
				}
				
				if(flag)
				{
					event.item.delayBeforeCanPickup = 100;
					event.item.setDead();
					event.item.setInvisible(true);
					Random rand = player.worldObj.rand;
					player.worldObj.playSoundAtEntity(player, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void startEating(PlayerUseItemEvent.Start event)
	{
		if(event.item.getItem() instanceof ItemFoodTFC)
		{
			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(event.entityPlayer);
			FoodRecord foodrecord = CWTFC_Core.getPlayerFoodRecord(event.entityPlayer);
			
			float deminEat = 5 * CWTFC_Core.getFoodsCount(foodrecord, event.item);
			
			if (foodstats.needFood() && deminEat != 0)
				return;
			else if(foodstats.stomachLevel <= 0)
				return;
			else if(deminEat == 0)
			{
				ChatComponentText text = new ChatComponentText("You have grown tired of this food and cannot eat it.");
				text.getChatStyle().setColor(EnumChatFormatting.DARK_RED).setItalic(true);
				event.entityPlayer.addChatComponentMessage(text);
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void stopEating(PlayerUseItemEvent.Tick event)
	{		
		if(event.item.getItem() instanceof ItemFoodTFC)
		{
			if(event.duration <= 1)
			{
				event.entityPlayer.setCurrentItemOrArmor(0, CWTFC_Core.processEating(event.item, event.entityPlayer.worldObj, event.entityPlayer, 5, false));
				event.entityPlayer.clearItemInUse();
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void updateTooltip(ItemTooltipEvent event)
	{
		ItemStack is = event.itemStack;
		if(is.getItem() instanceof ItemFoodTFC && !(is.getItem() instanceof ItemTFCFoodTransform))
		{
			if(((ItemFoodTFC)is.getItem()).isEdible(is))
				CWTFC_Core.getFoodUse(is, event.entityPlayer, event.toolTip);
			else
				event.toolTip.add(EnumChatFormatting.DARK_GRAY + "Not currently edible");
		}
	}
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.entityPlayer.worldObj.isRemote)
			return;

		ItemStack itemInHand = event.entityPlayer.getCurrentEquippedItem();
		World world = event.world;
		int x = event.x;
		int y = event.y;
		int z = event.z;
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		
		//Remove existing FoodPrep surfaces
		if(event.action == Action.RIGHT_CLICK_BLOCK && block instanceof BlockFoodPrep)
		{
			block.breakBlock(world, x, y, z, block, meta);
			world.setBlockToAir(x, y, z);
			event.setCanceled(true);
			return;
		}
		
		if(itemInHand == null)
			return;
		
		Item item = itemInHand.getItem();
		
		if(event.action == Action.RIGHT_CLICK_BLOCK && event.getResult() != Result.DENY)
		{
			//Prevent new Foodprep surfaces from being made
			if(item instanceof ItemKnife)
			{
				if(block != TFCBlocks.toolRack)
				{
					Material mat = block.getMaterial();
					if(event.face == 1 && block.isSideSolid(world, x, y, z, ForgeDirection.UP) &&!TFC_Core.isSoil(block) && !TFC_Core.isWater(block) 
						&& world.isAirBlock(x, y + 1, z) && (mat == Material.wood || mat == Material.rock || mat == Material.iron))
					{	
						event.setCanceled(true);
						return;
					}
				}
			}
			
			if(item == TFCItems.barleyWhole || item == TFCItems.oatWhole || item == TFCItems.riceWhole || 
					item == TFCItems.ryeWhole || item == TFCItems.wheatWhole)
			{
				Material mat = block.getMaterial();

				if(event.face == 1 && block.isSideSolid(world, x, y, z, ForgeDirection.UP) &&!TFC_Core.isSoil(block) && !TFC_Core.isWater(block) 
					&& world.isAirBlock(x, y + 1, z) && (mat == Material.wood || mat == Material.rock || mat == Material.iron))
				{
					if(world.setBlock(x, y + 1, z, CWTFCBlocks.GrainsBlock))
					{
						((TileGrains) world.getTileEntity(x, y + 1, z)).setplacedGrains(itemInHand.copy());
						event.entityPlayer.setCurrentItemOrArmor(0, null);
						return;
					}
				}
			}
			
			//Replace TFC hopper with CWTFC hopper
			if(itemInHand.getItem() == Item.getItemFromBlock(TFCBlocks.hopper))
			{
				event.setCanceled(true);
				switch(event.face)
				{
					case 0: world.setBlock(x, y - 1, z, CWTFCBlocks.hopperCWTFC, 0, 3); break;
					case 1: world.setBlock(x, y + 1, z, CWTFCBlocks.hopperCWTFC, 0, 3); break;
					case 2: world.setBlock(x, y, z - 1, CWTFCBlocks.hopperCWTFC, 0, 3); break;
					case 3: world.setBlock(x, y, z + 1, CWTFCBlocks.hopperCWTFC, 0, 3); break;
					case 4: world.setBlock(x - 1, y, z, CWTFCBlocks.hopperCWTFC, 0, 3); break;
					case 5: world.setBlock(x + 1, y, z, CWTFCBlocks.hopperCWTFC, 0, 3); break;
					default: break;
				}			
				
				event.entityPlayer.setCurrentItemOrArmor(0, null);
				return;
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event)
	{
		EntityLivingBase entity = event.entityLiving;

		//Reset eaten foods after death
		if (entity instanceof EntityPlayer && Settings.diminishingReturns)
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
