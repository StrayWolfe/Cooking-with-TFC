package straywolfe.cookingwithtfc.common.proxy;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import straywolfe.cookingwithtfc.api.CWTFCFluids;
import straywolfe.cookingwithtfc.api.CWTFCItems;
import straywolfe.cookingwithtfc.common.entity.*;
import straywolfe.cookingwithtfc.common.tileentity.*;

public class CommonProxyCWTFC 
{
	public void registerTileEntities(boolean clientReg)
	{		
		GameRegistry.registerTileEntity(TileNestBoxCWTFC.class, "TileNestBoxCWTFC");
		GameRegistry.registerTileEntity(TileGrains.class, "TileGrains");
		GameRegistry.registerTileEntity(TileMixBowl.class, "TileMixBowl");
		GameRegistry.registerTileEntity(TileHopperCWTFC.class, "TileHopperCWTFC");
		GameRegistry.registerTileEntity(TileBowl.class, "TileBowl");
		GameRegistry.registerTileEntity(TileMeat.class, "TileMeat");
		GameRegistry.registerTileEntity(TileCookingPot.class, "TileCookingPot");
		GameRegistry.registerTileEntity(TileSandwich.class, "TileSandwich");
		GameRegistry.registerTileEntity(TileTableStorage.class, "TileTableStorage");
		GameRegistry.registerTileEntity(TileCrop.class, "TileCrop");
		
		if(clientReg)
		{
			GameRegistry.registerTileEntity(TileClayOven.class, "TileClayOven");
			GameRegistry.registerTileEntity(TileGourd.class, "TilePumpkin");
		}
		
		EntityRegistry.registerGlobalEntityID(EntityTransformSheepTFC.class, "sheepCWTFC", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityTransformHorseTFC.class, "horseCWTFC", EntityRegistry.findGlobalUniqueEntityId());
		
		EntityRegistry.registerModEntity(EntityTransformCowTFC.class, "cowCWTFC", 6, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityTransformWolfTFC.class, "wolfCWTFC", 7, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityTransformBear.class, "bearCWTFC", 8, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityTransformChicken.class, "chickenCWTFC", 9, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityTransformPigTFC.class, "pigCWTFC", 10, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityTransformDeer.class, "deerCWTFC", 11, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityTransformPheasant.class, "PheasantCWTFC", 26, TerraFirmaCraft.instance, 160, 5, true);
	}
	
	public void registerRenderInformation()
	{
		//Client-side only
	}
	
	public void registerHandlers()
	{
		
	}
	
	public void registerFluids()
	{
		FluidRegistry.registerFluid(CWTFCFluids.MILKCURDLEDCWTFC);
		FluidRegistry.registerFluid(CWTFCFluids.MILKCWTFC);
		FluidRegistry.registerFluid(CWTFCFluids.MILKVINEGARCWTFC);
		FluidRegistry.registerFluid(CWTFCFluids.BROTH);
		FluidRegistry.registerFluid(CWTFCFluids.VEGETABLESOUP);
		FluidRegistry.registerFluid(CWTFCFluids.TOMATOSOUP);
		FluidRegistry.registerFluid(CWTFCFluids.CHICKENSOUP);
		FluidRegistry.registerFluid(CWTFCFluids.BEEFSTEW);
		FluidRegistry.registerFluid(CWTFCFluids.VENISONSTEW);
		FluidRegistry.registerFluid(CWTFCFluids.FISHCHOWDER);
	}
	
	public void setupFluids()
	{
		FluidContainerRegistry.registerFluidContainer(new FluidStack(CWTFCFluids.MILKCWTFC, 1000), ItemFoodTFC.createTag(new ItemStack(CWTFCItems.woodenBucketMilkCWTFC), 20, 0), new ItemStack(TFCItems.woodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(CWTFCFluids.BROTH, 1000), ItemFoodTFC.createTag(new ItemStack(CWTFCItems.Broth), 20, 0), new ItemStack(TFCItems.potteryJug, 1, 1));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(CWTFCFluids.VEGETABLESOUP, 250), ItemFoodTFC.createTag(new ItemStack(CWTFCItems.VegetableSoup), 10, 0), new ItemStack(TFCItems.potteryBowl, 1, 1));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(CWTFCFluids.TOMATOSOUP, 250), ItemFoodTFC.createTag(new ItemStack(CWTFCItems.TomatoSoup), 10, 0), new ItemStack(TFCItems.potteryBowl, 1, 1));		
		FluidContainerRegistry.registerFluidContainer(new FluidStack(CWTFCFluids.CHICKENSOUP, 250), ItemFoodTFC.createTag(new ItemStack(CWTFCItems.ChickenSoup), 10, 0), new ItemStack(TFCItems.potteryBowl, 1, 1));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(CWTFCFluids.BEEFSTEW, 250), ItemFoodTFC.createTag(new ItemStack(CWTFCItems.BeefStew), 10, 0), new ItemStack(TFCItems.potteryBowl, 1, 1));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(CWTFCFluids.VENISONSTEW, 250), ItemFoodTFC.createTag(new ItemStack(CWTFCItems.VenisonStew), 10, 0), new ItemStack(TFCItems.potteryBowl, 1, 1));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(CWTFCFluids.FISHCHOWDER, 250), ItemFoodTFC.createTag(new ItemStack(CWTFCItems.FishChowder), 10, 0), new ItemStack(TFCItems.potteryBowl, 1, 1));
	}
	
	public void registerWAILA()
	{
		FMLInterModComms.sendMessage("Waila", "register", "straywolfe.cookingwithtfc.client.waila.WAILAInfo.callbackRegister");
	}
}
