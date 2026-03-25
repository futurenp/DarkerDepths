package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.config.builders.DDBiomeConfigBuilder;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import com.naterbobber.darkerdepths.worldgen.ClimateParamsUtil;
import com.naterbobber.darkerdepths.worldgen.surfacerules.DDSurfaceRules;
import com.naterbobber.darkerdepths.worldgen.surfacerules.RepeatingYPatternRule;
import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.surface.SurfaceGeneration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.material.MaterialRuleList;

import java.util.List;

public class DDBiolithIntegration {

    public static void init() {
        BiomePlacement.addOverworld(DDResourceKeys.Biomes.SANDY_CATACOMBS,
                ClimateParamsUtil.climateParamsFromConfig(DDConfig.CONFIG.SANDY_CATACOMBS_CLIMATE));

        BiomePlacement.addOverworld(DDResourceKeys.Biomes.GLOWSHROOM_FOREST,
                ClimateParamsUtil.climateParamsFromConfig(DDConfig.CONFIG.GLOWSHROOM_FOREST_CLIMATE));

        BiomePlacement.addOverworld(DDResourceKeys.Biomes.MOLTEN_CAVERN,
                ClimateParamsUtil.climateParamsFromConfig(DDConfig.CONFIG.MOLTEN_CAVERN_CLIMATE));

        SurfaceGeneration.addOverworldSurfaceRules(
                DarkerDepths.id("rules/overworld"),
                DDSurfaceRules.makeRules()
        );
    }
}
