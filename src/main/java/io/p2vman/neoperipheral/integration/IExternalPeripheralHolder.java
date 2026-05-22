package io.p2vman.neoperipheral.integration;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.Direction;

import javax.annotation.Nullable;

public interface IExternalPeripheralHolder {
    @Nullable IPeripheral getPeripheral(@Nullable Direction direction);
}
