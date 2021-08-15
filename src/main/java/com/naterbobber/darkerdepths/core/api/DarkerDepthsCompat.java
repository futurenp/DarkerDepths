package com.naterbobber.darkerdepths.core.api;

import net.minecraftforge.fml.ModList;

//<>

public class DarkerDepthsCompat {
    public static final Boolean QUARK               = ModList.get() != null && ModList.get().getModContainerById("quark").isPresent();
    public static final Boolean CAVES_AND_CLIFFS    = ModList.get() != null && ModList.get().getModContainerById("cavesandcliffs").isPresent();
}