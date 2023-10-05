package kwemsmodsource;


import kwemsmodsource.init.BlocksRegistry;
import kwemsmodsource.proxy.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid= Main.MOD_ID, version = Main.VERSION, name = Main.NAME)
public class Main {
    public static final String MOD_ID = "kwemsmodsource";
    public static final String VERSION = "1.0";
    public static final String NAME = "kwemsmodsource";

    @Instance
    public static Main main;

    @SidedProxy(serverSide = "kwemsmodsource.proxy.CommonProxy", clientSide = "kwemsmodsource.proxy.ClientProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {

    }

    @EventBusSubscriber
    public static class RegistrationHandler{

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            BlocksRegistry.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event) throws Exception {
            try {
                IForgeRegistry<Item> registry = event.getRegistry();
                BlocksRegistry.registerItemBlocks(event.getRegistry());
            }
            catch(ArrayIndexOutOfBoundsException ex) {
                String message = ex.getMessage();
                throw ex;
            }
            catch(Throwable ex) {
                String message = ex.getMessage();
                throw ex;
            }
        }

        @SubscribeEvent
        public static void registerItems(ModelRegistryEvent event) {
            BlocksRegistry.registerModels();
        }


    }
}