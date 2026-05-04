package io.p2vman.neoperipheral.integration.cbc;

import dan200.computercraft.api.peripheral.PeripheralCapability;
import io.p2vman.neoperipheral.Config;
import io.p2vman.neoperipheral.integration.Integration;
import io.p2vman.neoperipheral.integration.cbc.peripheral.CannonMountPeripheral;
import io.p2vman.neoperipheral.integration.cbc.peripheral.FixedCannonMountPeripheral;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import rbasamoyai.createbigcannons.index.CBCBlockEntities;

@Integration(modid = "createbigcannons")
public class CreateBigCannonsIntegration {
    public CreateBigCannonsIntegration(IEventBus modBus) {
        modBus.addListener(CreateBigCannonsIntegration::registerCapabilities);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        if (Config._CHEAT_CANNON_MOUNT) {
            event.registerBlockEntity(
                    PeripheralCapability.get(),
                    CBCBlockEntities.CANNON_MOUNT.get(),
                    (blockEntity, side) -> ((CannonMountBlockEntityProxy) blockEntity).neoperipheral$getPeripheral(side, CannonMountPeripheral::new)
            );

            event.registerBlockEntity(
                    PeripheralCapability.get(),
                    CBCBlockEntities.FIXED_CANNON_MOUNT.get(),
                    (blockEntity, side) -> ((CannonMountBlockEntityProxy) blockEntity).neoperipheral$getPeripheral(side, FixedCannonMountPeripheral::new)
            );
        }
    }
}
