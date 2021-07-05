package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.util.helpers.BlockPosHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

//<>

public class VegetationPatchFeature extends Feature<VegetationPatchConfig> {
    public VegetationPatchFeature(Codec<VegetationPatchConfig> config) {
        super(config);
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, VegetationPatchConfig config) {
        Predicate<BlockState> replaceableTags = getReplaceablePredicate(config);
        int xRadius = config.xzRadius.get(rand) + 1;
        int zRadius = config.xzRadius.get(rand) + 1;
        Set<BlockPos> groundPatch = this.placeGroundPatch(worldIn, config, rand, pos, replaceableTags, xRadius, zRadius);
        this.generateVegetation(generator, worldIn, config, rand, groundPatch, xRadius, zRadius);
        return !groundPatch.isEmpty();
    }

    protected Set<BlockPos> placeGroundPatch(IWorld worldIn, VegetationPatchConfig configIn, Random rand, BlockPos pos, Predicate<BlockState> statePredicate, int xRadius, int zRadius) {
        BlockPos.Mutable mutable = pos.toMutable();
        BlockPos.Mutable mutated = mutable.toMutable();
        Direction surfaceDirection = configIn.surface.getDirection();
        Direction oppositeSurface = surfaceDirection.getOpposite();
        Set<BlockPos> positions = new HashSet<>();

        for(int x = -xRadius; x <= xRadius; ++x) {
            boolean inXRange = x == -xRadius || x == xRadius;

            for(int z = -zRadius; z <= zRadius; ++z) {
                boolean inZRange = z == -zRadius || z == zRadius;
                boolean inAnyRange = inXRange || inZRange;
                boolean inTotalRange = inXRange && inZRange;
                boolean inSingleRange = inAnyRange && !inTotalRange;
                if (!inTotalRange && (!inSingleRange || configIn.extraEdgeColumnChance != 0.0F && rand.nextFloat() <= configIn.extraEdgeColumnChance)) {
                    BlockPosHelper.setAndOffset(mutable, pos, x, 0, z);

                    int y;
                    for(y = 0; worldIn.hasBlockState(mutable, BlockState::isAir) && y < configIn.verticalRange; ++y) {
                        mutable.move(surfaceDirection);
                    }

                    for(y = 0; worldIn.hasBlockState(mutable, (state) -> {
                        return !state.isAir();
                    }) && y < configIn.verticalRange; ++y) {
                        mutable.move(oppositeSurface);
                    }

                    BlockPosHelper.setAndOffset(mutated, mutable, configIn.surface.getDirection());
                    BlockState state = worldIn.getBlockState(mutated);
                    if (worldIn.isAirBlock(mutable) && state.isSolidSide(worldIn, mutated, configIn.surface.getDirection().getOpposite())) {
                        int depth = configIn.depth.get(rand) + (configIn.extraBottomBlockChance > 0.0F && rand.nextFloat() < configIn.extraBottomBlockChance ? 1 : 0);
                        BlockPos immutable = mutated.toImmutable();
                        boolean canPlaceOnGround = this.placeGround(worldIn, configIn, statePredicate, rand, mutated, depth);
                        if (canPlaceOnGround) {
                            positions.add(immutable);
                        }
                    }
                }
            }
        }

        return positions;
    }

    protected void generateVegetation(ChunkGenerator generator, ISeedReader worldIn, VegetationPatchConfig configIn, Random rand, Set<BlockPos> positions, int x, int z) {
        for (BlockPos pos : positions) {
            if (configIn.vegetationChance > 0.0F && rand.nextFloat() < configIn.vegetationChance) {
                this.placeVegetation(worldIn, configIn, generator, rand, pos);
            }
        }
    }

    protected boolean placeVegetation(ISeedReader worldIn, VegetationPatchConfig configIn, ChunkGenerator generator, Random rand, BlockPos pos) {
        return configIn.vegetationFeature.get().generate(worldIn, generator, rand, pos.offset(configIn.surface.getDirection().getOpposite()));
    }

    protected boolean placeGround(IWorld worldIn, VegetationPatchConfig configIn, Predicate<BlockState> statePredicate, Random rand, BlockPos.Mutable pos, int height) {
        for (int y = 0; y < height; y++) {
            if (!statePredicate.test(worldIn.getBlockState(pos))) {
                return y != 0;
            }

            worldIn.setBlockState(pos, configIn.groundState.getBlockState(rand, pos), 2);
            pos.move(configIn.surface.getDirection());
        }

        return true;
    }

    private static Predicate<BlockState> getReplaceablePredicate(VegetationPatchConfig configIn) {
        ITag<Block> tag = BlockTags.getCollection().get(configIn.replaceable);
        return tag == null ? (block) -> true : (block) -> block.isIn(tag);
    }
}