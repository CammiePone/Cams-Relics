package com.camellias.camsrelics;

import java.io.File;

import com.camellias.camsrelics.common.tabs.CreativeTabRelics;
import com.camellias.camsrelics.core.handlers.RegistryHandler;
import com.camellias.camsrelics.core.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(
	modid = Reference.MODID, 
	name = Reference.NAME, 
	version = Reference.VERSION, 
	acceptedMinecraftVersions = Reference.ACCEPTEDVERSIONS, 
	dependencies = Reference.DEPENDENCIES)
public class Main 
{
	public static File config;
	public static boolean thaumcraftLoaded = false;
	
	public static final CreativeTabs RELIC_TAB = new CreativeTabRelics("relics_tab");
	
	@Instance
	public static Main instance;
	
	//Proxy
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	//Initialization
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		if(Loader.isModLoaded("thaumcraft"))
		{
			thaumcraftLoaded = true;
		}
		
		RegistryHandler.clientRegistries(event);
		RegistryHandler.preInitRegistries(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		RegistryHandler.initRegistries();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		System.out.println("Relics checking in!");
	}
	
	@EventHandler
	public static void serverInit(FMLServerStartingEvent event)
	{
		System.out.println("Relics checking in!");
	}
}
