package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDBiomes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class DDBiomeTagsProvider extends BiomeTagsProvider {

    public DDBiomeTagsProvider(DataGenerator p_211094_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_211094_, DarkerDepths.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(BiomeTags.HAS_MINESHAFT).add(DDBiomes.MOLTEN_CAVERN.get(), DDBiomes.SANDY_CATACOMBS.get(), DDBiomes.GLOWSHROOM_FOREST.get());
        this.tag(BiomeTags.HAS_RUINED_PORTAL_STANDARD).add(DDBiomes.MOLTEN_CAVERN.get(), DDBiomes.SANDY_CATACOMBS.get(), DDBiomes.GLOWSHROOM_FOREST.get());
        this.tag(BiomeTags.HAS_STRONGHOLD).add(DDBiomes.MOLTEN_CAVERN.get(), DDBiomes.SANDY_CATACOMBS.get(), DDBiomes.GLOWSHROOM_FOREST.get());
    }

}