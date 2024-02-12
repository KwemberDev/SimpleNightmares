package kwemsmod;

import com.mojang.authlib.GameProfile;
import kwemsmod.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModdedEntityPlayer extends EntityPlayer {
    public ModdedEntityPlayer(World worldIn, GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }
    // Your class constructor and other methods go here

    @Override
    public SleepResult trySleep(BlockPos bedLocation) {
        // Your custom logic for trying to sleep goes here
        IBlockState state = this.world.getBlockState(bedLocation);
        Block bed = state.getBlock();

        int heightOffset = 0; // default height offset

        if (bed == ModBlocks.woodencanopybed) {
            heightOffset = -4;
            return super.trySleep(bedLocation.add(0, heightOffset, 0));
        } // add more conditions as needed
        // Check the type of bed and set the height offset accordingly
        if (bed == ModBlocks.borealbed) {
            heightOffset = -4;
            return super.trySleep(bedLocation.add(0, heightOffset, 0));
        }


        return super.trySleep(bedLocation.add(0, heightOffset, 0));
    }
    @Override
    public void setPosition(double x, double y, double z) {
        super.setPosition(x, y, z);
        if (this.isPlayerSleeping()) {
            this.bedLocation = new BlockPos(x, y, z);
            this.sendPlayerAbilities();
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
