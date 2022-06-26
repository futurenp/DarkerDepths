package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDBiomes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class DDBiomeTagsProvider extends BiomeTagsProvider {

    public DDBiomeTagsProvider(DataGenerator p_211094_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_211094_, DarkerDepths.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(BiomeTags.HAS_MINESHAFT).add(DDBiomes.MOLTEN_CAVERN.get(), DDBiomes.SANDY_CATACOMBS.get(), DDBiomes.GLOWSHROOM_FOREST.get());
        this.tag(BiomeTags.HAS_RUINED_PORTAL_STANDARD).add(DDBiomes.MOLTEN_CAVERN.get(), DDBiomes.SANDY_CATACOMBS.get(), DDBiomes.GLOWSHROOM_FOREST.get());
        this.tag(BiomeTags.STRONGHOLD_BIASED_TO).add(DDBiomes.MOLTEN_CAVERN.get(), DDBiomes.SANDY_CATACOMBS.get(), DDBiomes.GLOWSHROOM_FOREST.get());
        this.tag(DDBiomes.MOLTEN_CAVERN, Tags.Biomes.IS_UNDERGROUND, Tags.Biomes.IS_OVERWORLD);
        this.tag(DDBiomes.SANDY_CATACOMBS, Tags.Biomes.IS_UNDERGROUND, Tags.Biomes.IS_OVERWORLD);
        this.tag(DDBiomes.GLOWSHROOM_FOREST, Tags.Biomes.IS_UNDERGROUND, Tags.Biomes.IS_OVERWORLD);
    }

    private void tag(RegistryObject<Biome> biome, TagKey<Biome>... tags) {
        for (TagKey<Biome> key : tags) {
            tag(key).add(biome.getKey());
        }
    }
}
