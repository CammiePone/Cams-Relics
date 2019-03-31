package com.camellias.camsrelics.client.render.entities;

import com.camellias.camsrelics.common.entities.EntityAirTornado;
import com.camellias.camsrelics.common.entities.EntityFireEmber;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFireEmber extends Render<EntityFireEmber>
{
	public RenderFireEmber(RenderManager renderManager)
	{
		super(renderManager);
	}
	
	@Override
	public ResourceLocation getEntityTexture(EntityFireEmber entity)
	{
		return null;
	}
}
