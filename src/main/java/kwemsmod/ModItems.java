package kwemsmod;

import kwemsmod.items.Bat;
import kwemsmod.items.DemonWand;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    @GameRegistry.ObjectHolder("KwemsMod:DemonWand")
    public static DemonWand demonwand;

    @GameRegistry.ObjectHolder("KwemsMod:Bat")
    public static Bat bat;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        demonwand.initModel();
        bat.initModel();
    }
}
