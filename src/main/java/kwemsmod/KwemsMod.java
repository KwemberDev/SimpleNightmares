package kwemsmod;

import kwemsmod.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid= KwemsMod.MOD_ID, version = KwemsMod.VERSION, name = KwemsMod.NAME)
public class KwemsMod {
    public static final String MOD_ID = "kwemsmod";
    public static final String VERSION = "1.0";
    public static final String NAME = "kwemsmod";

    @Instance
    public static KwemsMod INSTANCE;

    @SidedProxy(serverSide = "kwemsmod.proxy.CommonProxy", clientSide = "kwemsmod.proxy.ClientProxy")
    public static CommonProxy PROXY;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(PROXY);
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
}