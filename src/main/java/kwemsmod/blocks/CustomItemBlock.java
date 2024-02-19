package kwemsmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class CustomItemBlock extends ItemBlock {

    public CustomItemBlock(Block block) {
        super(block);
        this.setMaxStackSize(16); // Set the maximum stack size to 16
    }
}
