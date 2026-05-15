package io.p2vman.neoperipheral.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.peripheral.TriangleRadarPeripheral;
import io.p2vman.neoperipheral.util.PeripheralLazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TriangleRadarBlockEntity extends BasePeripheralBlockEntity {
    private final PeripheralLazy<TriangleRadarPeripheral, IPrefSource> peripheral;

    public TriangleRadarBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModRegistry.TRIANGLE_RADAR_BLOCK_ENTITY.get(), pos, blockState);
        this.peripheral = PeripheralLazy.<TriangleRadarPeripheral, IPrefSource>of(TriangleRadarPeripheral::new);
    }

    @Override
    public @Nullable IPeripheral getPeripheral(@Nullable Direction direction) {
        return peripheral.get(direction, this);
    }
}
