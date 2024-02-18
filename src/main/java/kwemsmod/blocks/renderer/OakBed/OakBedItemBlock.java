package kwemsmod.blocks.renderer.OakBed;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class OakBedItemBlock extends ItemBlock {
    public OakBedItemBlock(Block block) {
        super(block);
        this.setHasSubtypes(true);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey() + "." + EnumDyeColor.byMetadata(stack.getMetadata()).getName();
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
