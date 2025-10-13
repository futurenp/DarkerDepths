package com.naterbobber.darkerdepths.init;

import net.minecraft.world.level.block.SoundType;

import java.util.function.Supplier;

public class DDSoundTypes {
    public static final Supplier<SoundType> GRIMESTONE = () -> new SoundType(1.0F, 1.0F,
            DDSoundEvents.BLOCK_GRIMESTONE_BREAK.get(),
            DDSoundEvents.BLOCK_GRIMESTONE_STEP.get(),
            DDSoundEvents.BLOCK_GRIMESTONE_PLACE.get(),
            DDSoundEvents.BLOCK_GRIMESTONE_HIT.get(),
            DDSoundEvents.BLOCK_GRIMESTONE_FALL.get()
    );

    //    public static final SoundType ROPE = new SoundType(1.0F, 1.0F, BLOCK_ROPE_BREAK.get(), BLOCK_ROPE_STEP.get(), BLOCK_ROPE_PLACE.get(), null, null);
}
