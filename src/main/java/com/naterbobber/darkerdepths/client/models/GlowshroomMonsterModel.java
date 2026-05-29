package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@OnlyIn(Dist.CLIENT)
public class GlowshroomMonsterModel extends DefaultedEntityGeoModel<GlowshroomMonsterEntity> {
    public GlowshroomMonsterModel() {
        super(DarkerDepths.id("glowshroom_monster"), true);
    }

    @Override
    public ResourceLocation getTextureResource(GlowshroomMonsterEntity glowshroomMonsterEntity) {
        return DarkerDepths.id("textures/entity/glowshroom_monster/glowshroom_monster.png");
    }

    @Override
    public @Nullable RenderType getRenderType(GlowshroomMonsterEntity animatable, ResourceLocation texture) {
        return RenderType.entityCutout(texture);
    }
}