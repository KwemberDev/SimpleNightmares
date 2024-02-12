package kwemsmod;

import kwemsmod.blocks.BorealBed;
import kwemsmod.blocks.WoodenCanopyBed;
import kwemsmod.blocks.ManaCrystalBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    @GameRegistry.ObjectHolder("KwemsMod:ManaCrystalBlock")
    public static ManaCrystalBlock manacrystalblock;
    @GameRegistry.ObjectHolder("KwemsMod:WoodenCanopyBed")
    public static WoodenCanopyBed woodencanopybed;
    @GameRegistry.ObjectHolder("KwemsMod:BorealBed")
    public static BorealBed borealbed;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        manacrystalblock.initModel();
        woodencanopybed.initModel();
        borealbed.initModel();
    }
}