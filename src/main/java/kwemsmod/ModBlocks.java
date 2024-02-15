package kwemsmod;

import kwemsmod.blocks.renderer.OakBed.OakBed;
import kwemsmod.blocks.renderer.boorealbed.BorealBed;
import kwemsmod.blocks.renderer.canopybed.WoodenCanopyBed;
import kwemsmod.blocks.ManaCrystalBlock;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

public class ModBlocks {

    @GameRegistry.ObjectHolder("KwemsMod:ManaCrystalBlock")
    public static ManaCrystalBlock manacrystalblock;
    @GameRegistry.ObjectHolder("KwemsMod:WoodenCanopyBed")
    public static WoodenCanopyBed woodencanopybed;
    @GameRegistry.ObjectHolder("KwemsMod:BorealBed")
    public static BorealBed borealbed;
    @GameRegistry.ObjectHolder("KwemsMod:OakBed")
    public static OakBed oakbed;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        manacrystalblock.initModel();
        woodencanopybed.initModel();
        borealbed.initModel();;
    }
}