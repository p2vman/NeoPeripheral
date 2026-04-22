package io.p2vman.neoperipheral.peripheral.socket;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public interface Module {
    void onConnect();

    void onDisconnect();

    void onSave(CompoundTag tag, HolderLookup.Provider provider);

    void onLoad(CompoundTag tag, HolderLookup.Provider provider);

    void onModuleConnect(Module module, int slot);

    void onModuleDisconnect(Module module, int slot);

    String getType();
}
