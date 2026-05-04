package io.p2vman.neoperipheral.integration.cbc;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.util.PeripheralLazy;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface CannonMountBlockEntityProxy {
    IPeripheral neoperipheral$getPeripheral(Direction direction, PeripheralLazy.PeripheralFactory<IPeripheral, BlockEntity> factory);
}
