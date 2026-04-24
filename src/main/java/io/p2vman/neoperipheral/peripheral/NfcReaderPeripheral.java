package io.p2vman.neoperipheral.peripheral;

import io.p2vman.neoperipheral.IPrefSource;
import net.minecraft.core.Direction;

public class NfcReaderPeripheral extends BasePeripheral {
    public NfcReaderPeripheral(Direction direction, IPrefSource source) {
        super(source);
    }

    @Override
    public String getType() {
        return "neo_nfc_reader";
    }
}
