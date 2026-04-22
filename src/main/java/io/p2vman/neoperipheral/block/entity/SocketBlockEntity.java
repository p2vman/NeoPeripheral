package io.p2vman.neoperipheral.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.peripheral.SocketPeripheral;
import io.p2vman.neoperipheral.util.Lazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;


public class SocketBlockEntity extends BasePeripheralBlockEntity implements IPrefSource.IPrefHolder<SocketPeripheral> {
    private final Lazy<SocketPeripheral> peripheral;
    private final NonNullList<ItemStack> moduleItems = NonNullList.withSize(4, ItemStack.EMPTY);

    public SocketBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModRegistry.SOCKET_BLOCK_ENTITY.get(), pos, blockState);
        this.peripheral = Lazy.of(() -> {
            var pref = new SocketPeripheral(this);
            for (int i = 0; i < moduleItems.size(); i++) {
                var stack = moduleItems.get(i);
                if (stack.isEmpty()) continue;
                pref.addModuleItem(i, stack);
            }
            return pref;
        });
    }

    @Override
    public @Nullable IPeripheral getPeripheral(@Nullable Direction direction) {
        return this.peripheral.get();
    }

    @Override
    public BlockPos getPos() {
        return getBlockPos();
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this;
    }

    @Override
    public SocketPeripheral getPref() {
        return peripheral.get();
    }

    public NonNullList<ItemStack> getModuleItems() {
        return moduleItems;
    }

    public ItemStack getModuleItem(int slot) {
        if (slot < 0 || slot >= moduleItems.size()) return ItemStack.EMPTY;
        return moduleItems.get(slot);
    }

    public void setModuleItem(int slot, ItemStack stack) {
        if (slot < 0 || slot >= moduleItems.size()) return;
        moduleItems.set(slot, stack);
        setChanged();
        peripheral.get().addModuleItem(slot, stack);
    }

    public ItemStack removeModuleItem(int slot) {
        if (slot < 0 || slot >= moduleItems.size()) return ItemStack.EMPTY;
        ItemStack prev = moduleItems.get(slot);
        if (prev.isEmpty()) return ItemStack.EMPTY;
        moduleItems.set(slot, ItemStack.EMPTY);
        setChanged();
        peripheral.get().removeModuleItem(slot);
        return prev;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);
        ContainerHelper.saveAllItems(tag, moduleItems, provider);
        return tag;
    }

    @Override
    public @Nullable ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(CompoundTag p_187471_, HolderLookup.Provider p_323635_) {
        super.saveAdditional(p_187471_, p_323635_);
        ContainerHelper.saveAllItems(p_187471_, moduleItems, p_323635_);
    }

    @Override
    protected void loadAdditional(CompoundTag p_338466_, HolderLookup.Provider p_338445_) {
        super.loadAdditional(p_338466_, p_338445_);
        for (int i = 0; i < moduleItems.size(); i++) moduleItems.set(i, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(p_338466_, moduleItems, p_338445_);

    }
}
