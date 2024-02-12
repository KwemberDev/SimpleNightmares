package kwemsmod.proxy;

import kwemsmod.ModdedEntityPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerProxy extends CommonProxy {
    @SubscribeEvent
    public void onPlayerSleepInBed(PlayerSleepInBedEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if (player instanceof ModdedEntityPlayer) {
            ModdedEntityPlayer moddedPlayer = (ModdedEntityPlayer) player;
            BlockPos bedPos = event.getPos();
            EntityPlayer.SleepResult result = moddedPlayer.trySleep(bedPos);
            if (result == EntityPlayer.SleepResult.OK) {
                event.setResult(Event.Result.ALLOW);
            } else {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}