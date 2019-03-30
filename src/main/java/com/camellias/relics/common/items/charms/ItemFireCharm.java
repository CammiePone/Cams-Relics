package com.camellias.relics.common.items.charms;

import com.camellias.relics.common.items.ItemBauble;

import baubles.api.BaubleType;
import net.minecraft.item.ItemStack;

public class ItemFireCharm extends ItemBauble
{
	public ItemFireCharm(String name)
	{
		super(name);
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) 
	{
		return BaubleType.CHARM;
	}
}
