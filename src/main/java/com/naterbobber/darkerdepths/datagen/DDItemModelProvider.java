package com.naterbobber.darkerdepths.datagen;

import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DDItemModelProvider extends ItemModelProvider {

    public DDItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DarkerDepths.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
