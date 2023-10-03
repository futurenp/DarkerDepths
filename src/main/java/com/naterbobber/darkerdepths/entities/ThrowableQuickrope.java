package com.naterbobber.darkerdepths.entities;

import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class ThrowableQuickrope extends ThrowableItemProjectile {

    public ThrowableQuickrope(EntityType<? extends ThrowableItemProjectile> type, Level world) {
        super(type, world);
    }

    @Override
    protected void onHitBlock(BlockHitResult hit) {
        super.onHitBlock(hit);
        if (this.getLevel().getBlockState(hit.getBlockPos().below()).isAir()) {
            for (int i = 0; i < 16; i++) {
                this.getLevel().setBlock(hit.getBlockPos().below(i), DDBlocks.ROPE.get().defaultBlockState(), 2);
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return DDItems.QUICKROPE.get();
    }
}
