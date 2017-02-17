package straywolfe.cookingwithtfc.api.managers;

import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ChunkDataManager 
{
	private LongHashMap chunkmap = new LongHashMap();
	
	public ChunkDataManager(World world)
	{}
	
	public void removeData(int x, int z)
	{
		long key = ChunkCoordIntPair.chunkXZ2Int(x, z);
		if(chunkmap.containsItem(key))
		{
			chunkmap.remove(key);
		}
	}
	
	public void addData(long key, ChunkData cd)
	{
		chunkmap.add(key, cd);
	}
	
	public void addData(Chunk chunk, ChunkData cd)
	{
		chunkmap.add(ChunkCoordIntPair.chunkXZ2Int(chunk.xPosition, chunk.zPosition), cd);
	}

	public void addData(int x, int z, ChunkData cd)
	{
		chunkmap.add(ChunkCoordIntPair.chunkXZ2Int(x, z), cd);
	}

	public ChunkData getData(int x, int z)
	{
		long key = ChunkCoordIntPair.chunkXZ2Int(x, z);
		if(chunkmap.containsItem(key))
			return (ChunkData) chunkmap.getValueByKey(key);
		else return null;
	}

	public ChunkData getData(long key)
	{
		if(chunkmap.containsItem(key))
			return (ChunkData) chunkmap.getValueByKey(key);
		else return null;
	}

	public boolean hasData(long key)
	{
		return chunkmap.containsItem(key);
	}
}
