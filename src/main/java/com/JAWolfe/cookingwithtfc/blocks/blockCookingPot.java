package com.JAWolfe.cookingwithtfc.blocks;

import java.util.Random;

import com.JAWolfe.cookingwithtfc.CookingWithTFC;
import com.JAWolfe.cookingwithtfc.init.CWTFCBlocks;
import com.JAWolfe.cookingwithtfc.init.Items.CWTFCItems;
import com.JAWolfe.cookingwithtfc.items.ItemTFCMealTransform;
import com.JAWolfe.cookingwithtfc.references.GUIs;
import com.JAWolfe.cookingwithtfc.tileentities.TECookingPot;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.Tools.ItemCustomBucketMilk;
import com.bioxx.tfc.TileEntities.TEFirepit;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.IFluidContainerItem;

public class blockCookingPot extends BlockTerraContainer
{
	private IIcon[] ceramicIcons;
	
	public blockCookingPot()
	{
		super(Material.clay);
		this.setHardness(1F);
		this.setBlockName("CookingPot");
		this.setBlockBounds(0.25f, 0, 0.25f, 0.75f, 0.5f, 0.75f);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
		{
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		else
		{		
			ItemStack equippedItem = player.getCurrentEquippedItem();
			TECookingPot teCookingPot = (TECookingPot)world.getTileEntity(x, y, z);
			
			if (player.isSneaking() && equippedItem == null)
			{			
				if(teCookingPot.getStackInSlot(4) == null && teCookingPot.getStackInSlot(5) == null && 
				   teCookingPot.getStackInSlot(6) == null && teCookingPot.getStackInSlot(7) == null &&
				   teCookingPot.getCookingPotFluid() == null)
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
					
					world.setBlock(x, y, z, TFCBlocks.firepit, 1, 0x2);
					
					TEFirepit teFirepit = (TEFirepit)world.getTileEntity(x, y, z);
					teFirepit.fireTemp = teCookingPot.fireTemp;
					teFirepit.fuelTimeLeft = teCookingPot.fuelTimeLeft;
					teFirepit.fuelBurnTemp = teCookingPot.fuelBurnTemp;
					
					teFirepit.setInventorySlotContents(0, firewood[0]);
					teFirepit.setInventorySlotContents(3, firewood[1]);
					teFirepit.setInventorySlotContents(4, firewood[2]);
					teFirepit.setInventorySlotContents(5, firewood[3]);
					
					player.setCurrentItemOrArmor(0, new ItemStack(CWTFCItems.ClayCookingPot, 1, 1));
					return true;
				}
				return true;
			}
			else
			{
				if(!handleItems(player, teCookingPot, equippedItem, world, x, y, z))
					player.openGui(CookingWithTFC.instance, GUIs.CLAYCOOKINGPOT.ordinal(), world, x, y, z);
					
				return true;
			}
		}
	}
	
	private boolean handleItems(EntityPlayer player, TECookingPot te, ItemStack equippedItem, World world, int x, int y, int z)
	{	
		if(equippedItem == null)
			return false;
		
		if(equippedItem.getItem() == TFCItems.fireStarter ||  equippedItem.getItem() == TFCItems.flintSteel)
		{
			if(te.fireTemp < 210 && te.getStackInSlot(3) != null)
			{
				te.fireTemp = 300;
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
		else if(FluidContainerRegistry.isFilledContainer(equippedItem) || 
				equippedItem.getItem() instanceof IFluidContainerItem && 
				((IFluidContainerItem)equippedItem.getItem()).getFluid(equippedItem) != null)
		{
			ItemStack tmp = equippedItem.copy();
			tmp.stackSize = 1;
			ItemStack is = te.addLiquid(tmp);

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
		else if(FluidContainerRegistry.isEmptyContainer(equippedItem) || 
				equippedItem.getItem() instanceof IFluidContainerItem)
		{
			ItemStack tmp = equippedItem.copy();
			tmp.stackSize = 1;
			ItemStack is = te.removeLiquid(tmp);

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
				is = te.getStackInSlot(TECookingPot.FLUIDOUTPUT).copy();
				
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
		
		return false;
	}
	
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta >= 1)
		{
			if (rand.nextInt(24) == 0)
				world.playSoundEffect(x, y, z, "fire.fire", 0.4F + (rand.nextFloat() / 2), 0.7F + rand.nextFloat());

			float f = x + 0.5F;
			float f1 = y + 0.1F + rand.nextFloat() * 6F / 16F;
			float f2 = z + 0.5F;
			float f4 = rand.nextFloat() * 0.6F;
			float f5 = rand.nextFloat() * -0.6F;
			float f6 = rand.nextFloat() * -0.6F;
			world.spawnParticle("smoke", f + f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f + f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f + f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f + f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			if (((TECookingPot)world.getTileEntity(x, y, z)).fireTemp > 550)
			{
				world.spawnParticle("flame", f + f5 + 0.3F , f1, f2 + f6 + 0.2F, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f + f4 - 0.3F , f1, f2 + f6 + 0.1F, 0.0D, 0.0D, 0.0D);
			}
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
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		ceramicIcons = new IIcon[3];
		ceramicIcons[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Ceramic Vessel Top");
		ceramicIcons[1] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Ceramic Vessel Side");
		ceramicIcons[2] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "clay/Ceramic Vessel Bottom");
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(side == 1)
			return ceramicIcons[0];
		else if(side == 0)
			return ceramicIcons[2];
		else
			return ceramicIcons[1];
	}
	
	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		if(side == 1)
			return ceramicIcons[0];
		else if(side == 0)
			return ceramicIcons[2];
		else
			return ceramicIcons[1];
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TECookingPot();
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
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{
		eject(world, x, y, z);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion exp)
	{
		eject(world, x, y, z);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta)
	{
		eject(world, x, y, z);
	}
	
	public void eject(World world, int x, int y, int z)
	{
		if (world.getTileEntity(x, y, z) instanceof TECookingPot)
		{
			TECookingPot te = (TECookingPot)world.getTileEntity(x, y, z);
			te.ejectContents();
			world.removeTileEntity(x, y, z);
		}
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
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
