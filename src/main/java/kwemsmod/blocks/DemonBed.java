package kwemsmod.blocks;

import net.minecraft.block.material.Material;

public class DemonBed extends BlockBase {

    public DemonBed(String name) {
        super(Material.ROCK, name);
        setHardness(3f);
        setResistance(5f);
    }

}