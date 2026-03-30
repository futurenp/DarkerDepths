package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DDEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, DarkerDepths.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<PetrifiedBoatEntity>> PETRIFIED_BOAT = ENTITY_TYPES.register("petrified_boat",
            () -> EntityType.Builder.<PetrifiedBoatEntity>of(PetrifiedBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).build(DarkerDepths.id("petrified_boat").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<PetrifiedChestBoatEntity>> PETRIFIED_CHEST_BOAT = ENTITY_TYPES.register("petrified_chest_boat",
            () -> EntityType.Builder.<PetrifiedChestBoatEntity>of(PetrifiedChestBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).build(DarkerDepths.id("petrified_chest_boat").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<GlowshroomMonsterEntity>> GLOWSHROOM_MONSTER = ENTITY_TYPES.register("glowshroom_monster",
            () -> EntityType.Builder.of(GlowshroomMonsterEntity::new, MobCategory.MONSTER).sized(1.6F, 1.9F).clientTrackingRange(8).build(DarkerDepths.id("glowshroom_monster").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<BodySnatcherEntity>> BODY_SNATCHER = ENTITY_TYPES.register("body_snatcher",
            () -> EntityType.Builder.of(BodySnatcherEntity::new, MobCategory.MONSTER).sized(0.5F, 1.0F).clientTrackingRange(8).updateInterval(2).build(DarkerDepths.id("body_snatcher").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<VoidSoulKnightEntity>> VOID_SOUL_KNIGHT = ENTITY_TYPES.register("void_soul_knight",
            () -> EntityType.Builder.of(VoidSoulKnightEntity::new, MobCategory.MONSTER).sized(0.8F, 2.75F).clientTrackingRange(8).updateInterval(2).build(DarkerDepths.id("void_soul_knight").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<VoidSoulEntity>> VOID_SOUL = ENTITY_TYPES.register("void_soul",
            () -> EntityType.Builder.of(VoidSoulEntity::new, MobCategory.CREATURE).sized(0.4F, .4F).clientTrackingRange(8).updateInterval(2).build(DarkerDepths.id("void_soul").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<ScorcherEntity>> SCORCHER = ENTITY_TYPES.register("scorcher",
            () -> EntityType.Builder.of(ScorcherEntity::new, MobCategory.MONSTER).sized(1.0F, 1.0F).eyeHeight(0.575F).clientTrackingRange(8).updateInterval(2).build(DarkerDepths.id("scorcher").toString()));
}
