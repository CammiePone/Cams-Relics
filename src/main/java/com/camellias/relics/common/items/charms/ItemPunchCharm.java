package com.camellias.relics.common.items.charms;

import java.util.UUID;

import com.camellias.relics.common.items.ItemBauble;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

public class ItemPunchCharm extends ItemBauble
{
	UUID uuid = UUID.fromString("fefa521d-3ef2-4b4e-8586-58a840839c47");
	
	public ItemPunchCharm(String name)
	{
		super(name);
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) 
	{
		return BaubleType.CHARM;
	}
	
	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player)
	{
		if(!player.world.isRemote)
		{
			Multimap<String, AttributeModifier> attributes = HashMultimap.create();
			
			attributes.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(uuid, "Punch Charm", 4, 0));
			player.getAttributeMap().applyAttributeModifiers(attributes);
		}
	}
	
	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
	{
		if(!player.world.isRemote)
		{
			Multimap<String, AttributeModifier> attributes = HashMultimap.create();
			
			attributes.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(uuid, "Punch Charm", 4, 0));
			player.getAttributeMap().removeAttributeModifiers(attributes);
		}
	}
}
