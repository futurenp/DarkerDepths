package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.tileentities.DDSignTileEntity;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DDTileEntities {
    public static final DeferredRegister<TileEntityType<?>> HELPER = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, DarkerDepths.MODID);

    public static final RegistryObject<TileEntityType<DDSignTileEntity>> DD_SIGN = HELPER.register("dd_sign", () -> TileEntityType.Builder.create(DDSignTileEntity::new, DDBlocks.PETRIFIED_SIGN.get(), DDBlocks.PETRIFIED_WALL_SIGN.get()).build(null));

    public static void register(IEventBus eventBus) {
        HELPER.register(eventBus);
    }

}
