package straywolfe.cookingwithtfc.common.block;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class BlockPrepTable2 extends BlockPrepTable
{
	private String[] woodNames;
	
	public BlockPrepTable2()
	{
		super();
		woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length - 16);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return TFCBlocks.planks2.getIcon(side, meta - 16);
	}
}
