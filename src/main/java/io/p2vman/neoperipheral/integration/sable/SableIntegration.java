package io.p2vman.neoperipheral.integration.sable;

import dan200.computercraft.api.peripheral.PeripheralCapability;
import dan200.computercraft.api.pocket.IPocketUpgrade;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.upgrades.UpgradeType;
import io.p2vman.neoperipheral.Config;
import io.p2vman.neoperipheral.integration.Integration;
import io.p2vman.neoperipheral.integration.sable.block.CreativeRadarBlock;
import io.p2vman.neoperipheral.integration.sable.block.RadarBlock;
import io.p2vman.neoperipheral.integration.sable.block.SableEngineBlock;
import io.p2vman.neoperipheral.integration.sable.block.entity.CreativeRadarBlockEntity;
import io.p2vman.neoperipheral.integration.sable.block.entity.RadarBlockEntity;
import io.p2vman.neoperipheral.integration.sable.block.entity.SableEngineBlockEntity;
import io.p2vman.neoperipheral.integration.sable.upgrade.pocket.CreativeRadarPocketUpgrade;
import io.p2vman.neoperipheral.integration.sable.upgrade.pocket.RadarPocketUpgrade;
import io.p2vman.neoperipheral.integration.sable.upgrade.turtle.CreativeRadarTurtleUpgrade;
import io.p2vman.neoperipheral.integration.sable.upgrade.turtle.RadarTurtleUpgrade;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Set;
import java.util.function.Supplier;

import static io.p2vman.neoperipheral.ModRegistry.*;

@Integration(modid = "sable")
public class SableIntegration {
    public static final DeferredBlock<RadarBlock> RADAR_BLOCK =
            BLOCKS.register("radar_block", () -> new RadarBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<CreativeRadarBlock> CREATIVE_RADAR_BLOCK =
            BLOCKS.register("creative_radar_block", () -> new CreativeRadarBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<SableEngineBlock> SABLE_ENGINE_BLOCK =
            BLOCKS.register("sable_engine", () -> new SableEngineBlock(BlockBehaviour.Properties.of()));

    public static final DeferredItem<BlockItem> RADAR_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(RADAR_BLOCK);
    public static final DeferredItem<BlockItem> CREATIVE_RADAR_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(CREATIVE_RADAR_BLOCK);
    public static final DeferredItem<BlockItem> SABLE_ENGINE_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(SABLE_ENGINE_BLOCK);

    public static final Supplier<BlockEntityType<RadarBlockEntity>> RADAR_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "radar_block_entity",
            () -> new BlockEntityType<>(
                    RadarBlockEntity::new,
                    Set.of(RADAR_BLOCK.get()),
                    null
            )
    );

    public static final Supplier<BlockEntityType<CreativeRadarBlockEntity>> CREATIVE_RADAR_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "creative_radar_block_entity",
            () -> new BlockEntityType<>(
                    CreativeRadarBlockEntity::new,
                    Set.of(CREATIVE_RADAR_BLOCK.get()),
                    null
            )
    );

    public static final Supplier<BlockEntityType<SableEngineBlockEntity>> SABLE_ENGINE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "sable_engine",
            () -> new BlockEntityType<>(
                    SableEngineBlockEntity::new,
                    Set.of(SABLE_ENGINE_BLOCK.get()),
                    null
            )
    );

    public static final DeferredHolder<UpgradeType<? extends IPocketUpgrade>, UpgradeType<RadarPocketUpgrade>> RADAR_UPGRADE_TYPE =
            POCKET_UPGRADES.register("radar", () -> RadarPocketUpgrade.TYPE);

    public static final DeferredHolder<UpgradeType<? extends IPocketUpgrade>, UpgradeType<CreativeRadarPocketUpgrade>> CREATIVE_RADAR_UPGRADE_TYPE =
            POCKET_UPGRADES.register("creative_radar", () -> CreativeRadarPocketUpgrade.TYPE);

    public static final DeferredHolder<UpgradeType<? extends ITurtleUpgrade>, UpgradeType<RadarTurtleUpgrade>> RADAR_TURTLE_UPGRADE_TYPE =
            TURTLE_UPGRADES.register("radar", () -> RadarTurtleUpgrade.TYPE);

    public static final DeferredHolder<UpgradeType<? extends ITurtleUpgrade>, UpgradeType<CreativeRadarTurtleUpgrade>> CREATIVE_RADAR_TURTLE_UPGRADE_TYPE =
            TURTLE_UPGRADES.register("creative_radar", () -> CreativeRadarTurtleUpgrade.TYPE);

    public SableIntegration(IEventBus modBus) {
        modBus.addListener(SableIntegration::registerCapabilities);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        var cap = PeripheralCapability.get();

        event.registerBlockEntity(cap, RADAR_BLOCK_ENTITY.get(), RadarBlockEntity::getPeripheral);
        event.registerBlockEntity(cap, CREATIVE_RADAR_BLOCK_ENTITY.get(), CreativeRadarBlockEntity::getPeripheral);
        if (Config._SABLE_ENGINE_ENABLED) event.registerBlockEntity(cap, SABLE_ENGINE_BLOCK_ENTITY.get(), SableEngineBlockEntity::getPeripheral);
    }
}
