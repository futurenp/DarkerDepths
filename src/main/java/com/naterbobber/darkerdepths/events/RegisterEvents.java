package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.*;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkerDepths.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterEvents {

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event){
        SpawnPlacements.register(DDEntityTypes.GLOWSHROOM_MONSTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlowshroomMonsterEntity::checkMonsterSpawnRules);
        SpawnPlacements.register(DDEntityTypes.BODY_SNATCHER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BodySnatcherEntity::checkMonsterSpawnRules);
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
