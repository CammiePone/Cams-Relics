package com.camellias.camsrelics.core.network;

import com.camellias.camsrelics.Reference;
import com.camellias.camsrelics.core.network.packets.HoldSpacebarMessage;
import com.camellias.camsrelics.core.network.packets.SpawnAirMessage;
import com.camellias.camsrelics.core.network.packets.SpawnFireMessage;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	
	private static int nextId = 0;
	
	public static int next()
	{
		return nextId++;
	}
	
	public static void init()
	{
		INSTANCE.registerMessage(HoldSpacebarMessage.HoldSpacebarPacketHandler.class, HoldSpacebarMessage.class, next(), Side.SERVER);
		INSTANCE.registerMessage(HoldSpacebarMessage.HoldSpacebarPacketHandler.class, HoldSpacebarMessage.class, next(), Side.CLIENT);
		
		INSTANCE.registerMessage(SpawnAirMessage.SpawnAirPacketHandler.class, SpawnAirMessage.class, next(), Side.SERVER);
		INSTANCE.registerMessage(SpawnAirMessage.SpawnAirPacketHandler.class, SpawnAirMessage.class, next(), Side.CLIENT);
		
		INSTANCE.registerMessage(SpawnFireMessage.SpawnFirePacketHandler.class, SpawnFireMessage.class, next(), Side.SERVER);
		INSTANCE.registerMessage(SpawnFireMessage.SpawnFirePacketHandler.class, SpawnFireMessage.class, next(), Side.CLIENT);
	}
}
