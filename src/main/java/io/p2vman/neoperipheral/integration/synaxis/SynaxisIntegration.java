package io.p2vman.neoperipheral.integration.synaxis;

import com.verr1.synaxis.registry.SynaxisBlockEntities;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import io.p2vman.neoperipheral.integration.IExternalPeripheralHolder;
import io.p2vman.neoperipheral.integration.Integration;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@Integration(modid = "synaxis")
public class SynaxisIntegration {
    private static final ICapabilityProvider<BlockEntity, Direction, IPeripheral> base = (be, d) -> ((IExternalPeripheralHolder) be).getPeripheral(d);
    public SynaxisIntegration(IEventBus modBus) {
        modBus.addListener(SynaxisIntegration::registerCapabilities);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        var cap = PeripheralCapability.get();
        event.registerBlockEntity(
                cap,
                SynaxisBlockEntities.KINETIC_RESISTOR.get(),
                base
        );

        event.registerBlockEntity(
                cap,
                SynaxisBlockEntities.DYNAMIC_REVOLUTE_MOTOR.get(),
                base
        );

        event.registerBlockEntity(
                cap,
                SynaxisBlockEntities.COMPACT_FLAP.get(),
                base
        );

        event.registerBlockEntity(
                cap,
                SynaxisBlockEntities.CAMERA.get(),
                base
        );

        event.registerBlockEntity(
                cap,
                SynaxisBlockEntities.JET.get(),
                base
        );

        event.registerBlockEntity(
                cap,
                SynaxisBlockEntities.ANCHOR.get(),
                base
        );
    }
}
