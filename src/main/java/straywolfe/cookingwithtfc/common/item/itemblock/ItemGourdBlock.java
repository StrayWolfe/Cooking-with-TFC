package straywolfe.cookingwithtfc.common.item.itemblock;

import java.util.List;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.tileentity.TileGourd;

public class ItemGourdBlock extends ItemTerra
{
	public int type;
	
	
	public ItemGourdBlock(int Type)
	{
		super();
		this.type = Type;
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if (!world.isRemote && side == 1 && World.doesBlockHaveSolidTopSurface(world, x, y, z) &&
			player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack) && 
			world.isAirBlock(x, y + 1, z))
		{
			world.setBlock(x, y + 1, z, CWTFCBlocks.customGourd, type, 3);
			
			int rotation = MathHelper.floor_double((double)(player.rotationYaw * 16.0F / 360.0F) + 0.5D) & 15;
			
			if (world.getTileEntity(x, y + 1, z) instanceof TileGourd)
            {
				TileGourd te = (TileGourd)world.getTileEntity(x, y + 1, z);
				
				te.setType(type);
				te.setRotation(rotation);
				
				if(type == 1)
				{
					ItemStack fruit = new ItemStack(CWTFCItems.watermelon);
					
					if(stack.stackTagCompound != null)
						fruit.stackTagCompound = (NBTTagCompound)stack.stackTagCompound.copy();
					
					TFC_Core.tickDecay(fruit, world, x, y, z, 1.1f, 1f);
					
					te.setFruit(fruit);
				}
				
				world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
            }
			
			--stack.stackSize;
		}
		
		return false;
    }
	
	@Override
	public boolean onUpdate(ItemStack is, World world, int x, int y, int z)
	{
		return true;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(ModInfo.ModID + ":" + getUnlocalizedName().replace("item.", "") +"_Icon");
	}
	
	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.MEDIUM;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{		
		ItemTerra.addSizeInformation(is, arraylist);		
		
		addExtraInformation(is, player, arraylist);
	}
}
