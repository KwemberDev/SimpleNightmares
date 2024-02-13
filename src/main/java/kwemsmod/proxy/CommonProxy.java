package kwemsmod.proxy;

import kwemsmod.KwemsMod;
import kwemsmod.ModBlocks;
import kwemsmod.blocks.renderer.OakBed.OakBed;
import kwemsmod.blocks.renderer.OakBed.TileEntityOakBed;
import kwemsmod.blocks.renderer.boorealbed.BorealBed;
import kwemsmod.blocks.renderer.canopybed.WoodenCanopyBed;
import kwemsmod.blocks.ManaCrystalBlock;
import kwemsmod.blocks.renderer.boorealbed.TileEntityBorealBed;
import kwemsmod.blocks.renderer.canopybed.TileEntityWoodenCanopyBed;
import kwemsmod.config.Config;
import kwemsmod.items.Bat;
import kwemsmod.items.DemonWand;
import kwemsmod.items.NailBat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
        event.getRegistry().register(new OakBed());

        // register tileentities
        GameRegistry.registerTileEntity(TileEntityWoodenCanopyBed.class, KwemsMod.MODID + ".WoodenCanopyBed");
        GameRegistry.registerTileEntity(TileEntityBorealBed.class, KwemsMod.MODID + ".BorealBed");
        GameRegistry.registerTileEntity(TileEntityOakBed.class, KwemsMod.MODID + ".OakBed");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.manacrystalblock).setRegistryName(ModBlocks.manacrystalblock.getRegistryName()));
        event.getRegistry().register(new DemonWand());
        event.getRegistry().register(new Bat(Item.ToolMaterial.WOOD));
        event.getRegistry().register(new ItemBlock(ModBlocks.woodencanopybed).setRegistryName(ModBlocks.woodencanopybed.getRegistryName()));
        event.getRegistry().register(new NailBat(Item.ToolMaterial.STONE));
        event.getRegistry().register(new ItemBlock(ModBlocks.borealbed).setRegistryName(ModBlocks.borealbed.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.oakbed).setRegistryName(ModBlocks.oakbed.getRegistryName()));
    }
}