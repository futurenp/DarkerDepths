package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.common.entities.MagmaMinionEntity;
import com.naterbobber.darkerdepths.common.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.api.Registries;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDEntityTypes {
    public static final Registries HELPER = DarkerDepths.REGISTRIES;

    public static final RegistryObject<EntityType<GlowshroomMonsterEntity>> GLOWSHROOM_MONSTER  = HELPER.registerEntity("glowshroom_monster", EntityType.Builder.create(GlowshroomMonsterEntity::new, EntityClassification.MONSTER).size(2.0F, 2.0F));
    public static final RegistryObject<EntityType<MagmaMinionEntity>> MAGMA_MINION              = HELPER.registerEntity("magma_minion", EntityType.Builder.create(MagmaMinionEntity::new, EntityClassification.MONSTER).size(1.0F, 1.0F));
    public static final RegistryObject<EntityType<PetrifiedBoatEntity>> BOAT                    = HELPER.registerEntity("boat", EntityType.Builder.<PetrifiedBoatEntity>create(PetrifiedBoatEntity::new, EntityClassification.MISC).size(1.375F, 0.5625F));
}