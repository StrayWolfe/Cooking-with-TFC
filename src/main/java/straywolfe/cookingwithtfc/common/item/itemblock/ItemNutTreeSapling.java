package straywolfe.cookingwithtfc.common.item.itemblock;

import java.util.List;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.lib.Constants;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.tileentity.TileNutTree;

public class ItemNutTreeSapling extends ItemTerraBlock
{
	public ItemNutTreeSapling(Block b)
	{
		super(b);
		metaNames = Constants.NUTTREETYPES;
		icons = new IIcon[metaNames.length];
		setHasSubtypes(true);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < metaNames.length; i++)
			list.add(new ItemStack(this,1,i));
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote && side == 1)
		{
			Block block = world.getBlock(x, y, z);
			
			if(block.isNormalCube() && block.isOpaqueCube() && TFC_Core.isSoil(block) && world.isAirBlock(x, y + 1, z))
			{
				world.setBlock(x, y + 1, z, CWTFCBlocks.nutTreeLog, stack.getItemDamage(), 4);
				if(world.getTileEntity(x, y + 1, z) instanceof TileNutTree)
				{
					TileNutTree te = (TileNutTree)world.getTileEntity(x, y + 1, z);
					te.setTrunk(true);
					te.isSapling = 1;
					world.markBlockForUpdate(x, y + 1, z);
					--stack.stackSize;
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < metaNames.length; i++)
			icons[i] = registerer.registerIcon(ModInfo.ModID + ":Wood/Fruit Trees/" + metaNames[i] + " Sapling");
	}
	
	@Override
	public IIcon getIconFromDamage(int index)
	{
		return icons[index];
	}
	
	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}
}
