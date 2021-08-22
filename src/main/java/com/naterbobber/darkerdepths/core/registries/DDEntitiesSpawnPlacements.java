package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.entities.GlowshroomMonsterEntity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.gen.Heightmap;

public class DDEntitiesSpawnPlacements {

    public static void register() {
        EntitySpawnPlacementRegistry.register(DDEntityTypes.GLOWSHROOM_MONSTER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GlowshroomMonsterEntity::canSpawn);
    }

}
