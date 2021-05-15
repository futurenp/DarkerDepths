package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.common.entities.MagmaMinionEntity;
import com.naterbobber.darkerdepths.common.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.core.DarkerDepths;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, DarkerDepths.MODID);

    // Entity Types
    public static final RegistryObject<EntityType<GlowshroomMonsterEntity>> GLOWSHROOM_MONSTER = ENTITY_TYPES.register("glowshroom_monster",
            () -> EntityType.Builder.create(GlowshroomMonsterEntity::new, EntityClassification.CREATURE)
                    .size(2.0f, 2.0f) // Hitbox Size
                    .build(new ResourceLocation(DarkerDepths.MODID, "glowshroom_monster").toString()));

    public static final RegistryObject<EntityType<MagmaMinionEntity>> MAGMA_MINION = ENTITY_TYPES.register("magma_minion",
            () -> EntityType.Builder.create(MagmaMinionEntity::new, EntityClassification.CREATURE)
                    .size(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(DarkerDepths.MODID, "magma_minion").toString()));
    
    public static final RegistryObject<EntityType<PetrifiedBoatEntity>> BOAT = ENTITY_TYPES.register("boat",
    		() -> EntityType.Builder.<PetrifiedBoatEntity>create(PetrifiedBoatEntity::new, EntityClassification.MISC)
    				.size(1.375f, 0.5625f) // Hitbox Size
    				.build(new ResourceLocation(DarkerDepths.MODID, "boat").toString()));

}