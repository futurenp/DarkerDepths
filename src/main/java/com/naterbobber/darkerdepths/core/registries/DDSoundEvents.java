package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.core.CoreRegistries;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDSoundEvents {
    public static final CoreRegistries HELPER = DarkerDepths.REGISTRIES;

    public static final RegistryObject<SoundEvent> GRIMESTONE_BREAK = HELPER.registerSoundEvent("block.grimestone.break");
    public static final RegistryObject<SoundEvent> GRIMESTONE_STEP = HELPER.registerSoundEvent("block.grimestone.step");
    public static final RegistryObject<SoundEvent> GRIMESTONE_PLACE = HELPER.registerSoundEvent("block.grimestone.place");
    public static final RegistryObject<SoundEvent> GRIMESTONE_HIT = HELPER.registerSoundEvent("block.grimestone.hit");
    public static final RegistryObject<SoundEvent> GRIMESTONE_FALL = HELPER.registerSoundEvent("block.grimestone.fall");

    public static final RegistryObject<SoundEvent> ENTITY_GLOWSHROOM_MONSTER_AMBIENT        = HELPER.registerSoundEvent("entity.glowshroom_monster.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_GLOWSHROOM_MONSTER_HURT           = HELPER.registerSoundEvent("entity.glowshroom_monster.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_GLOWSHROOM_MONSTER_DEATH          = HELPER.registerSoundEvent("entity.glowshroom_monster.death");

    public static final SoundType GRIMESTONE_TYPE = new ForgeSoundType(1.0F, 1.0F, () -> GRIMESTONE_BREAK.get(), () -> GRIMESTONE_STEP.get(), () -> GRIMESTONE_PLACE.get(), () -> GRIMESTONE_HIT.get(), () -> GRIMESTONE_FALL.get());

}
