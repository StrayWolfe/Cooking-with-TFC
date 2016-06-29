package com.JAWolfe.cookingwithtfc.inventory;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.JAWolfe.cookingwithtfc.crafting.CookingPotManager;
import com.JAWolfe.cookingwithtfc.crafting.CookingPotRecipe;
import com.JAWolfe.cookingwithtfc.inventory.Containers.ContainerClayCookingPot;
import com.JAWolfe.cookingwithtfc.references.Textures;
import com.JAWolfe.cookingwithtfc.tileentities.TECookingPot;
import com.JAWolfe.cookingwithtfc.util.Helper;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.GUI.GuiContainerTFC;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class GUIClayCookingPot extends GuiContainerTFC
{	
	private TECookingPot teCookingPot;
	
	public GUIClayCookingPot(InventoryPlayer playerinv, TECookingPot pot, World world, int x, int y, int z)
	{
		super(new ContainerClayCookingPot(playerinv, pot, world, x, y, z), 176, 97);
		
		teCookingPot = pot;
	}	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		bindTexture(Textures.Gui.CLAYCOOKINGPOT);
		
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, this.getShiftedYSize());
		
		drawForeground(guiLeft, guiTop);

		if(teCookingPot != null)
		{			
			if(teCookingPot.getCookingPotFluid() != null)
			{
				int scale = teCookingPot.getLiquidScaled(50);
				FluidStack fluid = teCookingPot.getCookingPotFluid();
				IIcon liquidIcon = fluid.getFluid().getIcon(fluid);
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				int color = fluid.getFluid().getColor(fluid);				
				GL11.glColor4ub((byte) ((color >> 16) & 255), (byte) ((color >> 8) & 255), (byte) (color & 255), (byte) ((0xaa) & 255));
				int div = (int) Math.floor(scale / 8);
				int rem = scale - (div * 8);
				this.drawTexturedModelRectFromIcon(guiLeft + 157, guiTop + 68 - scale, liquidIcon, 8, div > 0 ? 8 : rem);
				for (int c = 0; div > 0 && c < div; c++)
				{
					this.drawTexturedModelRectFromIcon(guiLeft + 157, guiTop + 68 - (8 + (c * 8)), liquidIcon, 8, 8);
				}
				GL11.glColor3f(0, 0, 0);
				
				Helper.drawCenteredString(this.fontRendererObj, fluid.getFluid().getLocalizedName(fluid), guiLeft + 101, guiTop + 7, 0x555555);
			}
			if(teCookingPot.getRecipeID() != -1)
			{
				CookingPotRecipe recipe = CookingPotManager.getInstance().getRecipe(teCookingPot.getRecipeID());
				
				if(recipe.getOutputFluid() != null)
					Helper.drawCenteredString(this.fontRendererObj, "Output: " + recipe.getOutputFluid().getFluid().getLocalizedName(recipe.getOutputFluid()), guiLeft + 85, guiTop + 83, 0x555555);
				else
				{
					String name = "";
					ItemStack[] outputItems = recipe.getOutputItems();
					
					name = name.concat(outputItems[0].getDisplayName());
					if(outputItems.length > 1)
						name = name.concat("...");
					
					Helper.drawCenteredString(this.fontRendererObj, "Output: " + name, guiLeft + 85, guiTop + 83, 0x555555);
				}
					
			}
		}
		
		PlayerInventory.drawInventory(this, width, height, this.getShiftedYSize());
	}
	
	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		if (teCookingPot != null)
		{
			int scale = teCookingPot.getTemperatureScaled(49);
			drawTexturedModalRect(guiLeft + 30, guiTop + 65 - scale, 185, 32, 15, 5);
			
			if(teCookingPot.getCookTimer() != -1 && teCookingPot.fireTemp > 600)
			{
				drawTexturedModalRect(guiLeft + 71, guiTop + 20, 67, 102, 58, 49);

				int imageHeight = (int)(49 * teCookingPot.getCookTimerScale());
				drawTexturedModalRect(guiLeft + 71, guiTop + 69 - imageHeight, 4, 151 - imageHeight, 58, imageHeight);
			}
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if(teCookingPot != null)
		{		
			if (this.mouseInRegion(156, 17, 10, 52, mouseX, mouseY))
			{
				ArrayList<String> list = new ArrayList<String>();
				list.add(teCookingPot.getFluidLevel() + "mB");
				this.drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop + 8, this.fontRendererObj);
			}
		}
	}
}
