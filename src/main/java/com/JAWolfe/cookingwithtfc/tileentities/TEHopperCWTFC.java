package com.JAWolfe.cookingwithtfc.tileentities;

import com.JAWolfe.cookingwithtfc.crafting.PressManager;
import com.bioxx.tfc.Blocks.Terrain.BlockCobble;
import com.bioxx.tfc.Blocks.Terrain.BlockDirt;
import com.bioxx.tfc.Blocks.Terrain.BlockGravel;
import com.bioxx.tfc.Blocks.Terrain.BlockSand;
import com.bioxx.tfc.Blocks.Terrain.BlockSmooth;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.TileEntities.TEHopper;
import com.bioxx.tfc.api.Food;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidStack;

public class TEHopperCWTFC extends TEHopper
{	
	private int cooldown = -1;
	
	@Override
	public void updateEntity()
	{
		if (this.worldObj.isRemote)
		{
			if(pressCooldown > 0)
				--this.pressCooldown;
			else
				this.pressBlock = null;
		}
		else if (this.worldObj != null && !this.worldObj.isRemote)
		{
			--this.cooldown;

			TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord);

			if(pressCooldown > 0)
			{
				--this.pressCooldown;
				if(pressCooldown % 20 == 0)
					pressItems();
			}
			else if(pressCooldown == 0 && pressBlock != null)
			{
				for(int i = 0; i < getSizeInventory(); i++)
				{
					if (getStackInSlot(i) == null || ItemStack.areItemStacksEqual(getStackInSlot(i), pressBlock) && getStackInSlot(i).stackSize < getStackInSlot(i).getMaxStackSize())
					{
						if(getStackInSlot(i) == null)
							setInventorySlotContents(i, pressBlock);
						else
							getStackInSlot(i).stackSize++;
						this.pressBlock = null;
						break;
					}
				}
			}

			if (!this.isCoolingDown())
			{
				this.setCooldown(0);
				//this.feed();
			}
			Block blockAbove = worldObj.getBlock(xCoord, yCoord+1, zCoord);
			if(blockAbove != null && this.hasPressableItem() > 0)
			{
				if (pressBlock != null && !(blockAbove instanceof BlockCobble || blockAbove instanceof BlockGravel || blockAbove instanceof BlockSand || blockAbove instanceof BlockDirt))
				{
					TFC_Core.setBlockToAirWithDrops(worldObj, xCoord, yCoord+1, zCoord);
				}
				else if (blockAbove instanceof BlockSmooth)
				{
					pressBlock = new ItemStack(blockAbove, 1, worldObj.getBlockMetadata(xCoord, yCoord+1, zCoord));
					worldObj.setBlockToAir(xCoord, yCoord+1, zCoord);
					sendPressPacket();
					beginPressingItem();
				}
			}
		}
	}
	
	private void beginPressingItem()
	{
		int pressWeight = hasPressableItem();
		if(pressWeight > 0)
		{
			this.pressCooldown += pressWeight/0.64f * 20;
			sendCooldownPacket();	
		}
	}

	private void pressItems()
	{
		TEBarrel barrel = null;
		if(worldObj.getTileEntity(xCoord, yCoord-1, zCoord) instanceof TEBarrel)
			barrel = (TEBarrel) worldObj.getTileEntity(xCoord, yCoord-1, zCoord);

		ItemStack item = getPressableItem();
		if(item != null)
		{
			if(item.stackSize > 0)
				Food.setWeight(item, Food.getWeight(item) - 0.64f);//0.64 per cycle leads to 250mB per stack of olives

			if(barrel != null && barrel.canAcceptLiquids() && !barrel.getSealed())
			{
				barrel.addLiquid(new FluidStack(PressManager.getInstance().getMatchingRecipe(item.getItem()).getOutput(), 1));
			}
		}
	}

	@Override
	public int hasPressableItem()
	{
		int amount = 0;
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(getStackInSlot(i) != null && PressManager.getInstance().getMatchingRecipe(getStackInSlot(i).getItem()) != null)
			{
				amount += Math.floor(Food.getWeight(getStackInSlot(i)));
			}
		}
		return amount;
	}

	@Override
	public ItemStack getPressableItem()
	{
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(getStackInSlot(i) != null && PressManager.getInstance().getMatchingRecipe(getStackInSlot(i).getItem()) != null)
			{
				return getStackInSlot(i);
			}
		}
		return null;
	}
	
	@Override
	public boolean isCoolingDown()
	{
		return this.cooldown > 0;
	}
	
	@Override
	public void setCooldown(int time)
	{
		this.cooldown = time;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		//this.storage = new ItemStack[this.getSizeInventory()];

		if (nbt.hasKey("CustomName", 8))
		{
			setCustomName(nbt.getString("CustomName"));
		}

		this.cooldown = nbt.getInteger("TransferCooldown");

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < getSizeInventory())
			{
				setInventorySlotContents(b0, ItemStack.loadItemStackFromNBT(nbttagcompound1));
			}
		}

		this.pressCooldown = nbt.getInteger("pressCooldown");
		this.pressBlock = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("pressBlock"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < getSizeInventory(); ++i)
		{
			if (getStackInSlot(i) != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				getStackInSlot(i).writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbt.setTag("Items", nbttaglist);
		nbt.setInteger("TransferCooldown", this.cooldown);

		if (this.hasCustomInventoryName())
		{
			nbt.setString("CustomName", getInventoryName());
		}

		nbt.setInteger("pressCooldown", this.pressCooldown);

		if(pressBlock != null)
		{
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			pressBlock.writeToNBT(nbttagcompound1);
			nbt.setTag("pressBlock", nbttagcompound1);
		}
	}
	
	private void sendPressPacket() 
	{
		NBTTagCompound nbt = new NBTTagCompound();
		if(pressBlock != null)
		{
			NBTTagCompound pb = new NBTTagCompound();
			pressBlock.writeToNBT(pb);
			nbt.setTag("pressBlock", pb);
		}
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}

	private void sendCooldownPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("pressCooldown", pressCooldown);
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}
}
