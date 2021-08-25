package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.common.blocks.SpeleothemBlock;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.api.DarkerDepthsCompat;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Random;

public class SpeleothemFeature extends Feature<SpeleothemConfig> {

    public SpeleothemFeature(Codec<SpeleothemConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, SpeleothemConfig config) {
        if (world.isAirBlock(pos.down())) {
            return false;
        } else {
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            Block replaceState = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark", "stone_speleothem"));
            for (int i = 0; i < 60; i++) {
                int size = rand.nextInt(3) == 0 ? 2 : 3;
                mutable.setAndOffset(pos, rand.nextInt(13) - 6, rand.nextInt(7) - 6, rand.nextInt(13) - 6);
                if (world.isAirBlock(mutable)) {
                        if (world.getBlockState(mutable.down()) == config.bottomState) {
                            if (config.direction == Direction.UP) {
                                this.generateStalagmite(size, world, mutable, config.speleothem_type);
                            }
                        }
                    else {
                        if (world.getBlockState(mutable.up()) == config.bottomState) {
                            this.generateStalactite(size, world, mutable, config.speleothem_type);
                        }
                    }
                }
            }
            return true;
        }
    }

    private void generateStalagmite(int size, IWorld world, BlockPos pos, BlockState state) {
        if (size == 2) {
            this.setBlockState(world, pos, state.with(SpeleothemBlock.SIZE, SpeleothemBlock.SpeleothemSize.MEDIUM));
            this.setBlockState(world, pos.up(), state.with(SpeleothemBlock.SIZE, SpeleothemBlock.SpeleothemSize.SMALL));
        } else {
            this.setBlockState(world, pos, state.with(SpeleothemBlock.SIZE, SpeleothemBlock.SpeleothemSize.BIG));
            this.setBlockState(world, pos.up(), state.with(SpeleothemBlock.SIZE, SpeleothemBlock.SpeleothemSize.MEDIUM));
            this.setBlockState(world, pos.up(2), state.with(SpeleothemBlock.SIZE, SpeleothemBlock.SpeleothemSize.SMALL));
        }
    }

    private void generateStalactite(int size, IWorld world, BlockPos pos, BlockState state) {
        if (size == 2) {
            this.setBlockState(world, pos, state.with(SpeleothemBlock.SIZE, SpeleothemBlock.SpeleothemSize.MEDIUM));
            this.setBlockState(world, pos.down(), state.with(SpeleothemBlock.SIZE, SpeleothemBlock.SpeleothemSize.SMALL));
        } else {
            this.setBlockState(world, pos, state.with(SpeleothemBlock.SIZE, SpeleothemBlock.SpeleothemSize.BIG));
            this.setBlockState(world, pos.down(), state.with(SpeleothemBlock.SIZE, SpeleothemBlock.SpeleothemSize.MEDIUM));
            this.setBlockState(world, pos.down(2), state.with(SpeleothemBlock.SIZE, SpeleothemBlock.SpeleothemSize.SMALL));
        }
    }
}
