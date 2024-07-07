package kwemsmod.proxy;

import kwemsmod.ModBlocks;
import kwemsmod.ModItems;
import kwemsmod.blocks.renderer.OakBed.OakBed;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import static kwemsmod.KwemsMod.MODID;

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
        for (EnumDyeColor color : EnumDyeColor.values()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(OakBed.BEDS.get(color)), 0, new ModelResourceLocation(MODID + ":" + color.getName() + "_oakbed", "inventory"));
        }

    }

    @SubscribeEvent
    public void registerRenderers() {
    }
}