package com.naterbobber.darkerdepths.client.renderers.layers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.BodySnatcherModel;
import com.naterbobber.darkerdepths.entities.BodySnatcherEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BodySnatcherLayer extends EyesLayer<BodySnatcherEntity, BodySnatcherModel<BodySnatcherEntity>> {
    private static final RenderType BODY_SNATCHER = RenderType.eyes(DarkerDepths.id("textures/entity/body_snatcher/body_snatcher_emissive_layer.png"));

    public BodySnatcherLayer(RenderLayerParent<BodySnatcherEntity, BodySnatcherModel<BodySnatcherEntity>> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return BODY_SNATCHER;
    }
}
