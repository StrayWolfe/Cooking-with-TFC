package straywolfe.cookingwithtfc.common.block;

import java.util.List;

import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.common.item.ItemBread;
import straywolfe.cookingwithtfc.common.item.ItemTFCFoodTransform;
import straywolfe.cookingwithtfc.common.tileentity.TileSandwich;

public class BlockSandwich extends BlockTerraContainer
{
	public BlockSandwich()
	{
		super(Material.cake);
		setHardness(1F);
		setBlockName("SandwichsBlock");
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote && world.getTileEntity(x, y, z) instanceof TileSandwich)
		{
			TileSandwich te = (TileSandwich)world.getTileEntity(x, y, z);
			ItemStack equipped = player.getCurrentEquippedItem();
			
			if(!player.isSneaking())
			{				
				//Get top ingredient
				if(equipped == null)
				{
					player.setCurrentItemOrArmor(0, te.getTopSandwichItem());
					
					if(te.getSandwichContents()[0] == null)
					{
						world.removeTileEntity(x, y, z);
						world.setBlockToAir(x, y, z);
					}
				}
				//Add Ingredient to top
				else if(equipped.getItem() instanceof ItemTFCFoodTransform && ((ItemTFCFoodTransform)equipped.getItem()).edible)
				{
					ItemStack knife = null;
					boolean knifeNotNeeded = false;
					int slot = 0;
					
					if(equipped.getItem() instanceof ItemBread)
					{
						if(Food.getWeight(equipped) - 1 <= 0)
							knifeNotNeeded = true;
					}
					else if(Food.getWeight(equipped) - 2 <= 0)
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
					
					if((knife != null && te.getSandwichContents()[te.getTopToast()] == null) || knifeNotNeeded)
					{						
						if(te.getSandwichContents()[4] != null && !(equipped.getItem() instanceof ItemBread))
							return true;
						
						ItemStack food = equipped.copy();
						
						if(Food.getDecay(equipped) > 0)
						{
							if(equipped.getItem() instanceof ItemBread)
								Food.setWeight(equipped, Food.getWeight(equipped) - Food.getDecay(equipped) - 1);
							else
								Food.setWeight(equipped, Food.getWeight(equipped) - Food.getDecay(equipped) - 2);
							
							Food.setDecay(equipped, 0);
						}
						else
						{
							if(equipped.getItem() instanceof ItemBread)
								Food.setWeight(equipped, Food.getWeight(equipped) - 1);
							else
								Food.setWeight(equipped, Food.getWeight(equipped) - 2);
						}
						
						if(equipped.getItem() instanceof ItemBread)
							Food.setWeight(food, 1);
						else
							Food.setWeight(food, 2);
						
						te.setTopSandwichItem(food);
						
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
				//Pickup completed sandwich
				if(equipped == null && te.getSandwichContents()[te.getTopToast()] != null)
				{
					ItemStack sandwich = te.makeSandwich();
					Food.setMealSkill(sandwich, TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_COOKING).ordinal());
					TFC_Core.getSkillStats(player).increaseSkill(Global.SKILL_COOKING, 1);
					
					player.setCurrentItemOrArmor(0, sandwich);
					
					world.removeTileEntity(x, y, z);
					world.setBlockToAir(x, y, z);
				}
			}
			
			world.markBlockForUpdate(x, y, z);
		}
		
		return true;
	}
	
	public void eject(World world, int x, int y, int z)
	{
		if (world.getTileEntity(x, y, z) instanceof TileSandwich)
		{
			TileSandwich te = (TileSandwich)world.getTileEntity(x, y, z);
			te.ejectItem();
			world.removeTileEntity(x, y, z);
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
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileSandwich();
	}
	
	@Override
	public void registerBlockIcons(IIconRegister registerer) {}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return TFC_Textures.invisibleTexture;
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
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.isRemote)
		{
			if(!(world.getBlock(x, y - 1, z) instanceof BlockPrepTable || world.getBlock(x, y - 1, z) instanceof BlockPrepTable2))
			{
				eject(world, x, y, z);
				world.setBlockToAir(x, y, z);
			}
		}
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
		TileSandwich te = (TileSandwich)world.getTileEntity(x, y, z);
		float xCoord = te.getSandwichCoord(0);
		float zCoord = te.getSandwichCoord(1);
		
		if(xCoord != -1 && zCoord != -1)
		{
			float minX = xCoord;
			float maxX = xCoord + 0.4F;
			float minY = 0.0F;
			float maxY = 0.215F;
			float minZ = zCoord;
			float maxZ = zCoord + 0.4F;
			
			setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
		else
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
    }
    
    @SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
	{
    	TileSandwich te = (TileSandwich)world.getTileEntity(x, y, z);
		float xCoord = te.getSandwichCoord(0);
		float zCoord = te.getSandwichCoord(1);
		
		if(xCoord != -1 && zCoord != -1)
		{
			float minX = xCoord;
			float maxX = xCoord + 0.4F;
			float minY = 0.0F;
			float maxY = 0.215F;
			float minZ = zCoord;
			float maxZ = zCoord + 0.4F;
			
			setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
			super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
			setBlockBoundsBasedOnState(world, x, y, z);
		}
	}
}
