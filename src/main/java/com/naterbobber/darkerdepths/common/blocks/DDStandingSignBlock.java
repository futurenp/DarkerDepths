package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.common.tileentities.DDSignTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class DDStandingSignBlock extends StandingSignBlock {

    public DDStandingSignBlock(Properties properties, WoodType type) {
        super(properties, type);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new DDSignTileEntity();
    }
}
