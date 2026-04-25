package io.p2vman.neoperipheral.peripheral;

import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import it.unimi.dsi.fastutil.objects.ObjectList;

import javax.annotation.Nullable;

public interface IPeripheralAttacher extends IPeripheral {
    ObjectList<IComputerAccess> getAttachedComputers();

    default void attach(IComputerAccess computer) {
        getAttachedComputers().add(computer);
    }

    default void detach(IComputerAccess computer) {
        getAttachedComputers().remove(computer);
    }

    default void queueEvent(String var1, @Nullable Object... var2) {
        for (IComputerAccess computer : getAttachedComputers()) {
            computer.queueEvent(var1, var2);
        }
    }
}
