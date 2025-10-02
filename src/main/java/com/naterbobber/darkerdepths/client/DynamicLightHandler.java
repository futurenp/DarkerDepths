package com.naterbobber.darkerdepths.client;

import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@OnlyIn(Dist.CLIENT)
public class DynamicLightHandler {
    private static final Minecraft INSTANCE = Minecraft.getInstance();
    public static final Map<BlockPos, LightValue> LIGHT_SOURCES = new ConcurrentHashMap<>();
    private static final int LIGHT_SCAN_RADIUS = 64;

    public static void onClientTick() {

        if (INSTANCE.player == null || INSTANCE.level == null) {
            return;
        }

        LIGHT_SOURCES.forEach((pos, value) -> value.shouldKeep = false);

        AABB searchArea = INSTANCE.player.getBoundingBox().inflate(LIGHT_SCAN_RADIUS);

        INSTANCE.level.getEntitiesOfClass(LivingEntity.class, searchArea, DynamicLightHandler::shouldGlow)
                .forEach(entity -> {
                    BlockPos lightPos = entity.getOnPos();
                    if (!INSTANCE.level.getBlockState(lightPos).isSolid()) {
                        lightPos = entity.blockPosition();
                    }
                    LIGHT_SOURCES.computeIfAbsent(lightPos, k -> new LightValue()).shouldKeep = true;
                });

        if (!LIGHT_SOURCES.isEmpty()) {
            LIGHT_SOURCES.forEach((pos, value) -> {
                INSTANCE.level.getChunkSource().getLightEngine().checkBlock(pos);
            });
            LIGHT_SOURCES.entrySet().removeIf(entry -> !entry.getValue().shouldKeep);
        }
    }

    public static boolean shouldGlow(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == DDItems.GLOWSHROOM_CAP.get() && !entity.isSpectator();
    }

    public static class LightValue {
        public boolean shouldKeep = true;
    }
}

