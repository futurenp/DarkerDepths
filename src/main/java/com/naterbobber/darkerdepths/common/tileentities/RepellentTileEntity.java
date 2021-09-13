package com.naterbobber.darkerdepths.common.tileentities;

import com.naterbobber.darkerdepths.core.registries.DDTileEntities;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class RepellentTileEntity extends TileEntity implements ITickableTileEntity {
    private int activeTicks;

    public RepellentTileEntity() {
        super(DDTileEntities.REPELLENT.get());
    }

    @Override
    public void tick() {
        if (world != null) {
            if (this.world.isRemote()) {
                this.activeTicks++;
            }
            List<MonsterEntity> list = this.getWorld().getEntitiesWithinAABB(MonsterEntity.class, new AxisAlignedBB(this.getPos()).grow(6.0D));
            for (MonsterEntity monsters : list) {
                Vector3d vector3d = Vector3d.copyCenteredHorizontally(this.getPos());
                Vector3d avoidVec = RandomPositionGenerator.findRandomTargetBlockAwayFrom(monsters, 16, 7, vector3d);
                if (avoidVec == null) {
                    return;
                }
                monsters.getNavigator().clearPath();
                monsters.getNavigator().tryMoveToXYZ(avoidVec.x, avoidVec.y, avoidVec.z, 1.2D);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getActiveRotation(float tickDelta) {
        return (this.activeTicks + tickDelta) * -0.0375F;
    }

}
