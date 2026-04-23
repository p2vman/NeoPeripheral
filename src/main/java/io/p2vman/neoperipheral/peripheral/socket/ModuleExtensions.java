package io.p2vman.neoperipheral.peripheral.socket;

import dan200.computercraft.api.lua.LuaException;

public class ModuleExtensions {
    public static void checkModuleEnabled(AbstractModule module) throws LuaException {
        if  (!module.isEnabled()) throw new LuaException("Module is not enabled");
    }
}
