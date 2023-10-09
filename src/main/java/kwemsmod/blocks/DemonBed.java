package kwemsmod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.util.FakePlayer;


public class DemonBed extends BlockBase {

    public DemonBed(String name) {
        super(Material.ROCK, name);
        setHardness(3f);
        setResistance(-1f);
        setCreativeTab(CreativeTabs.DECORATIONS);
    }

    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    // Indicate that this block can be used as a bed
    public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, Entity player) {
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // Handle block activation here, e.g., opening a GUI or doing custom logic.
        if (!(player instanceof FakePlayer)) {
            // Your logic here
        }
        return true;
    }

}