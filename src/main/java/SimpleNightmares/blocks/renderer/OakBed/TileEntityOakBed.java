package SimpleNightmares.blocks.renderer.OakBed;

import net.minecraft.block.BlockBed;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityOakBed extends TileEntity {
    private EnumDyeColor color = EnumDyeColor.RED;


    public void setItemValues(ItemStack p_193051_1_)
    {
        this.setColor(EnumDyeColor.byMetadata(p_193051_1_.getMetadata()));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (compound.hasKey("color"))
        {
            this.color = EnumDyeColor.byMetadata(compound.getInteger("color"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("color", this.color.getMetadata());
        return compound;
    }
    @Override
    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }
    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 11, this.getUpdateTag());
    }

    public EnumDyeColor getColor()
    {
        return this.color;
    }

    public void setColor(EnumDyeColor color)
    {
        this.color = color;
        this.markDirty();
    }

    @SideOnly(Side.CLIENT)
    public boolean isHeadPiece()
    {
        return BlockBed.isHeadPiece(this.getBlockMetadata());
    }

    public ItemStack getItemStack()
    {
        return new ItemStack(Items.BED, 1, this.color.getMetadata());
    }
}