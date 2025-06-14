package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.api.DeathAnchorLocation;
import com.naterbobber.darkerdepths.blocks.DeathAnchorBlock;
import com.naterbobber.darkerdepths.entities.BodySnatcher;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDEnchantments;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import com.naterbobber.darkerdepths.init.DDPoiTypes;
import com.naterbobber.darkerdepths.item.StilettoItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;
import java.util.Set;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        SpawnPlacements.register(DDEntityTypes.GLOWSHROOM_MONSTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlowshroomMonsterEntity::canSpawn);
        event.put(DDEntityTypes.GLOWSHROOM_MONSTER.get(), GlowshroomMonsterEntity.createAttributes().build());
        event.put(DDEntityTypes.BODY_SNATCHER.get(), BodySnatcher.createAttributes().build());
    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource damageSource = event.getSource();
        Entity damageSourceEntity = damageSource.getEntity();
        if (entity.hasEffect(DDMobEffects.SOUL_BINDING.get()) && entity.getEffect(DDMobEffects.SOUL_BINDING.get()).getDuration() > 0) {
            event.setCanceled(false);
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
            event.setCanceled(true);

            GlobalPos globalPos = deathAnchorLocation.getDeathAnchorLocation().get();
            ResourceKey<Level> resourcekey = globalPos.dimension();
            ServerLevel newServer =  serverLevel.getServer().getLevel(resourcekey);
            ResourceKey<PoiType> key = DDPoiTypes.DEATH_ANCHOR.getKey();

            if (key == null || newServer == null) return;

            BlockPos pos = globalPos.pos();

            boolean exists = newServer.getPoiManager().existsAtPosition(key, pos);

            if (!exists) return;

            entity.setHealth(1.0F);
            entity.teleportTo(newServer, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, Set.of(), 0, 0);
            entity.addEffect(new MobEffectInstance(DDMobEffects.SOUL_BINDING.get(), 200));
            newServer.broadcastEntityEvent(entity, (byte)35);
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
            entity.kill();
        }
    }

}
