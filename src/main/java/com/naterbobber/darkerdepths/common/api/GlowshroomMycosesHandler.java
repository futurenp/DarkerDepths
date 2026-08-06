package com.naterbobber.darkerdepths.common.api;

import com.naterbobber.darkerdepths.common.init.DDMobEffects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GlowshroomMycosesHandler {

    public static void handleFleshEat(Player player, ItemStack item) {
        if(item.is(Items.ROTTEN_FLESH) && player.hasEffect(DDMobEffects.GLOWING_MYCOSES)) {
            giveMycoses(player);

            if(player.hasEffect(MobEffects.HUNGER)) {
                player.removeEffect(MobEffects.HUNGER);
            }

            var foodData = player.getFoodData();
            foodData.setSaturation(foodData.getSaturationLevel() + 5);
        }
    }

    public static void handleUndeadKill(LivingEntity deathSourceEntity, Entity deadEntity) {
        if(deadEntity.getType().is(EntityTypeTags.UNDEAD)) {
            if(deathSourceEntity.hasEffect(DDMobEffects.GLOWING_MYCOSES)) {
                giveMycoses(deathSourceEntity);
            }
        }
    }

    public static void giveMycoses(LivingEntity entity) {
        giveMycoses(entity, 50, 1, false);
    }


    public static void giveMycoses(LivingEntity entity, int duration, int amplifier, boolean playsound) {
        if(!entity.hasEffect(DDMobEffects.GLOWING_MYCOSES_ACTIVE) || playsound) {
            entity.level().playSound(
                    null,
                    entity.blockPosition(),
                    SoundEvents.BEACON_POWER_SELECT,
                    SoundSource.BLOCKS,
                    0.45F,
                    1.5F
            );
        }
        entity.addEffect(new MobEffectInstance(
                DDMobEffects.GLOWING_MYCOSES_ACTIVE,
                duration,
                amplifier,
                false,
                false,
                false)
        );
    }
}
