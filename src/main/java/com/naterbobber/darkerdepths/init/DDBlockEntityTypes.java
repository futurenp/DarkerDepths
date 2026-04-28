package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.blockentities.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DDBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, DarkerDepths.MOD_ID);



    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GeyserBlockEntity>> GEYSER = BLOCK_ENTITIES.register("geyser",
            () -> BlockEntityType.Builder.of(GeyserBlockEntity::new, DDBlocks.GEYSER.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TombBlockEntity>> TOMB = BLOCK_ENTITIES.register("tomb",
            () -> BlockEntityType.Builder.of(TombBlockEntity::new, DDBlocks.TOMB.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ParanoiaAltarBlockEntity>> PARANOIA_ALTAR = BLOCK_ENTITIES.register("paranoia_altar",
            () -> BlockEntityType.Builder.of(ParanoiaAltarBlockEntity::new, DDBlocks.PARANOIA_ALTAR.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MobPlacerBlockEntity>> MOB_PLACER = BLOCK_ENTITIES.register("mob_placer",
            () -> BlockEntityType.Builder.of(MobPlacerBlockEntity::new, DDBlocks.MOB_PLACER.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidSoulJarBlockEntity>> VOID_SOUL_JAR = BLOCK_ENTITIES.register("void_soul_jar",
            () -> BlockEntityType.Builder.of(VoidSoulJarBlockEntity::new, DDBlocks.VOID_SOUL_JAR.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GlowshroomHeartBlockEntity>> GLOWSHROOM_HEART = BLOCK_ENTITIES.register("glowshroom_heart",
            () -> BlockEntityType.Builder.of(GlowshroomHeartBlockEntity::new, DDBlocks.GLOWSHROOM_HEART.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DDSignBlockEntity>> DD_SIGN = BLOCK_ENTITIES.register("dd_sign",
            () -> BlockEntityType.Builder.of(DDSignBlockEntity::new,
                    DDBlocks.PETRIFIED_SIGN.get(),
                    DDBlocks.PETRIFIED_WALL_SIGN.get(),
                    DDBlocks.GLOWSHROOM_SIGN.get(),
                    DDBlocks.GLOWSHROOM_WALL_SIGN.get()
            ).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DDHangingSignBlockEntity>> DD_HANGING_SIGN = BLOCK_ENTITIES.register("dd_hanging_sign",
            () -> BlockEntityType.Builder.of(DDHangingSignBlockEntity::new,
                    DDBlocks.PETRIFIED_HANGING_SIGN.get(),
                    DDBlocks.PETRIFIED_WALL_HANGING_SIGN.get(),
                    DDBlocks.GLOWSHROOM_HANGING_SIGN.get(),
                    DDBlocks.GLOWSHROOM_WALL_HANGING_SIGN.get()
            ).build(null));
}

