package io.p2vman.neoperipheral.integration.drivebywire;

import io.p2vman.neoperipheral.integration.Integration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@Integration(modid = "drivebywire")
public class DriveByWireIntegration {
    public DriveByWireIntegration(IEventBus modBus) {
        modBus.addListener(DriveByWireIntegration::registerCapabilities);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

    }
}
