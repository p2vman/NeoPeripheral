package io.p2vman.neoperipheral.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.item.ModuleItem;
import io.p2vman.neoperipheral.peripheral.socket.Module;
import io.p2vman.neoperipheral.peripheral.socket.ModuleLookup;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class SocketPeripheral extends BasePeripheral {
    public final Module[] modules = new Module[4];
    public SocketPeripheral(IPrefSource source) {
        super(source);
    }

    @Override
    public String getType() {
        return "neo_socket";
    }

    public void addModuleItem(int slot, ItemStack stack) {
        if (!(stack.getItem() instanceof ModuleItem item)) return;
        var factory = ModuleLookup.lookup(item);
        if (factory == null) return;
        var module = factory.create(stack, this);
        module.onConnect();
        for (Module module1 : modules) {
            if (module1 == null || module1 == module) continue;
            module1.onModuleConnect(module, slot);
        }
        modules[slot] = module;
    }

    public void removeModuleItem(int slot) {
       var module = modules[slot];
       if (module == null) return;
       modules[slot] = null;
        for (Module module1 : modules) {
            if (module1 == null || module1 == module) continue;
            module1.onModuleDisconnect(module, slot);
        }
       module.onDisconnect();
    }

    @LuaFunction(value = {"findModule", "FindModule", "find", "Find"})
    public MethodResult findModule(String module_type) {
        for (Module module1 : modules) {
            if (module1 == null) continue;
            if (!Objects.equals(module1.getType(), module_type)) continue;
            return MethodResult.of(module1);
        }
        return MethodResult.of(null);
    }

    @LuaFunction(value = {"hashModule", "HashModule", "hash", "Hash"})
    public boolean hashModule(int slot) {
        if (slot < 0 || slot >= modules.length) return false;
        return modules[slot] != null;
    }

    @LuaFunction(value = {"getModule", "GetModule", "get", "Get"})
    public MethodResult getModule(int slot) {
        if (slot < 0 || slot >= modules.length) return MethodResult.of(null);
        if (modules[slot] == null) return MethodResult.of(null);
        return MethodResult.of(modules[slot]);
    }
}
