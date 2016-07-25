package com.github.backtolifemod.backtolife.world.biome;

import net.minecraft.world.biome.BiomeMesa;

public class BiomeFlamingCliffsFossilSite extends BiomeMesa {

    public BiomeFlamingCliffsFossilSite() {
        super(true, false, new BiomeProperties("Flaming Cliffs Fossil Site").setRainDisabled().setSnowEnabled().setTemperature(1.5F).setBaseHeight(1.5F).setHeightVariation(0.025F).setTemperature(2.0F));
    }
}
