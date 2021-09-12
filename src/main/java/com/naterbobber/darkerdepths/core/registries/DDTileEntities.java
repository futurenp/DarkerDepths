package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.tileentities.DDSignTileEntity;
import com.naterbobber.darkerdepths.common.tileentities.GeyserBlockTileEntity;
import com.naterbobber.darkerdepths.common.tileentities.RepellentTileEntity;
import com.naterbobber.darkerdepths.core.api.Registries;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDTileEntities {
    public static final Registries HELPER = DarkerDepths.REGISTRIES;

    public static final RegistryObject<TileEntityType<DDSignTileEntity>> PETRIFIED_SIGN = HELPER.registerTileEntity("petrified_sign", () -> TileEntityType.Builder.create(DDSignTileEntity::new, DDBlocks.PETRIFIED_SIGN.get(), DDBlocks.PETRIFIED_WALL_SIGN.get()).build(null));
    public static final RegistryObject<TileEntityType<GeyserBlockTileEntity>> GEYSER = HELPER.registerTileEntity("geyser", () -> TileEntityType.Builder.create(GeyserBlockTileEntity::new, DDBlocks.GEYSER.get()).build(null));

}