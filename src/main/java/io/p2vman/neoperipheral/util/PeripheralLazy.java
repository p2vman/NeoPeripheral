package io.p2vman.neoperipheral.util;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.IPrefSource;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class PeripheralLazy<T extends IPeripheral, S> {
    private final Object lock = new Object();
    private T value;
    private Boolean initialized;
    private final PeripheralFactory<T, S> provider;

    public static <T extends IPeripheral, S> PeripheralLazy<T, S> of() {
        return new PeripheralLazy<T, S>((PeripheralFactory<T, S>) null);
    }

    public static <T extends IPeripheral, S> PeripheralLazy<T, S> of(final T value)
    {
        return new PeripheralLazy<T, S>(value);
    }

    public static <T extends IPeripheral, S> PeripheralLazy<T, S> of(final PeripheralFactory<T, S> provider)
    {
        return new PeripheralLazy<T, S>(provider);
    }

    private PeripheralLazy(final T value)
    {
        this.value = value;
        this.initialized = true;
        this.provider = (direction, source) -> value;
    }

    private PeripheralLazy(final PeripheralFactory<T, S> provider)
    {
        this.value = null;
        this.initialized = false;
        this.provider = provider;
    }

    public T get(@Nullable Direction direction, S source)
    {
        synchronized (lock) {
            if (!initialized && provider != null) {
                initialized = true;
                this.value = provider.createPeripheral(direction, source);
            }

            return value;
        }
    }

    public @Nullable T get()
    {
        synchronized (lock) {
            return value;
        }
    }

    public void ifPresent(final Consumer<T> consumer) {
        synchronized (lock) {
            if (!initialized)
                return;

            consumer.accept(this.value);
        }
    }

    public T orElse(T elseValue) {
        synchronized (lock) {
            if (!initialized)
                return elseValue;

            return value;
        }
    }

    public T orElseGet(Supplier<T> elseSupplier) {
        synchronized (lock) {
            if (!initialized)
                return elseSupplier.get();

            return value;
        }
    }

    public T orElseThrow() {
        synchronized (lock) {
            if (!initialized)
                throw new NullPointerException();

            return value;
        }
    }

    @FunctionalInterface
    public interface PeripheralFactory<T extends IPeripheral, S> {
        @Nullable T createPeripheral(@Nullable Direction direction, S source);
    }
}
