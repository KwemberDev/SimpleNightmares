package SimpleNightmares.config;

import java.util.ArrayList;
import java.util.List;

public class MobGroup {
    public double weight;
    public List<MobInfo> mobs = new ArrayList<>();

    public static class MobInfo {
        public double chance;
        public String mobId;
        public int min;
        public int max;
    }
}
