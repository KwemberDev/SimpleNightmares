package ftblag.kwemsmod;

import org.apache.logging.log4j.Logger;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Objects;

@Mod(modid = kwemsmod.MODID, name = kwemsmod.NAME, version = kwemsmod.VERSION)
public class kwemsmod {
    public static final String MODID = "kwemsmod";
    public static final String NAME = "kwemsmod";
    public static final String VERSION = "1.0.0";
    public static final String RESOURCE_INVENTORY  = "inventory";

    public static DemonBed DemonBed;
    public static ItemBlock itemDemonBed;
    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        preInitTargetBlock();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("Mod initlialised :" + NAME);
    }

    private void preInitTargetBlock() {
        DemonBed = new DemonBed();
        ForgeRegistries.BLOCKS.register(DemonBed);

        itemDemonBed = new ItemBlock(DemonBed);
        itemDemonBed.setRegistryName(Objects.requireNonNull(DemonBed.getRegistryName()));
        ForgeRegistries.ITEMS.register(itemDemonBed);

        ModelResourceLocation chinaModelResourceLocation = new ModelResourceLocation(
                MODID + ":" + ftblag.kwemsmod.DemonBed.NAME, RESOURCE_INVENTORY);
        final int DEFAULT_ITEM_SUBTYPE = 0;
        ModelLoader.setCustomModelResourceLocation(itemDemonBed, DEFAULT_ITEM_SUBTYPE, chinaModelResourceLocation);
    }
}