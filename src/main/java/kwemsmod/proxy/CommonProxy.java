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
import kwemsmod.blocks.renderer.wroughtironbed.TileEntityWroughIronBed;
import kwemsmod.blocks.renderer.wroughtironbed.WroughtIronBed;
import kwemsmod.config.Config;
import kwemsmod.items.Bat;
import kwemsmod.items.DemonWand;
import kwemsmod.items.NailBat;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
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
        event.getRegistry().register(new WroughtIronBed());

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
        GameRegistry.registerTileEntity(TileEntityWroughIronBed.class, MODID + ".WroughtIronBed");
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.manacrystalblock).setRegistryName(ModBlocks.manacrystalblock.getRegistryName()));
        event.getRegistry().register(new DemonWand());
        event.getRegistry().register(new Bat(Item.ToolMaterial.WOOD));
        event.getRegistry().register(new ItemBlock(ModBlocks.woodencanopybed).setRegistryName(ModBlocks.woodencanopybed.getRegistryName()));
        event.getRegistry().register(new NailBat(Item.ToolMaterial.STONE));
        event.getRegistry().register(new ItemBlock(ModBlocks.borealbed).setRegistryName(ModBlocks.borealbed.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.wroughtironbed).setRegistryName(ModBlocks.wroughtironbed.getRegistryName()));
        for (EnumDyeColor color : EnumDyeColor.values()) {
            Block bed = OakBed.BEDS.get(color);
            event.getRegistry().register(new ItemBlock(bed).setRegistryName(bed.getRegistryName()));
        }
    }
}