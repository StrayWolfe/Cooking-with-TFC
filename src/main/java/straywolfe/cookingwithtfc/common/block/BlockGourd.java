package straywolfe.cookingwithtfc.common.block;

import java.util.Random;

import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.lib.Settings;
import straywolfe.cookingwithtfc.common.tileentity.TileGourd;

public class BlockGourd extends BlockTerraContainer
{		
	public BlockGourd()
	{
		super(Material.gourd);
		setBlockName("Gourd");
		setHardness(0.5F);
		setTickRandomly(true);
		setLightLevel(1.0F);
		setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.625F, 0.8125F);
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{		
		if(world.getBlockMetadata(x, y, z) == 3)
			return getLightValue();
		
		return 0;
	}
	
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) 
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			ItemStack is = player.getCurrentEquippedItem();
			Item item = is != null ? is.getItem() : null;
			
			if(meta > 1)
			{
				TileEntity tileentity = world.getTileEntity(x, y, z);				
				if(tileentity instanceof TileGourd)
				{
					if(item == Item.getItemFromBlock(TFCBlocks.torch))
					{
						((TileGourd)tileentity).resetHourPlaced();
						if(meta == 2)
							world.setBlockMetadataWithNotify(x, y, z, 3, 3);
					}
					else if(item == null)
					{
						((TileGourd)tileentity).setHourPlaced(-1);
						if(meta == 3)
							world.setBlockMetadataWithNotify(x, y, z, 2, 3);
					}
				}
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			ItemStack is = player.getCurrentEquippedItem();
			Item item = is != null ? is.getItem() : null;
			
			if(meta < 2 && (item != null && item instanceof ItemKnife))
			{
				ItemStack seeds = null;
				int skill = 20 - (int) (20 * TFC_Core.getSkillStats(player).getSkillMultiplier(Global.SKILL_AGRICULTURE));
				int stackSize = 1 + (world.rand.nextInt(1 + skill) == 0 ? 1 : 0);
				
				if(meta == 0)
				{
					seeds = new ItemStack(CWTFCItems.seedsPumpkin, stackSize);
					
					ItemStack product = null;
					
					if(Settings.tfcJackOLantern)
						product = new ItemStack(Item.getItemFromBlock(TFCBlocks.litPumpkin)); 
					else
						product = new ItemStack(CWTFCItems.jackolanternBlock);
					
					if(product != null)
						world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, product));
				}
				else if(meta == 1)
				{
					
					seeds = new ItemStack(CWTFCItems.seedsMelon, stackSize);
					
					if(world.getTileEntity(x, y, z) instanceof TileGourd)
					{
						ItemStack product = ((TileGourd)world.getTileEntity(x, y, z)).getFruit();
						
						TFC_Core.tickDecay(product, world, x, y, z, 1.1f, 1f);
						
						if(product != null)
						{
							for(int i = 0; i < 4; i++)
								world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, product));
						}
					}
				}
				
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, seeds));
				
				world.setBlockToAir(x, y, z);
				
				is.setItemDamage(is.getItemDamage() + 1);
				if(is.getItemDamage() > is.getMaxDamage())
					player.setCurrentItemOrArmor(0, null);
				
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{		
		if (!world.isRemote)
		{		
			int meta = world.getBlockMetadata(x, y, z);
			if(meta > 1 && world.getTileEntity(x, y, z) instanceof TileGourd)
			{
				TileGourd te = (TileGourd)world.getTileEntity(x, y, z);
				if (te.getHourPlaced() > 0 && !canEmitLight(te))
				{
					te.setHourPlaced(-1);
					if(meta == 3)
						world.setBlockMetadataWithNotify(x, y, z, 2, 3);
				}
			}
		}
	}
	
	public boolean canEmitLight(TileGourd te)
	{
		boolean infinite = Settings.lanternLifespan == 0;
		boolean pastLifeSpan = TFC_Time.getTotalHours() > te.getHourPlaced() + Settings.lanternLifespan;
		return infinite || !pastLifeSpan;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileGourd();
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
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
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata) 
	{
		dropItem(world, x, y, z, metadata);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		if(!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z))
		{
			int metadata = world.getBlockMetadata(x, y, z);
			
			dropItem(world, x, y, z, metadata);
			world.setBlockToAir(x, y, z);
		}
	}
	
	private void dropItem(World world, int x, int y, int z, int metadata)
	{
		if(!world.isRemote)
		{
			Random rand = new Random();
			ItemStack gourd = null;
			EntityItem entityitem;
			
			float f = rand.nextFloat() * 0.8F + 0.1F;
			float f1 = rand.nextFloat() * 0.8F + 0.3F;
			float f2 = rand.nextFloat() * 0.8F + 0.1F;
			
			switch(metadata)
			{
				case 0: gourd = new ItemStack(CWTFCItems.pumpkinBlock); break;
				case 1: gourd = new ItemStack(CWTFCItems.melonBlock); break;
				case 2: gourd = new ItemStack(CWTFCItems.jackolanternBlock); break;
				default: gourd = new ItemStack(CWTFCItems.pumpkinBlock); break;
			}
			
			entityitem = new EntityItem(world, x + f, y + f1, z + f2, gourd);
			entityitem.motionX = (float)rand.nextGaussian() * 0.05F;
			entityitem.motionY = (float)rand.nextGaussian() * 0.05F + 0.2F;
			entityitem.motionZ = (float)rand.nextGaussian() * 0.05F;
			world.spawnEntityInWorld(entityitem);
		}
	}
	
	@Override
	public int quantityDropped(Random rand)
	{
		return 0;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
	{
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		BlockCrop block = (BlockCrop)CWTFCBlocks.customCrop;
		
		if(meta == 1)
			return block.getCropIcon("Melon_Side");
		else
			return block.getCropIcon("Pumpkin_Side");
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {}
}
