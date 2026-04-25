package io.p2vman.neoperipheral.util;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.IPrefSource;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class PeripheralLazy<T extends IPeripheral> {
    private final Object lock = new Object();
    private T value;
    private Boolean initialized;
    private final PeripheralFactory<T> provider;

    public static <T extends IPeripheral> PeripheralLazy<T> of() {
        return new PeripheralLazy<T>((PeripheralFactory<T>) null);
    }

    public static <T extends IPeripheral> PeripheralLazy<T> of(final T value)
    {
        return new PeripheralLazy<T>(value);
    }

    public static <T extends IPeripheral> PeripheralLazy<T> of(final PeripheralFactory<T> provider)
    {
        return new PeripheralLazy<T>(provider);
    }

    private PeripheralLazy(final T value)
    {
        this.value = value;
        this.initialized = true;
        this.provider = (direction, source) -> value;
    }

    private PeripheralLazy(final PeripheralFactory<T> provider)
    {
        this.value = null;
        this.initialized = false;
        this.provider = provider;
    }

    public T get(@Nullable Direction direction, IPrefSource source)
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
    public interface PeripheralFactory<T extends IPeripheral> {
        @Nullable T createPeripheral(@Nullable Direction direction, IPrefSource source);
    }
}
