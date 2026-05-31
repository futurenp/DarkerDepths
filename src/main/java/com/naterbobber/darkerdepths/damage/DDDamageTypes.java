package com.naterbobber.darkerdepths.damage;

import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public class DDDamageTypes {

    public static void bootstrap(BootstrapContext<DamageType> context) {
        register(context, DDResourceKeys.DamageTypes.SOUL_BINDING_DAMAGE);
        register(context, DDResourceKeys.DamageTypes.VOID_SOUL_DAMAGE);
        register(context, DDResourceKeys.DamageTypes.GLOWSHROOM_SLAM_DAMAGE);
    }

    private static void register(BootstrapContext<DamageType> context, ResourceKey<DamageType> key) {
        context.register(key, new DamageType(key.location().getPath(), DamageScaling.NEVER, 0.0f));
    }

    public static DamageSource getDamageSource(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
    }
}
