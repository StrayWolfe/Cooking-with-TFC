package com.JAWolfe.cookingwithtfc.handlers.messages;

import com.JAWolfe.cookingwithtfc.core.CWTFC_Core;
import com.JAWolfe.cookingwithtfc.core.FoodRecord;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class MessageFoodRecord extends AbstractPacket
{
	public FoodRecord foodrecord;
	public int RecordSize;
	public int FoodListRef = 0;
	public String[] FoodsEaten;
	
	public MessageFoodRecord(){}
	
	public MessageFoodRecord(EntityPlayer player, FoodRecord fr)
	{
		this.foodrecord = fr;
		this.RecordSize = this.foodrecord.RecordSize;
		this.FoodsEaten = this.foodrecord.FoodsEaten;
		this.FoodListRef = this.foodrecord.FoodListRef;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		buffer.writeInt(this.RecordSize);
		buffer.writeInt(this.FoodListRef);
		for(int i = 0; i < this.RecordSize; ++i)
		{
			if(this.foodrecord.FoodsEaten[i] != null)
			{
				ByteBufUtils.writeUTF8String(buffer, this.foodrecord.FoodsEaten[i]);
			}
		}		
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		this.RecordSize = buffer.readInt();
		this.FoodsEaten = new String[RecordSize];
		this.FoodListRef =  buffer.readInt();
		for(int i = 0; i < this.RecordSize; ++i)
		{
			if(buffer.isReadable())
				this.FoodsEaten[i] = ByteBufUtils.readUTF8String(buffer);
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) 
	{
		this.foodrecord = new FoodRecord(player, this.RecordSize);
		this.foodrecord.RecordSize = this.RecordSize;
		this.foodrecord.FoodListRef = this.FoodListRef;
		for(int i = 0; i < this.FoodsEaten.length; i++)
		{
			this.foodrecord.FoodsEaten[i] = this.FoodsEaten[i];
		}
		CWTFC_Core.setPlayerFoodRecord(player, this.foodrecord);
	}

	@Override
	public void handleServerSide(EntityPlayer player) {}

}
