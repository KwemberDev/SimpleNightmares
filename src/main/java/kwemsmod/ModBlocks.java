package kwemsmod;

import kwemsmod.blocks.renderer.OakBed.OakBed;
import kwemsmod.blocks.renderer.boorealbed.BorealBed;
import kwemsmod.blocks.renderer.canopybed.WoodenCanopyBed;
import kwemsmod.blocks.ManaCrystalBlock;
import kwemsmod.blocks.renderer.lycanitebeds.DemonBed;
import kwemsmod.blocks.renderer.wroughtbeds.WroughtCincinnasiteBed;
import kwemsmod.blocks.renderer.wroughtbeds.WroughtIronBed;
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
    @GameRegistry.ObjectHolder("KwemsMod:OakBed")
    public static OakBed oakbed;
    @GameRegistry.ObjectHolder("KwemsMod:WroughtIronBed")
    public static WroughtIronBed wroughtironbed;
    @GameRegistry.ObjectHolder("kwemsMod:WroughtCincinnasiteBed")
    public static WroughtCincinnasiteBed wroughtCincinnasiteBed;
    @GameRegistry.ObjectHolder("KwemsMod:DemonBed")
    public static DemonBed demonbed;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        manacrystalblock.initModel();
        woodencanopybed.initModel();
        borealbed.initModel();
        wroughtironbed.initModel();
        wroughtCincinnasiteBed.initModel();
        demonbed.initModel();
    }
}