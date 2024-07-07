package kwemsmod.proxy;

import kwemsmod.ModBlocks;
import kwemsmod.blocks.CustomItemBlock;
import kwemsmod.blocks.ManaCrystalBlock;
import kwemsmod.blocks.renderer.OakBed.OakBed;
import kwemsmod.blocks.renderer.OakBed.TileEntityOakBed;
import kwemsmod.blocks.renderer.boorealbed.BorealBed;
import kwemsmod.blocks.renderer.boorealbed.TileEntityBorealBed;
import kwemsmod.blocks.renderer.canopybed.TileEntityWoodenCanopyBed;
import kwemsmod.blocks.renderer.canopybed.WoodenCanopyBed;
import kwemsmod.blocks.renderer.lycanitebeds.DemonBed;
import kwemsmod.blocks.renderer.lycanitebeds.TileEntityDemonBed;
import kwemsmod.blocks.renderer.wroughtbeds.TileEntityWroughtCincinnasiteBed;
import kwemsmod.blocks.renderer.wroughtbeds.TileEntityWroughtIronBed;
import kwemsmod.blocks.renderer.wroughtbeds.WroughtCincinnasiteBed;
import kwemsmod.blocks.renderer.wroughtbeds.WroughtIronBed;
import kwemsmod.commands.GetSleepPercentage;
import kwemsmod.commands.SleepPercentageCommand;
import kwemsmod.config.Config;
import kwemsmod.nightmares.NightmareEventHandler;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

import static kwemsmod.KwemsMod.MODID;

@Mod.EventBusSubscriber
public class CommonProxy {

    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "kwemsmod.cfg"));
        Config.readConfig();
    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new NightmareEventHandler());
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void onServerStartingEvent(FMLServerStartingEvent event) {
        event.registerServerCommand(new SleepPercentageCommand());
        event.registerServerCommand(new GetSleepPercentage());
    }

    // IMPORTANT
    // Here in commonproxy is where you register the blocks and items. by adding them to the registry.
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new ManaCrystalBlock());
        event.getRegistry().register(new WoodenCanopyBed());
        event.getRegistry().register(new BorealBed());
        event.getRegistry().register(new WroughtIronBed());
        event.getRegistry().register(new WroughtCincinnasiteBed());
        event.getRegistry().register(new DemonBed());

        for (EnumDyeColor color : EnumDyeColor.values()) {
            Block bed = new OakBed();
            bed.setTranslationKey(MODID + "." + color.getName() + "_oakbed");
            OakBed.BEDS.put(color, bed);
            bed.setRegistryName(MODID, color.getName() + "_oakbed");
            event.getRegistry().registerAll(bed);
        }


        // register tileentities
        GameRegistry.registerTileEntity(TileEntityWoodenCanopyBed.class, MODID + ".WoodenCanopyBed");
        GameRegistry.registerTileEntity(TileEntityBorealBed.class, MODID + ".BorealBed");
        GameRegistry.registerTileEntity(TileEntityOakBed.class, MODID + ".OakBed");
        GameRegistry.registerTileEntity(TileEntityWroughtIronBed.class, MODID + ".WroughtIronBed");
        GameRegistry.registerTileEntity(TileEntityWroughtCincinnasiteBed.class, MODID + ".wroughtcincinnasitebed");
        GameRegistry.registerTileEntity(TileEntityDemonBed.class, MODID + ".demonbed");
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.manacrystalblock).setRegistryName(ModBlocks.manacrystalblock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.woodencanopybed).setRegistryName(ModBlocks.woodencanopybed.getRegistryName()));
        event.getRegistry().register(new CustomItemBlock(ModBlocks.borealbed).setRegistryName(ModBlocks.borealbed.getRegistryName()));
        event.getRegistry().register(new CustomItemBlock(ModBlocks.wroughtironbed).setRegistryName(ModBlocks.wroughtironbed.getRegistryName()));
        event.getRegistry().register(new CustomItemBlock(ModBlocks.wroughtCincinnasiteBed).setRegistryName(ModBlocks.wroughtCincinnasiteBed.getRegistryName()));
        event.getRegistry().register(new CustomItemBlock(ModBlocks.demonbed).setRegistryName(ModBlocks.demonbed.getRegistryName()));

        for (EnumDyeColor color : EnumDyeColor.values()) {
            Block bed = OakBed.BEDS.get(color);
            event.getRegistry().register(new CustomItemBlock(bed).setRegistryName(bed.getRegistryName()));
        }
    }
}