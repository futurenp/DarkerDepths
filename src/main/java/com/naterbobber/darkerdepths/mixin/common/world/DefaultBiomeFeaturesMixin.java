package com.naterbobber.darkerdepths.mixin.common.world;

import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//<>

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {
    @Inject(method = "withCommonOverworldBlocks", at = @At("HEAD"), cancellable = true)
    private static void withCommonOverworldBlocks(BiomeGenerationSettings.Builder builder, CallbackInfo ci) {
        ci.cancel();
    }
}