package com.naterbobber.darkerdepths.util;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Consumer;

public class BiomeReagentHandler {

    public static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
    public static final Climate.Parameter DEFAULT_CAVE_BIOME_RANGE = Climate.Parameter.span(0.2F, 0.9F);

    public static final ResourceKey<Biome> MOLTEN_CAVERN = register("molten_cavern");
    public static final ResourceKey<Biome> SANDY_CATACOMBS = register("sandy_catacombs");
    public static final ResourceKey<Biome> GLOWSHROOM_FOREST = register("glowshroom_forest");

    public static void init(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
        consumer.accept(Pair.of(Climate.parameters(FULL_RANGE, FULL_RANGE, Climate.Parameter.span(0.6F, 0.7F), Climate.Parameter.span(Climate.Parameter.span(-1.0F, -0.78F), Climate.Parameter.span(-0.78F, -0.375F)), DEFAULT_CAVE_BIOME_RANGE, FULL_RANGE, 0.0F), MOLTEN_CAVERN));
        consumer.accept(Pair.of(Climate.parameters(FULL_RANGE, Climate.Parameter.span(-0.1F, 0.3F), Climate.Parameter.span(-0.19F, 0.03F), Climate.Parameter.span(0.45F, 1.0F), DEFAULT_CAVE_BIOME_RANGE, FULL_RANGE, 0.0F), SANDY_CATACOMBS));
        consumer.accept(Pair.of(Climate.parameters(FULL_RANGE, FULL_RANGE, Climate.Parameter.span(-1.2F, -0.455F), Climate.Parameter.span(-1.0F, -0.375F), DEFAULT_CAVE_BIOME_RANGE, FULL_RANGE, 0.0F), GLOWSHROOM_FOREST));
        //beta
//        consumer.accept(Pair.of(Climate.parameters(Climate.Parameter.span(0.8F, 1.0F), Climate.Parameter.span(0.4F, 1.0F), FULL_RANGE, FULL_RANGE, DEFAULT_CAVE_BIOME_RANGE, FULL_RANGE, 0.0F), MOLTEN_CAVERN));
//        consumer.accept(Pair.of(Climate.parameters(Climate.Parameter.span(0.0F, 1.0F), Climate.Parameter.span(0.1F, 1.0F), Climate.Parameter.span(0.3F, 1.0F), Climate.Parameter.span(0.5F, 1.0F), DEFAULT_CAVE_BIOME_RANGE, FULL_RANGE, 0.0F), SANDY_CATACOMBS));
//        consumer.accept(Pair.of(Climate.parameters(Climate.Parameter.span(-1.0F, -0.5F), Climate.Parameter.span(0.5F, 1.0F), FULL_RANGE, Climate.Parameter.span(0.2F, 1.0F), DEFAULT_CAVE_BIOME_RANGE, Climate.Parameter.span(-0.5F, 0.1F), 0.0F), GLOWSHROOM_FOREST));
        //v1
//        consumer.accept(Pair.of(Climate.parameters(Climate.Parameter.span(0.2F, 1.0F), Climate.Parameter.span(-0.1F, 1.0F), Climate.Parameter.span(-0.11F, 0.8F), Climate.Parameter.span(-1.0F, -0.375F), DEFAULT_CAVE_BIOME_RANGE, FULL_RANGE, 0.0F), MOLTEN_CAVERN));
//        consumer.accept(Pair.of(Climate.parameters(Climate.Parameter.span(0.2F, 1.0F), Climate.Parameter.span(0.3F, 1.0F), Climate.Parameter.span(-0.11F, 0.8F), Climate.Parameter.span(-0.375F, 0.05F), DEFAULT_CAVE_BIOME_RANGE, FULL_RANGE, 0.0F), SANDY_CATACOMBS));
//        consumer.accept(Pair.of(Climate.parameters(Climate.Parameter.span(-1.0F, -0.15F), Climate.Parameter.span(-0.1F, 0.3F), Climate.Parameter.span(-0.11F, 0.8F), Climate.Parameter.span(0.45F, 1.0F),  DEFAULT_CAVE_BIOME_RANGE, FULL_RANGE, 0.0F), GLOWSHROOM_FOREST));
        //v2
//        consumer.accept(Pair.of(Climate.parameters(FULL_RANGE, FULL_RANGE, Climate.Parameter.span(-0.19F, 0.8F), Climate.Parameter.span(Climate.Parameter.span(-1.0F, -0.78F), Climate.Parameter.span(-0.78F, -0.375F)), DEFAULT_CAVE_BIOME_RANGE, FULL_RANGE, 0.0F), MOLTEN_CAVERN));
    }

    private static ResourceKey<Biome> register(String id) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DarkerDepths.MODID, id));
    }


}