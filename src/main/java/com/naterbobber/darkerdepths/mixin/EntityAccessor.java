package com.naterbobber.darkerdepths.mixin;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor
    Object2DoubleMap<FluidType> getForgeFluidTypeHeight();

    @Accessor
    boolean isFirstTick();
}
