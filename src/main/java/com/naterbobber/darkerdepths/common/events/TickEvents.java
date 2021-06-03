package com.naterbobber.darkerdepths.common.events;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TickEvents {

    @SubscribeEvent
    public void onPlayerTick(net.minecraftforge.event.TickEvent.PlayerTickEvent event) {
        World world = event.player.getEntityWorld();
        PlayerEntity player = event.player;
        BlockPos pos = player.getPosition();
        for (int i = 5; i >= 0; i--) {
            if (world.getBlockState(pos.down(i)) == DDBlocks.GEISER.get().getDefaultState()) {
                Vector3d vector = player.getMotion();
                player.setMotion(vector.x, vector.y + ((Math.abs(-i - 5.5) / 10) / 4), vector.z);
            }
        }
    }

}
