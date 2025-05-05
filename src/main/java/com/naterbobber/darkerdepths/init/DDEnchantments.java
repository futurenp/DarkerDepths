package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.enchantments.QuickDashEnchantment;
import com.naterbobber.darkerdepths.enchantments.SwiftStrikeEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, DarkerDepths.MODID);

    public static final RegistryObject<Enchantment> SWIFT_STRIKE = ENCHANTMENTS.register("swift_strike", SwiftStrikeEnchantment::new);
    public static final RegistryObject<Enchantment> QUICK_DASH = ENCHANTMENTS.register("quick_dash", QuickDashEnchantment::new);

}
