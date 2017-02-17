package straywolfe.cookingwithtfc.api.managers;

import com.bioxx.tfc.Core.TFC_Time;

import net.minecraft.nbt.NBTTagCompound;

public class ChunkData 
{
	public int lastSpringTime;
	public boolean isUnloaded;

	public ChunkData()
	{
		lastSpringTime = TFC_Time.getYear();
	}
	
	public ChunkData(NBTTagCompound tag)
	{
		lastSpringTime = tag.getInteger("lastSpringTime");
	}
	
	public NBTTagCompound getTag()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("lastSpringTime", lastSpringTime);
		return tag;
	}
}
