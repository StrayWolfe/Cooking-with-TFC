package straywolfe.cookingwithtfc.common.tileentity;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.NetworkTileEntity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.CWTFCItems;

public class TileMixBowl extends NetworkTileEntity
{	
	private ItemStack bowlContents;
	private float bowlCoordX = -1;
	private float bowlCoordZ = -1;
	boolean complete;
	
	public TileMixBowl()
	{
		complete = false;
	}
	
	public ItemStack getOutput()
	{
		if(bowlContents != null)
		{
			Item doughType = null;
		
			if(bowlContents.getItem() == CWTFCItems.barleyGroundCWTFC)
				doughType = CWTFCItems.barleyDoughCWTFC;
			else if(bowlContents.getItem() == CWTFCItems.cornmealGroundCWTFC)
				doughType = CWTFCItems.cornmealDoughCWTFC;
			else if(bowlContents.getItem() == CWTFCItems.oatGroundCWTFC)
				doughType = CWTFCItems.oatDoughCWTFC;
			else if(bowlContents.getItem() == CWTFCItems.riceGroundCWTFC)
				doughType = CWTFCItems.riceDoughCWTFC;
			else if(bowlContents.getItem() == CWTFCItems.ryeGroundCWTFC)
				doughType = CWTFCItems.ryeDoughCWTFC;
			else if(bowlContents.getItem() == CWTFCItems.wheatGroundCWTFC)
				doughType = CWTFCItems.wheatDoughCWTFC;
			
			ItemStack dough = new ItemStack(doughType);
			
			if(bowlContents.stackTagCompound != null)
			{
				dough.stackTagCompound = (NBTTagCompound)bowlContents.stackTagCompound.copy();
			}
			
			return dough;
		}
		
		return null;
	}
	
	public void setContents(ItemStack item)
	{
		bowlContents = item;
	}
	
	public ItemStack getContents()
	{
		return bowlContents;
	}
	
	public void setCompleted(boolean value)
	{
		complete = value;
	}
	
	public boolean getCompleted()
	{
		return complete;
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
	
	public void ejectItem()
	{
		ejectItem(bowlContents);
		ejectItem(new ItemStack(CWTFCBlocks.mixingBowl, 1, 1));
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
	public void updateEntity()
	{
		if(!worldObj.isRemote && bowlContents != null)
		{			
			bowlContents = TFC_Core.tickDecay(bowlContents, worldObj,  xCoord, yCoord, zCoord, 1f, 1f);
			if(bowlContents == null)
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		bowlContents = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("bowlContents"));
		bowlCoordX = nbt.getFloat("bowlCoordX");
		bowlCoordZ = nbt.getFloat("bowlCoordZ");
		complete = nbt.getBoolean("complete");
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if(bowlContents != null)
		{
			NBTTagCompound bowlNBT = new NBTTagCompound();
			bowlContents.writeToNBT(bowlNBT);
			nbt.setTag("bowlContents", bowlNBT);
		}
		nbt.setFloat("bowlCoordX", bowlCoordX);
		nbt.setFloat("bowlCoordZ", bowlCoordZ);
		nbt.setBoolean("complete", complete);
	}
	
	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		bowlContents = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("bowlContents"));
		bowlCoordX = nbt.getFloat("bowlCoordX");
		bowlCoordZ = nbt.getFloat("bowlCoordZ");
		complete = nbt.getBoolean("complete");
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		if(bowlContents != null)
		{
			NBTTagCompound bowlNBT = new NBTTagCompound();
			bowlContents.writeToNBT(bowlNBT);
			nbt.setTag("bowlContents", bowlNBT);
		}
		nbt.setFloat("bowlCoordX", bowlCoordX);
		nbt.setFloat("bowlCoordZ", bowlCoordZ);
		nbt.setBoolean("complete", complete);
	}
	
	@Override
	public void handleDataPacket(NBTTagCompound nbt) 
	{
		bowlContents = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("bowlContents"));
		bowlCoordX = nbt.getFloat("bowlCoordX");
		bowlCoordZ = nbt.getFloat("bowlCoordZ");
		complete = nbt.getBoolean("complete");
	}
	
	@Override
	public void createDataNBT(NBTTagCompound nbt) 
	{
		if(bowlContents != null)
		{
			NBTTagCompound bowlNBT = new NBTTagCompound();
			bowlContents.writeToNBT(bowlNBT);
			nbt.setTag("bowlContents", bowlNBT);
		}
		nbt.setFloat("bowlCoordX", bowlCoordX);
		nbt.setFloat("bowlCoordZ", bowlCoordZ);
		nbt.setBoolean("complete", complete);
	}
}
