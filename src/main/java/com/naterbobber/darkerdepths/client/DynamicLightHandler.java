package com.naterbobber.darkerdepths.client;

import com.naterbobber.darkerdepths.compat.DDCompat;
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
    public static final Map<BlockPos, Boolean> LIGHT_SOURCES = new ConcurrentHashMap<>();
    private static final boolean ENABLED = !DDCompat.LAMB_DYNAMIC_LIGHTS.isLoaded();

    public static void onClientTick() {
        if(!ENABLED) return;
        var player = Minecraft.getInstance().player;
        var level = Minecraft.getInstance().level;

        if (player == null || level == null) {
            return;
        }

        LIGHT_SOURCES.replaceAll((pos, value) -> false);
        var searchArea = player.getBoundingBox().inflate(64);
        var entities = level.getEntitiesOfClass(LivingEntity.class, searchArea, DynamicLightHandler::shouldGlow);

        entities.forEach(entity -> {
            BlockPos lightPos = entity.getOnPos();
            if (!level.getBlockState(lightPos).isSolid()) {
                lightPos = entity.blockPosition();
            }
            LIGHT_SOURCES.put(lightPos, true);
        });


        if (!LIGHT_SOURCES.isEmpty()) {
            LIGHT_SOURCES.forEach((pos, value) -> {
                level.getChunkSource().getLightEngine().checkBlock(pos);
            });
            LIGHT_SOURCES.entrySet().removeIf(entry -> !entry.getValue());
        }
    }

    public static boolean shouldGlow(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == DDItems.GLOWSHROOM_CAP.get() && !entity.isSpectator();
    }
}

