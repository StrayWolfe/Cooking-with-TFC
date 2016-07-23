package straywolfe.cookingwithtfc.common.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class FoodRecord 
{
	public EntityPlayer player;
	public int RecordSize = 20; 
	public String[] FoodsEaten = new String[20];
	public int FoodListRef = 0;
	
	
	public FoodRecord(EntityPlayer player)
	{
		this.player = player;
	}
	
	public FoodRecord(EntityPlayer player, int recordSize)
	{
		this.player = player;
		
		RecordSize = recordSize;
	}
	
	public void readNBT(NBTTagCompound nbt)
	{		
		if (nbt.hasKey("foodRecord"))
		{
			NBTTagCompound foodRecord = nbt.getCompoundTag("foodRecord");
			
			NBTTagList tagList = foodRecord.getTagList("foodList", 10);
	        for (int i = 0; i < tagList.tagCount(); ++i)
	        {
	            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
	            String s = tagCompound.getString("Slot" + i);
	            FoodsEaten[i] = s;
	        }
	        this.RecordSize = foodRecord.getInteger("RecordSize");
			this.FoodListRef = foodRecord.getInteger("FoodListRef");
		}
	}
	
	public void writeNBT(NBTTagCompound nbt)
	{
		NBTTagCompound foodNBT = new NBTTagCompound();
		
		
		NBTTagList tagList = new NBTTagList();
		for(int i = 0; i < FoodsEaten.length; i++)
		{
			String s = FoodsEaten[i];
			if(s != null)
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setString("Slot" + i, s);
				tagList.appendTag(tag);
			}
		}
		foodNBT.setTag("foodList", tagList);
		
		foodNBT.setInteger("RecordSize", this.RecordSize);
		foodNBT.setInteger("FoodListRef", this.FoodListRef);
		
		nbt.setTag("foodRecord", foodNBT);
	}
}
