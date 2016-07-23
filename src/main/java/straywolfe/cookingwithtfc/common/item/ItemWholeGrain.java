package straywolfe.cookingwithtfc.common.item;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.tileentity.TileGrains;

public class ItemWholeGrain extends ItemTFCFoodTransform
{

	public ItemWholeGrain(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, float size, float decayrate, boolean edible, boolean usable) 
	{
		super(fg, sw, so, sa, bi, um, size, decayrate, edible, usable);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{		
		if(!world.isRemote)
		{
			Block id = world.getBlock(x, y, z);
			Material mat = id.getMaterial();

			if(side == 1 && id.isSideSolid(world, x, y, z, ForgeDirection.UP) &&!TFC_Core.isSoil(id) && !TFC_Core.isWater(id) 
				&& world.isAirBlock(x, y + 1, z) && (mat == Material.wood || mat == Material.rock || mat == Material.iron))
			{
				if(world.setBlock(x, y + 1, z, CWTFCBlocks.GrainsBlock))
				{
					((TileGrains) world.getTileEntity(x, y + 1, z)).setplacedGrains(itemstack);
					player.setCurrentItemOrArmor(0, null);
					return true;
				}
			}
		}
		return false;
	}
}
