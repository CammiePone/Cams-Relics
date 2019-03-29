package com.camellias.relics.common.items.charms;

import com.camellias.relics.common.items.ItemBauble;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemAirCharm extends ItemBauble
{
	public ItemAirCharm(String name)
	{
		super(name);
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
