package kwemsmod.blocks;

import kwemsmod.KwemsMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import java.util.Objects;

public class BlockBase extends Block {

    public BlockBase(Material material, String name) {
        super(material);
        setRegistryName(KwemsMod.MOD_ID, name);
        setTranslationKey(KwemsMod.MOD_ID + "." + name);
    }

    public Item createItemBlock() {
        ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setRegistryName(Objects.requireNonNull(getRegistryName()));
        return itemBlock;
    }
}