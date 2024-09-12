package SimpleNightmares.proxy;

import SimpleNightmares.ModBlocks;
import SimpleNightmares.blocks.CustomItemBlock;
import SimpleNightmares.blocks.dreamcatchers.DreamCatcher;
import SimpleNightmares.blocks.renderer.OakBed.OakBed;
import SimpleNightmares.blocks.renderer.OakBed.TileEntityOakBed;
import SimpleNightmares.blocks.renderer.wroughtbeds.TileEntityWroughtCincinnasiteBed;
import SimpleNightmares.blocks.renderer.wroughtbeds.TileEntityWroughtIronBed;
import SimpleNightmares.blocks.renderer.wroughtbeds.WroughtCincinnasiteBed;
import SimpleNightmares.blocks.renderer.wroughtbeds.WroughtIronBed;
import SimpleNightmares.commands.GetSleepPercentage;
import SimpleNightmares.commands.SleepPercentageCommand;
import SimpleNightmares.config.Config;
import SimpleNightmares.drops.CustomDropsHandler;
import SimpleNightmares.effects.BlessingOfTheFullMoonEffect;
import SimpleNightmares.items.MoonFragment;
import SimpleNightmares.nightmares.NightmareEventHandler;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
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

import static SimpleNightmares.SimpleNightmares.MODID;

@Mod.EventBusSubscriber
public class CommonProxy {
    public static Potion MOONBLESSING;

    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "SimpleNightmares.cfg"));
        Config.readConfig();
    }

    public static void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new NightmareEventHandler());
        MinecraftForge.EVENT_BUS.register(new CustomDropsHandler());
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
//        event.getRegistry().register(new WoodenCanopyBed());
//        event.getRegistry().register(new BorealBed());
        event.getRegistry().register(new WroughtIronBed());
        event.getRegistry().register(new WroughtCincinnasiteBed());
//        event.getRegistry().register(new DemonBed());
        event.getRegistry().register(new DreamCatcher());

        for (EnumDyeColor color : EnumDyeColor.values()) {
            Block bed = new OakBed();
            bed.setTranslationKey(MODID + "." + color.getName() + "_oakbed");
            OakBed.BEDS.put(color, bed);
            bed.setRegistryName(MODID, color.getName() + "_oakbed");
            event.getRegistry().registerAll(bed);
        }


        // register tileentities
//        GameRegistry.registerTileEntity(TileEntityWoodenCanopyBed.class, MODID + ".WoodenCanopyBed");
//        GameRegistry.registerTileEntity(TileEntityBorealBed.class, MODID + ".BorealBed");
        GameRegistry.registerTileEntity(TileEntityOakBed.class, MODID + ".OakBed");
        GameRegistry.registerTileEntity(TileEntityWroughtIronBed.class, MODID + ".WroughtIronBed");
        GameRegistry.registerTileEntity(TileEntityWroughtCincinnasiteBed.class, MODID + ".wroughtcincinnasitebed");
//        GameRegistry.registerTileEntity(TileEntityDemonBed.class, MODID + ".demonbed");
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new MoonFragment());
//        event.getRegistry().register(new ItemBlock(ModBlocks.woodencanopybed).setRegistryName(ModBlocks.woodencanopybed.getRegistryName()));
//        event.getRegistry().register(new CustomItemBlock(ModBlocks.borealbed).setRegistryName(ModBlocks.borealbed.getRegistryName()));
        event.getRegistry().register(new CustomItemBlock(ModBlocks.wroughtironbed).setRegistryName(ModBlocks.wroughtironbed.getRegistryName()));
        event.getRegistry().register(new CustomItemBlock(ModBlocks.wroughtCincinnasiteBed).setRegistryName(ModBlocks.wroughtCincinnasiteBed.getRegistryName()));
//        event.getRegistry().register(new CustomItemBlock(ModBlocks.demonbed).setRegistryName(ModBlocks.demonbed.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.dreamCatcher).setRegistryName(ModBlocks.dreamCatcher.getRegistryName()));

        for (EnumDyeColor color : EnumDyeColor.values()) {
            Block bed = OakBed.BEDS.get(color);
            event.getRegistry().register(new CustomItemBlock(bed).setRegistryName(bed.getRegistryName()));
        }
    }

    @SubscribeEvent
    public static void onRegisterPotions(RegistryEvent.Register<Potion> event) {
        MOONBLESSING = new BlessingOfTheFullMoonEffect(false, 9999999).setRegistryName("moonsblessing");
        event.getRegistry().registerAll(MOONBLESSING);
    }
}