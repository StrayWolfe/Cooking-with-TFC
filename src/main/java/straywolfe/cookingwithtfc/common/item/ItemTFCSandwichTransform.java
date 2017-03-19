package straywolfe.cookingwithtfc.common.item;

import java.util.List;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Render.Item.FoodItemRenderer;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.MinecraftForgeClient;

public class ItemTFCSandwichTransform extends ItemTFCMealTransform
{

	public ItemTFCSandwichTransform(int sw, int so, int sa, int bi, int um, float size, float maxWt, SkillRank skillRank, String RefName) 
	{
		super(sw, so, sa, bi, um, size, maxWt, skillRank, RefName);
		this.hasSubtypes = true;
		this.metaNames = new String[]{"Barley","Corn","Oat","Rice","Rye","Wheat"};
		this.metaIcons = new IIcon[6];
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{

		list.add(createTag(new ItemStack(this), getFoodMaxWeight(new ItemStack(this)), 0, new ItemStack[]{}, new float[]{}));
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{		
		metaIcons = new IIcon[metaNames.length];
		for(int i = 0; i < metaNames.length; i++)
		{
			metaIcons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + "Sandwich " + metaNames[i]);
		}
		
		this.itemIcon = metaIcons[0];
		MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
	}
}
