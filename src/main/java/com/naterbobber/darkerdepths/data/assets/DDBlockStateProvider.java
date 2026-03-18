package com.naterbobber.darkerdepths.data.assets;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.blockstates.PillarState;
import com.naterbobber.darkerdepths.block.custom.DarkslateBlock;
import com.naterbobber.darkerdepths.block.generic.*;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;
import java.util.HashSet;
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
                DDBlocks.GLIMMERING_VINE_PLANT,
                DDBlocks.GLIMMERING_VINES,
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
                DDBlocks.MOB_PLACER,
                DDBlocks.MOSSY_GRIMESTONE,
                DDBlocks.GLOWSHROOM_LAMP
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

    private void autoGenerateItemAssets() {
        DDItems.ITEMS.getEntries()
                .stream()
                .filter(holder -> !itemIgnores.contains(holder.get()))
                .forEach(holder -> {
            Item item = holder.get();

            switch (item) {
                case SpawnEggItem i -> spawnEggItem(holder);
//                case BlockItem i -> blockItems.add(holder.getRegisteredName());
                case BlockItem i -> {

                }
                default -> {
                    simpleItem(holder);
                }
            }

//            assignBlockItems();
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
                        case DarkslateBlock b -> darkslateBlockWithItem(holder, DDBlockStateProperties.HEAT_LEVEL);
                        case RotatedPillarBlock b -> rotatablePillarBlockWithItem(holder);
                        case ConnectedRotatablePillarBlock b -> connectedRotatablePillarBlockWithItem(holder);
                        case ConnectedPillarBlock b -> connectedPillarBlockWithItem(holder);
                        case SignBlock b -> skipBlock(holder);
                        case VerticalSlabBlock b -> skipBlock(holder);
                        case WoodPostBlock b -> skipBlock(holder);
                        default -> simpleBlockWithItem(holder);
                }
            });

    }

    private void assignBlockItems() {
        DDBlocks.BLOCKS.getEntries()
                .stream()
                .filter(holder -> !blockIgnores.contains(holder.get()))
                .forEach(holder -> {
                    if(blockItems.contains(holder.getRegisteredName())) {
                        simpleBlockItem(holder.get(), models().getExistingFile(blockTexture(holder.get())));
                    }
                });
    }

    private void relationalBlockWithItem(DeferredHolder<Block, ? extends Block> blockHolder) {
        IRelationalBlock block = (IRelationalBlock) blockHolder.get();
        Block parentBlock = block.getBaseBlock();

        switch (block) {
            case RelationalSlabBlock b -> slabBlockWithItem(blockHolder, parentBlock);
            case RelationalStairBlock b -> stairsBlockWithItem(blockHolder, parentBlock);
            case RelationalPressurePlateBlock b -> pressurePlateBlockWithItem(blockHolder, parentBlock);
            case RelationalButtonBlock b -> buttonBlockWithItem(blockHolder, parentBlock);
            case RelationalFenceBlock b -> fenceBlockWithItem(blockHolder, parentBlock);
            case RelationalFenceGateBlock b -> fenceGateBlockWithItem(blockHolder, parentBlock);
            case RelationalWallBlock b -> wallBlockWithItem(blockHolder, parentBlock);
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

        if(blockName.contains("wood")) {
            return;
        } else {
            logBlockWithItem(blockHolder);
        }
    }

    public void darkslateBlockWithItem(DeferredHolder<Block, ? extends Block> blockHolder, IntegerProperty property) {
        Block block = blockHolder.get();
        getVariantBuilder(block).forAllStates(state -> {
            Direction.Axis axis = state.getValue(RotatedPillarBlock.AXIS);
            int propValue = state.getValue(property);
            String propertyName = property.getName();
            String blockName = blockHolder.getId().getPath();

            String stateName = blockName + "_" + propertyName + "_" + propValue;
            if (propValue == 0) {
                stateName = blockName;
            }

            ResourceLocation sideTexture = modLoc("block/" + stateName);
            ResourceLocation endTexture = this.extend(sideTexture, "_top");

            ModelFile vertical;
            ModelFile horizontal;

            if (propValue >= 2) {
                ResourceLocation glowSideTexture = this.extend(sideTexture, "_glow");
                ResourceLocation glowEndTexture = this.extend(endTexture, "_glow");

                vertical = this.models().getBuilder(stateName)
                        .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile("minecraft:block/block"))
                        .renderType("minecraft:translucent")
                        .texture("particle", sideTexture)
                        .texture("base_side", sideTexture)
                        .texture("base_end", endTexture)
                        .texture("glow_side", glowSideTexture)
                        .texture("glow_end", glowEndTexture)
                        .element().from(0, 0, 0).to(16, 16, 16)
                        .face(Direction.UP).texture("#base_end").cullface(Direction.UP).end()
                        .face(Direction.DOWN).texture("#base_end").cullface(Direction.DOWN).end()
                        .face(Direction.NORTH).texture("#base_side").cullface(Direction.NORTH).end()
                        .face(Direction.SOUTH).texture("#base_side").cullface(Direction.SOUTH).end()
                        .face(Direction.EAST).texture("#base_side").cullface(Direction.EAST).end()
                        .face(Direction.WEST).texture("#base_side").cullface(Direction.WEST).end()
                        .end()
                        // Glowing overlay cube
                        .element().from(0, 0, 0).to(16, 16, 16)
                        .face(Direction.UP).texture("#glow_end").cullface(Direction.UP).end()
                        .face(Direction.DOWN).texture("#glow_end").cullface(Direction.DOWN).end()
                        .face(Direction.NORTH).texture("#glow_side").cullface(Direction.NORTH).end()
                        .face(Direction.SOUTH).texture("#glow_side").cullface(Direction.SOUTH).end()
                        .face(Direction.EAST).texture("#glow_side").cullface(Direction.EAST).end()
                        .face(Direction.WEST).texture("#glow_side").cullface(Direction.WEST).end()
                        .end();

                horizontal = this.models().getBuilder(stateName + "_horizontal")
                        .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile("minecraft:block/block"))
                        .renderType("minecraft:translucent")
                        .texture("particle", sideTexture)
                        .texture("base_side", sideTexture)
                        .texture("base_end", endTexture)
                        .texture("glow_side", glowSideTexture)
                        .texture("glow_end", glowEndTexture)
                        .element().from(0, 0, 0).to(16, 16, 16)
                        .face(Direction.UP).texture("#base_end").cullface(Direction.UP).end()
                        .face(Direction.DOWN).texture("#base_end").cullface(Direction.DOWN).end()
                        .face(Direction.NORTH).texture("#base_side").cullface(Direction.NORTH).rotation(net.neoforged.neoforge.client.model.generators.ModelBuilder.FaceRotation.CLOCKWISE_90).end()
                        .face(Direction.SOUTH).texture("#base_side").cullface(Direction.SOUTH).rotation(net.neoforged.neoforge.client.model.generators.ModelBuilder.FaceRotation.CLOCKWISE_90).end()
                        .face(Direction.EAST).texture("#base_side").cullface(Direction.EAST).rotation(net.neoforged.neoforge.client.model.generators.ModelBuilder.FaceRotation.CLOCKWISE_90).end()
                        .face(Direction.WEST).texture("#base_side").cullface(Direction.WEST).rotation(net.neoforged.neoforge.client.model.generators.ModelBuilder.FaceRotation.CLOCKWISE_90).end()
                        .end()
                        // Glowing overlay cube
                        .element().from(0, 0, 0).to(16, 16, 16)
                        .face(Direction.UP).texture("#glow_end").cullface(Direction.UP).end()
                        .face(Direction.DOWN).texture("#glow_end").cullface(Direction.DOWN).end()
                        .face(Direction.NORTH).texture("#glow_side").cullface(Direction.NORTH).rotation(net.neoforged.neoforge.client.model.generators.ModelBuilder.FaceRotation.CLOCKWISE_90).end()
                        .face(Direction.SOUTH).texture("#glow_side").cullface(Direction.SOUTH).rotation(net.neoforged.neoforge.client.model.generators.ModelBuilder.FaceRotation.CLOCKWISE_90).end()
                        .face(Direction.EAST).texture("#glow_side").cullface(Direction.EAST).rotation(net.neoforged.neoforge.client.model.generators.ModelBuilder.FaceRotation.CLOCKWISE_90).end()
                        .face(Direction.WEST).texture("#glow_side").cullface(Direction.WEST).rotation(net.neoforged.neoforge.client.model.generators.ModelBuilder.FaceRotation.CLOCKWISE_90).end()
                        .end();
            } else {
                vertical = this.models().cubeColumn(stateName, sideTexture, endTexture);
                horizontal = this.models().cubeColumnHorizontal(stateName + "_horizontal", sideTexture, endTexture);
            }

            if (axis == Direction.Axis.Y) {
                return ConfiguredModel.builder().modelFile(vertical).build();
            } else if (axis == Direction.Axis.Z) {
                return ConfiguredModel.builder().modelFile(horizontal).rotationX(90).build();
            } else {
                return ConfiguredModel.builder().modelFile(horizontal).rotationX(90).rotationY(90).build();
            }
        });

        simpleBlockItem(block, models().getExistingFile(blockTexture(block)));
    }

    public void geyserBlock(DeferredHolder<Block, ? extends Block> blockHolder) {
        Block block = blockHolder.get();

        ResourceLocation sideTexture = modLoc("block/geyser");
        ResourceLocation bottomTexture = modLoc("block/darkslate_top");
        ResourceLocation topTexture = modLoc("block/geyser_top");

        ResourceLocation heatedSideTexture = modLoc("block/heated_geyser");
        ResourceLocation heatedBottomTexture = modLoc("block/darkslate_heat_level_2_top");
        ResourceLocation heatedTopTexture = modLoc("block/heated_geyser_top");

        ResourceLocation heatedSideGlow = modLoc("block/heated_geyser_glow");
        ResourceLocation heatedBottomGlow = modLoc("block/darkslate_heat_level_2_top_glow");
        ResourceLocation heatedTopGlow = modLoc("block/heated_geyser_top_glow");

        ModelFile normalModel = models().cubeBottomTop(
                "geyser",
                sideTexture,
                bottomTexture,
                topTexture
        );

        ModelFile burstingModel = this.models().getBuilder("heated_geyser")
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile("minecraft:block/block"))
                .texture("particle", heatedSideTexture)
                .texture("base_side", heatedSideTexture)
                .texture("base_bottom", heatedBottomTexture)
                .texture("base_top", heatedTopTexture)
                .texture("glow_side", heatedSideGlow)
                .texture("glow_bottom", heatedBottomGlow)
                .texture("glow_top", heatedTopGlow)
                // Base stone cube
                .element().from(0, 0, 0).to(16, 16, 16)
                .face(Direction.DOWN).texture("#base_bottom").cullface(Direction.DOWN).end()
                .face(Direction.UP).texture("#base_top").cullface(Direction.UP).end()
                .face(Direction.NORTH).texture("#base_side").cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).texture("#base_side").cullface(Direction.SOUTH).end()
                .face(Direction.EAST).texture("#base_side").cullface(Direction.EAST).end()
                .face(Direction.WEST).texture("#base_side").cullface(Direction.WEST).end()
                .end()
                // Glowing overlay cube (Now includes the DOWN face)
                .element().from(0, 0, 0).to(16, 16, 16)
                .face(Direction.DOWN).texture("#glow_bottom").cullface(Direction.DOWN).end()
                .face(Direction.UP).texture("#glow_top").cullface(Direction.UP).end()
                .face(Direction.NORTH).texture("#glow_side").cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).texture("#glow_side").cullface(Direction.SOUTH).end()
                .face(Direction.EAST).texture("#glow_side").cullface(Direction.EAST).end()
                .face(Direction.WEST).texture("#glow_side").cullface(Direction.WEST).end()
                .end();

        directionalBlock(block, state -> {
            boolean isBursting = state.getValue(DDBlockStateProperties.BURSTING);
            return isBursting ? burstingModel : normalModel;
        });

        simpleBlockItem(block, normalModel);
    }

    private void doorBlockWithItem(DeferredHolder<Block, ? extends Block> block) {
        ResourceLocation location = block.getId();

        ResourceLocation bottomTexture = location.withPath("block/" + location.getPath() + "_bottom");
        ResourceLocation topTexture = location.withPath("block/" + location.getPath() + "_top");

        doorBlock((DoorBlock) block.get(), bottomTexture, topTexture);

        ResourceLocation itemTexture = location.withPath("item/" + location.getPath());
        itemModels().withExistingParent(block.getId().getPath(), "item/generated")
                .texture("layer0", itemTexture);
    }

    private void connectedPillarBlockWithItem(DeferredHolder<Block, ? extends Block> block) {
        ResourceLocation location = block.getId();
        String blockName = location.getPath();
        ResourceLocation endTexture = location.withPath("block/" + blockName + "_end");
        ResourceLocation sideTexture = location.withPath("block/" + blockName + "_side");
        ResourceLocation sideLowerTexture = location.withPath("block/" + blockName + "_side_lower");
        ResourceLocation sideMiddleTexture = location.withPath("block/" + blockName + "_side_middle");
        ResourceLocation sideUpperTexture = location.withPath("block/" + blockName + "_side_upper");

        ModelFile defaultModel = models().cubeColumn(blockName + "_default", sideTexture, endTexture);
        ModelFile lowerModel = models().cubeColumn(blockName + "_lower", sideLowerTexture, endTexture);
        ModelFile middleModel = models().cubeColumn(blockName + "_middle", sideMiddleTexture, endTexture);
        ModelFile upperModel = models().cubeColumn(blockName + "_upper", sideUpperTexture, endTexture);

        getVariantBuilder(block.get()).forAllStates(state -> {
            PillarState type = state.getValue(ConnectedRotatablePillarBlock.PILLAR_STATE);

            ModelFile modelToUse = switch (type) {
                case LOWER -> lowerModel;
                case MIDDLE -> middleModel;
                case UPPER -> upperModel;
                default -> defaultModel;
            };

            return ConfiguredModel.builder()
                    .modelFile(modelToUse)
                    .build();
        });

        simpleBlockItem(block.get(), defaultModel);
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
        ResourceLocation location = block.getId();
        String blockName = location.getPath();
        ResourceLocation topLocation = topBlock.getId();
        String parentBlockName = topLocation.getPath();

        ResourceLocation sideTexture = location.withPath("block/" + blockName);
        ResourceLocation topTexture = location.withPath("block/" + parentBlockName);
        ModelFile cubeColumn = models().cubeColumn(blockName, sideTexture, topTexture);

        getVariantBuilder(block.get()).partialState().setModels(new ConfiguredModel(cubeColumn));
        simpleBlockItem(block.get(), cubeColumn);
    }

    private void connectedRotatablePillarBlockWithItem(DeferredHolder<Block, ? extends Block> block) {
        ResourceLocation location = block.getId();
        String blockName = location.getPath();

        ResourceLocation endTexture = location.withPath("block/" + blockName + "_end");

        ResourceLocation sideTexture = location.withPath("block/" + blockName + "_side");
        ResourceLocation sideLowerTexture = location.withPath("block/" + blockName + "_side_lower");
        ResourceLocation sideMiddleTexture = location.withPath("block/" + blockName + "_side_middle");
        ResourceLocation sideUpperTexture = location.withPath("block/" + blockName + "_side_upper");

        ResourceLocation sideTextureHoriz = location.withPath("block/" + blockName + "_side_horizontal");
        ResourceLocation sideLowerTextureHoriz = location.withPath("block/" + blockName + "_side_lower_horizontal");
        ResourceLocation sideMiddleTextureHoriz = location.withPath("block/" + blockName + "_side_middle_horizontal");
        ResourceLocation sideUpperTextureHoriz = location.withPath("block/" + blockName + "_side_upper_horizontal");

        ModelFile defaultModelY = models().cubeColumn(blockName + "_default", sideTexture, endTexture);
        ModelFile lowerModelY = models().cubeColumn(blockName + "_lower", sideLowerTexture, endTexture);
        ModelFile middleModelY = models().cubeColumn(blockName + "_middle", sideMiddleTexture, endTexture);
        ModelFile upperModelY = models().cubeColumn(blockName + "_upper", sideUpperTexture, endTexture);

        ModelFile defaultModelZ = createZAxisModel(blockName + "_default_z", sideTextureHoriz, endTexture);
        ModelFile lowerModelZ = createZAxisModel(blockName + "_lower_z", sideLowerTextureHoriz, endTexture);
        ModelFile middleModelZ = createZAxisModel(blockName + "_middle_z", sideMiddleTextureHoriz, endTexture);
        ModelFile upperModelZ = createZAxisModel(blockName + "_upper_z", sideUpperTextureHoriz, endTexture);

        ModelFile defaultModelX = createXAxisModel(blockName + "_default_x", sideTextureHoriz, endTexture);
        ModelFile lowerModelX = createXAxisModel(blockName + "_lower_x", sideLowerTextureHoriz, endTexture);
        ModelFile middleModelX = createXAxisModel(blockName + "_middle_x", sideMiddleTextureHoriz, endTexture);
        ModelFile upperModelX = createXAxisModel(blockName + "_upper_x", sideUpperTextureHoriz, endTexture);

        getVariantBuilder(block.get()).forAllStates(state -> {
            PillarState type = state.getValue(ConnectedRotatablePillarBlock.PILLAR_STATE);
            Direction.Axis axis = state.getValue(ConnectedRotatablePillarBlock.AXIS);

            ModelFile modelToUse;

            switch (axis) {
                case X -> {
                    modelToUse = switch (type) {
                        case LOWER -> lowerModelX;
                        case MIDDLE -> middleModelX;
                        case UPPER -> upperModelX;
                        default -> defaultModelX;
                    };
                }
                case Z -> {
                    modelToUse = switch (type) {
                        case LOWER -> lowerModelZ;
                        case MIDDLE -> middleModelZ;
                        case UPPER -> upperModelZ;
                        default -> defaultModelZ;
                    };
                }
                case Y -> {
                    modelToUse = switch (type) {
                        case LOWER -> lowerModelY;
                        case MIDDLE -> middleModelY;
                        case UPPER -> upperModelY;
                        default -> defaultModelY;
                    };
                }
                default -> modelToUse = defaultModelY;
            }

            return ConfiguredModel.builder()
                    .modelFile(modelToUse)
                    .build();
        });

        simpleBlockItem(block.get(), defaultModelY);
    }

    private ModelFile createZAxisModel(String name, ResourceLocation sideTex, ResourceLocation endTex) {
        return models().withExistingParent(name, "block/block")
                .texture("particle", sideTex)
                .texture("side", sideTex)
                .texture("end", endTex)
                .element()
                .from(0, 0, 0).to(16, 16, 16)
                .face(Direction.NORTH).texture("#end").end()
                .face(Direction.SOUTH).texture("#end").end()
                .face(Direction.UP).texture("#side").rotation(ModelBuilder.FaceRotation.CLOCKWISE_90).end()
                .face(Direction.DOWN).texture("#side").rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90).end()
                .face(Direction.WEST).texture("#side").uvs(0, 0, 16, 16).end()
                .face(Direction.EAST).texture("#side").uvs(16, 0, 0, 16).end()
                .end();
    }

    private ModelFile createXAxisModel(String name, ResourceLocation sideTex, ResourceLocation endTex) {
        return models().withExistingParent(name, "block/block")
                .texture("particle", sideTex)
                .texture("side", sideTex)
                .texture("end", endTex)
                .element()
                .from(0, 0, 0).to(16, 16, 16)
                .face(Direction.EAST).texture("#end").end()
                .face(Direction.WEST).texture("#end").end()
                .face(Direction.UP).texture("#side").end()
                .face(Direction.DOWN).texture("#side").end()
                .face(Direction.SOUTH).texture("#side").uvs(0, 0, 16, 16).end()
                .face(Direction.NORTH).texture("#side").uvs(16, 0, 0, 16).end()
                .end();
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        String var10000 = rl.getNamespace();
        String var10001 = rl.getPath();
        return ResourceLocation.fromNamespaceAndPath(var10000, var10001 + suffix);
    }
}