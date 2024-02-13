package kwemsmod.proxy;

import kwemsmod.ModBlocks;
import kwemsmod.ModItems;
import kwemsmod.blocks.renderer.boorealbed.TileEntityBorealBed;
import kwemsmod.blocks.renderer.boorealbed.TileEntityBorealBedRenderer;
import kwemsmod.blocks.renderer.canopybed.TileEntityWoodenCanopyBed;
import kwemsmod.blocks.renderer.canopybed.TileEntityWoodenCanopyBedRenderer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
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
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBorealBed.class, new TileEntityBorealBedRenderer());
    }
}