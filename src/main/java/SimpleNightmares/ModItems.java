package SimpleNightmares;

import SimpleNightmares.items.MoonFragment;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    @GameRegistry.ObjectHolder("SimpleNightmares:MoonFragment")
    public static MoonFragment moonFragment;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        moonFragment.initModel();
    }

}
