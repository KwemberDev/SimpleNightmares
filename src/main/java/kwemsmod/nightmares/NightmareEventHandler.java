package kwemsmod.nightmares;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

import static kwemsmod.config.Config.enableNightmares;
import static kwemsmod.config.Config.nightmareChance;

public class NightmareEventHandler {
    private static final Random rand = new Random();

    @SubscribeEvent
    public void onPlayerWakeUp(PlayerWakeUpEvent event) {
        if (enableNightmares) {
            // Check if the event was triggered by the night being skipped, shouldSetSpawn in PlayerWakeUpEvent is only true if night is skipped.
            if (event.shouldSetSpawn()) { // remove if it aint work on sleeping bags in rl
                if (event.getEntity() instanceof EntityPlayerMP) {
                    EntityPlayerMP player = (EntityPlayerMP) event.getEntity();

                    // 20% chance to apply the nightmare effect
                    if ((rand.nextFloat() * 100) < nightmareChance) {
                        NightmareEffect.applyRandomNightmare(player);
                    }
                }
            }
        }
    }
}

