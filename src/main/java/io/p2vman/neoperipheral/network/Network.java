package io.p2vman.neoperipheral.network;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

public class Network implements INetwork {
    public ObjectList<IPacketReceiver> receivers = ObjectLists.synchronize(new ObjectArrayList<>());

    @Override
    public void addReceiver(@NotNull IPacketReceiver receiver) {
        receivers.add(receiver);
    }

    @Override
    public void removeReceiver(@NotNull IPacketReceiver receiver) {
        receivers.remove(receiver);
    }

    @Override
    public void transmit(@NotNull NetworkPacket packet) {
        Objects.requireNonNull(packet);
        for (IPacketReceiver receiver : receivers) {
            receiver.receive(packet);
        }
    }

    @Override
    public @NotNull Set<IDomainNameHandler> getDomainHandlers() {
        return Set.of();
    }
}
