package io.p2vman.neoperipheral.peripheral.socket.modules;

import io.p2vman.neoperipheral.peripheral.SocketPeripheral;
import io.p2vman.neoperipheral.peripheral.socket.AbstractModule;
import io.p2vman.neoperipheral.peripheral.socket.Module;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class CryptoModule extends AbstractModule {
    public CryptoModule(ItemStack itemStack, SocketPeripheral peripheral) {

    }
    @Override
    public void onSave(CompoundTag tag, HolderLookup.Provider provider) {

    }

    @Override
    public void onLoad(CompoundTag tag, HolderLookup.Provider provider) {

    }

    @Override
    public void onModuleConnect(Module module, int slot) {

    }

    @Override
    public void onModuleDisconnect(Module module, int slot) {

    }

    @Override
    public String getType() {
        return "";
    }
}
