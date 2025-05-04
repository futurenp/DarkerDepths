package com.naterbobber.darkerdepths.mixin;

import com.naterbobber.darkerdepths.init.DDBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Husk.class)
public class HuskMixin {

    @Inject(at = @At("HEAD"), method = "checkHuskSpawnRules", cancellable = true)
    private static void DD$checkHuskSpawnRules(EntityType<Husk> entityType, ServerLevelAccessor world, MobSpawnType mobSpawnType, BlockPos pos, RandomSource randomSource, CallbackInfoReturnable<Boolean> cir) {
        if (world.getBlockState(pos.below()).is(DDBlockTags.HUSKS_SPAWNABLE_ON)) {
            cir.setReturnValue(true);
        }
    }

}
