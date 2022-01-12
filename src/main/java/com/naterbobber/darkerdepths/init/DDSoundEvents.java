package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DarkerDepths.MODID);

    public static final RegistryObject<SoundEvent> BLOCK_GRIMESTONE_BREAK                   = registerSoundEvent("block.grimestone.break");
    public static final RegistryObject<SoundEvent> BLOCK_GRIMESTONE_STEP                    = registerSoundEvent("block.grimestone.step");
    public static final RegistryObject<SoundEvent> BLOCK_GRIMESTONE_PLACE                   = registerSoundEvent("block.grimestone.place");
    public static final RegistryObject<SoundEvent> BLOCK_GRIMESTONE_HIT                     = registerSoundEvent("block.grimestone.hit");
    public static final RegistryObject<SoundEvent> BLOCK_GRIMESTONE_FALL                    = registerSoundEvent("block.grimestone.fall");

    public static final RegistryObject<SoundEvent> ENTITY_GLOWSHROOM_MONSTER_AMBIENT        = registerSoundEvent("entity.glowshroom_monster.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_GLOWSHROOM_MONSTER_HURT           = registerSoundEvent("entity.glowshroom_monster.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_GLOWSHROOM_MONSTER_DEATH          = registerSoundEvent("entity.glowshroom_monster.death");

    public static final SoundType GRIMESTONE = new ForgeSoundType(1.0F, 1.0F, BLOCK_GRIMESTONE_BREAK, BLOCK_GRIMESTONE_STEP, BLOCK_GRIMESTONE_PLACE, BLOCK_GRIMESTONE_HIT, BLOCK_GRIMESTONE_FALL);

    public static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(DarkerDepths.MODID, name)));
    }

}
