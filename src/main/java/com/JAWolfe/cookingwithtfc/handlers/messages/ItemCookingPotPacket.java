package com.JAWolfe.cookingwithtfc.handlers.messages;

import com.JAWolfe.cookingwithtfc.items.Items.ItemClayCookingPot;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class ItemCookingPotPacket extends AbstractPacket
{
	private boolean inputMode;
	
	public ItemCookingPotPacket() {}

	
	public ItemCookingPotPacket(boolean mode)
	{
		this.inputMode = mode;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		buffer.writeBoolean(this.inputMode);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		this.inputMode = buffer.readBoolean();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		
	}

	@Override
	public void handleServerSide(EntityPlayer player) 
	{
		((ItemClayCookingPot)player.getCurrentEquippedItem().getItem()).setInputMode(player.getCurrentEquippedItem(), this.inputMode);
	}
}
