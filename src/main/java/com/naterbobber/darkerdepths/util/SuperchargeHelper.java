package com.naterbobber.darkerdepths.util;

import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.UUID;

public class SuperchargeHelper {

    // NBT Keys
    public static final String TAG_UPGRADE_MAIN = "darkerdepths_upgrades";
    public static final String TAG_WAS_UNBREAKABLE = "was_unbreakable";
    public static final String TAG_HAD_MODIFIERS = "had_modifiers";
    public static final String TAG_EXPIRATION = "expiration_tick";
    public static final String TAG_MINING_BOOST = "mining_boost";
    public static final String TAG_SPEED_BOOST_LEVEL = "speed_boost_level";
    public static final String TAG_DAMAGE_BOOST_LEVEL = "damage_boost_level";
    public static final String TAG_ATTACK_SPEED_BOOST_LEVEL = "attack_speed_boost_level";
    private static final UUID ATTACK_DAMAGE_MODIFIER_UUID = UUID.fromString("f8b8e0a8-f8a0-449e-917b-88a3a2dff83e");
    private static final UUID ATTACK_SPEED_MODIFIER_UUID = UUID.fromString("a7a7a3b8-1b0f-4859-866c-844a49e5e79f");
    private static int superchargeMinutes = 5;
    private static int superchargeBuff = 20;
    private static int digSpeedBuff = superchargeBuff;
    private static int attackDamageBuff = superchargeBuff;
    private static int attackSpeedBuff = superchargeBuff;

    public static void applyUpgrades(ItemStack tool, Level level) {
        if (tool.isEmpty() || level.isClientSide) {
            return;
        }

        boolean wasAlreadyUnbreakable = tool.hasTag() && tool.getTag().getBoolean("Unbreakable");
        boolean hadExistingModifiers = tool.hasTag() && tool.getTag().contains("AttributeModifiers", Tag.TAG_LIST);

        if (!hadExistingModifiers) {
            Multimap<Attribute, AttributeModifier> defaultModifiers = tool.getItem().getDefaultAttributeModifiers(EquipmentSlot.MAINHAND);
            for (Map.Entry<Attribute, AttributeModifier> entry : defaultModifiers.entries()) {
                tool.addAttributeModifier(entry.getKey(), entry.getValue(), EquipmentSlot.MAINHAND);
            }
        }

        long expirationTick = level.getGameTime() + (superchargeMinutes * 60 * 20);

        CompoundTag mainTag = tool.getOrCreateTag();
        CompoundTag upgradeTag = new CompoundTag();

        upgradeTag.putBoolean(TAG_MINING_BOOST, true);
        upgradeTag.putInt(TAG_SPEED_BOOST_LEVEL, digSpeedBuff);
        upgradeTag.putInt(TAG_DAMAGE_BOOST_LEVEL, attackDamageBuff);
        upgradeTag.putInt(TAG_ATTACK_SPEED_BOOST_LEVEL, attackSpeedBuff);
        upgradeTag.putLong(TAG_EXPIRATION, expirationTick);
        upgradeTag.putBoolean(TAG_WAS_UNBREAKABLE, wasAlreadyUnbreakable);
        upgradeTag.putBoolean(TAG_HAD_MODIFIERS, hadExistingModifiers);
        mainTag.put(TAG_UPGRADE_MAIN, upgradeTag);

        mainTag.putBoolean("Unbreakable", true);

        float damageMultiplier = attackDamageBuff / 100F;
        float speedMultiplier = attackSpeedBuff / 100F;
        AttributeModifier damageModifier = new AttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "DarkerDepths Supercharge Damage", damageMultiplier, AttributeModifier.Operation.MULTIPLY_TOTAL);
        AttributeModifier speedModifier = new AttributeModifier(ATTACK_SPEED_MODIFIER_UUID, "DarkerDepths Supercharge Speed", speedMultiplier, AttributeModifier.Operation.MULTIPLY_BASE);

        tool.addAttributeModifier(Attributes.ATTACK_DAMAGE, damageModifier, EquipmentSlot.MAINHAND);
        tool.addAttributeModifier(Attributes.ATTACK_SPEED, speedModifier, EquipmentSlot.MAINHAND);

        tool.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        tool.hideTooltipPart(ItemStack.TooltipPart.UNBREAKABLE);
    }

    public static void revertUpgrades(ItemStack tool) {
        if (tool.isEmpty() || !tool.hasTag()) {
            return;
        }

        CompoundTag mainTag = tool.getTag();
        if (mainTag == null || !mainTag.contains(TAG_UPGRADE_MAIN)) {
            return;
        }

        CompoundTag upgradeTag = mainTag.getCompound(TAG_UPGRADE_MAIN);
        boolean wasOriginallyUnbreakable = upgradeTag.getBoolean(TAG_WAS_UNBREAKABLE);
        boolean hadOriginalModifiers = upgradeTag.getBoolean(TAG_HAD_MODIFIERS);

        tool.resetHoverName();
        mainTag.remove("HideFlags");
        mainTag.remove(TAG_UPGRADE_MAIN);

        if (!wasOriginallyUnbreakable) {
            mainTag.remove("Unbreakable");
        }

        if (hadOriginalModifiers) {
            if (mainTag.contains("AttributeModifiers", Tag.TAG_LIST)) {
                ListTag modifiersList = mainTag.getList("AttributeModifiers", Tag.TAG_COMPOUND);
                for (int i = modifiersList.size() - 1; i >= 0; i--) {
                    CompoundTag modifierTag = modifiersList.getCompound(i);
                    if (modifierTag.hasUUID("UUID")) {
                        UUID uuid = modifierTag.getUUID("UUID");
                        if (uuid.equals(ATTACK_DAMAGE_MODIFIER_UUID) || uuid.equals(ATTACK_SPEED_MODIFIER_UUID)) {
                            modifiersList.remove(i);
                        }
                    }
                }
                if (modifiersList.isEmpty()) {
                    mainTag.remove("AttributeModifiers");
                }
            }
        } else {
            mainTag.remove("AttributeModifiers");
        }

        if (mainTag.isEmpty()) {
            tool.setTag(null);
        }
    }

    public static int getSuperchargeBuff(){
        return superchargeBuff;
    }
}