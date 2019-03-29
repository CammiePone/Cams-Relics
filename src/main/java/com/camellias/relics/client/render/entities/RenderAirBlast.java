package com.camellias.relics.client.render.entities;

import com.camellias.relics.common.entities.EntityAirBlast;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderAirBlast extends Render<EntityAirBlast>
{
	public RenderAirBlast(RenderManager renderManager)
	{
		super(renderManager);
	}
	
	@Override
	public ResourceLocation getEntityTexture(EntityAirBlast entity)
	{
		return null;
	}
}
