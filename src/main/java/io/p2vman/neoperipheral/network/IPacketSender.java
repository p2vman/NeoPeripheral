package io.p2vman.neoperipheral.network;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public interface IPacketSender {
    @Nonnull
    Level getLevel();

    @Nonnull
    Vec3 getPosition();

    @Nonnull
    String getSenderID();
}
