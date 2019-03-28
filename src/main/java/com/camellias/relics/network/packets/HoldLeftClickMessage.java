package com.camellias.relics.network.packets;

import com.camellias.relics.Main;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HoldLeftClickMessage implements IMessage
{
	public HoldLeftClickMessage()
	{
		
	}
	
	public int playerID;
	
	public HoldLeftClickMessage(EntityPlayer player)
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
	
	public static class HoldLeftClickPacketHandler implements IMessageHandler<HoldLeftClickMessage, IMessage>
	{
		@Override
		public IMessage onMessage(HoldLeftClickMessage message, MessageContext ctx)
		{
			Main.proxy.getThreadListener(ctx).addScheduledTask(() ->
			{
				if(Main.proxy.getPlayer(ctx) != null)
				{
					EntityPlayer player = (EntityPlayer) Main.proxy.getPlayer(ctx).world.getEntityByID(message.playerID);
					GameSettings settings = new GameSettings();
					
					settings.isKeyDown(settings.keyBindAttack);
				}
			});
			
			return null;
		}
	}
}
