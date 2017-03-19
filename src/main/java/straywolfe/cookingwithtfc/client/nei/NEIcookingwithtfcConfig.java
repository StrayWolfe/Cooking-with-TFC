package straywolfe.cookingwithtfc.client.nei;

import com.bioxx.tfc.Food.ItemSalad;
import com.bioxx.tfc.Food.ItemSandwich;
import com.bioxx.tfc.api.TFCItems;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import straywolfe.cookingwithtfc.api.CWTFCBlocks;
import straywolfe.cookingwithtfc.client.nei.NEIGUIHandler;
import straywolfe.cookingwithtfc.common.lib.ModInfo;

public class NEIcookingwithtfcConfig implements IConfigureNEI
{
	@Override
	public String getName() 
	{
		return ModInfo.ModName;
	}

	@Override
	public String getVersion() 
	{
		return ModInfo.ModVersion;
	}

	@Override
	public void loadConfig() 
	{
		CookingPotRecipeHandler cookingpotRecipeHandler = new CookingPotRecipeHandler();
		ClayOvenRecipeHandler clayovenRecipeHandler = new ClayOvenRecipeHandler();
		
		API.registerNEIGuiHandler(new NEIGUIHandler());
        
        API.registerRecipeHandler(cookingpotRecipeHandler);
        API.registerUsageHandler(cookingpotRecipeHandler);
        
        API.registerRecipeHandler(clayovenRecipeHandler);
        API.registerUsageHandler(clayovenRecipeHandler);
		
		API.hideItem(new ItemStack(CWTFCBlocks.GrainsBlock));
		API.hideItem(new ItemStack(CWTFCBlocks.hopperCWTFC));
		API.hideItem(new ItemStack(CWTFCBlocks.cookingPot));
		API.hideItem(new ItemStack(CWTFCBlocks.meatCWTFC));
		API.hideItem(new ItemStack(CWTFCBlocks.sandwichCWTFC));
		API.hideItem(new ItemStack(CWTFCBlocks.bowlCWTFC));
		API.hideItem(new ItemStack(CWTFCBlocks.clayOven));
		API.hideItem(new ItemStack(CWTFCBlocks.customCrop));
		API.hideItem(new ItemStack(CWTFCBlocks.customGourd));
		API.hideItem(new ItemStack(CWTFCBlocks.tableStorage));
		API.hideItem(new ItemStack(CWTFCBlocks.nutTreeLeaves));
		API.hideItem(new ItemStack(CWTFCBlocks.lumberConstruct));
		
		API.hideItem(new ItemStack(CWTFCBlocks.prepTable2E, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(CWTFCBlocks.prepTable2S, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(CWTFCBlocks.prepTable2W, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(CWTFCBlocks.prepTableE, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(CWTFCBlocks.prepTableS, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(CWTFCBlocks.prepTableW, 1, OreDictionary.WILDCARD_VALUE));
		
		API.hideItem(new ItemStack(CWTFCBlocks.naturalLog, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(CWTFCBlocks.woodVert, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(CWTFCBlocks.woodHorizEW, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(CWTFCBlocks.woodHorizNS, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(CWTFCBlocks.customLeaves, 1, OreDictionary.WILDCARD_VALUE));

		API.hideItem(ItemSalad.createTag(new ItemStack(TFCItems.salad, 1, OreDictionary.WILDCARD_VALUE)));
		API.hideItem(ItemSandwich.createTag(new ItemStack(TFCItems.sandwich, 1, OreDictionary.WILDCARD_VALUE)));
	}
}
