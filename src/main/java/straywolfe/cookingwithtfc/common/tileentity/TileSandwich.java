package straywolfe.cookingwithtfc.common.tileentity;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.NetworkTileEntity;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.core.helper.Helper;
import straywolfe.cookingwithtfc.common.item.ItemBread;
import straywolfe.cookingwithtfc.common.item.ItemTFCFoodTransform;
import straywolfe.cookingwithtfc.common.item.ItemTFCMealTransform;

public class TileSandwich extends NetworkTileEntity
{
	private ItemStack sandwichContents[];
	private int invSize;
	private int topToast;
	private String breadType;
	private String meatType;
	private float sandwichCoordX;
	private float sandwichCoordZ;
	
	public TileSandwich()
	{
		invSize = 6;
		sandwichContents = new ItemStack[invSize];
		topToast = 5;
		breadType = "";
		meatType = "";
		sandwichCoordX = 0;
		sandwichCoordZ = 0;
	}
	
	public ItemStack makeSandwich()
	{
		Item sandwichtype;
		
		//Chicken Sandwich
		if(CWTFCItems.chickenCookedCWTFC.getUnlocalizedName().equals(meatType) || 
				CWTFCItems.BoiledChicken.getUnlocalizedName().equals(meatType))
			sandwichtype = CWTFCItems.ChickenSandwich;
		//Ham Sandwich
		else if(CWTFCItems.porkchopCookedCWTFC.getUnlocalizedName().equals(meatType) || 
				CWTFCItems.BoiledPork.getUnlocalizedName().equals(meatType))
			sandwichtype = CWTFCItems.HamSandwich;
		//Egg Sandwich
		else if(CWTFCItems.eggCookedCWTFC.getUnlocalizedName().equals(meatType))
			sandwichtype = CWTFCItems.FriedEggSandwich;
		
		//Mutton Sandwich
		else if(CWTFCItems.muttonCookedCWTFC.getUnlocalizedName().equals(meatType))
			sandwichtype = CWTFCItems.MuttonSandwich;
		
		//Roast Beef Sandwich
		else if(CWTFCItems.beefCookedCWTFC.getUnlocalizedName().equals(meatType) || 
				CWTFCItems.BoiledBeef.getUnlocalizedName().equals(meatType))
			sandwichtype = CWTFCItems.RoastBeefSandwich;
		
		//Salmon Sandwich
		else if(CWTFCItems.fishCookedCWTFC.getUnlocalizedName().equals(meatType) || 
				CWTFCItems.BoiledFish.getUnlocalizedName().equals(meatType) ||
				CWTFCItems.calamariCookedCWTFC.getUnlocalizedName().equals(meatType))
			sandwichtype = CWTFCItems.SalmonSandwich;
		
		//Steak Sandwich
		else if(CWTFCItems.venisonCookedCWTFC.getUnlocalizedName().equals(meatType) || 
				CWTFCItems.BoiledVenison.getUnlocalizedName().equals(meatType) ||
				CWTFCItems.horseMeatCookedCWTFC.getUnlocalizedName().equals(meatType))
			sandwichtype = CWTFCItems.VenisonSteakSandwich;
		
		//Toast Sandwich
		else if(topToast == 1)
			sandwichtype = CWTFCItems.ToastSandwich;
		
		//Vegetarian Sandwich
		else
			sandwichtype = CWTFCItems.VegetarianSandwich;
		
		int breadMeta = 0;
		
		if(CWTFCItems.cornBreadCWTFC.getUnlocalizedName().equals(breadType))
			breadMeta = 1;
		else if(CWTFCItems.oatBreadCWTFC.getUnlocalizedName().equals(breadType))
			breadMeta = 2;
		else if(CWTFCItems.riceBreadCWTFC.getUnlocalizedName().equals(breadType))
			breadMeta = 3;
		else if(CWTFCItems.ryeBreadCWTFC.getUnlocalizedName().equals(breadType))
			breadMeta = 4;
		else if(CWTFCItems.wheatBreadCWTFC.getUnlocalizedName().equals(breadType))
			breadMeta = 5;
		
		float sandwichWt = 0;
		float sandwichDecay = 0;
		int ingredCount = 0;
		for(int i = 0; i < sandwichContents.length; i++)
		{
			if(sandwichContents[i] != null)
			{
				sandwichWt += Food.getWeight(sandwichContents[i]);
				sandwichDecay += Math.max(Food.getDecay(sandwichContents[i]), 0);
				ingredCount++;
			}
		}
		
		ItemStack[] foods = new ItemStack[ingredCount];
		int tracker = 0;
		for(int i = 0; i < sandwichContents.length; i++)
		{
			if(sandwichContents[i] != null)
			{
				foods[tracker] = sandwichContents[i];
				tracker++;
			}
		}
		
		float[] foodPct;
		if(ingredCount == 6)
			foodPct = new float[]{0.1F, 0.2F, 0.2F, 0.2F, 0.2F, 0.1F};
		else if(ingredCount == 5)
			foodPct = new float[]{0.125F, 0.25F, 0.25F, 0.25F, 0.125F};
		else if(ingredCount == 4)
			foodPct = new float[]{0.17F, 0.33F, 0.33F, 0.17F};
		else if(ingredCount == 3)
			foodPct = new float[]{0.25F, 0.5F, 0.25F};
		else
			foodPct = new float[]{0.5F, 0.5F};
		
		ItemStack sandwich = ItemTFCMealTransform.createTag(new ItemStack(sandwichtype, 1, breadMeta), sandwichWt, sandwichDecay, foods, foodPct);
		
		Helper.combineTastes(sandwich.getTagCompound(), foods);
		
		return sandwich;
	}
	
	public void setTopSandwichItem(ItemStack is)
	{
		for(int i = 0; i < invSize; i++)
		{
			if(sandwichContents[i] == null)
			{
				sandwichContents[i] = is;
				
				if(is.getItem() instanceof ItemBread)
				{
					if(i != 0)
						topToast = i;
					else
						breadType = is.getItem().getUnlocalizedName();						
				}
				
				
				if(((ItemTFCFoodTransform)is.getItem()).getFoodGroup() == EnumFoodGroup.Protein && meatType == "")
					meatType = is.getItem().getUnlocalizedName();
					
				return;
			}
		}
	}
	
	public ItemStack getTopSandwichItem()
	{
		for(int i = invSize - 1; i >= 0; i--)
		{
			if(sandwichContents[i] != null)
			{				
				ItemStack item = sandwichContents[i].copy();
				
				if(i != 0 && item.getItem() instanceof ItemBread)
					topToast = 5;
				
				if(item.getItem().getUnlocalizedName().equals(meatType))
					meatType = "";
				
				sandwichContents[i] = null;
				item.stackSize = 1;
				return item;
			}
		}
		return null;
	}
	
	public ItemStack[] getSandwichContents()
	{
		return sandwichContents;
	}
	
	public int getTopToast()
	{
		return topToast;
	}
	
	public void setSandwichCoord(float coord, int dir)
	{
		if(dir == 0)
			sandwichCoordX = coord;
		else
			sandwichCoordZ = coord;
	}
	
	public float getSandwichCoord(int dir)
	{
		if(dir == 0)
			return sandwichCoordX;
		else
			return sandwichCoordZ;
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
			TFC_Core.handleItemTicking(sandwichContents, worldObj,  xCoord, yCoord, zCoord, 1.1f);
		}
	}
	
	public void ejectItem()
	{
		for(int i = 0; i < invSize; i++)
		{
			ejectItem(sandwichContents[i]);
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
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		sandwichContents = new ItemStack[invSize];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte itemslot = nbttagcompound1.getByte("Slot");
			if(itemslot >= 0 && itemslot < nbttaglist.tagCount())
				sandwichContents[itemslot] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		topToast = nbt.getInteger("topToast");
		breadType = nbt.getString("breadType");
		meatType = nbt.getString("meatType");
		sandwichCoordX = nbt.getFloat("sandwichCoordX");
		sandwichCoordZ = nbt.getFloat("sandwichCoordZ");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < invSize; i++)
		{
			if(sandwichContents[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				sandwichContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
		nbt.setInteger("topToast", topToast);
		nbt.setString("breadType", breadType);
		nbt.setString("meatType", meatType);
		nbt.setFloat("sandwichCoordX", sandwichCoordX);
		nbt.setFloat("sandwichCoordZ", sandwichCoordZ);
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		sandwichContents = new ItemStack[invSize];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte itemslot = nbttagcompound1.getByte("Slot");
			if(itemslot >= 0 && itemslot < nbttaglist.tagCount())
				sandwichContents[itemslot] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		topToast = nbt.getInteger("topToast");
		breadType = nbt.getString("breadType");
		meatType = nbt.getString("meatType");
		sandwichCoordX = nbt.getFloat("sandwichCoordX");
		sandwichCoordZ = nbt.getFloat("sandwichCoordZ");
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < invSize; i++)
		{
			if(sandwichContents[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				sandwichContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
		nbt.setInteger("topToast", topToast);
		nbt.setString("breadType", breadType);
		nbt.setString("meatType", meatType);
		nbt.setFloat("sandwichCoordX", sandwichCoordX);
		nbt.setFloat("sandwichCoordZ", sandwichCoordZ);
	}
	
	@Override
	public void handleDataPacket(NBTTagCompound nbt) 
	{
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		sandwichContents = new ItemStack[invSize];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte itemslot = nbttagcompound1.getByte("Slot");
			if(itemslot >= 0 && itemslot < nbttaglist.tagCount())
				sandwichContents[itemslot] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		topToast = nbt.getInteger("topToast");
		breadType = nbt.getString("breadType");
		meatType = nbt.getString("meatType");
		sandwichCoordX = nbt.getFloat("sandwichCoordX");
		sandwichCoordZ = nbt.getFloat("sandwichCoordZ");
	}
	
	@Override
	public void createDataNBT(NBTTagCompound nbt) 
	{
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < invSize; i++)
		{
			if(sandwichContents[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				sandwichContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
		nbt.setInteger("topToast", topToast);
		nbt.setString("breadType", breadType);
		nbt.setString("meatType", meatType);
		nbt.setFloat("sandwichCoordX", sandwichCoordX);
		nbt.setFloat("sandwichCoordZ", sandwichCoordZ);
	}
}
