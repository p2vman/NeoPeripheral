package io.p2vman.neoperipheral.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.peripheral.EntityRadarPeripheral;
import io.p2vman.neoperipheral.util.PeripheralLazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EntityRadarBlockEntity extends BasePeripheralBlockEntity {
    private final PeripheralLazy<EntityRadarPeripheral, IPrefSource> peripheral;
    public EntityRadarBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModRegistry.ENTITY_RADAR_BLOCK_ENTITY.get(), pos, blockState);
        this.peripheral = PeripheralLazy.<EntityRadarPeripheral, IPrefSource>of(EntityRadarPeripheral::new);
    }

    @Override
    public @Nullable IPeripheral getPeripheral(@Nullable Direction direction) {
        return peripheral.get(direction, this);
    }
}