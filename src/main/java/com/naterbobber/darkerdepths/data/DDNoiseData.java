package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class DDNoiseData {

    public static void bootstrap(BootstrapContext<NormalNoise.NoiseParameters> context) {
        register(context, DDResourceKeys.Noises.CHALK, -7, 1.0, 1.0);
        register(context, DDResourceKeys.Noises.INNER_CHALK, -6, 1.0, 1.0);
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
