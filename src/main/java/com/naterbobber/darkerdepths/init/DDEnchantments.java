package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.enchantments.QuickDashEnchantment;
import com.naterbobber.darkerdepths.enchantments.SwiftStrikeEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DDEnchantments {

    // Corrected the DeferredRegister to use the correct registry and MODID
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT, DarkerDepths.MOD_ID);

    public static final DeferredHolder<Enchantment, SwiftStrikeEnchantment> SWIFT_STRIKE = ENCHANTMENTS.register("swift_strike", SwiftStrikeEnchantment::new);
    public static final DeferredHolder<Enchantment, QuickDashEnchantment> QUICK_DASH = ENCHANTMENTS.register("quick_dash", QuickDashEnchantment::new);
}
