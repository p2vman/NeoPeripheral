package io.p2vman.neoperipheral;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IPrefSource {
    BlockPos getPos();
    Level getLevel();
    BlockEntity getBlockEntity();
    void setLabel(String label);
    String getLabel();
    boolean hasLabel();

    interface IPrefHolder<T extends IPeripheral> {
        T getPref();
    }
}
