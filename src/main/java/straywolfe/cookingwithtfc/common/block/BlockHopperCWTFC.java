package straywolfe.cookingwithtfc.common.block;

import com.bioxx.tfc.Blocks.Devices.BlockHopper;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.common.tileentity.TileHopperCWTFC;

public class BlockHopperCWTFC extends BlockHopper
{
	
	public BlockHopperCWTFC()
	{
		super();
		this.setHardness(2F);
		this.setCreativeTab(null);
		this.setBlockName("Hopper");
	}

	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileHopperCWTFC();
	}
}
