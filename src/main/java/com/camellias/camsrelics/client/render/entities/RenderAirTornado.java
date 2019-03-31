package com.camellias.camsrelics.client.render.entities;

import com.camellias.camsrelics.common.entities.EntityAirTornado;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderAirTornado extends Render<EntityAirTornado>
{
	public RenderAirTornado(RenderManager renderManager)
	{
		super(renderManager);
	}
	
	@Override
	public ResourceLocation getEntityTexture(EntityAirTornado entity)
	{
		return null;
	}
}
