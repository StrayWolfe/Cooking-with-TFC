package straywolfe.cookingwithtfc.common.block;

import java.util.Random;

import com.bioxx.tfc.Blocks.Flora.BlockLogNatural;
import com.bioxx.tfc.api.TFCItems;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.lib.Constants;
import straywolfe.cookingwithtfc.common.lib.ModInfo;

public class BlockNaturalLog extends BlockLogNatural
{
	private static int damage;
	private static int logs;
	
	public BlockNaturalLog()
	{
		super();
		woodNames = Constants.WOODTYPES;
		sideIcons = new IIcon[woodNames.length];
		innerIcons = new IIcon[woodNames.length];
		rotatedSideIcons = new IIcon[woodNames.length];
		setHardness(50.0F);
		setStepSound(Block.soundTypeWood);
		setBlockName("Log");
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if(!world.isRemote)
		{
			if(!world.getBlock(x, y - 1, z).isOpaqueCube())
			{
				if (noLogsNearby(world, x + 1, y, z) && noLogsNearby(world, x - 1, y, z) &&
						noLogsNearby(world, x, y, z + 1) && noLogsNearby(world, x, y, z - 1) &&
						noLogsNearby(world, x + 1, y, z + 1) && noLogsNearby(world, x + 1, y, z - 1) &&
						noLogsNearby(world, x - 1, y, z + 1) && noLogsNearby(world, x - 1, y, z - 1) &&
						noLogsNearby(world, x + 1, y - 1, z) && noLogsNearby(world, x - 1, y - 1, z) &&
						noLogsNearby(world, x, y - 1, z + 1) && noLogsNearby(world, x, y - 1, z - 1) &&
						noLogsNearby(world, x + 1, y - 1, z + 1) && noLogsNearby(world, x + 1, y - 1, z - 1) &&
						noLogsNearby(world, x - 1, y - 1, z + 1) && noLogsNearby(world, x - 1, y - 1, z - 1))
						world.setBlock(x, y, z, Blocks.air, 0, 0x2);
			}
		}
	}
	
	private boolean noLogsNearby(World world, int x, int y, int z)
	{
		return world.blockExists(x, y, z) && world.getBlock(x, y, z) != this;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		for(int i = 0; i < woodNames.length; i++)
		{
			sideIcons[i] = reg.registerIcon(ModInfo.ModID + ":Wood/" + woodNames[i] + " Log");
			innerIcons[i] = reg.registerIcon(ModInfo.ModID + ":Wood/" + woodNames[i] + " Log Top");
			rotatedSideIcons[i] = reg.registerIcon(ModInfo.ModID + ":Wood/" + woodNames[i] + " Log Side");
		}
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{
		if(!world.isRemote)
		{
			boolean isAxe = false;
			boolean isHammer = false;
			boolean isStone = false;
			ItemStack equip = entityplayer.getCurrentEquippedItem();

			if (equip != null)
			{
				int[] equipIDs = OreDictionary.getOreIDs(equip);
				for (int id : equipIDs)
				{
					String name = OreDictionary.getOreName(id);
					if (name.startsWith("itemAxe"))
					{
						isAxe = true;
						if (name.startsWith("itemAxeStone"))
						{
							isStone = true;
							break;
						}
					}
					else if (name.startsWith("itemHammer"))
					{
						isHammer = true;
						break;
					}
				}
				
				if (isAxe)
				{
					int damage = -1;
					boolean[][][] checkArray = new boolean[10 * 2 + 1][256][10 * 2 + 1];
					scanLogs(world, x, y, z, meta, checkArray, (byte)0, (byte)0, (byte)0, equip, isStone);
					
					if (damage + equip.getItemDamage() > equip.getMaxDamage())
					{
						int ind = entityplayer.inventory.currentItem;
						entityplayer.inventory.setInventorySlotContents(ind, null);
						world.setBlock(x, y, z, this, meta, 0x2);
					}
					else
						equip.damageItem(damage, entityplayer);
					
					int smallStack = logs % 16;
					dropBlockAsItem(world, x, y, z, new ItemStack(CWTFCItems.logs, smallStack, damageDropped(meta)));
					logs -= smallStack;
					
					while (logs > 0)
					{
						dropBlockAsItem(world, x, y, z, new ItemStack(CWTFCItems.logs, 16, damageDropped(meta)));
						logs -= 16;
					}
				}
				else if (isHammer)
				{
					EntityItem item = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(TFCItems.stick, 1 + world.rand.nextInt(3)));
					world.spawnEntityInWorld(item);
				}
			}
			else
				world.setBlock(x, y, z, this, meta, 0x2);
		}
	}
	
	private void scanLogs(World world, int i, int j, int k, int meta, boolean[][][] checkArray, byte x, byte y, byte z, ItemStack stack, boolean isStone)
	{
		if(y >= 0 && j + y < 256)
		{
			int offsetX = 0;int offsetY = 0;int offsetZ = 0;
			checkArray[x + 10][y][z + 10] = true;
			
			for (offsetX = -3; offsetX <= 3; offsetX++)
			{
				for (offsetZ = -3; offsetZ <= 3; offsetZ++)
				{
					for (offsetY = 0; offsetY <= 2; offsetY++)
					{
						if(Math.abs(x + offsetX) <= 10 && j + y + offsetY < 256 && Math.abs(z + offsetZ) <= 10)
						{
							if(checkOut(world, i + x + offsetX, j + y + offsetY, k + z + offsetZ, meta)
									&& !(offsetX == 0 && offsetY == 0 && offsetZ == 0)
									&& !checkArray[x + offsetX + 10][y + offsetY][z + offsetZ + 10])
								scanLogs(world,i, j, k, meta, checkArray, (byte)(x + offsetX),(byte)(y + offsetY),(byte)(z + offsetZ), stack, isStone);
						}
					}
				}
			}
			
			damage++;
			if(stack != null)
			{
				if(damage+stack.getItemDamage() <= stack.getMaxDamage())
				{
					world.setBlock(i + x, j + y, k + z, Blocks.air, 0, 0x2);
					if (!isStone || world.rand.nextInt(10) != 0)
						logs++;
					if (logs >= 16)
					{
						dropBlockAsItem(world, i + x, j + y, k + z, new ItemStack(CWTFCItems.logs, 16, damageDropped(meta)));
						logs -= 16;						
					}
					notifyLeaves(world, i + x, j + y, k + z);
				}
			}
			else
			{
				world.setBlockToAir(i, j, k);
				logs++;
				if (logs >= 16)
				{
					dropBlockAsItem(world, i, j, k, new ItemStack(CWTFCItems.logs, 16, damageDropped(meta)));
					logs -= 16;						
				}
				notifyLeaves(world, i + x, j + y, k + z);
			}
		}
	}
	
	private boolean checkOut(World world, int x, int y, int z, int meta)
	{
		return world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) == meta;
	}
	
	private void notifyLeaves(World world, int x, int y, int z)
	{
		world.notifyBlockOfNeighborChange(x + 1, y, z, Blocks.air);
		world.notifyBlockOfNeighborChange(x - 1, y, z, Blocks.air);
		world.notifyBlockOfNeighborChange(x, y, z + 1, Blocks.air);
		world.notifyBlockOfNeighborChange(x, y, z - 1, Blocks.air);
		world.notifyBlockOfNeighborChange(x, y + 1, z, Blocks.air);
		world.notifyBlockOfNeighborChange(x, y - 1, z, Blocks.air);
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion ex)
	{
		boolean[][][] checkArray = new boolean[10 * 2 + 1][256][10 * 2 + 1];
		scanLogs(world, x, y, z, world.getBlockMetadata(x, y, z), checkArray, (byte)0, (byte)0, (byte)0, null, false);
	}
	
	@Override
	public Item getItemDropped(int i, Random random, int j)
	{
		return CWTFCItems.logs;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		int meta = world.getBlockMetadata(x, y, z);
		boolean check = false;
		for(int h = -2; h <= 2; h++)
		{
			for(int g = -2; g <= 2; g++)
			{
				for(int f = -2; f <= 2; f++)
				{
					if(world.getBlock(x + h, y + g, z + f) == this && world.getBlockMetadata(x + h, y + g, z + f) == meta)
						check = true;
				}
			}
		}
		
		if(!check)
		{
			world.setBlockToAir(x, y, z);
			dropBlockAsItem(world, x, y, z, new ItemStack(CWTFCItems.logs, 1, meta));
		}
	}
}
