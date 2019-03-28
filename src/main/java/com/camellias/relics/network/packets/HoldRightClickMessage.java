package com.camellias.relics.network.packets;

import com.camellias.relics.Main;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HoldRightClickMessage implements IMessage
{
	public HoldRightClickMessage()
	{
		
	}
	
	public int playerID;
	
	public HoldRightClickMessage(EntityPlayer player)
	{
		this.playerID = player.getEntityId();
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(playerID);
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.playerID = buf.readInt();
	}
	
//-------------------------------------------------------------------------------------------------------------------------//
	
	public static class HoldRightClickPacketHandler implements IMessageHandler<HoldRightClickMessage, IMessage>
	{
		@Override
		public IMessage onMessage(HoldRightClickMessage message, MessageContext ctx)
		{
			Main.proxy.getThreadListener(ctx).addScheduledTask(() ->
			{
				if(Main.proxy.getPlayer(ctx) != null)
				{
					EntityPlayer player = (EntityPlayer) Main.proxy.getPlayer(ctx).world.getEntityByID(message.playerID);
					GameSettings settings = new GameSettings();
					
					settings.isKeyDown(settings.keyBindUseItem);
				}
			});
			
			return null;
		}
	}
}
