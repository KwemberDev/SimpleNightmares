package SimpleNightmares.effects;

import SimpleNightmares.config.Config;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;

import java.util.Map;

public class BlessingOfTheFullMoonEffect extends Potion {
    public BlessingOfTheFullMoonEffect(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        this.setPotionName("\u00A7bLunar Blessing");

        for (Map.Entry<String, Double> entry : Config.attributeModifiers.entrySet()) {
            String attributeName = entry.getKey();
            double modifierAmount = entry.getValue();

            if (attributeName.equals("MAX_HEALTH")) {
                this.registerPotionAttributeModifier(SharedMonsterAttributes.MAX_HEALTH, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", modifierAmount, 0);
            } else if (attributeName.equals("ATTACK_DAMAGE")) {
                this.registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "5D6F0BA2-1186-46AC-B896-C61C5CEE99C2", modifierAmount, 0);
            } else if (attributeName.equals("ATTACK_SPEED")) {
                this.registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, "5D6F0BA2-1186-46AC-B896-C61C5CEE99C3", modifierAmount, 0);
            } else if (attributeName.equals("LUCK")) {
                this.registerPotionAttributeModifier(SharedMonsterAttributes.LUCK, "5D6F0BA2-1186-46AC-B896-C61C5CEE99C4", modifierAmount, 0);
            }
        }
    }
}

