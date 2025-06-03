package com.naterbobber.darkerdepths.blocks;

import net.minecraft.Util; // Import this
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.registries.ForgeRegistries; // Import this

public class WallVoidSoulTorchBlock extends WallTorchBlock {
    private String descriptionId;

    public WallVoidSoulTorchBlock(BlockBehaviour.Properties properties, ParticleOptions particleOptions) {
        super(properties, particleOptions);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }

    @Override
    public String getDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("block", ForgeRegistries.BLOCKS.getKey(this));
        }
        return this.descriptionId;
    }
}