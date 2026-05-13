package io.p2vman.neoperipheral.integration.ctc.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.block.entity.BasePeripheralBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TweakedLinkedControllerHubBlockEntity extends BasePeripheralBlockEntity {
    public TweakedLinkedControllerHubBlockEntity(BlockPos pos, BlockState blockState) {
        super(null, pos, blockState);
    }

    @Override
    public @Nullable IPeripheral getPeripheral(@Nullable Direction direction) {
        return null;
    }

    @Override
    public BlockPos getPos() {
        return null;
    }

    @Override
    public BlockEntity getBlockEntity() {
        return null;
    }
}
