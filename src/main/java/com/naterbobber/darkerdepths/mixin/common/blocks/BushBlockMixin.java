package com.naterbobber.darkerdepths.mixin.common.blocks;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//<>

@Mixin(BushBlock.class)
public class BushBlockMixin {
    @Inject(at = @At("RETURN"), method = "isValidGround", cancellable = true)
    private void isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(state.isIn(Blocks.GRASS_BLOCK) || state.isIn(Blocks.DIRT) || state.isIn(Blocks.COARSE_DIRT) || state.isIn(Blocks.PODZOL) || state.isIn(Blocks.FARMLAND) || state.isIn(DDBlocks.LUSH_ARIDROCK.get()));
    }
}