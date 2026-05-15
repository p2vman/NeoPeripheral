package io.p2vman.neoperipheral;

import com.mojang.logging.LogUtils;
import io.p2vman.myservice.MetaServiceLoader;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Mod(Neoperipheral.MODID)
public class Neoperipheral {
    public static final String MODID = "neoperipheral";
    public static final Boolean debug = Boolean.getBoolean(MODID+"-debug");
    private static final Logger LOGGER = LogUtils.getLogger();


    public Neoperipheral(IEventBus bus, ModContainer modContainer) {
        ModRegistry.BLOCKS.register(bus);
        ModRegistry.ITEMS.register(bus);
        ModRegistry.BLOCK_ENTITY_TYPES.register(bus);
        ModRegistry.CREATIVE_MODE_TABS.register(bus);
        ModComponents.REGISTRAR.register(bus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        bus.addListener(ModRegistry::registerCapabilities);

        try {
            MetaServiceLoader.load("neo_integration").forEach(neoAddon -> {
                try {
                    var modid = (String) neoAddon.metadata().getOrDefault("modid", "");
                    if (ModList.get().isLoaded(modid)) {
                        Class<?> clazz = Class.forName(neoAddon.className());
                        clazz.getDeclaredConstructor(IEventBus.class).newInstance(bus);
                        LOGGER.info("Integration '{}' loaded", modid);
                    }
                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                         IllegalAccessException | InvocationTargetException e) {
                    LOGGER.error("Failed to load neo_addon class", e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
