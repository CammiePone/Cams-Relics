package com.camellias.camsrelics.common.entities;

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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityFireEmber extends EntityThrowable
{
	protected EntityPlayer owner;
	private String ownerName;
	
	public EntityFireEmber(World world)
	{
		super(world);
		setSize(0.5F, 0.5F);
	}
	
	public EntityFireEmber(World world, EntityPlayer player)
	{
		super(world, player);
		this.owner = player;
	}
	
	@Override
	protected void onImpact(RayTraceResult result)
	{
		if(!world.isRemote)
		{
			if(result.typeOfHit == Type.ENTITY)
			{
				Entity entity = result.entityHit;
				EntityLivingBase owner = getOwner();
				
				if(entity != owner)
				{
					entity.attackEntityFrom(DamageSource.ON_FIRE, 4F);
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
	}
	
	@Override
	public void onUpdate()
	{
		super.onEntityUpdate();
		
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
		
		if((this.ownerName == null || this.ownerName.isEmpty()) && this.owner instanceof EntityPlayer)
		{
			this.ownerName = this.owner.getName();
		}
		
		tag.setString("ownerName", this.ownerName == null ? "" : this.ownerName);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);
		
		this.owner = null;
		this.ownerName = tag.getString("ownerName");
		
		if(this.ownerName != null && this.ownerName.isEmpty())
		{
			this.ownerName = null;
		}
		
		this.owner = this.getOwner();
	}
	
	public EntityPlayer getOwner()
	{
		if(this.owner == null && this.ownerName != null && !this.ownerName.isEmpty())
		{
			this.owner = this.world.getPlayerEntityByName(this.ownerName);
			
			if(this.owner == null && this.world instanceof WorldServer)
			{
				try
				{
					Entity entity = ((WorldServer)this.world).getEntityFromUuid(UUID.fromString(this.ownerName));
					
					if(entity instanceof EntityPlayer)
					{
						this.owner = (EntityPlayer)entity;
					}
				}
				catch(Throwable var2)
				{
					this.owner = null;
				}
			}
		}
		
		return this.owner;
	}
}
