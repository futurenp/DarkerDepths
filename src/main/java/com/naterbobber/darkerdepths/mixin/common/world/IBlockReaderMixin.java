package com.naterbobber.darkerdepths.mixin.common.world;

import com.naterbobber.darkerdepths.common.events.DynamicLightHandler;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

//<>

@Mixin(value = IBlockReader.class, priority = 124)
public interface IBlockReaderMixin {
    @Shadow
    BlockState getBlockState(BlockPos pos);

    /**
     * @author
     */
    @Overwrite
    default int getLightValue(BlockPos pos) {
        if (DynamicLightHandler.LIGHT_SOURCES.containsKey(pos) && DynamicLightHandler.LIGHT_SOURCES.get(pos).shouldKeep) {
            return 10;
        }
        return this.getBlockState(pos).getLightValue((IBlockReader) this, pos);
    }
}