package kwemsmod.ambush;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import java.util.Random;
import java.util.function.Function;

public class Ambushes {
    private static final Random rand = new Random();

    public static void createAmbush(EntityPlayerMP player) {
        World world = player.world;
        int numZombies = 2 + rand.nextInt(3);  // Random number between 4 and 9
        int numSkeletons = rand.nextInt(2); // number of skeletons between 0 and 2
        int numHusks = 1 + rand.nextInt(3);

        player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 60));
        player.sendStatusMessage(new TextComponentString(TextFormatting.DARK_GREEN + "\u00A7lYou are being ambushed!" + TextFormatting.RESET), true);
        player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ZOMBIE_DEATH, SoundCategory.PLAYERS, 1.0F, 1.0F);

        if (Loader.isModLoaded("lycanitesmobs")) {
            Potion insomnia = Potion.getPotionFromResourceLocation("lycanitesmobs:insomnia");
            if (insomnia != null) {
                player.addPotionEffect(new PotionEffect(insomnia, 2400)); // 2 minutes
            }
        }


        for (int i = 0; i < numZombies; i++) {
            spawnEntityInValidLocation(player, world, EntityZombie::new);
        }

        for (int i = 0; i < numSkeletons; i++) {
            spawnEntityInValidLocation(player, world, EntitySkeleton::new);
        }

        for (int i = 0; i < numHusks; i++) {
            spawnEntityInValidLocation(player, world, EntityHusk::new);
        }
    }

    private static void spawnEntityInValidLocation(EntityPlayerMP player, World world, Function<World, EntityLiving> entityCreator) {
        int x, y, z;
        BlockPos pos;
        int attempts = 0;
        int maxAttempts = 300; // Maximum number of attempts to find a valid location

        do {
            double angle = rand.nextDouble() * 2 * Math.PI; // Random angle in radians
            int distance = rand.nextInt(8) + 5; // Random distance between 5 and 20 blocks from the player

            x = (int) (player.posX + distance * Math.cos(angle));
            z = (int) (player.posZ + distance * Math.sin(angle));
            y = (int) player.posY;

            pos = new BlockPos(x, y, z);
            attempts++;
        } while ((attempts < maxAttempts) && (!world.getBlockState(pos).getMaterial().isReplaceable() || !world.getBlockState(pos.down()).isOpaqueCube()));

        if (attempts < maxAttempts) {
            EntityLiving entity = entityCreator.apply(world);
            entity.setLocationAndAngles(x, y, z, rand.nextFloat() * 360.0F, 0.0F);
            if (entity instanceof EntitySkeleton) {
                ((EntitySkeleton) entity).setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            }
            world.spawnEntity(entity);
        }
    }
}
