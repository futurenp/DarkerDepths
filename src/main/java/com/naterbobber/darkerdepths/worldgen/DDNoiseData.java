package com.naterbobber.darkerdepths.worldgen;

import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class DDNoiseData {

    public static void bootstrap(BootstrapContext<NormalNoise.NoiseParameters> context) {
        register(context, DDResourceKeys.Noises.MAGMA, -6, 0.4);
        register(context, DDResourceKeys.Noises.TUFF, -6, 1.0);
        register(context, DDResourceKeys.Noises.DUSKROCK, -5, 1.2);
        register(context, DDResourceKeys.Noises.GRIMESTONE_GLIST, -5, 1.2);
        register(context, DDResourceKeys.Noises.GRIMESTONE_MOSSY, -4, 2.0);
        register(context, DDResourceKeys.Noises.GLIST_GRIMESTONE, -6, 2.0);

    }

    private static void register(
            BootstrapContext<NormalNoise.NoiseParameters> context,
            ResourceKey<NormalNoise.NoiseParameters> key,
            int firstOctave,
            double amplitude,
            double... otherAmplitudes
    ) {
        context.register(key, new NormalNoise.NoiseParameters(firstOctave, amplitude, otherAmplitudes));
    }
}
