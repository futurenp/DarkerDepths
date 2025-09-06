package com.naterbobber.darkerdepths.util;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Consumer;

public class BiomeReagentHandler {
    public static final ResourceKey<Biome> MOLTEN_CAVERN = register("molten_cavern");
    public static final ResourceKey<Biome> SANDY_CATACOMBS = register("sandy_catacombs");
    public static final ResourceKey<Biome> GLOWSHROOM_FOREST = register("glowshroom_forest");

    public static void init(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
        consumer.accept(Pair.of(Climate.parameters(
                        Climate.Parameter.span(0.5F, 2.0F),
                        Climate.Parameter.span(-1.0F, 0.0F),
                        Climate.Parameter.span(0.0F, 0.3F),
                        Climate.Parameter.span(-1.0F, 1.0F),
                        Climate.Parameter.span(0.2F, 1F),
                        Climate.Parameter.span(-1.0F, 1.0F),
                        0.0F),
                SANDY_CATACOMBS)
        );

        consumer.accept(Pair.of(Climate.parameters(
                        Climate.Parameter.span(-0.5F, 1.0F),
                        Climate.Parameter.span(0.0F, 2.0F),
                        Climate.Parameter.span(-0.2F, 0.2F),
                        Climate.Parameter.span(-1.0F, -0.375F),
                        Climate.Parameter.span(0.2F, 0.5F),
                        Climate.Parameter.span(-1.0F, 1.0F),
                        0.0F),
                GLOWSHROOM_FOREST)
        );

        consumer.accept(Pair.of(Climate.parameters(
                        Climate.Parameter.span(0.0F, 1.0F),
                        Climate.Parameter.span(-1.0F, 0.0F),
                        Climate.Parameter.span(0.4F, 0.9F),
                        Climate.Parameter.span(0.3F, 2.0F),
                        Climate.Parameter.span(0.5F, 2.0F),
                        Climate.Parameter.span(-1.0F, 1.0F),
                        0.0F),
                MOLTEN_CAVERN)
        );
    }

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, DarkerDepths.id(name));
    }
}
