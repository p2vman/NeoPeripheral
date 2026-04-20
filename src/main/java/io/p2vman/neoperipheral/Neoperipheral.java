package io.p2vman.neoperipheral;

import com.mojang.logging.LogUtils;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import io.p2vman.neoperipheral.block.entity.CreativeRadarBlockEntity;
import io.p2vman.neoperipheral.block.entity.RadarBlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;

@Mod(Neoperipheral.MODID)
public class Neoperipheral {
    public static final String MODID = "neoperipheral";
    private static final Logger LOGGER = LogUtils.getLogger();


    public Neoperipheral(IEventBus bus, ModContainer modContainer) {
        ModRegistry.BLOCKS.register(bus);
        ModRegistry.ITEMS.register(bus);
        ModRegistry.BLOCK_ENTITY_TYPES.register(bus);


        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        bus.addListener(this::registerCapabilities);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(PeripheralCapability.get(), ModRegistry.RADAR_BLOCK_ENTITY.get(), RadarBlockEntity::getPeripheral);
        event.registerBlockEntity(PeripheralCapability.get(), ModRegistry.CREATIVE_RADAR_BLOCK_ENTITY.get(), CreativeRadarBlockEntity::getPeripheral);
    }
}
