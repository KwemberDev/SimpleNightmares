package kwemsmod.proxy;

import kwemsmod.KwemsMod;
import kwemsmod.ModBlocks;
import kwemsmod.ModItems;
import kwemsmod.blocks.renderer.TileEntityDemonBed;
import kwemsmod.blocks.renderer.TileEntityDemonBedRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
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
        Item item = Item.getItemFromBlock(ModBlocks.demonbed);
    }

    @SubscribeEvent
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDemonBed.class, new TileEntityDemonBedRenderer());
    }
}