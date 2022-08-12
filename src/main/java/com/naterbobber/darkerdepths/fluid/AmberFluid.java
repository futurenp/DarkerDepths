package com.naterbobber.darkerdepths.fluid;

import com.naterbobber.darkerdepths.init.DDFluidTypes;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDFluids;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class AmberFluid extends FlowingFluid {

    @Override
    public FluidType getFluidType() {
        return DDFluidTypes.AMBER_FLUID_TYPE.get();
    }

    public Fluid getFlowing() {
        return DDFluids.FLOWING_AMBER.get();
    }

    public Fluid getSource() {
        return DDFluids.AMBER.get();
    }

    public Item getBucket() {
        return DDItems.AMBER_BUCKET.get();
    }

    public void animateTick(Level world, BlockPos pos, FluidState fluidState, RandomSource random) {
        BlockPos blockpos = pos.above();
        if (world.getBlockState(blockpos).isAir() && !world.getBlockState(blockpos).isSolidRender(world, blockpos)) {
            if (random.nextInt(200) == 0) {
                world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
            }
        }
    }

    @Nullable
    public ParticleOptions getDripParticle() {
        return ParticleTypes.DRIPPING_LAVA;
    }

    protected void beforeDestroyingBlock(LevelAccessor world, BlockPos blockPos, BlockState p_76218_) {
        this.fizz(world, blockPos);
    }

    public int getSlopeFindDistance(LevelReader world) {
        return 2;
    }

    public BlockState createLegacyBlock(FluidState p_76249_) {
        return DDBlocks.AMBER_FLUID.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(p_76249_));
    }

    public boolean isSame(Fluid fluid) {
        return fluid == DDFluids.AMBER.get() || fluid == DDFluids.FLOWING_AMBER.get();
    }

    public int getDropOff(LevelReader p_76252_) {
        return p_76252_.dimensionType().ultraWarm() ? 1 : 2;
    }

    public boolean canBeReplacedWith(FluidState p_76233_, BlockGetter p_76234_, BlockPos p_76235_, Fluid p_76236_, Direction p_76237_) {
        return p_76233_.getHeight(p_76234_, p_76235_) >= 0.44444445F && p_76236_.is(FluidTags.WATER);
    }

    public int getTickDelay(LevelReader p_76226_) {
        return p_76226_.dimensionType().ultraWarm() ? 10 : 30;
    }

    public int getSpreadDelay(Level p_76203_, BlockPos p_76204_, FluidState p_76205_, FluidState p_76206_) {
        int i = this.getTickDelay(p_76203_);
        if (!p_76205_.isEmpty() && !p_76206_.isEmpty() && !p_76205_.getValue(FALLING) && !p_76206_.getValue(FALLING) && p_76206_.getHeight(p_76203_, p_76204_) > p_76205_.getHeight(p_76203_, p_76204_) && p_76203_.getRandom().nextInt(4) != 0) {
            i *= 4;
        }

        return i;
    }

    private void fizz(LevelAccessor p_76213_, BlockPos p_76214_) {
        p_76213_.levelEvent(1501, p_76214_, 0);
    }

    protected boolean canConvertToSource() {
        return false;
    }

    protected void spreadTo(LevelAccessor p_76220_, BlockPos p_76221_, BlockState p_76222_, Direction p_76223_, FluidState p_76224_) {
        if (p_76223_ == Direction.DOWN) {
            FluidState fluidstate = p_76220_.getFluidState(p_76221_);
            if (this.is(FluidTags.LAVA) && fluidstate.is(FluidTags.WATER)) {
                if (p_76222_.getBlock() instanceof LiquidBlock) {
                    p_76220_.setBlock(p_76221_, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(p_76220_, p_76221_, p_76221_, Blocks.STONE.defaultBlockState()), 3);
                }

                this.fizz(p_76220_, p_76221_);
                return;
            }
        }

        super.spreadTo(p_76220_, p_76221_, p_76222_, p_76223_, p_76224_);
    }

    protected boolean isRandomlyTicking() {
        return true;
    }

    protected float getExplosionResistance() {
        return 100.0F;
    }

    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_LAVA);
    }

    public static class Flowing extends AmberFluid {

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState fluidState) {
            return false;
        }
    }

    public static class Source extends AmberFluid {
        public int getAmount(FluidState p_76269_) {
            return 8;
        }

        public boolean isSource(FluidState p_76267_) {
            return true;
        }
    }
}
