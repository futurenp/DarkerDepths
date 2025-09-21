package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;

public class DDArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =

            DeferredRegister.create(Registries.ARMOR_MATERIAL, DarkerDepths.MOD_ID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> GLOWSHROOM_MATERIAL = ARMOR_MATERIALS.register("glowshroom", () ->
            new ArmorMaterial(
                    Map.of(ArmorItem.Type.HELMET, 1),
                    0,
                    SoundEvents.ARMOR_EQUIP_LEATHER,
                    () -> Ingredient.of(DDItems.GLOW_GRIME.get()),
                    List.of(new ArmorMaterial.Layer(DarkerDepths.id("glowshroom"))),
                    0.0f,
                    0.0f
            )
    );

    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }
}