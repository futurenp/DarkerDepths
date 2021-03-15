package com.naterbobber.darkerdepths.common.tileentities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.List;

//<>

public class HeartChamberTileEntity extends TileEntity implements ITickableTileEntity {
    private List<LivingEntity> entitiesInRange;
    private boolean shouldHeal;
    private int healWarmup;

    public HeartChamberTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
        if (this.shouldHeal) {
            if (this.healWarmup < 40) {
                ++this.healWarmup;
            } else {
                this.healEntities(this.world);
                this.addHealingParticles(this.world);
                this.shouldHeal = false;
            }
        }
    }

    private void healEntities(World worldIn) {
        if (!worldIn.isRemote) {
            this.entitiesInRange.stream().filter(this::isNearbyHealableEntities).forEach(this::heal);
        }
    }

    private void addHealingParticles(World worldIn) {
        if (worldIn.isRemote) {
            BlockPos pos = this.getPos();
            MutableInt mutableInt = new MutableInt(16700985);
            int entitiesInRange = (int)this.entitiesInRange.stream().filter((entities) -> pos.withinDistance(entities.getPositionVec(), 32.0d)).count();
            this.entitiesInRange.stream().filter(this::isNearbyHealableEntities).forEach((p_235655_4_) -> {
                float range = MathHelper.sqrt((p_235655_4_.getPosX() - (double)pos.getX()) * (p_235655_4_.getPosX() - (double)pos.getX()) + (p_235655_4_.getPosZ() - (double)pos.getZ()) * (p_235655_4_.getPosZ() - (double)pos.getZ()));
                double x = (double)((float)pos.getX() + 0.5F) + (double)(1.0F / range) * (p_235655_4_.getPosX() - (double)pos.getX());
                double z = (double)((float)pos.getZ() + 0.5F) + (double)(1.0F / range) * (p_235655_4_.getPosZ() - (double)pos.getZ());
                int index = MathHelper.clamp((entitiesInRange - 21) / -2, 3, 15);

                for(int i = 0; i < index; ++i) {
                    int color = mutableInt.addAndGet(5);
                    double red = (double) ColorHelper.PackedColor.getRed(color) / 255.0D;
                    double green = (double)ColorHelper.PackedColor.getGreen(color) / 255.0D;
                    double blue = (double)ColorHelper.PackedColor.getBlue(color) / 255.0D;
                    worldIn.addParticle(ParticleTypes.ENTITY_EFFECT, x, ((float)pos.getY() + 0.5F), z, red, green, blue);
                }
            });
        }
    }

    private boolean isNearbyHealableEntities(LivingEntity entity) {
        //TODO: create tag for pet entities
        return entity.isAlive() && !entity.removed && this.getPos().withinDistance(entity.getPositionVec(), 32.0d) && entity.getType().equals(EntityType.PLAYER);
    }

    private void heal(LivingEntity entity) {
        entity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 6000));
    }
}