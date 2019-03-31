package com.camellias.camsrelics.core.handlers;

import com.camellias.camsrelics.client.render.entities.RenderAirBlast;
import com.camellias.camsrelics.client.render.entities.RenderAirTornado;
import com.camellias.camsrelics.client.render.entities.RenderFireEmber;
import com.camellias.camsrelics.common.entities.EntityAirBlast;
import com.camellias.camsrelics.common.entities.EntityAirTornado;
import com.camellias.camsrelics.common.entities.EntityFireEmber;
import com.camellias.camsrelics.core.init.ModEntities;
import com.camellias.camsrelics.core.init.ModItems;
import com.camellias.camsrelics.core.network.NetworkHandler;
import com.camellias.camsrelics.core.util.IHasModel;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class RegistryHandler 
{	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : ModItems.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
	}
	
	@SubscribeEvent
	public static void registerEntities(Register<EntityEntry> event)
	{
		for(EntityEntry entry : ModEntities.REGISTRY)
		{
			event.getRegistry().register(entry);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void clientRegistries(FMLPreInitializationEvent event)
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityAirTornado.class, RenderAirTornado::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAirBlast.class, RenderAirBlast::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFireEmber.class, RenderFireEmber::new);
	}
	
	public static void preInitRegistries(FMLPreInitializationEvent event)
	{
		NetworkHandler.init();
		ConfigHandler.registerConfig(event);
	}
	
	public static void initRegistries()
	{
		
	}
}
