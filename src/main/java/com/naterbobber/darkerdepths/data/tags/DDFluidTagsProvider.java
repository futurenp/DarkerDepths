package com.naterbobber.darkerdepths.data.tags;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DDFluidTagsProvider extends FluidTagsProvider {
    public DDFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, DarkerDepths.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(DDTags.Fluids.VERY_HIGH_HEAT)
                .addTag(FluidTags.LAVA);

        this.tag(DDTags.Fluids.HEAT_PROVIDER)
                .addOptionalTag(DDTags.Fluids.VERY_HIGH_HEAT)
                .addOptionalTag(DDTags.Fluids.HIGH_HEAT)
                .addOptionalTag(DDTags.Fluids.MEDIUM_HEAT)
                .addOptionalTag(DDTags.Fluids.LOW_HEAT);
    }
}
