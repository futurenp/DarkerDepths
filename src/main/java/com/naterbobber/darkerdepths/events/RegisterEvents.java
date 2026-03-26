package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.entities.*;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;


public class RegisterEvents {

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(DDEntityTypes.GLOWSHROOM_MONSTER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlowshroomMonsterEntity::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(DDEntityTypes.BODY_SNATCHER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BodySnatcherEntity::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(DDEntityTypes.GLOWSHROOM_MONSTER.get(), GlowshroomMonsterEntity.createAttributes().build());
        event.put(DDEntityTypes.BODY_SNATCHER.get(), BodySnatcherEntity.createAttributes().build());
        event.put(DDEntityTypes.VOID_SOUL_KNIGHT.get(), VoidSoulKnightEntity.createAttributes().build());
        event.put(DDEntityTypes.VOID_SOUL.get(), VoidSoulEntity.createAttributes().build());
        event.put(DDEntityTypes.SCORCHER.get(), ScorcherEntity.createAttributes().build());
    }
}
