package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.client.models.DDDefaultEntityModel;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.entities.ScorcherEntity;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.Color;

@OnlyIn(Dist.CLIENT)
public class ScorcherRenderer extends GeoEntityRenderer<ScorcherEntity> {
    public ScorcherRenderer(EntityRendererProvider.Context context) {
        super(context, DDDefaultEntityModel.withRenderType(DDEntityTypes.SCORCHER, DDRenderTypes::emissiveSolid));
    }

    @Override
    public Color getRenderColor(ScorcherEntity animatable, float partialTick, int packedLight) {
        if (animatable.hurtTime > 0 || !animatable.isAlive()) {
            return Color.ofRGBA(255, 180, 180, 255);
        }

        return Color.WHITE;
    }
}