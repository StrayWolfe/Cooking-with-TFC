package straywolfe.cookingwithtfc.common.item;

import java.util.List;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Render.Item.FoodItemRenderer;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.MinecraftForgeClient;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.lib.ModInfo;

public class ItemTFCSalt extends ItemTFCFoodTransform
{
	public ItemTFCSalt(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, float size, float decayrate, boolean edible, boolean usable)
	{
		super(fg, sw, so, sa, bi, um, size, decayrate, edible, usable);
		this.hasSubtypes = true;
		this.metaNames = new String[]{"Rock", "Sea"};
		this.metaIcons = new IIcon[2];
		
		CWTFCItems.subfoodList.add(this);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < metaNames.length; i++)
		{
			list.add(createTag(new ItemStack(this, 1, i), this.getFoodMaxWeight(new ItemStack(this, 1)), 0));
		}
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{	
		metaIcons[0] = registerer.registerIcon(Reference.MOD_ID + ":Salt");
		metaIcons[1] = registerer.registerIcon(ModInfo.ModID + ":SeaSalt");
		MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
	}
	
	@Override
	public IIcon getIconFromDamage(int i)
	{
		if(metaNames != null && i < metaNames.length)
			return metaIcons[i];
		else
			return itemIcon;
	}
}
