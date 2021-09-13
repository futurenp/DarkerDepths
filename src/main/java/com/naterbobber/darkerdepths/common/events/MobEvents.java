package com.naterbobber.darkerdepths.common.events;

import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public void checkEntityJoinEvent(LivingSpawnEvent.CheckSpawn event) {
        Entity entity = event.getEntity();
        Predicate<BlockState> repellentPredicate = state -> {
            return state.getBlock().matchesBlock(DDBlocks.REPELLENT_BLOCK.get());
        };
        if (entity.getType().getClassification() == EntityClassification.MONSTER) {
            BlockPos blockPos;
            int distance = 6;
            for (int x = -distance; x < distance; x++) {
                for (int z = -distance; z < distance; z++) {
                    for (int y = -distance; y < distance; y++) {
                        blockPos = new BlockPos(entity.getPosX() + x, entity.getPosY() + y, entity.getPosZ() + z);
                        if (entity.getPosition().withinDistance(blockPos, distance) && repellentPredicate.test(entity.world.getBlockState(blockPos))) {
                            event.setResult(Event.Result.DENY);
                        }
                    }
                }
            }
        }
    }
}
