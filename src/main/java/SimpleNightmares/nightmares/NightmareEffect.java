package SimpleNightmares.nightmares;

import SimpleNightmares.ambush.PotionEffectGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Random;

import static SimpleNightmares.config.Config.*;

public class NightmareEffect {

    public static void applyRandomNightmare(EntityPlayerMP player) {
        potionNightmare(player);

        player.sendStatusMessage(new TextComponentString(TextFormatting.DARK_RED + "\u00A7lYou had a nightmare!" + TextFormatting.RESET), true);
        player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_WITHER_AMBIENT, SoundCategory.PLAYERS, 0.3F, 1.0F);
        player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    public static void potionNightmare(EntityPlayerMP player) {
        Random rand = new Random();
        player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 80));
        player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100));

        for (PotionEffectGroup effectGroup : potionNightmareEffects) {
            if (rand.nextFloat() < effectGroup.chance) {
                String[] effectParts = effectGroup.effectId.split(":");
                if (Loader.isModLoaded(effectParts[0])) {
                    Potion potion = Potion.getPotionFromResourceLocation(effectGroup.effectId);
                    if (potion != null) {
                        player.addPotionEffect(new PotionEffect(potion, effectGroup.duration, effectGroup.level));
                    }
                }
            }
        }
    }

}

