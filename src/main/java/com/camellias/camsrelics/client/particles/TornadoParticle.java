package com.camellias.camsrelics.client.particles;

import com.camellias.camsrelics.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TornadoParticle extends Particle
{
	private final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/particles/tornado");
	private double realX = posX, realZ = posZ;
	private static final double rotationSpeed = 0.8D;
	
	public TornadoParticle(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ)
	{
		super(world, x, y, z, velocityX, velocityY, velocityZ);
		
		particleMaxAge = 20;
		
		final float ALPHA_VALUE = 0.99F;
		this.particleAlpha = ALPHA_VALUE;
		
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;
		
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(TEXTURE.toString());
		setParticleTexture(sprite);
	}
	
	@Override
	public int getFXLayer()
	{
		return 1;
	}
	
	@Override
	public int getBrightnessForRender(float partialTicks)
	{
		final int FULL_BRIGHTNESS_VALUE = 0xf000f0;
		return FULL_BRIGHTNESS_VALUE;
	}
	
	@Override
	public boolean shouldDisableDepth()
	{
		return false;
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		double radius = 0.075D * particleAge;
		
		posX = realX + radius * Math.sin(particleAge * rotationSpeed);
		posY += 0.5D;
		posZ = realZ + radius * Math.cos(particleAge * rotationSpeed);
		
		if(this.particleMaxAge-- <= 0)
		{
			this.setExpired();
		}
	}
	
	@Override
	public void renderParticle(BufferBuilder bufferBuilder, Entity entity, float partialTick,
			float edgeLRdirectionX, float edgeUDdirectionY, float edgeLRdirectionZ,
			float edgeUDdirectionX, float edgeUDdirectionZ)
	{
		double minU = this.particleTexture.getMinU();
		double maxU = this.particleTexture.getMaxU();
		double minV = this.particleTexture.getMinV();
		double maxV = this.particleTexture.getMaxV();
		
		double scale = 0.1F * this.particleScale;
		final double scaleLR = scale;
		final double scaleUD = scale;
		double x = this.prevPosX + (this.posX - this.prevPosX) * partialTick - interpPosX;
		double y = this.prevPosY + (this.posY - this.prevPosY) * partialTick - interpPosY;
		double z = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTick - interpPosZ;
		
		int combinedBrightness = this.getBrightnessForRender(partialTick);
		int skyLightTimes16 = combinedBrightness >> 16 & 65535;
		int blockLightTimes16 = combinedBrightness & 65535;
		
		bufferBuilder.pos(x - edgeLRdirectionX * scaleLR - edgeUDdirectionX * scaleUD,
				y - edgeUDdirectionY * scaleUD,
				z - edgeLRdirectionZ * scaleLR - edgeUDdirectionZ * scaleUD)
				.tex(maxU, maxV)
				.color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
				.lightmap(skyLightTimes16, blockLightTimes16)
				.endVertex();
		
		bufferBuilder.pos(x - edgeLRdirectionX * scaleLR + edgeUDdirectionX * scaleUD,
				y + edgeUDdirectionY * scaleUD,
				z - edgeLRdirectionZ * scaleLR + edgeUDdirectionZ * scaleUD)
				.tex(maxU, minV)
				.color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
				.lightmap(skyLightTimes16, blockLightTimes16)
				.endVertex();
		
		bufferBuilder.pos(x + edgeLRdirectionX * scaleLR + edgeUDdirectionX * scaleUD,
				y + edgeUDdirectionY * scaleUD,
				z + edgeLRdirectionZ * scaleLR + edgeUDdirectionZ * scaleUD)
				.tex(minU, minV)
				.color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
				.lightmap(skyLightTimes16, blockLightTimes16)
				.endVertex();
		
		bufferBuilder.pos(x + edgeLRdirectionX * scaleLR - edgeUDdirectionX * scaleUD,
				y - edgeUDdirectionY * scaleUD,
				z + edgeLRdirectionZ * scaleLR - edgeUDdirectionZ * scaleUD)
				.tex(minU, maxV)
				.color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
				.lightmap(skyLightTimes16, blockLightTimes16)
				.endVertex();
	}
}
