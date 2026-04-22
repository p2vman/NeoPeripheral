package io.p2vman.neoperipheral.peripheral.socket;

import io.p2vman.neoperipheral.item.ModuleItem;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class ModuleLookup {
    private static final Object2ObjectMap<ModuleItem, ModuleFactory<Module>> lookup = new Object2ObjectOpenHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends ModuleItem> ModuleFactory<Module> lookup(T item) {
        return lookup.get(item);
    }

    public static <T extends ModuleItem> void register(T module, ModuleFactory<Module> moduleFactory) {
        lookup.put(module, moduleFactory);
    }
}