package com.naterbobber.darkerdepths.util;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.component.SuperchargeInfo;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.init.DDDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.Optional;

public class SuperchargeHelper {

    private static final ResourceLocation ATTACK_DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(DarkerDepths.MOD_ID, "supercharge_attack_damage");
    private static final ResourceLocation ATTACK_SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(DarkerDepths.MOD_ID, "supercharge_attack_speed");
    private static final ResourceLocation MINING_EFFICIENCY_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(DarkerDepths.MOD_ID, "supercharge_mining_speed");

    public static void applyUpgrades(ItemStack stack, Level level) {
        if (stack.isEmpty() || level.isClientSide) {
            return;
        }

        ItemAttributeModifiers originalModifiers = stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        Optional<Unbreakable> originalUnbreakable = Optional.ofNullable(stack.get(DataComponents.UNBREAKABLE));

        long expirationTick = level.getGameTime() + (DDConfig.CONFIG.SUPERCHARGE_DURATION.get() * 60 * 20L);
        SuperchargeInfo info = new SuperchargeInfo(expirationTick, originalUnbreakable, originalModifiers);
        stack.set(DDDataComponents.SUPERCHARGE_INFO.get(), info);

        MutableComponent prefix = Component.literal("Supercharged ").withStyle(ChatFormatting.AQUA).withStyle(style -> style.withItalic(false));
        Component originalName = stack.getHoverName().copy();
        stack.set(DataComponents.CUSTOM_NAME, prefix.append(originalName));

        float damageMultiplier = DDConfig.CONFIG.SUPERCHARGE_ATTACK_DAMAGE.get() / 100.0F;
        float attackSpeedMultiplier = DDConfig.CONFIG.SUPERCHARGE_ATTACK_SPEED.get() / 100.0F;
        float miningSpeedMultiplier = DDConfig.CONFIG.SUPERCHARGE_DIG_SPEED.get() / 100.0F;

        ItemAttributeModifiers newModifiers = originalModifiers
                .withModifierAdded(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, damageMultiplier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                        EquipmentSlotGroup.ANY
                ).withModifierAdded(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(ATTACK_SPEED_MODIFIER_ID, attackSpeedMultiplier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                        EquipmentSlotGroup.ANY
                ).withModifierAdded(
                        Attributes.MINING_EFFICIENCY,
                        new AttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, miningSpeedMultiplier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                        EquipmentSlotGroup.ANY
                );
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, newModifiers.withTooltip(false));

        if (DDConfig.CONFIG.SUPERCHARGE_UNBREAKABLE.get()) {
            stack.set(DataComponents.UNBREAKABLE, new Unbreakable(false));
        }
    }

    public static void revertUpgrades(ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        SuperchargeInfo info = stack.get(DDDataComponents.SUPERCHARGE_INFO.get());
        if (info == null) {
            return;
        }

        stack.remove(DDDataComponents.SUPERCHARGE_INFO.get());
        stack.remove(DataComponents.CUSTOM_NAME);

        if (info.originalModifiers().modifiers().isEmpty()) {
            stack.remove(DataComponents.ATTRIBUTE_MODIFIERS);
        } else {
            stack.set(DataComponents.ATTRIBUTE_MODIFIERS, info.originalModifiers());
        }

        if (info.originalUnbreakable().isPresent()) {
            stack.set(DataComponents.UNBREAKABLE, info.originalUnbreakable().get());
        } else {
            stack.remove(DataComponents.UNBREAKABLE);
        }
    }
}

