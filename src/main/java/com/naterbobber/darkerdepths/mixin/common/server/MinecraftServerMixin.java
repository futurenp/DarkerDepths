package com.naterbobber.darkerdepths.mixin.common.server;

import net.minecraft.block.Block;
import net.minecraft.entity.player.SpawnLocationHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IServerWorldInfo;
import net.minecraftforge.event.ForgeEventFactory;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

//<>

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Shadow @Final private static Logger LOGGER;

    @Inject(method = "func_240786_a_", at = @At("HEAD"), cancellable = true)
    private static void onInitialSpawn(ServerWorld worldIn, IServerWorldInfo worldInfo, boolean hasBonusChest, boolean flag, boolean flag1, CallbackInfo info) {
        ChunkGenerator chunkGenerator = worldIn.getChunkProvider().getChunkGenerator();
        if (!flag1) {
            worldInfo.setSpawn(BlockPos.ZERO.up(chunkGenerator.getGroundHeight()));
        } else if (flag) {
            worldInfo.setSpawn(BlockPos.ZERO.up());
        } else {
            if (ForgeEventFactory.onCreateWorldSpawn(worldIn, worldInfo)) return;
            BiomeProvider biomeProvider = chunkGenerator.getBiomeProvider();
            List<Biome> list = biomeProvider.getBiomesToSpawnIn();
            Random random = new Random(worldIn.getSeed());
            BlockPos blockPos = biomeProvider.func_225531_a_(0, worldIn.getSeaLevel(), 0, 256, list, random);
            ChunkPos chunkPos = blockPos == null ? new ChunkPos(0, 0) : new ChunkPos(blockPos);
            if (blockPos == null) {
                LOGGER.warn("Unable to find spawn biome");
            }

            for (Block block : BlockTags.VALID_SPAWN.getAllElements()) {
                if (biomeProvider.getSurfaceBlocks().contains(block.getDefaultState())) {
                    break;
                }
            }

            worldInfo.setSpawn(chunkPos.asBlockPos().add(8, chunkGenerator.getGroundHeight(), 8));
            int i1 = 0;
            int j1 = 0;
            int i = 0;
            int j = -1;

            for(int l = 0; l < 1024; ++l) {
                if (i1 > -16 && i1 <= 16 && j1 > -16 && j1 <= 16) {
                    BlockPos blockpos1 = SpawnLocationHelper.func_241094_a_(worldIn, new ChunkPos(chunkPos.x + i1, chunkPos.z + j1), flag);
                    if (blockpos1 != null) {
                        worldInfo.setSpawn(blockpos1);
                        break;
                    }
                }
                if (i1 == j1 || i1 < 0 && i1 == -j1 || i1 > 0 && i1 == 1 - j1) {
                    int k1 = i;
                    i = -j;
                    j = k1;
                }
                i1 += i;
                j1 += j;
            }
            if (hasBonusChest) {
                ConfiguredFeature<?, ?> configuredfeature = Feature.BONUS_CHEST.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
                configuredfeature.func_236265_a_(worldIn, worldIn.func_241112_a_(), chunkGenerator, worldIn.rand, new BlockPos(worldInfo.getSpawnX(), worldInfo.getSpawnY(), worldInfo.getSpawnZ()));
            }
        }
    }
}