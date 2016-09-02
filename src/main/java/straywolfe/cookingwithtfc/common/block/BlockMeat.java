package straywolfe.cookingwithtfc.common.block;

import java.util.List;

import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.api.Food;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.tileentity.TileMeat;

public class BlockMeat extends BlockTerraContainer
{	
	public BlockMeat()
	{
		super(Material.cake);
		setHardness(1F);
		setBlockName("MeatsBlock");
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if(!player.isSneaking() && world.getTileEntity(x, y, z) instanceof TileMeat)
			{
				TileMeat te = (TileMeat)world.getTileEntity(x, y, z);
				ItemStack item = player.getCurrentEquippedItem();
				
				if(item == null)
				{
					player.setCurrentItemOrArmor(0, te.getplacedMeat());
					world.removeTileEntity(x, y, z);
					world.setBlockToAir(x, y, z);
				}
				else if(item.getItem() == CWTFCItems.Salt)
				{
					Food.setWeight(item, te.saltMeat(item));
					if(te.getFlag())
					{
						world.removeTileEntity(x, y, z);
						world.setBlockToAir(x, y, z);
					}
				}
			}
		}
		return true;
	}
	
	public void eject(World world, int x, int y, int z)
	{
		if (world.getTileEntity(x, y, z) instanceof TileMeat)
		{
			((TileMeat)world.getTileEntity(x, y, z)).ejectItem();
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
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileMeat();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer) {}
	
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
		return CWTFCBlocks.meatRenderID;
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
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {		
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(tileentity != null && tileentity instanceof TileMeat)
		{
			TileMeat te = (TileMeat)tileentity;
			float xCoord = te.getMeatCoord(0);
			float zCoord = te.getMeatCoord(1);
			
			if(xCoord != -1 && zCoord != -1)
			{
				float minX = xCoord;
				float maxX = xCoord + 0.5F;
				float minY = 0.0F;
				float maxY = 0.09F;
				float minZ = zCoord;
				float maxZ = zCoord + 0.5F;
				
				setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
			}
			else
				setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.00F, 0.0F);
		}
    }
    
    @SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
	{
    	TileEntity tileentity = world.getTileEntity(x, y, z);
		if(tileentity != null && tileentity instanceof TileMeat)
		{
	    	TileMeat te = (TileMeat)tileentity;
			float xCoord = te.getMeatCoord(0);
			float zCoord = te.getMeatCoord(1);
			
			if(xCoord != -1 && zCoord != -1)
			{
				float minX = xCoord;
				float maxX = xCoord + 0.5F;
				float minY = 0.0F;
				float maxY = 0.09F;
				float minZ = zCoord;
				float maxZ = zCoord + 0.5F;
				
				setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
				super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
				setBlockBoundsBasedOnState(world, x, y, z);
			}
			else
			{
				setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.00F, 0.0F);
				super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
				setBlockBoundsBasedOnState(world, x, y, z);
			}
		}
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
