package io.p2vman.neoperipheral.integration.synaxis;

import com.verr1.synaxis.registry.SynaxisBlockEntities;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import io.p2vman.neoperipheral.integration.IExternalPeripheralHolder;
import io.p2vman.neoperipheral.integration.Integration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@Integration(modid = "synaxis")
public class SynaxisIntegration {
    public SynaxisIntegration(IEventBus modBus) {
        modBus.addListener(SynaxisIntegration::registerCapabilities);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                PeripheralCapability.get(),
                SynaxisBlockEntities.KINETIC_RESISTOR.get(),
                (b, d) -> ((IExternalPeripheralHolder) b).getPeripheral(d)
        );

        event.registerBlockEntity(
                PeripheralCapability.get(),
                SynaxisBlockEntities.DYNAMIC_REVOLUTE_MOTOR.get(),
                (b, d) -> ((IExternalPeripheralHolder) b).getPeripheral(d)
        );

        event.registerBlockEntity(
                PeripheralCapability.get(),
                SynaxisBlockEntities.COMPACT_FLAP.get(),
                (b, d) -> ((IExternalPeripheralHolder) b).getPeripheral(d)
        );

        event.registerBlockEntity(
                PeripheralCapability.get(),
                SynaxisBlockEntities.CAMERA.get(),
                (b, d) -> ((IExternalPeripheralHolder) b).getPeripheral(d)
        );

        event.registerBlockEntity(
                PeripheralCapability.get(),
                SynaxisBlockEntities.JET.get(),
                (b, d) -> ((IExternalPeripheralHolder) b).getPeripheral(d)
        );

        event.registerBlockEntity(
                PeripheralCapability.get(),
                SynaxisBlockEntities.ANCHOR.get(),
                (b, d) -> ((IExternalPeripheralHolder) b).getPeripheral(d)
        );
    }
}
