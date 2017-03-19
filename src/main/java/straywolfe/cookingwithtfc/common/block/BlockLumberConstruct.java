package straywolfe.cookingwithtfc.common.block;

import com.bioxx.tfc.Blocks.BlockWoodConstruct;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.tileentity.TileLumberConstruct;

public class BlockLumberConstruct extends BlockWoodConstruct
{
	public BlockLumberConstruct()
	{
		super();
		setHardness(4F);
		setStepSound(Block.soundTypeWood);
		setBlockName("LumberConstruct");
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileLumberConstruct();
	}
	
	@Override
	public int getRenderType()
	{
		return CWTFCBlocks.lumberConstructRenderID;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return CWTFCBlocks.woodPlank.getIcon(side, meta);
	}
}
