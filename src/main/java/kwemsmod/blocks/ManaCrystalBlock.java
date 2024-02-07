package kwemsmod.blocks;

import kwemsmod.KwemsMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ManaCrystalBlock extends Block {
    public ManaCrystalBlock() {
        super(Material.ROCK);
        setTranslationKey(KwemsMod.MODID + ".manacrystalblock");     // Used for localization (en_us.lang)
        setRegistryName("manacrystalblock");        // The unique name (within the mod) that identifies this block
    }
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}