package com.naterbobber.darkerdepths.damage;

import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class DDDamageTypes {

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(DDResourceKeys.DamageTypes.SOUL_BINDING_DAMAGE,
                new DamageType("soul_binding_damage", DamageScaling.NEVER, 0.0f));
    }
}
