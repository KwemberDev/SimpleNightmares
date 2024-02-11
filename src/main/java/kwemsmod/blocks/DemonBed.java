package kwemsmod.blocks;

import kwemsmod.KwemsMod;
import kwemsmod.blocks.renderer.TileEntityDemonBed;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

public class DemonBed extends BlockBed {
    private static final AxisAlignedBB HEAD_BOUNDING_BOX = new AxisAlignedBB(0, 0, 0, 1, 1.5625F, 1);
    private static final AxisAlignedBB BASE_BOUNDING_BOX = new AxisAlignedBB(0,0,0,1,1.5625F,1);
    public DemonBed() {
        super();
        setTranslationKey(KwemsMod.MODID + ".demonbed");     // Used for localization and naming (en_us.lang)
        setRegistryName("demonbed");        // The unique name (within the mod) that identifies this block
        this.setCreativeTab(CreativeTabs.MISC);
    }
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        EnumFacing enumfacing = placer.getHorizontalFacing();
        state = state.withProperty(FACING, enumfacing).withProperty(PART, BlockBed.EnumPartType.FOOT);
        if (worldIn.isAirBlock(pos.offset(enumfacing))) {
            worldIn.setBlockState(pos, state, 10);
            worldIn.setBlockState(pos.offset(enumfacing), state.withProperty(PART, BlockBed.EnumPartType.HEAD), 10);
        } else {
            // If the head can't be placed, also remove the base
            worldIn.setBlockToAir(pos);
            stack.grow(1); // Give the bed item back to the player, surprising how it doesnt do that automatically
        }
    }
    @Override
    public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity player) {
        return true;
    }
    @Override
    public TileEntity createNewTileEntity(final World worldIn, final int meta) {
        return new TileEntityDemonBed();
    }
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this));
    }
    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if (!worldIn.isRemote && !state.getValue(PART).equals(BlockBed.EnumPartType.FOOT)) {
            EntityItem dropItem = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Item.getItemFromBlock(this)));
            worldIn.spawnEntity(dropItem);
        }
    }
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        AxisAlignedBB boundingBox;
        if (state.getValue(PART) == BlockBed.EnumPartType.HEAD) {
            boundingBox = HEAD_BOUNDING_BOX;
        } else {
            boundingBox = BASE_BOUNDING_BOX;
        }

        switch (state.getValue(FACING)) {
            case EAST:
                boundingBox = rotate(boundingBox, -90);
                break;
            case SOUTH:
                boundingBox = rotate(boundingBox, -180);
                break;
            case WEST:
                boundingBox = rotate(boundingBox, -270);
                break;
            default:
                break;
        }

        return boundingBox;
    }
    private AxisAlignedBB rotate(AxisAlignedBB box, double angle) {
        Vec3d center = new Vec3d(0.5, 1, 0.5);
        angle = Math.toRadians(angle);

        return new AxisAlignedBB(
                center.x + (box.minX - center.x) * Math.cos(angle) - (box.minZ - center.z) * Math.sin(angle),
                box.minY,
                center.z + (box.minX - center.x) * Math.sin(angle) + (box.minZ - center.z) * Math.cos(angle),
                center.x + (box.maxX - center.x) * Math.cos(angle) - (box.maxZ - center.z) * Math.sin(angle),
                box.maxY,
                center.z + (box.maxX - center.x) * Math.sin(angle) + (box.maxZ - center.z) * Math.cos(angle)
        );
    }
}