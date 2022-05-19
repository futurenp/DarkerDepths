package com.naterbobber.darkerdepths.world.gen.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.world.gen.features.config.CorrespondentLayersConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

public class CorrespondentLayersFeature extends Feature<CorrespondentLayersConfig> {

    public CorrespondentLayersFeature(Codec<CorrespondentLayersConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CorrespondentLayersConfig> context) {
        WorldGenLevel worldgenlevel = context.level();
        CorrespondentLayersConfig config = context.config();
        Random random = context.random();
        BlockPos blockpos = context.origin();
        Predicate<BlockState> predicate = getReplaceableTag(config);
        int i = config.xzRadius.sample(random) + 1;
        int j = config.xzRadius.sample(random) + 1;
        Set<BlockPos> set = this.placeGroundPatch(worldgenlevel, config, random, blockpos, predicate, i, j);
        this.distributeVegetation(context, worldgenlevel, config, random, set, i, j);
        return !set.isEmpty();
    }

    protected Set<BlockPos> placeGroundPatch(WorldGenLevel world, CorrespondentLayersConfig config, Random random, BlockPos pos, Predicate<BlockState> predicate, int xRadius, int zRadius) {
        BlockPos.MutableBlockPos mut = pos.mutable();
        BlockPos.MutableBlockPos mut1 = mut.mutable();
        Direction direction = config.surface.getDirection();
        Direction direction1 = direction.getOpposite();
        Set<BlockPos> set = new HashSet<>();

        for(int i = -xRadius; i <= xRadius; ++i) {
            boolean flag = i == -xRadius || i == xRadius;

            for(int j = -zRadius; j <= zRadius; ++j) {
                boolean flag1 = j == -zRadius || j == zRadius;
                boolean flag2 = flag || flag1;
                boolean flag3 = flag && flag1;
                boolean flag4 = flag2 && !flag3;
                if (!flag3 && (!flag4 || config.extraEdgeColumnChance != 0.0F && !(random.nextFloat() > config.extraEdgeColumnChance))) {
                    mut.setWithOffset(pos, i, 0, j);

                    for(int k = 0; world.isStateAtPosition(mut, DripstoneUtils::isEmptyOrWater) && k < config.verticalRange; ++k) {
                        mut.move(direction);
                    }

                    for(int i1 = 0; world.isStateAtPosition(mut, (state) -> !(DripstoneUtils.isEmptyOrWater(state))) && i1 < config.verticalRange; ++i1) {
                        mut.move(direction1);
                    }

                    mut1.setWithOffset(mut, config.surface.getDirection());
                    BlockState blockstate = world.getBlockState(mut1);
                    if (world.isStateAtPosition(mut, DripstoneUtils::isEmptyOrWater) && blockstate.isFaceSturdy(world, mut1, config.surface.getDirection().getOpposite())) {
                        int l = config.depth.sample(random) + (config.extraBottomBlockChance > 0.0F && random.nextFloat() < config.extraBottomBlockChance ? 1 : 0);
                        BlockPos blockpos = mut1.immutable();
                        boolean flag5 = this.placeGround(world, config, predicate, random, mut1, l);
                        if (flag5) {
                            set.add(blockpos);
                        }
                    }
                }
            }
        }

        return set;
    }

    protected void distributeVegetation(FeaturePlaceContext<CorrespondentLayersConfig> context, WorldGenLevel world, CorrespondentLayersConfig config, Random random, Set<BlockPos> pos, int p_160619_, int p_160620_) {
        for(BlockPos blockpos : pos) {
            if (config.vegetationChance > 0.0F && random.nextFloat() < config.vegetationChance) {
                this.placeVegetation(world, config, context.chunkGenerator(), random, blockpos);
            }
        }

    }

    protected boolean placeVegetation(WorldGenLevel world, CorrespondentLayersConfig config, ChunkGenerator generator, Random random, BlockPos pos) {
        if (world.isStateAtPosition(pos.relative(config.surface.getDirection().getOpposite()), BlockBehaviour.BlockStateBase::isAir)) {
            return config.vegetationFeature.get().place(world, generator, random, pos.relative(config.surface.getDirection().getOpposite()));
        } else {
            return false;
        }
    }

    protected boolean placeGround(WorldGenLevel world, CorrespondentLayersConfig config, Predicate<BlockState> predicate, Random random, BlockPos.MutableBlockPos pos, int tries) {
        for(int i = 0; i < tries; ++i) {
            BlockState blockstate = config.groundState.getState(random, pos);
            BlockState belowState = config.belowState.getState(random, pos);
            if (world.getBlockState(pos.below()).is(Blocks.TUFF) || world.getBlockState(pos.below()).is(Blocks.DEEPSLATE)) {
                world.setBlock(pos.below(), belowState, 2);
                world.setBlock(pos, blockstate, 2);
            }
        }

        return true;
    }

    private static Predicate<BlockState> getReplaceableTag(CorrespondentLayersConfig config) {
        Tag<Block> tag = BlockTags.getAllTags().getTag(config.replaceable);
        return tag == null ? (blockState) -> true : (state) -> state.is(tag);
    }
}
