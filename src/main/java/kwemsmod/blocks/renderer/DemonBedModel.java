package kwemsmod.blocks.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class DemonBedModel extends ModelBase {
    // Define the parts of your model here
    public ModelRenderer part1;
    public ModelRenderer part2;
    // ...

    public DemonBedModel() {
        // Initialize and position the parts of your model here
        this.part1 = new ModelRenderer(this, 0, 0);
        this.part1.addBox(-16.0F, -16.0F, 0.0F, 32, 32, 16);
        this.part1.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.part2 = new ModelRenderer(this, 0, 48);
        this.part2.addBox(-16.0F, -16.0F, -16.0F, 32, 32, 16);
        this.part2.setRotationPoint(0.0F, 0.0F, 0.0F);
        // ...
    }

    public void render() {
        // Render the parts of your model here
        this.part1.render(0.0625F);
        this.part2.render(0.0625F);
        // ...
    }
}
