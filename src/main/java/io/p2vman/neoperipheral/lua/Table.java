package io.p2vman.neoperipheral.lua;

import dan200.computercraft.api.lua.LuaTable;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Table implements LuaTable<Object, Object> {
    private final Map<Object, Object> map;

    public Table() {
        this(new Object2ObjectOpenHashMap<>());
    }
    public Table(Map<Object, Object> map) {
        this.map = map;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return map.get(key);
    }

    @Override
    public @NotNull Set<Object> keySet() {
        return map.keySet();
    }

    @Override
    public @NotNull Collection<Object> values() {
        return map.values();
    }

    @Override
    public @NotNull Set<Entry<Object, Object>> entrySet() {
        return map.entrySet();
    }

    @Override
    public @Nullable Object put(Object o, Object o2) {
        return map.put(o, o2);
    }
}
