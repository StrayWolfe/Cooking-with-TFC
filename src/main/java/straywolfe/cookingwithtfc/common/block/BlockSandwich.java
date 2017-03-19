package straywolfe.cookingwithtfc.common.block;

import java.util.List;

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
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.core.helper.Helper;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.tileentity.TileSandwich;

public class BlockSandwich extends BlockTerraContainer
{
	@SideOnly(Side.CLIENT)
	private IIcon[] ToastTop;
	@SideOnly(Side.CLIENT)
	private IIcon[] ToastSide;
	@SideOnly(Side.CLIENT)
	private IIcon[] FoodGroups;
	
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
				else if(equipped.getItem() instanceof ItemFoodTFC && ((ItemFoodTFC)equipped.getItem()).isEdible(equipped))
				{
					ItemStack knife = null;
					boolean knifeNotNeeded = false;
					int slot = 0;
					
					if(Helper.isOre("itemBread", equipped))
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
						if(te.getSandwichContents()[4] != null && !(Helper.isOre("itemBread", equipped)))
							return true;
						
						ItemStack food = equipped.copy();
						
						if(Food.getDecay(equipped) > 0)
						{
							if(Helper.isOre("itemBread", equipped))
								Food.setWeight(equipped, Food.getWeight(equipped) - Food.getDecay(equipped) - 1);
							else
								Food.setWeight(equipped, Food.getWeight(equipped) - Food.getDecay(equipped) - 2);
							
							Food.setDecay(equipped, 0);
						}
						else
						{
							if(Helper.isOre("itemBread", equipped))
								Food.setWeight(equipped, Food.getWeight(equipped) - 1);
							else
								Food.setWeight(equipped, Food.getWeight(equipped) - 2);
						}
						
						Food.setDecay(food, 0);
						
						if(Helper.isOre("itemBread", equipped))
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
			te.markDirty();
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
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer) 
	{
		ToastTop = new IIcon[6];
		ToastSide = new IIcon[6];
		FoodGroups = new IIcon[4];
		
		ToastTop[0] = registerer.registerIcon(ModInfo.ModID + ":Meals/Barley Toast Top");
		ToastTop[1] = registerer.registerIcon(ModInfo.ModID + ":Meals/Corn Toast Top");
		ToastTop[2] = registerer.registerIcon(ModInfo.ModID + ":Meals/Oat Toast Top");
		ToastTop[3] = registerer.registerIcon(ModInfo.ModID + ":Meals/Rice Toast Top");
		ToastTop[4] = registerer.registerIcon(ModInfo.ModID + ":Meals/Rye Toast Top");
		ToastTop[5] = registerer.registerIcon(ModInfo.ModID + ":Meals/Wheat Toast Top");
		
		ToastSide[0] = registerer.registerIcon(ModInfo.ModID + ":Meals/Barley Toast Side");
		ToastSide[1] = registerer.registerIcon(ModInfo.ModID + ":Meals/Corn Toast Side");
		ToastSide[2] = registerer.registerIcon(ModInfo.ModID + ":Meals/Oat Toast Side");
		ToastSide[3] = registerer.registerIcon(ModInfo.ModID + ":Meals/Rice Toast Side");
		ToastSide[4] = registerer.registerIcon(ModInfo.ModID + ":Meals/Rye Toast Side");
		ToastSide[5] = registerer.registerIcon(ModInfo.ModID + ":Meals/Wheat Toast Side");
		
		FoodGroups[0] = registerer.registerIcon(ModInfo.ModID + ":Meals/Dairy");
		FoodGroups[1] = registerer.registerIcon(ModInfo.ModID + ":Meals/Fruit");
		FoodGroups[2] = registerer.registerIcon(ModInfo.ModID + ":Meals/Protein");
		FoodGroups[3] = registerer.registerIcon(ModInfo.ModID + ":Meals/Vegetable");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getSandwichIcon(String s)
	{
		if("BarleyTop".equals(s))
			return ToastTop[0];
		else if("CornTop".equals(s))
			return ToastTop[1];
		else if("OatTop".equals(s))
			return ToastTop[2];
		else if("RiceTop".equals(s))
			return ToastTop[3];
		else if("RyeTop".equals(s))
			return ToastTop[4];
		else if("WheatTop".equals(s))
			return ToastTop[5];
		else if("BarleySide".equals(s))
			return ToastSide[0];
		else if("CornSide".equals(s))
			return ToastSide[1];
		else if("OatSide".equals(s))
			return ToastSide[2];
		else if("RiceSide".equals(s))
			return ToastSide[3];
		else if("RyeSide".equals(s))
			return ToastSide[4];
		else if("WheatSide".equals(s))
			return ToastSide[5];
		else if("Dairy".equals(s))
			return FoodGroups[0];
		else if("Fruit".equals(s))
			return FoodGroups[1];
		else if("Protein".equals(s))
			return FoodGroups[2];
		else if("Vegetable".equals(s))
			return FoodGroups[3];
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
		return CWTFCBlocks.sandwichRenderID;
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
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(tileentity != null && tileentity instanceof TileSandwich)
		{
			TileSandwich te = (TileSandwich)tileentity;
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
				setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		}
    }
    
    @SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
	{
    	TileEntity tileentity = world.getTileEntity(x, y, z);
		if(tileentity != null && tileentity instanceof TileSandwich)
		{
			TileSandwich te = (TileSandwich)tileentity;
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
    
    @Override
	@SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
    {
    	Block block = world.getBlock(x, y, z);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		
		if (block != null && block == this && tileentity != null && tileentity instanceof TileSandwich)
		{
			TileSandwich te = (TileSandwich)tileentity;
			Item toast = (te.getSandwichContents()[0]).getItem();
			byte b0 = 2;

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
                        
                        if(toast == TFCItems.barleyBread)
                        	digging.setParticleIcon(ToastTop[0]);
        				else if(toast == TFCItems.cornBread)
        					digging.setParticleIcon(ToastTop[1]);
        				else if(toast == TFCItems.oatBread)
        					digging.setParticleIcon(ToastTop[2]);
        				else if(toast == TFCItems.riceBread)
        					digging.setParticleIcon(ToastTop[3]);
        				else if(toast == TFCItems.ryeBread)
        					digging.setParticleIcon(ToastTop[4]);
        				else
        					digging.setParticleIcon(ToastTop[5]);
                        
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
}
