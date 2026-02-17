package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.item.GlowshroomCapItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class GlowshroomCapRenderer extends GeoArmorRenderer<GlowshroomCapItem> {

    public GlowshroomCapRenderer() {
        super(new DefaultedItemGeoModel<>(DarkerDepths.id("armor/glowshroom_cap")));
    }

    @Override
    public RenderType getRenderType(GlowshroomCapItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return DDRenderTypes.EMISSIVE_SOLID(texture);
    }
}