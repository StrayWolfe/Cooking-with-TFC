package straywolfe.cookingwithtfc.common.block;

import java.util.Random;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Items.ItemCoal;
import com.bioxx.tfc.Items.ItemLogs;
import com.bioxx.tfc.Items.Tools.ItemFirestarter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.recipe.OvenManager;
import straywolfe.cookingwithtfc.common.item.ItemClayOvenWall;
import straywolfe.cookingwithtfc.common.lib.ClayOvenStages;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.tileentity.TileClayOven;

public class BlockClayOven extends BlockTerraContainer
{
	@SideOnly(Side.CLIENT)
	private static IIcon[] ClayOven;
	@SideOnly(Side.CLIENT)
	private static IIcon[] ClayOvenChimney;
	@SideOnly(Side.CLIENT)
	private static IIcon OvenInterior;
	@SideOnly(Side.CLIENT)
	private static IIcon OvenFireOn;
	@SideOnly(Side.CLIENT)
	private static IIcon OvenFireOff;
	
	public BlockClayOven()
	{
		super(Material.rock);
		setHardness(1F);
		setBlockName("ClayOven");
		setBlockBounds(0, 0, 0, 1, 0.9f, 1);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
			TileClayOven te = (TileClayOven)world.getTileEntity(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			
			if(equippedItem != null && equippedItem.getItem() instanceof ItemClayOvenWall)
			{
				int buildStage = te.getBuildStage();
				
				if(buildStage > ClayOvenStages.PLATFORM && buildStage < ClayOvenStages.CHIMNEY && te.getCuringStage() < 3)
				{
					te.setBuildStage(buildStage + 1);
					
					if(!entityplayer.capabilities.isCreativeMode)
						equippedItem.stackSize--;
				}
			}
			else if(equippedItem != null && TFC_Core.isSand(Block.getBlockFromItem(equippedItem.getItem())))
			{
				int buildStage = te.getBuildStage();
				if(buildStage == ClayOvenStages.PLATFORM)
				{
					if(te.getStackInSlot(6) == null)
					{
						te.setInventorySlotContents(6, new ItemStack(equippedItem.getItem(), 1, equippedItem.getItemDamage()));
						
						if(!entityplayer.capabilities.isCreativeMode)
							equippedItem.stackSize--;
					}
					te.setBuildStage(buildStage + 1);
				}
			}
			else if(isValidSide(meta, side) && hitY <= 0.5)
			{
				int buildStage = te.getBuildStage();
				
				if(equippedItem == null)
				{
					if (entityplayer.isSneaking())
					{
						if(buildStage == ClayOvenStages.CHIMNEY && te.getCuringStage() >= 3)
						{
							entityplayer.setCurrentItemOrArmor(0, te.getStackInSlot(6).copy());
							te.setInventorySlotContents(6, null);
							te.setBuildStage(buildStage + 1);
							te.setCuringTime(0);
						}
						else if(buildStage >= ClayOvenStages.INTERIOR)
						{
							for(int i = 0; i < 4; i++)
							{
								if(te.getStackInSlot(i) != null)
								{
									entityplayer.setCurrentItemOrArmor(0, te.getStackInSlot(i).copy());
									te.setInventorySlotContents(i, null);
									break;
								}
							}
						}
					}
					else
					{
						if(buildStage == ClayOvenStages.CURED)
						{
							int slot = getSlotFromHit(meta, hitX, hitZ);
							
							if(te.getStackInSlot(slot) != null)
							{
								entityplayer.setCurrentItemOrArmor(0, te.getStackInSlot(slot).copy());
								te.setInventorySlotContents(slot, null);
							}
						}
					}
				}
				else
				{
					Item item = equippedItem.getItem();
					if((item instanceof ItemFirestarter || item instanceof ItemFlintAndSteel) && buildStage >= ClayOvenStages.INTERIOR)
					{
						if(te.getFireState() == false && te.hasFuel())
						{
							if (item instanceof ItemFlintAndSteel)
							{
								Random rand = new Random();
								world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
							}
							
							if(buildStage != ClayOvenStages.CURED)
								te.setCuringTime(TFC_Time.getTotalTicks());
								
							te.fireTemp = 300;
							equippedItem.damageItem(1, entityplayer);
							te.setFireState(true);
							
							int durability = te.getDurability();
							if(durability == 0)
							{
								Random rand = new Random();
								if(rand.nextInt(99) == 0)
								{
									world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, TFC_Sounds.CERAMICBREAK, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
									world.setBlockToAir(x, y, z);
									eject(world, x, y, z);
								}
							}
							else
								te.setDurability(durability - 1);
						}
					}
					else if((item instanceof ItemLogs || item instanceof ItemCoal) && buildStage >= ClayOvenStages.INTERIOR)
					{
						if(te.getStackInSlot(0) == null)
						{
							te.setInventorySlotContents(0, new ItemStack(item, 1, equippedItem.getItemDamage()));
							
							if(!entityplayer.capabilities.isCreativeMode)
								equippedItem.stackSize--;
						}
					}
					else if(OvenManager.getInstance().findMatchingRecipe(equippedItem) != null && buildStage == ClayOvenStages.CURED)
					{
						ItemStack food = equippedItem.copy();
						
						int slot = getSlotFromHit(meta, hitX, hitZ);
						
						if(te.getStackInSlot(slot) == null)
						{
							te.setInventorySlotContents(slot, food);
							
							if(!entityplayer.capabilities.isCreativeMode)
								equippedItem.stackSize--;
						}
					}
				}
			}
			
			world.markBlockForUpdate(x, y, z);
		}
		return true;
	}
	
	public int getSlotFromHit(int meta, float hitX, float hitZ)
	{
		if(meta == 0)
		{
			if(hitX < 0.5)
				return 4;
			else
				return 5;
		}
		else if( meta == 1)
		{
			if(hitZ < 0.5)
				return 4;
			else
				return 5;
		}
		else if(meta == 2)
		{
			if(hitX < 0.5)
				return 5;
			else
				return 4;
		}
		else if(meta == 3)
		{
			if(hitZ < 0.5)
				return 5;
			else
				return 4;
		}
		
		return 4;
	}
	
	public boolean isValidSide(int meta, int side)
	{
		if(meta == 0 && side == 2)
			return true;
		else if(meta == 1 && side == 5)
			return true;
		else if(meta == 2 && side == 3)
			return true;
		else if(meta == 3 && side == 4)
			return true;
		
		return false;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (!(TFC_Core.isStoneIgEx(world.getBlock(x, y - 1, z)) || TFC_Core.isStoneIgIn(world.getBlock(x, y - 1, z))))
		{
			world.setBlockToAir(x, y, z);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		blockIcon = iconRegisterer.registerIcon(ModInfo.ModID + ":ClayOvenIcon");
		
		ClayOven = new IIcon[5];
		ClayOvenChimney = new IIcon[5];
		
		for(int i = 0; i < 5; i++)
		{
			ClayOven[i] = iconRegisterer.registerIcon(ModInfo.ModID + ":ClayOvenWall_" + (i + 1));
			ClayOvenChimney[i] = iconRegisterer.registerIcon(ModInfo.ModID + ":ClayOven_Chimney_" + (i + 1));
		}
		
		OvenInterior = iconRegisterer.registerIcon(ModInfo.ModID + ":ClayOven_Interior");
		OvenFireOn = iconRegisterer.registerIcon(Reference.MOD_ID + ":devices/Forge On");
		OvenFireOff = iconRegisterer.registerIcon(Reference.MOD_ID + ":devices/Forge Off");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getOvenIcon(String s)
	{
		if("ClayOven1".equals(s))
			return ClayOven[0];
		else if("ClayOven2".equals(s))
			return ClayOven[1];
		else if("ClayOven3".equals(s))
			return ClayOven[2];
		else if("ClayOven4".equals(s))
			return ClayOven[3];
		else if("ClayOven5".equals(s))
			return ClayOven[4];
		else if("ClayOvenChimney1".equals(s))
			return ClayOvenChimney[0];
		else if("ClayOvenChimney2".equals(s))
			return ClayOvenChimney[1];
		else if("ClayOvenChimney3".equals(s))
			return ClayOvenChimney[2];
		else if("ClayOvenChimney4".equals(s))
			return ClayOvenChimney[3];
		else if("ClayOvenChimney5".equals(s))
			return ClayOvenChimney[4];
		else if("OvenFireOn".equals(s))
			return OvenFireOn;
		else if("OvenFireOff".equals(s))
			return OvenFireOff;
		else if("OvenInterior".equals(s))
			return OvenInterior;
		else
			return null;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof TileClayOven)
		{
			int meta = world.getBlockMetadata(x, y, z);
			if (((TileClayOven)te).getFireState())
			{
				if (rand.nextInt(24) == 0)
					world.playSoundEffect(x, y, z, "fire.fire", 0.4F + (rand.nextFloat() / 2), 0.7F + rand.nextFloat());
	
				float xLocFL = x + 0.5F;
				float xLocFR = x + 0.5F;
				float zLocFL = z + 0.5F;
				float zLocFR = z + 0.5F;
				float xLocS = x + 0.5F;
				float zLocS = z + 0.5F;
				float yLocF = y + rand.nextFloat() * 0.25F;
				float yLocS = y + 0.8F;
				float randLL = rand.nextFloat() * 0.25F;
				float randLR = rand.nextFloat() * 0.25F;
				float randDL = rand.nextFloat() * 0.4F;
				float randDR = rand.nextFloat() * 0.4F;
				
				if(meta == 0)
				{
					zLocS -= 0.05F;
					zLocFL += randDL;
					zLocFR += randDR;
					xLocFL += randLL;
					xLocFR -= randLR;
				}
				if(meta == 1)
				{
					xLocS += 0.05F;
					xLocFL -= randDL;
					xLocFR -= randDR;
					zLocFL -= randLL;
					zLocFR += randLR;
				}
				if(meta == 2)
				{
					zLocS += 0.05F;
					zLocFL -= randDL;
					zLocFR -= randDR;
					xLocFL -= randLL;
					xLocFR += randLR;
				}
				if(meta == 3)
				{
					xLocS -= 0.05F;
					xLocFL += randDL;
					xLocFR += randDR;
					zLocFL += randLL;
					zLocFR -= randLR;
				}
				
				world.spawnParticle("flame", xLocFL, yLocF,  zLocFL, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", xLocFR , yLocF, zLocFR, 0.0D, 0.0D, 0.0D);
				
				world.spawnParticle("smoke", xLocS, yLocS,  zLocS, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("smoke", xLocS, yLocS, zLocS, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int l)
	{
		eject(world, x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		eject(world, x, y, z);
	}
	
	public void eject(World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileClayOven)
		{
			((TileClayOven)te).ejectContents();
			world.removeTileEntity(x, y, z);
		}
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(tileentity != null && tileentity instanceof TileClayOven)
		{
			boolean fireState = ((TileClayOven)tileentity).getFireState();
			if(fireState)
				return 7;
			else
				return 0;
		}
		
		return 0;
	}
	
	@Override
	public int getRenderType()
	{
	    return CWTFCBlocks.clayOvenRenderID;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
    {
		Block block = world.getBlock(x, y, z);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		
		if (block != null && block == this && tileentity != null && tileentity instanceof TileClayOven)
        {
			TileClayOven te = (TileClayOven)tileentity;
			int buildStage = te.getBuildStage();
			int curingStage = te.getCuringStage();
            byte b0 = 4;

            for (int i1 = 0; i1 < b0; ++i1)
            {
                for (int j1 = 0; j1 < b0; ++j1)
                {
                    for (int k1 = 0; k1 < b0; ++k1)
                    {
                        double d0 = (double)x + ((double)i1 + 0.5D) / (double)b0;
                        double d1 = (double)y + ((double)j1 + 0.5D) / (double)b0;
                        double d2 = (double)z + ((double)k1 + 0.5D) / (double)b0;
                        
                        EntityDiggingFX digging = new EntityDiggingFX(world, d0, d1, d2, d0 - (double)x - 0.5D, d1 - (double)y - 0.5D, d2 - (double)z - 0.5D, block, meta);
                        digging.applyColourMultiplier(x, y, z);
                        
                        if(buildStage <= ClayOvenStages.CHIMNEY && curingStage == 2)
                        	digging.setParticleIcon(ClayOven[1]);
        				else if(buildStage <= ClayOvenStages.CHIMNEY && curingStage >= 3)
        					digging.setParticleIcon(ClayOven[2]);
        				else if(buildStage == ClayOvenStages.INTERIOR && (curingStage == 1 || curingStage == 4))
        					digging.setParticleIcon(ClayOven[2]);
        				else if(buildStage == ClayOvenStages.INTERIOR && curingStage == 2)
        					digging.setParticleIcon(ClayOven[3]);
        				else if(buildStage == ClayOvenStages.CURED)
        					digging.setParticleIcon(ClayOven[4]);
        				else
        					digging.setParticleIcon(ClayOven[0]);
                        
                        effectRenderer.addEffect(digging);
                    }
                }
            }
        }
		
        return true;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
    {
        return true;
    }
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileClayOven();
	}
	
	@Override
	public int quantityDropped(Random rand)
	{
		return 0;
	}
}
