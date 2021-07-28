package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.tileentities.DDSignTileEntity;
import com.naterbobber.darkerdepths.core.CoreRegistries;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDTileEntities {

    public static final CoreRegistries HELPER = DarkerDepths.REGISTRIES;

    public static final RegistryObject<TileEntityType<DDSignTileEntity>> PETRIFIED_SIGN = HELPER.registerTileEntity("petrified_sign", () -> TileEntityType.Builder.create(DDSignTileEntity::new, DDBlocks.PETRIFIED_SIGN.get(), DDBlocks.PETRIFIED_WALL_SIGN.get()).build(null));

}
