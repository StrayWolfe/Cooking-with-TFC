package straywolfe.cookingwithtfc.common.block;

import java.util.List;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.tileentity.TileBowl;

public class BlockBowl extends BlockTerraContainer
{
	@SideOnly(Side.CLIENT)
	private static IIcon[] Salad;
			
	public BlockBowl()
	{
		super();
		
		setStepSound(soundTypeGlass);
		setHardness(0.5F);
		setBlockBounds(0, 0, 0, 1, 0.15F, 1);
		setBlockName("BowlCWTFC");
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote && world.getTileEntity(x, y, z) instanceof TileBowl)
		{
			TileBowl te = (TileBowl)world.getTileEntity(x, y, z);
			ItemStack equipped = player.getCurrentEquippedItem();
			
			if (!player.isSneaking())
			{
				//Get top ingredient
				if(equipped == null)
				{
					ItemStack topItem = te.getTopIngredient();
					player.setCurrentItemOrArmor(0, topItem);
					
					if(topItem != null && topItem.getItem() == TFCItems.potteryBowl)
					{
						world.removeTileEntity(x, y, z);
						world.setBlockToAir(x, y, z);
					}
				}
				//Add Ingredient to top
				else if(equipped.getItem() instanceof ItemFoodTFC && ((ItemFoodTFC)equipped.getItem()).isEdible(equipped))
				{
					ItemStack knife = null;
					boolean knifeNotNeeded = false;
					int slot = 0;
					
					if(Food.getWeight(equipped) - 5 <= 0)
						knifeNotNeeded = true;
					
					if(!knifeNotNeeded)
					{
						for(int i = 0; i < 9; i++)
						{
							ItemStack is = player.inventory.getStackInSlot(i);
							if(is != null && is.getItem() instanceof ItemKnife)
							{
								slot = i;
								knife = is;
								break;
							}
						}
					}
					
					if((knife != null && te.getSaladContents()[3] == null) || knifeNotNeeded)
					{
						ItemStack food = equipped.copy();
						
						if(Food.getDecay(equipped) > 0)
						{
							Food.setWeight(equipped, Food.getWeight(equipped) - Food.getDecay(equipped) - 5);
							Food.setDecay(equipped, 0);
						}
						else
							Food.setWeight(equipped, Food.getWeight(equipped) - 5);
						
						Food.setDecay(food, 0);
						Food.setWeight(food, 5);
						
						te.setTopIngredient(food);
						
						if(!knifeNotNeeded)
						{
							knife.setItemDamage(knife.getItemDamage() + 1);
							if(knife.getItemDamage() > knife.getMaxDamage())
								player.inventory.setInventorySlotContents(slot, null);
						}	
					}
				}
			}
			else
			{
				//Pickup completed Salad
				if(equipped == null && te.getSaladContents()[0] != null)
				{
					ItemStack salad = te.makeSalad();
					Food.setMealSkill(salad, TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_COOKING).ordinal());
					TFC_Core.getSkillStats(player).increaseSkill(Global.SKILL_COOKING, 1);
					
					player.setCurrentItemOrArmor(0, salad);
					
					world.removeTileEntity(x, y, z);
					world.setBlockToAir(x, y, z);
				}
			}
			world.markBlockForUpdate(x, y, z);
		}
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		blockIcon = registerer.registerIcon(Reference.MOD_ID + ":clay/Ceramic");
		
		Salad = new IIcon[3];
		
		Salad[0] = registerer.registerIcon(ModInfo.ModID + ":Meals/VeggySalad");
		Salad[1] = registerer.registerIcon(ModInfo.ModID + ":Meals/FruitSalad");
		Salad[2] = registerer.registerIcon(ModInfo.ModID + ":Meals/PotatoSalad");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getBowlIcon(String s)
	{
		if("VeggySalad".equals(s))
			return Salad[0];
		else if("FruitSalad".equals(s))
			return Salad[1];
		else if("PotatoSalad".equals(s))
			return Salad[2];
		else
			return null;
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
	public int getRenderType()
	{
		return CWTFCBlocks.bowlRenderID;
	}	
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileBowl();
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.isRemote && world.isAirBlock(x, y - 1, z))
		{
			eject(world, x, y, z);
			world.setBlockToAir(x, y, z);
			return;
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
		if (world.getTileEntity(x, y, z) instanceof TileBowl)
		{			
			((TileBowl)world.getTileEntity(x, y, z)).ejectItem();
    		world.removeTileEntity(x, y, z);
        }
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(tileentity != null && tileentity instanceof TileBowl)
		{
			TileBowl te = (TileBowl)tileentity;
			float xCoord = te.getBowlCoord(0);
			float zCoord = te.getBowlCoord(1);
			
			if(xCoord != -1 && zCoord != -1)
			{
				float minX = xCoord - 0.13F;
				float maxX = xCoord + 0.13F;
				float minY = 0.0F;
				float maxY = 0.12F;
				float minZ = zCoord - 0.13F;
				float maxZ = zCoord + 0.13F;
	
				setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
			}
			else
				setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		}
    }
    
    @SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
	{
    	TileEntity tileentity = world.getTileEntity(x, y, z);
		if(tileentity != null && tileentity instanceof TileBowl)
		{
			TileBowl te = (TileBowl)tileentity;
			float xCoord = te.getBowlCoord(0);
			float zCoord = te.getBowlCoord(1);
			
			if(xCoord != -1 && zCoord != -1)
			{
				float minX = xCoord - 0.13F;
				float maxX = xCoord + 0.13F;
				float minY = 0.0F;
				float maxY = 0.12F;
				float minZ = zCoord - 0.13F;
				float maxZ = zCoord + 0.13F;
				
				setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
				super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
				setBlockBoundsBasedOnState(world, x, y, z);
			}
		}
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
    {
        return true;
    }
}
