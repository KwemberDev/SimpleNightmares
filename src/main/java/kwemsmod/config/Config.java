package kwemsmod.config;

import kwemsmod.KwemsMod;
import kwemsmod.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import net.minecraftforge.common.config.Configuration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_DIMENSIONS = "dimensions";

    // This values below you can access elsewhere in your mod:
    public static List<String> customBedBlocks = new ArrayList<>();

    // Call this from CommonProxy.preInit(). It will create our config if it doesn't
    // exist yet and read the values if it does exist.
    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
            initDimensionConfig(cfg);
        } catch (Exception e1) {
            KwemsMod.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
        customBedBlocks = Arrays.asList(cfg.getStringList("customBedBlocks", CATEGORY_GENERAL, new String[] {
                "kwemsmod:black_oakbed",
                "kwemsmod:blue_oakbed",
                "kwemsmod:borealbed",
                "kwemsmod:brown_oakbed",
                "kwemsmod:cyan_oakbed",
                "kwemsmod:gray_oakbed",
                "kwemsmod:green_oakbed",
                "kwemsmod:light_blue_oakbed",
                "kwemsmod:lime_oakbed",
                "kwemsmod:magenta_oakbed",
                "kwemsmod:orange_oakbed",
                "kwemsmod:pink_oakbed",
                "kwemsmod:purple_oakbed",
                "kwemsmod:red_oakbed",
                "kwemsmod:silver_oakbed",
                "kwemsmod:white_oakbed",
                "kwemsmod:yellow_oakbed"
        }, "Names of the custom bed blocks affected by this mods custom sleep positioning:"));
    }

    private static void initDimensionConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_DIMENSIONS, "Dimension configuration");
    }
}
