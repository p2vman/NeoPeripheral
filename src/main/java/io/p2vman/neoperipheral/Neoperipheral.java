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
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
            var addons = MetaServiceLoader.load("neo_integration").stream()
                    .collect(Collectors.toMap(
                            a -> (String) a.metadata().getOrDefault("modid", ""),
                            a -> a,
                            (a, b) -> a,
                            LinkedHashMap::new
                    ));

            Set<String> visited = new LinkedHashSet<>();
            Set<String> visiting = new HashSet<>();

            Consumer<String>[] visit = new Consumer[1];
            visit[0] = modid -> {
                if (visited.contains(modid) || !addons.containsKey(modid)) return;
                if (!visiting.add(modid)) {
                    LOGGER.warn("Circular dependency at '{}'", modid);
                    return;
                }
                if (addons.get(modid).metadata().get("depends") instanceof List<?> deps)
                    deps.forEach(dep -> visit[0].accept((String) dep));
                visiting.remove(modid);
                visited.add(modid);
            };

            addons.keySet().forEach(visit[0]);

            visited.forEach(modid -> {
                try {
                    if (ModList.get().isLoaded(modid)) {
                        Class<?> clazz = Class.forName(addons.get(modid).className());
                        clazz.getDeclaredConstructor(IEventBus.class).newInstance(bus);
                        LOGGER.info("Integration '{}' loaded", modid);
                    }
                } catch (ReflectiveOperationException e) {
                    LOGGER.error("Failed to load neo_addon class", e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
