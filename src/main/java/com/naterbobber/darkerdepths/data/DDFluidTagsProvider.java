package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDFluidTags;
import com.naterbobber.darkerdepths.init.DDFluids;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class DDFluidTagsProvider extends FluidTagsProvider {

    public DDFluidTagsProvider(DataGenerator dataGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, DarkerDepths.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(DDFluidTags.AMBER).add(DDFluids.AMBER.get(), DDFluids.FLOWING_AMBER.get());
    }
}
