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

public class CreativeRadarBlockEntity extends BasePeripheralBlockEntity implements IPrefSource, IPrefSource.IPrefHolder<RadarPeripheral> {
    private Lazy<RadarPeripheral> radarPeripheral;
    public CreativeRadarBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModRegistry.CREATIVE_RADAR_BLOCK_ENTITY.get(), pos, blockState);
        this.radarPeripheral = Lazy.of(() -> new RadarPeripheral(this, true));
    }

    @Override
    public BlockPos getPos() {
        return getBlockPos();
    }

    @Override
    public RadarPeripheral getPref() {
        return radarPeripheral.get();
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this;
    }

    @Nullable
    public IPeripheral getPeripheral(@Nullable Direction direction) {
        return radarPeripheral.get();
    }
}
