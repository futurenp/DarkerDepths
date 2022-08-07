package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.entities.PetrifiedChestBoatEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DarkerDepths.MODID);

    public static final RegistryObject<EntityType<PetrifiedBoatEntity>> PETRIFIED_BOAT = ENTITY_TYPES.register("petrified_boat", () -> EntityType.Builder.<PetrifiedBoatEntity>of(PetrifiedBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).build(new ResourceLocation(DarkerDepths.MODID, "petrified_boat").toString()));
    public static final RegistryObject<EntityType<PetrifiedChestBoatEntity>> PETRIFIED_CHEST_BOAT = ENTITY_TYPES.register("petrified_chest_boat", () -> EntityType.Builder.<PetrifiedChestBoatEntity>of(PetrifiedChestBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).build(new ResourceLocation(DarkerDepths.MODID, "petrified_chest_boat").toString()));
    public static final RegistryObject<EntityType<GlowshroomMonsterEntity>> GLOWSHROOM_MONSTER = ENTITY_TYPES.register("glowshroom_monster", () -> EntityType.Builder.of(GlowshroomMonsterEntity::new, MobCategory.MONSTER).sized(1.3964844F, 1.4F).clientTrackingRange(8).build(new ResourceLocation(DarkerDepths.MODID, "glowshroom_monster").toString()));

}
