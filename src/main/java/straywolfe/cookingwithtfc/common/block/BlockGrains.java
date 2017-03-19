package straywolfe.cookingwithtfc.common.block;

import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.tileentity.TileGrains;

public class BlockGrains extends BlockTerraContainer
{			
	@SideOnly(Side.CLIENT)
	private IIcon[] barleyIcon;
	@SideOnly(Side.CLIENT)
	private IIcon[] oatIcon;
	@SideOnly(Side.CLIENT)
	private IIcon[] riceIcon;
	@SideOnly(Side.CLIENT)
	private IIcon[] ryeIcon;
	@SideOnly(Side.CLIENT)
	private IIcon[] wheatIcon;
	
	public BlockGrains()
	{
		super();
		this.setStepSound(soundTypeGrass);
		this.setHardness(0.5F);
		this.setCreativeTab(null);
		this.setBlockName("GrainsBlock");
		this.setBlockBounds(0, 0, 0, 1, 0.05f, 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int meta)
	{
		if(access.getTileEntity(x, y, z) instanceof TileGrains)
		{
			TileGrains te = (TileGrains) access.getTileEntity(x, y, z);
				
			if(te.getplacedGrains() != null)
			{
				if(te.getplacedGrains().getItem() == TFCItems.barleyWhole)
					return barleyIcon[te.stage];
				else if(te.getplacedGrains().getItem() == TFCItems.oatWhole)
					return oatIcon[te.stage];
				else if(te.getplacedGrains().getItem() == TFCItems.riceWhole)
					return riceIcon[te.stage];
				else if(te.getplacedGrains().getItem() == TFCItems.ryeWhole)
					return ryeIcon[te.stage];
				else if(te.getplacedGrains().getItem() == TFCItems.wheatWhole)
					return wheatIcon[te.stage];
			}
		}
		return TFC_Textures.invisibleTexture;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		barleyIcon = new IIcon[4];
		oatIcon = new IIcon[4];
		riceIcon = new IIcon[4];
		ryeIcon = new IIcon[4];
		wheatIcon = new IIcon[4];
		
		for(int i = 1; i < 5; i++)
		{
			barleyIcon[i - 1] = iconRegisterer.registerIcon(ModInfo.ModID + ":Grains/Barley (" + i + ")");
			oatIcon[i - 1] = iconRegisterer.registerIcon(ModInfo.ModID + ":Grains/Oat (" + i + ")");
			riceIcon[i - 1] = iconRegisterer.registerIcon(ModInfo.ModID + ":Grains/Rice (" + i + ")");
			ryeIcon[i - 1] = iconRegisterer.registerIcon(ModInfo.ModID + ":Grains/Rye (" + i + ")");
			wheatIcon[i - 1] = iconRegisterer.registerIcon(ModInfo.ModID + ":Grains/Wheat (" + i + ")");
		}
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
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+0.05, z+1);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileGrains();
	}
	
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityplayer)
	{
		if(!world.isRemote && world.getTileEntity(x, y, z) instanceof TileGrains)
		{
			TileGrains te = (TileGrains)world.getTileEntity(x, y, z);
			if(te.processGrains())
			{
				eject(world, x, y, z);
				world.setBlockToAir(x, y, z);
			}
		}
	}
	
	public void eject(World world, int x, int y, int z)
	{
		if (world.getTileEntity(x, y, z) instanceof TileGrains)
		{
			TileGrains te = (TileGrains)world.getTileEntity(x, y, z);
			te.ejectItem();
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
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return true;
	}
}
