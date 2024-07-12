package SimpleNightmares.ambush;

import SimpleNightmares.config.MobGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import java.util.Random;
import java.util.function.Function;

import static SimpleNightmares.config.Config.*;

public class Ambushes {
    private static final Random rand = new Random();

    public static void createAmbush(EntityPlayerMP player) {
        World world = player.world;

        player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 60));
        player.sendStatusMessage(new TextComponentString(TextFormatting.DARK_GREEN + "\u00A7lYou are being ambushed!" + TextFormatting.RESET), true);
        player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ZOMBIE_ATTACK_DOOR_WOOD, SoundCategory.PLAYERS, 0.6F, 1.0F);
        player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ZOMBIE_DEATH, SoundCategory.PLAYERS, 0.9F, 1.0F);
        player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.PLAYERS, 0.9F, 1.0F);

        for (PotionEffectGroup effectGroup : potionAmbushEffects) {
            if (Math.random() < effectGroup.chance) {
                String[] effectParts = effectGroup.effectId.split(":");
                if (Loader.isModLoaded(effectParts[0])) {
                    Potion potion = Potion.getPotionFromResourceLocation(effectGroup.effectId);
                    if (potion != null) {
                        player.addPotionEffect(new PotionEffect(potion, effectGroup.duration, effectGroup.level));
                    }
                }
            }
        }

        // Choose a group based on weight
        double totalWeight = mobGroups.stream().mapToDouble(g -> g.weight).sum();
        System.out.println(totalWeight);
        double value = rand.nextDouble() * totalWeight;
        for (MobGroup group : mobGroups) {
            value -= group.weight;
            if (value < 0) {
                System.out.println(group);
                // Spawn mobs from this group
                for (MobGroup.MobInfo mobInfo : group.mobs) {
                    System.out.println(mobInfo);
                    System.out.println(mobInfo.mobId);
                    System.out.println(mobInfo.chance);
                    System.out.println(mobInfo.min);
                    System.out.println(mobInfo.max);
                    if (rand.nextDouble() < mobInfo.chance) {
                        int count = rand.nextInt(mobInfo.max - mobInfo.min + 1) + mobInfo.min;
                        for (int i = 0; i < count; i++) {
                            System.out.println("tried to spawn " + mobInfo.mobId);
                            spawnEntityInValidLocation(player, world, getEntityCreator(mobInfo.mobId));
                        }
                    }
                }
                break;
            }
        }
    }


    private static void spawnEntityInValidLocation(EntityPlayerMP player, World world, Function<World, EntityLiving> entityCreator) {
        System.out.println("TRIED TO SPAWN MOB");
        int x, y, z;
        BlockPos pos;
        int attempts = 0;
        int maxAttempts = 500; // Maximum number of attempts to find a valid location

        do {
            double angle = rand.nextDouble() * 2 * Math.PI; // Random angle in radians
            int distance = rand.nextInt(distanceFromPlayerMax - distanceFromPlayerMin) + distanceFromPlayerMin; // Random distance from the player. within the config range.

            x = (int) (player.posX + distance * Math.cos(angle));
            z = (int) (player.posZ + distance * Math.sin(angle));
            y = (int) player.posY + rand.nextInt(maxYLevelAbovePlayer + maxYLevelBelowPlayer) - maxYLevelBelowPlayer;

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

    private static Function<World, EntityLiving> getEntityCreator(String mobId) {
        return world -> {
            Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation(mobId), world);
            if (entity instanceof EntityLiving) {
                return (EntityLiving) entity;
            } else {
                throw new IllegalArgumentException("The mob ID " + mobId + " does not correspond to a living entity.");
            }
        };
    }
}
