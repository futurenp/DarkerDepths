package com.naterbobber.darkerdepths.common.events;

import com.naterbobber.darkerdepths.common.blocks.GeyserBlock;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.registries.DDItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber
public class TickEvents {

    @SubscribeEvent
    public void onEntityTick(LivingEvent.LivingUpdateEvent event) {
        World world = event.getEntity().world;
        LivingEntity entity = event.getEntityLiving();
        BlockPos pos = entity.getPosition();
        BlockState GEYSER = DDBlocks.GEYSER.get().getDefaultState().with(GeyserBlock.POWERED, false);

        if (!entity.isSpectator()) {
            for (int i = 5; i >= 0; i--) {
                Vector3d motion = entity.getMotion();
                double pushVal = (Math.abs(-1 - 5.5) / 10);
                if (world.getBlockState(pos.down(i)) == GEYSER.with(GeyserBlock.FACING, Direction.UP)) {
                    entity.setMotion(motion.x, motion.y + pushVal / 4, motion.z);
                    if (world.getBlockState(pos.down(i + 1)).matchesBlock(Blocks.MAGMA_BLOCK)) {
                        entity.setMotion(motion.x, motion.y + pushVal / 2, motion.z);
                    }
                } else if (world.getBlockState(pos.up(i)) == GEYSER.with(GeyserBlock.FACING, Direction.DOWN)) {
                    entity.setMotion(motion.x, motion.y - pushVal / 4, motion.z);
                    if (world.getBlockState(pos.up(i + 1)).matchesBlock(Blocks.MAGMA_BLOCK)) {
                        entity.setMotion(motion.x, motion.y - pushVal / 2, motion.z);
                    }
                } else if (world.getBlockState(pos.west(i)) == GEYSER.with(GeyserBlock.FACING, Direction.EAST)) {
                    entity.setMotion(motion.x + pushVal / 4, motion.y, motion.z);
                    if (world.getBlockState(pos.west(i + 1)).matchesBlock(Blocks.MAGMA_BLOCK)) {
                        entity.setMotion(motion.x + pushVal / 2, motion.y, motion.z);
                    }
                } else if (world.getBlockState(pos.east(i)) == GEYSER.with(GeyserBlock.FACING, Direction.WEST)) {
                    entity.setMotion(motion.x - pushVal / 4, motion.y, motion.z);
                    if (world.getBlockState(pos.east(i + 1)).matchesBlock(Blocks.MAGMA_BLOCK)) {
                        entity.setMotion(motion.x - pushVal / 2, motion.y, motion.z);
                    }
                } else if (world.getBlockState(pos.south(i)) == GEYSER.with(GeyserBlock.FACING, Direction.NORTH)) {
                    entity.setMotion(motion.x, motion.y, motion.z - pushVal / 4);
                    if (world.getBlockState(pos.south(i + 1)).matchesBlock(Blocks.MAGMA_BLOCK)) {
                        entity.setMotion(motion.x, motion.y, motion.z - pushVal / 2);
                    }
                } else if (world.getBlockState(pos.north(i)) == GEYSER.with(GeyserBlock.FACING, Direction.SOUTH)) {
                    entity.setMotion(motion.x, motion.y, motion.z + pushVal / 4);
                    if (world.getBlockState(pos.north(i + 1)).matchesBlock(Blocks.MAGMA_BLOCK)) {
                        entity.setMotion(motion.x, motion.y, motion.z + pushVal / 2);
                    }
                }
            }
        }
    }

}