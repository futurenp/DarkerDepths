package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.api.DeathAnchorLocation;
import com.naterbobber.darkerdepths.entities.BodySnatcherEntity;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.entities.VoidSoulEntity;
import com.naterbobber.darkerdepths.entities.VoidSoulKnightEntity;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDDamageTypes;
import com.naterbobber.darkerdepths.init.DDDataComponents;
import com.naterbobber.darkerdepths.component.EnchantmentEffects;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import com.naterbobber.darkerdepths.init.DDPoiTypes;
import com.naterbobber.darkerdepths.network.SendDeathAnchorPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.Optional;
import java.util.Set;

@EventBusSubscriber(modid = DarkerDepths.MOD_ID)
public class MobEvents {

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
                EnchantmentHelper.runIterationOnItem(itemStack, (holder, i) -> holder.value().modifyItemFilteredCount(EnchantmentEffects.SWIFT_STRIKE_HIT, serverLevel, i, itemStack, mutableFloat));
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
        LivingEntity entity = event.getEntity();
        if (entity.level() instanceof ServerLevel serverLevel && entity instanceof DeathAnchorLocation deathAnchorLocation && deathAnchorLocation.getDeathAnchorLocation().isPresent()) {
            GlobalPos globalPos = deathAnchorLocation.getDeathAnchorLocation().get();
            ResourceKey<Level> resourcekey = globalPos.dimension();
            ServerLevel newServer =  serverLevel.getServer().getLevel(resourcekey);
            ResourceKey<PoiType> key = DDPoiTypes.DEATH_ANCHOR.getKey();

            if (key == null || newServer == null) return;

            BlockPos pos = globalPos.pos();

            boolean exists = newServer.getPoiManager().existsAtPosition(key, pos);

            if (!exists) return;

            event.setCanceled(true);
            entity.setHealth(1.0F);

            BlockPos teleportPos = pos.above();

            if (!serverLevel.getBlockState(teleportPos).isAir()) {
                boolean safe = false;
                int radius = 1;
                for (int y = -radius; y <= radius; y++) {
                    for (int x = -radius; x <= radius; x++) {
                        for (int z = -radius; z <= radius; z++) {
                            BlockPos blockPos = teleportPos.offset(x, y, z);
                            Vec3 vec3 = DismountHelper.findSafeDismountLocation(entity.getType(), serverLevel, blockPos, true);
                            if (vec3 != null) {
                                teleportPos = BlockPos.containing(vec3);
                                safe = true;
                            }
                        }
                    }
                }
                if (!safe) {
                    if (serverLevel.getBlockState(teleportPos).is(BlockTags.FEATURES_CANNOT_REPLACE) && serverLevel.getBlockState(teleportPos.above()).is(BlockTags.FEATURES_CANNOT_REPLACE)) {
                        serverLevel.destroyBlock(teleportPos, false);
                        serverLevel.destroyBlock(teleportPos.above(), false);
                    }
                }
            }

            entity.addEffect(new MobEffectInstance(DDMobEffects.SOUL_BINDING, 200, 0, true, false));
            entity.teleportTo(newServer, teleportPos.getX() + 0.5D, teleportPos.getY(), teleportPos.getZ() + 0.5D, Set.of(), 0, 0);

            if (entity instanceof ServerPlayer serverPlayer) {
                PacketDistributor.sendToPlayer(serverPlayer, new SendDeathAnchorPacket());
            }

            entity.setRemainingFireTicks(0);
        }
    }

    @SubscribeEvent
    public static void onMobEffectRemove(MobEffectEvent.Remove event) {
        if (event.getEffect() == DDMobEffects.SOUL_BINDING.get()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onMobEffectExpired(MobEffectEvent.Expired event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (effectInstance != null && effectInstance.getEffect() == DDMobEffects.SOUL_BINDING.get()) {
            if (entity instanceof DeathAnchorLocation deathAnchorLocation && deathAnchorLocation.getDeathAnchorLocation().isPresent()) {
                GlobalPos globalPos = deathAnchorLocation.getDeathAnchorLocation().get();
                ResourceKey<Level> resourcekey = globalPos.dimension();
                BlockPos pos = globalPos.pos();
                ResourceKey<PoiType> key = DDPoiTypes.DEATH_ANCHOR.getKey();

                if (key == null) return;

                ServerLevel newServer;
                if (entity.level() instanceof ServerLevel serverLevel && (newServer = serverLevel.getServer().getLevel(resourcekey)) != null && newServer.getPoiManager().existsAtPosition(key, pos)) {
                    newServer.scheduleTick(pos, DDBlocks.DEATH_ANCHOR.get(), 2);
                }

                deathAnchorLocation.setDeathAnchorLocation(Optional.empty());
            }
            if (entity instanceof Player player && !player.getAbilities().instabuild) {
                DamageSource damageSource = new DamageSource(
                        entity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
                                .getHolderOrThrow(DDDamageTypes.SOUL_BINDING_DAMAGE)
                );
                entity.hurt(damageSource, Float.MAX_VALUE);
            }
        }
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
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, MobEffectInstance.INFINITE_DURATION, 0, false, false, true));
        }

        if (previouslyEquipped.is(DDItems.GLOWSHROOM_CAP.get())) {
            if (!newlyEquipped.is(DDItems.GLOWSHROOM_CAP.get())) {
                player.removeEffect(MobEffects.DIG_SPEED);
            }
        }
    }

}
