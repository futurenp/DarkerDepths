package com.naterbobber.darkerdepths.datagen;

import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DDBlockstateProvider extends BlockStateProvider {

    public DDBlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, DarkerDepths.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
    }
}
