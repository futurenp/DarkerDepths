package com.naterbobber.darkerdepths.common.events;

import com.naterbobber.darkerdepths.core.registries.DDItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//<>

@OnlyIn(Dist.CLIENT)
public class DynamicLightHandler {
    private static final double REFRESH_RATE = 1.0D;
    private static final Minecraft INSTANCE = Minecraft.getInstance();
    public static final Map<BlockPos, LightValue> LIGHT_SOURCES = new ConcurrentHashMap<>();

    public static void tick(LivingEntity entity) {
        if (entity != null && INSTANCE.player != null && INSTANCE.player.ticksExisted % REFRESH_RATE == 0) {
            if (entity == INSTANCE.player) {
                LIGHT_SOURCES.forEach((pos, value) -> value.shouldKeep = false);

                INSTANCE.world.getEntitiesWithinAABB(LivingEntity.class, INSTANCE.player.getBoundingBox(), DynamicLightHandler::shouldGlow).forEach(entityIn -> LIGHT_SOURCES.put(entityIn.getPosition(), new LightValue()));

                if (!LIGHT_SOURCES.isEmpty()) {
                    LIGHT_SOURCES.forEach((pos, value) -> INSTANCE.world.getChunkProvider().getLightManager().checkBlock(pos));
                    LIGHT_SOURCES.entrySet().removeIf(entry -> !entry.getValue().shouldKeep);
                }
            }
        }
    }

    public static boolean shouldGlow(LivingEntity entity) {
        return entity.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == DDItems.GLOWSHROOM_CAP.get();
    }

    public static class LightValue {
        public boolean shouldKeep = true;
    }
}