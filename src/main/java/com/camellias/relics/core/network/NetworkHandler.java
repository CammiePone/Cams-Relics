package com.camellias.relics.core.network;

import com.camellias.relics.Reference;
import com.camellias.relics.core.network.packets.HoldSpacebarMessage;
import com.camellias.relics.core.network.packets.SpawnAirBlastMessage;

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
		
		INSTANCE.registerMessage(SpawnAirBlastMessage.SpawnAirBlastPacketHandler.class, SpawnAirBlastMessage.class, next(), Side.SERVER);
		INSTANCE.registerMessage(SpawnAirBlastMessage.SpawnAirBlastPacketHandler.class, SpawnAirBlastMessage.class, next(), Side.CLIENT);
	}
}
