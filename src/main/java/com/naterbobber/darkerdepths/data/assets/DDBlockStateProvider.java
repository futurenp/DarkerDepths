package com.naterbobber.darkerdepths.data.assets;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.ConnectedPillarBlock;
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
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DDBlockStateProvider extends BlockStateProvider {
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

        simpleBlockWithItem(DDBlocks.AMBER_BLOCK);
        logBlockWithItem(DDBlocks.ARID_DEEPSLATE);
        simpleBlockWithItem(DDBlocks.GLOWSHROOM_BLOCK);
        logBlockWithItem(DDBlocks.GLOWSHROOM_STEM);
        simpleBlockWithItem(DDBlocks.LIVING_CRYSTAL);
        logBlockWithItem(DDBlocks.POROUS_PETRIFIED_LOG);
        simpleBlockWithItem(DDBlocks.ASH_BLOCK);
        simpleBlockWithItem(DDBlocks.FORSAKEN_BRONZE_BLOCK);
        simpleBlock(DDBlocks.MOB_PLACER.get());

        crossBlockWithItem(DDBlocks.MOSSY_SPROUTS);
        crossBlockWithItem(DDBlocks.DRY_SPROUTS);

        logBlockSet(DDBlocks.ARIDROCK,
                DDBlocks.ARIDROCK_STAIRS,
                DDBlocks.ARIDROCK_SLAB,
                DDBlocks.ARIDROCK_WALL);
        simpleBlockSet(DDBlocks.POLISHED_ARIDROCK,
                DDBlocks.POLISHED_ARIDROCK_STAIRS,
                DDBlocks.POLISHED_ARIDROCK_SLAB);
        simpleBlockSet(DDBlocks.ARIDROCK_BRICKS,
                DDBlocks.ARIDROCK_BRICKS_STAIRS,
                DDBlocks.ARIDROCK_BRICKS_SLAB,
                DDBlocks.ARIDROCK_BRICKS_WALL);
        simpleBlockWithItem(DDBlocks.CHISELED_ARIDROCK_BRICKS);
        simpleBlockWithItem(DDBlocks.CRACKED_ARIDROCK_BRICKS);

        simpleBlockSet(DDBlocks.DUSKROCK,
                DDBlocks.DUSKROCK_STAIRS,
                DDBlocks.DUSKROCK_SLAB,
                DDBlocks.DUSKROCK_WALL);
        simpleBlockSet(DDBlocks.POLISHED_DUSKROCK,
                DDBlocks.POLISHED_DUSKROCK_STAIRS,
                DDBlocks.POLISHED_DUSKROCK_SLAB);
        simpleBlockSet(DDBlocks.DUSKROCK_BRICKS,
                DDBlocks.DUSKROCK_BRICKS_STAIRS,
                DDBlocks.DUSKROCK_BRICKS_SLAB,
                DDBlocks.DUSKROCK_BRICKS_WALL);
        simpleBlockWithItem(DDBlocks.CHISELED_DUSKROCK_BRICKS);
        simpleBlockWithItem(DDBlocks.CRACKED_DUSKROCK_BRICKS);

        logBlockSet(DDBlocks.GRIMESTONE,
                DDBlocks.GRIMESTONE_STAIRS,
                DDBlocks.GRIMESTONE_SLAB,
                DDBlocks.GRIMESTONE_WALL);
        simpleBlockSet(DDBlocks.POLISHED_GRIMESTONE,
                DDBlocks.POLISHED_GRIMESTONE_STAIRS,
                DDBlocks.POLISHED_GRIMESTONE_SLAB);
        simpleBlockSet(DDBlocks.GRIMESTONE_BRICKS,
                DDBlocks.GRIMESTONE_BRICKS_STAIRS,
                DDBlocks.GRIMESTONE_BRICKS_SLAB,
                DDBlocks.GRIMESTONE_BRICKS_WALL);
        simpleBlockWithItem(DDBlocks.CHISELED_GRIMESTONE_BRICKS);
        simpleBlockWithItem(DDBlocks.CRACKED_GRIMESTONE_BRICKS);

        logBlockSet(DDBlocks.DARKSLATE,
                DDBlocks.DARKSLATE_STAIRS,
                DDBlocks.DARKSLATE_SLAB,
                DDBlocks.DARKSLATE_WALL);
        simpleBlockSet(DDBlocks.POLISHED_DARKSLATE,
                DDBlocks.POLISHED_DARKSLATE_STAIRS,
                DDBlocks.POLISHED_DARKSLATE_SLAB);
        simpleBlockSet(DDBlocks.DARKSLATE_BRICKS,
                DDBlocks.DARKSLATE_BRICKS_STAIRS,
                DDBlocks.DARKSLATE_BRICKS_SLAB,
                DDBlocks.DARKSLATE_BRICKS_WALL);
        simpleBlockWithItem(DDBlocks.CHISELED_DARKSLATE_BRICKS);
        simpleBlockWithItem(DDBlocks.CRACKED_DARKSLATE_BRICKS);

        simpleWoodTypeSet(
                DDBlocks.PETRIFIED_PLANKS,
                DDBlocks.PETRIFIED_STAIRS,
                DDBlocks.PETRIFIED_SLAB,
                DDBlocks.PETRIFIED_FENCE,
                DDBlocks.PETRIFIED_FENCE_GATE,
                DDBlocks.PETRIFIED_DOOR,
                DDBlocks.PETRIFIED_TRAPDOOR,
                DDBlocks.PETRIFIED_PRESSURE_PLATE,
                DDBlocks.PETRIFIED_BUTTON,
                DDBlocks.PETRIFIED_LOG,
                DDBlocks.PETRIFIED_WOOD,
                DDBlocks.STRIPPED_PETRIFIED_LOG,
                DDBlocks.STRIPPED_PETRIFIED_WOOD
        );

        connectedPillarBlockWithItem(DDBlocks.ARIDROCK_PILLAR);
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

    private void stairsBlockWithItem(DeferredHolder<Block, ? extends Block> block, DeferredHolder<Block, ? extends Block> parentTexture) {
        stairsBlock((StairBlock) block.get(), blockTexture(parentTexture.get()));
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void slabBlockWithItem(DeferredHolder<Block, ? extends Block> block, DeferredHolder<Block, ? extends Block> parentTexture) {
        slabBlock((SlabBlock) block.get(), blockTexture(parentTexture.get()), blockTexture(parentTexture.get()));
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void wallBlockWithItem(DeferredHolder<Block, ? extends Block> block, DeferredHolder<Block, ? extends Block> parentTexture) {
        wallBlock((WallBlock) block.get(), blockTexture(parentTexture.get()));
        itemModels().wallInventory(block.getId().getPath(), blockTexture(parentTexture.get()));
    }

    private void fenceBlockWithItem(DeferredHolder<Block, ? extends Block> block, DeferredHolder<Block, ? extends Block> parentTexture) {
        fenceBlock((FenceBlock) block.get(), blockTexture(parentTexture.get()));
        itemModels().fenceInventory(block.getId().getPath(), blockTexture(parentTexture.get()));
    }

    private void fenceGateBlockWithItem(DeferredHolder<Block, ? extends Block> block, DeferredHolder<Block, ? extends Block> parentTexture) {
        fenceGateBlock((FenceGateBlock) block.get(), blockTexture(parentTexture.get()));
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
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
        ResourceLocation topTexture = location.withPath("block/" + blockName + "_top");
        ResourceLocation sideTexture = location.withPath("block/" + blockName + "_side");
        ResourceLocation sideLowerTexture = location.withPath("block/" + blockName + "_side_lower");
        ResourceLocation sideMiddleTexture = location.withPath("block/" + blockName + "_side_middle");
        ResourceLocation sideUpperTexture = location.withPath("block/" + blockName + "_side_upper");

        ModelFile defaultModel = models().cubeColumn(blockName + "_default", sideTexture, topTexture);
        ModelFile lowerModel = models().cubeColumn(blockName + "_lower", sideLowerTexture, topTexture);
        ModelFile middleModel = models().cubeColumn(blockName + "_middle", sideMiddleTexture, topTexture);
        ModelFile upperModel = models().cubeColumn(blockName + "_upper", sideUpperTexture, topTexture);

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

    private void pressurePlateBlockWithItem(DeferredHolder<Block, ? extends Block> block, DeferredHolder<Block, ? extends Block> parentTexture) {
        pressurePlateBlock((PressurePlateBlock) block.get(), blockTexture(parentTexture.get()));
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void buttonBlockWithItem(DeferredHolder<Block, ? extends Block> block, DeferredHolder<Block, ? extends Block> parentTexture) {
        buttonBlock((ButtonBlock) block.get(), blockTexture(parentTexture.get()));
        itemModels().buttonInventory(block.getId().getPath(), blockTexture(parentTexture.get()));
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



    private void simpleBlockSet(DeferredHolder<Block, ? extends Block> baseBlock,
                                DeferredHolder<Block, ? extends Block> stairsBlock,
                                DeferredHolder<Block, ? extends Block> slabBlock,
                                DeferredHolder<Block, ? extends Block> wallBlock)
    {
        simpleBlockWithItem(baseBlock);
        stairsBlockWithItem(stairsBlock, baseBlock);
        slabBlockWithItem(slabBlock, baseBlock);
        wallBlockWithItem(wallBlock, baseBlock);
    }

    private void simpleBlockSet(DeferredHolder<Block, ? extends Block> baseBlock,
                                DeferredHolder<Block, ? extends Block> stairsBlock,
                                DeferredHolder<Block, ? extends Block> slabBlock)
    {
        simpleBlockWithItem(baseBlock);
        stairsBlockWithItem(stairsBlock, baseBlock);
        slabBlockWithItem(slabBlock, baseBlock);
    }

    private void logBlockSet(DeferredHolder<Block, ? extends Block> baseBlock,
                             DeferredHolder<Block, ? extends Block> stairsBlock,
                             DeferredHolder<Block, ? extends Block> slabBlock,
                             DeferredHolder<Block, ? extends Block> wallBlock)
    {
        logBlockWithItem(baseBlock);
        stairsBlockWithItem(stairsBlock, baseBlock);
        slabBlockWithItem(slabBlock, baseBlock);
        wallBlockWithItem(wallBlock, baseBlock);
    }

    private void simpleWoodTypeSet(DeferredHolder<Block, ? extends Block> plankBlock,
                      DeferredHolder<Block, ? extends Block> stairsBlock,
                      DeferredHolder<Block, ? extends Block> slabBlock,
                      DeferredHolder<Block, ? extends Block> fenceBlock,
                      DeferredHolder<Block, ? extends Block> fenceGateBlock,
                      DeferredHolder<Block, ? extends Block> doorBlock,
                      DeferredHolder<Block, ? extends Block> trapdoorBlock,
                      DeferredHolder<Block, ? extends Block> pressurePlateBlock,
                      DeferredHolder<Block, ? extends Block> buttonBlock,
                      DeferredHolder<Block, ? extends Block> logBlock,
                      DeferredHolder<Block, ? extends Block> woodBlock,
                      DeferredHolder<Block, ? extends Block> strippedLogBlock,
                      DeferredHolder<Block, ? extends Block> strippedWoodBlock
                      )
    {
        simpleBlockWithItem(plankBlock);
        stairsBlockWithItem(stairsBlock, plankBlock);
        slabBlockWithItem(slabBlock, plankBlock);
        fenceBlockWithItem(fenceBlock, plankBlock);
        fenceGateBlockWithItem(fenceGateBlock, plankBlock);
        doorBlockWithItem(doorBlock);
        trapdoorBlockWithItem(trapdoorBlock);
        pressurePlateBlockWithItem(pressurePlateBlock, plankBlock);
        buttonBlockWithItem(buttonBlock, plankBlock);
        logBlockWithItem(logBlock);
        woodBlockWithItem(woodBlock, logBlock);
        logBlockWithItem(strippedLogBlock);
        woodBlockWithItem(strippedWoodBlock, strippedLogBlock);
    }
}