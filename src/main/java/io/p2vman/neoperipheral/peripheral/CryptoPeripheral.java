package io.p2vman.neoperipheral.peripheral;

import io.p2vman.neoperipheral.IPrefSource;
import net.minecraft.core.Direction;

public class CryptoPeripheral extends BasePeripheral {
    public CryptoPeripheral(Direction direction, IPrefSource source) {
        super(source);
    }

    @Override
    public String getType() {
        return "neo_crypto";
    }
}
