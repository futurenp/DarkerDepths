package com.naterbobber.darkerdepths.worldgen.retrogen;

import com.naterbobber.darkerdepths.worldgen.retrogen.processors.HeatPropagationProcessor;
import java.util.ArrayList;
import java.util.List;

public class ChunkPostProcessManager {
    public static final List<IChunkPostProcessor> PROCESSORS = new ArrayList<>();

    static {
        PROCESSORS.add(new HeatPropagationProcessor());
    }
}