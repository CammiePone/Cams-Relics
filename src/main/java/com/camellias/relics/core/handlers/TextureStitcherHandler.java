package com.camellias.relics.core.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class TextureStitcherHandler
{
	@SubscribeEvent
	public static void stitcherEventPre(TextureStitchEvent.Pre event)
	{
		ResourceLocation TEXTURE = new ResourceLocation("relics:textures/particles/tornado");
		event.getMap().registerSprite(TEXTURE);
	}
}
