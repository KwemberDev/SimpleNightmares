package kwemsmod.proxy;

import kwemsmod.ModBlocks;
import kwemsmod.ModItems;
import kwemsmod.blocks.renderer.TileEntityWoodenCanopyBed;
import kwemsmod.blocks.renderer.TileEntityWoodenCanopyBedRenderer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
    }
}