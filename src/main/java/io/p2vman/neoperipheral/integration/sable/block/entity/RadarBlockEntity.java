package io.p2vman.neoperipheral.integration.sable.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.block.entity.BasePeripheralBlockEntity;
import io.p2vman.neoperipheral.integration.sable.SableIntegration;
import io.p2vman.neoperipheral.integration.sable.peripheral.RadarPeripheral;
import io.p2vman.neoperipheral.util.Lazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class RadarBlockEntity extends BasePeripheralBlockEntity implements IPrefSource.IPrefHolder<RadarPeripheral> {
    private final Lazy<RadarPeripheral> peripheral;
    public RadarBlockEntity(BlockPos pos, BlockState blockState) {
        super(SableIntegration.RADAR_BLOCK_ENTITY.get(), pos, blockState);
        this.peripheral = Lazy.of(() -> new RadarPeripheral(this, false));
    }

    @Override
    public RadarPeripheral getPref() {
        return peripheral.get();
    }

    @Nullable
    @Override
    public IPeripheral getPeripheral(@Nullable Direction direction) {
        return peripheral.get();
    }
}
