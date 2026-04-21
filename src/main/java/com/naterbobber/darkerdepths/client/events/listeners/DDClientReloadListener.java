package com.naterbobber.darkerdepths.client.events.listeners;

import com.naterbobber.darkerdepths.client.render.EmissiveModelManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DDClientReloadListener extends SimplePreparableReloadListener {
    @Override
    protected Object prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        return null;
    }

    @Override
    protected void apply(Object o, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        EmissiveModelManager.BlockBaker.clearHolders();
    }
}
