package straywolfe.cookingwithtfc.common.block;

import java.util.Random;

import com.bioxx.tfc.Blocks.Vanilla.BlockCustomLeaves;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.CookingWithTFC;
import straywolfe.cookingwithtfc.common.lib.Constants;
import straywolfe.cookingwithtfc.common.lib.ModInfo;

public class BlockLeaves extends BlockCustomLeaves
{

	public BlockLeaves()
	{
		super();
		woodNames = Constants.WOODTYPES;
		icons = new IIcon[woodNames.length];
		iconsOpaque = new IIcon[woodNames.length];
		setHardness(0.2F);
		setLightOpacity(1);
		setStepSound(Block.soundTypeGrass);
		setBlockName("Leaves");
	}
	
	@Override
	public int colorMultiplier(IBlockAccess bAccess, int x, int y, int z)
	{
		return CookingWithTFC.proxy.foliageColorMultiplier(bAccess, x, y, z);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int xOrig, int yOrig, int zOrig, Block b)
	{
		if (!world.isRemote)
		{
			int var6 = world.getBlockMetadata(xOrig, yOrig, zOrig);

			byte searchRadius = 4;
			int maxDist = searchRadius + 1;
			byte searchDistance = 11;
			int center = searchDistance / 2;
			adjacentTreeBlocks = null;
			
			if (adjacentTreeBlocks == null)
				adjacentTreeBlocks = new int[searchDistance][searchDistance][searchDistance];
			
			if (world.checkChunksExist(xOrig - maxDist, yOrig - maxDist, zOrig - maxDist, xOrig + maxDist, yOrig + maxDist, zOrig + maxDist))
			{
				for (int xd = -searchRadius; xd <= searchRadius; ++xd)
				{
					int searchY = searchRadius - Math.abs(xd);
					for (int yd = -searchY; yd <= searchY; ++yd)
					{
						int searchZ = searchY - Math.abs(yd);
						for (int zd = -searchZ; zd <= searchZ; ++zd)
						{
							Block block = world.getBlock(xOrig + xd, yOrig + yd, zOrig + zd);
							if (block == CWTFCBlocks.naturalLog)
								adjacentTreeBlocks[xd + center][yd + center][zd + center] = 0;
							else if (block == this && var6 == world.getBlockMetadata(xOrig + xd, yOrig + yd, zOrig + zd))
								adjacentTreeBlocks[xd + center][yd + center][zd + center] = -2;
							else
								adjacentTreeBlocks[xd + center][yd + center][zd + center] = -1;
						}
					}
				}
				
				for (int pass = 1; pass <= 4; ++pass)
				{
					for (int xd = -searchRadius; xd <= searchRadius; ++xd)
					{
						int searchY = searchRadius - Math.abs(xd);
						for (int yd = -searchY; yd <= searchY; ++yd)
						{
							int searchZ = searchY - Math.abs(yd);
							for (int zd = -searchZ; zd <= searchZ; ++zd)
							{
								if (adjacentTreeBlocks[xd + center][yd + center][zd + center] == pass - 1)
								{
									if (adjacentTreeBlocks[xd + center - 1][yd + center][zd + center] == -2)
										adjacentTreeBlocks[xd + center - 1][yd + center][zd + center] = pass;

									if (adjacentTreeBlocks[xd + center + 1][yd + center][zd + center] == -2)
										adjacentTreeBlocks[xd + center + 1][yd + center][zd + center] = pass;

									if (adjacentTreeBlocks[xd + center][yd + center - 1][zd + center] == -2)
										adjacentTreeBlocks[xd + center][yd + center - 1][zd + center] = pass;

									if (adjacentTreeBlocks[xd + center][yd + center + 1][zd + center] == -2)
										adjacentTreeBlocks[xd + center][yd + center + 1][zd + center] = pass;

									if (adjacentTreeBlocks[xd + center][yd + center][zd + center - 1] == -2)
										adjacentTreeBlocks[xd + center][yd + center][zd + center - 1] = pass;

									if (adjacentTreeBlocks[xd + center][yd + center][zd + center + 1] == -2)
										adjacentTreeBlocks[xd + center][yd + center][zd + center + 1] = pass;
								}
							}
						}
					}
				}
			}
			
			int res = adjacentTreeBlocks[center][center][center];

			if (res < 0)
			{
				if(world.getChunkFromBlockCoords(xOrig, zOrig) != null)
					world.setBlockToAir(xOrig, yOrig, zOrig);
			}
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < this.woodNames.length; i++)
		{
			icons[i] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/" + woodNames[i] + " Leaves Fancy");
			iconsOpaque[i] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/" + woodNames[i] + " Leaves");
		}
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int meta)
	{
		if (!world.isRemote)
		{
			ItemStack itemstack = entityplayer.inventory.getCurrentItem();
			int[] equipIDs = OreDictionary.getOreIDs(itemstack);
			for (int id : equipIDs)
			{
				String name = OreDictionary.getOreName(id);
				if (name.startsWith("itemScythe"))
				{
					for (int x = -1; x < 2; x++)
					{
						for (int z = -1; z < 2; z++)
						{
							for (int y = -1; y < 2; y++)
							{
								if (world.getBlock(i + x, j + y, k + z).getMaterial() == Material.leaves &&
										entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem) != null)
									{
										addDrops(entityplayer, world, i + x, j + y, k + z, meta, true);
										super.harvestBlock(world, entityplayer, i + x, j + y, k + z, meta);

										itemstack.damageItem(1, entityplayer);
										if (itemstack.stackSize == 0)
											entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
									}
							}
						}
					}
					return;
				}
			}
			
			addDrops(entityplayer, world, i, j, k, meta, false);
			super.harvestBlock(world, entityplayer, i, j, k, meta);
		}
	}
	
	private void addDrops(EntityPlayer entityplayer, World world, int x,int y, int z, int meta, boolean isScythe)
	{
		entityplayer.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		entityplayer.addExhaustion(isScythe ? 0.045F : 0.025F);
		if (world.rand.nextInt(100) < (isScythe ? 28 : 11))
			dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.stick, 1));
		else if (world.rand.nextInt(100) < (isScythe ? 4 : 2) && TFCOptions.enableSaplingDrops)
			dropBlockAsItem(world, x, y, z, new ItemStack(getItemDropped(0, null, 0), 1, meta));
		
		if(world.rand.nextInt(100) < (isScythe ? 8 : 6))
		{
			Item nut = null;
			
			switch(meta)
			{
				case 0: nut = CWTFCItems.walnut;
			}
			
			if(nut != null)
				dropBlockAsItem(world, x, y, z, ItemFoodTFC.createTag(new ItemStack(nut), CropIndex.getWeight(4, world.rand)));
		}
			
		
		if(isScythe)
		{
			dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			if(world.rand.nextInt(100) < 30)
				dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.stick, 1));
			world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public Item getItemDropped(int i, Random rand, int j)
	{
		return Item.getItemFromBlock(CWTFCBlocks.customSapling);		
	}
}
