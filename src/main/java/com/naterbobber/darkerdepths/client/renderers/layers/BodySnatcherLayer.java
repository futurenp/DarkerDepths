package com.naterbobber.darkerdepths.client.renderers.layers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.BodySnatcherModel;
import com.naterbobber.darkerdepths.entities.BodySnatcher;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BodySnatcherLayer extends EyesLayer<BodySnatcher, BodySnatcherModel<BodySnatcher>> {
    private static final RenderType BODY_SNATCHER = RenderType.eyes(DarkerDepths.id("textures/entity/body_snatcher/body_snatcher_emissive_layer.png"));

    public BodySnatcherLayer(RenderLayerParent<BodySnatcher, BodySnatcherModel<BodySnatcher>> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return BODY_SNATCHER;
    }
}
