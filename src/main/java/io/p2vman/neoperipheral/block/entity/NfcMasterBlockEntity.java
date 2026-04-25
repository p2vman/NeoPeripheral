package io.p2vman.neoperipheral.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.peripheral.NfcMasterPeripheral;
import io.p2vman.neoperipheral.util.PeripheralLazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class NfcMasterBlockEntity extends BasePeripheralBlockEntity {
    private final PeripheralLazy<NfcMasterPeripheral> peripheral;
    private ItemStack card = ItemStack.EMPTY;

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

    public ItemStack getCard() {
        return card;
    }

    public void setCard(ItemStack stack) {
        this.card = stack;
        var peripheral = this.peripheral.get();
        if (peripheral != null) {
            peripheral.onCardChanged(stack);
        }
        setChanged();
    }

    public boolean isEmpty() {
        return card.isEmpty();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider p_338445_) {
        super.saveAdditional(tag, p_338445_);
        if (!card.isEmpty()) {
            tag.put("card", card.save(p_338445_, new CompoundTag()));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider p_338445_) {
        super.loadAdditional(tag, p_338445_);
        if (tag.contains("card")) {
            card = ItemStack.parse(p_338445_, tag.getCompound("card")).orElse(ItemStack.EMPTY);
        } else {
            card = ItemStack.EMPTY;
        }
    }
}
