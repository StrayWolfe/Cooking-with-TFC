package straywolfe.cookingwithtfc.common.tileentity;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.NetworkTileEntity;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.core.helper.Helper;
import straywolfe.cookingwithtfc.common.item.ItemTFCFoodTransform;
import straywolfe.cookingwithtfc.common.item.ItemTFCMealTransform;

public class TileBowl extends NetworkTileEntity
{
	private ItemStack saladContents[];
	private int invSize;
	private float bowlCoordX = -1;
	private float bowlCoordZ = -1;
	private int saladType = 0;
	
	public TileBowl()
	{
		invSize = 4;
		saladContents = new ItemStack[invSize];
	}
	
	public ItemStack makeSalad()
	{
		Item SaladType;
		
		if(saladType == 2)
			SaladType = CWTFCItems.PotatoSalad;
		else if(saladType == 1)
			SaladType = CWTFCItems.FruitSalad;
		else
			SaladType = CWTFCItems.VeggySalad;
		
		float sandwichWt = 0;
		float sandwichDecay = 0;
		int ingredCount = 0;
		for(int i = 0; i < saladContents.length; i++)
		{
			if(saladContents[i] != null)
			{
				sandwichWt += Food.getWeight(saladContents[i]);
				sandwichDecay += Math.max(Food.getDecay(saladContents[i]), 0);
				ingredCount++;
			}
		}
		
		ItemStack[] foods = new ItemStack[ingredCount];
		int tracker = 0;
		for(int i = 0; i < saladContents.length; i++)
		{
			if(saladContents[i] != null)
			{
				foods[tracker] = saladContents[i];
				tracker++;
			}
		}
		
		float[] foodPct;
		if(ingredCount == 4)
			foodPct = new float[]{0.25F, 0.25F, 0.25F, 0.25F};
		else if(ingredCount == 3)
			foodPct = new float[]{0.33F, 0.34F, 0.33F};
		else if(ingredCount == 2)
			foodPct = new float[]{0.5F, 0.5F};
		else
			foodPct = new float[]{1.0F};
		
		ItemStack salad = ItemTFCMealTransform.createTag(new ItemStack(SaladType), sandwichWt, sandwichDecay, foods, foodPct);
		
		Helper.combineTastes(salad.getTagCompound(), foods);
		
		return salad;
	}
	
	public void setTopIngredient(ItemStack is)
	{		
		for(int i = 0; i < invSize; i++)
		{
			if(saladContents[i] == null)
			{
				saladContents[i] = is;
				setSaladType();
				return;
			}
		}
	}
	
	public ItemStack getTopIngredient()
	{		
		for(int i = invSize - 1; i >= 0; i--)
		{
			if(saladContents[i] != null)
			{				
				ItemStack item = saladContents[i].copy();				
				saladContents[i] = null;
				item.stackSize = 1;
				setSaladType();
				return item;
			}
		}
		return new ItemStack(TFCItems.potteryBowl, 1, 1);
	}
	
	private void setSaladType()
	{
		int fruit = 0;
		int veggy = 0;
		boolean potato = false;
		
		for(int i = 0; i < invSize; i++)
		{
			if(saladContents[i] != null)
			{
				EnumFoodGroup fg = ((ItemTFCFoodTransform)saladContents[i].getItem()).getFoodGroup();
				
				if(fg == EnumFoodGroup.Fruit)
					fruit++;
				else if(saladContents[i].getItem() == CWTFCItems.potatoCWTFC)
					potato = true;
				else if(fg == EnumFoodGroup.Vegetable)
					veggy++;
			}
		}
		
		if(potato == true)
			saladType = 2;
		else if(fruit > veggy)
			saladType = 1;
		else
			saladType = 0;
	}
	
	public int getSaladType()
	{
		return saladType;
	}
	
	public ItemStack[] getSaladContents()
	{
		return saladContents;
	}
	
	public void setBowlCoord(float coord, int dir)
	{
		if(dir == 0)
			bowlCoordX = coord;
		else
			bowlCoordZ = coord;
	}
	
	public float getBowlCoord(int dir)
	{
		if(dir == 0)
			return bowlCoordX;
		else
			return bowlCoordZ;
	}
	
	public int getInvSize()
	{
		return invSize;
	}
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{			
			TFC_Core.handleItemTicking(saladContents, worldObj,  xCoord, yCoord, zCoord, 1.1f);
		}
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
	
	public void ejectItem()
	{
		ejectItem(new ItemStack(TFCItems.potteryBowl, 1, 1));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		saladContents = new ItemStack[invSize];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte itemslot = nbttagcompound1.getByte("Slot");
			if(itemslot >= 0 && itemslot < nbttaglist.tagCount())
				saladContents[itemslot] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		bowlCoordX = nbt.getFloat("bowlCoordX");
		bowlCoordZ = nbt.getFloat("bowlCoordZ");
		saladType = nbt.getInteger("saladType");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < invSize; i++)
		{
			if(saladContents[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				saladContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
		nbt.setFloat("bowlCoordX", bowlCoordX);
		nbt.setFloat("bowlCoordZ", bowlCoordZ);
		nbt.setInteger("saladType", saladType);
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt)
	{
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		saladContents = new ItemStack[invSize];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte itemslot = nbttagcompound1.getByte("Slot");
			if(itemslot >= 0 && itemslot < nbttaglist.tagCount())
				saladContents[itemslot] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		bowlCoordX = nbt.getFloat("bowlCoordX");
		bowlCoordZ = nbt.getFloat("bowlCoordZ");
		saladType = nbt.getInteger("saladType");
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt)
	{
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < invSize; i++)
		{
			if(saladContents[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				saladContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
		nbt.setFloat("bowlCoordX", bowlCoordX);
		nbt.setFloat("bowlCoordZ", bowlCoordZ);
		nbt.setInteger("saladType", saladType);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) 
	{
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		saladContents = new ItemStack[invSize];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte itemslot = nbttagcompound1.getByte("Slot");
			if(itemslot >= 0 && itemslot < nbttaglist.tagCount())
				saladContents[itemslot] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		bowlCoordX = nbt.getFloat("bowlCoordX");
		bowlCoordZ = nbt.getFloat("bowlCoordZ");
		saladType = nbt.getInteger("saladType");
	}
	
	@Override
	public void createDataNBT(NBTTagCompound nbt) 
	{
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < invSize; i++)
		{
			if(saladContents[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				saladContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
		nbt.setFloat("bowlCoordX", bowlCoordX);
		nbt.setFloat("bowlCoordZ", bowlCoordZ);
		nbt.setInteger("saladType", saladType);
	}
}
