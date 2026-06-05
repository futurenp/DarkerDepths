package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.api.death_anchor.SoulBindingHandler;
import com.naterbobber.darkerdepths.init.*;
import com.naterbobber.darkerdepths.init.DDEnchantmentEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.stream.StreamSupport;

@EventBusSubscriber(modid = DarkerDepths.MOD_ID)
public class LivingEvents {

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();
        DamageSource damageSource = event.getSource();
        Entity damageSourceEntity = damageSource.getEntity();
        if (entity.hasEffect(DDMobEffects.SOUL_BINDING) && entity.getEffect(DDMobEffects.SOUL_BINDING).getDuration() > 0) {
            event.setNewDamage(0.0F);
        }
        if (damageSourceEntity instanceof Player player && player.level() instanceof ServerLevel serverLevel) {
            ItemStack itemStack = player.getItemInHand(player.getUsedItemHand());
            if (itemStack.is(DDItems.STILETTO.get())) {
                MutableFloat mutableFloat = new MutableFloat(0.0F);
                EnchantmentHelper.runIterationOnItem(itemStack, (holder, i) -> holder.value().modifyItemFilteredCount(DDEnchantmentEffects.SWIFT_STRIKE_HIT.get(), serverLevel, i, itemStack, mutableFloat));
                int swiftStrike = Math.max(0, mutableFloat.intValue());

                if (swiftStrike > 0 && itemStack.getOrDefault(DDDataComponents.STILETTO_TIME, 0) > 0) {
                    player.getCooldowns().removeCooldown(itemStack.getItem());
                    entity.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeathEvent(LivingDeathEvent event) {
        if(event.getEntity() instanceof Player player) {
            SoulBindingHandler.handleDeath(player, event);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        SoulBindingHandler.handleRespawn(event.getOriginal(), event.getEntity());
    }

    @SubscribeEvent
    public static void onMobEffectRemove(MobEffectEvent.Remove event) {
        if (event.getEffect() == DDMobEffects.SOUL_BINDING.get()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onMobEffectExpired(MobEffectEvent.Expired event) {
        SoulBindingHandler.handleDeathAnchorReset(event.getEntity(), event.getEffectInstance());
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof Player player) || player.level().isClientSide()) {
            return;
        }

        if (event.getSlot() != EquipmentSlot.HEAD) {
            return;
        }

        ItemStack newlyEquipped = event.getTo();
        ItemStack previouslyEquipped = event.getFrom();

        if (newlyEquipped.is(DDItems.GLOWSHROOM_CAP.get())) {
            player.addEffect(new MobEffectInstance(
                            MobEffects.DIG_SPEED,
                            MobEffectInstance.INFINITE_DURATION,
                            0,
                            false,
                            false,
                            true
                    )
            );
        }

        if (previouslyEquipped.is(DDItems.GLOWSHROOM_CAP.get())) {
            if (!newlyEquipped.is(DDItems.GLOWSHROOM_CAP.get())) {
                player.removeEffect(MobEffects.DIG_SPEED);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingEntityUseItem(LivingEntityUseItemEvent.Finish event) {
        if(!(event.getEntity() instanceof Player player)) {
            return;
        }

        if(!event.getItem().is(Items.MILK_BUCKET)) {
            return;
        }

        if(StreamSupport.stream(player.getArmorSlots().spliterator(), false)
                .anyMatch(armor -> armor.is(DDItems.GLOWSHROOM_CAP.get()))) {
            player.addEffect(new MobEffectInstance(
                            MobEffects.DIG_SPEED,
                            MobEffectInstance.INFINITE_DURATION,
                            0,
                            false,
                            false,
                            true
                    )
            );
        }
    }

}
