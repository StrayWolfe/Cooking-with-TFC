package straywolfe.cookingwithtfc.common.item.itemblock;

import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;

import net.minecraft.block.Block;
import straywolfe.cookingwithtfc.common.lib.Constants;

public class ItemCustomWood extends ItemTerraBlock
{
	public ItemCustomWood(Block b)
	{
		super(b);
		metaNames = Constants.WOODTYPES;
	}
}
