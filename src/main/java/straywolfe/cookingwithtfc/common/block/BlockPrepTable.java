package straywolfe.cookingwithtfc.common.block;

import java.util.List;
import java.util.Random;

import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.tileentity.TileBowl;

public class BlockPrepTable extends BlockTerra
{		
	private String[] woodNames;
	
	public BlockPrepTable()
	{
		super(Material.wood);
		setHardness(1F);
		setBlockName("PrepTableBlock");
		woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0,16);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote && !player.isSneaking())
		{
			ItemStack equipped = player.getCurrentEquippedItem();
			
			if(side == 1 && equipped != null && world.isAirBlock(x, y + 1, z))
			{
				if(equipped.getItemDamage() == 1 && equipped.getItem() == TFCItems.potteryBowl)
				{
					if(world.setBlock(x, y + 1, z, CWTFCBlocks.bowlCWTFC))
					{
						float size = 0.12F;
						float xMin = hitX - size;
						float xMax = hitX + size;
						float zMin = hitZ - size;
						float zMax = hitZ + size;
						
						if(!(xMin >= 0 && xMax <= 1 && zMin >= 0 && zMax <= 1))
						{
							if(xMin < 0)
								xMin = 0.01F;
							
							if(xMax > 1)
								xMin = 1 - (0.083F * 3);
							
							if(zMin < 0)
								zMin = 0.01F;
							
							if(zMax > 1)
								zMin = 1 - (0.083F * 3);
						}
						
						TileBowl te = (TileBowl)world.getTileEntity(x, y + 1, z);						
						te.setBowlCoord(xMin + 0.12F, 0);
						te.setBowlCoord(zMin + 0.12F, 1);
						
						equipped.stackSize--;
						player.setCurrentItemOrArmor(0, equipped);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int metadata)
	{
		eject(world, x, y, z, metadata);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
    {
		eject(world, x, y, z, metadata);
    }
	
	public void eject(World world, int x, int y, int z, int metadata)
	{
		boolean flag = false;
		Block block = this;
		
		if ((block == CWTFCBlocks.prepTableN || block == CWTFCBlocks.prepTable2N) && !world.isAirBlock(x, y, z - 1))
		{
			world.setBlockToAir(x, y, z - 1);
			flag = true;
        }
		else if((block == CWTFCBlocks.prepTableS || block == CWTFCBlocks.prepTable2S) && !world.isAirBlock(x, y, z + 1))
		{
			world.setBlockToAir(x, y, z + 1);
			flag = true;
		}
		else if((block == CWTFCBlocks.prepTableE || block == CWTFCBlocks.prepTable2E) && !world.isAirBlock(x + 1, y, z))
		{
			world.setBlockToAir(x + 1, y, z);
			flag = true;
		}
		else if((block == CWTFCBlocks.prepTableW || block == CWTFCBlocks.prepTable2W) && !world.isAirBlock(x - 1, y, z))
		{
			world.setBlockToAir(x - 1, y, z);
			flag = true;
		}
		
		if(flag)
		{
			EntityItem entityitem;
			Random rand = new Random();
			float f = rand.nextFloat() * 0.8F + 0.1F;
			float f1 = rand.nextFloat() * 2.0F + 0.4F;
			float f2 = rand.nextFloat() * 0.8F + 0.1F;
	
	
			entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(Item.getItemFromBlock(block), 1, metadata));
			entityitem.motionX = (float)rand.nextGaussian() * 0.05F;
			entityitem.motionY = (float)rand.nextGaussian() * 0.1F;
			entityitem.motionZ = (float)rand.nextGaussian() * 0.05F;
			world.spawnEntityInWorld(entityitem);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List metadata)
	{
		for(int i = 0; i < woodNames.length; i++)
			metadata.add(new ItemStack(this, 1, i));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.blockIcon = register.registerIcon("planks_oak");
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return TFCBlocks.planks.getIcon(side, meta);
	}
	
	@Override
	public int getRenderType()
	{
		return CWTFCBlocks.prepTableRenderID;
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
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
    {	
		if(side == ForgeDirection.UP)
			return true;
		
		return false;
    }
}
