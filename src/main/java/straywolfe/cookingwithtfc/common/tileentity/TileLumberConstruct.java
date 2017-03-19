package straywolfe.cookingwithtfc.common.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.TileEntities.TEWoodConstruct;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import straywolfe.cookingwithtfc.api.CWTFCItems;

public class TileLumberConstruct extends TEWoodConstruct
{
	public TileLumberConstruct()
	{
		super();
	}
	
	@Override
	public void ejectContents()
	{
		for(int i = 0; i < 192; i++)
		{
			if(data.get(i))
			{
				data.clear(i);
				ItemStack stack = new ItemStack(CWTFCItems.singlePlank, 1, woodTypes[i]);
				EntityItem e = new EntityItem(worldObj, xCoord, yCoord, zCoord, stack);
				e.delayBeforeCanPickup = 5;
				worldObj.spawnEntityInWorld(e);
			}
		}
	}
	
	@Override
	public List<ItemStack> getDrops()
	{
		List<ItemStack> list = new ArrayList<ItemStack>();
		for(int i = 0; i < 192; i++)
		{
			if(data.get(i))
			{
				ItemStack stack = new ItemStack(CWTFCItems.singlePlank, 1, woodTypes[i]);
				list.add(stack);
			}
		}
		
		return list;
	}
}
