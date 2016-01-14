package com.JAWolfe.cookingwithtfc.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.JAWolfe.cookingwithtfc.init.CWTFCBlocks;
import com.JAWolfe.cookingwithtfc.tileentities.TENestBoxCWTFC;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.AI.EntityAIFindNest;
import com.bioxx.tfc.api.Entities.IAnimal.GenderEnum;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public class AIFindNest extends EntityAIFindNest
{
	private EntityCreature chicken;
	private int movement;
	private final double field_75404_b;
	private int maxSittingTicks;
	private Map<String, Long> failureDepressionMap;
	private int currentTick;
	private double compoundDistance;
	private int lastCheckedTick;
	private boolean end;
	
	/** X Coordinate of a nearby sitable block */
	private int sitableBlockX = -1;

	/** Y Coordinate of a nearby sitable block */
	private int sitableBlockY =-1;

	/** Z Coordinate of a nearby sitable block */
	private int sitableBlockZ =-1;

	public AIFindNest(EntityAnimal eAnimal, double par2) 
	{
		super(eAnimal, par2);
		this.chicken = eAnimal;
		this.failureDepressionMap = new HashMap<String,Long>();
		this.field_75404_b = par2;
		this.setMutexBits(5);
	}

	@Override
	public boolean shouldExecute()
	{
		if(chicken instanceof EntityTransformChickenTFC && !(chicken instanceof EntityTransformPheasant))
		{
			return ((EntityTransformChickenTFC) chicken).isAdult() &&((EntityTransformChickenTFC) chicken).getFamiliarity() >= 15 &&
					chicken.worldObj.getBlock((int)chicken.posX, (int)chicken.posY,(int)chicken.posZ) != CWTFCBlocks.nestBoxCWTFC &&
					chicken.worldObj.getBlock((int)chicken.posX, (int)chicken.posY - 1,(int)chicken.posZ) != CWTFCBlocks.nestBoxCWTFC &&
					this.getNearbySitableBlockDistance() &&
					((EntityTransformChickenTFC) chicken).getGender() == GenderEnum.FEMALE;
		}
		return false;
	}
	
	@Override
	public void startExecuting()
	{
		this.chicken.getNavigator().tryMoveToXYZ(this.sitableBlockX + 0.5D, this.sitableBlockY + 1, this.sitableBlockZ + 0.5D, this.field_75404_b);
		this.currentTick = 0;
		this.movement = 0;
		this.compoundDistance = 0;
		this.lastCheckedTick = 0;
		this.end = false;
		this.maxSittingTicks = this.chicken.getRNG().nextInt(this.chicken.getRNG().nextInt(1200) + 1200) + 1200;
	}
	
	@Override
	public boolean continueExecuting()
	{
		if (this.chicken.getDistanceSq(sitableBlockX + 0.5, sitableBlockY, sitableBlockZ + 0.5) < 0.2)
			this.chicken.getNavigator().clearPathEntity();

		if(this.end)
		{
			this.end = false;
			return end;
		}
		return this.currentTick <= this.maxSittingTicks && this.movement <= 60 && this.isSittableBlock(this.chicken.worldObj, this.sitableBlockX, this.sitableBlockY, this.sitableBlockZ);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateTask()
	{
		++this.currentTick;
		
		if (this.chicken.getDistanceSq(this.sitableBlockX, this.sitableBlockY + 1, this.sitableBlockZ) > 1.0D)
		{
			this.chicken.getNavigator().tryMoveToXYZ(this.sitableBlockX + 0.5D, this.sitableBlockY + 1, this.sitableBlockZ + 0.5D, this.field_75404_b);
			++this.movement;
			this.compoundDistance += this.chicken.getDistance(this.chicken.lastTickPosX, this.chicken.lastTickPosY, this.chicken.lastTickPosZ);
			if(this.currentTick - 40 > this.lastCheckedTick)
			{
				ArrayList<EntityTransformChickenTFC> crowd = (ArrayList<EntityTransformChickenTFC>) chicken.worldObj.getEntitiesWithinAABB(EntityTransformChickenTFC.class, chicken.boundingBox.expand(24, 2, 24));
				ArrayList<EntityTransformChickenTFC> invalid = new ArrayList<EntityTransformChickenTFC>();
				for(EntityTransformChickenTFC chicken : crowd){
					if(chicken.getGender().equals(GenderEnum.MALE) || chicken.isChild()){
						invalid.add(chicken);
					}
				}
				crowd.removeAll(invalid);
				crowd.remove(chicken);
				if(this.compoundDistance < 0.5 || crowd.size() >= 10)
				{
					failureDepressionMap.put((this.sitableBlockX + "," + this.sitableBlockY + "," + this.sitableBlockZ), TFC_Time.getTotalTicks() + 24000);
					this.end = true;
				}
				else
				{
					this.lastCheckedTick = this.currentTick;
				}
			}
		}
		else
		{
			--this.movement;
		}
	}
	
	protected boolean getNearbySitableBlockDistance()
	{
		int i = (int)this.chicken.posY;
		double d0 = 2.147483647E9D;

		for (int j = (int) this.chicken.posX - 16; j < this.chicken.posX + 16.0D; ++j)
		{
			for (int k = (int) this.chicken.posZ - 16; k < this.chicken.posZ + 16.0D; ++k)
			{
				for(int l = i; l < i+4; l++)
				{
					if (this.isSittableBlock(this.chicken.worldObj, j, l, k) && this.chicken.worldObj.isAirBlock(j, l + 1, k))
					{
						double d1 = this.chicken.getDistanceSq(j, l, k);

						if (d1 < d0)
						{
							this.sitableBlockX = j;
							this.sitableBlockY = l;
							this.sitableBlockZ = k;
							d0 = d1;
						}
					}
				}
			}
		}
		return d0 < 2.147483647E9D;
	}
	
	/**
	 * Determines whether the creature wants to sit on the block at given coordinate
	 */
	protected boolean isSittableBlock(World world, int x, int y, int z)
	{
		if(failureDepressionMap.containsKey((x + "," + y + "," + z)))
		{
			long time = failureDepressionMap.get((x + "," + y + "," + z));
			if(time > TFC_Time.getTotalTicks())
				return false;
			else
				failureDepressionMap.remove(new int[]{x, y, z});
		}

		if (world.getBlock(x, y, z) == CWTFCBlocks.nestBoxCWTFC)
		{
			TENestBoxCWTFC tileentitynest = (TENestBoxCWTFC) world.getTileEntity(x, y, z);

			if (!tileentitynest.hasBird() || tileentitynest.getBird() == chicken)
				return true;
		}

		return false;
	}
}
