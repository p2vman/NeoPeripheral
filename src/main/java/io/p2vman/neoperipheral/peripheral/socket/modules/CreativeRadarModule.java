package io.p2vman.neoperipheral.peripheral.socket.modules;


import io.p2vman.neoperipheral.peripheral.SocketPeripheral;
import net.minecraft.world.item.ItemStack;

public class CreativeRadarModule extends RadarModule {
    public CreativeRadarModule(ItemStack itemStack, SocketPeripheral peripheral) {
        super(itemStack, peripheral, true);
    }
}
