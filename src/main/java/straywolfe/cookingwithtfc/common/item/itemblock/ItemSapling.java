package straywolfe.cookingwithtfc.common.item.itemblock;

import java.util.List;

import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import straywolfe.cookingwithtfc.common.lib.Constants;
import straywolfe.cookingwithtfc.common.lib.ModInfo;

public class ItemSapling extends ItemTerraBlock
{
	public ItemSapling(Block b)
	{
		super(b);
		metaNames = Constants.WOODTYPES;
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
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < metaNames.length; i++)
			icons[i] = registerer.registerIcon(ModInfo.ModID + ":Wood/" + metaNames[i] + " Sapling");
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
