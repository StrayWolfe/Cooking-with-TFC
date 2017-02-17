package straywolfe.cookingwithtfc.common.item;

import java.util.List;
import java.util.Random;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.api.managers.CWTFCCropIndex;
import straywolfe.cookingwithtfc.api.managers.CropManager;
import straywolfe.cookingwithtfc.common.lib.ModInfo;
import straywolfe.cookingwithtfc.common.tileentity.TileCrop;

public class ItemCustomSeeds extends ItemTerra
{
	private int cropId;

	public ItemCustomSeeds(int cropId)
	{
		super();
		this.cropId = cropId;
		setWeight(EnumWeight.LIGHT);
		setSize(EnumSize.TINY);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote && side == 1 && player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack))
		{
			Block soil = world.getBlock(x, y, z);
			Random rand = new Random();
			if ((soil == TFCBlocks.tilledSoil || soil == TFCBlocks.tilledSoil2) && world.isAirBlock(x, y + 1, z))
			{
				CWTFCCropIndex crop = CropManager.getInstance().getCropFromId(cropId);
				if(crop != null)
				{
					if (crop.needsSunlight && !TECrop.hasSunlight(world, x, y + 1, z))
					{
						TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("gui.seeds.failedSun"));
						return false;
					}

					if (TFC_Climate.getHeightAdjustedTemp(world, x, y, z) <= crop.minAliveTemp && !crop.dormantInFrost)
					{
						TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("gui.seeds.failedTemp"));
						return false;
					}
					
					world.setBlock(x, y + 1, z, CWTFCBlocks.customCrop, rand.nextInt(4), 3);
					
					TileCrop teCrop = (TileCrop)world.getTileEntity(x, y + 1, z);
					teCrop.setCropID(cropId);
					
					world.markBlockForUpdate(teCrop.xCoord, teCrop.yCoord, teCrop.zCoord);
					world.markBlockForUpdate(x, y, z);
					--stack.stackSize;
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(ModInfo.ModID + ":" + getUnlocalizedName().replace("item.", ""));
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);

		SkillRank rank = TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_AGRICULTURE);
		
		CWTFCCropIndex id = CropManager.getInstance().getCropFromId(cropId);
		int nutrient = 0;
		
		if(id != null)
			nutrient = id.getCycleType();
		
		
		if (rank == SkillRank.Expert || rank == SkillRank.Master)
		{
			switch (nutrient)
			{
			case 0:
				arraylist.add(EnumChatFormatting.RED + TFC_Core.translate("gui.Nutrient.A"));
				break;
			case 1:
				arraylist.add(EnumChatFormatting.GOLD + TFC_Core.translate("gui.Nutrient.B"));
				break;
			case 2:
				arraylist.add(EnumChatFormatting.YELLOW + TFC_Core.translate("gui.Nutrient.C"));
				break;
			default:
				break;
			}

		}
	}
}
