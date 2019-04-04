package com.camellias.camsrelics.common.entities;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityAirBlast extends Entity
{
	protected EntityPlayer owner;
	private String ownerName;
	
	public EntityAirBlast(World world)
	{
		super(world);
		setSize(0.5F, 0.5F);
	}
	
	public EntityAirBlast(World world, EntityPlayer player)
	{
		super(world);
		this.owner = player;
	}
	
	@Override
	public boolean canBePushed()
	{
		return false;
	}
	
	@Override
	public void entityInit()
	{
		setEntityInvulnerable(true);
		setNoGravity(true);
	}
	
	@Override
	public void onUpdate()
	{
		super.onEntityUpdate();
		
		this.playSound(SoundEvents.ITEM_ELYTRA_FLYING, 5.0F, 1.0F);
		
		if(!world.isRemote)
		{
			noClip = false;
			
			List<Entity> list = this.world.<Entity>getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox().grow(3D));
			EntityPlayer owner = getOwner();
			
			if(!list.isEmpty())
			{
				for(Entity entity : list)
				{
					if(entity != owner && entity != this)
					{
						Vec3d entityPos = entity.getPositionVector();
						double distanceSq = entityPos.squareDistanceTo(this.getPositionVector());
						double acceleration = Math.max(distanceSq / 10, 0.0001f);
						Vec3d dir = this.getPositionVector().add(entityPos).normalize();
						dir = new Vec3d(dir.x * acceleration, dir.y * (acceleration * 3), dir.z * acceleration);
						
						entity.addVelocity(dir.x, dir.y, dir.z);
					}
				}
			}
			
			if(ticksExisted > 5)
			{
				setDead();
			}
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		if((this.ownerName == null || this.ownerName.isEmpty()) && this.owner instanceof EntityPlayer)
		{
			this.ownerName = this.owner.getName();
		}
		
		tag.setString("ownerName", this.ownerName == null ? "" : this.ownerName);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
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
