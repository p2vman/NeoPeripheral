package io.p2vman.neoperipheral;

import com.mojang.logging.LogUtils;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import io.p2vman.neoperipheral.block.*;
import io.p2vman.neoperipheral.block.entity.*;
import io.p2vman.neoperipheral.item.NfcCardItem;
import io.p2vman.neoperipheral.util.ItemsDeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.Set;
import java.util.function.Supplier;

public class ModRegistry {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Neoperipheral.MODID);
    public static final ItemsDeferredRegister ITEMS =
            ItemsDeferredRegister.create(Neoperipheral.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Neoperipheral.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Neoperipheral.MODID);

    public static final DeferredBlock<RadarBlock> RADAR_BLOCK =
            BLOCKS.register("radar_block", () -> new RadarBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<CreativeRadarBlock> CREATIVE_RADAR_BLOCK =
            BLOCKS.register("creative_radar_block", () -> new CreativeRadarBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<NfcMasterBlock> NFC_MASTER_BLOCK =
            BLOCKS.register("nfc_master", () -> new NfcMasterBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<NfcReaderBlock> NFC_READER_BLOCK =
            BLOCKS.register("nfc_reader", () -> new NfcReaderBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<EntityRadarBlock> ENTITY_RADAR_BLOCK =
            BLOCKS.register("entity_radar",  () -> new EntityRadarBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<SableEngineBlock> SABLE_ENGINE_BLOCK =
            BLOCKS.register("sable_engine", () -> new SableEngineBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<TriangleRadarBlock> TRIANGLE_RADAR_BLOCK =
            BLOCKS.register("triangle_radar",   () -> new TriangleRadarBlock(BlockBehaviour.Properties.of()));

    public static final DeferredItem<BlockItem> RADAR_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(RADAR_BLOCK);
    public static final DeferredItem<BlockItem> CREATIVE_RADAR_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(CREATIVE_RADAR_BLOCK);
    public static final DeferredItem<BlockItem> NFC_MASTER_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(NFC_MASTER_BLOCK);
    public static final DeferredItem<BlockItem> NFC_READER_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(NFC_READER_BLOCK);
    public static final DeferredItem<BlockItem> ENTITY_RADAR_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(ENTITY_RADAR_BLOCK);
    public static final DeferredItem<BlockItem> SABLE_ENGINE_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(SABLE_ENGINE_BLOCK);
    public static final DeferredItem<BlockItem> TRIANGLE_RADAR_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(TRIANGLE_RADAR_BLOCK);

    public static final DeferredItem<NfcCardItem> NFC_CARD_ITEM =
            ITEMS.registerItem("nfc_card", NfcCardItem::new, new Item.Properties().stacksTo(1));

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

    public static final Supplier<BlockEntityType<NfcMasterBlockEntity>> NFC_MASTER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "nfc_master",
            () -> new BlockEntityType<>(
                    NfcMasterBlockEntity::new,
                    Set.of(NFC_MASTER_BLOCK.get()),
                    null
            )
    );

    public static final Supplier<BlockEntityType<NfcReaderBlockEntity>> NFC_READER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "nfc_reader",
            () -> new BlockEntityType<>(
                    NfcReaderBlockEntity::new,
                    Set.of(NFC_READER_BLOCK.get()),
                    null
            )
    );

    public static final Supplier<BlockEntityType<EntityRadarBlockEntity>> ENTITY_RADAR_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "entity_radar",
            () -> new BlockEntityType<>(
                    EntityRadarBlockEntity::new,
                    Set.of(ENTITY_RADAR_BLOCK.get()),
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

    public static final Supplier<BlockEntityType<TriangleRadarBlockEntity>> TRIANGLE_RADAR_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "triangle_radar",
            () -> new BlockEntityType<>(
                    TriangleRadarBlockEntity::new,
                    Set.of(TRIANGLE_RADAR_BLOCK.get()),
                    null
            )
    );

    public static final Supplier<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Neoperipheral.MODID + ".tab"))
            .icon(() -> new ItemStack(RADAR_BLOCK_ITEM.asItem()))
            .displayItems((params, output) -> {
                output.accept(RADAR_BLOCK_ITEM.get());
                output.accept(CREATIVE_RADAR_BLOCK_ITEM.get());
                output.accept(NFC_MASTER_BLOCK_ITEM.get());
                output.accept(NFC_READER_BLOCK_ITEM.get());
                output.accept(NFC_CARD_ITEM.get());
                output.accept(ENTITY_RADAR_BLOCK_ITEM.get());
                output.accept(SABLE_ENGINE_BLOCK_ITEM.get());
                output.accept(TRIANGLE_RADAR_BLOCK_ITEM.get());
            })
            .build()
    );

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(PeripheralCapability.get(), RADAR_BLOCK_ENTITY.get(), RadarBlockEntity::getPeripheral);
        event.registerBlockEntity(PeripheralCapability.get(), CREATIVE_RADAR_BLOCK_ENTITY.get(), CreativeRadarBlockEntity::getPeripheral);
        event.registerBlockEntity(PeripheralCapability.get(), NFC_MASTER_BLOCK_ENTITY.get(), NfcMasterBlockEntity::getPeripheral);
        event.registerBlockEntity(PeripheralCapability.get(), NFC_READER_BLOCK_ENTITY.get(), NfcReaderBlockEntity::getPeripheral);
        event.registerBlockEntity(PeripheralCapability.get(), ENTITY_RADAR_BLOCK_ENTITY.get(), EntityRadarBlockEntity::getPeripheral);
        if (Config._SABLE_ENGINE_ENABLED) event.registerBlockEntity(PeripheralCapability.get(), SABLE_ENGINE_BLOCK_ENTITY.get(), SableEngineBlockEntity::getPeripheral);
        if (Neoperipheral.debug) {
            event.registerBlockEntity(PeripheralCapability.get(), TRIANGLE_RADAR_BLOCK_ENTITY.get(), TriangleRadarBlockEntity::getPeripheral);
        }
    }
}
