package com.naterbobber.darkerdepths.mixin.common.world;

import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.gen.carver.WorldCarver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//<>

@Mixin(WorldCarver.class)
public class WorldCarverMixin {
    @Inject(method = "canCarveBlock", at = @At("HEAD"), cancellable = true)
    protected void isCarvable(BlockState state, BlockState aboveState, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(state.isIn(BlockTags.BASE_STONE_OVERWORLD));
    }
}