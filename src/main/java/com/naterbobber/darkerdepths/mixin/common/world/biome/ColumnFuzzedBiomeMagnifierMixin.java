package com.naterbobber.darkerdepths.mixin.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.ColumnFuzzedBiomeMagnifier;
import net.minecraft.world.biome.FuzzedBiomeMagnifier;
import net.minecraft.world.biome.BiomeManager.IBiomeReader;

//<>

/**
* @author CorgiTaco
*/
@Mixin(ColumnFuzzedBiomeMagnifier.class)
public class ColumnFuzzedBiomeMagnifierMixin {
	@Inject(method = "getBiome", at = @At("HEAD"), cancellable = true)
	public void getBiome(long seed, int x, int y, int z, IBiomeReader biomeReader, CallbackInfoReturnable<Biome> cir) {
		cir.setReturnValue(FuzzedBiomeMagnifier.INSTANCE.getBiome(seed, x, y, z, biomeReader));
	}
}