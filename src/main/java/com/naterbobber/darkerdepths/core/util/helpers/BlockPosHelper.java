package com.naterbobber.darkerdepths.core.util.helpers;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;

//<>

public class BlockPosHelper {
    public static void setAndOffset(BlockPos.Mutable pos, Vector3i vec3i, Direction direction) {
        pos.setPos(vec3i.getX() + direction.getXOffset(), vec3i.getY() + direction.getYOffset(), vec3i.getZ() + direction.getZOffset());
    }

    public static BlockPos set(BlockPos.Mutable mutable, Vector3i pos, int x, int y, int z) {
        return mutable.add(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
    }

    public static void setAndOffset(BlockPos.Mutable pos, Vector3i vec3i, int x, int y, int z) {
        pos.setPos(vec3i.getX() + x, vec3i.getY() + y, vec3i.getZ() + z);
    }

    public static BlockPos atY(BlockPos pos, int yIn) {
        return new BlockPos.Mutable(pos.getX(), yIn, pos.getZ());
    }
}