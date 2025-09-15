package com.naterbobber.darkerdepths.world.gen.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.world.gen.features.config.CorrespondentLayersConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.HashSet;
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
        RandomSource random = context.random();
        BlockPos blockpos = context.origin();
        Predicate<BlockState> predicate = state -> state.is(config.replaceable);
        int i = config.xzRadius.sample(random) + 1;
        int j = config.xzRadius.sample(random) + 1;
        Set<BlockPos> set = this.placeGroundPatch(worldgenlevel, config, random, blockpos, predicate, i, j);
        this.distributeVegetation(context, worldgenlevel, config, random, set, i, j);
        return !set.isEmpty();
    }

    protected Set<BlockPos> placeGroundPatch(WorldGenLevel world, CorrespondentLayersConfig config, RandomSource random, BlockPos pos, Predicate<BlockState> predicate, int xRadius, int zRadius) {
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

    protected void distributeVegetation(FeaturePlaceContext<CorrespondentLayersConfig> context, WorldGenLevel world, CorrespondentLayersConfig config, RandomSource random, Set<BlockPos> pos, int p_160619_, int p_160620_) {
        for(BlockPos blockpos : pos) {
            if (config.vegetationChance > 0.0F && random.nextFloat() < config.vegetationChance) {
                this.placeVegetation(world, config, context.chunkGenerator(), random, blockpos);
            }
        }

    }

    protected boolean placeVegetation(WorldGenLevel world, CorrespondentLayersConfig config, ChunkGenerator generator, RandomSource random, BlockPos pos) {
        if (world.isStateAtPosition(pos.relative(config.surface.getDirection().getOpposite()), BlockBehaviour.BlockStateBase::isAir) && world.getBlockState(pos).is(config.layers.get(0).getState(random, pos).getBlock())) {
            return config.vegetationFeature.get().place(world, generator, random, pos.relative(config.surface.getDirection().getOpposite()));
        } else {
            return false;
        }
    }


    //yo this method is ass
    //redo and implement dynamic array, so the list can be abstractly expanded to place more blocks underneath
    protected boolean placeGround(WorldGenLevel world, CorrespondentLayersConfig config, Predicate<BlockState> predicate, RandomSource random, BlockPos.MutableBlockPos pos, int tries) {
        for (int i = 0; i < tries; ++i) {
            BlockState blockstate = config.layers.get(0).getState(random, pos);
            BlockState belowState = config.layers.get(1).getState(random, pos);
            BlockState posBelow = world.getBlockState(pos.below());

            //Check whether the config is aridrocks' config. Redo this system in the future
            if (blockstate == DDBlocks.ARIDROCK.get().defaultBlockState() && (world.getBlockState(pos).is(DDBlocks.DUSKROCK.get()) || posBelow.is(DDBlocks.DUSKROCK.get()))) continue;

            if (blockstate == DDBlocks.ARIDROCK.get().defaultBlockState() && !(posBelow.is(Blocks.DEEPSLATE) || posBelow.is(Blocks.TUFF))) {
                belowState = DDBlocks.ARIDROCK.get().defaultBlockState();
            }

            if (posBelow.is(Blocks.TUFF) || posBelow.is(Blocks.DEEPSLATE)) {
                world.setBlock(pos.below(), belowState, 2);
                world.setBlock(pos, blockstate, 2);
                if (config.xzReplace) {
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        if (world.getBlockState(pos.relative(direction)).is(Blocks.DEEPSLATE) && world.getBlockState(pos.relative(direction).above()).canBeReplaced()) {
                            world.setBlock(pos.relative(direction), DDBlocks.ARID_DEEPSLATE.get().defaultBlockState(), 2);
                        } else if (world.getBlockState(pos.below().relative(direction)).is(Blocks.DEEPSLATE) && world.getBlockState(pos.relative(direction)).canBeReplaced()) {
                            world.setBlock(pos.below().relative(direction), DDBlocks.ARIDROCK.get().defaultBlockState(), 2);
                        }
                    }
                }
            }
            else if (posBelow.is(BlockTags.BASE_STONE_OVERWORLD)) {
                belowState = belowState.is(DDBlocks.ARID_DEEPSLATE.get()) ? DDBlocks.ARIDROCK.get().defaultBlockState() : belowState;
                world.setBlock(pos.below(), belowState, 2);
                world.setBlock(pos, blockstate, 2);
            }
        }

        return true;
    }

}
