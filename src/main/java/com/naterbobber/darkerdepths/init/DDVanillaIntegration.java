package com.naterbobber.darkerdepths.init;

import com.google.common.collect.Maps;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;

public class DDVanillaIntegration {

    public static void init() {
        registerStrippable(DDBlocks.PETRIFIED_LOG.get(), DDBlocks.STRIPPED_PETRIFIED_LOG.get());
        registerStrippable(DDBlocks.PETRIFIED_WOOD.get(), DDBlocks.STRIPPED_PETRIFIED_WOOD.get());
        registerStrippable(DDBlocks.PETRIFIED_POST.get(), DDBlocks.STRIPPED_PETRIFIED_POST.get());

        registerFlammables(DDBlocks.ROPE.get(), 60, 100);
        registerFlammables(DDBlocks.PETRIFIED_ROOTS.get(), 60, 100);
        registerFlammables(DDBlocks.DRY_SPROUTS.get(), 60, 100);

        registerCompostable(0.65F, DDBlocks.GLOWSHROOM.get().asItem());
        registerCompostable(0.85F, DDBlocks.GLOWSPURS.get().asItem());
        registerCompostable(0.5F,  DDBlocks.GLIMMERING_VINES.get().asItem());
        registerCompostable(0.65F, DDBlocks.MOSSY_SPROUTS.get().asItem());
        registerCompostable(0.3F,  DDBlocks.PETRIFIED_ROOTS.get().asItem());
    }

    private static void registerStrippable(Block unstrippedBlock, Block strippedBlock) {
        AxeItem.STRIPPABLES = Maps.newHashMap(AxeItem.STRIPPABLES);
        AxeItem.STRIPPABLES.put(unstrippedBlock, strippedBlock);
    }

    private static void registerFlammables(Block block, int encouragement, int flammability) {
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        fireBlock.setFlammable(block, encouragement, flammability);
    }

    private static void registerCompostable(float chance, Item item) {
        ComposterBlock.add(chance, item);
    }

}
