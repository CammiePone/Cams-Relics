package com.camellias.camsrelics.common.items.charms;

import com.camellias.camsrelics.common.items.ItemBauble;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class ItemFireCharm extends ItemBauble
{
	public ItemFireCharm(String name)
	{
		super(name);
	}
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player)
	{
		if(!player.world.isRemote)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 10, 0, true, false));
		}
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) 
	{
		return BaubleType.CHARM;
	}
}
