package io.p2vman.neoperipheral.network;

import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public interface IPacketReceiver {
    @Nonnull
    Level getLevel();

    void receive(@Nonnull NetworkPacket var1);
}
