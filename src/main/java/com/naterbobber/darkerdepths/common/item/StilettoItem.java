package com.naterbobber.darkerdepths.common.item;

import com.naterbobber.darkerdepths.common.init.DDDataComponents;
import com.naterbobber.darkerdepths.common.init.DDEnchantmentEffects;
import com.naterbobber.darkerdepths.common.init.DDItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.List;

public class StilettoItem extends SwordItem {
    public static final StilettoTier STILETTO = new StilettoTier();

    public StilettoItem(Item.Properties properties) {
        super(STILETTO, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        var itemStack = player.getItemInHand(hand);

        if (player.isFallFlying() || player.isCrouching()) {
            return InteractionResultHolder.pass(itemStack);
        }

        itemStack.hurtAndBreak(2, player, LivingEntity.getSlotForHand(hand));

        double dash = 1.85D;
        var dashMovement = player.getLookAngle().multiply(1.0D, 1.5D, 1.0D).normalize().multiply(dash, dash / 2.0D, dash);
        player.addDeltaMovement(dashMovement);

        player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
        if (level instanceof ServerLevel serverLevel) {
            spawnDashParticles(serverLevel, player, dashMovement);
            var mutableFloat = new MutableFloat(0.0F);
            EnchantmentHelper.runIterationOnItem(itemStack,
                    (holder, i) ->
                            holder.value().modifyItemFilteredCount(DDEnchantmentEffects.QUICK_DASH_DURATION.get(), serverLevel, i, itemStack, mutableFloat));
            int quickDash = Math.max(0, mutableFloat.intValue());

            int cooldown = 200;

            int reducedCooldown = (int) (cooldown * (1 - (0.25F * quickDash)));

            if (!player.getCooldowns().isOnCooldown(itemStack.getItem())) {
                player.getCooldowns().addCooldown(itemStack.getItem(), reducedCooldown);
            }

            itemStack.set(DDDataComponents.STILETTO_TIME, 20);
        }

        return InteractionResultHolder.success(itemStack);
    }

    private static void spawnDashParticles(ServerLevel level, Player player, Vec3 dashMovement) {
        var direction = dashMovement.normalize();
        var center = player.position().add(0.0D, player.getBbHeight() * 0.5D, 0.0D);
        var origin = center.subtract(direction.scale(0.35D));
        var random = player.getRandom();

        for (int i = 0; i < 8; i++) {
            double speed = 0.1D + random.nextDouble() * 0.15D;
            level.sendParticles(ParticleTypes.CLOUD,
                    origin.x + (random.nextDouble() - 0.5D) * 0.5D,
                    origin.y + (random.nextDouble() - 0.5D) * 0.5D,
                    origin.z + (random.nextDouble() - 0.5D) * 0.5D,
                    0, -direction.x * speed, -direction.y * speed, -direction.z * speed, 1.0D);
        }
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int index, boolean selected) {
        int timeframe = itemStack.getOrDefault(DDDataComponents.STILETTO_TIME, 0);
        if (timeframe > 0) {
            itemStack.set(DDDataComponents.STILETTO_TIME, timeframe - 1);
        }
        int readyTicks = itemStack.getOrDefault(DDDataComponents.STILETTO_READY_TIME, 0);
        if (readyTicks > 0) {
            itemStack.set(DDDataComponents.STILETTO_READY_TIME, readyTicks - 1);
         }
        if (entity instanceof Player player && player.getCooldowns().getCooldownPercent(this, 0.0F) == 0.1F) {
            itemStack.set(DDDataComponents.STILETTO_READY_TIME, 10);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext context, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.darkerdepths.press_shift").withStyle(ChatFormatting.GRAY));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.darkerdepths.stiletto.shift_desc").withStyle(ChatFormatting.YELLOW));
        }

        super.appendHoverText(pStack, context, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        if (itemAbility == ItemAbilities.SWORD_SWEEP) {
            return false;
        }

        return super.canPerformAction(stack, itemAbility);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if(enchantment.is(Enchantments.SWEEPING_EDGE)) {
            return false;
        }

        return super.supportsEnchantment(stack, enchantment);
    }

    public static class StilettoTier implements Tier {
        @Override
        public int getUses() {
            return 400;
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
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return null;
        }

        @Override
        public int getEnchantmentValue() {
            return 10;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(DDItems.FORSAKEN_BRONZE_SCRAP.get());
        }
    }

}
