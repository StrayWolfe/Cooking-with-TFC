package straywolfe.cookingwithtfc.common.tileentity;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.FloraIndex;
import com.bioxx.tfc.Food.FloraManager;
import com.bioxx.tfc.TileEntities.NetworkTileEntity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.block.BlockNutLeaves;
import straywolfe.cookingwithtfc.common.block.BlockNutTree;

public class TileNutTree extends NetworkTileEntity
{
	public boolean isTrunk;
	public int height;
	public long birthTimeWood;
	public long birthTimeLeaves;
	public int isSapling;
	
	public TileNutTree()
	{
		height = 0;
		isTrunk = false;
		birthTimeWood = birthTimeLeaves = TFC_Time.getTotalDays();
		isSapling = -1;
	}
	
	public void initBirth()
	{
		birthTimeWood = birthTimeLeaves = TFC_Time.getTotalDays();
	}
	
	public void setBirthWood(long t)
	{
		birthTimeWood = t;
	}

	public void increaseBirthWood(long t)
	{
		birthTimeWood += t;
	}

	public void setBirthLeaves(long t)
	{
		birthTimeLeaves = t;
	}
	public void increaseBirthLeaves(long t)
	{
		birthTimeLeaves += t;
	}

	public void setTrunk(boolean b)
	{
		isTrunk = b;
	}

	public void setHeight(int h)
	{
		height = h;
	}
	
	public void initBirth(boolean isTrunk, int h)
	{
		setTrunk(isTrunk);
		setHeight(h);
		isSapling = 0;
		initBirth();
	}

	public void setupBirth(boolean isTrunk, int h, long woodBirth, long leafBirth)
	{
		setTrunk(isTrunk);
		setHeight(h);
		setBirthWood(woodBirth);
		setBirthLeaves(leafBirth);
		isSapling = 0;
	}
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
			if(block instanceof BlockNutTree)
			{
				BlockNutTree tree = (BlockNutTree)block;
				int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
				float temp = TFC_Climate.getHeightAdjustedTemp(worldObj, xCoord, yCoord, zCoord);
				int month = TFC_Time.getSeasonAdjustedMonth(zCoord);
				int LEAF_GROWTH_RATE = 20;
				long TRUNK_GROW_TIME = (long) (1.5F * TFC_Time.daysInMonth);
				long BRANCH_GROW_TIME = TFC_Time.daysInMonth;
				FloraManager manager = FloraManager.getInstance();
				FloraIndex fi = manager.findMatchingIndex(tree.getTreeType(block, meta));
				
				if(isSapling == 1 && birthTimeLeaves + 2 < TFC_Time.getTotalDays())
				{
					isSapling = 0;
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
				
				if (month < 9 && fi != null && temp >= fi.minTemp && temp < fi.maxTemp)
				{
					int t = 1;
					if (month < 3)
						t = 2;
					if(meta == 2)
					{
						//Update Trunk
						if (birthTimeWood + TRUNK_GROW_TIME < TFC_Time.getTotalDays() && height < 6 && isTrunk && worldObj.rand.nextInt(16 / t) == 0 &&
								checkBranch(xCoord, yCoord + 1, zCoord))
						{
							worldObj.setBlock(xCoord, yCoord + 1, zCoord, CWTFCBlocks.nutTreeLog, meta, 3);
							TileEntity te = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
							if (te instanceof TileNutTree)
							{
								((TileNutTree)te).setupBirth(true, height + 1, birthTimeWood + TRUNK_GROW_TIME, birthTimeLeaves);
								
								increaseBirthWood(TRUNK_GROW_TIME);
								worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
								worldObj.markBlockForUpdate(xCoord, yCoord + 1, zCoord);
							}
						}
						//Update Leaves
						if (birthTimeLeaves + 2 < TFC_Time.getTotalDays() && worldObj.rand.nextInt((int) LEAF_GROWTH_RATE) == 0 && height > 2 && worldObj.getBlock(xCoord, yCoord + 1, zCoord) != CWTFCBlocks.nutTreeLog)
						{
							int m = meta < 8 ? meta : meta - 8;
							Block bid = CWTFCBlocks.nutTreeLeaves;
							
							if (checkLeaves(xCoord + 1, yCoord, zCoord))
								worldObj.setBlock(xCoord + 1, yCoord, zCoord, bid, m, 3);
							else if (checkLeaves(xCoord - 1, yCoord, zCoord))
								worldObj.setBlock(xCoord - 1, yCoord, zCoord, bid, m, 3);
							else if (checkLeaves(xCoord, yCoord, zCoord + 1))
								worldObj.setBlock(xCoord, yCoord, zCoord + 1, bid, m, 3);
							else if (checkLeaves(xCoord, yCoord, zCoord - 1))
								worldObj.setBlock(xCoord, yCoord, zCoord - 1, bid, m, 3);
	
							this.increaseBirthLeaves(2);
							worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
								
						}
					}
					else
					{
						//Update Trunk
						if (birthTimeWood + TRUNK_GROW_TIME < TFC_Time.getTotalDays() && height < 3 && isTrunk && worldObj.rand.nextInt(16 / t) == 0 &&
								checkBranch(xCoord, yCoord + 1, zCoord))
						{
							worldObj.setBlock(xCoord, yCoord + 1, zCoord, CWTFCBlocks.nutTreeLog, meta, 3);
							TileEntity te = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
							if (te instanceof TileNutTree)
							{
								((TileNutTree)te).setupBirth(true, height + 1, birthTimeWood + TRUNK_GROW_TIME, birthTimeLeaves);
								
								increaseBirthWood(TRUNK_GROW_TIME);
								worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
								worldObj.markBlockForUpdate(xCoord, yCoord + 1, zCoord);
							}
						}
						//Update branches
						else if (birthTimeWood + BRANCH_GROW_TIME < TFC_Time.getTotalDays() && height == 2 && isTrunk && worldObj.rand.nextInt(16 / t) == 0 &&
								worldObj.getBlock(xCoord, yCoord + 1, zCoord) == CWTFCBlocks.nutTreeLog)
						{
							int r = worldObj.rand.nextInt(4);
							if (r == 0 && checkBranch(xCoord + 1, yCoord, zCoord))
								createBranch(xCoord + 1, yCoord, zCoord, meta, BRANCH_GROW_TIME);
							else if (r == 1 && checkBranch(xCoord, yCoord, zCoord - 1))
								createBranch(xCoord, yCoord, zCoord - 1, meta, BRANCH_GROW_TIME);
							else if (r == 2 && checkBranch(xCoord - 1, yCoord, zCoord))
								createBranch(xCoord - 1, yCoord, zCoord, meta, BRANCH_GROW_TIME);
							else if (r == 3 && checkBranch(xCoord, yCoord, zCoord + 1))
								createBranch(xCoord, yCoord, zCoord + 1, meta, BRANCH_GROW_TIME);
							
							increaseBirthWood(BRANCH_GROW_TIME);
							worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						}
						
						//Update Leaves
						if (birthTimeLeaves + 2 < TFC_Time.getTotalDays() && worldObj.rand.nextInt((int) LEAF_GROWTH_RATE) == 0 && worldObj.getBlock(xCoord, yCoord + 2, zCoord) != CWTFCBlocks.nutTreeLog)
						{
							int m = meta < 8 ? meta : meta - 8;
							Block bid = CWTFCBlocks.nutTreeLeaves;
							
							if (checkLeaves(xCoord, yCoord + 1, zCoord))
								worldObj.setBlock(xCoord, yCoord + 1, zCoord, bid, m, 3);
							else if (checkLeaves(xCoord + 1, yCoord, zCoord))
								worldObj.setBlock(xCoord + 1, yCoord, zCoord, bid, m, 3);
							else if (checkLeaves(xCoord - 1, yCoord, zCoord))
								worldObj.setBlock(xCoord - 1, yCoord, zCoord, bid, m, 3);
							else if (checkLeaves(xCoord, yCoord, zCoord + 1))
								worldObj.setBlock(xCoord, yCoord, zCoord + 1, bid, m, 3);
							else if (checkLeaves(xCoord, yCoord, zCoord - 1))
								worldObj.setBlock(xCoord, yCoord, zCoord - 1, bid, m, 3);
							else if (checkLeaves(xCoord + 1, yCoord, zCoord - 1)) 
								worldObj.setBlock(xCoord + 1, yCoord, zCoord - 1, bid, m, 3);
							else if (checkLeaves(xCoord + 1, yCoord, zCoord + 1))
								worldObj.setBlock(xCoord + 1, yCoord, zCoord + 1, bid, m, 3);
							else if (checkLeaves(xCoord - 1, yCoord, zCoord - 1))
								worldObj.setBlock(xCoord - 1, yCoord, zCoord - 1, bid, m, 3);
							else if (checkLeaves(xCoord - 1, yCoord, zCoord + 1))
								worldObj.setBlock(xCoord - 1, yCoord, zCoord + 1, bid, m, 3);
	
							this.increaseBirthLeaves(2);
							worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
								
						}
					}
				}
			}
		}
	}
	
	protected boolean checkLeaves(int x, int y, int z)
	{
		return worldObj.blockExists(x, y, z) && worldObj.isAirBlock(x, y, z) && BlockNutLeaves.canStay(worldObj, x, y, z);
	}
	
	protected boolean checkBranch(int x, int y, int z)
	{
		return worldObj.blockExists(x, y, z) && (worldObj.isAirBlock(x, y, z) || worldObj.getBlock(x, y, z) == CWTFCBlocks.nutTreeLeaves);
	}
	
	protected void createBranch(int x, int y, int z, int meta, long BRANCH_GROW_TIME)
	{
		worldObj.setBlock(x, y, z, CWTFCBlocks.nutTreeLog, meta, 3);
		TileEntity te = worldObj.getTileEntity(x, y, z);
		if (te instanceof TileNutTree)
		{
			((TileNutTree) te).setupBirth(false, height, birthTimeWood + BRANCH_GROW_TIME, birthTimeLeaves);
			worldObj.markBlockForUpdate(x, y, z);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		birthTimeWood = nbt.getLong("birthTime");
		birthTimeLeaves = nbt.getLong("birthTimeLeaves");
		isTrunk = nbt.getBoolean("isTrunk");
		height = nbt.getInteger("height");
		isSapling = nbt.getInteger("isSapling");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setLong("birthTime", birthTimeWood);
		nbt.setLong("birthTimeLeaves", birthTimeLeaves);
		nbt.setBoolean("isTrunk", isTrunk);
		nbt.setInteger("height", height);
		nbt.setInteger("isSapling", isSapling);
	}
	
	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		isSapling = nbt.getInteger("isSapling");
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}
	
	@Override
	public void handleDataPacket(NBTTagCompound nbt) 
	{
		isSapling = nbt.getInteger("isSapling");
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}
	
	@Override
	public void createDataNBT(NBTTagCompound nbt) 
	{
		nbt.setInteger("isSapling", isSapling);
	}
	
	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		nbt.setInteger("isSapling", isSapling);
	}
}
