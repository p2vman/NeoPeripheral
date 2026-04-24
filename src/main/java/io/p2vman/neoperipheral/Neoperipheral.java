package io.p2vman.neoperipheral;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(Neoperipheral.MODID)
public class Neoperipheral {
    public static final String MODID = "neoperipheral";
    private static final Logger LOGGER = LogUtils.getLogger();


    public Neoperipheral(IEventBus bus, ModContainer modContainer) {
        ModRegistry.BLOCKS.register(bus);
        ModRegistry.ITEMS.register(bus);
        ModRegistry.BLOCK_ENTITY_TYPES.register(bus);
        ModRegistry.CREATIVE_MODE_TABS.register(bus);
        ModComponents.REGISTRAR.register(bus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        bus.addListener(ModRegistry::registerCapabilities);
    }
}
