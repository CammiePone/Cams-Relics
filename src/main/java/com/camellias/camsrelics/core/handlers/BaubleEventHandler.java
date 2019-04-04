package com.camellias.camsrelics.core.handlers;

import java.util.Random;

import com.camellias.camsrelics.client.particles.TornadoParticle;
import com.camellias.camsrelics.core.init.ModItems;
import com.camellias.camsrelics.core.network.NetworkHandler;
import com.camellias.camsrelics.core.network.packets.HoldSpacebarMessage;
import com.camellias.camsrelics.core.network.packets.SpawnAirMessage;
import com.camellias.camsrelics.core.network.packets.SpawnFireMessage;

import baubles.api.BaublesApi;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@EventBusSubscriber
public class BaubleEventHandler
{
	public static int baubleTimer = 0;
	
	@SubscribeEvent
	public static void onPlayerAttack(LivingHurtEvent event)
	{
		if((event.getSource().getTrueSource() instanceof EntityPlayer) && (event.getEntityLiving() instanceof EntityLivingBase))
		{
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			EntityLivingBase target = (EntityLivingBase) event.getEntityLiving();
			
			if(BaublesApi.isBaubleEquipped(player, ModItems.PUNCH_CHARM) > -1)
			{
				if(player.getHeldItemMainhand().isEmpty())
				{
					target.hurtResistantTime = 0;
					
					if(player.isAirBorne)
					{
						float amount = event.getAmount();
						
						event.setAmount(amount * 4);
					}
				}
				else
				{
					event.setAmount(1);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent event)
	{
		if(event.phase == Phase.END)
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			GameSettings settings = Minecraft.getMinecraft().gameSettings;
			GuiScreen gui = Minecraft.getMinecraft().currentScreen;
			KeyBinding rclick = settings.keyBindUseItem;
			baubleTimer++;
			
			/**
			 * Bauble right clicks
			 */
			if(settings.isKeyDown(rclick) && gui == null)
			{
				if(player.getHeldItemMainhand().isEmpty() && player.getHeldItemOffhand().isEmpty())
				{
					/**
					 * Charm of Typhoons
					 */
					if(BaublesApi.isBaubleEquipped(player, ModItems.AIR_CHARM) > -1)
					{
						if(baubleTimer >= 20)
						{
							NetworkHandler.INSTANCE.sendToServer(new SpawnAirMessage(player));
							baubleTimer = 0;
						}
					}
					
					/**
					 * Charm of (TBD)
					 */
					if(BaublesApi.isBaubleEquipped(player, ModItems.FIRE_CHARM) > -1)
					{
						if(baubleTimer >= 20)
						{
							NetworkHandler.INSTANCE.sendToServer(new SpawnFireMessage(player));
							baubleTimer = 0;
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;
		
		/**
		 * Air charm floatiness
		 */
		if(BaublesApi.isBaubleEquipped(player, ModItems.AIR_CHARM) > -1)
		{
			World world = player.world;
			BlockPos pos = player.getPosition();
			BlockPos blockpos = pos.add(0, -1, 0);
			IBlockState state = player.world.getBlockState(blockpos);
			
			if(!player.isElytraFlying() || !player.capabilities.isFlying)
			{
				if(state.getBlock().isPassable(player.world, blockpos))
				{
					if(world.isRemote)
					{
						GameSettings settings = Minecraft.getMinecraft().gameSettings;
						KeyBinding jump = settings.keyBindJump;
						GuiScreen gui = Minecraft.getMinecraft().currentScreen;
						
						if(GameSettings.isKeyDown(jump) && gui == null)
						{
							NetworkHandler.INSTANCE.sendToAll(new HoldSpacebarMessage(player));
						}
					}
					
					if(player.isSneaking())
					{
						player.motionY = -0.25D;
					}
					if((player.motionY <= 0.05D && player.motionY >= -0.05D))
					{
						player.motionY = player.motionY / 1.025D;
					}
					
					player.setNoGravity(true);
					player.jumpMovementFactor *= 1.5F;
					player.fallDistance = 0.0F;
					
					if(world.isRemote)
					{
						for(int i = 0; i < 5; i++)
						{
							Random rand = world.rand;
							double positionX = player.posX + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width;
							double positionY = player.posY - 0.5D;
							double positionZ = player.posZ + (double)(rand.nextFloat() * player.width * 2.0F) - (double)player.width;
							
							TornadoParticle tornado = new TornadoParticle(world, positionX, positionY, positionZ, 0, 0.05D, 0);
							Minecraft.getMinecraft().effectRenderer.addEffect(tornado);
						}
					}
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
