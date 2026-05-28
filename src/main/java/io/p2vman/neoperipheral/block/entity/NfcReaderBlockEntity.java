package io.p2vman.neoperipheral.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.peripheral.NfcReaderPeripheral;
import io.p2vman.neoperipheral.util.PeripheralLazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class NfcReaderBlockEntity extends BasePeripheralBlockEntity {
    private final PeripheralLazy<NfcReaderPeripheral, IPrefSource> peripheral;
    public NfcReaderBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModRegistry.NFC_READER_BLOCK_ENTITY.get(), pos, blockState);
        this.peripheral = PeripheralLazy.<NfcReaderPeripheral, IPrefSource>of(NfcReaderPeripheral::new);
    }

    @Override
    public @Nullable IPeripheral getPeripheral(@Nullable Direction direction) {
        return peripheral.get(direction, this);
    }

    public void read(ItemStack itemStack) {
        peripheral.ifPresent((peripheral) -> {
            peripheral.read(itemStack);
        });
    }
}
