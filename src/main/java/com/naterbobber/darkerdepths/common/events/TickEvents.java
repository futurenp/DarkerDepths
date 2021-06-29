package com.naterbobber.darkerdepths.common.events;

import com.naterbobber.darkerdepths.common.blocks.GeyserBlock;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TickEvents {
    @SubscribeEvent
    public void onEntityTick(LivingEvent.LivingUpdateEvent event) {
        World world = event.getEntity().world;
        LivingEntity entity = event.getEntityLiving();
        BlockPos pos = entity.getPosition();

        for (int i = 5; i >= 0; i--) {
            if (world.getBlockState(pos.down(i)) == DDBlocks.GEYSER.get().getDefaultState().with(GeyserBlock.POWERED, false)) {
                Vector3d motion = entity.getMotion();
                entity.setMotion(motion.x, motion.y + ((Math.abs(-i - 5.5) / 10) / 4), motion.z);
                if (world.getBlockState(pos.down(i + 1)) == Blocks.MAGMA_BLOCK.getDefaultState()) {
                    entity.setMotion(motion.x, motion.y + ((Math.abs(-i - 5.5) / 10) / 2), motion.z);
                }
            }
        }
    }
}