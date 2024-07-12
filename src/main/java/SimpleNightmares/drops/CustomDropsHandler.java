package SimpleNightmares.drops;

import SimpleNightmares.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static SimpleNightmares.config.Config.dropMoonFragmentChance;

import java.util.Random;

import java.util.Random;

public class CustomDropsHandler {
    private final Random random = new Random();

    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event) {
        if (event.getEntity() instanceof EntityMob) { // Check if the entity is a hostile mob
            World world = event.getEntity().getEntityWorld();
            boolean isNight = !world.isDaytime(); // Check if it's night time

            // Get the current moon phase factor
            float moonPhaseFactor = world.getCurrentMoonPhaseFactor();

            if (isNight && random.nextFloat() < dropMoonFragmentChance * moonPhaseFactor) { // chance to drop during night time scales with moon phase
                event.getDrops().add(new EntityItem(world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, new ItemStack(ModItems.moonFragment)));
            }
        }
    }
}


