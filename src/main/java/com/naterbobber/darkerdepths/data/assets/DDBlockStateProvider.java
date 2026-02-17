package com.naterbobber.darkerdepths.data.assets;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

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
    }

    private void simpleItem(RegistryObject<Item> item) {
        itemModels().withExistingParent(item.getId().getPath(), "item/generated")
                .texture("layer0", modLoc("item/" + item.getId().getPath()));
    }

    private void simpleBlockWithItem(RegistryObject<Block> block) {
        simpleBlock(block.get());
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void logBlockWithItem(RegistryObject<Block> block) {
        logBlock((RotatedPillarBlock) block.get());
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void stairsBlockWithItem(RegistryObject<Block> block, RegistryObject<Block> parentTexture) {
        stairsBlock((StairBlock) block.get(), blockTexture(parentTexture.get()));
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void slabBlockWithItem(RegistryObject<Block> block, RegistryObject<Block> parentTexture) {
        slabBlock((SlabBlock) block.get(), blockTexture(parentTexture.get()), blockTexture(parentTexture.get()));
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

//    private void verticalSlabBlockWithItem(RegistryObject<Block> block, RegistryObject<Block> parentTexture) {
//        slabBlock((SlabBlock) block.get(), blockTexture(parentTexture.get()), blockTexture(parentTexture.get()));
//        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
//    }

    private void wallBlockWithItem(RegistryObject<Block> block, RegistryObject<Block> parentTexture) {
        wallBlock((WallBlock) block.get(), blockTexture(parentTexture.get()));
        itemModels().wallInventory(block.getId().getPath(), blockTexture(parentTexture.get()));
    }

    private void fenceBlockWithItem(RegistryObject<Block> block, RegistryObject<Block> parentTexture) {
        fenceBlock((FenceBlock) block.get(), blockTexture(parentTexture.get()));
        itemModels().fenceInventory(block.getId().getPath(), blockTexture(parentTexture.get()));
    }

    private void fenceGateBlockWithItem(RegistryObject<Block> block, RegistryObject<Block> parentTexture) {
        fenceGateBlock((FenceGateBlock) block.get(), blockTexture(parentTexture.get()));
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void doorBlockWithItem(RegistryObject<Block> block) {
        ResourceLocation location = block.getId();

        ResourceLocation bottomTexture = location.withPath("block/" + location.getPath() + "_bottom");
        ResourceLocation topTexture = location.withPath("block/" + location.getPath() + "_top");

        doorBlock((DoorBlock) block.get(), bottomTexture, topTexture);

        ResourceLocation itemTexture = location.withPath("item/" + location.getPath());
        itemModels().withExistingParent(block.getId().getPath(), "item/generated")
                .texture("layer0", itemTexture);
    }

    private void trapdoorBlockWithItem(RegistryObject<Block> block) {
        ResourceLocation location = block.getId();
        String path = "block/" + location.getPath();
        trapdoorBlock((TrapDoorBlock) block.get(), modLoc(path), true);
        simpleBlockItem(block.get(), models().getExistingFile(modLoc(path + "_bottom")));
    }

    private void pressurePlateBlockWithItem(RegistryObject<Block> block, RegistryObject<Block> parentTexture) {
        pressurePlateBlock((PressurePlateBlock) block.get(), blockTexture(parentTexture.get()));
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void buttonBlockWithItem(RegistryObject<Block> block, RegistryObject<Block> parentTexture) {
        buttonBlock((ButtonBlock) block.get(), blockTexture(parentTexture.get()));
        itemModels().buttonInventory(block.getId().getPath(), blockTexture(parentTexture.get()));
    }

    private void woodBlockWithItem(RegistryObject<Block> block, RegistryObject<Block> logTextureSource) {
        ResourceLocation texture = DarkerDepths.id("block/" + logTextureSource.getId().getPath());
        axisBlock((RotatedPillarBlock) block.get(), texture, texture);
        simpleBlockItem(block.get(), models().getExistingFile(blockTexture(block.get())));
    }

    private void crossBlockWithItem(RegistryObject<Block> block) {
        simpleBlock(block.get(), models().cross(block.getId().getPath(), blockTexture(block.get())).renderType("cutout"));
        itemModels().withExistingParent(block.getId().getPath(), "item/generated")
                .texture("layer0", blockTexture(block.get()));
    }



    private void simpleBlockSet(RegistryObject<Block> baseBlock,
                                RegistryObject<Block> stairsBlock,
                                RegistryObject<Block> slabBlock,
                                RegistryObject<Block> wallBlock)
    {
        simpleBlockWithItem(baseBlock);
        stairsBlockWithItem(stairsBlock, baseBlock);
        slabBlockWithItem(slabBlock, baseBlock);
        wallBlockWithItem(wallBlock, baseBlock);
    }

    private void simpleBlockSet(RegistryObject<Block> baseBlock,
                                RegistryObject<Block> stairsBlock,
                                RegistryObject<Block> slabBlock)
    {
        simpleBlockWithItem(baseBlock);
        stairsBlockWithItem(stairsBlock, baseBlock);
        slabBlockWithItem(slabBlock, baseBlock);
    }

    private void logBlockSet(RegistryObject<Block> baseBlock,
                             RegistryObject<Block> stairsBlock,
                             RegistryObject<Block> slabBlock,
                             RegistryObject<Block> wallBlock)
    {
        logBlockWithItem(baseBlock);
        stairsBlockWithItem(stairsBlock, baseBlock);
        slabBlockWithItem(slabBlock, baseBlock);
        wallBlockWithItem(wallBlock, baseBlock);
    }

    private void simpleWoodTypeSet(RegistryObject<Block> plankBlock,
                      RegistryObject<Block> stairsBlock,
                      RegistryObject<Block> slabBlock,
                      RegistryObject<Block> fenceBlock,
                      RegistryObject<Block> fenceGateBlock,
                      RegistryObject<Block> doorBlock,
                      RegistryObject<Block> trapdoorBlock,
                      RegistryObject<Block> pressurePlateBlock,
                      RegistryObject<Block> buttonBlock,
                      RegistryObject<Block> logBlock,
                      RegistryObject<Block> woodBlock,
                      RegistryObject<Block> strippedLogBlock,
                      RegistryObject<Block> strippedWoodBlock
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