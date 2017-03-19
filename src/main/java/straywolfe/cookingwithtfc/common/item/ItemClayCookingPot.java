package straywolfe.cookingwithtfc.common.item;

import java.util.List;

import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Items.Pottery.ItemPotteryBase;
import com.bioxx.tfc.TileEntities.TEFirepit;
import com.bioxx.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.core.Tabs;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.tileentity.TileCookingPot;

public class ItemClayCookingPot extends ItemPotteryBase
{	
	public ItemClayCookingPot()
	{
		super();
		this.hasSubtypes = true;
		this.metaNames = new String[]{"Clay", "Ceramic"};
		setCreativeTab(Tabs.MAINTAB);
		this.setFolder("pottery/");
		this.setUnlocalizedName("clayCookingPot");
	}
	
	@SuppressWarnings("static-access")
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if(!world.isRemote)
		{
			super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
			
			Block block = world.getBlock(x, y, z);		
			
			if(block.equals(TFCBlocks.firepit) && stack.getItemDamage() == 1)
			{
				TEFirepit tefirepit = (TEFirepit)world.getTileEntity(x, y, z);
				
				if(tefirepit.getStackInSlot(1) == null && tefirepit.getStackInSlot(7) == null && tefirepit.getStackInSlot(8) == null)
				{
					ItemStack[] firewood = new ItemStack[4];
					firewood[0] = tefirepit.getStackInSlot(0);
					firewood[1] = tefirepit.getStackInSlot(3);
					firewood[2] = tefirepit.getStackInSlot(4);
					firewood[3] = tefirepit.getStackInSlot(5);
					
					for(int i = 0; i < tefirepit.getSizeInventory(); i++)
					{
						tefirepit.setInventorySlotContents(i, null);
					}
					
					world.setBlock(x, y, z, CWTFCBlocks.cookingPot, world.getBlockMetadata(x, y, z), 3);
					stack.stackSize--;
					
					TileCookingPot teCookingPot = (TileCookingPot)world.getTileEntity(x, y, z);
					teCookingPot.fireTemp = tefirepit.fireTemp;
					teCookingPot.fuelTimeLeft = tefirepit.fuelTimeLeft;
					teCookingPot.fuelBurnTemp = tefirepit.fuelBurnTemp;
					
					for(int i = 0; i < firewood.length; i++)
					{
						teCookingPot.setInventorySlotContents(teCookingPot.INVFUELSTART + i, firewood[i]);
					}
				
					return true;
				}
			}
		}
		
		return false;
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.clayIcon = registerer.registerIcon(ModInfo.ModID + ":" + "Clay Cooking Pot");
		this.ceramicIcon = registerer.registerIcon(ModInfo.ModID + ":" + "Ceramic Cooking Pot");
	}
	
	@Override
	public boolean canStack()
	{
		return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{		
		ItemTerra.addSizeInformation(is, arraylist);		
		
		addExtraInformation(is, player, arraylist);
	}
}
