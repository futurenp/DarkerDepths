package com.naterbobber.darkerdepths.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class StilettoItem extends SwordItem {
    public static final StilettoTier STILETTO = new StilettoTier();

    public StilettoItem(int damage, float speed, Properties properties) {
        super(STILETTO, damage, speed, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        itemStack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(hand));

        double dash = 1.85D;
        player.addDeltaMovement(player.getLookAngle().multiply(1.0D, 1.5D, 1.0D).normalize().multiply(dash, dash / 2.0D, dash));
        player.getCooldowns().addCooldown(this, 200);

        player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);

        return InteractionResultHolder.success(itemStack);
    }

    public static class StilettoTier implements Tier {
        @Override
        public int getUses() {
            return 800;
        }

        @Override
        public float getSpeed() {
            return 0.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 2.0F;
        }

        @Override
        public int getLevel() {
            return 2;
        }

        @Override
        public int getEnchantmentValue() {
            return 10;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return null;
        }
    }

}
