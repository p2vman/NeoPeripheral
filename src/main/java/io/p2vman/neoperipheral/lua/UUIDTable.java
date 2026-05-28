package io.p2vman.neoperipheral.lua;

import dan200.computercraft.api.lua.LuaTable;
import io.p2vman.neoperipheral.util.SimpleEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.squiddev.cobalt.LuaNumber;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class UUIDTable implements LuaTable<Integer, Object> {
    private @Nullable UUID uuid;
    public UUIDTable(@Nullable UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public int size() {
        return 4;
    }

    @Override
    public boolean isEmpty() {
        return uuid == null;
    }

    @Override
    public boolean containsKey(Object key) {
        if (!(key instanceof LuaNumber number)) return false;
        var in = number.toInteger();
        return in >= 1 && in < 5;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        if (uuid == null) return null;
        if (!(key instanceof LuaNumber number)) return  null;
        var in = number.toInteger();

        var least = uuid.getLeastSignificantBits();
        var most = uuid.getMostSignificantBits();
        if (in == 1) {
            return (int) (least & 0xFFFFFFFFL);
        } else if (in == 2) {
            return (int) (least >>> 32);
        } else if (in == 3) {
            return (int) (most & 0xFFFFFFFFL);
        } else if (in == 4) {
            return (int) (most >>> 32);
        }
        return null;
    }

    @Override
    public @NotNull Set<Integer> keySet() {
        if (uuid == null) return Collections.emptySet();
        return Set.of(1, 2, 3, 4);
    }

    @Override
    public @NotNull Collection<Object> values() {
        if (uuid == null) return Set.of();
        var least = uuid.getLeastSignificantBits();
        var most = uuid.getMostSignificantBits();
        return Set.of((int) (least & 0xFFFFFFFFL), (int) (least >>> 32), (int) (most & 0xFFFFFFFFL), (int) (most >>> 32));
    }

    @Override
    public @NotNull Set<Entry<Integer, Object>> entrySet() {
        if (uuid == null) return Set.of();
        var least = uuid.getLeastSignificantBits();
        var most = uuid.getMostSignificantBits();
        return Set.of(
                new SimpleEntry<>(1, (int) (least & 0xFFFFFFFFL)),
                new SimpleEntry<>(2, (int) (least >>> 32)),
                new SimpleEntry<>(3, (int) (most & 0xFFFFFFFFL)),
                new SimpleEntry<>(4, (int) (most >>> 32))
        );
    }

    @Override
    public @Nullable Object put(Integer o, Object o2) {
        throw new UnsupportedOperationException("read-only");
    }
}
