package com.JAWolfe.cookingwithtfc.tileentities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.JAWolfe.cookingwithtfc.crafting.FoodManager;
import com.JAWolfe.cookingwithtfc.crafting.FoodRecipe;
import com.JAWolfe.cookingwithtfc.init.CWTFCBlocks;
import com.JAWolfe.cookingwithtfc.items.ItemTFCFoodTransform;
import com.JAWolfe.cookingwithtfc.items.ItemTFCMealTransform;
import com.JAWolfe.cookingwithtfc.items.ItemBlocks.ItemMixingBowl;
import com.JAWolfe.cookingwithtfc.util.Helper;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.TileEntities.NetworkTileEntity;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TEPrepTable extends NetworkTileEntity implements IInventory
{
	public ItemStack[] prepTableItemStacks = new ItemStack[25];
	private List<FoodRecipe> recipeList;
	public int tableType;
	private int recipeListRef;
	private ForgeDirection direction;
	private boolean TableOpen = false;
	
	public static final int OUTPUT_SLOT = 0;
	public static final int FOOD_INPUT_START = 1;
	public static final int COOKWARE_INPUT_START = 17;
	
	public TEPrepTable()
	{
		recipeList = new ArrayList<FoodRecipe>();
	}
	
	@Override
	public void updateEntity()
	{
		TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);
	}
	
	public void setDirection(ForgeDirection dir)
	{
		direction = dir;
	}
	
	public ForgeDirection getDirection()
	{
		return direction;
	}
	
	public void consumeIngredients(ItemStack is, EntityPlayer player)
	{
		if(getRecipeListSize() > 0)
		{
			float foodWt = Food.getWeight(is);
			
			ItemStack[] ingreds = recipeList.get(recipeListRef).getReqIngred();
			for(int i = 0; i < ingreds.length; i++)
			{
				for(int j = FOOD_INPUT_START; j < COOKWARE_INPUT_START; j++)
				{
					ItemStack foodStack = this.getStackInSlot(j);
					
					if(foodStack != null && foodStack.getItem() == ingreds[i].getItem() && foodStack.getItemDamage() == ingreds[i].getItemDamage())
					{
						float ingredWt = Food.getWeight(foodStack) - Math.max(Food.getDecay(foodStack), 0);
						float remainingWt = ingredWt - (foodWt * recipeList.get(recipeListRef).getPctIngred(i));
						if(remainingWt <= 0)
						{
							this.setInventorySlotContents(j, null);
							break;
						}
						else
						{
							Food.setWeight(foodStack, remainingWt);
							Food.setDecay(foodStack, 0);
							this.setInventorySlotContents(j, foodStack);
							break;
						}
					}
				}
			}
			
			ItemStack[] cookware = recipeList.get(recipeListRef).getReqCookware();
			for(int i = 0; i < cookware.length; i++)
			{
				for(int j = COOKWARE_INPUT_START; j < this.getSizeInventory(); j++)
				{
					ItemStack cookwareStack = this.getStackInSlot(j);
					if(cookwareStack != null)
					{
						if(cookwareStack.getItem() == cookware[i].getItem() &&
								cookwareStack.getItemDamage() == cookware[i].getItemDamage())
						{
							if(cookwareStack.getItem() == TFCItems.potterySmallVessel)
							{
								setInventorySlotContents(j, null);
								worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
								break;
							}
							else if(cookwareStack.getItem() == TFCItems.potteryBowl)
							{
								if(cookwareStack.stackSize > 1)
									cookwareStack.stackSize--;
								else
								{
									setInventorySlotContents(j, null);
									worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
								}
								break;
							}
							else if(cookwareStack.getItem() == TFCItems.potteryJug)
							{
								if(cookwareStack.getItemDamage() == 2)
								{
									setInventorySlotContents(j, new ItemStack(TFCItems.potteryJug, 1, 1));
								}
								else if(cookwareStack.getItemDamage() == 1)
								{
									setInventorySlotContents(j, null);
									worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
								}
								break;
							}
						}
						
						if(cookwareStack.getItem() instanceof ItemKnife && cookware[i].getItem() instanceof ItemKnife)
						{
							getStackInSlot(j).damageItem(1, player);
							break;
						}
					}
				}
			}
			TFC_Core.getSkillStats(player).increaseSkill(Global.SKILL_COOKING, 1);
		}
	}
	
	public void updateRecipeOutput()
	{
		if(getRecipeListSize() > 0)
		{
			FoodRecipe outputRecipe = recipeList.get(recipeListRef);
			ItemStack isOutput = new ItemStack(outputRecipe.getResult().getItem(), 1, outputRecipe.getResult().getItemDamage());
			float recipeWeight;
			if(isOutput.getItem() instanceof ItemTFCFoodTransform)
				recipeWeight = ((ItemFoodTFC)isOutput.getItem()).getFoodMaxWeight(isOutput);
			else
				recipeWeight = ((ItemTFCMealTransform)isOutput.getItem()).getMaxFoodWt();
			
			ItemStack[] ingreds = outputRecipe.getReqIngred();
			ItemStack[] slotIngreds = new ItemStack[ingreds.length];
			for(int i = 0; i < ingreds.length; i++)
			{
				for(int j = FOOD_INPUT_START; j < COOKWARE_INPUT_START; j++)
				{
					if(this.getStackInSlot(j) != null && this.getStackInSlot(j).getItem() == ingreds[i].getItem() && this.getStackInSlot(j).getItemDamage() == ingreds[i].getItemDamage())
					{
						slotIngreds[i] = this.getStackInSlot(j);
						float weight = Food.getWeight(this.getStackInSlot(j));
						float decay = Math.max(Food.getDecay(this.getStackInSlot(j)), 0);
						float IngredWt =  weight - decay;
						if(IngredWt < recipeWeight * outputRecipe.getPctIngred(i))
							recipeWeight = IngredWt	* (1/outputRecipe.getPctIngred(i));
					}
						
				}
			}
			
			if(isOutput.getItem() instanceof ItemTFCFoodTransform)
				ItemTFCFoodTransform.createTag(isOutput, recipeWeight, 0);
			else
			{
				ItemTFCMealTransform.createTag(isOutput, recipeWeight, 0, ingreds, outputRecipe.getPctList());
			}
				
			Helper.combineTastes(isOutput.getTagCompound(), slotIngreds);
			
			if(outputRecipe.isSalted())
				Food.setSalted(isOutput, true);
			
			this.setInventorySlotContents(OUTPUT_SLOT, isOutput);
		}
	}
	
	public void popRecipes(EntityPlayer player)
	{
		ItemStack[] ingredients = new ItemStack[16];
		ItemStack[] cookware = new ItemStack[8];
		
		for(int i = 0; i < ingredients.length; i++)
			ingredients[i] = this.getStackInSlot(i + FOOD_INPUT_START);
		
		for(int i = 0; i < cookware.length; i++)
			cookware[i] = this.getStackInSlot(i + COOKWARE_INPUT_START);
		
		recipeList.clear();
		recipeList = FoodManager.getInstance().findMatchingRecipes(cookware, ingredients, player);
		recipeListRef = 0;
		
		updateRecipeOutput();
	}
	
	public void ejectItem()
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		if(tableType < 16)
		{
			entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, 
					new ItemStack(CWTFCBlocks.prepTable, 1, tableType));
		}
		else
		{
			entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, 
					new ItemStack(CWTFCBlocks.prepTable2, 1));
		}
		spawnItemEntity(entityitem, rand, f3);
		
		for(int i = 1; i < prepTableItemStacks.length; i++)
		{
			if(prepTableItemStacks[i] != null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, 
						prepTableItemStacks[i]);
				spawnItemEntity(entityitem, rand, f3);
			}
		}
	}
	
	private void spawnItemEntity(EntityItem entityitem, Random rand, float f)
	{
		entityitem.motionX = (float)rand.nextGaussian() * f;
		entityitem.motionY = (float)rand.nextGaussian() * f + 0.05F;
		entityitem.motionZ = (float)rand.nextGaussian() * f;
		worldObj.spawnEntityInWorld(entityitem);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);		
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		prepTableItemStacks = new ItemStack[25];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte itemslot = nbttagcompound1.getByte("Slot");
			if(itemslot >= 0 && itemslot < prepTableItemStacks.length)
				prepTableItemStacks[itemslot] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		this.direction = ForgeDirection.getOrientation(nbt.getInteger("Direction"));
		this.tableType = nbt.getInteger("TableType");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 1; i < prepTableItemStacks.length; i++)
		{
			if(prepTableItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				prepTableItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
		
		nbt.setInteger("Direction", this.direction.ordinal());
		nbt.setInteger("TableType", this.tableType);
	}
	
	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		this.direction = ForgeDirection.getOrientation(nbt.getInteger("Direction"));
		this.tableType = nbt.getInteger("TableType");
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) 
	{
		this.direction = ForgeDirection.getOrientation(nbt.getInteger("Direction"));
		this.tableType = nbt.getInteger("TableType");
	}
	
	@Override
	public void createDataNBT(NBTTagCompound nbt) 
	{
		nbt.setInteger("Direction", this.direction.ordinal());
		nbt.setInteger("TableType", this.tableType);
	}
	
	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		nbt.setInteger("Direction", this.direction.ordinal());
		nbt.setInteger("TableType", this.tableType);
	}

	@Override
	public int getSizeInventory() 
	{
		return this.prepTableItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return this.prepTableItemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int size) 
	{
		if(this.prepTableItemStacks[slot] != null)
		{
			ItemStack is;
			if(this.prepTableItemStacks[slot].stackSize <= size)
			{
				is = this.prepTableItemStacks[slot];
				this.prepTableItemStacks[slot] = null;
				this.markDirty();
				return is;
			}
			else
			{
				is = this.prepTableItemStacks[slot].splitStack(size);
				if(this.prepTableItemStacks[slot].stackSize == 0)
					this.prepTableItemStacks[slot] = null;
				this.markDirty();
				return is;
			}
		}
		else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) 
	{
		if(this.prepTableItemStacks[slot] != null)
		{
			ItemStack is = this.prepTableItemStacks[slot];
			this.prepTableItemStacks[slot] = null;
			return is;
		}
		else
			return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack is) 
	{		
		if(!TFC_Core.areItemsEqual(this.prepTableItemStacks[slot], is))
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		this.prepTableItemStacks[slot] = is;
	}

	@Override
	public String getInventoryName() 
	{
		return "FoodPrepTable";
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
		boolean withingRange = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
		if(withingRange)
			return !this.getOppositeTE().isOpen() || !this.isOpen();
			
		return false;
	}

	@Override
	public void openInventory() 
	{
		this.TableOpen = true;
	}

	@Override
	public void closeInventory() 
	{
		this.TableOpen = false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return false;
	}
	
	public int getRecipeListSize()
	{
		return this.recipeList.size();
	}
	
	public void clearRecipeList()
	{
		this.recipeList.clear();
	}
	
	public int getRecipeListRef()
	{
		return this.recipeListRef;
	}
	
	public void addRecipeListRef()
	{
		this.recipeListRef++;
		updateRecipeOutput();
	}
	
	public void subRecipeListRef()
	{
		this.recipeListRef--;
		updateRecipeOutput();
	}
	
	public boolean isCookware(ItemStack is)
	{
		if(is.getItem() == TFCItems.potterySmallVessel && is.getItemDamage() == 1 && is.stackTagCompound == null)
			return true;
		else if((is.getItem() == TFCItems.potteryBowl || is.getItem() == TFCItems.potteryJug) && is.getItemDamage() != 0)
			return true;
		else if(is.getItem() instanceof ItemMixingBowl  && is.getItemDamage() == 1)
			return true;
		else if(is.getItem() instanceof ItemKnife)
			return true;
		
		return  false;
	}
	
	public void updateOppositeTable()
	{
		TEPrepTable oppTable = getOppositeTE();
		for(int i = 0; i < this.getSizeInventory(); i++)
			oppTable.setInventorySlotContents(i, this.getStackInSlot(i));
	}
	
	public TEPrepTable getOppositeTE()
	{
		ForgeDirection dir = this.getDirection();
		int x = this.xCoord;
        int z = this.zCoord;

    	x = this.xCoord + dir.offsetX;
    	z = this.zCoord + dir.offsetZ;
        
        World world = this.getWorldObj();
        
        Block tableBlock;
        if(tableType == 16)
        	tableBlock = CWTFCBlocks.prepTable2;
        else
        	tableBlock = CWTFCBlocks.prepTable;
        
        if (world.getBlock(x, this.yCoord, z).equals(tableBlock))
            return (TEPrepTable) world.getTileEntity(x, this.yCoord, z);
        else
            return null;
	}
	
	public boolean isOpen()
	{
		return this.TableOpen;
	}
}
