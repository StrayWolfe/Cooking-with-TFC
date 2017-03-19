package straywolfe.cookingwithtfc.common.block;

import java.util.Random;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCOptions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.managers.CWTFCCropIndex;
import straywolfe.cookingwithtfc.api.managers.CropManager;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.registries.PlantRegistry;
import straywolfe.cookingwithtfc.common.tileentity.TileCrop;

public class BlockCrop extends BlockContainer
{
	private IIcon[] iconsGourds = new IIcon[6];
	private IIcon[] iconsBrownMushroom = new IIcon[4];
	private IIcon[] iconsRedMushroom = new IIcon[4];
	private IIcon[] iconCelery = new IIcon[7];
	private IIcon[] iconLettuce = new IIcon[6];
	private IIcon[] iconPeanut = new IIcon[6];
	
	
	public BlockCrop() 
	{
		super(Material.plants);
		setBlockName("customCrop");
		blockHardness = 0.5F;
	}	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		for(int i = 1; i <= 4; i++)
		{
			iconsGourds[i - 1] = register.registerIcon(ModInfo.ModID + ":Crops/Pumpkin_" + i);
			iconsBrownMushroom[i - 1] = register.registerIcon(ModInfo.ModID + ":Crops/BrownMushroom_" + i);
			iconsRedMushroom[i - 1] = register.registerIcon(ModInfo.ModID + ":Crops/RedMushroom_" + i);
		}
		
		for(int i = 1; i <= 6; i++)
		{
			iconLettuce[i - 1] = register.registerIcon(ModInfo.ModID + ":Crops/Lettuce_" + i);
			iconPeanut[i - 1] = register.registerIcon(ModInfo.ModID + ":Crops/Peanut_" + i);
		}
		
		for(int i = 1; i <= 7; i++)
		{
			iconCelery[i - 1] = register.registerIcon(ModInfo.ModID + ":Crops/Celery_" + i);
		}
		
		iconsGourds[4] = register.registerIcon(ModInfo.ModID + ":Crops/Watermelon_1");
		iconsGourds[5] = register.registerIcon(ModInfo.ModID + ":Crops/Watermelon_2");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getCropIcon(String s)
	{
		if("Pumpkin_Side".equals(s))
			return iconsGourds[0];
		else if("Pumpkin_Top".equals(s))
			return iconsGourds[1];
		else if("Plant_Top".equals(s))
			return iconsGourds[2];
		else if("Plant_Bottom".equals(s))
			return iconsGourds[3];
		else if("Melon_Side".equals(s))
			return iconsGourds[4];
		else if("Melon_Top".equals(s))
			return iconsGourds[5];
		else
			return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int meta)
	{
		TileCrop te = (TileCrop)access.getTileEntity(x, y, z);
		int cropID = te.getCropID();
		CWTFCCropIndex crop = CropManager.getInstance().getCropFromId(cropID);
		
		if(crop != null)
		{
			int stage = (int) Math.floor(te.growth);
			if(stage >= crop.numGrowthStages)
				stage = crop.numGrowthStages - 1;
			
			switch(cropID)
			{
				case PlantRegistry.WATERMELON: 
					return iconsGourds[4];
				case PlantRegistry.PUMPKIN: 
					return iconsGourds[0];
				case PlantRegistry.BROWNMUSHROOM:
					return iconsBrownMushroom[stage];
				case PlantRegistry.REDMUSHROOM:
					return iconsRedMushroom[stage];
				case PlantRegistry.CELERY:
					return iconCelery[stage];
				case PlantRegistry.LETTUCE:
					return iconLettuce[stage];
				case PlantRegistry.PEANUT:
					return iconPeanut[stage];
				default: 
					return iconsGourds[2];
			}
		}
		
		return iconsGourds[2];
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			TileCrop te = (TileCrop)world.getTileEntity(x, y, z);
			CWTFCCropIndex crop = CropManager.getInstance().getCropFromId(te.getCropID());
			
			if(TFCOptions.enableDebugMode)
			{
				TerraFirmaCraft.LOG.info("Crop ID: " + te.getCropID());
				TerraFirmaCraft.LOG.info("Est Growth: " + te.getEstimatedGrowth(crop));
			}
		}
		
		return false;
	}
	
	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int l, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			ItemStack itemstack = player.inventory.getCurrentItem();
			int[] equipIDs = OreDictionary.getOreIDs(itemstack);

			for (int id : equipIDs)
			{
				String name = OreDictionary.getOreName(id);
				if (name.startsWith("itemScythe"))
				{
					for (int i = -1; i < 2; i++)
					{
						for (int j = -1; j < 2; j++)
						{
							if (world.getBlock(x + i, y, z + j) == this && player.inventory.getStackInSlot(player.inventory.currentItem) != null)
							{
								player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
								TileCrop teCrop = (TileCrop)world.getTileEntity(x + i, y, z + j);
								
								teCrop.onHarvest(world, player);

								world.setBlockToAir(x + i, y, z + j);

								itemstack.damageItem(1, player);
								if (itemstack.stackSize == 0)
									player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
							}
						}
					}
					return;
				}
			}
			
			((TileCrop)world.getTileEntity(x, y, z)).onHarvest(world, player);
		}
	}
	
	@Override
	public int getRenderType()
	{
		return CWTFCBlocks.customCropRenderID;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) 
	{
		return new TileCrop();
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{		
		if(!canBlockStay(world, x, y, z))
			world.setBlockToAir(x, y, z);
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return TFC_Core.isFarmland(world.getBlock(x, y - 1, z)) || TFC_Core.isSoil(world.getBlock(x, y - 1, z));
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		TileCrop te = (TileCrop)world.getTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		int stage = (int) Math.floor(te.growth);
		
		switch(te.getCropID())
		{
			case PlantRegistry.WATERMELON: 
			case PlantRegistry.PUMPKIN:
			{
				if(stage > 4)
					stage = 4;
				
				if(stage < 3)
					return null;
				else if(stage == 3)
				{
					switch(meta)
					{
						case 0: return AxisAlignedBB.getBoundingBox(x + 0.125, y, z + 0.3125, x + 0.4375, y + 0.3125, z + 0.625);
						case 1: return AxisAlignedBB.getBoundingBox(x + 0.375, y, z + 0.125, x + 0.6875, y + 0.3125, z + 0.4375);
						case 2: return AxisAlignedBB.getBoundingBox(x + 0.3125, y, z + 0.5625, x + 0.625, y + 0.3125, z + 0.875);
						default: return AxisAlignedBB.getBoundingBox(x + 0.375, y, z + 0.5625, x + 0.6875, y + 0.3125, z + 0.875);
					}
				}
				else
				{
					switch(meta)
					{
						case 0: return AxisAlignedBB.getBoundingBox(x, y, z, x + 0.625, y + 0.625, z + 0.625);
						case 1: return AxisAlignedBB.getBoundingBox(x + 0.375, y, z, x + 1, y + 0.625, z + 0.625);
						case 2: return AxisAlignedBB.getBoundingBox(x, y, z + 0.375, x + 0.625, y + 0.625, z + 1);
						default: return AxisAlignedBB.getBoundingBox(x + 0.375, y, z + 0.375, x + 1, y + 0.625, z + 1);
					}
				}
			}
			default: return null;
		}
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		
		switch(((TileCrop)world.getTileEntity(x, y, z)).getCropID())
		{
			case PlantRegistry.WATERMELON: 
			case PlantRegistry.PUMPKIN:
				return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.3, z + 1);
			default:
				return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.2, z + 1);
		}
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) 
	{
		if(world.getTileEntity(x, y, z) instanceof TileCrop)
		{
			switch(((TileCrop)world.getTileEntity(x, y, z)).getCropID())
			{
				case PlantRegistry.WATERMELON: 
				case PlantRegistry.PUMPKIN:
					setBlockBounds(0, 0, 0, 1, 0.3f, 1); break;
				default:
					setBlockBounds(0, 0, 0, 1, 0.2f, 1); break;
			}
		}
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
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
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}
}
