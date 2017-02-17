package straywolfe.cookingwithtfc.common.block;

import com.bioxx.tfc.Blocks.BlockTerraContainer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.tileentity.TileTableStorage;

public class BlockTableStorage extends BlockTerraContainer
{
	public BlockTableStorage()
	{
		super(Material.circuits);
		setHardness(1F);
		setBlockName("TableStorage");
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1F, 1.0F);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			TileEntity tileentity = world.getTileEntity(x, y, z);
			
			if(tileentity != null && tileentity instanceof TileTableStorage && side == 1)
			{
				TileTableStorage te = (TileTableStorage)tileentity;
				ItemStack equippedItem = player.getCurrentEquippedItem();
				boolean needUpdate = false;
				
				if(hitX > 0.5F && hitZ > 0.5F)
				{
					if(handleItem(te, equippedItem, player, 3))
						needUpdate = true;
				}
				else if(hitX <= 0.5F && hitZ > 0.5F)
				{
					if(handleItem(te, equippedItem, player, 2))
						needUpdate = true;
				}
				else if(hitX > 0.5F && hitZ <= 0.5F)
				{
					if(handleItem(te, equippedItem, player, 1))
						needUpdate = true;
				}
				else if(hitX <= 0.5F && hitZ <= 0.5F)
				{
					if(handleItem(te, equippedItem, player, 0))
						needUpdate = true;
				}
				
				boolean destroyBlock = true;
				
				for(int i = 0; i < te.getSizeInventory(); i++)
				{
					if(te.getStackInSlot(i) != null)
					{
						destroyBlock = false;
						break;
					}
				}
				
				if(destroyBlock)
				{
					world.removeTileEntity(x, y, z);
					world.setBlockToAir(x, y, z);
				}
				else if(needUpdate)
					world.markBlockForUpdate(x, y, z);
			}
		}
		
		return true;
	}
	
	private boolean handleItem(TileTableStorage te, ItemStack equippedItem, EntityPlayer player, int slot)
	{
		boolean update = false;
		ItemStack tableSlot = te.getStackInSlot(slot);
		
		if(equippedItem == null)
		{
			if(player.isSneaking() && tableSlot != null && tableSlot.stackSize > 1)
			{
				ItemStack toHand = tableSlot.copy();
				ItemStack toTable = tableSlot.copy();
				
				toHand.stackSize = 1;
				toTable.stackSize--;
				player.setCurrentItemOrArmor(0, toHand);
				te.setInventorySlotContents(slot, toTable);
				update = true;
			}
			else
			{
				player.setCurrentItemOrArmor(0, tableSlot);
				te.setInventorySlotContents(slot, null);
				update = true;
			}
		}
		else if(!(equippedItem.getItem() instanceof ItemBlock))
		{
			if(tableSlot == null)
			{
				te.setInventorySlotContents(slot, equippedItem); 
				player.setCurrentItemOrArmor(0, null);
				update = true;
			}
			else if(OreDictionary.itemMatches(equippedItem, tableSlot, true))
			{
				int maxSize = tableSlot.getMaxStackSize();

				if(tableSlot.stackSize < maxSize)
				{	
					ItemStack toHand = equippedItem.copy();
					ItemStack toTable = tableSlot.copy();
					int spaceLeft = maxSize - toTable.stackSize;
					
					if(spaceLeft >= toHand.stackSize)
					{
						toTable.stackSize = toTable.stackSize + toHand.stackSize;
						toHand = null;
					}
					else
					{
						toHand.stackSize = toHand.stackSize - spaceLeft;
						toTable.stackSize = maxSize;
					}
					
					player.setCurrentItemOrArmor(0, toHand);
					te.setInventorySlotContents(slot, toTable); 
					update = true;
				}
			}
		}
		
		return update;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer) 
	{
		blockIcon = iconRegisterer.registerIcon(ModInfo.ModID + ":TableStorage");
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
		return CWTFCBlocks.tableStorageRenderID;
	}
	
	public void eject(World world, int x, int y, int z)
	{
		if (world.getTileEntity(x, y, z) instanceof TileTableStorage)
		{
			((TileTableStorage)world.getTileEntity(x, y, z)).ejectContents();
			world.removeTileEntity(x, y, z);
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
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.isRemote && world.isAirBlock(x, y - 1, z))
		{
			eject(world, x, y, z);
			world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileTableStorage();
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
}
