package io.p2vman.neoperipheral.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.peripheral.SableEnginePeripheral;
import io.p2vman.neoperipheral.util.PeripheralLazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SableEngineBlockEntity extends BasePeripheralBlockEntity {
    private final PeripheralLazy<SableEnginePeripheral, IPrefSource> peripheral;
    public SableEngineBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModRegistry.SABLE_ENGINE_BLOCK_ENTITY.get(), pos, blockState);
        this.peripheral = PeripheralLazy.<SableEnginePeripheral, IPrefSource>of(SableEnginePeripheral::new);
    }

    @Override
    public @Nullable IPeripheral getPeripheral(@Nullable Direction direction) {
        return peripheral.get(direction, this);
    }

}
