package com.JAWolfe.cookingwithtfc.handlers;

import com.JAWolfe.cookingwithtfc.tileentities.TEPrepTable;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class CreateRecipePacket extends AbstractPacket
{
	private int x;
	private int y;
	private int z;
	private int button;
	
	public CreateRecipePacket(){}
	
	public CreateRecipePacket(TEPrepTable te, int button)
	{
		this.x = te.xCoord;
		this.y = te.yCoord;
		this.z = te.zCoord;
		this.button = button;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(this.x);
		buffer.writeInt(this.y);
		buffer.writeInt(this.z);
		buffer.writeInt(this.button);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
		this.button = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		TEPrepTable te = (TEPrepTable) player.worldObj.getTileEntity(x, y, z);
		
		switch(this.button)
		{
			case 0:
			{
				te.subRecipeListRef();
				break;
			}
			case 1:
			{
				te.addRecipeListRef();
				break;
			}
			case 2:
			{
				te.popRecipes(player);
				break;
			}
			default: break;
		}
	}
}
