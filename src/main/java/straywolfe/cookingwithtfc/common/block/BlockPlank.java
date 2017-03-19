package straywolfe.cookingwithtfc.common.block;

import com.bioxx.tfc.Blocks.BlockPlanks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import straywolfe.cookingwithtfc.common.core.Tabs;
import straywolfe.cookingwithtfc.common.lib.Constants;
import straywolfe.cookingwithtfc.common.lib.ModInfo;

public class BlockPlank extends BlockPlanks
{
	public BlockPlank()
	{
		super(Material.wood);
		woodNames = Constants.WOODTYPES;
		setBlockName("woodPlank");
		setCreativeTab(Tabs.MAINTAB);
		icons = new IIcon[woodNames.length];
		setHardness(4.0F);
		setResistance(5.0F);
		setStepSound(Block.soundTypeWood);
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return icons[meta];
	}
	
	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < woodNames.length; i++)
			icons[i] = registerer.registerIcon(ModInfo.ModID + ":Wood/" + woodNames[i] + " Plank");
	}
}
