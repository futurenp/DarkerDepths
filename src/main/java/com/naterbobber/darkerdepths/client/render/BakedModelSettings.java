package com.naterbobber.darkerdepths.client.render;

import net.minecraft.client.resources.model.BakedModel;
import net.neoforged.neoforge.client.model.BakedModelWrapper;

public interface BakedModelSettings {
    BakedModelWrapper<? extends BakedModel> model(BakedModel originalModel);
}
