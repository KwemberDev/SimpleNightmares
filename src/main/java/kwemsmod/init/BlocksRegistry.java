package kwemsmod.init;

import java.util.ArrayList;

import kwemsmod.KwemsMod;
import kwemsmod.blocks.BlockBase;
import kwemsmod.blocks.DemonBed;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class BlocksRegistry {
    public static final ArrayList<BlockBase> BLOCKS = new ArrayList<>();

    @GameRegistry.ObjectHolder(KwemsMod.MOD_ID + ":demon_bed")
    public static final DemonBed DEMON_BED = new DemonBed("demon_bed");

    public static void register(IForgeRegistry<Block> registry) {
        for(BlockBase block : BLOCKS) {
            registry.register(block);
        }
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        for(BlockBase block : BLOCKS) {
            registry.register(block.createItemBlock());
        }
    }

    static {
        BLOCKS.add(DEMON_BED);
    }
}