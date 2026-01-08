package com.naterbobber.darkerdepths.data.tags;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.util.DDTags;
import com.naterbobber.darkerdepths.worldgen.DDBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DDBiomeTagsProvider extends BiomeTagsProvider {

    public DDBiomeTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, DarkerDepths.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        DDBiomes.BIOMES.stream().forEach(this::addDefaultOverworldBiomeTags);

        this.tag(Tags.Biomes.IS_HOT_OVERWORLD).addOptional(DDBiomes.MOLTEN_CAVERN.location());
        this.tag(Tags.Biomes.IS_DRY_OVERWORLD).addOptional(DDBiomes.SANDY_CATACOMBS.location());
        this.tag(Tags.Biomes.IS_COLD_OVERWORLD).addOptional(DDBiomes.GLOWSHROOM_FOREST.location());

        this.tag(DDTags.Biomes.CATACOMBS).addOptional(DDBiomes.SANDY_CATACOMBS.location());

        this.tag(DDTags.Biomes.HAS_ROPE_MINE_FOREST).add(
                Biomes.PLAINS,
                Biomes.FOREST,
                Biomes.DARK_FOREST,
                Biomes.BIRCH_FOREST,
                Biomes.JUNGLE,
                Biomes.MANGROVE_SWAMP);

        this.tag(DDTags.Biomes.HAS_ROPE_MINE_DESERT).add(Biomes.DESERT);
    }

    private void addDefaultOverworldBiomeTags(ResourceKey<Biome> biome) {
        this.tag(BiomeTags.HAS_MINESHAFT).addOptional(biome.location());
        this.tag(BiomeTags.HAS_RUINED_PORTAL_STANDARD).addOptional(biome.location());
        this.tag(BiomeTags.STRONGHOLD_BIASED_TO).addOptional(biome.location());
        this.tag(BiomeTags.IS_OVERWORLD).addOptional(biome.location());
        this.tag(Tags.Biomes.IS_UNDERGROUND).addOptional(biome.location());
    }
}