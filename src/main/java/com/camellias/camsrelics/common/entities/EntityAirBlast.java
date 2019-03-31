package com.camellias.camsrelics.common.entities;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityAirBlast extends Entity
{
	private static final DataParameter<String> THROWER = EntityDataManager.createKey(EntityAirTornado.class, DataSerializers.STRING);
	
	public EntityAirBlast(World world)
	{
		super(world);
	}
	
	public EntityAirBlast(World world, EntityPlayer player)
	{
		super(world);
		player = (EntityPlayer) getThrower();
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
		setSize(1.0F, 0.5F);
		dataManager.register(THROWER, "");
	}
	
	@Override
	public void onUpdate()
	{
		super.onEntityUpdate();
		noClip = true;
		setSize(0.25F * ticksExisted, 0.5F);
		
		List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox());
		EntityLivingBase owner = getThrower();
		
		if(!list.isEmpty())
		{
			for(EntityLivingBase entity : list)
			{
				if(entity != owner)
				{
					entity.setVelocity(entity.motionX * -1.5F, entity.motionY + 0.8F, entity.motionZ * -1.5F);
				}
			}
		}
		
		if(ticksExisted > 40)
		{
			setDead();
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setString("thrower", dataManager.get(THROWER));
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
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
