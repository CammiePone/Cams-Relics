package com.camellias.relics.handlers;

import java.util.Random;

import com.camellias.relics.init.ModItems;
import com.camellias.relics.network.NetworkHandler;
import com.camellias.relics.network.packets.HoldLeftClickMessage;
import com.camellias.relics.network.packets.HoldRightClickMessage;
import com.camellias.relics.network.packets.HoldSpacebarMessage;

import baubles.api.BaublesApi;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@EventBusSubscriber
public class BaubleEventHandler
{
	@SubscribeEvent
	public static void onPlayerAttack(AttackEntityEvent event)
	{
		if((event.getEntityLiving() instanceof EntityPlayer) && (event.getTarget() instanceof EntityLivingBase))
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			EntityLivingBase target = (EntityLivingBase) event.getTarget();
			
			if((BaublesApi.isBaubleEquipped(player, ModItems.PUNCH_CHARM) > -1) && (player.getHeldItemMainhand().isEmpty()))
			{
				target.hurtResistantTime = 0;
			}
		}
	}
	
	
	
	
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		if(BaublesApi.isBaubleEquipped(event.player, ModItems.AIR_CHARM) > -1)
		{
			EntityPlayer player = event.player;
			World world = player.world;
			BlockPos pos = player.getPosition();
			BlockPos blockpos = pos.add(0, -1, 0);
			IBlockState state = player.world.getBlockState(blockpos);
			Random rand = new Random();
			double d2 = rand.nextGaussian() * 0.02D;
            double d0 = rand.nextGaussian() * -0.02D;
            double d1 = rand.nextGaussian() * 0.02D;
			
			if(!player.isElytraFlying() || !player.capabilities.isFlying)
			{
				if(state.getBlock().isPassable(player.world, blockpos))
				{
					if(world.isRemote)
					{
						GameSettings settings = new GameSettings();
						GuiScreen gui = Minecraft.getMinecraft().currentScreen;
						
						if(settings.isKeyDown(settings.keyBindJump) && !(gui instanceof GuiScreen))
						{
							player.motionY = 0.25D;
							NetworkHandler.INSTANCE.sendToServer(new HoldSpacebarMessage(player));
						}
						if(player.isSneaking())
						{
							player.motionY = -0.25D;
						}
						if((player.motionY <= 0.2D && player.motionY >= -0.2D))
						{
							player.motionY = player.motionY / 1.025D;
						}
					}
					
					player.setNoGravity(true);
					player.jumpMovementFactor *= 1.75F;
					player.fallDistance = 0.0F;
					player.world.spawnParticle(EnumParticleTypes.CLOUD, 
							player.posX + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width,
							player.posY,
							player.posZ + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width,
							d2, d0, d1);
				}
				else
				{
					player.setNoGravity(false);
				}
			}
			else
			{
				player.setNoGravity(false);
			}
		}
	}
}
