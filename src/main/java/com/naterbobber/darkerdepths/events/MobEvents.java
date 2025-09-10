package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.api.DeathAnchorLocation;
import com.naterbobber.darkerdepths.entities.BodySnatcherEntity;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.entities.VoidSoulEntity;
import com.naterbobber.darkerdepths.entities.VoidSoulKnightEntity;
import com.naterbobber.darkerdepths.init.*;
import com.naterbobber.darkerdepths.item.StilettoItem;
import com.naterbobber.darkerdepths.network.SendDeathAnchorPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
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
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.Optional;
import java.util.Set;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        SpawnPlacements.register(DDEntityTypes.GLOWSHROOM_MONSTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlowshroomMonsterEntity::checkMonsterSpawnRules);
        SpawnPlacements.register(DDEntityTypes.BODY_SNATCHER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BodySnatcherEntity::checkMonsterSpawnRules);
        event.put(DDEntityTypes.GLOWSHROOM_MONSTER.get(), GlowshroomMonsterEntity.createAttributes().build());
        event.put(DDEntityTypes.BODY_SNATCHER.get(), BodySnatcherEntity.createAttributes().build());
        event.put(DDEntityTypes.VOID_SOUL_KNIGHT.get(), VoidSoulKnightEntity.createAttributes().build());
        event.put(DDEntityTypes.VOID_SOUL.get(), VoidSoulEntity.createAttributes().build());
    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource damageSource = event.getSource();
        Entity damageSourceEntity = damageSource.getEntity();
        if (entity.hasEffect(DDMobEffects.SOUL_BINDING.get()) && entity.getEffect(DDMobEffects.SOUL_BINDING.get()).getDuration() > 0) {
            event.setAmount(0.0F);
            event.setCanceled(true);
        }
        if (damageSourceEntity instanceof Player player) {
            ItemStack itemStack = player.getItemInHand(player.getUsedItemHand());
            if (itemStack.is(DDItems.STILETTO.get())) {
                CompoundTag tag = itemStack.getTag();
                if (EnchantmentHelper.getTagEnchantmentLevel(DDEnchantments.SWIFT_STRIKE.get(), itemStack) > 0 && tag != null && tag.getInt(StilettoItem.TIME_FRAME) > 0) {
                    player.getCooldowns().removeCooldown(itemStack.getItem());
                    entity.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {
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

            entity.teleportTo(newServer, teleportPos.getX() + 0.5D, teleportPos.getY(), teleportPos.getZ() + 0.5D, Set.of(), 0, 0);
            entity.addEffect(new MobEffectInstance(DDMobEffects.SOUL_BINDING.get(), 200, 0, true, false));

            if (entity instanceof ServerPlayer serverPlayer) {
                DDNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new SendDeathAnchorPacket());
                DDNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new ServerboundMovePlayerPacket.Pos(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), serverPlayer.onGround()));
            }

            entity.setRemainingFireTicks(0);
            //doesnt work
            serverLevel.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ANVIL_FALL, entity.getSoundSource(), 1.0F, 1.0F, false);
        }
    }

    @SubscribeEvent
    public void onMobEffectRemove(MobEffectEvent.Remove event) {
        if (event.getEffect() == DDMobEffects.SOUL_BINDING.get()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onMobEffectExpired(MobEffectEvent.Expired event) {
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
    public void onEquipmentChange(LivingEquipmentChangeEvent event) {
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
