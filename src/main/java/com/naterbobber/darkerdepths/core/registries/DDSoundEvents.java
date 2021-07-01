package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DDSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDEVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DarkerDepths.MODID);

    public static final RegistryObject<SoundEvent> ENTITY_GLOWSHROOM_MONSTER_AMBIENT        = regsterSounds("entity.glowshroom_monster.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_GLOWSHROOM_MONSTER_HURT           = regsterSounds("entity.glowshroom_monster.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_GLOWSHROOM_MONSTER_DEATH          = regsterSounds("entity.glowshroom_monster.death");

    public static final RegistryObject<SoundEvent> regsterSounds(String id) {
        return SOUNDEVENTS.register(id, () -> new SoundEvent(new ResourceLocation(DarkerDepths.MODID, id)));
    }

    public static void register(IEventBus eventBus) {
        SOUNDEVENTS.register(eventBus);
    }

}
