package io.p2vman.neoperipheral.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.peripheral.ModemPeripheral;
import io.p2vman.neoperipheral.util.PeripheralLazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ModemBlockEntity extends BasePeripheralBlockEntity {
    private final PeripheralLazy<ModemPeripheral, IPrefSource> peripheral;
    public ModemBlockEntity(BlockPos pos, BlockState blockState) {
        super(null, pos, blockState);
        this.peripheral = PeripheralLazy.<ModemPeripheral, IPrefSource>of(ModemPeripheral::new);
    }

    @Override
    public @Nullable IPeripheral getPeripheral(@Nullable Direction direction) {
        return peripheral.get(direction, this);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        var per = peripheral.get();
        if (per != null) {

        }
    }
}
