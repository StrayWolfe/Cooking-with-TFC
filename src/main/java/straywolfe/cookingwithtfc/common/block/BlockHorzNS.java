package straywolfe.cookingwithtfc.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.lib.Constants;

public class BlockHorzNS extends BlockVertLog
{
	public BlockHorzNS()
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
				return log.sideIcons[meta];
			else if(side == 2 || side == 3)
				return log.innerIcons[meta];
			else
				return log.rotatedSideIcons[meta];
		}
		
		return null;
	}
	
	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes" })
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityliving)
	{
		int dir = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
		int metadata = world.getBlockMetadata(x, y, z);

		if(dir == 1 || dir == 3)
			world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
	}
}
