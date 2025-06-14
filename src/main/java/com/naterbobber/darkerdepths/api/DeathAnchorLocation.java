package com.naterbobber.darkerdepths.api;

import net.minecraft.core.GlobalPos;

import java.util.Optional;

public interface DeathAnchorLocation {

    void setDeathAnchorLocation(Optional<GlobalPos> globalPos);

    Optional<GlobalPos> getDeathAnchorLocation();

}
