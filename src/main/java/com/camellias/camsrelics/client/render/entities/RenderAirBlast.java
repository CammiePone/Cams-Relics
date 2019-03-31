package com.camellias.camsrelics.client.render.entities;

import com.camellias.camsrelics.common.entities.EntityAirBlast;
import com.camellias.camsrelics.common.entities.EntityAirTornado;

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
