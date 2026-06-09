package io.p2vman.neoperipheral;

import com.mojang.logging.LogUtils;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import dan200.computercraft.api.pocket.IPocketUpgrade;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.upgrades.UpgradeBase;
import dan200.computercraft.api.upgrades.UpgradeData;
import dan200.computercraft.api.upgrades.UpgradeType;
import dan200.computercraft.shared.util.DataComponentUtil;
import io.p2vman.neoperipheral.block.*;
import io.p2vman.neoperipheral.block.entity.*;
import io.p2vman.neoperipheral.item.AdvancedServerItem;
import io.p2vman.neoperipheral.item.NfcCardItem;
import io.p2vman.neoperipheral.item.ServerItem;
import io.p2vman.neoperipheral.upgrade.pocket.CreativeRadarPocketUpgrade;
import io.p2vman.neoperipheral.upgrade.pocket.RadarPocketUpgrade;
import io.p2vman.neoperipheral.upgrade.turtle.CreativeRadarTurtleUpgrade;
import io.p2vman.neoperipheral.upgrade.turtle.RadarTurtleUpgrade;
import io.p2vman.neoperipheral.util.ItemsDeferredRegister;
import net.minecraft.core.Holder;
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
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
    public static final DeferredRegister<UpgradeType<? extends IPocketUpgrade>> POCKET_UPGRADES =
            DeferredRegister.create(IPocketUpgrade.typeRegistry(), Neoperipheral.MODID);
    public static final DeferredRegister<UpgradeType<? extends ITurtleUpgrade>> TURTLE_UPGRADES =
            DeferredRegister.create(ITurtleUpgrade.typeRegistry(), Neoperipheral.MODID);

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
            BLOCKS.register("triangle_radar", () -> new TriangleRadarBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<RackBlock> RACK_BLOCK =
            BLOCKS.register("rack", () -> new RackBlock(BlockBehaviour.Properties.of()));

    public static final DeferredItem<BlockItem> RADAR_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(RADAR_BLOCK);
    public static final DeferredItem<BlockItem> CREATIVE_RADAR_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(CREATIVE_RADAR_BLOCK);
    public static final DeferredItem<BlockItem> NFC_MASTER_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(NFC_MASTER_BLOCK);
    public static final DeferredItem<BlockItem> NFC_READER_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(NFC_READER_BLOCK);
    public static final DeferredItem<BlockItem> ENTITY_RADAR_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(ENTITY_RADAR_BLOCK);
    public static final DeferredItem<BlockItem> SABLE_ENGINE_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(SABLE_ENGINE_BLOCK);
    public static final DeferredItem<BlockItem> TRIANGLE_RADAR_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(TRIANGLE_RADAR_BLOCK);
    public static final DeferredItem<BlockItem> RACK_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(RACK_BLOCK);

    public static final DeferredItem<NfcCardItem> NFC_CARD_ITEM =
            ITEMS.registerItem("nfc_card", NfcCardItem::new, new Item.Properties().stacksTo(1));

    public static final DeferredItem<ServerItem> SERVER_ITEM =
            ITEMS.registerItem("server", ServerItem::new);
    public static final DeferredItem<AdvancedServerItem> ADVANCED_SERVER_ITEM =
            ITEMS.registerItem("advanced_server", AdvancedServerItem::new);

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

    public static final Supplier<BlockEntityType<RackBlockEntity>> RACK_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "rack",
            () -> new BlockEntityType<>(
                    RackBlockEntity::new,
                    Set.of(RACK_BLOCK.get()),
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

    private static boolean isOurUpgrade(Holder.Reference<? extends UpgradeBase> upgrade) {
        return upgrade.key().location().getNamespace().equals(Neoperipheral.MODID);
    }

    public static final Supplier<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Neoperipheral.MODID + ".tab"))
            .icon(() -> new ItemStack(RADAR_BLOCK_ITEM.asItem()))
            .displayItems((params, output) -> {
                ITEMS.getEntries().stream().map(DeferredHolder::get).forEach(output::accept);


                Stream<ItemStack> filteredItemStacks = params.holders()
                        .lookupOrThrow(IPocketUpgrade.REGISTRY)
                        .listElements()
                        .filter(ModRegistry::isOurUpgrade)
                        .flatMap(x -> Stream.of(
                                DataComponentUtil.createStack(
                                        dan200.computercraft.shared.ModRegistry.Items.POCKET_COMPUTER_NORMAL.get(),
                                        dan200.computercraft.shared.ModRegistry.DataComponents.POCKET_UPGRADE.get(),
                                        UpgradeData.ofDefault(x)),
                                DataComponentUtil.createStack(
                                        dan200.computercraft.shared.ModRegistry.Items.POCKET_COMPUTER_ADVANCED.get(),
                                        dan200.computercraft.shared.ModRegistry.DataComponents.POCKET_UPGRADE.get(),
                                        UpgradeData.ofDefault(x))
                        ));

                filteredItemStacks.forEach(output::accept);

                filteredItemStacks = params.holders()
                        .lookupOrThrow(ITurtleUpgrade.REGISTRY)
                        .listElements()
                        .filter(ModRegistry::isOurUpgrade)
                        .flatMap(x -> Stream.of(
                                DataComponentUtil.createStack(
                                        dan200.computercraft.shared.ModRegistry.Items.TURTLE_NORMAL.get(),
                                        dan200.computercraft.shared.ModRegistry.DataComponents.LEFT_TURTLE_UPGRADE.get(),
                                        UpgradeData.ofDefault(x)),
                                DataComponentUtil.createStack(
                                        dan200.computercraft.shared.ModRegistry.Items.TURTLE_ADVANCED.get(),
                                        dan200.computercraft.shared.ModRegistry.DataComponents.LEFT_TURTLE_UPGRADE.get(),
                                        UpgradeData.ofDefault(x))
                        ));

                filteredItemStacks.forEach(output::accept);
            })
            .build()
    );

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        var cap = PeripheralCapability.get();
        event.registerBlockEntity(cap, RADAR_BLOCK_ENTITY.get(), RadarBlockEntity::getPeripheral);
        event.registerBlockEntity(cap, CREATIVE_RADAR_BLOCK_ENTITY.get(), CreativeRadarBlockEntity::getPeripheral);
        event.registerBlockEntity(cap, NFC_MASTER_BLOCK_ENTITY.get(), NfcMasterBlockEntity::getPeripheral);
        event.registerBlockEntity(cap, NFC_READER_BLOCK_ENTITY.get(), NfcReaderBlockEntity::getPeripheral);
        event.registerBlockEntity(cap, ENTITY_RADAR_BLOCK_ENTITY.get(), EntityRadarBlockEntity::getPeripheral);
        event.registerBlockEntity(cap, RADAR_BLOCK_ENTITY.get(), RadarBlockEntity::getPeripheral);
        if (Config._SABLE_ENGINE_ENABLED) event.registerBlockEntity(cap, SABLE_ENGINE_BLOCK_ENTITY.get(), SableEngineBlockEntity::getPeripheral);
        if (Neoperipheral.debug) {
            event.registerBlockEntity(cap, TRIANGLE_RADAR_BLOCK_ENTITY.get(), TriangleRadarBlockEntity::getPeripheral);
        }
    }
}
