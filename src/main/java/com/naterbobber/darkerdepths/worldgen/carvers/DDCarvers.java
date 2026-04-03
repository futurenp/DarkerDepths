package com.naterbobber.darkerdepths.worldgen.carvers;

import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDWorldCarvers;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

import static com.naterbobber.darkerdepths.util.DDResourceKeys.ConfiguredWorldCarvers.*;

public class DDCarvers {

    public static void bootstrap(BootstrapContext<ConfiguredWorldCarver<?>> context) {
        HolderGetter<Block> holdergetter = context.lookup(Registries.BLOCK);

        context.register(
                DUSKROCK_CARVER,
                DDWorldCarvers.BLOCK_CARVER.get()
                        .configured(
                                new BlockCarverConfiguration(
                                        1F,
                                        UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180)),
                                        UniformFloat.of(0.1F, 0.9F), VerticalAnchor.aboveBottom(8),
                                        CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()),
                                        holdergetter.getOrThrow(DDTags.Blocks.CATACOMBS_STRIPE_REPLACEABLE),
                                        UniformFloat.of(0.5F, 0.8F),
                                        UniformFloat.of(1.4F, 1.8F),
                                        UniformFloat.of(-1.0F, -0.4F),
                                        DDBlocks.DUSKROCK.get()
                                )
                        )
        );

        context.register(
                MAGMA_CARVER,
                DDWorldCarvers.BLOCK_CARVER.get()
                        .configured(
                                new BlockCarverConfiguration(
                                        0.65F,
                                        UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180)),
                                        UniformFloat.of(0.1F, 0.9F), VerticalAnchor.aboveBottom(8),
                                        CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()),
                                        holdergetter.getOrThrow(DDTags.Blocks.MAGMA_STRIPE_REPLACEABLE),
                                        UniformFloat.of(0.4F, 0.5F),
                                        UniformFloat.of(1.2F, 1.5F),
                                        UniformFloat.of(-1.0F, -0.4F),
                                        Blocks.MAGMA_BLOCK
                                )
                        )
        );

        context.register(
                TUFF_CARVER,
                DDWorldCarvers.BLOCK_CARVER.get()
                        .configured(
                                new BlockCarverConfiguration(
                                        0.5F,
                                        UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180)),
                                        UniformFloat.of(0.1F, 0.9F), VerticalAnchor.aboveBottom(8),
                                        CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()),
                                        holdergetter.getOrThrow(DDTags.Blocks.MAGMA_STRIPE_REPLACEABLE),
                                        UniformFloat.of(0.8F, 1.2F),
                                        UniformFloat.of(1.8F, 2.4F),
                                        UniformFloat.of(-1.0F, -0.4F),
                                        Blocks.TUFF
                                )
                        )
        );

        context.register(
                MAGMA_LINE_CARVER,
                DDWorldCarvers.CURTAIN_CARVER.get()
                        .configured(
                                new BlockCarverConfiguration(
                                        0.02F,
                                        UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180)),
                                        UniformFloat.of(0.1F, 0.9F), VerticalAnchor.aboveBottom(8),
                                        CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()),
                                        holdergetter.getOrThrow(DDTags.Blocks.MAGMA_STRIPE_REPLACEABLE),
                                        UniformFloat.of(0.4F, 0.5F),
                                        UniformFloat.of(1.2F, 1.5F),
                                        UniformFloat.of(-1.0F, -0.4F),
                                        Blocks.MAGMA_BLOCK
                                )
                        )
        );
    }
}
