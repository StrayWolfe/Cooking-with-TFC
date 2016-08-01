package straywolfe.cookingwithtfc.common.core.helper;

import com.bioxx.tfc.api.Interfaces.IFood;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class Helper 
{
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
}
