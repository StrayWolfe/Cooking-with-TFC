package straywolfe.cookingwithtfc.common.tileentity;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.NetworkTileEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileGourd extends NetworkTileEntity
{
    private int rotation;
    private int type;
    private int hourPlaced;
    private ItemStack fruit;
    
	public TileGourd()
	{
		rotation = -1;
		type = -1;
		hourPlaced = -1;
	}
	
	public void setRotation(int rot)
	{
		rotation = rot;
	}
	
	public int getRotation()
	{
		return rotation;
	}
	
	public void setType(int Type)
	{
		type = Type;
	}
	
	public int getType()
	{
		return type;
	}
	
	public void resetHourPlaced()
	{
		hourPlaced = (int) TFC_Time.getTotalHours();
	}
	
	public void setHourPlaced(int time)
	{
		hourPlaced = time;
	}
	
	public int getHourPlaced()
	{
		return hourPlaced;
	}
	
	@Override
	public boolean canUpdate()
	{
		return false;
	}
	
	public void setFruit(ItemStack Fruit)
	{
		fruit = Fruit;
	}
	
	public ItemStack getFruit()
	{
		return fruit;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		super.readFromNBT(nbt);
		rotation = nbt.getInteger("rotation");
		type = nbt.getInteger("type");
		hourPlaced = nbt.getInteger("hourPlaced");
		fruit = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("fruit"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) 
	{
		super.writeToNBT(nbt);        
		nbt.setInteger("rotation", rotation);
		nbt.setInteger("type", type);
		nbt.setInteger("hourPlaced", hourPlaced);
		
		if(fruit != null)
		{
			NBTTagCompound fruitNBT = new NBTTagCompound();
			fruit.writeToNBT(fruitNBT);
	        nbt.setTag("fruit", fruitNBT);
		}
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		rotation = nbt.getInteger("rotation");
		type = nbt.getInteger("type");
		hourPlaced = nbt.getInteger("hourPlaced");
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}
	
	@Override
	public void handleDataPacket(NBTTagCompound nbt) 
	{
		rotation = nbt.getInteger("rotation");
		type = nbt.getInteger("type");
		hourPlaced = nbt.getInteger("hourPlaced");
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		nbt.setInteger("rotation", rotation);
		nbt.setInteger("type", type);
		nbt.setInteger("hourPlaced", hourPlaced);
	}
	
	@Override
	public void createDataNBT(NBTTagCompound nbt) 
	{
		nbt.setInteger("rotation", rotation);
		nbt.setInteger("type", type);
		nbt.setInteger("hourPlaced", hourPlaced);
	}
}
