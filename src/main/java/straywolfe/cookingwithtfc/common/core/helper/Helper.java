package straywolfe.cookingwithtfc.common.core.helper;

import java.util.Map;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Interfaces.IFood;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.LoaderState.ModState;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class Helper 
{	
	public static boolean placingPermission(World world, EntityPlayer player, ItemStack stack, int x, int y, int z, int side)
	{
		if(stack.stackSize > 0 && player.canPlayerEdit(x, y, z, side, stack) && 
				(world.isAirBlock(x, y, z) || world.getBlock(x, y, z).isReplaceable(world, x, y, z)))
			return true;
		else
			return false;
	}
	
	public static boolean isReqModVersion(String modname, String reqversion)
	{
		Map<String,ModContainer> modList = Loader.instance().getIndexedModList();
		
		if(modList.containsKey(modname))
		{
			ModContainer mod = modList.get(modname);
			if(Loader.instance().getModState(mod) != ModState.DISABLED && versionCheck(reqversion, mod.getVersion()) != -1)
				return true;
		}
		
		return false;
	}
	
	public static int versionCheck(String reqVersion, String curVersion)
	{
		int reqV = 0, modV = 0;
		
		for (int i = 0, j = 0; (i < reqVersion.length() || j < curVersion.length());)
		{
			while (i < reqVersion.length() && reqVersion.charAt(i) != '.')
			{
				reqV = reqV * 10 + (reqVersion.charAt(i) - '0');
				i++;
			}
			
			while (j < curVersion.length() && curVersion.charAt(j) != '.')
			{
				modV = modV * 10 + (curVersion.charAt(i) - '0');
				j++;
			}
			
			if(reqV > modV)
				return 1;
			if(reqV < modV)
				return -1;
			
			reqV = modV = 0;
	        i++;
	        j++;
		}
		
		return 0;
	}
	
	public static void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}
	
	public static boolean areItemStacksEqual(ItemStack input, ItemStack target)
    {
        return input == target || OreDictionary.itemMatches(target, input, false);
    }
	
	public static boolean isOre(String oreDict, ItemStack ore)
	{
		for(ItemStack ostack : OreDictionary.getOres(oreDict)) 
		{
			ItemStack cstack = ostack.copy();
			if(cstack.getItemDamage() == Short.MAX_VALUE)
				cstack.setItemDamage(ore.getItemDamage());
			
			if(ore.isItemEqual(cstack)) 
				return true;
		}
		return false;
	}
	
	public static ItemStack getItemStackForFluid(FluidStack fluidStack)
    {
        if (fluidStack == null)
        	return null;

        ItemStack fluidContainer = null;
        
        for (FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData())
        {
            if (data.fluid.isFluidEqual(fluidStack))
            {
            	fluidContainer = data.filledContainer.copy();
            	
                int capacity = FluidContainerRegistry.getContainerCapacity(data.fluid, data.emptyContainer);
                
                if (capacity == 0) 
                	fluidContainer.stackSize = 0;
                else 
                	fluidContainer.stackSize = fluidStack.amount / capacity;
                
                break;
            }
        }
        
        return fluidContainer;
    }
	
	public static void combineTastes(NBTTagCompound nbt, ItemStack[] isArray)
	{
		int tasteSweet = 0;
		int tasteSour = 0;
		int tasteSalty = 0;
		int tasteBitter = 0;
		int tasteUmami = 0;

		for (int i = 0; i < isArray.length; i++)
		{
			if(isArray[i] != null)
			{
				tasteSweet += ((IFood)isArray[i].getItem()).getTasteSweet(isArray[i]);
				tasteSour += ((IFood)isArray[i].getItem()).getTasteSour(isArray[i]);
				tasteSalty += ((IFood)isArray[i].getItem()).getTasteSalty(isArray[i]);
				tasteBitter += ((IFood)isArray[i].getItem()).getTasteBitter(isArray[i]);
				tasteUmami += ((IFood)isArray[i].getItem()).getTasteSavory(isArray[i]);
			}
		}
		nbt.setInteger("tasteSweet", tasteSweet);
		nbt.setInteger("tasteSour", tasteSour);
		nbt.setInteger("tasteSalty", tasteSalty);
		nbt.setInteger("tasteBitter", tasteBitter);
		nbt.setInteger("tasteUmami", tasteUmami);
	}
	
	public static boolean isStone(Block is)
	{
		return TFC_Core.isSmoothStone(is) || 
				TFC_Core.isBrickStone(is) || 
				TFC_Core.isRawStone(is) || 
				TFC_Core.isCobbleStone(is);
	}
	
	public static void postNBTError(EntityPlayer player, ItemStack is)
	{
		String error = translate("error.error") + " " + is.getUnlocalizedName() + " " +
				translate("error.NBT") + " " + translate("error.Contact");
		TerraFirmaCraft.LOG.error(error);
		TFC_Core.sendInfoMessage(player, new ChatComponentText(error));
	}
	
	public static String translate(String s)
	{
		return StatCollector.translateToLocal(s);
	}
}
