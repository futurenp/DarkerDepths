package com.naterbobber.darkerdepths.common.tileentities;

import com.naterbobber.darkerdepths.common.blocks.GeyserBlock;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.registries.DDTileEntities;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class GeyserBlockTileEntity extends TileEntity implements ITickableTileEntity {

    public GeyserBlockTileEntity() {
        super(DDTileEntities.GEYSER.get());
    }

    @Override
    public void tick() {
        if (world.getBlockState(pos) == DDBlocks.GEYSER.get().getDefaultState().with(GeyserBlock.POWERED, false)) {
            for (int i = 1; i < 7; i++) {
                if (!world.isAirBlock(pos.up(i))) {
                    break;
                }
                List<Entity> nearbyEntities = this.getWorld().getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(this.getPos().up(i)));
                for (Entity entity : nearbyEntities) {
                    Vector3d motion = entity.getMotion();
                    entity.setMotion(motion.x, motion.y + 0.06D, motion.z);
                    entity.fallDistance = 0.0F;
                    if (world.getBlockState(pos.down()).isIn(Blocks.MAGMA_BLOCK)) {
                        entity.setMotion(motion.x, motion.y + 0.12D, motion.z);
                        entity.fallDistance = 0.0F;
                    }
                }
            }
        }
    }
}
