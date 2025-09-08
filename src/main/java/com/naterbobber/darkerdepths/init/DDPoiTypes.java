package com.naterbobber.darkerdepths.init;

import com.google.common.collect.ImmutableSet;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;



import java.util.Set;

//@Mod.EventBusSubscriber(modid = DarkerDepths.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDPoiTypes {

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, DarkerDepths.MOD_ID);

    public static final DeferredHolder<PoiType, PoiType> DEATH_ANCHOR = POI_TYPES.register("death_anchor", () -> new PoiType(getBlockStates(DDBlocks.DEATH_ANCHOR.get()), 1, 1));

    private static Set<BlockState> getBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }

}
