package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.advancements.criteria.CrystalMelonTrigger;
import com.naterbobber.darkerdepths.advancements.criteria.GeyserBoostTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DDCriteria {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS =
            DeferredRegister.create(Registries.TRIGGER_TYPE, DarkerDepths.MOD_ID);

    public static final Supplier<GeyserBoostTrigger> GEYSER_ELYTRA_BOOST =
            TRIGGERS.register("geyser_elytra_boost", GeyserBoostTrigger::new);

    public static final Supplier<GeyserBoostTrigger> GEYSER_BOOST =
            TRIGGERS.register("geyser_boost", GeyserBoostTrigger::new);

    public static final Supplier<CrystalMelonTrigger> USED_CRYSTAL_MELON =
            TRIGGERS.register("used_crystal_melon", CrystalMelonTrigger::new);
}