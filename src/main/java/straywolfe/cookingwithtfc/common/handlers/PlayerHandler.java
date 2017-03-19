package straywolfe.cookingwithtfc.common.handlers;

import java.util.ArrayList;
import java.util.Random;

import com.bioxx.tfc.Blocks.Devices.BlockFoodPrep;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.Tools.ItemKnife;
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
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.core.CWTFC_Core;
import straywolfe.cookingwithtfc.common.core.FoodRecord;
import straywolfe.cookingwithtfc.common.item.ItemTFCFoodTransform;
import straywolfe.cookingwithtfc.common.lib.Settings;
import straywolfe.cookingwithtfc.common.tileentity.TileGrains;

public class PlayerHandler 
{	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event)
	{
		EntityPlayer player = event.entityPlayer;

		//Convert TFC Salt with custom salt as a food item
		if(player.inventory.getFirstEmptyStack() != -1)
		{
			ItemStack is = event.item.getEntityItem();
			Item droppedItem = is.getItem();
	
			if(droppedItem != null && droppedItem == TFCItems.powder && is.getItemDamage() == 9)
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
							entityitem.motionX = entityitem.motionY = entityitem.motionZ = 0;
							player.worldObj.spawnEntityInWorld(entityitem);
						}
					}
				}

				event.item.delayBeforeCanPickup = 100;
				event.item.setDead();
				event.item.setInvisible(true);
			}
		}
	}
	
	@SubscribeEvent
	public void startEating(PlayerUseItemEvent.Start event)
	{
		//Stop eating if pickiness limit is reached 
		if(event.item.getItem() instanceof ItemFoodTFC && Settings.diminishingReturns)
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
		//Replace TFC eating mechanic with custom eating mechanic
		if(event.item.getItem() instanceof ItemFoodTFC && Settings.diminishingReturns)
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
		//Add food consumption info to TFC Foods
		ItemStack is = event.itemStack;
		if(is.getItem() instanceof ItemFoodTFC && !(is.getItem() instanceof ItemTFCFoodTransform) && Settings.diminishingReturns)
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
			
			//Place grain block
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
			/*if(itemInHand.getItem() == Item.getItemFromBlock(TFCBlocks.hopper))
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
			}*/
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
	
	@SubscribeEvent
	public void onBlockBreak(BreakEvent event)
	{
		Random rand = event.world.rand;
		
		//Drop nuts from leaves
		if(event.block == TFCBlocks.leaves && rand.nextInt(100) < 10)
		{
			Item nut = null;
			
			switch(event.blockMetadata)
			{
				case 0: nut = CWTFCItems.acorn; break;
				case 3: nut = CWTFCItems.chestnut; break;
				case 5: nut = CWTFCItems.pecan; break;
				case 8: nut = CWTFCItems.pineNut; break;
			}
			
			if(nut != null)
			{
				ItemStack is = ItemFoodTFC.createTag(new ItemStack(nut), CropIndex.getWeight(4, rand));
				event.world.spawnEntityInWorld(new EntityItem(event.world, event.x + 0.5, event.y + 0.5, event.z + 0.5, is));
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockHarvest(HarvestDropsEvent event)
	{		
		//Replace pumpkin block drop with pumpkin seeds
		if(!Settings.disablePumpkins && event.block == TFCBlocks.pumpkin)
		{
			event.drops.clear();
			event.drops.add(new ItemStack(CWTFCItems.seedsPumpkin));
		}
	}
}
