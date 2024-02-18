package kwemsmod;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.entity.player.EntityPlayer;
import kwemsmod.config.*;

@EventBusSubscriber(modid = KwemsMod.MODID)
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
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
}
