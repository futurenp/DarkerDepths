package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.neoforged.neoforge.registries.DeferredHolder;

public class DDSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, DarkerDepths.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_GRIMESTONE_BREAK                   = registerSoundEvent("block.grimestone.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_GRIMESTONE_STEP                    = registerSoundEvent("block.grimestone.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_GRIMESTONE_PLACE                   = registerSoundEvent("block.grimestone.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_GRIMESTONE_HIT                     = registerSoundEvent("block.grimestone.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_GRIMESTONE_FALL                    = registerSoundEvent("block.grimestone.fall");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_VOID_SOUL_KNIGHT_HEAVY_SWING      = registerSoundEvent("entity.void_soul_knight.heavy_swing");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_VOID_SOUL_KNIGHT_LIGHT_SWING      = registerSoundEvent("entity.void_soul_knight.light_swing");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_GLOWSHROOM_MONSTER_AMBIENT        = registerSoundEvent("entity.glowshroom_monster.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_GLOWSHROOM_MONSTER_HURT           = registerSoundEvent("entity.glowshroom_monster.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_GLOWSHROOM_MONSTER_DEATH          = registerSoundEvent("entity.glowshroom_monster.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_DEATH_ANCHOR_SOUL_BINDING          = registerSoundEvent("block.death_anchor.soul_binding");

    public static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(DarkerDepths.id(name)));
    }

}
