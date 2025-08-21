package com.naterbobber.darkerdepths.client.renderers.layers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.VoidSoulKnightModel;
import com.naterbobber.darkerdepths.entities.VoidSoulKnight;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VoidSoulKnightLayer extends EyesLayer<VoidSoulKnight, VoidSoulKnightModel<VoidSoulKnight>> {
    private static final RenderType VOID_SOUL_KNIGHT = RenderType.eyes(DarkerDepths.id("textures/entity/void_soul_knight/void_soul_knight_emmissive_layer.png"));

    public VoidSoulKnightLayer(RenderLayerParent<VoidSoulKnight, VoidSoulKnightModel<VoidSoulKnight>> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return VOID_SOUL_KNIGHT;
    }
}
