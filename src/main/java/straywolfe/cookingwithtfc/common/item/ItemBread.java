package straywolfe.cookingwithtfc.common.item;

import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.block.BlockPrepTable;
import straywolfe.cookingwithtfc.common.block.BlockPrepTable2;
import straywolfe.cookingwithtfc.common.tileentity.TileSandwich;

public class ItemBread  extends ItemTFCFoodTransform
{

	public ItemBread(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, float size) 
	{
		super(fg, sw, so, sa, bi, um, size);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote && side == 1 && !player.isSneaking() && world.isAirBlock(x, y + 1, z))
		{
			ItemStack knife = null;
			boolean knifeNotNeeded = false;
			ItemStack equipped = player.getCurrentEquippedItem();
			int slot = 0;
			
			if(Food.getWeight(equipped) - 1 <= 0)
				knifeNotNeeded = true;
			
			if(!knifeNotNeeded)
			{
				for(int i = 0; i < 9; i++)
				{
					ItemStack item = player.inventory.getStackInSlot(i);
					if(item != null && item.getItem() instanceof ItemKnife)
					{
						slot = i;
						knife = item;
						break;
					}
				}
			}
			
			if(knife != null || knifeNotNeeded)
			{
				Block block = world.getBlock(x, y, z);
				if(block != null && (block instanceof BlockPrepTable || block instanceof BlockPrepTable2))
				{
					if(world.setBlock(x, y + 1, z, CWTFCBlocks.sandwichCWTFC))
					{
						TileSandwich te = (TileSandwich)world.getTileEntity(x, y + 1, z);
						
						float size = 0.2F;
						float xMin = hitX - size;
						float xMax = hitX + size;
						float zMin = hitZ - size;
						float zMax = hitZ + size;
						
						if(!(xMin >= 0 && xMax <= 1 && zMin >= 0 && zMax <= 1))
						{
							if(xMin < 0)
								xMin = 0;
							
							if(xMax > 1)
								xMin = 1 - (size * 2);
							
							if(zMin < 0)
								zMin = 0;
							
							if(zMax > 1)
								zMin = 1 - (size * 2);
						}
						
						ItemStack bread = equipped.copy();
						
						if(Food.getDecay(equipped) > 0)
						{
							Food.setWeight(equipped, Food.getWeight(equipped) - Food.getDecay(equipped) - 1);
							Food.setDecay(equipped, 0);
						}
						else
							Food.setWeight(equipped, Food.getWeight(equipped) - 1);
						
						Food.setWeight(bread, 1);
						te.setTopSandwichItem(bread);
						te.setSandwichCoord(xMin, 0);
						te.setSandwichCoord(zMin, 1);
						
						if(!knifeNotNeeded)
						{
							knife.setItemDamage(knife.getItemDamage() + 1);
							if(knife.getItemDamage() > knife.getMaxDamage())
								player.inventory.setInventorySlotContents(slot, null);
						}
						
						return true;
					}
				}
			}
		}
		
		return false;
	}
}
