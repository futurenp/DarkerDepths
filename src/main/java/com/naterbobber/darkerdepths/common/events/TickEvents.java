package com.naterbobber.darkerdepths.common.events;

import com.naterbobber.darkerdepths.common.blocks.GeyserBlock;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
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
                if (world.getBlockState(pos.down(i)) == GEYSER) {
                    entity.setMotion(motion.x, motion.y + pushVal / 4, motion.z);
                    if (world.getBlockState(pos.down(i + 1)).matchesBlock(Blocks.MAGMA_BLOCK)) {
                        entity.setMotion(motion.x, motion.y + pushVal / 2, motion.z);
                    }
                }
            }
        }
    }
}