package kwemsmod;

import kwemsmod.items.LightWand;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    @GameRegistry.ObjectHolder("KwemsMod:LightWand")
    public static LightWand lightwand;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        lightwand.initModel();
    }
}
