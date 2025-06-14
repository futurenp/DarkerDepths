package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.data.loot.packs.VanillaEntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Stream;

public class DDEntityLoot extends VanillaEntityLoot {

    @Override
    public void generate() {
        this.add(DDEntityTypes.GLOWSHROOM_MONSTER.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(DDItems.GLOWSHROOM_CAP.get()))).withPool(LootPool.lootPool().add(LootItem.lootTableItem(DDItems.GLOW_GRIME.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))).withPool(LootPool.lootPool().add(LootItem.lootTableItem(DDBlocks.GRIMESTONE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F))))));
        this.add(DDEntityTypes.BODY_SNATCHER.get(), LootTable.lootTable());
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return DDEntityTypes.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
    }

}
