package com.camellias.relics.core.init;

import java.util.ArrayList;
import java.util.List;

import com.camellias.relics.common.items.charms.ItemAirCharm;
import com.camellias.relics.common.items.charms.ItemEarthCharm;
import com.camellias.relics.common.items.charms.ItemFireCharm;
import com.camellias.relics.common.items.charms.ItemPunchCharm;
import com.camellias.relics.common.items.charms.ItemWaterCharm;

import net.minecraft.item.Item;

public class ModItems 
{
	//-----Item list-----//
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item AIR_CHARM = new ItemAirCharm("air_charm");
	public static final Item EARTH_CHARM = new ItemEarthCharm("earth_charm");
	public static final Item FIRE_CHARM = new ItemFireCharm("fire_charm");
	public static final Item WATER_CHARM = new ItemWaterCharm("water_charm");
	public static final Item PUNCH_CHARM = new ItemPunchCharm("punch_charm");
}
