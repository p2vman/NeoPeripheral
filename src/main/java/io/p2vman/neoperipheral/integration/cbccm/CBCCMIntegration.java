package io.p2vman.neoperipheral.integration.cbccm;

import com.cubester.cbc_compact_mount.CMEntities;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import io.p2vman.neoperipheral.Config;
import io.p2vman.neoperipheral.integration.Integration;
import io.p2vman.neoperipheral.integration.cbc.CannonMountBlockEntityProxy;
import io.p2vman.neoperipheral.integration.cbccm.peripheral.CompactCannonMountPeripheral;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@Integration(modid = "cbc_compact_mount", depends = "createbigcannons")
public class CBCCMIntegration {
    public CBCCMIntegration(IEventBus modBus) {
        modBus.addListener(CBCCMIntegration::registerCapabilities);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        if (Config._CHEAT_CANNON_MOUNT) {
            event.registerBlockEntity(
                    PeripheralCapability.get(),
                    CMEntities.COMPACT_CANNON_MOUNT.get(),
                    (blockEntity, side) -> ((CannonMountBlockEntityProxy) blockEntity).neoperipheral$getPeripheral(side, CompactCannonMountPeripheral::new)
            );
        }
    }
}
