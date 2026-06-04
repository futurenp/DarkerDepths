package com.naterbobber.darkerdepths.api.death_anchor;

import com.naterbobber.darkerdepths.damage.DDDamageTypes;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import com.naterbobber.darkerdepths.init.DDPoiTypes;
import com.naterbobber.darkerdepths.network.packets.DeathAnchorActivatePacket;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Optional;
import java.util.Set;

public class SoulBindingHandler {
    public static void handleDeath(Player player, LivingDeathEvent event) {
        if (player.level() instanceof ServerLevel serverLevel
                && player instanceof IDeathAnchorExtension deathAnchorExtension
                && deathAnchorExtension.darkerDepths$getDeathAnchorLocation().isPresent()
        ) {
            var globalPos = deathAnchorExtension.darkerDepths$getDeathAnchorLocation().get();
            var resourcekey = globalPos.dimension();
            var newServer =  serverLevel.getServer().getLevel(resourcekey);
            var poiKey = DDPoiTypes.DEATH_ANCHOR.getKey();
            var pos = globalPos.pos();

            if (newServer == null) return;

            boolean exists = newServer.getPoiManager().existsAtPosition(poiKey, pos);
            if (!exists) return;

            event.setCanceled(true);
            player.setHealth(1.0F);
            player.addEffect(new MobEffectInstance(DDMobEffects.SOUL_BINDING, 200, 0, true, false));

            var uncheckedTeleportPos = pos.above();

            if (!serverLevel.getBlockState(uncheckedTeleportPos).isAir()) {
                checkAlternateTeleportPositions(player, serverLevel, uncheckedTeleportPos);
            }

            player.teleportTo(newServer, uncheckedTeleportPos.getX() + 0.5D, uncheckedTeleportPos.getY(), uncheckedTeleportPos.getZ() + 0.5D, Set.of(), 0, 0);
            handleHealthDebuff(player, deathAnchorExtension);

            if (player instanceof ServerPlayer serverPlayer) {
                PacketDistributor.sendToPlayer(serverPlayer, new DeathAnchorActivatePacket());
            }

            player.setRemainingFireTicks(0);
        }
    }

    private static void checkAlternateTeleportPositions(Player player, ServerLevel serverLevel, BlockPos uncheckedTeleportPos) {
        var teleportPos = new BlockPos.MutableBlockPos();

        boolean safe = false;
        int radius = 1;
        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    teleportPos.set(uncheckedTeleportPos.offset(x, y, z));
                    Vec3 vec3 = DismountHelper.findSafeDismountLocation(player.getType(), serverLevel, teleportPos, true);
                    if (vec3 != null) {
                        uncheckedTeleportPos = BlockPos.containing(vec3);
                        safe = true;
                    }
                }
            }
        }
        if (!safe) {
            if (serverLevel.getBlockState(uncheckedTeleportPos).is(BlockTags.FEATURES_CANNOT_REPLACE) && serverLevel.getBlockState(uncheckedTeleportPos.above()).is(BlockTags.FEATURES_CANNOT_REPLACE)) {
                serverLevel.destroyBlock(uncheckedTeleportPos, false);
                serverLevel.destroyBlock(uncheckedTeleportPos.above(), false);
            }
        }
    }

    private static void handleHealthDebuff(Player player, IDeathAnchorExtension deathAnchorExtension) {
        var healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        double currentModifierAmount;
        if(healthAttribute != null) {
            if(healthAttribute.hasModifier(IDeathAnchorExtension.MAX_HEALTH_DEBUFF_ID)) {
                currentModifierAmount = -1 * healthAttribute.getModifier(IDeathAnchorExtension.MAX_HEALTH_DEBUFF_ID).amount();
                currentModifierAmount = Math.max(((1 - currentModifierAmount) / 2) - 1, -0.9);
            } else {
                currentModifierAmount = -0.5;
            }
            healthAttribute.addOrReplacePermanentModifier(
                    new AttributeModifier(IDeathAnchorExtension.MAX_HEALTH_DEBUFF_ID, currentModifierAmount, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            );
        }
        deathAnchorExtension.darkerDepths$setHealthCoolDown(IDeathAnchorExtension.RECOVERY_COOLDOWN);
    }

    public static void handleRespawn(Player originalPlayer, Player respawnedPlayer) {
        var healthAttribute = originalPlayer.getAttribute(Attributes.MAX_HEALTH);
        if(healthAttribute == null) return;
        if(!healthAttribute.hasModifier(IDeathAnchorExtension.MAX_HEALTH_DEBUFF_ID)) return;
        healthAttribute.getModifier(IDeathAnchorExtension.MAX_HEALTH_DEBUFF_ID).amount();
        var newHealthAttribute = respawnedPlayer.getAttribute(Attributes.MAX_HEALTH);
        if(newHealthAttribute == null || healthAttribute.getModifier(IDeathAnchorExtension.MAX_HEALTH_DEBUFF_ID) == null) return;
        newHealthAttribute.addOrReplacePermanentModifier(healthAttribute.getModifier(IDeathAnchorExtension.MAX_HEALTH_DEBUFF_ID));
    }

    public static void handleDeathAnchorReset(Entity entity, MobEffectInstance mobEffectInstance) {
        if (!(mobEffectInstance != null && mobEffectInstance.getEffect().value() == DDMobEffects.SOUL_BINDING.get())) return;

        if (entity instanceof IDeathAnchorExtension deathAnchorLocation && deathAnchorLocation.darkerDepths$getDeathAnchorLocation().isPresent()) {
            var globalPos = deathAnchorLocation.darkerDepths$getDeathAnchorLocation().get();
            var levelKey = globalPos.dimension();
            var pos = globalPos.pos();
            var poiKey = DDPoiTypes.DEATH_ANCHOR.getKey();
            var newServer = entity.level().getServer().getLevel(levelKey);

            if (newServer != null && newServer.getPoiManager().existsAtPosition(poiKey, pos)) {
                newServer.scheduleTick(pos, DDBlocks.DEATH_ANCHOR.get(), 2);
            }

            deathAnchorLocation.darkerDepths$setDeathAnchorLocation(Optional.empty());
        }

        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            var damageSource = DDDamageTypes.getDamageSource(entity.level(), DDResourceKeys.DamageTypes.SOUL_BINDING_DAMAGE);
            entity.hurt(damageSource, Float.MAX_VALUE);
        }
    }
}
