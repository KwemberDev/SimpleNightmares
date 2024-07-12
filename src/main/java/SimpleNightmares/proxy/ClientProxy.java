package SimpleNightmares.proxy;

import SimpleNightmares.ModBlocks;
import SimpleNightmares.ModItems;
import SimpleNightmares.blocks.renderer.OakBed.OakBed;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.asm.transformers.ItemBlockTransformer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import static SimpleNightmares.ModBlocks.dreamCatcher;
import static SimpleNightmares.SimpleNightmares.MODID;

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