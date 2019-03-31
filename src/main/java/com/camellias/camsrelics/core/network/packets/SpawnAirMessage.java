package com.camellias.camsrelics.core.network.packets;

import com.camellias.camsrelics.Main;
import com.camellias.camsrelics.common.entities.EntityAirBlast;
import com.camellias.camsrelics.common.entities.EntityAirTornado;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SpawnAirMessage implements IMessage
{
	public SpawnAirMessage()
	{
		
	}
	
	public int playerID;
	
	public SpawnAirMessage(EntityPlayer player)
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
	
	public static class SpawnAirPacketHandler implements IMessageHandler<SpawnAirMessage, IMessage>
	{
		@Override
		public IMessage onMessage(SpawnAirMessage message, MessageContext ctx)
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
							EntityAirTornado air_tornado = new EntityAirTornado(world, player);
							air_tornado.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.35F, 1.0F);
							air_tornado.setPosition(player.posX, player.posY, player.posZ);
							world.spawnEntity(air_tornado);
						}
						else
						{
							EntityAirBlast air_blast = new EntityAirBlast(world, player);
							air_blast.setPosition(player.posX, player.posY, player.posZ);
							world.spawnEntity(air_blast);
						}
					}
				}
			});
			
			return null;
		}
	}
}
