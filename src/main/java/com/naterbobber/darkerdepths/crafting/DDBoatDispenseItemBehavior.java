package com.naterbobber.darkerdepths.crafting;

import com.naterbobber.darkerdepths.entities.PetrifiedBoatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class DDBoatDispenseItemBehavior extends DefaultDispenseItemBehavior {
    private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

    public DDBoatDispenseItemBehavior() {
    }

    @Override
    public ItemStack execute(BlockSource source, ItemStack stack) {
        Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
        Level level = source.getLevel();
        double d0 = source.x() + (double)((float)direction.getStepX() * 1.125F);
        double d1 = source.y() + (double)((float)direction.getStepY() * 1.125F);
        double d2 = source.z() + (double)((float)direction.getStepZ() * 1.125F);
        BlockPos blockpos = source.getPos().relative(direction);
        double d3;
        if (level.getFluidState(blockpos).is(FluidTags.WATER)) {
            d3 = 1.0D;
        } else {
            if (!level.getBlockState(blockpos).isAir() || !level.getFluidState(blockpos.below()).is(FluidTags.WATER)) {
                return this.defaultDispenseItemBehavior.dispense(source, stack);
            }

            d3 = 0.0D;
        }

        PetrifiedBoatEntity boat = new PetrifiedBoatEntity(level, d0, d1 + d3, d2);
        boat.setBoatType(PetrifiedBoatEntity.BoatType.PETRIFIED);
        boat.setYRot(direction.toYRot());
        level.addFreshEntity(boat);
        stack.shrink(1);
        return stack;
    }

    @Override
    protected void playSound(BlockSource source) {
        source.getLevel().levelEvent(1000, source.getPos(), 0);
    }

}