package straywolfe.cookingwithtfc.common.block;

import com.bioxx.tfc.Blocks.Devices.BlockNestBox;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.common.tileentity.TileNestBoxCWTFC;

public class BlockNestBoxCWTFC extends BlockNestBox
{
	public BlockNestBoxCWTFC()
	{
		super();
		this.setBlockName("NestBox");
		this.setHardness(1);
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileNestBoxCWTFC();
	}
}
