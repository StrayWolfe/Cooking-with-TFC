package straywolfe.cookingwithtfc.common.tileentity;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.NetworkTileEntity;
import com.bioxx.tfc.api.Food;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileMeat extends NetworkTileEntity
{
	private ItemStack placedMeat;
	private float meatCoordX = 0;
	private float meatCoordZ = 0;
	private boolean removeFlag = false;
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote && placedMeat != null)
		{
			placedMeat = TFC_Core.tickDecay(placedMeat, worldObj,  xCoord, yCoord, zCoord, 1.1f, 1f);
			if(placedMeat == null)
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
	}
	
	public float saltMeat(ItemStack salt)
	{	
		float remainingSalt = Food.getWeight(salt);
		float saltedWt = remainingSalt * 25;
		ItemStack saltedMeat = placedMeat.copy();
		Food.setSalted(saltedMeat, true);
		
		if(saltedWt >= Food.getWeight(placedMeat))
		{
			remainingSalt = remainingSalt - (Food.getWeight(placedMeat)/25);
			ejectItem(saltedMeat);
			removeFlag = true;
		}
		else
		{
			Food.setWeight(saltedMeat, saltedWt);
			Food.setWeight(placedMeat, Food.getWeight(placedMeat) - saltedWt);
			remainingSalt = 0;
			ejectItem(saltedMeat);
		}
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return remainingSalt;
	}
	
	public void setplacedMeat(ItemStack is)
	{
		placedMeat = is;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public ItemStack getplacedMeat()
	{
		return placedMeat;
	}
	
	public void setMeatCoord(float coord, int dir)
	{
		if(dir == 0)
			meatCoordX = coord;
		else
			meatCoordZ = coord;
	}
	
	public float getMeatCoord(int dir)
	{
		if(dir == 0)
			return meatCoordX;
		else
			return meatCoordZ;
	}
	
	public boolean getFlag()
	{
		return removeFlag;
	}
	
	public void ejectItem()
	{
		ejectItem(placedMeat);
	}
	
	public void ejectItem(ItemStack is)
	{
		EntityItem entityitem;

		if(is != null)
		{
			entityitem = new EntityItem(worldObj, xCoord + 0.5F, yCoord + 0.1F, zCoord + 0.5F, is);
			entityitem.motionX = 0;
			entityitem.motionY = 0;
			entityitem.motionZ = 0;
			worldObj.spawnEntityInWorld(entityitem);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		placedMeat = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("placedMeat"));
		meatCoordX = nbt.getFloat("meatCoordX");
		meatCoordZ = nbt.getFloat("meatCoordZ");
		removeFlag = nbt.getBoolean("removeFlag");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagCompound meatNBT = new NBTTagCompound();
		placedMeat.writeToNBT(meatNBT);
		nbt.setTag("placedMeat", meatNBT);
		nbt.setFloat("meatCoordX", meatCoordX);
		nbt.setFloat("meatCoordZ", meatCoordZ);
		nbt.setBoolean("removeFlag", removeFlag);
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		placedMeat = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("placedMeat"));
		meatCoordX = nbt.getFloat("meatCoordX");
		meatCoordZ = nbt.getFloat("meatCoordZ");
		removeFlag = nbt.getBoolean("removeFlag");
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		NBTTagCompound meatNBT = new NBTTagCompound();
        placedMeat.writeToNBT(meatNBT);
        nbt.setTag("placedMeat", meatNBT);
        nbt.setFloat("meatCoordX", meatCoordX);
		nbt.setFloat("meatCoordZ", meatCoordZ);
		nbt.setBoolean("removeFlag", removeFlag);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) 
	{
		placedMeat = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("placedMeat"));
		meatCoordX = nbt.getFloat("meatCoordX");
		meatCoordZ = nbt.getFloat("meatCoordZ");
		removeFlag = nbt.getBoolean("removeFlag");
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}
	
	@Override
	public void createDataNBT(NBTTagCompound nbt) 
	{
		NBTTagCompound meatNBT = new NBTTagCompound();
		placedMeat.writeToNBT(meatNBT);
        nbt.setTag("placedMeat", meatNBT);
        nbt.setFloat("meatCoordX", meatCoordX);
		nbt.setFloat("meatCoordZ", meatCoordZ);
		nbt.setBoolean("removeFlag", removeFlag);
	}
}
