package straywolfe.cookingwithtfc.common.block;

import java.util.Random;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.Flora.BlockFruitLeaves;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.FloraIndex;
import com.bioxx.tfc.Food.FloraManager;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.Util.Helper;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.CookingWithTFC;
import straywolfe.cookingwithtfc.common.lib.Constants;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.tileentity.TileFruitLeaves;

public class BlockNutLeaves extends BlockFruitLeaves
{
	protected String[] woodNames;
	protected IIcon[] icons;
	protected IIcon[] iconsOpaque;
	protected IIcon[] iconsFruit;
	protected IIcon[] iconsFlowers;
	protected IIcon[] iconPalm;
	
	public BlockNutLeaves()
	{
		super(0);
		setBlockName("NutTreeLeaves");
		setHardness(0.5F);
		setResistance(1F);
		setStepSound(Block.soundTypeGrass);
		woodNames = new String[Constants.NUTTREETYPES.length];
		woodNames = Constants.NUTTREETYPES.clone();
		icons = new IIcon[2];
		iconsOpaque = new IIcon[2];
		iconPalm = new IIcon[3];
		iconsFruit = new IIcon[5];
		iconsFlowers = new IIcon[5];
	}
	
	@Override
	public int colorMultiplier(IBlockAccess bAccess, int x, int y, int z)
	{
		return CookingWithTFC.proxy.foliageColorMultiplier(bAccess, x, y, z);
	}
	
	@Override
	public int getRenderType()
	{
		return CWTFCBlocks.nutLeavesRenderID;
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		meta = meta < 8 ? meta : meta - 8;
		
		if (TerraFirmaCraft.proxy.getGraphicsLevel())
		{
			if(meta > 2)
				return icons[0];
			else if(meta == 2)
				return iconPalm[0];
			else
				return icons[meta];
		}
		else
		{
			if(meta > 2)
				return iconsOpaque[0];
			else if(meta == 2)
				return iconPalm[0];
			else
				return iconsOpaque[meta];
		}
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		icons[0] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Almond Leaves");
		icons[1] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Cashew Leaves");		
		
		iconsOpaque[0] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Almond Leaves Opaque");
		iconsOpaque[1] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Cashew Leaves Opaque");
		
		iconPalm[0] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Coconut Leaves 1");
		iconPalm[1] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Coconut Leaves 2");
		iconPalm[2] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Coconut");
		
		iconsFruit[0] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Almond Nut");
		iconsFruit[1] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Cashew");
		iconsFruit[2] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Hazelnuts");
		iconsFruit[3] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Macadamia Nuts");
		iconsFruit[4] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Pistachios");
		
		iconsFlowers[0] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Almond Flowers");
		iconsFlowers[1] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Cashew Flowers");
		iconsFlowers[2] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Hazelnut Flowers");
		iconsFlowers[3] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Macadamia Flowers");
		iconsFlowers[4] = iconRegisterer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/Pistachio Flowers");
	}
	
	public IIcon getFruitIcon(int meta)
	{
		switch(meta)
		{
			case 0: return iconsFruit[0];
			case 1: return iconsFruit[1];
			case 3: return iconsFruit[2];
			case 4: return iconsFruit[3];
			case 5: return iconsFruit[4];
			default: return iconsFruit[0];
		}
	}
	
	public IIcon getFlowerIcon(int meta)
	{
		switch(meta)
		{
			case 0: return iconsFlowers[0];
			case 1: return iconsFlowers[1];
			case 3: return iconsFlowers[2];
			case 4: return iconsFlowers[3];
			case 5: return iconsFlowers[4];
			default: return iconsFlowers[0];
		}
	}
	
	public IIcon getPalmIcon(int type)
	{
		return iconPalm[type];
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		lifeCycle(world, x, y, z);
	}
	
	public void lifeCycle(World world, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			if (!canStay(world, x, y, z))
			{
				world.setBlockToAir(x, y, z);
				return;
			}
			
			Random rand = new Random();
			int meta = world.getBlockMetadata(x, y, z);
			int m = meta - 8;

			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = manager.findMatchingIndex(getTreeType(this, m));
			FloraIndex fi2 = manager.findMatchingIndex(getTreeType(this, meta));

			float temp = TFC_Climate.getHeightAdjustedTemp(world, x, y, z);
			TileFruitLeaves te = (TileFruitLeaves) world.getTileEntity(x, y, z);
			
			if(te != null)
			{
				if(fi2 != null)
				{
					if(temp >= fi2.minTemp && temp < fi2.maxTemp)
					{
						if (fi2.inHarvest(TFC_Time.getSeasonAdjustedMonth(z)) && !te.hasFruit && TFC_Time.getMonthsSinceDay(te.dayHarvested) > 1)
						{
							if(meta < 8)
							{
								meta += 8;
								te.hasFruit = true;
								te.dayFruited = TFC_Time.getTotalDays();
							}
							world.setBlockMetadataWithNotify(x, y, z, meta, 3);
						}
					}
					else
					{
						if(meta > 7 && rand.nextInt(10) == 0 && te.hasFruit)
						{
							te.hasFruit = false;
							world.setBlockMetadataWithNotify(x, y, z, meta - 8, 3);
						}
					}
				}
				
				if(fi != null && !fi.inHarvest(TFC_Time.getSeasonAdjustedMonth(z)) && 
						world.getBlockMetadata(x, y, z) > 7 && te.hasFruit)
				{
					te.hasFruit = false;
					world.setBlockMetadataWithNotify(x, y, z, meta - 8, 3); 
				}
				
				if (rand.nextInt(100) > 50)
					world.markBlockForUpdate(x, y, z);
			}
		}
	}
	
	public static boolean canStay(World world, int x, int y, int z)
	{		
		if(world.getBlockMetadata(x, y, z) == 2)
		{
			for (int i = 1; i >= -1; i--)
			{
				for (int k = 1; k >= -1; k--)
				{
					if (world.getBlock(i + x, y, k + z) == CWTFCBlocks.nutTreeLog &&
							world.getBlock(i + x, y + 1, k + z) != CWTFCBlocks.nutTreeLog)
						return true;
				}
			}
		}
		else
		{
			for (int i = 1; i >= -1; i--)
			{
				for (int j = 0; j >= -1; j--)
				{
					for (int k = 1; k >= -1; k--)
					{
						if (world.getBlock(i + x, j + y, k + z) == CWTFCBlocks.nutTreeLog &&
								world.getBlock(i + x, j + y + 1, k + z) != CWTFCBlocks.nutTreeLog)
							return true;
					}
				}
			}
		}
		
		return false;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		lifeCycle(world, x, y, z);
	}
	
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityplayer)
	{
		dropFruit(world, x, y, z);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		return dropFruit(world, x, y, z);
	}
	
	public String getTreeType(Block block, int meta)
	{
		meta = meta < 8 ? meta : meta - 8;
		
		if(meta < woodNames.length && meta >= 0)
		{
			return woodNames[meta];
		}
		
		return "";
	}
	
	public boolean dropFruit(World world, int x, int y,int z)
	{
		if (!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = manager.findMatchingIndex(getTreeType(this, meta));
			
			if (fi != null && (fi.inHarvest(TFC_Time.getSeasonAdjustedMonth(z)) || fi.inHarvest((TFC_Time.getSeasonAdjustedMonth(z) + 11) % 12) && meta > 7))
			{
				TileFruitLeaves te = (TileFruitLeaves) world.getTileEntity(x, y, z);
				if (te != null && te.hasFruit)
				{
					te.hasFruit = false;
					te.dayHarvested = TFC_Time.getTotalDays();
					world.setBlockMetadataWithNotify(x, y, z, meta - 8, 3);
					dropBlockAsItem(world, x, y, z, ItemFoodTFC.createTag(fi.getOutput(), Helper.roundNumber(4 + (world.rand.nextFloat() * 12), 10)));
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileFruitLeaves();
	}
	
	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int l, EntityPlayer player)
	{
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta == 2 || meta == 10)
		{
			ItemStack equip = player.getCurrentEquippedItem();
		
			if (equip != null)
			{
				boolean isAxeorSaw = false;
				int[] equipIDs = OreDictionary.getOreIDs(equip);
				
				for (int id : equipIDs)
				{
					String name = OreDictionary.getOreName(id);
					if (name.startsWith("itemAxe") || name.startsWith("itemSaw"))
					{
						isAxeorSaw = true;
						break;
					}
				}
				
				if(isAxeorSaw && world.rand.nextInt(6) == 0)
					dropBlockAsItem(world, x, y, z, new ItemStack(CWTFCBlocks.nutTreeLog, 1, 2));
			}
		}
	}
}
