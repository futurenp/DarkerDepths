package com.naterbobber.darkerdepths.data.assets;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.generic.*;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
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

    public DDBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DarkerDepths.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleItem(DDItems.AMBER);
        simpleItem(DDItems.VOID_SOUL_REQUIEM);
        simpleItem(DDItems.GLOW_GRIME);
        simpleItem(DDItems.GLOWSHROOM_CAP);
        simpleItem(DDItems.FORSAKEN_BRONZE_SCRAP);
        simpleItem(DDItems.FORSAKEN_BRONZE_INGOT);

        add(this::woodBlockWithItem, DDBlocks.PETRIFIED_WOOD, DDBlocks.PETRIFIED_LOG);
        add(this::woodBlockWithItem, DDBlocks.STRIPPED_PETRIFIED_WOOD, DDBlocks.STRIPPED_PETRIFIED_LOG);

        skip(
                DDBlocks.VOID_SOUL_JAR,
                DDBlocks.DEATH_ANCHOR,
                DDBlocks.AMBER_CLUSTER,
                DDBlocks.DEATH_ANCHOR,
                DDBlocks.AMBER_CLUSTER,
                DDBlocks.ASH,
                DDBlocks.CRYSTAL_MELON,
                DDBlocks.DEAD_LIVING_CRYSTAL,
                DDBlocks.GEYSER,
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

        autoGenerateBlockAssets();
    }

    @SafeVarargs
    private void skip(DeferredHolder<Block, ? extends Block>... blockHolders) {
        Arrays.stream(blockHolders).forEach(blockHolder -> blockIgnores.add(blockHolder.get()));
    }

    private void add(Consumer<DeferredHolder<Block, ? extends Block>> function, DeferredHolder<Block, ? extends Block> block) {
        blockIgnores.add(block.get());
        function.accept(block);
    }

    private void add(BiConsumer<DeferredHolder<Block, ? extends Block>, DeferredHolder<Block, ? extends Block>> function, DeferredHolder<Block, ? extends Block> block, DeferredHolder<Block, ? extends Block> parentBlock) {
        blockIgnores.add(block.get());
        function.accept(block, parentBlock);
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
                        case ConnectedPillarBlock b -> connectedPillarBlockWithItem(holder);
                        case SignBlock b -> skip(holder);
                        case VerticalSlabBlock b -> skip(holder);
                        case WoodPostBlock b -> skip(holder);
                        default -> simpleBlockWithItem(holder);
                }
            });

    }

    private void simpleItem(DeferredHolder<Item, ? extends Item> item) {
        itemModels().withExistingParent(item.getId().getPath(), "item/generated")
                .texture("layer0", modLoc("item/" + item.getId().getPath()));
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

    private void rotatablePillarBlockWithItem(DeferredHolder<Block, ? extends Block> blockHolder) {
        RotatedPillarBlock block = (RotatedPillarBlock) blockHolder.get();
        String blockName = block.getName().getString();

        if(blockName.contains("wood")) {
            return;
        } else {
            logBlockWithItem(blockHolder);
        }
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
            ConnectedPillarBlock.PillarState type = state.getValue(ConnectedPillarBlock.PILLAR_STATE);

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
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(DarkerDepths.MOD_ID, "block/" + logTextureSource.getId().getPath());
        axisBlock((RotatedPillarBlock) block.get(), texture, texture);
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void crossBlockWithItem(DeferredHolder<Block, ? extends Block> block) {
        simpleBlock(block.get(), models().cross(block.getId().getPath(), blockTexture(block.get())).renderType("cutout"));
        itemModels().withExistingParent(block.getId().getPath(), "item/generated")
                .texture("layer0", blockTexture(block.get()));
    }
}