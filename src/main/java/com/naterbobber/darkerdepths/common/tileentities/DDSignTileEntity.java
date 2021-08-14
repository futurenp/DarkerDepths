package com.naterbobber.darkerdepths.common.tileentities;

import com.naterbobber.darkerdepths.core.registries.DDTileEntities;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class DDSignTileEntity extends SignTileEntity {

    @Override
    public TileEntityType<?> getType() {
        return DDTileEntities.PETRIFIED_SIGN.get();
    }

}