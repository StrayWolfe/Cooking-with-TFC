package straywolfe.cookingwithtfc.common.block;

import java.util.Random;

import com.bioxx.tfc.Blocks.Flora.BlockSapling;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.TESapling;
import com.bioxx.tfc.api.TFCOptions;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import straywolfe.cookingwithtfc.common.core.Tabs;
import straywolfe.cookingwithtfc.common.lib.Constants;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.worldgen.WorldGenShortTrees;

public class BlockCustomSapling extends BlockSapling
{
	public BlockCustomSapling()
	{
		super();
		woodNames = Constants.WOODTYPES;
		setHardness(0.0F);
		setStepSound(Block.soundTypeGrass);
		setBlockName("Sapling");
		setCreativeTab(Tabs.MAINTAB);
		icons = new IIcon[woodNames.length];
	}
	
	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < woodNames.length; i++)
			icons[i] = registerer.registerIcon(ModInfo.ModID + ":Wood/" + woodNames[i] + " Sapling");
	}
	
	@Override
	public void growTree(World world, int i, int j, int k, Random rand, long timestamp)
	{
		int meta = world.getBlockMetadata(i, j, k);
		world.setBlockToAir(i, j, k);
		WorldGenerator worldGen = new WorldGenShortTrees(false, meta);
		
		if (worldGen != null && !worldGen.generate(world, rand, i, j, k))
		{
			world.setBlock(i, j, k, this, meta, 3);
			if (world.getTileEntity(i, j, k) instanceof TESapling)
			{
				TESapling te = (TESapling) world.getTileEntity(i, j, k);
				te.growTime = timestamp;
				te.enoughSpace = false;
				te.markDirty();
			}
		}
	}
	
	@Override
	public void onBlockAdded(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		float growSpeed = 1;
		if(meta == 0)
			growSpeed = 1.4f;
		
		if (world.getTileEntity(i, j, k) instanceof TESapling)
		{
			TESapling te = (TESapling) world.getTileEntity(i, j, k);
			
			if (te != null && te.growTime == 0)
				te.growTime = (long) (TFC_Time.getTotalTicks() + (TFC_Time.DAY_LENGTH * 7 * growSpeed * TFCOptions.saplingTimerMultiplier) + (world.rand.nextFloat() * TFC_Time.DAY_LENGTH));
		}
	}
}
