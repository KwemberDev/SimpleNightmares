package SimpleNightmares.nightmares;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class NightEvent extends PlayerEvent {
    private boolean nightmare;
    private boolean ambush;

    public NightEvent(EntityPlayer player, boolean nightmare, boolean ambush) {
        super(player);
        this.nightmare = nightmare;
        this.ambush = ambush;
    }

    public boolean isNightmare() {
        return this.nightmare;
    }

    public void setNightmare(boolean nightmare) {
        this.nightmare = nightmare;
    }

    public boolean isAmbush() {
        return this.ambush;
    }

    public void setAmbush(boolean ambush) {
        this.ambush = ambush;
    }
}
