package com.naterbobber.darkerdepths.mixin.common.world.biome.provider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.naterbobber.darkerdepths.common.world.gen.layer.CaveLayerUtil;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.layer.Layer;

//<>

/**
 * @author BlackGear27
 */
@Mixin(OverworldBiomeProvider.class)
public class OverworldBiomeProviderMixin {
    private Layer caveLayer;

    @Inject(at = @At("RETURN"), method = "<init>(JZZ)V")
    private void biomeprovider(long seed, boolean legacyBiomes, boolean largeBiomes, CallbackInfo info) {
        this.caveLayer = CaveLayerUtil.createLayers(seed);
    }

    @Inject(at = @At("RETURN"), method = "getNoiseBiome(III)Lnet/minecraft/world/biome/Biome;", cancellable = true)
    private void caveBiomeManager(int x, int y, int z, CallbackInfoReturnable<Biome> info) {
        Biome result = info.getReturnValue();
        if (y > 7) {
            info.setReturnValue(result);
        } else {
            info.setReturnValue(this.caveLayer.func_215738_a(x, z));
        }
    }
}