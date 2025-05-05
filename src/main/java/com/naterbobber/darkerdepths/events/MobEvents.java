package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.init.DDEnchantments;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        SpawnPlacements.register(DDEntityTypes.GLOWSHROOM_MONSTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlowshroomMonsterEntity::canSpawn);
        event.put(DDEntityTypes.GLOWSHROOM_MONSTER.get(), GlowshroomMonsterEntity.createAttributes().build());
    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource damageSource = event.getSource();
        Entity damageSourceEntity = damageSource.getEntity();
        if (entity != null && damageSourceEntity instanceof Player player) {
            ItemStack itemStack = player.getItemInHand(player.getUsedItemHand());
            if (itemStack.is(DDItems.STILETTO.get())) {
                CompoundTag tag = itemStack.getTag();
                if (EnchantmentHelper.getTagEnchantmentLevel(DDEnchantments.SWIFT_STRIKE.get(), itemStack) > 0 && tag != null && tag.getInt("Timeframe") > 0) {
                    player.getCooldowns().removeCooldown(itemStack.getItem());
                    entity.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
                }
            }
        }
    }

}
