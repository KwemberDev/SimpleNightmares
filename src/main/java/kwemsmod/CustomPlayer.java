package kwemsmod;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CustomPlayer extends EntityPlayer {
    public CustomPlayer(World worldIn, GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }
    // Constructor and other methods...

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.isPlayerSleeping()) {
            // Adjust the player's sleeping height here
            this.setPosition(this.posX + 3F, this.posY + 10F, this.posZ + 0.5F);
        }
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
