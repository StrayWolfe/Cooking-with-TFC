package straywolfe.cookingwithtfc.common.item;

import java.util.List;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Render.Item.FoodItemRenderer;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
		
		CWTFCItems.subfoodList.add(this);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < metaNames.length; i++)
		{
			list.add(createTag(new ItemStack(this, 1, i), this.getFoodMaxWeight(new ItemStack(this)), 0));
		}
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{	
		metaIcons = new IIcon[2];
		metaIcons[0] = registerer.registerIcon(Reference.MOD_ID + ":Salt");
		metaIcons[1] = registerer.registerIcon(ModInfo.ModID + ":Foods/SeaSalt");
		
		MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		if(stack.getItemDamage() == 0)
			return metaIcons[0];
		else
			return metaIcons[1];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		if(damage == 0)
			return metaIcons[0];
		else
			return metaIcons[1];
	}
}
