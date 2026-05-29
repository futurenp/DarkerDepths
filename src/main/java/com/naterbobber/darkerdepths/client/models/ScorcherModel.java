package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.entities.ScorcherEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@OnlyIn(Dist.CLIENT)
public class ScorcherModel extends DefaultedEntityGeoModel<ScorcherEntity> {
    public ScorcherModel() {
        super(DarkerDepths.id("scorcher"), true);
    }

    @Override
    public @Nullable RenderType getRenderType(ScorcherEntity animatable, ResourceLocation texture) {
        return DDRenderTypes.emissiveSolid(texture);
    }
}