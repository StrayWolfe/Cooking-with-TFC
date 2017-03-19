package straywolfe.cookingwithtfc.common.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import straywolfe.cookingwithtfc.api.CWTFCItems;

public class Tabs extends CreativeTabs
{
	public static final CreativeTabs MAINTAB = new Tabs("CWTFCMain");
	
	public Tabs(String name)
	{
		super(name);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getTabIconItem()
	{
		return CWTFCItems.celery;
	}
	
	@Override
	public ItemStack getIconItemStack()
	{
		return new ItemStack(CWTFCItems.celery);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel()
	{
		return StatCollector.translateToLocal("itemGroup." + this.getTabLabel());
	}
}
