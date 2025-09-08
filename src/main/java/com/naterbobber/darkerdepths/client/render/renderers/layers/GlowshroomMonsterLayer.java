package com.naterbobber.darkerdepths.client.render.renderers.layers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.GlowshroomMonsterModel;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


@Deprecated
//convert to geo model
@OnlyIn(Dist.CLIENT)
public class GlowshroomMonsterLayer extends EyesLayer<GlowshroomMonsterEntity, GlowshroomMonsterModel<GlowshroomMonsterEntity>> {
    private static final RenderType GLOWSHROOM_MONSTER = RenderType.eyes(DarkerDepths.id("textures/entity/glowshroom_monster/glowshroom_monster_glowmask.png"));

    public GlowshroomMonsterLayer(RenderLayerParent<GlowshroomMonsterEntity, GlowshroomMonsterModel<GlowshroomMonsterEntity>> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return GLOWSHROOM_MONSTER;
    }
}
