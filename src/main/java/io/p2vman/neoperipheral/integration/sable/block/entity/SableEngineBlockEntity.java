package io.p2vman.neoperipheral.integration.sable.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.block.entity.BasePeripheralBlockEntity;
import io.p2vman.neoperipheral.integration.sable.SableIntegration;
import io.p2vman.neoperipheral.integration.sable.peripheral.SableEnginePeripheral;
import io.p2vman.neoperipheral.util.PeripheralLazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SableEngineBlockEntity extends BasePeripheralBlockEntity {
    private final PeripheralLazy<SableEnginePeripheral, IPrefSource> peripheral;
    public SableEngineBlockEntity(BlockPos pos, BlockState blockState) {
        super(SableIntegration.SABLE_ENGINE_BLOCK_ENTITY.get(), pos, blockState);
        this.peripheral = PeripheralLazy.<SableEnginePeripheral, IPrefSource>of(SableEnginePeripheral::new);
    }

    @Override
    public @Nullable IPeripheral getPeripheral(@Nullable Direction direction) {
        return peripheral.get(direction, this);
    }

    public void tick() {
        var per = peripheral.get();
        if (per != null) {
            per.tick();
        }
    }
}
