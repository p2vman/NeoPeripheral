package io.p2vman.neoperipheral.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.IPrefSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public abstract class BasePeripheralBlockEntity extends BlockEntity implements IPrefSource {
    public BasePeripheralBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Nullable
    public abstract IPeripheral getPeripheral(@Nullable Direction direction);
}
