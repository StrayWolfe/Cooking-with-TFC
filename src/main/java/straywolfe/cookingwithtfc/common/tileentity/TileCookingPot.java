package straywolfe.cookingwithtfc.common.tileentity;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Enums.EnumFuelMaterial;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.api.recipe.CookingPotManager;
import straywolfe.cookingwithtfc.api.recipe.CookingPotRecipe;
import straywolfe.cookingwithtfc.common.core.helper.Helper;
import straywolfe.cookingwithtfc.common.item.ItemTFCAdjutableFood;
import straywolfe.cookingwithtfc.common.item.ItemTFCFoodTransform;
import straywolfe.cookingwithtfc.common.item.ItemTFCMealTransform;

public class TileCookingPot extends TEFireEntity implements IInventory
{
	private FluidStack cookingPotFluid;
	private ItemStack cookingPotInv[];
	private boolean hasLid;
	private boolean isDone;
	private int recipeID = -1;
	private int cookTimer = -1;
	private int cookTime = -1;
	public static final int INVFUELSTART = 0;
	public static final int INVFUELEND = 3;
	public static final int INVFOODSTART = 4;
	public static final int INVFOODEND = 13;
	public static final int INVFOODCOUNT = 10;
	public static final int FLUIDOUTPUT = 14;
	public static final int INVSIZE = 15;
	
	
	public TileCookingPot()
	{
		hasLid = true;
		isDone = false;
		cookingPotInv = new ItemStack[INVSIZE];
		maxFireTempScale = 2000;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			List list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1.1, zCoord + 1));

			if (list != null && !list.isEmpty() && cookingPotInv[0] == null)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					EntityItem entity = (EntityItem) iterator.next();
					ItemStack is = entity.getEntityItem();
					Item item = is.getItem();

					if (item == TFCItems.logs || item == Item.getItemFromBlock(TFCBlocks.peat))
					{
						for (int c = 0; c < is.stackSize; c++)
						{
							if (cookingPotInv[0] == null)
							{
								setInventorySlotContents(0, new ItemStack(item, 1, is.getItemDamage()));
								is.stackSize--;
								handleFuelStack();
							}
						}

						if (is.stackSize == 0)
						{
							entity.setDead();
							int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
							worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 3);
							worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						}
					}
				}
			}
			
			if(fireTemp > 600 && hasLid)
				cookFood();
			
			handleFuelStack();
			
			if (fireTemp < 1 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 0)
			{
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			else if (fireTemp >= 1 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 1)
			{
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			
			if(fuelTimeLeft > 0 && fireTemp >= 1)
			{
				if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 2)
				{
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 3);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
			else if(fuelTimeLeft <= 0 && fireTemp >= 1 && cookingPotInv[3] != null &&
						(!worldObj.canLightningStrikeAt(xCoord, yCoord, zCoord) && !worldObj.canLightningStrikeAt(xCoord, yCoord + 1, zCoord) ||
							!worldObj.isRaining()))
			{
				if(cookingPotInv[3] != null)
				{
					EnumFuelMaterial m = TFC_Core.getFuelMaterial(cookingPotInv[3]);
					fuelTasteProfile = m.ordinal();
					cookingPotInv[3] = null;
					fuelTimeLeft = m.burnTimeMax;
					fuelBurnTemp = m.burnTempMax;
				}
			}
			
			float desiredTemp = handleTemp();

			handleTempFlux(desiredTemp);
			
			handleAirReduction();
			
			if(cookingPotFluid != null && cookingPotFluid.amount <= 0)
				cookingPotFluid = null;
			
			if(fuelTimeLeft <= 0)
				TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);
		}
	}
	
	private void cookFood()
	{
		if(recipeID != -1)
		{
			if(cookTimer == 0)
			{
				CookingPotRecipe recipe = CookingPotManager.getInstance().getRecipe(recipeID);
				ItemStack[] recipeOutput = recipe.getOutputItems();			
				cookingPotFluid = null;
				cookingPotInv[FLUIDOUTPUT] = null;
				
				//Set output fluid
				if(recipe.getOutputFluid() != null)
				{
					cookingPotFluid = new FluidStack(recipe.getOutputFluid().getFluid(), recipe.getOutputFluid().amount);
				
					//Set out itemstack
					cookingPotInv[FLUIDOUTPUT] = Helper.getItemStackForFluid(cookingPotFluid);
				}
				
				//Set meal itemstack for fluid
				if(cookingPotInv[FLUIDOUTPUT] != null && cookingPotInv[FLUIDOUTPUT].getItem() instanceof ItemTFCMealTransform)
				{					
					int tracker = 0;
					int ingredCount = recipe.getInputItems().size();
					ItemStack inputFluid = Helper.getItemStackForFluid(recipe.getInputFluid());
					ItemStack[] inputFoods;
					float[] foodPct;
					float weight = Food.getWeight(cookingPotInv[FLUIDOUTPUT]);
					
					if(inputFluid != null && (inputFluid.getItem() instanceof ItemTFCAdjutableFood || inputFluid.getItem() instanceof ItemTFCFoodTransform))
					{
						inputFoods = new ItemStack[ingredCount + 1];
						foodPct = new float[ingredCount + 1];
					}
					else
					{
						inputFoods = new ItemStack[ingredCount];
						foodPct = new float[ingredCount];
					}
					
					for(int i = INVFOODSTART; i <= INVFOODEND; i++)
					{
						if(cookingPotInv[i] != null && cookingPotInv[i].getItem() instanceof IFood)
						{
							inputFoods[tracker] = cookingPotInv[i];
							foodPct[tracker] = Food.getWeight(cookingPotInv[i]) / (weight * cookingPotInv[FLUIDOUTPUT].stackSize);
							tracker++;
						}
					}
					
					if(inputFluid != null && (inputFluid.getItem() instanceof ItemTFCAdjutableFood || inputFluid.getItem() instanceof ItemTFCFoodTransform))
					{
						inputFoods[tracker] = inputFluid;
						foodPct[tracker] = Food.getWeight(inputFluid) / (weight * cookingPotInv[FLUIDOUTPUT].stackSize);
					}
					
					cookingPotInv[FLUIDOUTPUT] = ItemTFCMealTransform.createTag(new ItemStack(cookingPotInv[FLUIDOUTPUT].getItem(), 1), weight, 0F, inputFoods, foodPct);
					
					Helper.combineTastes(cookingPotInv[FLUIDOUTPUT].getTagCompound(), inputFoods);
				}
				
				//Set cooked foods
				if(recipeOutput != null)
				{
					for(int x = 0; x < recipeOutput.length; x++)
					{
						ItemStack food = new ItemStack(recipeOutput[x].getItem(), 1, recipeOutput[x].getItemDamage());
						cookingPotInv[x + INVFOODSTART] = ItemFoodTFC.createTag(food, 2, 0);
					}
					
					if(recipeOutput.length < INVFOODCOUNT)
					{
						for(int x = recipeOutput.length; x < INVFOODCOUNT; x++)
							cookingPotInv[x + INVFOODSTART] = null;
					}
				}
				else
				{
					for(int i = INVFOODSTART; i <= INVFOODEND; i++)
						cookingPotInv[i] = null;
				}
				
				recipeID = cookTimer = cookTime = -1;
				isDone = true;
				
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			else
				cookTimer--;
		}
	}
	
	public void recipeHandling()
	{
		if(cookingPotInv != null)
		{
			ItemStack[] recipeInput = new ItemStack[INVFOODEND - INVFOODSTART + 1];
			for(int x = 0; x < recipeInput.length; x++)
			{
				recipeInput[x] = cookingPotInv[x + INVFOODSTART];
			}
			
			CookingPotRecipe recipe = CookingPotManager.getInstance().findMatchingRecipe(cookingPotFluid, recipeInput);
			if(recipe != null)
			{
				cookTimer = cookTime = (int)((recipe.getCookTime()/60F) * TFC_Time.HOUR_LENGTH);
				recipeID = CookingPotManager.getInstance().getRecipeID(recipe);
			}
			else
				recipeID = cookTimer = cookTime = -1;
		}
	}
	
	public ItemStack getLastItem()
	{
		for(int i = INVFOODEND; i >= INVFOODSTART; i--)
		{
			if(cookingPotInv[i] != null)
			{				
				ItemStack item = cookingPotInv[i].copy();				
				cookingPotInv[i] = null;
				item.stackSize = 1;
				recipeHandling();
				return item;
			}
		}
		return null;
	}
	
	public void setLastItem(ItemStack is)
	{
		for(int i = INVFOODSTART; i <= INVFOODEND; i++)
		{
			if(cookingPotInv[i] == null)
			{
				cookingPotInv[i] = is;
				recipeHandling();
				return;
			}
		}
	}
	
	public void handleFuelStack()
	{
		if(cookingPotInv[1] == null && cookingPotInv[0] != null)
		{
			cookingPotInv[1] = cookingPotInv[0];
			cookingPotInv[0] = null;
		}
		if(cookingPotInv[2] == null && cookingPotInv[1] != null)
		{
			cookingPotInv[2] = cookingPotInv[1];
			cookingPotInv[1] = null;
		}
		if(cookingPotInv[3] == null && cookingPotInv[2] != null)
		{
			cookingPotInv[3] = cookingPotInv[2];
			cookingPotInv[2] = null;
		}
	}
	
	public boolean addLiquid(FluidStack fluid)
	{
		if(fluid != null)
		{
			if(cookingPotFluid == null)
			{
				cookingPotFluid = fluid.copy();
				if (cookingPotFluid.amount > this.getMaxLiquid())
				{
					cookingPotFluid.amount = getMaxLiquid();
					fluid.amount = fluid.amount - this.getMaxLiquid();
				}
				else
					fluid.amount = 0;
			}
			else
			{
				if (cookingPotFluid.amount == getMaxLiquid() || !cookingPotFluid.isFluidEqual(fluid))
					return false;

				int a = cookingPotFluid.amount + fluid.amount - getMaxLiquid();
				cookingPotFluid.amount = Math.min(cookingPotFluid.amount + fluid.amount, getMaxLiquid());
				if (a > 0)
					fluid.amount = a;
				else
					fluid.amount = 0;
			}
			
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			recipeHandling();
			return true;
		}
		
		return false;
	}
	
	public ItemStack addLiquid(ItemStack is)
	{
		if(is == null || is.stackSize > 1)
			return is;
		
		if(FluidContainerRegistry.isFilledContainer(is))
		{
			FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(is);
			if(addLiquid(fs))
			{
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				return FluidContainerRegistry.drainFluidContainer(is);
			}
		}
		else if(is.getItem() instanceof IFluidContainerItem)
		{
			FluidStack isfs = ((IFluidContainerItem) is.getItem()).getFluid(is);
			if(isfs != null && addLiquid(isfs))
			{
				((IFluidContainerItem) is.getItem()).drain(is, is.getMaxDamage(), true);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
		return is;
	}
	
	public ItemStack removeLiquid(ItemStack is)
	{
		if(is == null || is.stackSize > 1)
			return is;
		if(FluidContainerRegistry.isEmptyContainer(is))
		{
			ItemStack out = FluidContainerRegistry.fillFluidContainer(cookingPotFluid, is);
			if(out != null)
			{
				FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(out);
				cookingPotFluid.amount -= fs.amount;
				is = null;
				if(cookingPotFluid.amount <= 0)
				{
					cookingPotFluid = null;
				}
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				recipeHandling();
				return out;
			}
		}
		else if(cookingPotFluid != null && is.getItem() instanceof IFluidContainerItem)
		{
			FluidStack isfs = ((IFluidContainerItem) is.getItem()).getFluid(is);
			if(isfs == null || cookingPotFluid.isFluidEqual(isfs))
			{
				cookingPotFluid.amount -= ((IFluidContainerItem) is.getItem()).fill(is, cookingPotFluid, true);
				if(cookingPotFluid.amount <= 0)
					cookingPotFluid = null;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				recipeHandling();
			}
		}
		return is;
	}
	
	public FluidStack getCookingPotFluid()
	{
		return cookingPotFluid;
	}
	
	public void setCookingPotFluid(FluidStack fs)
	{
		cookingPotFluid = fs;
	}
	
	public int getFluidLevel()
	{
		if(cookingPotFluid != null)
			return cookingPotFluid.amount;
		
		return 0;
	}
	
	public void setFluidLevel(int amount)
	{
		if(cookingPotFluid != null)
			cookingPotFluid.amount = amount;
	}
	
	public void removeFluid()
	{
		if(cookingPotFluid != null)
			cookingPotFluid = null;
	}
	
	public int getLiquidScaled(int i)
	{
		if(cookingPotFluid != null)
			return cookingPotFluid.amount * i/getMaxLiquid();
		
		return 0;
	}
	
	public int getRecipeID()
	{
		return recipeID;
	}
	
	public void setRecipeID(int ID)
	{
		recipeID = ID;
	}
	
	public int getCookTimer()
	{
		return cookTimer;
	}
	
	public float getCookTimerScale()
	{
		if(recipeID != -1)
		{
			return 1 - (float)cookTimer/(float)cookTime;
		}
		
		return 0;
	}
	
	public void setCookTimer(int time)
	{
		cookTimer = time;
	}
	
	public int getCookTime()
	{
		return cookTime;
	}
	
	public void setCookTime(int time)
	{
		cookTime = time;
	}
	
	public int getMaxLiquid()
	{
		return 1000;
	}
	
	public boolean getHasLid()
	{
		return hasLid;
	}
	
	public void setHasLid(boolean lid)
	{
		isDone = false;
		hasLid = lid;
	}
	
	public boolean getIsDone()
	{
		return isDone;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		
		cookingPotFluid = FluidStack.loadFluidStackFromNBT(nbttagcompound.getCompoundTag("fluidNBT"));
		recipeID = nbttagcompound.getInteger("recipeID");
		cookTimer = nbttagcompound.getInteger("cookTimer");
		cookTime = nbttagcompound.getInteger("cookTime");
		hasLid = nbttagcompound.getBoolean("hasLid");
		isDone = nbttagcompound.getBoolean("isDone");
		
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		cookingPotInv = new ItemStack[cookingPotInv.length];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte itemslot = nbttagcompound1.getByte("Slot");
			if(itemslot >= 0 && itemslot < cookingPotInv.length)
				cookingPotInv[itemslot] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		
		NBTTagCompound fluidNBT = new NBTTagCompound();
		if(cookingPotFluid != null)
			cookingPotFluid.writeToNBT(fluidNBT);
		nbttagcompound.setTag("fluidNBT", fluidNBT);
		
		nbttagcompound.setInteger("recipeID", recipeID);
		nbttagcompound.setInteger("cookTimer", cookTimer);
		nbttagcompound.setInteger("cookTime", cookTime);
		nbttagcompound.setBoolean("hasLid", hasLid);
		nbttagcompound.setBoolean("isDone", isDone);
		
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < cookingPotInv.length; i++)
		{
			if(cookingPotInv[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				cookingPotInv[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
	}
	
	@Override
	public void handleInitPacket(NBTTagCompound nbt)
	{
		cookingPotFluid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("fluidNBT"));
		recipeID = nbt.getInteger("recipeID");
		cookTimer = nbt.getInteger("cookTimer");
		cookTime = nbt.getInteger("cookTime");
		hasLid = nbt.getBoolean("hasLid");
		isDone = nbt.getBoolean("isDone");
		fireTemp = nbt.getFloat("temperature");
		
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		cookingPotInv = new ItemStack[cookingPotInv.length];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte itemslot = nbttagcompound1.getByte("Slot");
			if(itemslot >= 0 && itemslot < cookingPotInv.length)
				cookingPotInv[itemslot] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		cookingPotFluid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("fluidNBT"));
		recipeID = nbt.getInteger("recipeID");
		cookTimer = nbt.getInteger("cookTimer");
		cookTime = nbt.getInteger("cookTime");
		hasLid = nbt.getBoolean("hasLid");
		isDone = nbt.getBoolean("isDone");
		fireTemp = nbt.getFloat("temperature");
		
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		cookingPotInv = new ItemStack[cookingPotInv.length];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte itemslot = nbttagcompound1.getByte("Slot");
			if(itemslot >= 0 && itemslot < cookingPotInv.length)
				cookingPotInv[itemslot] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt)
	{
		NBTTagCompound fluidNBT = new NBTTagCompound();
		if(cookingPotFluid != null)
			cookingPotFluid.writeToNBT(fluidNBT);
		nbt.setTag("fluidNBT", fluidNBT);
		
		nbt.setInteger("recipeID", recipeID);
		nbt.setInteger("cookTimer", cookTimer);
		nbt.setInteger("cookTime", cookTime);
		nbt.setBoolean("hasLid", hasLid);
		nbt.setBoolean("isDone", isDone);
		nbt.setFloat("temperature", fireTemp);
		
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < cookingPotInv.length; i++)
		{
			if(cookingPotInv[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				cookingPotInv[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt)
	{
		NBTTagCompound fluidNBT = new NBTTagCompound();
		if(cookingPotFluid != null)
			cookingPotFluid.writeToNBT(fluidNBT);
		nbt.setTag("fluidNBT", fluidNBT);
		
		nbt.setInteger("recipeID", recipeID);
		nbt.setInteger("cookTimer", cookTimer);
		nbt.setInteger("cookTime", cookTime);
		nbt.setBoolean("hasLid", hasLid);
		nbt.setBoolean("isDone", isDone);
		nbt.setFloat("temperature", fireTemp);
		
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < cookingPotInv.length; i++)
		{
			if(cookingPotInv[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				cookingPotInv[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
	}
	
	public void ejectContents()
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 0.8F + 0.3F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		for (int i = 0; i < getSizeInventory() - 1; i++)
		{
			if(cookingPotInv[i]!= null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, cookingPotInv[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
				cookingPotInv[i] = null;
			}
		}
		
		entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, new ItemStack(CWTFCItems.ClayCookingPot, 1, 1));
		entityitem.motionX = (float)rand.nextGaussian() * f3;
		entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
		entityitem.motionZ = (float)rand.nextGaussian() * f3;
		worldObj.spawnEntityInWorld(entityitem);
	}
	
	@Override
	public int getSizeInventory() 
	{
		return INVSIZE;
	}

	@Override
	public ItemStack getStackInSlot(int Slot) 
	{
		return cookingPotInv[Slot];
	}

	@Override
	public ItemStack decrStackSize(int Slot, int Amount) 
	{
		if(cookingPotInv[Slot] != null)
		{
			if(cookingPotInv[Slot].stackSize <= Amount)
			{
				ItemStack itemstack = cookingPotInv[Slot];
				cookingPotInv[Slot] = null;
				return itemstack;
			}
			ItemStack itemstack1 = cookingPotInv[Slot].splitStack(Amount);
			if(cookingPotInv[Slot].stackSize == 0)
				cookingPotInv[Slot] = null;
			return itemstack1;
		}
		else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) 
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int Slot, ItemStack itemstack) 
	{
		cookingPotInv[Slot] = itemstack;
		
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        {
			itemstack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
	}

	@Override
	public String getInventoryName() 
	{
		return "Cooking Pot";
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
