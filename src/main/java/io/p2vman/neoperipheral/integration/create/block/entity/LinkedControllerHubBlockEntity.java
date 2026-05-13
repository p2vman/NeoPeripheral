package io.p2vman.neoperipheral.integration.create.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.block.entity.BasePeripheralBlockEntity;
import io.p2vman.neoperipheral.integration.create.CreateIntegration;
import io.p2vman.neoperipheral.integration.create.peripheral.LinkedControllerHubPeripheral;
import io.p2vman.neoperipheral.util.Lazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class LinkedControllerHubBlockEntity extends BasePeripheralBlockEntity {
    public final Lazy<LinkedControllerHubPeripheral> peripheral;
    public LinkedControllerHubBlockEntity(BlockPos pos, BlockState blockState) {
        super(CreateIntegration.LINKED_CONTROLLER_HUB_BLOCK_ENTITY.get(), pos, blockState);
        this.peripheral = Lazy.of(() -> {
            return new LinkedControllerHubPeripheral(this);
        });
    }

    @Override
    public @Nullable IPeripheral getPeripheral(@Nullable Direction direction) {
        return peripheral.get();
    }

    @Override
    public BlockPos getPos() {
        return worldPosition;
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this;
    }
}
