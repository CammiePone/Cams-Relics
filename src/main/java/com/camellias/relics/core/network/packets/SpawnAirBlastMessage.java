package com.camellias.relics.core.network.packets;

import com.camellias.relics.Main;
import com.camellias.relics.common.entities.EntityAirBlast;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SpawnAirBlastMessage implements IMessage
{
	public SpawnAirBlastMessage()
	{
		
	}
	
	public int playerID;
	
	public SpawnAirBlastMessage(EntityPlayer player)
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
	
	public static class SpawnAirBlastPacketHandler implements IMessageHandler<SpawnAirBlastMessage, IMessage>
	{
		@Override
		public IMessage onMessage(SpawnAirBlastMessage message, MessageContext ctx)
		{
			Main.proxy.getThreadListener(ctx).addScheduledTask(() ->
			{
				if(Main.proxy.getPlayer(ctx) != null)
				{
					EntityPlayer player = (EntityPlayer) Main.proxy.getPlayer(ctx).world.getEntityByID(message.playerID);
					World world = ctx.getServerHandler().player.getServerWorld();
					
					if(!world.isRemote)
					{
						EntityAirBlast air_blast = new EntityAirBlast(world, player);
						air_blast.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.35F, 1.0F);
						air_blast.setPosition(player.posX, player.posY, player.posZ);
						world.spawnEntity(air_blast);
					}
				}
			});
			
			return null;
		}
	}
}
