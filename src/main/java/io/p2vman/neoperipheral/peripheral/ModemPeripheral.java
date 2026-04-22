package io.p2vman.neoperipheral.peripheral;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.IPrefSource;
import org.jetbrains.annotations.Nullable;

public class ModemPeripheral extends BasePeripheral {

    public ModemPeripheral(IPrefSource source) {
        super(source);
    }

    @Override
    public String getType() {
        return "neo_modem";
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return iPeripheral == this;
    }
}
