package com.JAWolfe.cookingwithtfc.GUIs;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.JAWolfe.cookingwithtfc.GUIs.Containers.ContainerClayCookingPot;
import com.JAWolfe.cookingwithtfc.handlers.messages.ItemCookingPotPacket;
import com.JAWolfe.cookingwithtfc.items.Items.ItemClayCookingPot;
import com.JAWolfe.cookingwithtfc.references.Textures;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.GUI.GuiBarrel;
import com.bioxx.tfc.GUI.GuiContainerTFC;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class GUIClayCookingPot extends GuiContainerTFC
{
	private EntityPlayer player;
	
	public GUIClayCookingPot(InventoryPlayer playerinv, World world, int x, int y, int z)
	{
		super(new ContainerClayCookingPot(playerinv, world, x, y, z), 176, 85);
		player = playerinv.player;
		guiLeft = (width - 208) / 2;
		guiTop = (height - 198) / 2;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		createButtons();
	}
	
	@SuppressWarnings("unchecked")
	public void createButtons()
	{
		buttonList.clear();
		
		ItemStack cookingPot = player.getCurrentEquippedItem();
		if(((ItemClayCookingPot)cookingPot.getItem()).getInputMode(cookingPot))
			buttonList.add(new GuiModeButton(0, guiLeft + 29, guiTop + 32, 16, 16, this, TFC_Core.translate("gui.Barrel.ToggleOn"), 0, 204, 16, 16));
		else
			buttonList.add(new GuiModeButton(0, guiLeft + 29, guiTop + 32, 16, 16, this, TFC_Core.translate("gui.Barrel.ToggleOff"), 0, 188, 16, 16));
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		((GuiButton) buttonList.get(0)).visible = true;
	}
	
	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
		{
			ItemStack cookingPot = player.getCurrentEquippedItem();
			
			if(((ItemClayCookingPot)cookingPot.getItem()).getInputMode(cookingPot))
			{
				((ItemClayCookingPot)cookingPot.getItem()).setInputMode(cookingPot, false);
				TerraFirmaCraft.PACKET_PIPELINE.sendToServer(new ItemCookingPotPacket(false));
			}
			else
			{
				((ItemClayCookingPot)cookingPot.getItem()).setInputMode(cookingPot, true);
				TerraFirmaCraft.PACKET_PIPELINE.sendToServer(new ItemCookingPotPacket(true));
			}
			
			createButtons();
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		bindTexture(Textures.Gui.CLAYCOOKINGPOTINIT);
		
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, this.getShiftedYSize());
		
		int scale = 0;
		if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemClayCookingPot)
		{
			ItemClayCookingPot cookingPot = (ItemClayCookingPot)player.getCurrentEquippedItem().getItem();
			
			if(cookingPot.getPotFluid() != null)
			{
				scale = cookingPot.getLiquidScaled(50);
				IIcon liquidIcon = cookingPot.getPotFluid().getFluid().getIcon(cookingPot.getPotFluid());
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				int color = cookingPot.getPotFluid().getFluid().getColor(cookingPot.getPotFluid());				
				GL11.glColor4ub((byte) ((color >> 16) & 255), (byte) ((color >> 8) & 255), (byte) (color & 255), (byte) ((0xaa) & 255));
				int div = (int) Math.floor(scale / 8);
				int rem = scale - (div * 8);
				this.drawTexturedModelRectFromIcon(guiLeft + 12, guiTop + 65 - scale, liquidIcon, 8, div > 0 ? 8 : rem);
				for (int c = 0; div > 0 && c < div; c++)
				{
					this.drawTexturedModelRectFromIcon(guiLeft + 12, guiTop + 65 - (8 + (c * 8)), liquidIcon, 8, 8);
				}
				GL11.glColor3f(0, 0, 0);
				
				drawCenteredString(this.fontRendererObj, cookingPot.getPotFluid().getFluid().getLocalizedName(cookingPot.getPotFluid()), guiLeft + 88, guiTop + 7, 0x555555);
			}
		}
		
		PlayerInventory.drawInventory(this, width, height, this.getShiftedYSize() - 2);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if (this.mouseInRegion(12, 15, 9, 50, mouseX, mouseY))
		{
			ArrayList<String> list = new ArrayList<String>();
			ItemClayCookingPot cookingPot = (ItemClayCookingPot)player.getCurrentEquippedItem().getItem();
			list.add(cookingPot.getFluidLevel() + "mB");
			this.drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop + 8, this.fontRendererObj);
		}
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}
	
	public class GuiModeButton extends GuiButton
	{
		private GUIClayCookingPot screen;
		private IIcon buttonicon;

		private int xPos;
		private int yPos = 172;
		private int xSize = 31;
		private int ySize = 15;

		public GuiModeButton(int index, int xPos, int yPos, int width, int height, GUIClayCookingPot gui, String s, int xp, int yp, int xs, int ys)
		{
			super(index, xPos, yPos, width, height, s);
			screen = gui;
			this.xPos = xp;
			this.yPos = yp;
			xSize = xs;
			ySize = ys;
		}

		@Override
		public void drawButton(Minecraft mc, int x, int y)
		{
			if (this.visible)
			{
				TFC_Core.bindTexture(GuiBarrel.TEXTURE);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.zLevel = 301f;
				this.drawTexturedModalRect(this.xPosition, this.yPosition, xPos, yPos, xSize, ySize);
				this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				if (buttonicon != null)
					this.drawTexturedModelRectFromIcon(this.xPosition + 12, this.yPosition + 4, buttonicon, 8, 8);

				this.zLevel = 0;
				this.mouseDragged(mc, x, y);

				if (field_146123_n)
				{
					screen.drawTooltip(x, y, this.displayString);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				}
			}
		}
	}
}
