package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        SpawnPlacements.register(DDEntityTypes.GLOWSHROOM_MONSTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlowshroomMonsterEntity::canSpawn);
        event.put(DDEntityTypes.GLOWSHROOM_MONSTER.get(), GlowshroomMonsterEntity.createAttributes().build());
    }

}
