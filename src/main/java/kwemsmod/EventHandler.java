package kwemsmod;

import kwemsmod.commands.SleepPercentageCommand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.entity.player.EntityPlayer;
import kwemsmod.config.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static kwemsmod.ambush.Ambushes.createAmbush;
import static kwemsmod.config.Config.ambushChance;
import static kwemsmod.config.Config.enableAmbush;

@EventBusSubscriber(modid = KwemsMod.MODID)
public class EventHandler {
    private static final SleepPercentageCommand sleepCommand = new SleepPercentageCommand();
    private static final Map<String, Boolean> sleepingLastTick = new HashMap<>();
    private static final Random rand = new Random();

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        handleSleepingPosition(event);
        handleSleepCommand(event);
    }

    private static void handleSleepingPosition(PlayerTickEvent event) {
        if (event.phase != PlayerTickEvent.Phase.END) return;

        EntityPlayer player = event.player;
        if (player.isPlayerSleeping()) {
            // Get the bed's block state
            IBlockState bedBlockState = player.world.getBlockState(player.bedLocation);

            // Check if the bed is a custom bed from your mod
            if (!Config.customBedBlocks.contains(bedBlockState.getBlock().getRegistryName().toString())) return;

            // Get the bed's bounding box
            AxisAlignedBB bedBoundingBox = bedBlockState.getBoundingBox(player.world, player.bedLocation);

            // Calculate the desired Y position
            double bedTop = bedBoundingBox.maxY + player.bedLocation.getY();
            double desiredY = bedTop+0.125D;

            // Set the player's position
            player.setPosition(player.posX, desiredY, player.posZ);
        }
    }


    private static void handleSleepCommand(PlayerTickEvent event) {
        if (!(event.player instanceof EntityPlayerMP)) {
            return;
        }

        EntityPlayerMP playerMP = (EntityPlayerMP) event.player;
        MinecraftServer server = playerMP.getServer();

        String playerName = playerMP.getName();
        boolean wasSleeping = sleepingLastTick.getOrDefault(playerName, false);
        boolean isSleeping = playerMP.isPlayerFullyAsleep();

        if (isSleeping && !wasSleeping) {
            // 10% chance to create an ambush and wake the player up
            if (rand.nextFloat() < ambushChance && enableAmbush) {
                createAmbush(playerMP);
                playerMP.wakeUpPlayer(false, false, false);
            } else if (playerMP.world.getGameRules().getBoolean("doDaylightCycle")) {
                sleepCommand.onPlayerSleep(server, playerName);
            }
        }

        sleepingLastTick.put(playerName, isSleeping);
    }
}

