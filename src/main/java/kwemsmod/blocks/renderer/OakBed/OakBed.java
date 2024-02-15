package kwemsmod.blocks.renderer.OakBed;

import kwemsmod.KwemsMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
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

import java.util.HashMap;
import java.util.Map;

public class OakBed extends BlockBed {
    private static final AxisAlignedBB HEAD_BOUNDING_BOX = new AxisAlignedBB(0, 0, 0, 1, 0.4375F, 1);
    private static final AxisAlignedBB BASE_BOUNDING_BOX = new AxisAlignedBB(0,0,0,1,0.4375F,1);
    public OakBed(EnumDyeColor color) {
        super();
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
        return new TileEntityOakBed();
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
    // for colors

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        if (state.getValue(PART) == BlockBed.EnumPartType.FOOT)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityBed)
            {
                EnumDyeColor enumdyecolor = ((TileEntityBed)tileentity).getColor();
                return MapColor.getBlockColor(enumdyecolor);
            }
        }

        return MapColor.CLOTH;
    }
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        BlockPos blockpos = pos;

        if (state.getValue(PART) == BlockBed.EnumPartType.FOOT)
        {
            blockpos = pos.offset((EnumFacing)state.getValue(FACING));
        }

        TileEntity tileentity = worldIn.getTileEntity(blockpos);
        EnumDyeColor enumdyecolor = tileentity instanceof TileEntityBed ? ((TileEntityBed)tileentity).getColor() : EnumDyeColor.RED;
        return new ItemStack(Items.BED, 1, enumdyecolor.getMetadata());
    }
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if (player.capabilities.isCreativeMode && state.getValue(PART) == BlockBed.EnumPartType.FOOT)
        {
            BlockPos blockpos = pos.offset((EnumFacing)state.getValue(FACING));

            if (worldIn.getBlockState(blockpos).getBlock() == this)
            {
                worldIn.setBlockToAir(blockpos);
            }
        }
    }
    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
    {
        if (state.getValue(PART) == BlockBed.EnumPartType.HEAD && te instanceof TileEntityBed)
        {
            TileEntityBed tileentitybed = (TileEntityBed)te;
            ItemStack itemstack = tileentitybed.getItemStack();
            spawnAsEntity(worldIn, pos, itemstack);
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, (TileEntity)null, stack);
        }
    }
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.byHorizontalIndex(meta);
        return (meta & 8) > 0 ? this.getDefaultState().withProperty(PART, BlockBed.EnumPartType.HEAD).withProperty(FACING, enumfacing).withProperty(OCCUPIED, Boolean.valueOf((meta & 4) > 0)) : this.getDefaultState().withProperty(PART, BlockBed.EnumPartType.FOOT).withProperty(FACING, enumfacing);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        if (state.getValue(PART) == BlockBed.EnumPartType.FOOT)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.offset((EnumFacing)state.getValue(FACING)));

            if (iblockstate.getBlock() == this)
            {
                state = state.withProperty(OCCUPIED, iblockstate.getValue(OCCUPIED));
            }
        }

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();

        if (state.getValue(PART) == BlockBed.EnumPartType.HEAD)
        {
            i |= 8;

            if (((Boolean)state.getValue(OCCUPIED)).booleanValue())
            {
                i |= 4;
            }
        }

        return i;
    }

    public static final Map<EnumDyeColor, Block> BEDS = new HashMap<>();

    public static Block get(EnumDyeColor color) {
        return BEDS.get(color);
    }
}