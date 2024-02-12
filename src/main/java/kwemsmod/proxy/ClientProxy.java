package kwemsmod.proxy;

import kwemsmod.ModBlocks;
import kwemsmod.ModItems;
import kwemsmod.ModdedEntityPlayer;
import kwemsmod.blocks.renderer.boorealbed.TileEntityBorealBed;
import kwemsmod.blocks.renderer.boorealbed.TileEntityBorealBedRenderer;
import kwemsmod.blocks.renderer.canopybed.TileEntityWoodenCanopyBed;
import kwemsmod.blocks.renderer.canopybed.TileEntityWoodenCanopyBedRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModBlocks.initModels();
        ModItems.initModels();
    }

    @SubscribeEvent
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWoodenCanopyBed.class, new TileEntityWoodenCanopyBedRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBorealBed.class, new TileEntityBorealBedRenderer());
    }
    @SubscribeEvent
    public void onPlayerSleepInBed(PlayerSleepInBedEvent event) throws InterruptedException {
        EntityPlayer player = event.getEntityPlayer();
        if (player instanceof ModdedEntityPlayer) {
            ModdedEntityPlayer moddedPlayer = (ModdedEntityPlayer) player;
            BlockPos bedPos = event.getPos();
            EntityPlayer.SleepResult result = moddedPlayer.trySleep(bedPos);
            if (result == EntityPlayer.SleepResult.OK) {
                Thread.sleep(100);
                // Send a packet to all clients updating the player's position
                Packet<?> packet = new SPacketEntityTeleport(moddedPlayer);
                for (EntityPlayerMP otherPlayer : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
                    otherPlayer.connection.sendPacket(packet);
                }
                event.setResult(Event.Result.ALLOW);
            } else {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}