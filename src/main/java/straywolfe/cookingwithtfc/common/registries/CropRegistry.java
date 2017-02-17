package straywolfe.cookingwithtfc.common.registries;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.api.managers.CWTFCCropIndex;
import straywolfe.cookingwithtfc.api.managers.CropManager;
import straywolfe.cookingwithtfc.common.lib.Settings;

public class CropRegistry 
{
	public static final int WATERMELON = 50;
	public static final int PUMPKIN = 51;
	public static final int BROWNMUSHROOM = 52;
	public static final int REDMUSHROOM = 53;
	public static final int CELERY = 54;
	public static final int LETTUCE = 55;
	
	public static void registerCrops()
	{		
		CropManager cropmanager = CropManager.getInstance();
		
		cropmanager.addCrop(new CWTFCCropIndex(/*ID*/PUMPKIN, /*Name*/"Pumpkin", /*type*/2, /*time*/32, /*stages*/5, /*minGTemp*/10, /*minATemp*/0, /*nutrientUsage*/0.9f, CWTFCItems.seedsPumpkin).setOutput1(CWTFCItems.pumpkinBlock, 1f).setWaterUsage(1.2f).setHarvestSeed(false));
		cropmanager.addCrop(new CWTFCCropIndex(/*ID*/BROWNMUSHROOM, /*Name*/"BrownMushroom", /*type*/1, /*time*/12, /*stages*/4, /*minGTemp*/18, /*minATemp*/0, /*nutrientUsage*/2.0f, CWTFCItems.sporesBrownMushroom).setOutput1(Settings.vanillaMushrooms ? Item.getItemFromBlock(Blocks.brown_mushroom) : CWTFCItems.brownMushroom, 12f).setGoesDormant(true).setNeedsSunlight(false));
		cropmanager.addCrop(new CWTFCCropIndex(/*ID*/REDMUSHROOM, /*Name*/"RedMushroom", /*type*/1, /*time*/12, /*stages*/4, /*minGTemp*/18, /*minATemp*/0, /*nutrientUsage*/2.0f, CWTFCItems.sporesRedMushroom).setOutput1(Settings.vanillaMushrooms ? Item.getItemFromBlock(Blocks.red_mushroom) : CWTFCItems.redMushroom, 12f).setGoesDormant(true).setNeedsSunlight(false));
		
		if(!Settings.disableCelery)
			cropmanager.addWildCrop(new CWTFCCropIndex(/*ID*/CELERY, /*Name*/"Celery", /*type*/2, /*time*/39, /*stages*/7, /*minGTemp*/18, /*minATemp*/12, /*nutrientUsage*/0.75f, CWTFCItems.seedsCelery).setOutput1(CWTFCItems.celery, 16f).setWaterUsage(1.5f));
		
		if(!Settings.disableLettuce)
			cropmanager.addWildCrop(new CWTFCCropIndex(/*ID*/LETTUCE, /*Name*/"Lettuce", /*type*/1, /*time*/25, /*stages*/6, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/1.1f, CWTFCItems.seedsLettuce).setOutput1(CWTFCItems.lettuce, 17f).setWaterUsage(0.9f));
		
		if(!Settings.disableMelons)
			cropmanager.addWildCrop(new CWTFCCropIndex(/*ID*/WATERMELON, /*Name*/"Watermelon", /*type*/2, /*time*/30, /*stages*/5, /*minGTemp*/10, /*minATemp*/0, /*nutrientUsage*/0.9f, CWTFCItems.seedsMelon).setOutput1(Settings.vanillaMelons ? Item.getItemFromBlock(Blocks.melon_block) : CWTFCItems.melonBlock, 50f).setWaterUsage(1.2f).setHarvestSeed(Settings.vanillaMelons ? true : false));
	}
}
