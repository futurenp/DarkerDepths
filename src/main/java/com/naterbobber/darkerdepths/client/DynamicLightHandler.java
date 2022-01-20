package com.naterbobber.darkerdepths.client;

import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@OnlyIn(Dist.CLIENT)
public class DynamicLightHandler {
    private static final double REFRESH_RATE = 1.0D;
    private static final Minecraft INSTANCE = Minecraft.getInstance();
    public static final Map<BlockPos, LightValue> LIGHT_SOURCES = new ConcurrentHashMap<>();

    public static void tick(LivingEntity entity) {
        if (entity != null && INSTANCE.player != null && INSTANCE.player.tickCount % REFRESH_RATE == 0) {
            if (entity == INSTANCE.player) {
                LIGHT_SOURCES.forEach((pos, value) -> value.shouldKeep = false);

                INSTANCE.level.getEntitiesOfClass(LivingEntity.class, INSTANCE.player.getBoundingBox(), DynamicLightHandler::shouldGlow).forEach(entityIn -> LIGHT_SOURCES.put(entityIn.getOnPos(), new LightValue()));

                if (!LIGHT_SOURCES.isEmpty()) {
                    LIGHT_SOURCES.forEach((pos, value) -> INSTANCE.level.getChunkSource().getLightEngine().checkBlock(pos));
                    LIGHT_SOURCES.entrySet().removeIf(entry -> !entry.getValue().shouldKeep);
                }
            }
        }
    }

    public static boolean shouldGlow(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == DDItems.GLOWSHROOM_CAP.get();
    }

    public static class LightValue {
        public boolean shouldKeep = true;
    }
}
