package io.p2vman.neoperipheral.util;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;

import java.util.Map;
import java.util.Optional;

public class ArgumentsExtension {
    public static Optional<Map<?, ?>> optTable(IArguments arguments, int index) throws LuaException {
        Object value = arguments.get(index);
        if (value == null) {
            return Optional.empty();
        } else if (!(value instanceof Map)) {
            return Optional.empty();
        } else {
            return Optional.of((Map)value);
        }
    }



}
