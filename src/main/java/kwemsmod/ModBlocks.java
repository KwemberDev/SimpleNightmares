package kwemsmod;

import kwemsmod.blocks.ManaCrystalBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    @GameRegistry.ObjectHolder("KwemsMod:ManaCrystalBlock")
    public static ManaCrystalBlock manacrystalblock;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        manacrystalblock.initModel();
    }
}