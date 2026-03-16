package com.naterbobber.darkerdepths.worldgen.structures;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import org.slf4j.Logger;

public class DDMineshaftPieces {
    static final Logger LOGGER = LogUtils.getLogger();
    private static final int DEFAULT_SHAFT_WIDTH = 3;
    private static final int DEFAULT_SHAFT_HEIGHT = 3;
    private static final int DEFAULT_SHAFT_LENGTH = 5;
    private static final int MAX_PILLAR_HEIGHT = 20;
    private static final int MAX_CHAIN_HEIGHT = 50;
    private static final int MAX_DEPTH = 8;
    public static final int MAGIC_START_Y = 50;

    public DDMineshaftPieces() {
    }

    private static MineShaftPiece createRandomShaftPiece(StructurePieceAccessor pieces, RandomSource random, int x, int y, int z, @Nullable Direction orientation, int genDepth, DDMineshaftStructure.Type type) {
        int i = random.nextInt(100);
        if (i >= 80) {
            BoundingBox boundingbox = DDMineshaftPieces.MineShaftCrossing.findCrossing(pieces, random, x, y, z, orientation);
            if (boundingbox != null) {
                return new MineShaftCrossing(genDepth, boundingbox, orientation, type);
            }
        } else if (i >= 70) {
            BoundingBox boundingbox1 = DDMineshaftPieces.MineShaftStairs.findStairs(pieces, random, x, y, z, orientation);
            if (boundingbox1 != null) {
                return new MineShaftStairs(genDepth, boundingbox1, orientation, type);
            }
        } else {
            BoundingBox boundingbox2 = DDMineshaftPieces.MineShaftCorridor.findCorridorSize(pieces, random, x, y, z, orientation);
            if (boundingbox2 != null) {
                return new MineShaftCorridor(genDepth, random, boundingbox2, orientation, type);
            }
        }

        return null;
    }

    static MineShaftPiece generateAndAddPiece(StructurePiece piece, StructurePieceAccessor pieces, RandomSource random, int x, int y, int z, Direction direction, int genDepth) {
        if (genDepth > 8) {
            return null;
        } else if (Math.abs(x - piece.getBoundingBox().minX()) <= 80 && Math.abs(z - piece.getBoundingBox().minZ()) <= 80) {
            DDMineshaftStructure.Type DDmineshaftstructure$type = ((MineShaftPiece)piece).type;
            MineShaftPiece DDmineshaftpieces$mineshaftpiece = createRandomShaftPiece(pieces, random, x, y, z, direction, genDepth + 1, DDmineshaftstructure$type);
            if (DDmineshaftpieces$mineshaftpiece != null) {
                pieces.addPiece(DDmineshaftpieces$mineshaftpiece);
                DDmineshaftpieces$mineshaftpiece.addChildren(piece, pieces, random);
            }

            return DDmineshaftpieces$mineshaftpiece;
        } else {
            return null;
        }
    }

    public static class MineShaftCorridor extends MineShaftPiece {
        private final boolean hasRails;
        private final boolean spiderCorridor;
        private boolean hasPlacedSpider;
        private final int numSections;

        public MineShaftCorridor(CompoundTag tag) {
            super(StructurePieceType.MINE_SHAFT_CORRIDOR, tag);
            this.hasRails = tag.getBoolean("hr");
            this.spiderCorridor = tag.getBoolean("sc");
            this.hasPlacedSpider = tag.getBoolean("hps");
            this.numSections = tag.getInt("Num");
        }

        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            tag.putBoolean("hr", this.hasRails);
            tag.putBoolean("sc", this.spiderCorridor);
            tag.putBoolean("hps", this.hasPlacedSpider);
            tag.putInt("Num", this.numSections);
        }

        public MineShaftCorridor(int genDepth, RandomSource random, BoundingBox boundingBox, Direction orientation, DDMineshaftStructure.Type type) {
            super(StructurePieceType.MINE_SHAFT_CORRIDOR, genDepth, type, boundingBox);
            this.setOrientation(orientation);
            this.hasRails = random.nextInt(3) == 0;
            this.spiderCorridor = !this.hasRails && random.nextInt(23) == 0;
            if (this.getOrientation().getAxis() == Axis.Z) {
                this.numSections = boundingBox.getZSpan() / 5;
            } else {
                this.numSections = boundingBox.getXSpan() / 5;
            }

        }

        @Nullable
        public static BoundingBox findCorridorSize(StructurePieceAccessor pieces, RandomSource random, int x, int y, int z, Direction direction) {
            for(int i = random.nextInt(3) + 2; i > 0; --i) {
                int j = i * 5;
                BoundingBox var10000;
                switch (direction) {
                    case SOUTH -> var10000 = new BoundingBox(0, 0, 0, 2, 2, j - 1);
                    case WEST -> var10000 = new BoundingBox(-(j - 1), 0, 0, 0, 2, 2);
                    case EAST -> var10000 = new BoundingBox(0, 0, 0, j - 1, 2, 2);
                    default -> var10000 = new BoundingBox(0, 0, -(j - 1), 2, 2, 0);
                }

                BoundingBox $$11 = var10000;
                $$11.move(x, y, z);
                if (pieces.findCollisionPiece($$11) == null) {
                    return $$11;
                }
            }

            return null;
        }

        public void addChildren(StructurePiece piece, StructurePieceAccessor pieces, RandomSource random) {
            int i = this.getGenDepth();
            int j = random.nextInt(4);
            Direction direction = this.getOrientation();
            if (direction != null) {
                switch (direction) {
                    case SOUTH:
                        if (j <= 1) {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX(), this.boundingBox.minY() - 1 + random.nextInt(3), this.boundingBox.maxZ() + 1, direction, i);
                        } else if (j == 2) {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() - 1, this.boundingBox.minY() - 1 + random.nextInt(3), this.boundingBox.maxZ() - 3, Direction.WEST, i);
                        } else {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.maxX() + 1, this.boundingBox.minY() - 1 + random.nextInt(3), this.boundingBox.maxZ() - 3, Direction.EAST, i);
                        }
                        break;
                    case WEST:
                        if (j <= 1) {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() - 1, this.boundingBox.minY() - 1 + random.nextInt(3), this.boundingBox.minZ(), direction, i);
                        } else if (j == 2) {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX(), this.boundingBox.minY() - 1 + random.nextInt(3), this.boundingBox.minZ() - 1, Direction.NORTH, i);
                        } else {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX(), this.boundingBox.minY() - 1 + random.nextInt(3), this.boundingBox.maxZ() + 1, Direction.SOUTH, i);
                        }
                        break;
                    case EAST:
                        if (j <= 1) {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.maxX() + 1, this.boundingBox.minY() - 1 + random.nextInt(3), this.boundingBox.minZ(), direction, i);
                        } else if (j == 2) {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.maxX() - 3, this.boundingBox.minY() - 1 + random.nextInt(3), this.boundingBox.minZ() - 1, Direction.NORTH, i);
                        } else {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.maxX() - 3, this.boundingBox.minY() - 1 + random.nextInt(3), this.boundingBox.maxZ() + 1, Direction.SOUTH, i);
                        }
                        break;
                    case NORTH:
                    default:
                        if (j <= 1) {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX(), this.boundingBox.minY() - 1 + random.nextInt(3), this.boundingBox.minZ() - 1, direction, i);
                        } else if (j == 2) {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() - 1, this.boundingBox.minY() - 1 + random.nextInt(3), this.boundingBox.minZ(), Direction.WEST, i);
                        } else {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.maxX() + 1, this.boundingBox.minY() - 1 + random.nextInt(3), this.boundingBox.minZ(), Direction.EAST, i);
                        }
                }
            }

            if (i < 8) {
                if (direction != Direction.NORTH && direction != Direction.SOUTH) {
                    for(int i1 = this.boundingBox.minX() + 3; i1 + 3 <= this.boundingBox.maxX(); i1 += 5) {
                        int j1 = random.nextInt(5);
                        if (j1 == 0) {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, i1, this.boundingBox.minY(), this.boundingBox.minZ() - 1, Direction.NORTH, i + 1);
                        } else if (j1 == 1) {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, i1, this.boundingBox.minY(), this.boundingBox.maxZ() + 1, Direction.SOUTH, i + 1);
                        }
                    }
                } else {
                    for(int k = this.boundingBox.minZ() + 3; k + 3 <= this.boundingBox.maxZ(); k += 5) {
                        int l = random.nextInt(5);
                        if (l == 0) {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() - 1, this.boundingBox.minY(), k, Direction.WEST, i + 1);
                        } else if (l == 1) {
                            DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.maxX() + 1, this.boundingBox.minY(), k, Direction.EAST, i + 1);
                        }
                    }
                }
            }

        }

        protected boolean createChest(WorldGenLevel level, BoundingBox box, RandomSource random, int x, int y, int z, ResourceKey<LootTable> lootTable) {
            BlockPos blockpos = this.getWorldPos(x, y, z);
            if (box.isInside(blockpos) && level.getBlockState(blockpos).isAir() && !level.getBlockState(blockpos.below()).isAir()) {
                BlockState blockstate = (BlockState)Blocks.RAIL.defaultBlockState().setValue(RailBlock.SHAPE, random.nextBoolean() ? RailShape.NORTH_SOUTH : RailShape.EAST_WEST);
                this.placeBlock(level, blockstate, x, y, z, box);
                MinecartChest minecartchest = new MinecartChest(level.getLevel(), (double)blockpos.getX() + (double)0.5F, (double)blockpos.getY() + (double)0.5F, (double)blockpos.getZ() + (double)0.5F);
                minecartchest.setLootTable(lootTable, random.nextLong());
                level.addFreshEntity(minecartchest);
                return true;
            } else {
                return false;
            }
        }

        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
            if (!this.isInInvalidLocation(level, box)) {
                int i = 0;
                int j = 2;
                int k = 0;
                int l = 2;
                int i1 = this.numSections * 5 - 1;
                BlockState blockstate = this.type.getPlanksState();
                this.generateBox(level, box, 0, 0, 0, 2, 1, i1, CAVE_AIR, CAVE_AIR, false);
                this.generateMaybeBox(level, box, random, 0.8F, 0, 2, 0, 2, 2, i1, CAVE_AIR, CAVE_AIR, false, false);
                if (this.spiderCorridor) {
                    this.generateMaybeBox(level, box, random, 0.6F, 0, 0, 0, 2, 1, i1, Blocks.COBWEB.defaultBlockState(), CAVE_AIR, false, true);
                }

                for(int j1 = 0; j1 < this.numSections; ++j1) {
                    int k1 = 2 + j1 * 5;
                    this.placeSupport(level, box, 0, 0, k1, 2, 2, random);
                    this.maybePlaceCobWeb(level, box, random, 0.1F, 0, 2, k1 - 1);
                    this.maybePlaceCobWeb(level, box, random, 0.1F, 2, 2, k1 - 1);
                    this.maybePlaceCobWeb(level, box, random, 0.1F, 0, 2, k1 + 1);
                    this.maybePlaceCobWeb(level, box, random, 0.1F, 2, 2, k1 + 1);
                    this.maybePlaceCobWeb(level, box, random, 0.05F, 0, 2, k1 - 2);
                    this.maybePlaceCobWeb(level, box, random, 0.05F, 2, 2, k1 - 2);
                    this.maybePlaceCobWeb(level, box, random, 0.05F, 0, 2, k1 + 2);
                    this.maybePlaceCobWeb(level, box, random, 0.05F, 2, 2, k1 + 2);
                    if (random.nextInt(100) == 0) {
                        this.createChest(level, box, random, 2, 0, k1 - 1, BuiltInLootTables.ABANDONED_MINESHAFT);
                    }

                    if (random.nextInt(100) == 0) {
                        this.createChest(level, box, random, 0, 0, k1 + 1, BuiltInLootTables.ABANDONED_MINESHAFT);
                    }

                    if (this.spiderCorridor && !this.hasPlacedSpider) {
                        int l1 = 1;
                        int i2 = k1 - 1 + random.nextInt(3);
                        BlockPos blockpos = this.getWorldPos(1, 0, i2);
                        if (box.isInside(blockpos) && this.isInterior(level, 1, 0, i2, box)) {
                            this.hasPlacedSpider = true;
                            level.setBlock(blockpos, Blocks.SPAWNER.defaultBlockState(), 2);
                            BlockEntity var20 = level.getBlockEntity(blockpos);
                            if (var20 instanceof SpawnerBlockEntity) {
                                SpawnerBlockEntity spawnerblockentity = (SpawnerBlockEntity)var20;
                                spawnerblockentity.setEntityId(EntityType.CAVE_SPIDER, random);
                            }
                        }
                    }
                }

                for(int j2 = 0; j2 <= 2; ++j2) {
                    for(int l2 = 0; l2 <= i1; ++l2) {
                        this.setPlanksBlock(level, box, blockstate, j2, -1, l2);
                    }
                }

                int k2 = 2;
                this.placeDoubleLowerOrUpperSupport(level, box, 0, -1, 2);
                if (this.numSections > 1) {
                    int i3 = i1 - 2;
                    this.placeDoubleLowerOrUpperSupport(level, box, 0, -1, i3);
                }

                if (this.hasRails) {
                    BlockState blockstate1 = (BlockState)Blocks.RAIL.defaultBlockState().setValue(RailBlock.SHAPE, RailShape.NORTH_SOUTH);

                    for(int j3 = 0; j3 <= i1; ++j3) {
                        BlockState blockstate2 = this.getBlock(level, 1, -1, j3, box);
                        if (!blockstate2.isAir() && blockstate2.isSolidRender(level, this.getWorldPos(1, -1, j3))) {
                            float f = this.isInterior(level, 1, 0, j3, box) ? 0.7F : 0.9F;
                            this.maybeGenerateBlock(level, box, random, f, 1, 0, j3, blockstate1);
                        }
                    }
                }
            }

        }

        private void placeDoubleLowerOrUpperSupport(WorldGenLevel level, BoundingBox box, int x, int y, int z) {
            BlockState blockstate = this.type.getWoodState();
            BlockState blockstate1 = this.type.getPlanksState();
            if (this.getBlock(level, x, y, z, box).is(blockstate1.getBlock())) {
                this.fillPillarDownOrChainUp(level, blockstate, x, y, z, box);
            }

            if (this.getBlock(level, x + 2, y, z, box).is(blockstate1.getBlock())) {
                this.fillPillarDownOrChainUp(level, blockstate, x + 2, y, z, box);
            }

        }

        protected void fillColumnDown(WorldGenLevel level, BlockState state, int x, int y, int z, BoundingBox box) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = this.getWorldPos(x, y, z);
            if (box.isInside(blockpos$mutableblockpos)) {
                int i = blockpos$mutableblockpos.getY();

                while(this.isReplaceableByStructures(level.getBlockState(blockpos$mutableblockpos)) && blockpos$mutableblockpos.getY() > level.getMinBuildHeight() + 1) {
                    blockpos$mutableblockpos.move(Direction.DOWN);
                }

                if (this.canPlaceColumnOnTopOf(level, blockpos$mutableblockpos, level.getBlockState(blockpos$mutableblockpos))) {
                    while(blockpos$mutableblockpos.getY() < i) {
                        blockpos$mutableblockpos.move(Direction.UP);
                        level.setBlock(blockpos$mutableblockpos, state, 2);
                    }
                }
            }

        }

        protected void fillPillarDownOrChainUp(WorldGenLevel level, BlockState state, int x, int y, int z, BoundingBox box) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = this.getWorldPos(x, y, z);
            if (box.isInside(blockpos$mutableblockpos)) {
                int i = blockpos$mutableblockpos.getY();
                int j = 1;
                boolean flag = true;

                for(boolean flag1 = true; flag || flag1; ++j) {
                    if (flag) {
                        blockpos$mutableblockpos.setY(i - j);
                        BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
                        boolean flag2 = this.isReplaceableByStructures(blockstate) && !blockstate.is(Blocks.LAVA);
                        if (!flag2 && this.canPlaceColumnOnTopOf(level, blockpos$mutableblockpos, blockstate)) {
                            fillColumnBetween(level, state, blockpos$mutableblockpos, i - j + 1, i);
                            return;
                        }

                        flag = j <= 20 && flag2 && blockpos$mutableblockpos.getY() > level.getMinBuildHeight() + 1;
                    }

                    if (flag1) {
                        blockpos$mutableblockpos.setY(i + j);
                        BlockState blockstate1 = level.getBlockState(blockpos$mutableblockpos);
                        boolean flag3 = this.isReplaceableByStructures(blockstate1);
                        if (!flag3 && this.canHangChainBelow(level, blockpos$mutableblockpos, blockstate1)) {
                            level.setBlock(blockpos$mutableblockpos.setY(i + 1), this.type.getFenceState(), 2);
                            fillColumnBetween(level, Blocks.CHAIN.defaultBlockState(), blockpos$mutableblockpos, i + 2, i + j);
                            return;
                        }

                        flag1 = j <= 50 && flag3 && blockpos$mutableblockpos.getY() < level.getMaxBuildHeight() - 1;
                    }
                }
            }

        }

        private static void fillColumnBetween(WorldGenLevel level, BlockState state, BlockPos.MutableBlockPos pos, int minY, int maxY) {
            for(int i = minY; i < maxY; ++i) {
                level.setBlock(pos.setY(i), state, 2);
            }

        }

        private boolean canPlaceColumnOnTopOf(LevelReader level, BlockPos pos, BlockState state) {
            return state.isFaceSturdy(level, pos, Direction.UP);
        }

        private boolean canHangChainBelow(LevelReader level, BlockPos pos, BlockState state) {
            return Block.canSupportCenter(level, pos, Direction.DOWN) && !(state.getBlock() instanceof FallingBlock);
        }

        private void placeSupport(WorldGenLevel level, BoundingBox box, int minX, int minY, int z, int maxY, int maxX, RandomSource random) {
            if (this.isSupportingBox(level, box, minX, maxX, maxY, z)) {
                BlockState blockstate = this.type.getPlanksState();
                BlockState blockstate1 = this.type.getFenceState();
                this.generateBox(level, box, minX, minY, z, minX, maxY - 1, z, (BlockState)blockstate1.setValue(FenceBlock.WEST, true), CAVE_AIR, false);
                this.generateBox(level, box, maxX, minY, z, maxX, maxY - 1, z, (BlockState)blockstate1.setValue(FenceBlock.EAST, true), CAVE_AIR, false);
                if (random.nextInt(4) == 0) {
                    this.generateBox(level, box, minX, maxY, z, minX, maxY, z, blockstate, CAVE_AIR, false);
                    this.generateBox(level, box, maxX, maxY, z, maxX, maxY, z, blockstate, CAVE_AIR, false);
                } else {
                    this.generateBox(level, box, minX, maxY, z, maxX, maxY, z, blockstate, CAVE_AIR, false);
                    this.maybeGenerateBlock(level, box, random, 0.05F, minX + 1, maxY, z - 1, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.SOUTH));
                    this.maybeGenerateBlock(level, box, random, 0.05F, minX + 1, maxY, z + 1, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.NORTH));
                }
            }

        }

        private void maybePlaceCobWeb(WorldGenLevel level, BoundingBox box, RandomSource random, float chance, int x, int y, int z) {
            if (this.isInterior(level, x, y, z, box) && random.nextFloat() < chance && this.hasSturdyNeighbours(level, box, x, y, z, 2)) {
                this.placeBlock(level, Blocks.COBWEB.defaultBlockState(), x, y, z, box);
            }

        }

        private boolean hasSturdyNeighbours(WorldGenLevel level, BoundingBox box, int x, int y, int z, int required) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = this.getWorldPos(x, y, z);
            int i = 0;

            for(Direction direction : Direction.values()) {
                blockpos$mutableblockpos.move(direction);
                if (box.isInside(blockpos$mutableblockpos) && level.getBlockState(blockpos$mutableblockpos).isFaceSturdy(level, blockpos$mutableblockpos, direction.getOpposite())) {
                    ++i;
                    if (i >= required) {
                        return true;
                    }
                }

                blockpos$mutableblockpos.move(direction.getOpposite());
            }

            return false;
        }
    }

    public static class MineShaftCrossing extends MineShaftPiece {
        private final Direction direction;
        private final boolean isTwoFloored;

        public MineShaftCrossing(CompoundTag tag) {
            super(StructurePieceType.MINE_SHAFT_CROSSING, tag);
            this.isTwoFloored = tag.getBoolean("tf");
            this.direction = Direction.from2DDataValue(tag.getInt("D"));
        }

        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            tag.putBoolean("tf", this.isTwoFloored);
            tag.putInt("D", this.direction.get2DDataValue());
        }

        public MineShaftCrossing(int genDepth, BoundingBox boundingBox, @Nullable Direction direction, DDMineshaftStructure.Type type) {
            super(StructurePieceType.MINE_SHAFT_CROSSING, genDepth, type, boundingBox);
            this.direction = direction;
            this.isTwoFloored = boundingBox.getYSpan() > 3;
        }

        @Nullable
        public static BoundingBox findCrossing(StructurePieceAccessor pieces, RandomSource random, int x, int y, int z, Direction direction) {
            int i;
            if (random.nextInt(4) == 0) {
                i = 6;
            } else {
                i = 2;
            }

            BoundingBox var10000;
            switch (direction) {
                case SOUTH -> var10000 = new BoundingBox(-1, 0, 0, 3, i, 4);
                case WEST -> var10000 = new BoundingBox(-4, 0, -1, 0, i, 3);
                case EAST -> var10000 = new BoundingBox(0, 0, -1, 4, i, 3);
                default -> var10000 = new BoundingBox(-1, 0, -4, 3, i, 0);
            }

            BoundingBox $$11 = var10000;
            $$11.move(x, y, z);
            return pieces.findCollisionPiece($$11) != null ? null : $$11;
        }

        public void addChildren(StructurePiece piece, StructurePieceAccessor pieces, RandomSource random) {
            int i = this.getGenDepth();
            switch (this.direction) {
                case SOUTH:
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.maxZ() + 1, Direction.SOUTH, i);
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() - 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, Direction.WEST, i);
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.maxX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, Direction.EAST, i);
                    break;
                case WEST:
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() - 1, Direction.NORTH, i);
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.maxZ() + 1, Direction.SOUTH, i);
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() - 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, Direction.WEST, i);
                    break;
                case EAST:
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() - 1, Direction.NORTH, i);
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.maxZ() + 1, Direction.SOUTH, i);
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.maxX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, Direction.EAST, i);
                    break;
                case NORTH:
                default:
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() - 1, Direction.NORTH, i);
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() - 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, Direction.WEST, i);
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.maxX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, Direction.EAST, i);
            }

            if (this.isTwoFloored) {
                if (random.nextBoolean()) {
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() + 1, this.boundingBox.minY() + 3 + 1, this.boundingBox.minZ() - 1, Direction.NORTH, i);
                }

                if (random.nextBoolean()) {
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() - 1, this.boundingBox.minY() + 3 + 1, this.boundingBox.minZ() + 1, Direction.WEST, i);
                }

                if (random.nextBoolean()) {
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.maxX() + 1, this.boundingBox.minY() + 3 + 1, this.boundingBox.minZ() + 1, Direction.EAST, i);
                }

                if (random.nextBoolean()) {
                    DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() + 1, this.boundingBox.minY() + 3 + 1, this.boundingBox.maxZ() + 1, Direction.SOUTH, i);
                }
            }

        }

        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
            if (!this.isInInvalidLocation(level, box)) {
                BlockState blockstate = this.type.getPlanksState();
                if (this.isTwoFloored) {
                    this.generateBox(level, box, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.minZ(), this.boundingBox.maxX() - 1, this.boundingBox.minY() + 3 - 1, this.boundingBox.maxZ(), CAVE_AIR, CAVE_AIR, false);
                    this.generateBox(level, box, this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ() + 1, this.boundingBox.maxX(), this.boundingBox.minY() + 3 - 1, this.boundingBox.maxZ() - 1, CAVE_AIR, CAVE_AIR, false);
                    this.generateBox(level, box, this.boundingBox.minX() + 1, this.boundingBox.maxY() - 2, this.boundingBox.minZ(), this.boundingBox.maxX() - 1, this.boundingBox.maxY(), this.boundingBox.maxZ(), CAVE_AIR, CAVE_AIR, false);
                    this.generateBox(level, box, this.boundingBox.minX(), this.boundingBox.maxY() - 2, this.boundingBox.minZ() + 1, this.boundingBox.maxX(), this.boundingBox.maxY(), this.boundingBox.maxZ() - 1, CAVE_AIR, CAVE_AIR, false);
                    this.generateBox(level, box, this.boundingBox.minX() + 1, this.boundingBox.minY() + 3, this.boundingBox.minZ() + 1, this.boundingBox.maxX() - 1, this.boundingBox.minY() + 3, this.boundingBox.maxZ() - 1, CAVE_AIR, CAVE_AIR, false);
                } else {
                    this.generateBox(level, box, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.minZ(), this.boundingBox.maxX() - 1, this.boundingBox.maxY(), this.boundingBox.maxZ(), CAVE_AIR, CAVE_AIR, false);
                    this.generateBox(level, box, this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ() + 1, this.boundingBox.maxX(), this.boundingBox.maxY(), this.boundingBox.maxZ() - 1, CAVE_AIR, CAVE_AIR, false);
                }

                this.placeSupportPillar(level, box, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, this.boundingBox.maxY());
                this.placeSupportPillar(level, box, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.maxZ() - 1, this.boundingBox.maxY());
                this.placeSupportPillar(level, box, this.boundingBox.maxX() - 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, this.boundingBox.maxY());
                this.placeSupportPillar(level, box, this.boundingBox.maxX() - 1, this.boundingBox.minY(), this.boundingBox.maxZ() - 1, this.boundingBox.maxY());
                int i = this.boundingBox.minY() - 1;

                for(int j = this.boundingBox.minX(); j <= this.boundingBox.maxX(); ++j) {
                    for(int k = this.boundingBox.minZ(); k <= this.boundingBox.maxZ(); ++k) {
                        this.setPlanksBlock(level, box, blockstate, j, i, k);
                    }
                }
            }

        }

        private void placeSupportPillar(WorldGenLevel level, BoundingBox box, int x, int y, int z, int maxY) {
            if (!this.getBlock(level, x, maxY + 1, z, box).isAir()) {
                this.generateBox(level, box, x, y, z, x, maxY, z, this.type.getPlanksState(), CAVE_AIR, false);
            }

        }
    }

    abstract static class MineShaftPiece extends StructurePiece {
        protected DDMineshaftStructure.Type type;

        public MineShaftPiece(StructurePieceType structurePieceType, int genDepth, DDMineshaftStructure.Type type, BoundingBox boundingBox) {
            super(structurePieceType, genDepth, boundingBox);
            this.type = type;
        }

        public MineShaftPiece(StructurePieceType type, CompoundTag tag) {
            super(type, tag);
            this.type = DDMineshaftStructure.Type.byId(tag.getInt("MST"));
        }

        protected boolean canBeReplaced(LevelReader level, int x, int y, int z, BoundingBox box) {
            BlockState blockstate = this.getBlock(level, x, y, z, box);
            return !blockstate.is(this.type.getPlanksState().getBlock()) && !blockstate.is(this.type.getWoodState().getBlock()) && !blockstate.is(this.type.getFenceState().getBlock()) && !blockstate.is(Blocks.CHAIN);
        }

        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            tag.putInt("MST", this.type.ordinal());
        }

        protected boolean isSupportingBox(BlockGetter level, BoundingBox box, int xStart, int xEnd, int y, int z) {
            for(int i = xStart; i <= xEnd; ++i) {
                if (this.getBlock(level, i, y + 1, z, box).isAir()) {
                    return false;
                }
            }

            return true;
        }

        protected boolean isInInvalidLocation(LevelAccessor level, BoundingBox boundingBox) {
            int i = Math.max(this.boundingBox.minX() - 1, boundingBox.minX());
            int j = Math.max(this.boundingBox.minY() - 1, boundingBox.minY());
            int k = Math.max(this.boundingBox.minZ() - 1, boundingBox.minZ());
            int l = Math.min(this.boundingBox.maxX() + 1, boundingBox.maxX());
            int i1 = Math.min(this.boundingBox.maxY() + 1, boundingBox.maxY());
            int j1 = Math.min(this.boundingBox.maxZ() + 1, boundingBox.maxZ());
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos((i + l) / 2, (j + i1) / 2, (k + j1) / 2);
            if (level.getBiome(blockpos$mutableblockpos).is(BiomeTags.MINESHAFT_BLOCKING)) {
                return true;
            } else {
                for(int k1 = i; k1 <= l; ++k1) {
                    for(int l1 = k; l1 <= j1; ++l1) {
                        if (level.getBlockState(blockpos$mutableblockpos.set(k1, j, l1)).liquid()) {
                            return true;
                        }

                        if (level.getBlockState(blockpos$mutableblockpos.set(k1, i1, l1)).liquid()) {
                            return true;
                        }
                    }
                }

                for(int i2 = i; i2 <= l; ++i2) {
                    for(int k2 = j; k2 <= i1; ++k2) {
                        if (level.getBlockState(blockpos$mutableblockpos.set(i2, k2, k)).liquid()) {
                            return true;
                        }

                        if (level.getBlockState(blockpos$mutableblockpos.set(i2, k2, j1)).liquid()) {
                            return true;
                        }
                    }
                }

                for(int j2 = k; j2 <= j1; ++j2) {
                    for(int l2 = j; l2 <= i1; ++l2) {
                        if (level.getBlockState(blockpos$mutableblockpos.set(i, l2, j2)).liquid()) {
                            return true;
                        }

                        if (level.getBlockState(blockpos$mutableblockpos.set(l, l2, j2)).liquid()) {
                            return true;
                        }
                    }
                }

                return false;
            }
        }

        protected void setPlanksBlock(WorldGenLevel level, BoundingBox box, BlockState plankState, int x, int y, int z) {
            if (this.isInterior(level, x, y, z, box)) {
                BlockPos blockpos = this.getWorldPos(x, y, z);
                BlockState blockstate = level.getBlockState(blockpos);
                if (!blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                    level.setBlock(blockpos, plankState, 2);
                }
            }

        }
    }

    public static class MineShaftRoom extends MineShaftPiece {
        private final List<BoundingBox> childEntranceBoxes = Lists.newLinkedList();

        public MineShaftRoom(int genDepth, RandomSource random, int x, int z, DDMineshaftStructure.Type type) {
            super(StructurePieceType.MINE_SHAFT_ROOM, genDepth, type, new BoundingBox(x, 50, z, x + 7 + random.nextInt(6), 54 + random.nextInt(6), z + 7 + random.nextInt(6)));
            this.type = type;
        }

        public MineShaftRoom(CompoundTag tag) {
            super(StructurePieceType.MINE_SHAFT_ROOM, tag);
            DataResult var10000 = BoundingBox.CODEC.listOf().parse(NbtOps.INSTANCE, tag.getList("Entrances", 11));
            Logger var10001 = DDMineshaftPieces.LOGGER;
            Objects.requireNonNull(var10001);
            Optional var2 = var10000.resultOrPartial();
            List var3 = this.childEntranceBoxes;
            Objects.requireNonNull(var3);
        }

        public void addChildren(StructurePiece piece, StructurePieceAccessor pieces, RandomSource random) {
            int i = this.getGenDepth();
            int k = this.boundingBox.getYSpan() - 3 - 1;
            if (k <= 0) {
                k = 1;
            }

            int j;
            for(j = 0; j < this.boundingBox.getXSpan(); j += 4) {
                j += random.nextInt(this.boundingBox.getXSpan());
                if (j + 3 > this.boundingBox.getXSpan()) {
                    break;
                }

                MineShaftPiece DDmineshaftpieces$mineshaftpiece = DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() + j, this.boundingBox.minY() + random.nextInt(k) + 1, this.boundingBox.minZ() - 1, Direction.NORTH, i);
                if (DDmineshaftpieces$mineshaftpiece != null) {
                    BoundingBox boundingbox = DDmineshaftpieces$mineshaftpiece.getBoundingBox();
                    this.childEntranceBoxes.add(new BoundingBox(boundingbox.minX(), boundingbox.minY(), this.boundingBox.minZ(), boundingbox.maxX(), boundingbox.maxY(), this.boundingBox.minZ() + 1));
                }
            }

            for(j = 0; j < this.boundingBox.getXSpan(); j += 4) {
                j += random.nextInt(this.boundingBox.getXSpan());
                if (j + 3 > this.boundingBox.getXSpan()) {
                    break;
                }

                MineShaftPiece DDmineshaftpieces$mineshaftpiece1 = DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() + j, this.boundingBox.minY() + random.nextInt(k) + 1, this.boundingBox.maxZ() + 1, Direction.SOUTH, i);
                if (DDmineshaftpieces$mineshaftpiece1 != null) {
                    BoundingBox boundingbox1 = DDmineshaftpieces$mineshaftpiece1.getBoundingBox();
                    this.childEntranceBoxes.add(new BoundingBox(boundingbox1.minX(), boundingbox1.minY(), this.boundingBox.maxZ() - 1, boundingbox1.maxX(), boundingbox1.maxY(), this.boundingBox.maxZ()));
                }
            }

            for(j = 0; j < this.boundingBox.getZSpan(); j += 4) {
                j += random.nextInt(this.boundingBox.getZSpan());
                if (j + 3 > this.boundingBox.getZSpan()) {
                    break;
                }

                MineShaftPiece DDmineshaftpieces$mineshaftpiece2 = DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() - 1, this.boundingBox.minY() + random.nextInt(k) + 1, this.boundingBox.minZ() + j, Direction.WEST, i);
                if (DDmineshaftpieces$mineshaftpiece2 != null) {
                    BoundingBox boundingbox2 = DDmineshaftpieces$mineshaftpiece2.getBoundingBox();
                    this.childEntranceBoxes.add(new BoundingBox(this.boundingBox.minX(), boundingbox2.minY(), boundingbox2.minZ(), this.boundingBox.minX() + 1, boundingbox2.maxY(), boundingbox2.maxZ()));
                }
            }

            for(j = 0; j < this.boundingBox.getZSpan(); j += 4) {
                j += random.nextInt(this.boundingBox.getZSpan());
                if (j + 3 > this.boundingBox.getZSpan()) {
                    break;
                }

                StructurePiece structurepiece = DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.maxX() + 1, this.boundingBox.minY() + random.nextInt(k) + 1, this.boundingBox.minZ() + j, Direction.EAST, i);
                if (structurepiece != null) {
                    BoundingBox boundingbox3 = structurepiece.getBoundingBox();
                    this.childEntranceBoxes.add(new BoundingBox(this.boundingBox.maxX() - 1, boundingbox3.minY(), boundingbox3.minZ(), this.boundingBox.maxX(), boundingbox3.maxY(), boundingbox3.maxZ()));
                }
            }

        }

        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
            if (!this.isInInvalidLocation(level, box)) {
                this.generateBox(level, box, this.boundingBox.minX(), this.boundingBox.minY() + 1, this.boundingBox.minZ(), this.boundingBox.maxX(), Math.min(this.boundingBox.minY() + 3, this.boundingBox.maxY()), this.boundingBox.maxZ(), CAVE_AIR, CAVE_AIR, false);

                for(BoundingBox boundingbox : this.childEntranceBoxes) {
                    this.generateBox(level, box, boundingbox.minX(), boundingbox.maxY() - 2, boundingbox.minZ(), boundingbox.maxX(), boundingbox.maxY(), boundingbox.maxZ(), CAVE_AIR, CAVE_AIR, false);
                }

                this.generateUpperHalfSphere(level, box, this.boundingBox.minX(), this.boundingBox.minY() + 4, this.boundingBox.minZ(), this.boundingBox.maxX(), this.boundingBox.maxY(), this.boundingBox.maxZ(), CAVE_AIR, false);
            }

        }

        public void move(int x, int y, int z) {
            super.move(x, y, z);

            for(BoundingBox boundingbox : this.childEntranceBoxes) {
                boundingbox.move(x, y, z);
            }

        }

        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            DataResult var10000 = BoundingBox.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.childEntranceBoxes);
            Logger var10001 = DDMineshaftPieces.LOGGER;
            Objects.requireNonNull(var10001);
            var10000.resultOrPartial().ifPresent((p_227930_) -> tag.put("Entrances", (Tag) p_227930_));
        }
    }

    public static class MineShaftStairs extends MineShaftPiece {
        public MineShaftStairs(int genDepth, BoundingBox boundingBox, Direction orientation, DDMineshaftStructure.Type type) {
            super(StructurePieceType.MINE_SHAFT_STAIRS, genDepth, type, boundingBox);
            this.setOrientation(orientation);
        }

        public MineShaftStairs(CompoundTag tag) {
            super(StructurePieceType.MINE_SHAFT_STAIRS, tag);
        }

        @Nullable
        public static BoundingBox findStairs(StructurePieceAccessor pieces, RandomSource random, int x, int y, int z, Direction direction) {
            BoundingBox var10000;
            switch (direction) {
                case SOUTH -> var10000 = new BoundingBox(0, -5, 0, 2, 2, 8);
                case WEST -> var10000 = new BoundingBox(-8, -5, 0, 0, 2, 2);
                case EAST -> var10000 = new BoundingBox(0, -5, 0, 8, 2, 2);
                default -> var10000 = new BoundingBox(0, -5, -8, 2, 2, 0);
            }

            BoundingBox $$9 = var10000;
            $$9.move(x, y, z);
            return pieces.findCollisionPiece($$9) != null ? null : $$9;
        }

        public void addChildren(StructurePiece piece, StructurePieceAccessor pieces, RandomSource random) {
            int i = this.getGenDepth();
            Direction direction = this.getOrientation();
            if (direction != null) {
                switch (direction) {
                    case SOUTH:
                        DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.maxZ() + 1, Direction.SOUTH, i);
                        break;
                    case WEST:
                        DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX() - 1, this.boundingBox.minY(), this.boundingBox.minZ(), Direction.WEST, i);
                        break;
                    case EAST:
                        DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.maxX() + 1, this.boundingBox.minY(), this.boundingBox.minZ(), Direction.EAST, i);
                        break;
                    case NORTH:
                    default:
                        DDMineshaftPieces.generateAndAddPiece(piece, pieces, random, this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ() - 1, Direction.NORTH, i);
                }
            }

        }

        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
            if (!this.isInInvalidLocation(level, box)) {
                this.generateBox(level, box, 0, 5, 0, 2, 7, 1, CAVE_AIR, CAVE_AIR, false);
                this.generateBox(level, box, 0, 0, 7, 2, 2, 8, CAVE_AIR, CAVE_AIR, false);

                for(int i = 0; i < 5; ++i) {
                    this.generateBox(level, box, 0, 5 - i - (i < 4 ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, CAVE_AIR, CAVE_AIR, false);
                }
            }

        }
    }
}
