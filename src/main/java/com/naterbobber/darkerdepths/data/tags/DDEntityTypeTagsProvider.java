package com.naterbobber.darkerdepths.data.tags;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DDEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public DDEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, DarkerDepths.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(DDTags.EntityTypes.GLOWSHROOM_MONSTER_TARGET).add(
                EntityType.ZOMBIE,
                EntityType.HUSK,
                EntityType.ZOMBIE_HORSE,
                EntityType.ZOMBIE_VILLAGER,
                EntityType.ZOMBIFIED_PIGLIN
        ).addTag(EntityTypeTags.SKELETONS);

        this.tag(DDTags.EntityTypes.VOID_SOUL).add(
                DDEntityTypes.VOID_SOUL_KNIGHT.get(),
                DDEntityTypes.VOID_SOUL.get(),
                DDEntityTypes.BODY_SNATCHER.get()
        );
    }
}
