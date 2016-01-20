package com.JAWolfe.cookingwithtfc.tileentities;

import com.JAWolfe.cookingwithtfc.entities.EntityTransformChickenTFC;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.TileEntities.TENestBox;
import com.bioxx.tfc.api.TFCOptions;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TENestBoxCWTFC extends TENestBox
{	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			EntityAnimal bird = getBird();
			if(bird!=null)
			{
				ItemStack item = ((EntityTransformChickenTFC)bird).getEggs();

				EntityTransformChickenTFC father = (EntityTransformChickenTFC) getRooster();
				for(int i = 0; item != null && i < this.getSizeInventory();i++)
				{
					if(inventory[i] == null)
					{
						ItemFoodTFC.createTag(item, 2.0f);
						if(father != null)
						{
							NBTTagCompound nbt = item.getTagCompound();
							nbt.setLong("Fertilized", TFC_Time.getTotalTicks() + (long) (TFCOptions.animalTimeMultiplier * TFC_Time.ticksInMonth * 0.75f));
							nbt.setTag("Genes", this.createGenes((EntityTransformChickenTFC) bird, father));
							item.setTagCompound(nbt);
						}
						worldObj.playSoundAtEntity(bird,"mob.chicken.plop", 1.0F, (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F + 1.0F);
						setInventorySlotContents(i,item);
						break;
					}
				}
			}

			//Care for the eggs in the hatchery
			for(int i = 0;i < this.getSizeInventory();i++)
			{
				if(inventory[i] != null && inventory[i].getTagCompound() != null && inventory[i].getTagCompound().hasKey("Fertilized"))
				{
					long time = inventory[i].getTagCompound().getLong("Fertilized");
					if(time <= TFC_Time.getTotalTicks())
					{
						EntityTransformChickenTFC chick = new EntityTransformChickenTFC(worldObj, xCoord + 0.5, yCoord + 1, zCoord + 0.5, 
								(NBTTagCompound) inventory[i].getTagCompound().getTag("Genes"));
						if(worldObj.isAirBlock(xCoord, yCoord + 1, zCoord))
							chick.setLocationAndAngles (xCoord, yCoord + 1, zCoord, 0.0F, 0.0F);
						else
							chick.setLocationAndAngles (xCoord, yCoord, zCoord, 0.0F, 0.0F);
						chick.rotationYawHead = chick.rotationYaw;
						chick.renderYawOffset = chick.rotationYaw;
						worldObj.spawnEntityInWorld(chick);
						inventory[i] = null;
					}
				}
			}
		}	
	}
}
