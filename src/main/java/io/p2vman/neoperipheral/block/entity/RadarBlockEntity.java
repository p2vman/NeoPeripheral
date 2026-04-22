package io.p2vman.neoperipheral.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.peripheral.RadarPeripheral;
import io.p2vman.neoperipheral.util.Lazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class RadarBlockEntity extends BasePeripheralBlockEntity implements IPrefSource.IPrefHolder<RadarPeripheral> {
    private final Lazy<RadarPeripheral> peripheral;
    public RadarBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModRegistry.RADAR_BLOCK_ENTITY.get(), pos, blockState);
        this.peripheral = Lazy.of(() -> new RadarPeripheral(this, false));
    }

    @Override
    public BlockPos getPos() {
        return getBlockPos();
    }

    @Override
    public RadarPeripheral getPref() {
        return peripheral.get();
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this;
    }

    @Nullable
    @Override
    public IPeripheral getPeripheral(@Nullable Direction direction) {
        return peripheral.get();
    }
}
