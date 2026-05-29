package com.naterbobber.darkerdepths.mixin;

import com.naterbobber.darkerdepths.client.DynamicLightHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.BlockLightEngine;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@OnlyIn(Dist.CLIENT)
@Mixin(BlockLightEngine.class)
public class BlockLightEngineMixin {

    @Inject(at = @At("HEAD"), method = "getEmission", cancellable = true)
    private void DD$getEmission(long sectionPos, BlockState state, CallbackInfoReturnable<Integer> cir) {
        BlockPos blockPos = BlockPos.of(sectionPos);
        if (DynamicLightHandler.LIGHT_SOURCES.containsKey(blockPos) && DynamicLightHandler.LIGHT_SOURCES.get(blockPos)) {
            cir.setReturnValue(12);
        }
    }

}
