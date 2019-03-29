package com.camellias.relics.common.entities;

import java.util.Random;
import java.util.UUID;

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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class EntityAirBlast extends EntityThrowable
{
	private static final DataParameter<String> THROWER = EntityDataManager.createKey(EntityAirBlast.class, DataSerializers.STRING);
	
	public EntityAirBlast(World world)
	{
		super(world);
	}
	
	public EntityAirBlast(World world, EntityPlayer player)
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
					boolean flag = entity.attackEntityFrom(DamageSource.FLY_INTO_WALL, 2F);
					
					entity.setPositionAndUpdate(this.posX, this.posY + 0.5, this.posZ);
				}
			}
		}
	}
	
	@Override
	protected void entityInit()
	{
		setEntityInvulnerable(true);
		setNoGravity(true);
		setSize(1.0F, 2.0F);
		dataManager.register(THROWER, "");
	}
	
	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
		setSize(1.0F, 2.0F);
		
		Random rand = new Random();
		double d0 = rand.nextGaussian() * -0.1D;
		double d1 = rand.nextGaussian() * 0.1D;
		
		for(int i = 0; i < 8; i++)
		{
			world.spawnParticle(EnumParticleTypes.CLOUD, 
					posX + (double)(rand.nextFloat() * width * 2.0F) - (double)width,
					posY + (double)(rand.nextFloat() * height * 2.0F) - (double)height,
					posZ + (double)(rand.nextFloat() * width * 2.0F) - (double)width,
					motionX * d1, motionY * d0, motionZ * d1);
		}
		
		if(ticksExisted < 100)
		{
			motionY += 0.0015;
		}
		
		if(ticksExisted > 100)
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
