package com.camellias.relics.init;

import com.camellias.relics.Main;
import com.camellias.relics.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities
{
	public static void registerEntities()
	{
		
	}
	
	private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), 
				entity, name, id, Main.instance, range, 1, true);
	}
}
