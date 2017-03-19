package straywolfe.cookingwithtfc.common.item.itemblock;

import java.util.List;

import com.bioxx.tfc.Items.ItemLogs;
import com.bioxx.tfc.TileEntities.TELogPile;
import com.bioxx.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.lib.ModInfo;

public class ItemLog extends ItemLogs
{
	public IIcon[] icons;
	
	public ItemLog()
	{
		super();
		metaNames = new String[]{"Walnut"};
		icons = new IIcon[metaNames.length];
		setUnlocalizedName("Log");
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < metaNames.length; i++)
			list.add(new ItemStack(this, 1, i));
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < metaNames.length; i++)
			icons[i] = registerer.registerIcon(ModInfo.ModID + ":wood/" + metaNames[i] + " Log");
	}
	
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		if(meta < icons.length)
			return icons[meta];
		return icons[0];
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if (entityplayer.isSneaking() && (world.getBlock(x, y, z) != TFCBlocks.logPile || side != 1 && side != 0))
			{
				int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;
				if (side == 0)
					--y;
				else if (side == 1)
					++y;
				else if (side == 2)
					--z;
				else if (side == 3)
					++z;
				else if (side == 4)
					--x;
				else if (side == 5)
					++x;
				if(world.canPlaceEntityOnSide(TFCBlocks.logPile, x, y, z, false, side, entityplayer, itemstack))
					if (createPile(itemstack, entityplayer, world, x, y, z, side, dir)) 
					{
						itemstack.stackSize = itemstack.stackSize-1;
						playSound(world, x, y, z);
					}
				return true;
			}
			else if(world.getBlock(x, y, z) == TFCBlocks.logPile)
			{
				TELogPile te = (TELogPile)world.getTileEntity(x, y, z);
				if(te != null)
				{
					if(te.storage[0] != null && te.contentsMatch(0,itemstack)) {
						te.injectContents(0,1);
					} else if(te.storage[0] == null) {
						te.addContents(0, new ItemStack(this,1, itemstack.getItemDamage()));
					} else if(te.storage[1] != null && te.contentsMatch(1,itemstack)) {
						te.injectContents(1,1);
					} else if(te.storage[1] == null) {
						te.addContents(1, new ItemStack(this,1, itemstack.getItemDamage()));
					} else if(te.storage[2] != null && te.contentsMatch(2,itemstack)) {
						te.injectContents(2,1);
					} else if(te.storage[2] == null) {
						te.addContents(2, new ItemStack(this,1, itemstack.getItemDamage()));
					} else if(te.storage[3] != null && te.contentsMatch(3,itemstack)) {
						te.injectContents(3,1);
					} else if(te.storage[3] == null) {
						te.addContents(3, new ItemStack(this,1, itemstack.getItemDamage()));
					} else
					{
						int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;
						if (side == 0)
							--y;
						else if (side == 1)
							++y;
						else if (side == 2)
							--z;
						else if (side == 3)
							++z;
						else if (side == 4)
							--x;
						else if (side == 5)
							++x;
						if (!createPile(itemstack, entityplayer, world, x, y, z, side, dir)) {
							return true;
						}

					}
					playSound(world, x, y, z);
					itemstack.stackSize = itemstack.stackSize-1;
					return true;
				}
			}
			else
			{
				int meta = itemstack.getItemDamage();
				Block block = CWTFCBlocks.woodVert;
				
				if(side == 0 && block.canPlaceBlockAt(world, x, y-1, z) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x, y-1, z, false, side, null, itemstack))
				{
					world.setBlock(x, y-1, z, block, meta, 3);
					itemstack.stackSize = itemstack.stackSize-1;
					playSound(world, x, y, z);
				}
				else if(side == 1 && block.canPlaceBlockAt(world, x, y+1, z) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x, y+1, z, false, side, null, itemstack))
				{
					world.setBlock(x, y+1, z, block, meta, 3);
					itemstack.stackSize = itemstack.stackSize-1;
					playSound(world, x, y, z);
				}
				else if(side == 2 && block.canPlaceBlockAt(world, x, y, z-1) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x, y, z-1, false, side, null, itemstack))
				{
					setSide(world, itemstack, meta, side, x, y, z-1);
				}
				else if(side == 3 && block.canPlaceBlockAt(world, x, y, z+1) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x, y, z+1, false, side, null, itemstack))
				{
					setSide(world, itemstack, meta, side, x, y, z+1);
				}
				else if(side == 4 && block.canPlaceBlockAt(world, x-1, y, z) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x-1, y, z, false, side, null, itemstack))
				{
					setSide(world, itemstack, meta, side, x-1, y, z);
				}
				else if(side == 5 && block.canPlaceBlockAt(world, x+1, y, z) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x+1, y, z, false, side, null, itemstack))
				{
					setSide(world, itemstack, meta, side, x+1, y, z);
				}
				return true;
			}
		}
		return false;		
	}
	
	private boolean createPile(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, int l)
	{
		TELogPile te = null;
		if(world.isAirBlock(x, y, z) && isValid(world, x, y, z))
		{
			world.setBlock(x, y, z, TFCBlocks.logPile, l, 3);
			te = (TELogPile)world.getTileEntity(x, y, z);
		}
		else
		{
			return false;
		}

		if(te != null)
		{
			te.storage[0] = new ItemStack(this,1,itemstack.getItemDamage());
			if(entityplayer.capabilities.isCreativeMode)
			{
				te.storage[0] = new ItemStack(this,4,itemstack.getItemDamage());
				te.storage[1] = new ItemStack(this,4,itemstack.getItemDamage());
				te.storage[2] = new ItemStack(this,4,itemstack.getItemDamage());
				te.storage[3] = new ItemStack(this,4,itemstack.getItemDamage());
			}
		}

		return true;
	}
	
	private void playSound(World world, int x, int y, int z)
	{
		world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, TFCBlocks.logNatural.stepSound.func_150496_b(), (TFCBlocks.logNatural.stepSound.getVolume() + 1.0F) / 2.0F, TFCBlocks.logNatural.stepSound.getPitch() * 0.8F);
	}
	
	@Override
	public void setSide(World world, ItemStack itemstack, int meta, int side, int x, int y, int z)
	{
		if (side == 2 || side == 3) {
			world.setBlock(x, y, z, CWTFCBlocks.woodHorizNS, meta, 3);
			itemstack.stackSize = itemstack.stackSize-1;
			playSound(world, x, y, z);
		}
		else if (side == 4 || side == 5) {
			world.setBlock(x, y, z, CWTFCBlocks.woodHorizEW, meta, 3);
			itemstack.stackSize = itemstack.stackSize-1;
			playSound(world, x, y, z);
		}
	}
}
