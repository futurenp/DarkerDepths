package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.world.gen.biome.CrystalCaveBiome;
import com.naterbobber.darkerdepths.common.world.gen.biome.MoltenCaveBiome;
import com.naterbobber.darkerdepths.common.world.gen.biome.SandyCatacombsBiome;
import com.naterbobber.darkerdepths.core.DDRegistryHelper;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDBiomes {
    public static final DDRegistryHelper HELPER = DarkerDepths.REGISTRY_HELPER;

    public static final RegistryObject<Biome> MOLTEN_CAVERN 	= HELPER.registerBiome("molten_cavern", () -> new MoltenCaveBiome().build());
    public static final RegistryObject<Biome> SANDY_CATACOMBS 	= HELPER.registerBiome("sandy_catacombs", () -> new SandyCatacombsBiome().build());
    public static final RegistryObject<Biome> CRYSTAL_CAVE 	    = HELPER.registerBiome("crystal_cave", () -> new CrystalCaveBiome().build());
}