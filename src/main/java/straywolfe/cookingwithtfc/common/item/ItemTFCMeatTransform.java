package straywolfe.cookingwithtfc.common.item;

import java.util.List;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Render.Item.FoodItemRenderer;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.common.block.BlockPrepTable;
import straywolfe.cookingwithtfc.common.block.BlockPrepTable2;
import straywolfe.cookingwithtfc.common.core.CWTFC_Core;
import straywolfe.cookingwithtfc.common.lib.Settings;
import straywolfe.cookingwithtfc.common.tileentity.TileMeat;

public class ItemTFCMeatTransform extends ItemTFCFoodTransform
{
	private float consumeSize = 5F;
		
	public ItemTFCMeatTransform(EnumFoodGroup fg, int sw, int so, int sa, int bi, int um, boolean edible, boolean usable, float size) 
	{
		super(fg, sw, so, sa, bi, um, size, 2.0F, edible, usable);
		
		consumeSize = size;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		String name = getUnlocalizedName().replace(" Raw", "");

		itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + name.replace("item.", ""));
		
		if(hasCookedIcon)
		{			
			cookedIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + name.replace("item.", ""));
		}
		
		MinecraftForgeClient.registerItemRenderer(this, new FoodItemRenderer());
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		if(Settings.diminishingReturns)
			return CWTFC_Core.processRightClick(is, player, consumeSize, isEdible(is));
		else
		{
			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
			if (foodstats.needFood() && this.isEdible(is))
				player.setItemInUse(is, 32);

			return is;
		}
	}
	
	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		return CWTFC_Core.processEating(is, world, player, consumeSize, false);
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote && !edible && side == 1 && !player.isSneaking() && world.isAirBlock(x, y + 1, z) && !Food.isSalted(itemstack))
		{
			Block block = world.getBlock(x, y, z);
			if(block != null && (block instanceof BlockPrepTable || block instanceof BlockPrepTable2))
			{
				if(world.setBlock(x, y + 1, z, CWTFCBlocks.meatCWTFC))
				{
					float size = 0.25F;
					float xMin = hitX - size;
					float xMax = hitX + size;
					float zMin = hitZ - size;
					float zMax = hitZ + size;
					TileMeat te = (TileMeat)world.getTileEntity(x, y + 1, z);
					
					if(!(xMin >= 0 && xMax <= 1 && zMin >= 0 && zMax <= 1))
					{
						if(xMin < 0)
							xMin = 0;
						
						if(xMax > 1)
							xMin = 1 - (size * 2);
						
						if(zMin < 0)
							zMin = 0;
						
						if(zMax > 1)
							zMin = 1 - (size * 2);
					}
					
					te.setplacedMeat(player.getCurrentEquippedItem());
					te.setMeatCoord(xMin, 0);
					te.setMeatCoord(zMin, 1);
					player.setCurrentItemOrArmor(0, null);
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{		
		ItemTerra.addSizeInformation(is, arraylist);
		arraylist.add(ItemFoodTFC.getFoodGroupName(getFoodGroup()));
		

		if (is.hasTagCompound())
		{
			ItemFoodTFC.addFoodHeatInformation(is, arraylist);
			addFoodInformation(is, player, arraylist);
			
			if(Settings.diminishingReturns)
			{
				if(isEdible(is))
					CWTFC_Core.getFoodUse(is, player, arraylist);
				else
					arraylist.add(EnumChatFormatting.DARK_GRAY + "Not currently edible");
			}
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		StringBuilder name = new StringBuilder();
		if (is.hasTagCompound()) // Circular reference avoidance
		{
			if(Food.isPickled(is))
				name.append(TFC_Core.translate("word.pickled")).append(' ');
			else if(Food.isBrined(is) && !Food.isDried(is))
				name.append(TFC_Core.translate("word.brined")).append(' ');

			if(Food.isSalted(is))
				name.append(TFC_Core.translate("word.salted")).append(' ');
			
			if(!Food.isCooked(is) && Food.isSmoked(is))
				name.append(TFC_Core.translate("word.smoked")).append(' ');

			if(Food.isDried(is) && !Food.isCooked(is))
				name.append(TFC_Core.translate("word.dried")).append(' ');
			
			if(Food.isInfused(is))
				name.append(TFC_Core.translate(Food.getInfusion(is) + ".name")).append(' ');
		}

		return name.append(TFC_Core.translate(getUnlocalizedName(is) + ".name")).append(getCookedLevelString(is)).toString();
	}
	
	@Override
	protected String getCookedLevelString(ItemStack is)
	{
		String s = "";
		if(Food.isCooked(is))
		{
			s+= " (";
			int cookedLevel = ((int)Food.getCooked(is)-600)/120;
			switch(cookedLevel)
			{
			case 0: s+=TFC_Core.translate("food.cooked.rare");break;
			case 1: s+=TFC_Core.translate("food.cooked.medrare");break;
			case 2: s+=TFC_Core.translate("food.cooked.med");break;
			case 3: s+=TFC_Core.translate("food.cooked.medwell");break;
			default: s+=TFC_Core.translate("food.cooked.well");break;
			}
			s+= ")";
		}
		return s;
	}
}