package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

import java.util.function.Function;

public class DDDefaultEntityModel<T extends GeoEntity> extends DefaultedEntityGeoModel<T> {
    protected Function<ResourceLocation, RenderType> renderTypeFactory;

    protected <E extends Entity> DDDefaultEntityModel(DeferredHolder<EntityType<?>, EntityType<E>> entityType) {
        super(DarkerDepths.id(entityType.get().toShortString()), true);
    }

    protected <E extends Entity> DDDefaultEntityModel(DeferredHolder<EntityType<?>, EntityType<E>> entityType, Function<ResourceLocation, RenderType> renderTypeFactory) {
        this(entityType);
        this.renderTypeFactory = renderTypeFactory;
    }

    public static <T extends GeoEntity, E extends Entity> DDDefaultEntityModel<T> withRenderType(DeferredHolder<EntityType<?>, EntityType<E>> entityType, Function<ResourceLocation, RenderType> renderTypeFactory) {
        return new DDDefaultEntityModel<>(entityType, renderTypeFactory);
    }

    public static <T extends GeoEntity, E extends Entity> DDDefaultEntityModel<T> of(DeferredHolder<EntityType<?>, EntityType<E>> entityType) {
        return new DDDefaultEntityModel<>(entityType);
    }

    protected DDDefaultEntityModel(String location) {
        super(DarkerDepths.id(location), true);
    }

    protected DDDefaultEntityModel(String location, Function<ResourceLocation, RenderType> renderTypeFactory) {
        this(location);
        this.renderTypeFactory = renderTypeFactory;
    }

    public static <T extends GeoEntity> DDDefaultEntityModel<T> withStringLocRenderType(String location, Function<ResourceLocation, RenderType> renderTypeFactory) {
        return new DDDefaultEntityModel<>(location, renderTypeFactory);
    }

    public static <T extends GeoEntity> DDDefaultEntityModel<T> withStringLoc(String location) {
        return new DDDefaultEntityModel<>(location);
    }

    @Override
    public @Nullable RenderType getRenderType(T animatable, ResourceLocation texture) {
        if(renderTypeFactory == null) {
            return super.getRenderType(animatable, texture);
        }
        return renderTypeFactory.apply(texture);
    }

    @Override
    public ResourceLocation buildFormattedTexturePath(ResourceLocation basePath) {
        return basePath.withPath("textures/" + subtype() + "/" + basePath.getPath() + "/" + basePath.getPath() + ".png");
    }
}
