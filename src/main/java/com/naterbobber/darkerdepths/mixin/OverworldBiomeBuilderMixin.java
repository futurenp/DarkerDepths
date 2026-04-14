package com.naterbobber.darkerdepths.mixin;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.compat.DDCompat;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.worldgen.ClimateParamsUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public class OverworldBiomeBuilderMixin {

    @Inject(at = @At("RETURN"), method = "addUndergroundBiomes")
    public void addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, CallbackInfo ci) {
        if(DDConfig.DD_BIOME_INJECTION.get() && !DDCompat.TERRABLENDER.isLoaded()) {
            ClimateParamsUtil.createBiomes(consumer);
        }
    }
}
