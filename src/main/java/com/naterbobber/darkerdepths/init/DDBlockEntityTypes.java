package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.blocks.blockentities.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DarkerDepths.MODID);

    public static final RegistryObject<BlockEntityType<DDSignBlockEntity>> DD_SIGN = BLOCK_ENTITIES.register("dd_sign",
            () -> BlockEntityType.Builder.of(DDSignBlockEntity::new, DDBlocks.PETRIFIED_SIGN.get(), DDBlocks.PETRIFIED_WALL_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<GeyserBlockEntity>> GEYSER = BLOCK_ENTITIES.register("geyser",
            () -> BlockEntityType.Builder.of(GeyserBlockEntity::new, DDBlocks.GEYSER.get()).build(null));
    public static final RegistryObject<BlockEntityType<TombBlockEntity>> TOMB = BLOCK_ENTITIES.register("tomb",
            () -> BlockEntityType.Builder.of(TombBlockEntity::new, DDBlocks.TOMB.get()).build(null));
    public static final RegistryObject<BlockEntityType<ParanoiaAltarBlockEntity>> PARANOIA_ALTAR = BLOCK_ENTITIES.register("paranoia_altar",
            () -> BlockEntityType.Builder.of(ParanoiaAltarBlockEntity::new, DDBlocks.PARANOIA_ALTAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<MobPlacerBlockEntity>> MOB_PLACER = BLOCK_ENTITIES.register("mob_placer",
            () -> BlockEntityType.Builder.of(MobPlacerBlockEntity::new, DDBlocks.MOB_PLACER.get()).build(null));
}
