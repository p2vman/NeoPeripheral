package io.p2vman.neoperipheral.peripheral;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.IPrefSource;
import org.jetbrains.annotations.Nullable;

public abstract class BasePeripheral implements IPeripheral {
    protected final IPrefSource source;
    public BasePeripheral(IPrefSource source) {
        this.source = source;
    }

    public IPrefSource getSource() {
        return source;
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return iPeripheral == this;
    }
}
