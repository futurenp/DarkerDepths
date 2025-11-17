package com.naterbobber.darkerdepths.mixin;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.init.DDBiomes;
import com.naterbobber.darkerdepths.util.BiomeReagentHandler;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public class OverworldBiomeBuilderMixin {

//    @Inject(at = @At("RETURN"), method = "addUndergroundBiomes")
//    public void addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, CallbackInfo ci) {
//        BiomeReagentHandler.init(consumer);
//    }

    @Inject(at = @At("RETURN"), method = "addUndergroundBiomes")
    private void addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, CallbackInfo ci) {
        // Sandy Catacombs - warm, dry, deep underground
        consumer.accept(Pair.of(
                Climate.parameters(
                        Climate.Parameter.span(0.5F, 1.2F),      // temperature: warm
                        Climate.Parameter.span(-1.0F, -0.4F),    // humidity: dry
                        Climate.Parameter.span(0.1F, 0.3F),      // continentalness
                        Climate.Parameter.span(-0.25F, 1.0F),    // erosion
                        Climate.Parameter.span(-1.0F, 1.0F),     // depth: underground
                        Climate.Parameter.span(0.3F, 2.0F),      // weirdness
                        0.0F                                      // offset
                ),
                DDBiomes.SANDY_CATACOMBS
        ));

        // Glowshroom Forest - moderate temperature, humid, deep underground
        consumer.accept(Pair.of(
                Climate.parameters(
                        Climate.Parameter.span(0.4F, 1.0F),      // temperature: moderate to warm
                        Climate.Parameter.span(0.6F, 1.2F),      // humidity: humid
                        Climate.Parameter.span(-0.5F, 0.5F),     // continentalness
                        Climate.Parameter.span(0.1F, 0.5F),      // erosion
                        Climate.Parameter.span(-1.0F, 1.0F),     // depth: underground
                        Climate.Parameter.span(0.3F, 0.9F),      // weirdness
                        0.0F                                      // offset
                ),
                DDBiomes.GLOWSHROOM_FOREST
        ));

        // Molten Cavern - hot, dry, deep underground
        consumer.accept(Pair.of(
                Climate.parameters(
                        Climate.Parameter.span(-0.75F, 0.75F),   // temperature: very hot
                        Climate.Parameter.span(-1.0F, 1.0F),     // humidity: any
                        Climate.Parameter.span(0.65F, 0.8F),     // continentalness
                        Climate.Parameter.span(-0.325F, 0.0F),   // erosion
                        Climate.Parameter.span(-1.0F, 1.0F),     // depth: deep underground
                        Climate.Parameter.span(0.8F, 2.0F),      // weirdness: high
                        0.0F                                      // offset
                ),
                DDBiomes.MOLTEN_CAVERN
        ));
    }

}
