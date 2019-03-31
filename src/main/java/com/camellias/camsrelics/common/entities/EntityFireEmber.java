package com.camellias.camsrelics.common.entities;

import java.util.Random;
import java.util.UUID;

import com.camellias.camsrelics.client.particles.TornadoParticle;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class EntityFireEmber extends EntityThrowable
{
	private static final DataParameter<String> THROWER = EntityDataManager.createKey(EntityAirTornado.class, DataSerializers.STRING);
	
	public EntityFireEmber(World world)
	{
		super(world);
	}
	
	public EntityFireEmber(World world, EntityPlayer player)
	{
		super(world, player);
	}
	
	@Override
	protected void onImpact(RayTraceResult result)
	{
		if(!world.isRemote)
		{
			if(result.typeOfHit == Type.ENTITY)
			{
				Entity entity = result.entityHit;
				EntityLivingBase thrower = getThrower();
				
				if(entity != thrower)
				{
					entity.attackEntityFrom(DamageSource.IN_FIRE, 4F);
					entity.setFire(10);
				}
			}
		}
	}
	
	@Override
	protected void entityInit()
	{
		setEntityInvulnerable(true);
		setNoGravity(true);
		setSize(0.5F, 0.5F);
		dataManager.register(THROWER, "");
	}
	
	@Override
	public void onUpdate()
	{
		super.onEntityUpdate();
		setSize(0.5F, 0.5F);
		
		if(world.isRemote)
		{
			for(int i = 0; i < 5; i++)
			{
				Random rand = world.rand;
				double positionX = posX + (double)(rand.nextFloat() * width * 2.0F) - (double)width;
				double positionY = posY + (double)(rand.nextFloat() * height * 2.0F) - (double)height;
				double positionZ = posZ + (double)(rand.nextFloat() * width * 2.0F) - (double)width;
				
				world.spawnParticle(EnumParticleTypes.FLAME, positionX, positionY, positionZ, 0, 0, 0);
			}
		}
		
		if(ticksExisted > 200)
		{
			setDead();
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);
		tag.setString("thrower", dataManager.get(THROWER));
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);
		tag.setString("thrower", dataManager.get(THROWER));
	}
	
	public EntityLivingBase getThrower()
	{
		String uuid = dataManager.get(THROWER);
		
		if(uuid == null || uuid.isEmpty())
		{
			return null;
		}
		
		EntityLivingBase player = world.getPlayerEntityByUUID(UUID.fromString(uuid));
		
		if(player != null)
		{
			return player;
		}
		
		for(Entity entity : world.getLoadedEntityList())
		{
			if(entity instanceof EntityLivingBase && uuid.equals(entity.getUniqueID().toString()))
			{
				return (EntityLivingBase) entity;
			}
		}
		
		return null;
	}
}
