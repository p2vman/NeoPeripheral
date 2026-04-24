package io.p2vman.neoperipheral.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.peripheral.NfcMasterPeripheral;
import io.p2vman.neoperipheral.util.PeripheralLazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class NfcMasterBlockEntity extends BasePeripheralBlockEntity {
    private final PeripheralLazy<NfcMasterPeripheral> peripheral;
    public NfcMasterBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModRegistry.NFC_MASTER_BLOCK_ENTITY.get(), pos, blockState);
        this.peripheral = PeripheralLazy.<NfcMasterPeripheral>of(NfcMasterPeripheral::new);
    }

    @Override
    public @Nullable IPeripheral getPeripheral(@Nullable Direction direction) {
        return peripheral.get(direction, this);
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
