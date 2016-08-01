package straywolfe.cookingwithtfc.common.item;

import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBread  extends ItemTFCFoodTransform
{

	public ItemBread(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, float size) 
	{
		super(fg, sw, so, sa, bi, um, size);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{		
		return false;
	}
}
