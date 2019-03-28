package com.camellias.relics.common.items.charms;

import com.camellias.relics.common.items.ItemBauble;
import com.camellias.relics.network.NetworkHandler;
import com.camellias.relics.network.packets.HoldLeftClickMessage;
import com.camellias.relics.network.packets.HoldRightClickMessage;
import com.camellias.relics.network.packets.SpawnAirBlastMessage;

import baubles.api.BaubleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemAirCharm extends ItemBauble
{
	public ItemAirCharm(String name)
	{
		super(name);
	}
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity)
	{
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			World world = player.world;
			
			if(player.isAirBorne)
			{
				if(world.isRemote)
				{
					GameSettings settings = new GameSettings();
					GuiScreen gui = Minecraft.getMinecraft().currentScreen;
					
					if(gui == null)
					{
						if(settings.isKeyDown(settings.keyBindUseItem) && player.getCooldownTracker().getCooldown(this, 0) == 0)
						{
							player.getCooldownTracker().setCooldown(this, 20);
							
							NetworkHandler.INSTANCE.sendToServer(new SpawnAirBlastMessage(player));
						}
						if(settings.isKeyDown(settings.keyBindAttack) && player.getCooldownTracker().getCooldown(this, 0) == 0)
						{
							player.getCooldownTracker().setCooldown(this, 20);
						}
					}
				}
			}
			if(player.onGround)
			{
				if(world.isRemote)
				{
					GameSettings settings = new GameSettings();
					GuiScreen gui = Minecraft.getMinecraft().currentScreen;
					
					if(gui == null)
					{
						if(settings.isKeyDown(settings.keyBindUseItem) && player.getCooldownTracker().getCooldown(this, 0) == 0)
						{
							player.getCooldownTracker().setCooldown(this, 20);
						}
						if(settings.isKeyDown(settings.keyBindAttack) && player.getCooldownTracker().getCooldown(this, 0) == 0)
						{
							player.getCooldownTracker().setCooldown(this, 20);
						}
					}
				}
			}
		}
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) 
	{
		return BaubleType.CHARM;
	}
	
	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
	{
		if(player instanceof EntityPlayer)
		{
			player.setNoGravity(false);
		}
	}
	
	public static boolean isUsable(ItemStack itemstack)
	{
		return itemstack.getItemDamage() < itemstack.getMaxDamage() - 1;
	}
}
