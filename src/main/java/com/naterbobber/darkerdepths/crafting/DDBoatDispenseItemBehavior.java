package com.naterbobber.darkerdepths.crafting;

import com.naterbobber.darkerdepths.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.entities.PetrifiedChestBoatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;

public class DDBoatDispenseItemBehavior extends DefaultDispenseItemBehavior {
    private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
    private final boolean isChestBoat;

    public DDBoatDispenseItemBehavior() {
        this(false);
    }

    public DDBoatDispenseItemBehavior(boolean isChestBoat) {
        this.isChestBoat = isChestBoat;
    }

    @Override
    public ItemStack execute(BlockSource source, ItemStack stack) {
        Direction direction = source.state().getValue(DispenserBlock.FACING);
        Level level = source.level();
        Vec3 center = source.center();
        double d0 = center.x() + (double)((float)direction.getStepX() * 1.125F);
        double d1 = center.y() + (double)((float)direction.getStepY() * 1.125F);
        double d2 = center.z() + (double)((float)direction.getStepZ() * 1.125F);
        BlockPos blockpos = source.pos().relative(direction);
        double d3;
        if (level.getFluidState(blockpos).is(FluidTags.WATER)) {
            d3 = 1.0D;
        } else {
            if (!level.getBlockState(blockpos).isAir() || !level.getFluidState(blockpos.below()).is(FluidTags.WATER)) {
                return this.defaultDispenseItemBehavior.dispense(source, stack);
            }

            d3 = 0.0D;
        }

        PetrifiedBoatEntity boat = this.isChestBoat ? new PetrifiedChestBoatEntity(level, d0, d1 + d3, d2) : new PetrifiedBoatEntity(level, d0, d1 + d3, d2);
        boat.setBoatType(PetrifiedBoatEntity.BoatType.PETRIFIED);
        boat.setYRot(direction.toYRot());
        level.addFreshEntity(boat);
        stack.shrink(1);
        return stack;
    }

    @Override
    protected void playSound(BlockSource source) {
        source.level().levelEvent(1000, source.pos(), 0);
    }

}
