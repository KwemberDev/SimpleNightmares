package kwemsmod.proxy;

import kwemsmod.KwemsMod;
import kwemsmod.ModBlocks;
import kwemsmod.blocks.BorealBed;
import kwemsmod.ModdedEntityPlayer;
import kwemsmod.blocks.WoodenCanopyBed;
import kwemsmod.blocks.ManaCrystalBlock;
import kwemsmod.blocks.renderer.boorealbed.TileEntityBorealBed;
import kwemsmod.blocks.renderer.canopybed.TileEntityWoodenCanopyBed;
import kwemsmod.config.Config;
import kwemsmod.items.Bat;
import kwemsmod.items.DemonWand;
import kwemsmod.items.NailBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {
    public static Configuration config;
    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "kwemsmod.cfg"));
        Config.readConfig();
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }

    }

    // IMPORTANT
    // Here in commonproxy is where you register the blocks and items. by adding them to the registry.
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new ManaCrystalBlock());
        event.getRegistry().register(new WoodenCanopyBed());
        event.getRegistry().register(new BorealBed());


        GameRegistry.registerTileEntity(TileEntityWoodenCanopyBed.class, KwemsMod.MODID + ".WoodenCanopyBed");
        GameRegistry.registerTileEntity(TileEntityBorealBed.class, KwemsMod.MODID + ".BorealBed");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.manacrystalblock).setRegistryName(ModBlocks.manacrystalblock.getRegistryName()));
        event.getRegistry().register(new DemonWand());
        event.getRegistry().register(new Bat(Item.ToolMaterial.WOOD));
        event.getRegistry().register(new ItemBlock(ModBlocks.woodencanopybed).setRegistryName(ModBlocks.woodencanopybed.getRegistryName()));
        event.getRegistry().register(new NailBat(Item.ToolMaterial.STONE));
        event.getRegistry().register(new ItemBlock(ModBlocks.borealbed).setRegistryName(ModBlocks.borealbed.getRegistryName()));

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