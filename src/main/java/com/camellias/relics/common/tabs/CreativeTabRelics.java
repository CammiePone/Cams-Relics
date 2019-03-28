package com.camellias.relics.common.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CreativeTabRelics extends CreativeTabs
{
	public CreativeTabRelics(String name)
	{
		super(name);
	}
	
	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(Items.AIR);
	}
}
