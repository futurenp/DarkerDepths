package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DDDamageTypeTagsProvider extends TagsProvider<DamageType> {
    public DDDamageTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.DAMAGE_TYPE, pProvider, DarkerDepths.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(DamageTypeTags.BYPASSES_INVULNERABILITY).add(DDDamageTypes.SOUL_BINDING_DAMAGE);
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(DDDamageTypes.SOUL_BINDING_DAMAGE);
        this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(DDDamageTypes.SOUL_BINDING_DAMAGE);
        this.tag(DamageTypeTags.BYPASSES_RESISTANCE).add(DDDamageTypes.SOUL_BINDING_DAMAGE);
        this.tag(DamageTypeTags.BYPASSES_EFFECTS).add(DDDamageTypes.SOUL_BINDING_DAMAGE);
    }
}