package com.JAWolfe.cookingwithtfc.proxy;


import com.JAWolfe.cookingwithtfc.entities.EntityTransformBear;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformChickenTFC;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformCowTFC;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformDeer;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformHorseTFC;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformPheasant;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformPigTFC;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformSheepTFC;
import com.JAWolfe.cookingwithtfc.entities.EntityTransformWolfTFC;
import com.JAWolfe.cookingwithtfc.tileentities.TEGrains;
import com.JAWolfe.cookingwithtfc.tileentities.TEMixBowl;
import com.JAWolfe.cookingwithtfc.tileentities.TENestBoxCWTFC;
import com.JAWolfe.cookingwithtfc.tileentities.TEPrepTable;

import com.bioxx.tfc.TerraFirmaCraft;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;

public abstract class CommonProxyCWTFC implements IProxyCWTFC
{
	public EntityPlayer getPlayerEntity(MessageContext ctx) 
	{
		return ctx.getServerHandler().playerEntity;
	}
	
	public void registerTileEntities(boolean b)
	{		
		GameRegistry.registerTileEntity(TENestBoxCWTFC.class, "NestBoxCWTFC");
		GameRegistry.registerTileEntity(TEGrains.class, "TEGrains");
		GameRegistry.registerTileEntity(TEMixBowl.class, "TEMixingBowl");
		GameRegistry.registerTileEntity(TEPrepTable.class, "TEPrepTable");
		
		EntityRegistry.registerGlobalEntityID(EntityTransformSheepTFC.class, "sheepCWTFC", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityTransformHorseTFC.class, "horseCWTFC", EntityRegistry.findGlobalUniqueEntityId());
		
		EntityRegistry.registerModEntity(EntityTransformCowTFC.class, "cowCWTFC", 6, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityTransformWolfTFC.class, "wolfCWTFC", 7, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityTransformBear.class, "bearCWTFC", 8, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityTransformChickenTFC.class, "chickenCWTFC", 9, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityTransformPigTFC.class, "pigCWTFC", 10, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityTransformDeer.class, "deerCWTFC", 11, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityTransformPheasant.class, "PheasantCWTFC", 26, TerraFirmaCraft.instance, 160, 5, true);
	}
	
	public void registerRenderInformation()
	{
		//Not on Server
	}
	
	public void registerWAILA()
	{
		FMLInterModComms.sendMessage("Waila", "register", "com.JAWolfe.cookingwithtfc.thirdparty.waila.WAILAInfo.callbackRegister");
		FMLInterModComms.sendMessage("Waila", "register", "com.JAWolfe.cookingwithtfc.thirdparty.waila.WAILAMobs.callbackRegister");
	}
}