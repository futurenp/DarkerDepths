package com.naterbobber.darkerdepths.mixin;

import com.naterbobber.darkerdepths.client.DynamicLightHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockGetter.class)
public interface BlockGetterMixin {

    @Shadow BlockState getBlockState(BlockPos pos);

    /**
     * @author Blackgear
     * @reason Insert a reason here
     */
    @Overwrite
    default int getLightEmission(BlockPos pos) {
        if (DynamicLightHandler.LIGHT_SOURCES.containsKey(pos) && DynamicLightHandler.LIGHT_SOURCES.get(pos).shouldKeep) {
            return 10;
        }
        return this.getBlockState(pos).getLightEmission((BlockGetter) this, pos);
    }

}
