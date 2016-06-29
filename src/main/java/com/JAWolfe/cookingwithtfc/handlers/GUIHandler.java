package com.JAWolfe.cookingwithtfc.handlers;

import com.JAWolfe.cookingwithtfc.inventory.GUIClayCookingPot;
import com.JAWolfe.cookingwithtfc.inventory.GUIPrepTable;
import com.JAWolfe.cookingwithtfc.inventory.Containers.ContainerClayCookingPot;
import com.JAWolfe.cookingwithtfc.inventory.Containers.ContainerPrepTable;
import com.JAWolfe.cookingwithtfc.references.GUIs;
import com.JAWolfe.cookingwithtfc.tileentities.TECookingPot;
import com.JAWolfe.cookingwithtfc.tileentities.TEPrepTable;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity entity = world.getTileEntity(x, y, z);
		
		if(ID == GUIs.PREPTABLE.ordinal() && entity != null && entity instanceof TEPrepTable)
			return new ContainerPrepTable(player.inventory, (TEPrepTable)entity);
		if(ID == GUIs.CLAYCOOKINGPOT.ordinal() && entity != null && entity instanceof TECookingPot)
			return new ContainerClayCookingPot(player.inventory, (TECookingPot)entity, world, x, y, z);

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity entity = world.getTileEntity(x, y, z);
		
		if(ID == GUIs.PREPTABLE.ordinal() && entity != null && entity instanceof TEPrepTable)
			return new GUIPrepTable(player.inventory, (TEPrepTable)entity, world, x, y, z);
		if(ID == GUIs.CLAYCOOKINGPOT.ordinal() && entity != null && entity instanceof TECookingPot)
			return new GUIClayCookingPot(player.inventory, (TECookingPot)entity, world, x, y, z);

		return null;
	}

}
