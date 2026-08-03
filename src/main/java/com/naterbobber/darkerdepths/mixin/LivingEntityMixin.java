package com.naterbobber.darkerdepths.mixin;

import com.naterbobber.darkerdepths.block.custom.TombBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "setPosToBed", at = @At("HEAD"), cancellable = true)
    private void darkerdepths$setPosToBed(BlockPos pos, CallbackInfo ci) {
        var entity = (LivingEntity) (Object) this;
        var level = entity.level();
        var state = level.getBlockState(pos);

        if (state.getBlock() instanceof TombBlock) {
            var facing = state.getValue(HorizontalDirectionalBlock.FACING);

            double x = pos.getX() + 0.5 + (facing.getStepZ() - facing.getStepX()) * 0.5;
            double y = pos.getY() + 0.6875;
            double z = pos.getZ() + 0.5 - (facing.getStepZ() + facing.getStepX()) * 0.5;

            entity.setPos(x, y, z);
            ci.cancel();
        }
    }
}