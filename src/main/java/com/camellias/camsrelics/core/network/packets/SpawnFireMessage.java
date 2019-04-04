package com.camellias.camsrelics.core.network.packets;

import com.camellias.camsrelics.Main;
import com.camellias.camsrelics.common.entities.EntityFireEmber;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SpawnFireMessage implements IMessage
{
	public SpawnFireMessage()
	{
		
	}
	
	public int playerID;
	
	public SpawnFireMessage(EntityPlayer player)
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
	
	public static class SpawnFirePacketHandler implements IMessageHandler<SpawnFireMessage, IMessage>
	{
		@Override
		public IMessage onMessage(SpawnFireMessage message, MessageContext ctx)
		{
			Main.proxy.getThreadListener(ctx).addScheduledTask(() ->
			{
				if(Main.proxy.getPlayer(ctx) != null)
				{
					EntityPlayer player = (EntityPlayer) Main.proxy.getPlayer(ctx).world.getEntityByID(message.playerID);
					World world = ctx.getServerHandler().player.getServerWorld();
					
					if(!world.isRemote)
					{
						if(!player.isSneaking())
						{
							EntityFireEmber fire_ember = new EntityFireEmber(world, player);
							fire_ember.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 2.0F, 1.0F);
							world.spawnEntity(fire_ember);
						}
						else
						{
							
						}
					}
				}
			});
			
			return null;
		}
	}
}
