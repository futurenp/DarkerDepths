package com.naterbobber.darkerdepths.common.items;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class DDSpawnEggItem extends SpawnEggItem {
    protected static final List<DDSpawnEggItem> UNADDED_EGGS = new ArrayList<>();
    private final Lazy<? extends EntityType<?>> entityTypeSupplier;

    public DDSpawnEggItem(RegistryObject<? extends EntityType<?>> entityTypeSupplier, int primaryColour, int secondaryColour, Item.Properties properties) {
        super(null, primaryColour, secondaryColour, properties);
        this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
        UNADDED_EGGS.add(this);
    }

    @Override
    public EntityType<?> getType(CompoundNBT nbt) {
        return this.entityTypeSupplier.get();
    }

}
