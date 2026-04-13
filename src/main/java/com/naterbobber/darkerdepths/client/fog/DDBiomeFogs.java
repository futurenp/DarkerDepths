package com.naterbobber.darkerdepths.client.fog;

import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import software.bernie.geckolib.util.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DDBiomeFogs {
    public static final List<BiomeFog> BIOME_FOGS = new ArrayList<>();
    public static final BiomeFog MOLTEN_CAVERN = create(DDResourceKeys.Biomes.MOLTEN_CAVERN,
            Color.ofRGB(0.42F, 0.27F, 0.18F),
            DDConfig.CONFIG.MOLTEN_CAVERN_FOG_MIN,
            DDConfig.CONFIG.MOLTEN_CAVERN_FOG_MAX
    );

    public static final BiomeFog SANDY_CATACOMBS = create(DDResourceKeys.Biomes.SANDY_CATACOMBS,
            Color.ofRGB(0.22F, 0.13F, 0.10F),
            DDConfig.CONFIG.SANDY_CATACOMBS_FOG_MIN,
            DDConfig.CONFIG.SANDY_CATACOMBS_FOG_MAX
    );

    public static final BiomeFog GLOWSHROOM_FOREST = create(DDResourceKeys.Biomes.GLOWSHROOM_FOREST,
            Color.ofRGB(0.16F, 0.34F, 0.24F),
            DDConfig.CONFIG.GLOWSHROOM_FOREST_FOG_MIN,
            DDConfig.CONFIG.GLOWSHROOM_FOREST_FOG_MAX
    );

    public static final BiomeFog CHALK_CAVES = create(DDResourceKeys.Biomes.CHALK_CAVES,
            Color.ofRGB(0.28F, 0.26F, 0.3F),
            () -> 0,
            () -> 72
    );

    private static BiomeFog create(ResourceKey<Biome> biome, Color color, Supplier<Integer> minDist, Supplier<Integer> maxDist) {
        var biomeFog = new BiomeFog(biome, color, minDist, maxDist);
        BIOME_FOGS.add(biomeFog);
        return biomeFog;
    }
}
