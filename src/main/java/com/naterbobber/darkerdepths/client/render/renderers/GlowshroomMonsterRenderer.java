package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.GlowshroomMonsterModel;
import com.naterbobber.darkerdepths.client.render.renderers.layers.GlowshroomMonsterLayer;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.init.DDModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GlowshroomMonsterRenderer extends MobRenderer<GlowshroomMonsterEntity, GlowshroomMonsterModel<GlowshroomMonsterEntity>> {
    public static final ResourceLocation TEXTURE = DarkerDepths.id("textures/entity/glowshroom_monster/glowshroom_monster.png");

    public GlowshroomMonsterRenderer(EntityRendererProvider.Context context) {
        super(context, new GlowshroomMonsterModel<>(context.bakeLayer(DDModelLayers.GLOWSHROOM_MONSTER)), 0.8F);
        this.addLayer(new GlowshroomMonsterLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(GlowshroomMonsterEntity entity) {
        return TEXTURE;
    }
}
