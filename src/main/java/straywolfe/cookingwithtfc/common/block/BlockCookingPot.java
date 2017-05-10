package straywolfe.cookingwithtfc.common.block;

import java.util.Random;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Effects.GasFX;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.Tools.ItemCustomBucketMilk;
import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.TileEntities.TEFirepit;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.IFluidContainerItem;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.item.ItemTFCMealTransform;
import straywolfe.cookingwithtfc.common.tileentity.TileCookingPot;

public class BlockCookingPot extends BlockTerraContainer
{	
	public BlockCookingPot()
	{
		super(Material.clay);
		setHardness(1F);
		setBlockName("CookingPot");
		setBlockBounds(0.25f, 0, 0.25f, 0.75f, 0.5f, 0.75f);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{		
			ItemStack equippedItem = player.getCurrentEquippedItem();
			TileCookingPot teCookingPot = (TileCookingPot)world.getTileEntity(x, y, z);
			
			if(side == 0 || side == 1)
			{
				if(player.isSneaking())
				{
					//Shift right click adds and removes lid
					if(teCookingPot.getHasLid())
						teCookingPot.setHasLid(false);
					else
						teCookingPot.setHasLid(true);
				}
				else if(!teCookingPot.getHasLid())
				{
					//Right click w/o items to remove ingredients
					if(equippedItem == null)
					{
						player.setCurrentItemOrArmor(0, teCookingPot.getLastItem());
					}
					//Right click with ingredient to add ingredients
					else if(equippedItem.getItem() instanceof ItemFoodTFC)
					{
						ItemStack knife = null;
						boolean knifeNotNeeded = false;
						int slot = 0;
						int foodWt = 2;
						
						if(Food.getWeight(equippedItem) - foodWt <= 0)
							knifeNotNeeded = true;
						
						if(!knifeNotNeeded)
						{
							for(int i = 0; i < 9; i++)
							{
								ItemStack is = player.inventory.getStackInSlot(i);
								if(is != null && is.getItem() instanceof ItemKnife)
								{
									slot = i;
									knife = is;
									break;
								}
							}
						}
						
						if((knife != null && teCookingPot.getStackInSlot(TileCookingPot.FLUIDOUTPUT - 1) == null) || knifeNotNeeded)
						{
							ItemStack food = equippedItem.copy();
							
							if(Food.getDecay(equippedItem) > 0)
							{
								Food.setWeight(equippedItem, Food.getWeight(equippedItem) - Food.getDecay(equippedItem) - foodWt);
								
								Food.setDecay(equippedItem, 0);
							}
							else
								Food.setWeight(equippedItem, Food.getWeight(equippedItem) - foodWt);
							
							Food.setDecay(food, 0);
							Food.setWeight(food, foodWt);
							
							teCookingPot.setLastItem(food);
							
							if(!knifeNotNeeded)
							{
								knife.setItemDamage(knife.getItemDamage() + 1);
								if(knife.getItemDamage() > knife.getMaxDamage())
									player.inventory.setInventorySlotContents(slot, null);
							}						
						}
					}
					//Right click with empty container to remove fluid
					else if(FluidContainerRegistry.isEmptyContainer(equippedItem) || 
							equippedItem.getItem() instanceof IFluidContainerItem)
					{
						ItemStack tmp = equippedItem.copy();
						tmp.stackSize = 1;
						ItemStack is = teCookingPot.removeLiquid(tmp);

						if (ItemStack.areItemStacksEqual(equippedItem, is))
						{
							return false;
						}

						if (is.getItem() == TFCItems.woodenBucketMilk)
						{
							ItemCustomBucketMilk.createTag(is, 20f);
						}
						
						if(is.getItem() instanceof ItemTFCMealTransform)
						{
							if(teCookingPot.getStackInSlot(TileCookingPot.FLUIDOUTPUT) != null)
								is = teCookingPot.getStackInSlot(TileCookingPot.FLUIDOUTPUT).copy();
							
							Food.setMealSkill(is, TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_COOKING).ordinal());
						}

						equippedItem.stackSize--;

						if (equippedItem.stackSize == 0)
							player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

						if ( equippedItem.stackSize == 0 && ( is.getMaxStackSize() == 1 || ! player.inventory.hasItemStack(is) ) )
							player.inventory.setInventorySlotContents(player.inventory.currentItem, is);
						else
						{
							if (!player.inventory.addItemStackToInventory(is))
								player.dropPlayerItemWithRandomChoice(is, false);
						}

						if ( player.inventoryContainer != null )
							player.inventoryContainer.detectAndSendChanges();
						
						return true;
					}
					//Right click with full container to add fluid
					else if(FluidContainerRegistry.isFilledContainer(equippedItem) || 
							equippedItem.getItem() instanceof IFluidContainerItem && 
							((IFluidContainerItem)equippedItem.getItem()).getFluid(equippedItem) != null)
					{
						ItemStack tmp = equippedItem.copy();
						tmp.stackSize = 1;
						ItemStack is = teCookingPot.addLiquid(tmp);

						if (ItemStack.areItemStacksEqual(equippedItem, is))
						{
							return false;
						}

						equippedItem.stackSize--;
						
						if (equippedItem.stackSize == 0)
							player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

						if (equippedItem.stackSize == 0 && (is.getMaxStackSize() == 1 || !player.inventory.hasItemStack(is)))
							player.inventory.setInventorySlotContents(player.inventory.currentItem, is);
						else
						{
							if (!player.inventory.addItemStackToInventory(is))
								player.dropPlayerItemWithRandomChoice(is, false);
						}

						if ( player.inventoryContainer != null )
							player.inventoryContainer.detectAndSendChanges();
						
						return true;
					}
				}
			}
			else
			{
				//Shift right click to remove pot
				if(player.isSneaking())
				{
					boolean hasFood = false;
					for(int i = TileCookingPot.INVFOODSTART; i <= TileCookingPot.INVFOODEND; i++)
					{
						if(teCookingPot.getStackInSlot(i) != null)
						{
							hasFood = true;
							break;
						}
					}
					
					if (equippedItem == null && teCookingPot.getHasLid() && !hasFood && teCookingPot.getCookingPotFluid() == null)
					{
						ItemStack[] firewood = new ItemStack[4];
						firewood[0] = teCookingPot.getStackInSlot(0);
						firewood[1] = teCookingPot.getStackInSlot(1);
						firewood[2] = teCookingPot.getStackInSlot(2);
						firewood[3] = teCookingPot.getStackInSlot(3);
						
						for(int i = 0; i < teCookingPot.getSizeInventory(); i++)
						{
							teCookingPot.setInventorySlotContents(i, null);
						}

						world.setBlock(x, y, z, TFCBlocks.firepit, world.getBlockMetadata(x, y, z), 3);
						
						TEFirepit teFirepit = (TEFirepit)world.getTileEntity(x, y, z);
						teFirepit.fireTemp = teCookingPot.fireTemp;
						teFirepit.fuelTimeLeft = teCookingPot.fuelTimeLeft;
						teFirepit.fuelBurnTemp = teCookingPot.fuelBurnTemp;
						
						teFirepit.setInventorySlotContents(0, firewood[0]);
						teFirepit.setInventorySlotContents(3, firewood[1]);
						teFirepit.setInventorySlotContents(4, firewood[2]);
						teFirepit.setInventorySlotContents(5, firewood[3]);
						
						return true;
					}
				}
				//Right click with and w/o items to add and remove logs and light fire
				else
				{
					//Right click with empty hand to remove logs
					if(equippedItem == null)
					{
						if(teCookingPot.getStackInSlot(3) != null)
						{
							player.setCurrentItemOrArmor(0, teCookingPot.getStackInSlot(3));
							teCookingPot.setInventorySlotContents(3, null);
						}
					}
					//Right click to light fire
					else if(equippedItem.getItem() == TFCItems.fireStarter ||  equippedItem.getItem() == TFCItems.flintSteel)
					{
						if(teCookingPot.fireTemp < 210 && teCookingPot.getStackInSlot(3) != null)
						{
							teCookingPot.fireTemp = 300;
							if (equippedItem.getItem() instanceof ItemFlintAndSteel)
							{
								Random rand = new Random();
								world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
							}
							equippedItem.damageItem(1, player);
							world.setBlockMetadataWithNotify(x, y, z, 1, 3);
							return true;
						}
					}
					//Right click with logs to add firewood
					else if(equippedItem.getItem() == TFCItems.logs || equippedItem.getItem() == Item.getItemFromBlock(TFCBlocks.peat))
					{
						if(teCookingPot.getStackInSlot(0) == null)
						{
							teCookingPot.setInventorySlotContents(0, new ItemStack(equippedItem.getItem(), 1, equippedItem.getItemDamage()));
							equippedItem.stackSize--;
						}
					}
				}
			}
			
			world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z), 3);
			world.markBlockForUpdate(x, y, z);
				
			return true;
		}
		
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		int meta = world.getBlockMetadata(x, y, z);
		TileCookingPot te = (TileCookingPot)world.getTileEntity(x, y, z);
		
		if (meta >= 1)
		{
			if(random.nextInt(24) == 0)
				world.playSoundEffect(x, y, z, "fire.fire", 0.4F + (random.nextFloat() / 2), 0.7F + random.nextFloat());

			float f = x + 0.5F;
			float f1 = y + 0.1F + random.nextFloat() * 6F / 16F;
			float f2 = z + 0.5F;
			float f4 = random.nextFloat() * 0.6F;
			float f5 = random.nextFloat() * -0.6F;
			float f6 = random.nextFloat() * -0.6F;
			world.spawnParticle("smoke", f + f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f + f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f + f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f + f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			if (te.fireTemp > 550)
			{
				world.spawnParticle("flame", f + f5 + 0.3F , f1, f2 + f6 + 0.2F, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f + f4 - 0.3F , f1, f2 + f6 + 0.1F, 0.0D, 0.0D, 0.0D);
			}
		}
		
		if(te.fireTemp > 600 && te.getHasLid() && te.getRecipeID() != -1 && te.getCookTimer() > 0)
		{
			double f4 = random.nextFloat() * -0.1F;
			double f5 = random.nextFloat() * -0.1F;
			double f6 = random.nextFloat() * -0.1F;

			Minecraft.getMinecraft().effectRenderer.addEffect(new GasFX(world, x, y, z, f4, f5, f6));
			f4 = random.nextFloat() * -0.1F;
			f5 = random.nextFloat() * -0.1F;
			f6 = random.nextFloat() * -0.1F;
			Minecraft.getMinecraft().effectRenderer.addEffect(new GasFX(world, x, y, z, f4, f5, f6));
		}
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 0)
			return 0;
		else
			return 10;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Ceramic");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileCookingPot();
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return CWTFCBlocks.cookingPotRenderID;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) 
    {
		if (world.getBlockMetadata(x, y, z) >= 1 && !entity.isImmuneToFire() && entity instanceof EntityLivingBase)
		{
			entity.setFire(2);
		}
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.getBlock(x, y - 1, z).isOpaqueCube())
		{
			eject(world, x, y, z);
			world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int l)
	{
		eject(world, x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		eject(world, x, y, z);
	}
	
	public void eject(World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileCookingPot)
		{
			((TileCookingPot)te).ejectContents();			
			world.removeTileEntity(x, y, z);
		}
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
	{
		return null;
	}
	
	@Override
	public boolean getBlocksMovement(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}
	
	@Override
	public int quantityDropped(Random rand)
	{
		return 0;
	}
}
