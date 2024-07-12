package SimpleNightmares;

import SimpleNightmares.blocks.dreamcatchers.DreamCatcher;
import SimpleNightmares.blocks.renderer.OakBed.OakBed;
import SimpleNightmares.blocks.renderer.wroughtbeds.WroughtCincinnasiteBed;
import SimpleNightmares.blocks.renderer.wroughtbeds.WroughtIronBed;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

//    @GameRegistry.ObjectHolder("SimpleNightmares:WoodenCanopyBed")
//    public static WoodenCanopyBed woodencanopybed;
//    @GameRegistry.ObjectHolder("SimpleNightmares:BorealBed")
//    public static BorealBed borealbed;
    @GameRegistry.ObjectHolder("SimpleNightmares:OakBed")
    public static OakBed oakbed;
    @GameRegistry.ObjectHolder("SimpleNightmares:WroughtIronBed")
    public static WroughtIronBed wroughtironbed;
    @GameRegistry.ObjectHolder("SimpleNightmares:WroughtCincinnasiteBed")
    public static WroughtCincinnasiteBed wroughtCincinnasiteBed;
//    @GameRegistry.ObjectHolder("SimpleNightmares:DemonBed")
//    public static DemonBed demonbed;

    @GameRegistry.ObjectHolder("SimpleNightmares:DreamCatcher")
    public static DreamCatcher dreamCatcher;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
//        woodencanopybed.initModel();
//        borealbed.initModel();
        wroughtironbed.initModel();
        wroughtCincinnasiteBed.initModel();
//        demonbed.initModel();
        dreamCatcher.initModel();
    }
}