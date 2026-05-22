package io.p2vman.neoperipheral.network;

import javax.annotation.Nonnull;
import java.util.Set;

public interface INetwork {
    void addReceiver(@Nonnull IPacketReceiver var1);

    void removeReceiver(@Nonnull IPacketReceiver var1);

    void transmit(@Nonnull NetworkPacket var1);

    @Nonnull
    Set<IDomainNameHandler> getDomainHandlers();
}
