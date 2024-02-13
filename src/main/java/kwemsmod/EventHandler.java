package kwemsmod;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.entity.player.EntityPlayer;

@EventBusSubscriber(modid = KwemsMod.MODID)
public class EventHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        if (event.phase != PlayerTickEvent.Phase.END) return;

        EntityPlayer player = event.player;
        if (player.isPlayerSleeping()) {
            // Get the bed's bounding box
            AxisAlignedBB bedBoundingBox = player.world.getBlockState(player.bedLocation).getBoundingBox(player.world, player.bedLocation);

            // Calculate the desired Y position
            double bedTop = bedBoundingBox.maxY + player.bedLocation.getY();
            double desiredY = bedTop;

            // Set the player's position
            player.setPosition(player.posX, desiredY, player.posZ);
        }
    }
}
