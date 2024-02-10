package kwemsmod;

import kwemsmod.blocks.renderer.TileEntityDemonBedRenderer;
import kwemsmod.blocks.renderer.TileEntityDemonBed;
import kwemsmod.proxy.CommonProxy;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;



// the @mod tell forge that this is a mod, the identifiers behind it are for the mod info.
@Mod(modid = KwemsMod.MODID, name = KwemsMod.MODNAME, version = KwemsMod.MODVERSION, dependencies = "required-after:forge@[11.16.0.1865,)", useMetadata = true)
public class KwemsMod {

    // create the mod info data
    public static final String MODID = "kwemsmod";
    public static final String MODNAME = "Kwembers Mod";
    public static final String MODVERSION= "Alpha 0.0.1";

    //tells forge where to look for the server and client side proxies
    @SidedProxy(clientSide = "kwemsmod.proxy.ClientProxy", serverSide = "kwemsmod.proxy.ServerProxy")
    public static CommonProxy proxy;

    // creates an instance of the mod, tells forge to run this class on startup
    @Mod.Instance
    public static KwemsMod instance;

    // creates a logger for the mod to log to console
    public static Logger logger;

    //IMPORTANT
    //this is the preinit, init, and postinit methods for starting up the mod on forge, you register the loggers and the initialization events here.
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}