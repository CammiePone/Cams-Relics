package com.camellias.camsrelics.common.items.charms;

import com.camellias.camsrelics.common.items.ItemBauble;

import baubles.api.BaubleType;
import net.minecraft.item.ItemStack;

public class ItemEarthCharm extends ItemBauble
{
	public ItemEarthCharm(String name)
	{
		super(name);
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) 
	{
		return BaubleType.CHARM;
	}
}
