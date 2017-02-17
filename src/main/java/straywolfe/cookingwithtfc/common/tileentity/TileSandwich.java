package straywolfe.cookingwithtfc.common.tileentity;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.NetworkTileEntity;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.Food.ItemFoodTFC;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.core.helper.Helper;
import straywolfe.cookingwithtfc.common.item.ItemTFCMealTransform;

public class TileSandwich extends NetworkTileEntity
{
	private ItemStack sandwichContents[] = new ItemStack[6];
	private int topToast = 5;
	private String breadType = "";
	private String meatType = "";
	private float sandwichCoordX = -1;
	private float sandwichCoordZ = -1;
	
	public ItemStack makeSandwich()
	{
		Item sandwichtype;
		
		//Chicken Sandwich
		if(TFCItems.chickenRaw.getUnlocalizedName().equals(meatType) || 
				CWTFCItems.BoiledChicken.getUnlocalizedName().equals(meatType))
			sandwichtype = CWTFCItems.ChickenSandwich;
		//Ham Sandwich
		else if(TFCItems.porkchopRaw.getUnlocalizedName().equals(meatType) || 
				CWTFCItems.BoiledPork.getUnlocalizedName().equals(meatType))
			sandwichtype = CWTFCItems.HamSandwich;
		//Egg Sandwich
		else if(TFCItems.eggCooked.getUnlocalizedName().equals(meatType))
			sandwichtype = CWTFCItems.FriedEggSandwich;
		
		//Mutton Sandwich
		else if(TFCItems.muttonRaw.getUnlocalizedName().equals(meatType))
			sandwichtype = CWTFCItems.MuttonSandwich;
		
		//Roast Beef Sandwich
		else if(TFCItems.beefRaw.getUnlocalizedName().equals(meatType) || 
				CWTFCItems.BoiledBeef.getUnlocalizedName().equals(meatType))
			sandwichtype = CWTFCItems.RoastBeefSandwich;
		
		//Salmon Sandwich
		else if(TFCItems.fishRaw.getUnlocalizedName().equals(meatType) || 
				CWTFCItems.BoiledFish.getUnlocalizedName().equals(meatType) ||
				TFCItems.calamariRaw.getUnlocalizedName().equals(meatType))
			sandwichtype = CWTFCItems.SalmonSandwich;
		
		//Steak Sandwich
		else if(TFCItems.venisonRaw.getUnlocalizedName().equals(meatType) || 
				CWTFCItems.BoiledVenison.getUnlocalizedName().equals(meatType) ||
				TFCItems.horseMeatRaw.getUnlocalizedName().equals(meatType))
			sandwichtype = CWTFCItems.VenisonSteakSandwich;
		
		//Toast Sandwich
		else if(topToast == 1)
			sandwichtype = CWTFCItems.ToastSandwich;
		
		//Vegetarian Sandwich
		else
			sandwichtype = CWTFCItems.VegetarianSandwich;
		
		int breadMeta = 0;
		
		if(TFCItems.cornBread.getUnlocalizedName().equals(breadType))
			breadMeta = 1;
		else if(TFCItems.oatBread.getUnlocalizedName().equals(breadType))
			breadMeta = 2;
		else if(TFCItems.riceBread.getUnlocalizedName().equals(breadType))
			breadMeta = 3;
		else if(TFCItems.ryeBread.getUnlocalizedName().equals(breadType))
			breadMeta = 4;
		else if(TFCItems.wheatBread.getUnlocalizedName().equals(breadType))
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
		for(int i = 0; i < sandwichContents.length; i++)
		{
			if(sandwichContents[i] == null)
			{
				sandwichContents[i] = is;
				
				if(Helper.isOre("itemBread", is))
				{
					if(i != 0)
						topToast = i;
					else
						breadType = is.getItem().getUnlocalizedName();						
				}
				
				
				if(((ItemFoodTFC)is.getItem()).getFoodGroup() == EnumFoodGroup.Protein && meatType == "")
					meatType = is.getItem().getUnlocalizedName();
					
				return;
			}
		}
	}
	
	public ItemStack getTopSandwichItem()
	{
		for(int i = sandwichContents.length - 1; i >= 0; i--)
		{
			if(sandwichContents[i] != null)
			{				
				ItemStack item = sandwichContents[i].copy();
				
				if(i != 0 && Helper.isOre("itemBread", item))
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
		return sandwichContents.length;
	}
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{			
			TFC_Core.handleItemTicking(sandwichContents, worldObj,  xCoord, yCoord, zCoord, 1.1f);
			
			boolean destroyBlock = true;
			
			for(int i = 0; i < sandwichContents.length; i++)
			{
				if(sandwichContents[i] != null)
					destroyBlock = false;
				else if(i + 1 < sandwichContents.length && sandwichContents[i + 1] != null)
				{
					ejectItem();
					worldObj.setBlockToAir(xCoord, yCoord, zCoord);
					worldObj.removeTileEntity(xCoord, yCoord, zCoord);
					return;
				}
			}
			
			if(destroyBlock)
			{
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
				worldObj.removeTileEntity(xCoord, yCoord, zCoord);
			}
		}
	}
	
	public void ejectItem()
	{
		for(int i = 0; i < sandwichContents.length; i++)
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
		sandwichContents = new ItemStack[sandwichContents.length];
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
		for(int i = 0; i < sandwichContents.length; i++)
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
		sandwichContents = new ItemStack[sandwichContents.length];
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
		for(int i = 0; i < sandwichContents.length; i++)
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
		sandwichContents = new ItemStack[sandwichContents.length];
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
		for(int i = 0; i < sandwichContents.length; i++)
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
