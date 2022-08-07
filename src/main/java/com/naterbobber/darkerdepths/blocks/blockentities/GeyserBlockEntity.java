package com.naterbobber.darkerdepths.blocks.blockentities;

import com.naterbobber.darkerdepths.blocks.GeyserBlock;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.init.DDBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class GeyserBlockEntity extends BlockEntity {

    public GeyserBlockEntity(BlockPos pos, BlockState state) {
        super(DDBlockEntityTypes.GEYSER.get(), pos, state);
    }

    public static void geyserTick(Level world, BlockPos pos, BlockState state, GeyserBlockEntity geyserBlockEntity) {
        Direction direction = state.getValue(GeyserBlock.FACING);
        double booster = (world.getBlockState(pos.relative(direction.getOpposite())).is(DDBlockTags.GEYSER_BOOSTERS) ? 0.12 : 0.06) * direction.getAxisDirection().getStep();
        for (int i = 1; i < 7; i++) {
            BlockPos relativePosition = pos.relative(direction, i);
            BlockState relativeState = world.getBlockState(relativePosition);
            if (!(world.isStateAtPosition(relativePosition, DripstoneUtils::isEmptyOrWaterOrLava) || (relativeState.hasProperty(BlockStateProperties.LAYERS) && relativeState.getValue(BlockStateProperties.LAYERS) == 1))) {
                break;
            }
            List<Entity> nearbyEntities = world.getEntitiesOfClass(Entity.class, new AABB(relativePosition));
            for (Entity entity : nearbyEntities) {
                Vec3 motion = entity.getDeltaMovement();
                double xBooster = direction.getAxis() == Direction.Axis.X ? booster : 0.0D;
                double yBooster = direction.getAxis() == Direction.Axis.Y ? booster : 0.0D;
                double zBooster = direction.getAxis() == Direction.Axis.Z ? booster : 0.0D;
                entity.setDeltaMovement(motion.x + xBooster, motion.y + yBooster, motion.z + zBooster);
                entity.fallDistance = 0.0F;
            }
        }
    }

}
