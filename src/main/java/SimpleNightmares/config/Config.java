package SimpleNightmares.config;

import SimpleNightmares.SimpleNightmares;
import SimpleNightmares.ambush.PotionEffectGroup;
import SimpleNightmares.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.util.*;

public class Config {

    private static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_SLEEP = "sleep";
    public static final String CATEGORY_NIGHTMARE = "nightmare";
    public static final String CATEGORY_AMBUSH = "ambush";
    public static final String CATEGORY_AMBUSHMOBS = "ambushMobs";
    public static final String CATEGORY_AMBUSHEFFECTS = "ambushEffects";
    public static final String CATEGORY_NIGHTMAREEFFECTS = "nightmareEffects";
    public static final String CATEGORY_MOONBLESSING = "moonBlessing";

    // This values below you can access elsewhere in your mod:
    public static List<String> customBedBlocks = new ArrayList<>();
    public static boolean enableChatRemarks = true;
    public static List<String> chatFeedback = new ArrayList<>();
    public static boolean enableChatFeedback = true;
    public static int sleepPercentage = 100;
    public static int sleepPlayers = -1;
    public static String sleepMessageColor = "GOLD";
    public static String wakeUpMessageColor = "GOLD";

    public static boolean enableSinglePlayerDebug = false;

    public static boolean enableNightmares = true;
    public static float nightmareChance = 10;
    public static double dropMoonFragmentChance = 0.01;

    public static double ambushChance = 0.05;
    public static boolean enableAmbush = true;
    public static boolean needsOpenSky = false;
    public static int distanceFromPlayerMin = 5;
    public static int distanceFromPlayerMax = 20;
    public static int maxYLevelAbovePlayer = 10;
    public static int maxYLevelBelowPlayer = 6;

    public static List<PotionEffectGroup> potionNightmareEffects = new ArrayList<>();
    public static List<PotionEffectGroup> potionAmbushEffects = new ArrayList<>();
    public static List<MobGroup> mobGroups = new ArrayList<>();
    public static final Map<String, Double> attributeModifiers = new HashMap<>();


    // Call this from CommonProxy.preInit(). It will create our config if it doesn't
    // exist yet and read the values if it does exist.
    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
            initSleepConfig(cfg);
            initNightmareConfig(cfg);
            initNightmarePotionEffectsConfig(cfg);
            initAmbushConfig(cfg);
            initAmbushMobsConfig(cfg);
            initAmbushPotionEffectsConfig(cfg);
            initMoonBlessingConfig(cfg);
        } catch (Exception e1) {
            SimpleNightmares.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
        customBedBlocks = Arrays.asList(cfg.getStringList("customBedBlocks", CATEGORY_GENERAL, new String[]{
                "simplenightmares:black_oakbed",
                "simplenightmares:blue_oakbed",
                "simplenightmares:borealbed",
                "simplenightmares:brown_oakbed",
                "simplenightmares:cyan_oakbed",
                "simplenightmares:gray_oakbed",
                "simplenightmares:green_oakbed",
                "simplenightmares:light_blue_oakbed",
                "simplenightmares:lime_oakbed",
                "simplenightmares:magenta_oakbed",
                "simplenightmares:orange_oakbed",
                "simplenightmares:pink_oakbed",
                "simplenightmares:purple_oakbed",
                "simplenightmares:red_oakbed",
                "simplenightmares:silver_oakbed",
                "simplenightmares:white_oakbed",
                "simplenightmares:yellow_oakbed"
        }, "Names of the custom bed blocks affected by this mods custom sleep positioning:"));
    }

    private static void initSleepConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_SLEEP, "Sleep Configuration:");
        enableSinglePlayerDebug = cfg.getBoolean("enableSinglePlayerDebug", CATEGORY_SLEEP, false, "Enable or Disable the sleep percentage chat messages for singleplayer. Usually for debug purposes only.");

        sleepPercentage = cfg.getInt("sleepPercentage", CATEGORY_SLEEP, 100, 0, 100, "The percentage of players that needs to sleep to skip the night, if this option is selected. ONLY ALTER THROUGH IN GAME COMMAND /setsleeppercentage");
        sleepPlayers = cfg.getInt("sleepPlayers", CATEGORY_SLEEP, -1, -1, 99, "The amount of players that need to sleep to skip the night, if this option is selected. ONLY ALTER THROUGH IN GAME COMMAND /setsleeppercentage");

        enableChatFeedback = cfg.getBoolean("enableChatFeedback", CATEGORY_SLEEP, true, "Enable or Disable the 'player is sleeping' feedback in chat");
        enableChatRemarks = cfg.getBoolean("enableChatRemarks", CATEGORY_SLEEP, true, "Enable or Disable the feedback in chat when sleeping in multiplayer.");

        sleepMessageColor = cfg.getString("sleepMessageColor", CATEGORY_SLEEP, "GOLD", "Set the color for the 'is now sleeping' message. Color name must match TextFormatting enums.");
        wakeUpMessageColor = cfg.getString("wakeUpMessageColor", CATEGORY_SLEEP, "GOLD", "Set the color for the wake up chat remarks. Color name must match TextFormatting enums.");

        //list of chat messages when sleeping in multiplayer:
        chatFeedback = Arrays.asList(cfg.getStringList("ChatFeedback", CATEGORY_SLEEP, new String[]{
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
        }, "List of Chat messages as feedback for sleeping in multiplayer."));
    }

    private static void initNightmareConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_NIGHTMARE, "Nightmare Configuration");
        enableNightmares = cfg.getBoolean("enableNightmares", CATEGORY_NIGHTMARE, true, "Enable of Disable the nightmare events.");
        nightmareChance = cfg.getFloat("nightmareChance", CATEGORY_NIGHTMARE, 10, 0, 100, "The chance (%) for the player to have a nightmare.");
        dropMoonFragmentChance = cfg.getFloat("dropMoonFragmentChance", CATEGORY_NIGHTMARE, 0.02F, 0.0F, 1.0F, "Chance for hostile mobs to drop a moon fragment during the night. \nFragment drop chance scales linearly with moon phase, from 0 at new moon to the set chance at full moon.");
    }

    public static void initNightmarePotionEffectsConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_NIGHTMAREEFFECTS, "Nightmare Potion Effects Configuration");
        String[] defaultConfig = new String[] {
                "0.99,minecraft:weakness,1200,2",
                "0.99,lycanitesmobs:insomnia,2400,2",
                "0.99,lycanitesmobs:penetration,2400,0",
                "0.99,minecraft:poison,2400,0",
                "0.99,minecraft:unlock,2400,1",
        };
        String[] potionEffectConfig = cfg.getStringList("nightmareEffectConfig", CATEGORY_NIGHTMAREEFFECTS, defaultConfig, "");
        for (String effect : potionEffectConfig) {
            String[] parts = effect.split(",");
            PotionEffectGroup effectGroup = new PotionEffectGroup();
            effectGroup.chance = Double.parseDouble(parts[0]);
            effectGroup.effectId = parts[1];
            effectGroup.duration = parts.length > 2 ? Integer.parseInt(parts[2]) : 600; // default to 30 seconds
            effectGroup.level = parts.length > 3 ? Integer.parseInt(parts[3]) : 0; // default to level 1
            potionNightmareEffects.add(effectGroup);
        }
    }

    private static void initAmbushConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_AMBUSH, "Ambush Configuration");
        ambushChance = cfg.getFloat("ambushChance", CATEGORY_AMBUSH, 0.05F, 0, 1, "The chance for a player to get an ambush when sleeping");
        enableAmbush = cfg.getBoolean("enableAmbush", CATEGORY_AMBUSH, true, "Enable or Disable ambushes when sleeping.");
        needsOpenSky = cfg.getBoolean("needsOpenSky", CATEGORY_AMBUSH, false, "Toggle if an ambush can only happen if the player is sleeping in the open (exposed to the sky)");
        distanceFromPlayerMin = cfg.getInt("distanceFromPlayerMin", CATEGORY_AMBUSH, 5, 1, 64, "Minimal distance an ambush mob can spawn from the player. Must be smaller than the maximum value");
        distanceFromPlayerMax = cfg.getInt("distanceFromPlayerMax", CATEGORY_AMBUSH, 20, 2, 128, "Maximum distance an ambush mob can spawn from the player. Must be bigger than the minimal value.");
        maxYLevelAbovePlayer = cfg.getInt("maxYLevelAbovePlayer", CATEGORY_AMBUSH, 10, 1, 128, "Change the maximum Y level above the player that ambush mobs can spawn.");
        maxYLevelBelowPlayer = cfg.getInt("maxYLevelBelowPlayer", CATEGORY_AMBUSH, 6, 1, 32, "Change the maximum Y level below the player that ambush mobs can spawn");

    }

    public static void initAmbushMobsConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_AMBUSHMOBS, "Ambush mobs Configuration");
        String[] defaultConfig = new String[] {
                "5/1.0,minecraft:zombie,4,9;0.7,minecraft:skeleton,0,2;0.9,minecraft:husk,1,5;",
                "5/0.9,minecraft:skeleton_horse,1,3;1.0,minecraft:skeleton,2,4;0.5,minecraft:silverfish,2,4;"
        };
        String[] mobGroupConfig = cfg.getStringList("MobGroupConfig", CATEGORY_AMBUSHMOBS, defaultConfig, "");
        for (String group : mobGroupConfig) {
            String[] parts = group.split("/");
            MobGroup mobGroup = new MobGroup();
            mobGroup.weight = Double.parseDouble(parts[0]);
            String[] mobs = parts[1].split(";");
            for (String mob : mobs) {
                String[] mobParts = mob.split(",");
                MobGroup.MobInfo mobInfo = new MobGroup.MobInfo();
                mobInfo.chance = Double.parseDouble(mobParts[0]);
                mobInfo.mobId = mobParts[1];
                mobInfo.min = Integer.parseInt(mobParts[2]);
                mobInfo.max = Integer.parseInt(mobParts[3]);
                mobGroup.mobs.add(mobInfo);
            }
            mobGroups.add(mobGroup);
        }
    }

    public static void initAmbushPotionEffectsConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_AMBUSHEFFECTS, "Ambush Potion Effects Configuration");
        String[] defaultConfig = new String[] {
                "0.99,minecraft:weakness,1200,2",
                "0.99,lycanitesmobs:insomnia,2400,2"
        };
        String[] potionEffectConfig = cfg.getStringList("PotionEffectConfig", CATEGORY_AMBUSHEFFECTS, defaultConfig, "");
        for (String effect : potionEffectConfig) {
            String[] parts = effect.split(",");
            PotionEffectGroup effectGroup = new PotionEffectGroup();
            effectGroup.chance = Double.parseDouble(parts[0]);
            effectGroup.effectId = parts[1];
            effectGroup.duration = parts.length > 2 ? Integer.parseInt(parts[2]) : 600; // default to 30 seconds
            effectGroup.level = parts.length > 3 ? Integer.parseInt(parts[3]) : 0; // default to level 1
            potionAmbushEffects.add(effectGroup);
        }
    }

    private static void initMoonBlessingConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_MOONBLESSING, "Moon Blessing Configuration");
        String[] defaultModifiers = new String[] {"MAX_HEALTH,0.1", "ATTACK_SPEED,0.2", "LUCK,0.1", "ATTACK_DAMAGE,5.5"};
        String[] modifiersFromConfig = cfg.getStringList("attributeModifiers", CATEGORY_MOONBLESSING, defaultModifiers, "Attribute Modifiers");

        for (String s : modifiersFromConfig) {
            String[] parts = s.split(",");
            attributeModifiers.put(parts[0], Double.parseDouble(parts[1]));
        }
    }
}

