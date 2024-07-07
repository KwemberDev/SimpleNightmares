package kwemsmod.config;

import kwemsmod.KwemsMod;
import kwemsmod.proxy.CommonProxy;
import net.minecraft.init.MobEffects;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import net.minecraftforge.common.config.Configuration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_DIMENSIONS = "dimensions";
    public static final String CATEGORY_SLEEP = "sleep";
    public static final String CATEGORY_NIGHTMARE = "nightmare";
    public static final String CATEGORY_AMBUSH = "ambush";
    public static final String CATEGORY_NIGHTMAREEFFECT = "nightmareEffect";

    // This values below you can access elsewhere in your mod:
    public static List<String> customBedBlocks = new ArrayList<>();
    public static boolean enableChatRemarks = true;
    public static List<String> chatFeedback = new ArrayList<>();
    public static boolean enableChatFeedback = true;
    public static int sleepPercentage = 100;
    public static int sleepPlayers = -1;
    public static boolean enableNightmares = true;
    public static float nightmareChance = 10;

    public static int hungerDuration = 1200;
    public static int miningfatigueDuration = 1200;
    public static int poisonDuration = 160;
    public static int slownessDuration = 600;
    public static int weaknessDuration = 1200;
    public static int witherDuration = 160;

    public static double ambushChance = 0.05;
    public static boolean enableAmbush = true;


    // Call this from CommonProxy.preInit(). It will create our config if it doesn't
    // exist yet and read the values if it does exist.
    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
            initDimensionConfig(cfg);
            initSleepConfig(cfg);
            initNightmareConfig(cfg);
            initNightmareDurationConfig(cfg);
            initAmbushConfig(cfg);
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

    private static void initSleepConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_SLEEP, "Sleep Configuration:");
        enableChatFeedback = cfg.getBoolean("enableChatFeedback", CATEGORY_SLEEP, true, "Enable or Disable the 'player is sleeping' feedback in chat");
        sleepPercentage = cfg.getInt("sleepPercentage", CATEGORY_SLEEP, 100, 0, 100, "The percentage of players that needs to sleep to skip the night, if this option is selected.");

        enableChatRemarks = cfg.getBoolean("enableChatRemarks", CATEGORY_SLEEP, true, "Enable or Disable the feedback in chat when sleeping in multiplayer.");
        sleepPlayers = cfg.getInt("sleepPlayers", CATEGORY_SLEEP, -1, -1, 99,"The amount of players that need to sleep to skip the night, if this option is selected.");


                //list of chat messages when sleeping in multiplayer:
                chatFeedback = Arrays.asList(cfg.getStringList("ChatFeedback", CATEGORY_SLEEP, new String[] {
                "Look alive soldier! Time for Pain!",
                "The power of Ra is blasting your eyes. You awake from your crypt.",
                "Narrator speaking, time to get your butt moving.",
                "A grue quickly moves away after having stalked you throughout the night, good morning!",
                "You wake up remembering vague images of a forgotten world.",
                "Don't keep the devil waiting, my friend.",
                "You just lost 10 minutes of progression.",
                "You woke up on the wrong side of the bed today...",
                "At least you don't need to get ready for school.",
                "Another day, another emerald.",
                "A Champion was supposed to spawn last night, but no... You just had to sleep.",
                "The \u00A74\u00A7k Cities of the Lost\u00A7r are waiting..."
        },"List of Chat messages as feedback for sleeping in multiplayer."));
    }

    private static void initNightmareConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_NIGHTMARE, "Nightmare Configuration");
        enableNightmares = cfg.getBoolean("enableNightmares", CATEGORY_NIGHTMARE, true, "Enable of Disable the nightmare events.");
        nightmareChance = cfg.getFloat("nightmareChance", CATEGORY_NIGHTMARE, 10, 0, 100, "The chance (%) for the player to have a nightmare.");
    }

    private static void initNightmareDurationConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_NIGHTMAREEFFECT, "Set the duration of nightmare effects here:");
        hungerDuration = cfg.getInt("hungerDuration", CATEGORY_NIGHTMAREEFFECT, 1200, 0, 9999, "");
        miningfatigueDuration = cfg.getInt("miningfatigueDuration", CATEGORY_NIGHTMAREEFFECT, 1200, 0, 9999, "");
        weaknessDuration = cfg.getInt("weaknessDuration", CATEGORY_NIGHTMAREEFFECT, 1200, 0, 9999, "");
        witherDuration = cfg.getInt("witherDuration", CATEGORY_NIGHTMAREEFFECT, 160, 0, 9999, "");
        slownessDuration = cfg.getInt("slownessDuration", CATEGORY_NIGHTMAREEFFECT, 600, 0, 9999, "");
        poisonDuration = cfg.getInt("poisonDuration", CATEGORY_NIGHTMAREEFFECT, 160, 0, 9999, "");
    }

    private static void initAmbushConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_AMBUSH, "Ambush Configuration");
        ambushChance = cfg.getFloat("ambushChance", CATEGORY_AMBUSH, 0.05F, 0, 1, "The chance for a player to get an ambush when sleeping");
        enableAmbush = cfg.getBoolean("enableAmbush", CATEGORY_AMBUSH,true, "Enable or Disable ambushes when sleeping.");
    }
}
