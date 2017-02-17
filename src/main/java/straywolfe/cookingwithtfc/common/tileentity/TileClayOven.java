package straywolfe.cookingwithtfc.common.tileentity;

import java.util.Random;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.NetworkTileEntity;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Enums.EnumFuelMaterial;
import com.bioxx.tfc.api.Interfaces.IFood;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import straywolfe.cookingwithtfc.api.managers.OvenManager;
import straywolfe.cookingwithtfc.api.managers.OvenRecipe;
import straywolfe.cookingwithtfc.common.lib.Constants;
import straywolfe.cookingwithtfc.common.lib.Settings;

public class TileClayOven extends NetworkTileEntity implements IInventory
{
	private ItemStack ovenContents[];
	private int buildStage;
	private long curingTime;
	private int curingFlag;
	private boolean fireOn;
	public float fireTemp;
	public int fuelTimeLeft;
	public int fuelBurnTemp;
	public int fuelTasteProfile;
	public int durability;
	
	public TileClayOven()
	{
		ovenContents = new ItemStack[7];
		fireOn = false;
		durability = Settings.ClayOvenDurability;
	}
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{	
			boolean update = false;
			careForInventorySlot(ovenContents[4]);
			careForInventorySlot(ovenContents[5]);
			
			if(cookItemSlot(4))
				update = true;
			
			if(cookItemSlot(5))
				update = true;
			
			handleFuelStack();
			
			if (!hasFuel() && fireOn)
			{
				fireOn = false;
				update = true;
			}
			
			if(fuelTimeLeft <= 0 && fireTemp >= 1 && ovenContents[3] != null)
			{
				if(ovenContents[3] != null)
				{
					EnumFuelMaterial m = TFC_Core.getFuelMaterial(ovenContents[3]);
					fuelTasteProfile = m.ordinal();
					fuelTimeLeft = m.burnTimeMax;
					fuelBurnTemp = m.burnTempMax;
					ovenContents[3] = null;
					update = true;
				}
			}
			
			float desiredTemp = handleTemp();

			if(fireTemp < desiredTemp)
				fireTemp += 2;
			else if(fireTemp > desiredTemp)
			{
				if(desiredTemp == 0)
					fireTemp -= 0.5;
			}
			
			if(fireTemp > fuelBurnTemp)
				fireTemp = fuelBurnTemp;
			else if(fireTemp < 0)
				fireTemp = 0;
			
			if(fuelTimeLeft <= 0)
				TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);

			if(curingTime > 0)
			{
				if(buildStage <= Constants.CHIMNEY && TFC_Core.isExposedToRain(worldObj, xCoord, yCoord, zCoord))
				{
					curingTime = TFC_Time.getTotalTicks();
					update = true;
				}

				if(buildStage >= Constants.INTERIOR && fireTemp < 300)
					curingTime++;
				
				if(getCuringStage() > curingFlag)
				{
					curingFlag = getCuringStage();
					
					if(getCuringStage() == 3)
					{
						if(buildStage == Constants.INTERIOR)
							buildStage++;
						
						setCuringTime(0);
						curingFlag = 0;
					}
					
					update = true;
				}
			}
			
			if(update)
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	public boolean cookItemSlot(int slot)
	{
		ItemStack is = ovenContents[slot];
		
		if(is != null)
		{
			OvenRecipe recipe = OvenManager.getInstance().findMatchingRecipe(is);
			if(recipe != null && TFC_ItemHeat.getTemp(is) >= recipe.getCookedTemp())
			{
				if(recipe.getOutput() != null)
				{
					ItemStack output = recipe.getOutput().copy();
					
					if (is.stackTagCompound != null)
			        {
						output.stackTagCompound = (NBTTagCompound)is.stackTagCompound.copy();
			        }
					
					ovenContents[slot] = output;
				}
				else
					ovenContents[slot] = null;
				
				return true;
			}
		}
		
		return false;
	}
	
	public void careForInventorySlot(ItemStack is)
	{
		if(is != null)
		{
			OvenRecipe recipe = OvenManager.getInstance().findMatchingRecipe(is);

			if (recipe != null)
			{
				float temp = TFC_ItemHeat.getTemp(is);
				if (fuelTimeLeft > 0 && is.getItem() instanceof IFood)
				{
					float inc = Food.getCooked(is) + Math.min(fireTemp / 700, 2f);
					Food.setCooked(is, inc);
					temp = inc;
					if (Food.isCooked(is))
					{
						int[] cookedTasteProfile = new int[]
						{ 0, 0, 0, 0, 0 };
						Random r = new Random(((IFood)is.getItem()).getFoodID() + (((int) Food.getCooked(is) - 600) / 120));
						cookedTasteProfile[0] = r.nextInt(31) - 15;
						cookedTasteProfile[1] = r.nextInt(31) - 15;
						cookedTasteProfile[2] = r.nextInt(31) - 15;
						cookedTasteProfile[3] = r.nextInt(31) - 15;
						cookedTasteProfile[4] = r.nextInt(31) - 15;
						Food.setCookedProfile(is, cookedTasteProfile);
						Food.setFuelProfile(is, EnumFuelMaterial.getFuelProfile(fuelTasteProfile));
					}
				}
				else if (fireTemp > temp)
					temp += TFC_ItemHeat.getTempIncrease(is);
				else
					temp -= TFC_ItemHeat.getTempDecrease(is);
				TFC_ItemHeat.setTemp(is, temp);
			}
		}
	}
	
	public float handleTemp()
	{
		if(fuelTimeLeft > 0)
		{
			fuelTimeLeft--;
		}
		else if(fuelTimeLeft < 0)
			fuelTimeLeft = 0;
		
		if(fuelTimeLeft > 0)
			return fuelBurnTemp;
		else
			return 0;
	}
	
	public boolean hasFood()
	{
		if(ovenContents[4] != null || ovenContents[5] != null)
			return true;
		
		return false;
	}
	
	public boolean hasFuel()
	{
		if(ovenContents[0] != null || ovenContents[1] != null || ovenContents[2] != null || ovenContents[3] != null)
			return true;
			
		return false;
	}
	
	public void handleFuelStack()
	{
		if(ovenContents[1] == null && ovenContents[0] != null)
		{
			ovenContents[1] = ovenContents[0];
			ovenContents[0] = null;
		}
		if(ovenContents[2] == null && ovenContents[1] != null)
		{
			ovenContents[2] = ovenContents[1];
			ovenContents[1] = null;
		}
		if(ovenContents[3] == null && ovenContents[2] != null)
		{
			ovenContents[3] = ovenContents[2];
			ovenContents[2] = null;
		}
	}
	
	public void setBuildStage(int stage)
	{
		buildStage = stage;
	}
	
	public int getBuildStage()
	{
		return buildStage;
	}
	
	public boolean getFireState()
	{
		return fireOn;
	}
	
	public void setFireState(boolean state)
	{
		fireOn = state;
	}
	
	public void setCuringTime(long time)
	{
		curingTime = time;
	}
	
	public long getCuringTime()
	{
		return curingTime;
	}
	
	public int getCuringStage()
	{		
		float time;
		
		if(buildStage == Constants.INTERIOR)
			time = (float)(TFC_Time.getTotalTicks() - curingTime) / Settings.ovenFiringTime;
		else
			time = (float)(TFC_Time.getTotalTicks() - curingTime) / Settings.ovenHardeningTime;
		
		if(curingTime > 0)
		{
			if(time < 0.5)
				return 1;
			else if(time >= 0.5 && time < 1)
				return 2;
			else
				return 3;
		}
		else
			return 4;
	}
	
	public int getDurability()
	{
		return durability;
	}
	
	public void setDurability(int value)
	{
		durability = value;
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
			if(ovenContents[i] != null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, ovenContents[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
				ovenContents[i] = null;
			}
		}
		
		if(buildStage <= Constants.CHIMNEY && getCuringStage() < 3)
		{
			int clayBalls = 5;
			
			if(buildStage > Constants.PLATFORM)
				clayBalls = clayBalls * (buildStage - 1);
			
			ItemStack clay = new ItemStack(TFCItems.clayBall, clayBalls);
			entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, clay);
			entityitem.motionX = (float)rand.nextGaussian() * f3;
			entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
			entityitem.motionZ = (float)rand.nextGaussian() * f3;
			worldObj.spawnEntityInWorld(entityitem);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		fireTemp = nbt.getFloat("temperature");
		fuelTimeLeft = nbt.getInteger("fuelTime");
		fuelBurnTemp = nbt.getInteger("fuelTemp");
		fuelTasteProfile = nbt.getInteger("fuelTasteProfile");
		buildStage = nbt.getInteger("buildStage");
		fireOn = nbt.getBoolean("fireOn");
		curingTime = nbt.getLong("curingTime");
		curingFlag = nbt.getInteger("curingFlag");
		durability = nbt.getInteger("durability");
		
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		ovenContents = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < ovenContents.length)
				ovenContents[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setFloat("temperature", fireTemp);
		nbt.setInteger("fuelTime", fuelTimeLeft);
		nbt.setInteger("fuelTemp", fuelBurnTemp);
		nbt.setInteger("fuelTasteProfile", fuelTasteProfile);
		nbt.setInteger("buildStage", buildStage);
		nbt.setBoolean("fireOn", fireOn);
		nbt.setLong("curingTime", curingTime);
		nbt.setInteger("curingFlag", curingFlag);
		nbt.setInteger("durability", durability);
		
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < ovenContents.length; i++)
		{
			if(ovenContents[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				ovenContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		ovenContents = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < ovenContents.length)
				ovenContents[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		buildStage = nbt.getInteger("buildStage");
		fireOn = nbt.getBoolean("fireOn");
		curingTime = nbt.getLong("curingTime");
	}
	
	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		ovenContents = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < ovenContents.length)
				ovenContents[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		buildStage = nbt.getInteger("buildStage");
		fireOn = nbt.getBoolean("fireOn");
		curingTime = nbt.getLong("curingTime");
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < ovenContents.length; i++)
		{
			if(ovenContents[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				ovenContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
		nbt.setInteger("buildStage", buildStage);
		nbt.setBoolean("fireOn", fireOn);
		nbt.setLong("curingTime", curingTime);
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt)
	{
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < ovenContents.length; i++)
		{
			if(ovenContents[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				ovenContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
		nbt.setInteger("buildStage", buildStage);
		nbt.setBoolean("fireOn", fireOn);
		nbt.setLong("curingTime", curingTime);
	}
	
	@Override
	public int getSizeInventory() 
	{
		return ovenContents.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return ovenContents[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) 
	{
		if(ovenContents[slot] != null)
		{
			if(ovenContents[slot].stackSize <= amount)
			{
				ItemStack itemstack = ovenContents[slot];
				ovenContents[slot] = null;
				return itemstack;
			}
			ItemStack itemstack1 = ovenContents[slot].splitStack(amount);
			if(ovenContents[slot].stackSize == 0)
				ovenContents[slot] = null;
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
	public void setInventorySlotContents(int slot, ItemStack is) 
	{
		ovenContents[slot] = is;
		
		if(is != null && is.stackSize > getInventoryStackLimit())
			is.stackSize = getInventoryStackLimit();
	}

	@Override
	public String getInventoryName() 
	{
		return "BrickOven";
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
