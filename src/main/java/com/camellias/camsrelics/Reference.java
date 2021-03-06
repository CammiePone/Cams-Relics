package com.camellias.camsrelics;

public class Reference 
{
	//Essentials
	public static final String MODID = "camsrelics";
	public static final String NAME = "Cam's Relics";
	public static final String VERSION = "0.1";
	public static final String ACCEPTEDVERSIONS = "[1.12.2]";
	public static final String DEPENDENCIES = "required-after:forge@[14.23.5.2795,];" + "required-after:baubles@[1.5.2,];";
		
	//Proxies
	public static final String CLIENT_PROXY_CLASS = "com.camellias.camsrelics.core.proxy.ClientProxy";
	public static final String COMMON_PROXY_CLASS = "com.camellias.camsrelics.core.proxy.CommonProxy";
}
