package straywolfe.cookingwithtfc.common.block;

import java.util.Random;

import com.bioxx.tfc.Blocks.Flora.BlockLogVert;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.lib.Constants;

public class BlockVertLog extends BlockLogVert
{
	public BlockVertLog()
	{
		super();
		woodNames = Constants.WOODTYPES;
		setBlockName("WoodVert");
		setHardness(20);
		setResistance(15F);
		setStepSound(Block.soundTypeWood);
		setHarvestLevel("axe", 1);
		setHarvestLevel("hammer", 1);
	}
	
	@Override
	public Item getItemDropped(int i, Random r, int j)
	{
		return CWTFCItems.logs;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return CWTFCBlocks.naturalLog.getIcon(side, meta);
	}
}
