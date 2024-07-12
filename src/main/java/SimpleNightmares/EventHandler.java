package SimpleNightmares;

import SimpleNightmares.commands.SleepPercentageCommand;
import SimpleNightmares.nightmares.NightEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.entity.player.EntityPlayer;
import SimpleNightmares.config.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static SimpleNightmares.ambush.Ambushes.createAmbush;
import static SimpleNightmares.config.Config.*;

@EventBusSubscriber(modid = SimpleNightmares.MODID)
public class EventHandler {
    private static final SleepPercentageCommand sleepCommand = new SleepPercentageCommand();
    public static final Map<String, Boolean> sleepingLastTick = new HashMap<>();
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
            boolean isBedExposedToSky = true; // Default to true if needsOpenSky is false
            if (needsOpenSky) {
                BlockPos bedPos = playerMP.bedLocation;
                isBedExposedToSky = playerMP.world.canSeeSky(bedPos);
            }
            // chance to create an ambush and wake the player up, if the bed is exposed to the sky or needsOpenSky is false
            if (rand.nextFloat() < ambushChance && enableAmbush && isBedExposedToSky) {
                // Create and post the NightEvent
                NightEvent nightEvent = new NightEvent(playerMP, false, true);
                MinecraftForge.EVENT_BUS.post(nightEvent);

                // Only create the ambush if it wasn't cancelled by another mod
                if (nightEvent.isAmbush()) {
                    createAmbush(playerMP);
                    playerMP.wakeUpPlayer(false, false, false);
                }

            } else if (playerMP.world.getGameRules().getBoolean("doDaylightCycle")) {
                sleepCommand.onPlayerSleep(server, playerName);
            }
        }
        sleepingLastTick.put(playerName, isSleeping);
    }
}

