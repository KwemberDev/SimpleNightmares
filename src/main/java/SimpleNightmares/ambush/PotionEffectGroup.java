package SimpleNightmares.ambush;

public class PotionEffectGroup {
    public double chance;
    public String effectId;
    public int duration;
    public int level;

    public PotionEffectGroup() {
    }

    public PotionEffectGroup(double chance, String effectId, int duration, int level) {
        this.chance = chance;
        this.effectId = effectId;
        this.duration = duration;
        this.level = level;
    }
}
