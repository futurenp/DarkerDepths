package com.naterbobber.darkerdepths.init;

import com.google.common.collect.ImmutableSet;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDPoiTypes {

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, DarkerDepths.MODID);

    public static final RegistryObject<PoiType> DEATH_ANCHOR = POI_TYPES.register("death_anchor", () -> new PoiType(getBlockStates(DDBlocks.DEATH_ANCHOR.get()), 1, 1));

    private static Set<BlockState> getBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }

}
