package com.naterbobber.darkerdepths.blockentities;

import com.mojang.math.Vector3d;
import com.naterbobber.darkerdepths.blocks.GeyserBlock;
import com.naterbobber.darkerdepths.init.DDBlockEntities;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class GeyserBlockEntity extends BlockEntity {

    public GeyserBlockEntity(BlockPos pos, BlockState state) {
        super(DDBlockEntities.GEYSER.get(), pos, state);
    }

    public static void geyserTick(Level world, BlockPos pos, BlockState state, GeyserBlockEntity geyserBlockEntity) {
        for (int i = 1; i < 7; i++) {
            if (!isValidForHover(world, pos, i)) {
                break;
            }
            List<Entity> nearbyEntities = world.getEntitiesOfClass(Entity.class, new AABB(pos.above(i)));
            for (Entity entity : nearbyEntities) {
                Vec3 motion = entity.getDeltaMovement();
                entity.setDeltaMovement(motion.x, motion.y + 0.06D, motion.z);
                entity.fallDistance = 0.0F;
                if (world.getBlockState(pos.below()).is(Blocks.MAGMA_BLOCK)) {
                    entity.setDeltaMovement(motion.x, motion.y + 0.12D, motion.z);
                    entity.fallDistance = 0.0F;
                }
            }
        }
    }

    private static boolean isValidForHover(Level world, BlockPos blockPos, int distance) {
        return world.isEmptyBlock(blockPos.above(distance)) || world.getBlockState(blockPos.above(distance)).is(Blocks.WATER) || world.getBlockState(blockPos.above(distance)).is(Blocks.LAVA);
    }

}
