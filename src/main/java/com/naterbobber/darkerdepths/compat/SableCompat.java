package com.naterbobber.darkerdepths.compat;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.LevelChunk;

public class SableCompat {
    private static final ThreadLocal<SuppressionContext> CONTEXT =
            ThreadLocal.withInitial(SuppressionContext::new);

    public static void beginSuppression(LevelChunk chunk, BlockPos pos) {
        var context = CONTEXT.get();

        context.active = true;
        context.chunk = chunk;
        context.pos = pos.asLong();
    }

    public static void endSuppression() {
        var context = CONTEXT.get();

        context.active = false;
        context.chunk = null;
    }

    public static boolean shouldSuppressBlockChange(LevelChunk chunk, int x, int y, int z) {
        var context = CONTEXT.get();

        return context.active
                && context.chunk == chunk
                && context.pos == BlockPos.asLong(x, y, z);
    }

    private static class SuppressionContext {
        private boolean active;
        private LevelChunk chunk;
        private long pos;
    }
}