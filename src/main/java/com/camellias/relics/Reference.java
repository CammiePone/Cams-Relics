package com.camellias.relics;

public class Reference 
{
	//Essentials
	public static final String MODID = "relics";
	public static final String NAME = "Relics";
	public static final String VERSION = "0.1";
	public static final String ACCEPTEDVERSIONS = "[1.12.2]";
	public static final String DEPENDENCIES = "required-after:forge@[14.23.5.2795,];" + "required-after:baubles@[1.5.2,];";
		
	//Proxies
	public static final String CLIENT_PROXY_CLASS = "com.camellias.relics.proxy.ClientProxy";
	public static final String COMMON_PROXY_CLASS = "com.camellias.relics.proxy.CommonProxy";
}
