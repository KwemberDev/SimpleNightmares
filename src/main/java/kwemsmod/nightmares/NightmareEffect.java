package kwemsmod.nightmares;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Random;

import static kwemsmod.config.Config.*;

public class NightmareEffect {

    private static final PotionEffect[] BAD_POTIONS = new PotionEffect[]{
            new PotionEffect(MobEffects.HUNGER, hungerDuration),
            new PotionEffect(MobEffects.MINING_FATIGUE, miningfatigueDuration),
            new PotionEffect(MobEffects.POISON, poisonDuration),
            new PotionEffect(MobEffects.SLOWNESS, slownessDuration),
            new PotionEffect(MobEffects.WEAKNESS, weaknessDuration),
            new PotionEffect(MobEffects.WITHER, witherDuration)
    };

    private static final String[] LYCAMOBS = new String[]{
            "lycanitesmobs:grue",
            "lycanitesmobs:reaper"
    };

    public static void applyRandomNightmare(EntityPlayerMP player) {
        float rand = new Random().nextFloat();

        if (Loader.isModLoaded("lycanitesmobs")) {
            if (rand < 0.8) {
                potionNightmare(player);
            } else {
                mobNightmare(player);
            }
        } else {
            potionNightmare(player);
        }

        player.sendStatusMessage(new TextComponentString(TextFormatting.DARK_RED + "\u00A7lYou had a nightmare!" + TextFormatting.RESET), true);
        player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_WITHER_AMBIENT, SoundCategory.PLAYERS, 1.0F, 1.0F);

    }

    public static void potionNightmare(EntityPlayerMP player) {
        Random rand = new Random();
        PotionEffect randomPotionEffect = BAD_POTIONS[rand.nextInt(BAD_POTIONS.length)];
        player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 80));
        player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100));
        player.addPotionEffect(randomPotionEffect);
    }

    public static void mobNightmare(EntityPlayerMP player) {
        // spawn lycanite mob
        Random rand = new Random();
        player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 80));
        player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100));
        Entity entity = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(LYCAMOBS[rand.nextInt(LYCAMOBS.length)])).newInstance(player.world);
        entity.setPosition(player.posX, player.posY+2, player.posZ);
        player.world.spawnEntity(entity);
    }
}

