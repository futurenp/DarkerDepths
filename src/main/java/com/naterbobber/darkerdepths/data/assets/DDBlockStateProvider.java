package com.naterbobber.darkerdepths.data.assets;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.blockstates.PillarState;
import com.naterbobber.darkerdepths.block.blockstates.PostState;
import com.naterbobber.darkerdepths.block.blockstates.VerticalSlabState;
import com.naterbobber.darkerdepths.block.custom.darkslate.*;
import com.naterbobber.darkerdepths.block.generic.*;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DDBlockStateProvider extends BlockStateProvider {
    private final Set<Block> blockIgnores = new HashSet<>();
    private final Set<Item> itemIgnores = new HashSet<>();
    private final Set<String> blockItems = new HashSet<>();


    public DDBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DarkerDepths.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        add(this::woodBlockWithItem, DDBlocks.PETRIFIED_WOOD, DDBlocks.PETRIFIED_LOG);
        add(this::woodBlockWithItem, DDBlocks.STRIPPED_PETRIFIED_WOOD, DDBlocks.STRIPPED_PETRIFIED_LOG);
        add(this::columnBlockWithItem, DDBlocks.PETRIFIED_BOOKSHELF, DDBlocks.PETRIFIED_PLANKS);
        add(this::woodBlockWithItem, DDBlocks.PETRIFIED_BOARDS, DDBlocks.PETRIFIED_BOARDS);
        add(this::geyserBlock, DDBlocks.GEYSER);
        add(this::crystalHuskBlock, DDBlocks.CRYSTAL_HUSK);
        add(this::livingCrystalBlock, DDBlocks.LIVING_CRYSTAL);
        add(this::airBlock, DDBlocks.SCORCHER_LIGHT_BLOCK);
        add(this::airBlock, DDBlocks.MOB_PLACER);
        add(this::crossWithGlow, DDBlocks.GLIMMERING_VINES);
        add(this::crossWithGlow, DDBlocks.GLIMMERING_VINE_PLANT);
        add(this::glowLampBlock, DDBlocks.GLOWSHROOM_LAMP);
        add(this::woodBlockWithItem, DDBlocks.GLOWSHROOM_HYPHAE, DDBlocks.GLOWSHROOM_STEM);
        add(this::woodBlockWithItem, DDBlocks.STRIPPED_GLOWSHROOM_HYPHAE, DDBlocks.STRIPPED_GLOWSHROOM_STEM);
        add(this::columnBlockWithItem, DDBlocks.GLOWSHROOM_BOOKSHELF, DDBlocks.GLOWSHROOM_PLANKS);
        add(this::woodBlockWithItem, DDBlocks.GLOWSHROOM_BOARDS, DDBlocks.GLOWSHROOM_PLANKS);
        add(this::verticalPlanksBlockWithItem, DDBlocks.VERTICAL_GLOWSHROOM_PLANKS, DDBlocks.GLOWSHROOM_PLANKS);
        add(this::verticalPlanksBlockWithItem, DDBlocks.VERTICAL_PETRIFIED_PLANKS, DDBlocks.PETRIFIED_PLANKS);

        add(this::rotatableDarkslateBlockWithItem, DDBlocks.DARKSLATE);
        add(this::darkslateSlabBlockWithItem, DDBlocks.DARKSLATE_SLAB, DDBlocks.DARKSLATE, true);
        add(this::darkslateVerticalSlabBlockWithItem, DDBlocks.DARKSLATE_VERTICAL_SLAB, DDBlocks.DARKSLATE, true);
        add(this::darkslateStairBlockWithItem, DDBlocks.DARKSLATE_STAIRS, DDBlocks.DARKSLATE, true);
        add(this::darkslateWallBlockWithItem, DDBlocks.DARKSLATE_WALL, DDBlocks.DARKSLATE);

        add(this::darkslateBlockWithItem, DDBlocks.POLISHED_DARKSLATE);
        add(this::darkslateSlabBlockWithItem, DDBlocks.POLISHED_DARKSLATE_SLAB, DDBlocks.POLISHED_DARKSLATE, false);
        add(this::darkslateVerticalSlabBlockWithItem, DDBlocks.POLISHED_DARKSLATE_VERTICAL_SLAB, DDBlocks.POLISHED_DARKSLATE, false);
        add(this::darkslateStairBlockWithItem, DDBlocks.POLISHED_DARKSLATE_STAIRS, DDBlocks.POLISHED_DARKSLATE, false);

        add(this::darkslateBlockWithItem, DDBlocks.DARKSLATE_BRICKS);
        add(this::darkslateSlabBlockWithItem, DDBlocks.DARKSLATE_BRICKS_SLAB, DDBlocks.DARKSLATE_BRICKS, false);
        add(this::darkslateVerticalSlabBlockWithItem, DDBlocks.DARKSLATE_BRICKS_VERTICAL_SLAB, DDBlocks.DARKSLATE_BRICKS, false);
        add(this::darkslateStairBlockWithItem, DDBlocks.DARKSLATE_BRICKS_STAIRS, DDBlocks.DARKSLATE_BRICKS, false);
        add(this::darkslateWallBlockWithItem, DDBlocks.DARKSLATE_BRICKS_WALL, DDBlocks.DARKSLATE_BRICKS);

        add(this::darkslateBlockWithItem, DDBlocks.CRACKED_DARKSLATE_BRICKS);
        add(this::darkslateBlockWithItem, DDBlocks.CHISELED_DARKSLATE_BRICKS);
        add(this::connectedRotatableDarkslatePillarBlockWithItem, DDBlocks.DARKSLATE_PILLAR);



        skipBlock(
                DDBlocks.VOID_SOUL_JAR,
                DDBlocks.DEATH_ANCHOR,
                DDBlocks.AMBER_CLUSTER,
                DDBlocks.DEATH_ANCHOR,
                DDBlocks.AMBER_CLUSTER,
                DDBlocks.ASH,
                DDBlocks.CRYSTAL_MELON,
                DDBlocks.CRYSTAL_HUSK,
                DDBlocks.GLOWSHROOM,
                DDBlocks.GLOWSHROOM_HEART,
                DDBlocks.GLOWSPURS,
                DDBlocks.MAGMA_PAD,
                DDBlocks.PARANOIA_ALTAR,
                DDBlocks.PETRIFIED_ROOTS,
                DDBlocks.PETRIFIED_ROOTS_PLANT,
                DDBlocks.STONE_MELON,
                DDBlocks.TOMB,
                DDBlocks.SKULL_WALL,
                DDBlocks.POTTED_GLOWSHROOM,
                DDBlocks.ROPE,
                DDBlocks.VOID_SOUL_TORCH,
                DDBlocks.WALL_VOID_SOUL_TORCH,
                DDBlocks.GLOWSHROOM_LANTERN,
                DDBlocks.MOSSY_GRIMESTONE,
                DDBlocks.ARID_DEEPSLATE
        );

        skipItem(DDItems.STILETTO,
                DDItems.PARANOIA_ALTAR,
                DDItems.PETRIFIED_BOAT,
                DDItems.PETRIFIED_CHEST_BOAT,
                DDItems.VOID_SOUL_JAR
        );

        autoGenerateBlockAssets();
        autoGenerateItemAssets();
    }

    @SafeVarargs
    private void skipBlock(DeferredHolder<Block, ? extends Block>... blockHolders) {
        Arrays.stream(blockHolders).forEach(blockHolder -> blockIgnores.add(blockHolder.get()));
    }

    @SafeVarargs
    private void skipItem(DeferredHolder<Item, ? extends Item>... itemHolders) {
        Arrays.stream(itemHolders).forEach(itemHolder -> itemIgnores.add(itemHolder.get()));
    }

    private void add(Consumer<DeferredHolder<Block, ? extends Block>> function, DeferredHolder<Block, ? extends Block> block) {
        blockIgnores.add(block.get());
        function.accept(block);
    }

    private void add(BiConsumer<DeferredHolder<Block, ? extends Block>, DeferredHolder<Block, ? extends Block>> function, DeferredHolder<Block, ? extends Block> block, DeferredHolder<Block, ? extends Block> parentBlock) {
        blockIgnores.add(block.get());
        function.accept(block, parentBlock);
    }

    private void add(TriConsumer<DeferredHolder<Block, ? extends Block>, DeferredHolder<Block, ? extends Block>, Boolean> function, DeferredHolder<Block, ? extends Block> block, DeferredHolder<Block, ? extends Block> parentBlock, Boolean value) {
        blockIgnores.add(block.get());
        function.accept(block, parentBlock, value);
    }

    private void autoGenerateItemAssets() {
        DDItems.ITEMS.getEntries()
                .stream()
                .filter(holder -> !itemIgnores.contains(holder.get()))
                .forEach(holder -> {
            Item item = holder.get();

            switch (item) {
                case SpawnEggItem i -> spawnEggItem(holder);
                case SignItem i -> simpleItem(holder);
                case BlockItem i -> {

                }
                default -> simpleItem(holder);
            }
        });

    }

    private void autoGenerateBlockAssets() {
        DDBlocks.BLOCKS.getEntries()
                .stream()
                .filter(holder -> !blockIgnores.contains(holder.get()))
                .forEach(holder -> {
                    Block block = holder.get();

                    switch (block) {
                        case IRelationalBlock b -> relationalBlockWithItem(holder);
                        case DoorBlock b -> doorBlockWithItem(holder);
                        case TrapDoorBlock b -> trapdoorBlockWithItem(holder);
                        case BushBlock b -> crossBlockWithItem(holder);
                        case RotatedPillarBlock b -> rotatablePillarBlockWithItem(holder);
                        case ConnectedRotatablePillarBlock b -> connectedRotatablePillarBlockWithItem(holder);
                        case ConnectedPillarBlock b -> connectedPillarBlockWithItem(holder);
                        case SignBlock b -> skipBlock(holder);
                        default -> simpleBlockWithItem(holder);
                }
            });

    }

    private void relationalBlockWithItem(DeferredHolder<Block, ? extends Block> blockHolder) {
        IRelationalBlock block = (IRelationalBlock) blockHolder.get();
        Block parentBlock = block.getBaseBlock();

        switch (block) {
            case SlabBlock b -> slabBlockWithItem(blockHolder, parentBlock);
            case VerticalSlabBlock b -> verticalSlabBlockWithItem(blockHolder, parentBlock);
            case StairBlock b -> stairsBlockWithItem(blockHolder, parentBlock);
            case PressurePlateBlock b -> pressurePlateBlockWithItem(blockHolder, parentBlock);
            case ButtonBlock b -> buttonBlockWithItem(blockHolder, parentBlock);
            case FenceBlock b -> fenceBlockWithItem(blockHolder, parentBlock);
            case FenceGateBlock b -> fenceGateBlockWithItem(blockHolder, parentBlock);
            case WallBlock b -> wallBlockWithItem(blockHolder, parentBlock);
            case WoodPostBlock b -> postBlockWithItem(blockHolder, parentBlock);
            default -> throw new IllegalStateException("Unexpected value: " + block);
        }
    }

    private void simpleItem(DeferredHolder<Item, ? extends Item> item) {
        itemModels().withExistingParent(item.getId().getPath(), "item/generated")
                .texture("layer0", modLoc("item/" + item.getId().getPath()));
    }

    private void spawnEggItem(DeferredHolder<Item, ? extends Item> item) {
        itemModels().spawnEggItem(item.get());
    }

    private void simpleBlockWithItem(DeferredHolder<Block, ? extends Block> block) {
        simpleBlock(block.get());
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void logBlockWithItem(DeferredHolder<Block, ? extends Block> block) {
        logBlock((RotatedPillarBlock) block.get());
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void stairsBlockWithItem(DeferredHolder<Block, ? extends Block> block, Block parentTexture) {
        stairsBlock((StairBlock) block.get(), blockTexture(parentTexture));
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void slabBlockWithItem(DeferredHolder<Block, ? extends Block> block, Block parentTexture) {
        slabBlock((SlabBlock) block.get(), blockTexture(parentTexture), blockTexture(parentTexture));
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void wallBlockWithItem(DeferredHolder<Block, ? extends Block> block, Block parentTexture) {
        wallBlock((WallBlock) block.get(), blockTexture(parentTexture));
        itemModels().wallInventory(block.getId().getPath(), blockTexture(parentTexture));
    }

    private void fenceBlockWithItem(DeferredHolder<Block, ? extends Block> block, Block parentTexture) {
        fenceBlock((FenceBlock) block.get(), blockTexture(parentTexture));
        itemModels().fenceInventory(block.getId().getPath(), blockTexture(parentTexture));
    }

    private void fenceGateBlockWithItem(DeferredHolder<Block, ? extends Block> block, Block parentTexture) {
        fenceGateBlock((FenceGateBlock) block.get(), blockTexture(parentTexture));
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void rotatablePillarBlockWithItem(DeferredHolder<Block, ? extends Block> blockHolder) {
        RotatedPillarBlock block = (RotatedPillarBlock) blockHolder.get();
        String blockName = block.getName().getString();

        if(!blockName.contains("wood")) {
            logBlockWithItem(blockHolder);
        }
    }

    public void rotatableDarkslateBlockWithItem(DeferredHolder<Block, ? extends Block> blockHolder) {
        Block block = blockHolder.get();
        getVariantBuilder(block).forAllStates(state -> {
            var stateName = getDarkslateStateName(blockHolder, state);

            var sideTexture = getDarkslateTexture(state, blockHolder);
            var endTexture = this.extend(sideTexture, "_top");

            var vertical = this.models().cubeColumn(stateName, sideTexture, endTexture);
            var horizontal = this.models().cubeColumnHorizontal(stateName + "_horizontal", sideTexture, endTexture);

            return switch (state.getValue(RotatedPillarBlock.AXIS)) {
                case X -> ConfiguredModel.builder().modelFile(horizontal).rotationX(90).rotationY(90).build();
                case Y -> ConfiguredModel.builder().modelFile(vertical).build();
                case Z -> ConfiguredModel.builder().modelFile(horizontal).rotationX(90).build();
            };
        });

        defaultBlockItem(blockHolder);
    }

    public void darkslateBlockWithItem(DeferredHolder<Block, ? extends Block> blockHolder) {
        var block = blockHolder.get();
        getVariantBuilder(block).forAllStates(state -> {
            var stateName = getDarkslateStateName(blockHolder, state);
            var texture = getDarkslateTexture(state, blockHolder);
            var model = this.models().cubeAll(stateName, texture);

            return ConfiguredModel.builder().modelFile(model).build();
        });

        defaultBlockItem(blockHolder);
    }

    private void darkslateSlabBlockWithItem(DeferredHolder<Block, ? extends Block> blockHolder, DeferredHolder<Block, ? extends Block> parentHolder, Boolean uniqueEnds) {
        if(!(blockHolder.get() instanceof DarkslateSlabBlock)) return ;

        getVariantBuilder(blockHolder.get()).forAllStatesExcept(state -> {
            var stateName = getDarkslateStateName(blockHolder, state);

            var sideTexture = getDarkslateTexture(state, parentHolder);
            ResourceLocation endTexture;
            if(uniqueEnds) {
                endTexture = this.extend(sideTexture, "_top");
            } else {
                endTexture = sideTexture;
            }

            var bottom = this.models().slab(stateName, sideTexture, endTexture, endTexture);
            var top = this.models().slabTop(stateName + "_top", sideTexture, endTexture, endTexture);
            var doubleSlab = this.models().cubeColumn(stateName + "_double", sideTexture, endTexture);

            return switch (state.getValue(SlabBlock.TYPE)) {
                case TOP -> ConfiguredModel.builder().modelFile(top).build();
                case BOTTOM -> ConfiguredModel.builder().modelFile(bottom).build();
                case DOUBLE -> ConfiguredModel.builder().modelFile(doubleSlab).build();
            };
        }, SlabBlock.WATERLOGGED);

        defaultBlockItem(blockHolder);
    }

    private void darkslateVerticalSlabBlockWithItem(DeferredHolder<Block, ? extends Block> blockHolder, DeferredHolder<Block, ? extends Block> parentHolder, Boolean uniqueEnds) {
        if(!(blockHolder.get() instanceof DarkslateVerticalSlabBlock)) return;

        getVariantBuilder(blockHolder.get()).forAllStatesExcept(state -> {
            var stateName = getDarkslateStateName(blockHolder, state);

            var sideTexture = getDarkslateTexture(state, parentHolder);
            ResourceLocation endTexture;
            if(uniqueEnds) {
                endTexture = this.extend(sideTexture, "_top");
            } else {
                endTexture = sideTexture;
            }

            var slab = models().withExistingParent(stateName, DarkerDepths.id("block/vertical_slab_column"))
                    .texture("texture", sideTexture).texture("bottom", endTexture).texture("top", endTexture);
            var doubleSlab = this.models().cubeColumn(stateName + "_double", sideTexture, endTexture);

            return switch (state.getValue(DDBlockStateProperties.VERTICAL_SLAB_STATE)) {
                case NORTH -> ConfiguredModel.builder().modelFile(slab).build();
                case EAST -> ConfiguredModel.builder().modelFile(slab).rotationY(90).build();
                case SOUTH -> ConfiguredModel.builder().modelFile(slab).rotationY(180).build();
                case WEST -> ConfiguredModel.builder().modelFile(slab).rotationY(270).build();
                default -> ConfiguredModel.builder().modelFile(doubleSlab).build();
            };
        }, VerticalSlabBlock.WATERLOGGED);

        defaultBlockItem(blockHolder);
    }

    private void darkslateStairBlockWithItem(DeferredHolder<Block, ? extends Block> blockHolder, DeferredHolder<Block, ? extends Block> parentHolder, Boolean uniqueEnds) {
        if(!(blockHolder.get() instanceof DarkslateStairBlock)) return;

        getVariantBuilder(blockHolder.get()).forAllStatesExcept(state -> {
            var stateName = getDarkslateStateName(blockHolder, state);

            var sideTexture = getDarkslateTexture(state, parentHolder);
            var endTexture = uniqueEnds ? this.extend(sideTexture, "_top") : sideTexture;

            var stairs = models().stairs(stateName, sideTexture, endTexture, endTexture);
            var stairsInner = models().stairsInner(stateName + "_inner", sideTexture, endTexture, endTexture);
            var stairsOuter = models().stairsOuter(stateName + "_outer", sideTexture, endTexture, endTexture);

            var facing = state.getValue(StairBlock.FACING);
            var half = state.getValue(StairBlock.HALF);
            var shape = state.getValue(StairBlock.SHAPE);
            int yRot = (int)facing.getClockWise().toYRot();
            if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) {
                yRot += 270;
            }

            if (shape != StairsShape.STRAIGHT && half == Half.TOP) {
                yRot += 90;
            }

            yRot %= 360;
            boolean uvlock = yRot != 0 || half == Half.TOP;
            return ConfiguredModel.builder()
                    .modelFile(shape == StairsShape.STRAIGHT ? stairs : (shape != StairsShape.INNER_LEFT && shape != StairsShape.INNER_RIGHT ? stairsOuter : stairsInner))
                    .rotationX(half == Half.BOTTOM ? 0 : 180)
                    .rotationY(yRot)
                    .uvLock(uvlock)
                    .build();

        }, StairBlock.WATERLOGGED);

        defaultBlockItem(blockHolder);
    }

    private void darkslateWallBlockWithItem(DeferredHolder<Block, ? extends Block> blockHolder, DeferredHolder<Block, ? extends Block> parentHolder) {
        if(!(blockHolder.get() instanceof DarkslateWallBlock)) return;
        var heatProperty = DDBlockStateProperties.HEAT_LEVEL;
        var block = blockHolder.get();
        var builder = getMultipartBuilder(block);

        for(int heat : heatProperty.getPossibleValues()) {
            var state = blockHolder.get().defaultBlockState().setValue(heatProperty, heat);
            var stateName = getDarkslateStateName(blockHolder, state);

            var texture = getDarkslateTexture(state, parentHolder);
            var post = models().wallPost(stateName + "_post", texture);
            var side = models().wallSide(stateName + "_side", texture);
            var sideTall = models().wallSideTall(stateName + "_side_tall", texture);

            builder.part().modelFile(post).addModel()
                    .condition(WallBlock.UP, true)
                    .condition(heatProperty, heat)
                    .end();

            WALL_PROPS.entrySet().stream().filter((e) -> e.getKey().getAxis().isHorizontal()).forEach((e) -> {
                wallSidePart(builder, side, e, WallSide.LOW, heat);
                wallSidePart(builder, sideTall, e, WallSide.TALL, heat);
            });
        }

        itemModels().wallInventory(blockHolder.getId().getPath(), blockTexture(DDBlocks.DARKSLATE.get()));
    }

    private void wallSidePart(MultiPartBlockStateBuilder builder, ModelFile model, Map.Entry<Direction, Property<WallSide>> entry, WallSide height, int heat) {
        var heatProperty = DDBlockStateProperties.HEAT_LEVEL;

        builder.part().modelFile(model).rotationY(((int)entry.getKey().toYRot() + 180) % 360).uvLock(true)
                .addModel()
                .condition(entry.getValue(), height)
                .condition(heatProperty, heat).end();
    }

    private void connectedRotatableDarkslatePillarBlockWithItem(DeferredHolder<Block, ? extends Block> block) {
        var blockName = block.getId().getPath();
        var heatProperty = DDBlockStateProperties.HEAT_LEVEL;

        getVariantBuilder(block.get()).forAllStates(state -> {
            int heat = state.getValue(heatProperty);

            var heatExtension = heat != 0 ? "_" + heatProperty.getName() + "_" + heat : "";

            var verticalTextures = PillarTextures.fromNameAndExtensionDarkslate(blockName, "", heat);
            var horizontalTextures = PillarTextures.fromNameAndExtensionDarkslate(blockName, "horizontal", heat);

            var textures = state.getValue(BlockStateProperties.AXIS) == Direction.Axis.Y
                    ? verticalTextures : horizontalTextures;
            var modelToUse = createPillarFromState(blockName + heatExtension, textures, state);

            return ConfiguredModel.builder()
                    .modelFile(modelToUse)
                    .build();
        });

        simpleBlockItem(block.get(), models().getExistingFile(modLoc("block/" + blockName + "_default")));
    }

    private ResourceLocation getDarkslateTexture(BlockState state, DeferredHolder<Block, ? extends Block> blockHolder) {
        return modLoc("block/" + getDarkslateTextureName(state, blockHolder));
    }

    private String getDarkslateTextureName(BlockState blockState, DeferredHolder<Block, ? extends Block> blockHolder) {
        var property = DDBlockStateProperties.HEAT_LEVEL;
        int heatLevel = blockState.getValue(property);
        var propertyName = property.getName();

        var darkslateName = blockHolder.getId().getPath();
        var textureName = darkslateName + "_" + propertyName + "_" + heatLevel;
        if (heatLevel == 0) {
            textureName = darkslateName;
        }

        return textureName;
    }

    private String getDarkslateStateName(DeferredHolder<Block, ? extends Block> blockHolder, BlockState blockState) {
        var property = DDBlockStateProperties.HEAT_LEVEL;
        var blockName = blockHolder.getId().getPath();
        int heatLevel = blockState.getValue(property);
        var propertyName = property.getName();
        var stateName = blockName + "_" + propertyName + "_" + heatLevel;
        if (heatLevel == 0) {
            stateName = blockName;
        }

        return stateName;
    }

    public void airBlock(DeferredHolder<Block, ? extends Block> block) {
        ModelFile airModel = models().getExistingFile(mcLoc("block/air"));
        simpleBlock(block.get(), airModel);
    }

    public void crossWithGlow(DeferredHolder<Block, ? extends Block> block) {
        var blockName = block.getId().getPath();
        ResourceLocation texture = modLoc("block/" + blockName);
        ResourceLocation glowTexture = this.extend(texture, "_glow");

        var modelBuilder = this.models().getBuilder(blockName)
                .parent(new ModelFile.UncheckedModelFile("minecraft:block/block"))
                .renderType("minecraft:translucent")
                .texture("particle", texture)
                .texture("cross", texture)
                .texture("glow", glowTexture);

        modelBuilder.element().from(0, 0, 8).to(16, 16, 8.001f)
                .rotation().origin(8, 8, 8).axis(Direction.Axis.Y).angle(45f).rescale(true).end()
                .face(Direction.NORTH).texture("#cross").uvs(0, 0, 16, 16).end()
                .face(Direction.SOUTH).texture("#cross").uvs(0, 0, 16, 16).end()
                .end();

        modelBuilder.element().from(8, 0, 0).to(8.001f, 16, 16)
                .rotation().origin(8, 8, 8).axis(Direction.Axis.Y).angle(45f).rescale(true).end()
                .face(Direction.EAST).texture("#cross").uvs(0, 0, 16, 16).end()
                .face(Direction.WEST).texture("#cross").uvs(0, 0, 16, 16).end()
                .end();

        modelBuilder.element().from(-0.01f, -0.01f, 7.99f).to(16.01f, 16.01f, 8.01f)
                .rotation().origin(8, 8, 8).axis(Direction.Axis.Y).angle(45f).rescale(true).end()
                .shade(false)
                .face(Direction.NORTH).texture("#glow").uvs(0, 0, 16, 16).end()
                .face(Direction.SOUTH).texture("#glow").uvs(0, 0, 16, 16).end()
                .end();

        modelBuilder.element().from(7.99f, -0.01f, -0.01f).to(8.01f, 16.01f, 16.01f)
                .rotation().origin(8, 8, 8).axis(Direction.Axis.Y).angle(45f).rescale(true).end()
                .shade(false)
                .face(Direction.EAST).texture("#glow").uvs(0, 0, 16, 16).end()
                .face(Direction.WEST).texture("#glow").uvs(0, 0, 16, 16).end()
                .end();

        var model = ConfiguredModel.builder().modelFile(modelBuilder).build();

        simpleBlock(block.get(), model);

        itemModels().withExistingParent(block.getId().getPath(), "item/generated")
                .texture("layer0", blockTexture(block.get()));
    }

    public void glowLampBlock(DeferredHolder<Block, ? extends Block> block) {
        String blockName = block.getId().getPath();

        ResourceLocation offTexture = modLoc("block/" + blockName);
        ResourceLocation onTexture = this.extend(offTexture, "_lit");
        ResourceLocation glowTexture = this.extend(offTexture, "_lit_glow");

        var offModel = models().withExistingParent(blockName, mcLoc("block/cube_all"))
                .texture("all", offTexture);

        var onModel = models().withExistingParent(blockName + "_on", modLoc("block/glow_block"))
                .texture("particle", onTexture)
                .texture("base", onTexture)
                .texture("glow", glowTexture);

        getVariantBuilder(block.get())
                .partialState().with(BlockStateProperties.LIT, false)
                .addModels(new ConfiguredModel(offModel))
                .partialState().with(BlockStateProperties.LIT, true)
                .addModels(new ConfiguredModel(onModel));

        simpleBlockItem(block.get(), offModel);
    }

    public void verticalSlabBlockWithItem(DeferredHolder<Block, ? extends Block> block, Block parentBlock) {
        String blockName = block.getId().getPath();
        ResourceLocation texture = blockTexture(parentBlock);

        var defaultModel = models().withExistingParent(blockName, DarkerDepths.id("block/vertical_slab"))
                .texture("texture", texture);

        var doubleModel = models().getExistingFile(
                models().modLoc(BuiltInRegistries.BLOCK.getKey(parentBlock).getPath())
        );

        getVariantBuilder(block.get())
                .partialState().with(DDBlockStateProperties.VERTICAL_SLAB_STATE, VerticalSlabState.NORTH).addModels(new ConfiguredModel(defaultModel, 0, 0, true))
                .partialState().with(DDBlockStateProperties.VERTICAL_SLAB_STATE, VerticalSlabState.EAST).addModels(new ConfiguredModel(defaultModel, 0, 90, true))
                .partialState().with(DDBlockStateProperties.VERTICAL_SLAB_STATE, VerticalSlabState.SOUTH).addModels(new ConfiguredModel(defaultModel, 0, 180, true))
                .partialState().with(DDBlockStateProperties.VERTICAL_SLAB_STATE, VerticalSlabState.WEST).addModels(new ConfiguredModel(defaultModel, 0, 270, true))
                .partialState().with(DDBlockStateProperties.VERTICAL_SLAB_STATE, VerticalSlabState.DOUBLE).addModels(new ConfiguredModel(doubleModel, 0, 0, true));

        simpleBlockItem(block.get(), defaultModel);
    }

    public void verticalPlanksBlockWithItem(DeferredHolder<Block, ? extends Block> block, DeferredHolder<Block, ? extends Block> parentBlock) {
        String blockName = block.getId().getPath();
        ResourceLocation texture = blockTexture(parentBlock.get());

        var defaultModel = models().withExistingParent(blockName, DarkerDepths.id("block/vertical_planks"))
                .texture("all", texture);

        simpleBlock(block.get(), defaultModel);
        simpleBlockItem(block.get(), defaultModel);
    }

    public void postBlockWithItem(DeferredHolder<Block, ? extends Block> block, Block parentBlock) {
        String blockName = block.getId().getPath();
        ResourceLocation texture = blockTexture(parentBlock);

        var postModel = models().withExistingParent(blockName, DarkerDepths.id("block/post"))
                .texture("texture", texture);
        var chainSmall = models().getExistingFile(modLoc("block/chain_small"));
        var chainSmallTop = models().getExistingFile(modLoc("block/chain_small_top"));
        var otherPost = models().withExistingParent(blockName + "_connect", DarkerDepths.id("block/post_connect"))
                .texture("texture", texture);
        var otherPostTop = models().withExistingParent(blockName + "_connect_top", DarkerDepths.id("block/post_connect_top"))
                .texture("texture", texture);

        var builder = getMultipartBuilder(block.get());

        builder.part().modelFile(postModel).addModel()
                .condition(BlockStateProperties.AXIS, Direction.Axis.Y);
        builder.part().modelFile(postModel).rotationX(90).rotationY(90).addModel()
                .condition(BlockStateProperties.AXIS, Direction.Axis.X);
        builder.part().modelFile(postModel).rotationX(90).addModel()
                .condition(BlockStateProperties.AXIS, Direction.Axis.Z);

        builder.part().modelFile(chainSmall).addModel()
                .condition(DDBlockStateProperties.CONNECT_DOWN, PostState.CHAIN);
        builder.part().modelFile(chainSmallTop).addModel()
                .condition(DDBlockStateProperties.CONNECT_UP, PostState.CHAIN);
        builder.part().modelFile(chainSmallTop).rotationX(90).addModel()
                .condition(DDBlockStateProperties.CONNECT_NORTH, PostState.CHAIN);
        builder.part().modelFile(chainSmall).rotationX(90).addModel()
                .condition(DDBlockStateProperties.CONNECT_SOUTH, PostState.CHAIN);
        builder.part().modelFile(chainSmallTop).rotationX(90).rotationY(90).addModel()
                .condition(DDBlockStateProperties.CONNECT_WEST, PostState.CHAIN);
        builder.part().modelFile(chainSmall).rotationX(90).rotationY(90).addModel()
                .condition(DDBlockStateProperties.CONNECT_EAST, PostState.CHAIN);

        builder.part().modelFile(otherPost).addModel()
                .condition(DDBlockStateProperties.CONNECT_DOWN, PostState.OTHER_POST);
        builder.part().modelFile(otherPostTop).addModel()
                .condition(DDBlockStateProperties.CONNECT_UP, PostState.OTHER_POST);
        builder.part().modelFile(otherPostTop).rotationX(90).addModel()
                .condition(DDBlockStateProperties.CONNECT_NORTH, PostState.OTHER_POST);
        builder.part().modelFile(otherPost).rotationX(90).addModel()
                .condition(DDBlockStateProperties.CONNECT_SOUTH, PostState.OTHER_POST);
        builder.part().modelFile(otherPostTop).rotationX(90).rotationY(90).addModel()
                .condition(DDBlockStateProperties.CONNECT_WEST, PostState.OTHER_POST);
        builder.part().modelFile(otherPost).rotationX(90).rotationY(90).addModel()
                .condition(DDBlockStateProperties.CONNECT_EAST, PostState.OTHER_POST);

        simpleBlockItem(block.get(), postModel);
    }


    public void crystalHuskBlock(DeferredHolder<Block, ? extends Block> block) {
        getVariantBuilder(block.get()).forAllStates(state -> {
            int growth = state.getValue(DDBlockStateProperties.CRYSTAL_GROWTH_LEVEL);
            int heat = state.getValue(DDBlockStateProperties.HEAT_LEVEL);

            String baseName = "crystal_husk";
            if (growth > 0) {
                baseName += "_growth_" + growth;
            }

            String heatSuffix = "";
            if (heat == 1) {
                heatSuffix = "_heat_1";
            } else if (heat >= 2) {
                heatSuffix = "_heat_2";
            }

            String modelName = baseName + heatSuffix;
            ResourceLocation textureLocation = modLoc("block/" + modelName);

            ModelFile model = models().cubeAll(modelName, textureLocation);

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });

        simpleBlockItem(block.get(), models().getExistingFile(modLoc("block/crystal_husk")));
    }

    public void livingCrystalBlock(DeferredHolder<Block, ? extends Block> block) {
        getVariantBuilder(block.get()).forAllStates(state -> {
            int heat = state.getValue(DDBlockStateProperties.HEAT_LEVEL);

            String baseName = "living_crystal";
            String heatSuffix = "";

            if (heat == 1) {
                heatSuffix = "_heat_1";
            } else if (heat >= 2) {
                heatSuffix = "_heat_2";
            }

            String modelName = baseName + heatSuffix;
            ResourceLocation textureLocation = modLoc("block/" + modelName);

            ModelFile model = models().cubeAll(modelName, textureLocation);

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });

        simpleBlockItem(block.get(), models().getExistingFile(modLoc("block/living_crystal")));
    }

    public void geyserBlock(DeferredHolder<Block, ? extends Block> blockHolder) {
        var sideTexture = modLoc("block/geyser");
        var bottomTexture = modLoc("block/darkslate_top");
        var topTexture = modLoc("block/geyser_top");

        var heatedSideTexture = modLoc("block/heated_geyser");
        var heatedBottomTexture = modLoc("block/darkslate_heat_level_3_top");
        var heatedTopTexture = modLoc("block/heated_geyser_top");

        var normalModel = models().cubeBottomTop(
                "geyser",
                sideTexture,
                bottomTexture,
                topTexture
        );

        var burstingModel = models().cubeBottomTop(
                "heated_geyser",
                heatedSideTexture,
                heatedBottomTexture,
                heatedTopTexture
        );

        var block = blockHolder.get();

        directionalBlock(block, state -> {
            boolean isBursting = state.getValue(DDBlockStateProperties.BURSTING);
            return isBursting ? burstingModel : normalModel;
        });

        simpleBlockItem(block, normalModel);
    }

    private void doorBlockWithItem(DeferredHolder<Block, ? extends Block> block) {
        var location = block.getId();

        var bottomTexture = location.withPath("block/" + location.getPath() + "_bottom");
        var topTexture = location.withPath("block/" + location.getPath() + "_top");
        var itemTexture = location.withPath("item/" + location.getPath());

        doorBlockWithRenderType((DoorBlock) block.get(), bottomTexture, topTexture, "cutout");

        itemModels().withExistingParent(block.getId().getPath(), "item/generated")
                .texture("layer0", itemTexture);
    }

    private void connectedPillarBlockWithItem(DeferredHolder<Block, ? extends Block> block) {
        var blockName = block.getId().getPath();
        var textures = PillarTextures.fromName(blockName);

        getVariantBuilder(block.get()).forAllStates(state -> {
            var modelToUse = createPillarFromState(blockName, textures, state);

            return ConfiguredModel.builder()
                    .modelFile(modelToUse)
                    .build();
        });

        simpleBlockItem(block.get(), models().getExistingFile(modLoc("block/" + blockName + "_default")));
    }

    private void trapdoorBlockWithItem(DeferredHolder<Block, ? extends Block> block) {
        ResourceLocation location = block.getId();
        String path = "block/" + location.getPath();
        trapdoorBlock((TrapDoorBlock) block.get(), modLoc(path), true);
        simpleBlockItem(block.get(), models().getExistingFile(modLoc(path + "_bottom")));
    }

    private void pressurePlateBlockWithItem(DeferredHolder<Block, ? extends Block> block, Block parentTexture) {
        pressurePlateBlock((PressurePlateBlock) block.get(), blockTexture(parentTexture));
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void buttonBlockWithItem(DeferredHolder<Block, ? extends Block> block, Block parentTexture) {
        buttonBlock((ButtonBlock) block.get(), blockTexture(parentTexture));
        itemModels().buttonInventory(block.getId().getPath(), blockTexture(parentTexture));
    }

    private void woodBlockWithItem(DeferredHolder<Block, ? extends Block> block, DeferredHolder<Block, ? extends Block> logTextureSource) {
        ResourceLocation texture = DarkerDepths.id("block/" + logTextureSource.getId().getPath());
        axisBlock((RotatedPillarBlock) block.get(), texture, texture);
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void crossBlockWithItem(DeferredHolder<Block, ? extends Block> block) {
        simpleBlock(block.get(), models().cross(block.getId().getPath(), blockTexture(block.get())).renderType("cutout"));
        itemModels().withExistingParent(block.getId().getPath(), "item/generated")
                .texture("layer0", blockTexture(block.get()));
    }

    private void columnBlockWithItem(DeferredHolder<Block, ? extends Block> block, DeferredHolder<Block, ? extends Block> topBlock) {
        var location = block.getId();
        var blockName = location.getPath();
        var parentBlockName = topBlock.getId().getPath();

        var sideTexture = location.withPath("block/" + blockName);
        var topTexture = location.withPath("block/" + parentBlockName);
        var cubeColumn = models().cubeColumn(blockName, sideTexture, topTexture);

        getVariantBuilder(block.get()).partialState().setModels(new ConfiguredModel(cubeColumn));
        simpleBlockItem(block.get(), cubeColumn);
    }

    private void connectedRotatablePillarBlockWithItem(DeferredHolder<Block, ? extends Block> block) {
        var blockName = block.getId().getPath();
        var verticalTextures = PillarTextures.fromName(blockName);
        var horizontalTextures = PillarTextures.fromNameAndExtension(blockName, "horizontal");

        getVariantBuilder(block.get()).forAllStates(state -> {
            var textures = state.getValue(BlockStateProperties.AXIS) == Direction.Axis.Y
                    ? verticalTextures : horizontalTextures;
            var modelToUse = createPillarFromState(blockName, textures, state);

            return ConfiguredModel.builder()
                    .modelFile(modelToUse)
                    .build();
        });

        simpleBlockItem(block.get(), models().getExistingFile(modLoc("block/" + blockName + "_default")));
    }

    private ModelFile createPillarFromState(String blockName, PillarTextures textures, BlockState state) {
        var pillarState = state.getValue(DDBlockStateProperties.PILLAR_STATE);
        var axisProperty = BlockStateProperties.AXIS;
        Direction.Axis axis = null;

        if(state.hasProperty(axisProperty)) {
            axis = state.getValue(axisProperty);
        }

        ResourceLocation sideTex;
        switch(pillarState) {
            case LOWER -> sideTex = textures.lowerTexture;
            case MIDDLE -> sideTex = textures.middleTexture;
            case UPPER -> sideTex = textures.upperTexture;
            default -> sideTex = textures.texture;
        }

        var endTex = textures.endTexture;

        if(axis == Direction.Axis.Y || axis == null) {
            return models().cubeColumn(blockName + "_" + pillarState, sideTex, endTex);
        }

        var parentName = "block/pillar_" + axis;
        var modelName = blockName + "_" + pillarState + "_" + axis;

        return models().withExistingParent(modelName, DarkerDepths.id(parentName))
                .texture("particle", sideTex)
                .texture("side", sideTex)
                .texture("end", endTex);
    }

    private record PillarTextures(ResourceLocation texture, ResourceLocation lowerTexture, ResourceLocation middleTexture, ResourceLocation upperTexture, ResourceLocation endTexture) {
        public static PillarTextures fromNameAndExtension(String blockName, String extension) {
            if(!extension.isEmpty()) {
                extension = "_" + extension;
            }
            var defaultPath = "block/" + blockName + "_";
            var texture = DarkerDepths.id(defaultPath + "side" + extension);
            var lowerTexture = DarkerDepths.id(defaultPath + "side_lower" + extension);
            var middleTexture = DarkerDepths.id(defaultPath + "side_middle" + extension);
            var upperTexture = DarkerDepths.id(defaultPath + "side_upper" + extension);
            var endTexture = DarkerDepths.id(defaultPath + "end");

            return new PillarTextures(texture, lowerTexture, middleTexture, upperTexture, endTexture);
        }

        public static PillarTextures fromNameAndExtensionDarkslate(String blockName, String extension, int heat) {
            if(!extension.isEmpty()) {
                extension = "_" + extension;
            }

            var heatProperty = DDBlockStateProperties.HEAT_LEVEL;
            var heatExtension = heat == 0 ? "" : "_" + heatProperty.getName() + "_" + heat;

            var defaultPath = "block/" + blockName + "_";
            var texture = DarkerDepths.id(defaultPath + "side" + extension + heatExtension);
            var lowerTexture = DarkerDepths.id(defaultPath + "side_lower" + extension + heatExtension);
            var middleTexture = DarkerDepths.id(defaultPath + "side_middle" + extension + heatExtension);
            var upperTexture = DarkerDepths.id(defaultPath + "side_upper" + extension + heatExtension);
            var endTexture = DarkerDepths.id(defaultPath + "end" + heatExtension);

            return new PillarTextures(texture, lowerTexture, middleTexture, upperTexture, endTexture);
        }

        public static PillarTextures fromName(String blockName) {
            return PillarTextures.fromNameAndExtension(blockName, "");
        }
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        String var10000 = rl.getNamespace();
        String var10001 = rl.getPath();
        return ResourceLocation.fromNamespaceAndPath(var10000, var10001 + suffix);
    }

    private void defaultBlockItem(DeferredHolder<Block, ? extends Block> blockHolder) {
        simpleBlockItem(blockHolder.get(), models().getExistingFile(modLoc("block/" + blockHolder.getId().getPath())));
    }
}