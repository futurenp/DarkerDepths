package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DDSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDEVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DarkerDepths.MODID);

    public static final RegistryObject<SoundEvent> GRIMESTONE_BREAK = registerSounds("block.grimestone.break");
    public static final RegistryObject<SoundEvent> GRIMESTONE_STEP = registerSounds("block.grimestone.step");
    public static final RegistryObject<SoundEvent> GRIMESTONE_PLACE = registerSounds("block.grimestone.place");
    public static final RegistryObject<SoundEvent> GRIMESTONE_HIT = registerSounds("block.grimestone.hit");
    public static final RegistryObject<SoundEvent> GRIMESTONE_FALL = registerSounds("block.grimestone.fall");

    public static final RegistryObject<SoundEvent> ENTITY_GLOWSHROOM_MONSTER_AMBIENT        = registerSounds("entity.glowshroom_monster.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_GLOWSHROOM_MONSTER_HURT           = registerSounds("entity.glowshroom_monster.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_GLOWSHROOM_MONSTER_DEATH          = registerSounds("entity.glowshroom_monster.death");

    public static final SoundType GRIMESTONE_TYPE = new ForgeSoundType(1.0F, 1.0F, () -> GRIMESTONE_BREAK.get(), () -> GRIMESTONE_STEP.get(), () -> GRIMESTONE_PLACE.get(), () -> GRIMESTONE_HIT.get(), () -> GRIMESTONE_FALL.get());

    public static final RegistryObject<SoundEvent> registerSounds(String id) {
        return SOUNDEVENTS.register(id, () -> new SoundEvent(new ResourceLocation(DarkerDepths.MODID, id)));
    }

    public static void register(IEventBus eventBus) {
        SOUNDEVENTS.register(eventBus);
    }

}
