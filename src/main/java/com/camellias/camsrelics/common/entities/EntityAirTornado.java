package com.camellias.camsrelics.common.entities;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.camellias.camsrelics.client.particles.TornadoParticle;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityAirTornado extends EntityThrowable
{
	protected EntityPlayer owner;
	private String ownerName;
	
	public EntityAirTornado(World world)
	{
		super(world);
		setSize(1.0F, 2.0F);
	}
	
	public EntityAirTornado(World world, EntityPlayer player)
	{
		super(world, player);
		this.owner = player;
	}
	
	@Override
	protected void onImpact(RayTraceResult result)
	{
		
	}
	
	@Override
	protected void entityInit()
	{
		setEntityInvulnerable(true);
		setNoGravity(true);
	}
	
	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
		
		this.playSound(SoundEvents.ITEM_ELYTRA_FLYING, 2.0F, 1.0F);
		
		if(world.isRemote)
		{
			for(int i = 0; i < 5; i++)
			{
				Random rand = world.rand;
				double positionX = posX + (double)(rand.nextFloat() * width * 2.0F) - (double)width;
				double positionY = posY;
				double positionZ = posZ + (double)(rand.nextFloat() * width * 2.0F) - (double)width;
				
				TornadoParticle tornado = new TornadoParticle(world, positionX, positionY, positionZ, 0, 0.1D, 0);
				Minecraft.getMinecraft().effectRenderer.addEffect(tornado);
			}
		}
		
		if(!world.isRemote)
		{
			List<Entity> list = this.world.<Entity>getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox().grow(0.5D));
			EntityPlayer owner = getOwner();
			
			if(!list.isEmpty())
			{
				for(Entity entity : list)
				{
					if((entity != owner) && !(entity instanceof EntityAirTornado))
					{
						entity.attackEntityFrom(DamageSource.FLY_INTO_WALL, 2F);
						
						Vec3d entityPos = entity.getPositionVector();
						double distanceSq = entityPos.squareDistanceTo(this.getPositionVector());
						double acceleration = Math.max(distanceSq, 0.0001f);
						Vec3d dir = this.getPositionVector().subtract(entityPos).normalize();
						
						dir = new Vec3d(dir.x * acceleration, dir.y * acceleration, dir.z * acceleration);
						
						if(entity instanceof EntityPlayer)
						{
							continue;
						}
						
						entity.addVelocity(dir.x, dir.y, dir.z);
					}
				}
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
