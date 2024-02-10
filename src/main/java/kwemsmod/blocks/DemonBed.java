package kwemsmod.blocks;

import kwemsmod.KwemsMod;
import kwemsmod.blocks.renderer.TileEntityDemonBed;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

public class DemonBed extends BlockBed {
    public DemonBed() {
        super();
        setTranslationKey(KwemsMod.MODID + ".demonbed");     // Used for localization (en_us.lang)
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
            stack.grow(1); // Give the bed item back to the player
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
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}