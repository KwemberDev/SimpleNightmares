package SimpleNightmares.effects;

import SimpleNightmares.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

import static SimpleNightmares.SimpleNightmares.MODID;

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


    ResourceLocation icon = new ResourceLocation(MODID +":textures/gui/rnblessing.png");

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha)
    {
        mc.getTextureManager().bindTexture(icon);

        Gui.drawModalRectWithCustomSizedTexture(x+3, y+3, 0, 0, 16, 16, 16, 16);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {
        mc.getTextureManager().bindTexture(icon);
        Gui.drawModalRectWithCustomSizedTexture(x+8, y+8, 0, 0, 16, 16, 16, 16);
    }
}

