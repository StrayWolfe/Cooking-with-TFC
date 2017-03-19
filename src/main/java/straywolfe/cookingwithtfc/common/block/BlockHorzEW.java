package straywolfe.cookingwithtfc.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.lib.Constants;

public class BlockHorzEW extends BlockHorzNS
{
	public BlockHorzEW()
	{
		super();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{		
		if(meta < Constants.WOODTYPES.length)
		{
			BlockNaturalLog log = (BlockNaturalLog)CWTFCBlocks.naturalLog;
			
			if(side == 0 || side == 1)
				return log.rotatedSideIcons[meta];
			else if(side == 4 || side == 5)
				return log.innerIcons[meta];
			else
				return log.rotatedSideIcons[meta];
		}
		
		return null;
	}
}
