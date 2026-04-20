package io.p2vman.neoperipheral.lua;

import dan200.computercraft.api.lua.LuaTable;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;

public class TableArray implements LuaTable<Integer, Object> {
    private final Int2ObjectMap<Object> list;

    public TableArray() {
        this.list = new Int2ObjectArrayMap<>();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return list.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return list.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return list.get(key);
    }

    @Override
    public @NotNull Set<Integer> keySet() {
        return list.keySet();
    }

    @Override
    public @NotNull Collection<Object> values() {
        return list.values();
    }

    @Override
    public @NotNull Set<Entry<Integer, Object>> entrySet() {
        return list.entrySet();
    }

    @Override
    public @Nullable Object put(Integer o, Object o2) {
        return list.put(o, o2);
    }
}
