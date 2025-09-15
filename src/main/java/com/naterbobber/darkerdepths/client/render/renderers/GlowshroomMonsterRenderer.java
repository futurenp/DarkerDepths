package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.GlowshroomMonsterModel;
import com.naterbobber.darkerdepths.client.render.renderers.layers.DDCustomRenderTypeLayer;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class GlowshroomMonsterRenderer extends GeoEntityRenderer<GlowshroomMonsterEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, "textures/entity/glowshroom_monster/glowshroom_monster_glowmask.png");

    public GlowshroomMonsterRenderer(EntityRendererProvider.Context context) {
        super(context, new GlowshroomMonsterModel());
        addRenderLayer(new DDCustomRenderTypeLayer<>(this, RenderType.eyes(TEXTURE)));
    }
}
