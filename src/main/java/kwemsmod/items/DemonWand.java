package kwemsmod.items;

import kwemsmod.KwemsMod;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class DemonWand extends Item {
    private static final float damageAmount = 10;
    private static final float range = 10;
    public DemonWand() {
        setRegistryName("demonwand");
        setTranslationKey(KwemsMod.MODID + ".demonwand");
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote) { // We only want to run this on the server side
            Vec3d lookVec = playerIn.getLookVec(); // This is the direction the player is looking
            Vec3d startVec = new Vec3d(playerIn.posX, playerIn.posY + playerIn.getEyeHeight(), playerIn.posZ); // This is the player's current position
            Vec3d endVec = startVec.add(lookVec.scale(range)); // This is the position in front of the player, in the direction they are looking

            float boxWidth = 1.0F; // Width and height of the box. Adjust as needed.
            AxisAlignedBB playerViewCone = new AxisAlignedBB(startVec.x - boxWidth / 2, startVec.y - boxWidth / 2, startVec.z - boxWidth / 2, endVec.x + boxWidth / 2, endVec.y + boxWidth / 2, endVec.z + boxWidth / 2); // This creates a box from the player's position to the position in front of them

            List<Entity> entitiesInFront = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, playerViewCone); // This gets all entities within the box, excluding the player themselves

            for (Entity entity : entitiesInFront) {
                if (entity instanceof EntityLivingBase) { // Check if the entity is a living entity
                    RayTraceResult rayTraceResult = worldIn.rayTraceBlocks(startVec, new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ));
                    if (rayTraceResult == null || rayTraceResult.typeOfHit == RayTraceResult.Type.ENTITY) {
                        entity.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), damageAmount);
                    }
                }
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

}