package com.naterbobber.darkerdepths.item;

import com.naterbobber.darkerdepths.init.DDEnchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class StilettoItem extends SwordItem {
    public static final StilettoTier STILETTO = new StilettoTier();
    public static final String TIME_FRAME = "TimeFrame";
    public static final String READY_TICKS = "ReadyTicks";

    public StilettoItem(int damage, float speed, Properties properties) {
        super(STILETTO, damage, speed, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack itemStack = player.getItemInHand(hand);

        //Check for flying with elytra
        if (player.isFallFlying()) {
            return InteractionResultHolder.pass(itemStack);
        }

        itemStack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(hand));

        double dash = 1.85D;
        player.addDeltaMovement(player.getLookAngle().multiply(1.0D, 1.5D, 1.0D).normalize().multiply(dash, dash / 2.0D, dash));

        player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
        int quickDash = EnchantmentHelper.getTagEnchantmentLevel(DDEnchantments.QUICK_DASH.get(), itemStack);
        int cooldown = 200;
        int reducedCooldown = (int) (cooldown * (1 - (0.25F * quickDash)));

        if (!player.getCooldowns().isOnCooldown(itemStack.getItem())) {
            player.getCooldowns().addCooldown(itemStack.getItem(), reducedCooldown);
        }
        itemStack.getOrCreateTag().putInt(TIME_FRAME, 20);

        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int index, boolean selected) {
        CompoundTag tag = itemStack.getTag();
        if (tag != null) {
            int timeframe = tag.getInt(TIME_FRAME);
            if (timeframe > 0) {
                tag.putInt(TIME_FRAME, timeframe - 1);
            }
            int readyTicks = tag.getInt(READY_TICKS);
            if (readyTicks > 0) {
                tag.putInt(READY_TICKS, readyTicks - 1);
            }
        }
        if (entity instanceof Player player && player.getCooldowns().getCooldownPercent(this, 0.0F) == 0.1F) {
            itemStack.getOrCreateTag().putInt(READY_TICKS, 10);
        }
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
            return Ingredient.EMPTY;
        }
    }

}
