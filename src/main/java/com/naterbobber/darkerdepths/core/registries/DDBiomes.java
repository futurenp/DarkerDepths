package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.world.gen.biome.AbstractCaveBiome;
import com.naterbobber.darkerdepths.common.world.gen.biome.CaveBiome;
import com.naterbobber.darkerdepths.common.world.gen.biome.MoltenCavernBiome;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

//<>

public class DDBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, DarkerDepths.MODID);

    public static final RegistryObject<AbstractCaveBiome> DEFAULT_CAVE  = createBiome("cave", CaveBiome::new);
    public static final RegistryObject<AbstractCaveBiome> MOLTEN_CAVERN = createBiome("molten_cavern", MoltenCavernBiome::new);

    private static <B extends Biome> RegistryObject<B> createBiome(String name, Supplier<B> supplier) {
        return BIOMES.register(name, supplier);
    }

    public static void applyBiomeFeatures() {
        BIOMES.getEntries().forEach((builder) -> {
            Biome biome = builder.get();
            if (biome instanceof AbstractCaveBiome){
                if (biome != null) {
                    ((AbstractCaveBiome)biome).addFeatures();
                }
            }
        });
    }
}