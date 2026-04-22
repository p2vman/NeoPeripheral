package io.p2vman.neoperipheral.peripheral.socket;

import io.p2vman.neoperipheral.peripheral.SocketPeripheral;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface ModuleFactory<T extends Module> {
    T create(ItemStack itemStack, SocketPeripheral peripheral);
}
