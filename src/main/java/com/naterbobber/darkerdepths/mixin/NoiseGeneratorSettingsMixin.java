package com.naterbobber.darkerdepths.mixin;

import com.naterbobber.darkerdepths.compat.DDCompat;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.worldgen.surfacerules.DDSurfaceRules;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseGeneratorSettings.class)
public class NoiseGeneratorSettingsMixin {

    @Inject(method = "surfaceRule", at = @At("RETURN"), cancellable = true)
    private void injectCustomSurfaceRules(CallbackInfoReturnable<SurfaceRules.RuleSource> cir) {
        if(!DDConfig.DD_BIOME_INJECTION.get() || DDCompat.TERRABLENDER.isLoaded()) return;
        SurfaceRules.RuleSource originalRules = cir.getReturnValue();
        SurfaceRules.RuleSource customRules = DDSurfaceRules.makeRules();
        SurfaceRules.RuleSource sequencedRules = SurfaceRules.sequence(customRules, originalRules);
        cir.setReturnValue(sequencedRules);
    }
}