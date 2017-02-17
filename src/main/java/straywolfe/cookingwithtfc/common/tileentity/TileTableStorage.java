package straywolfe.cookingwithtfc.common.tileentity;

import java.util.Random;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.NetworkTileEntity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileTableStorage extends NetworkTileEntity implements IInventory
{
	private ItemStack tableStorage[];
	
	public TileTableStorage()
	{
		tableStorage = new ItemStack[4];
	}
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);
			
			boolean destroyBlock = true;
			
			for(int i = 0; i < getSizeInventory(); i++)
			{
				if(getStackInSlot(i) != null)
				{
					destroyBlock = false;
					break;
				}
			}
			
			if(destroyBlock)
			{
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
				worldObj.removeTileEntity(xCoord, yCoord, zCoord);
			}
		}
	}
	
	public void ejectContents()
	{
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 0.8F + 0.3F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;
		float f3 = 0.05F;
		
		for (int i = 0; i < getSizeInventory(); i++)
		{
			if(tableStorage[i] != null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, tableStorage[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
				tableStorage[i] = null;
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		tableStorage = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < getSizeInventory())
				tableStorage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(tableStorage[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				tableStorage[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		tableStorage = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < getSizeInventory())
				tableStorage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}
	
	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		tableStorage = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < getSizeInventory())
				tableStorage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(tableStorage[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				tableStorage[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt)
	{
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(tableStorage[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				tableStorage[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
	}
	
	@Override
	public int getSizeInventory() 
	{
		return tableStorage.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return tableStorage[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) 
	{
		if(tableStorage[slot] != null)
		{
			if(tableStorage[slot].stackSize <= amount)
			{
				ItemStack itemstack = tableStorage[slot];
				tableStorage[slot] = null;
				return itemstack;
			}
			ItemStack itemstack1 = tableStorage[slot].splitStack(amount);
			if(tableStorage[slot].stackSize == 0)
				tableStorage[slot] = null;
			return itemstack1;
		}
		else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) 
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) 
	{
		tableStorage[slot] = itemstack;
	}

	@Override
	public String getInventoryName() 
	{
		return "TableStorage";
	}

	@Override
	public boolean hasCustomInventoryName() 
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) 
	{
		return false;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) 
	{
		return false;
	}
}
