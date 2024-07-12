package SimpleNightmares.nightmares;

import SimpleNightmares.ModItems;
import SimpleNightmares.blocks.dreamcatchers.DreamCatcher;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

import static SimpleNightmares.EventHandler.sleepingLastTick;
import static SimpleNightmares.config.Config.*;


public class NightmareEventHandler {
    private static final Random rand = new Random();

    @SubscribeEvent
    public void onPlayerWakeUp(PlayerWakeUpEvent event) {
        if (enableNightmares) {

            if (event.getEntity() instanceof EntityPlayerMP) {
                EntityPlayerMP player = (EntityPlayerMP) event.getEntity();

                ((WorldServer) player.world).addScheduledTask(() -> {

                    long timeOfDay = player.world.getWorldTime() % 24000;

                    if (timeOfDay < 13000) {
                        if ((rand.nextFloat() * 100) < nightmareChance) {
                            // Check for DreamCatcher block in area
                            boolean isDreamCatcherNearby = false;
                            for (int x = -15; x <= 15; x++) {
                                for (int y = -15; y <= 15; y++) {
                                    for (int z = -15; z <= 15; z++) {
                                        BlockPos pos = player.getPosition().add(x, y, z);
                                        if (player.world.getBlockState(pos).getBlock() instanceof DreamCatcher) {
                                            isDreamCatcherNearby = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            // Apply effect based on presence of DreamCatcher
                            if (isDreamCatcherNearby) {
                                // Apply buff, add custom status effect later
                                player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("simplenightmares:moonsblessing"), 12000, 0));
                            } else {
                                // Create and post the NightmareEvent
                                NightEvent nightEvent = new NightEvent(player, true, false);
                                MinecraftForge.EVENT_BUS.post(nightEvent);

                                // Only apply the nightmare if it wasn't cancelled by another mod
                                if (nightEvent.isNightmare()) {
                                    NightmareEffect.applyRandomNightmare(player);
                                }
                            }
                        }
                    }
                });
            }
        }
    }
}




