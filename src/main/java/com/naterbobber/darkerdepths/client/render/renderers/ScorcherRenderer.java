package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.client.models.ScorcherModel;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.entities.ScorcherEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class ScorcherRenderer extends GeoEntityRenderer<ScorcherEntity> {
    public ScorcherRenderer(EntityRendererProvider.Context context) {
        super(context, new ScorcherModel());
    }

    @Override
    public @Nullable RenderType getRenderType(ScorcherEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return DDRenderTypes.EMISSIVE_TRANSPARENT(this.getTextureLocation(animatable));
    }

    @Override
    public Color getRenderColor(ScorcherEntity animatable, float partialTick, int packedLight) {
        if (animatable.hurtTime > 0 || !animatable.isAlive()) {
            return Color.ofRGBA(255, 180, 180, 255);
        }

        return Color.WHITE;
    }
}