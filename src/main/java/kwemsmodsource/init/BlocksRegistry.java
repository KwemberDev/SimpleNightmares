package kwemsmodsource.init;

import java.util.ArrayList;

import kwemsmodsource.blocks.BlockBase;
import kwemsmodsource.blocks.DemonBed;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class BlocksRegistry {
    public static final ArrayList<BlockBase> BLOCKS = new ArrayList<BlockBase>();
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

    public static void registerModels() {
        for(BlockBase block : BLOCKS) {
            block.registerItemModel(Item.getItemFromBlock(block));
        }
    }
}