package com.naterbobber.darkerdepths.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StringLightHandler {
    private static final Map<UUID, BlockInfo> placementInfo = new HashMap<>();

    public static Map<UUID, BlockInfo> getPlacementInfo() {
        return placementInfo;
    }

    public static BlockInfo getBlockInfo(UUID uuid) {
        return placementInfo.get(uuid);
    }

    public static void setPlacement(Player player, BlockPos pos, BlockState state) {
        placementInfo.put(player.getUUID(), new BlockInfo(pos, state));
    }

    public static void removePlacement(UUID uuid) {
        placementInfo.remove(uuid);
    }

    public record BlockInfo(BlockPos pos, BlockState state) {}
}
