package kwemsmod.blocks.renderer.wroughtironbed;

import kwemsmod.blocks.renderer.boorealbed.TileEntityBorealBed;
import net.minecraft.client.model.ModelBed;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityWroughtIronBedRenderer extends TileEntitySpecialRenderer<TileEntityWroughIronBed> {
    private final ModelBed model = new ModelBed();
    private static final ResourceLocation TEXTURE = new ResourceLocation("kwemsmod", "textures/blocks/wroughtironbed.png");

    @Override
    public void render(TileEntityWroughIronBed te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (te.getWorld() != null) {
            this.bindTexture(TEXTURE);
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x, (float)y + 0.5625F, (float)z);
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(1F, 1F, 1F);
            this.model.render();
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }
}

