package io.p2vman.neoperipheral;

import com.mojang.logging.LogUtils;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import io.p2vman.neoperipheral.block.*;
import io.p2vman.neoperipheral.block.entity.*;
import io.p2vman.neoperipheral.item.ModuleItem;
import io.p2vman.neoperipheral.item.NfcCardItem;
import io.p2vman.neoperipheral.peripheral.socket.ModuleLookup;
import io.p2vman.neoperipheral.peripheral.socket.modules.CreativeRadarModule;
import io.p2vman.neoperipheral.peripheral.socket.modules.CryptoModule;
import io.p2vman.neoperipheral.peripheral.socket.modules.RadarModule;
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
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Neoperipheral.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Neoperipheral.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Neoperipheral.MODID);

    public static final DeferredBlock<RadarBlock> RADAR_BLOCK =
            BLOCKS.register("radar_block", () -> new RadarBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<CreativeRadarBlock> CREATIVE_RADAR_BLOCK =
            BLOCKS.register("creative_radar_block", () -> new CreativeRadarBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<SocketBlock> SOCKET_BLOCK =
            BLOCKS.register("socket", () -> new SocketBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<CryptoBlock> CRYPTO_BLOCK =
            BLOCKS.register("crypto", () -> new CryptoBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<NfcMasterBlock> NFC_MASTER_BLOCK =
            BLOCKS.register("nfc_master", () -> new NfcMasterBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<NfcReaderBlock> NFC_READER_BLOCK =
            BLOCKS.register("nfc_reader", () -> new NfcReaderBlock(BlockBehaviour.Properties.of()));

    public static final DeferredItem<BlockItem> RADAR_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(RADAR_BLOCK);

    public static final DeferredItem<BlockItem> CREATIVE_RADAR_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(CREATIVE_RADAR_BLOCK);

    public static final DeferredItem<BlockItem> SOCKET_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(SOCKET_BLOCK);

    public static final DeferredItem<BlockItem> CRYPTO_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(CRYPTO_BLOCK);

    public static final DeferredItem<BlockItem> NFC_MASTER_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(NFC_MASTER_BLOCK);

    public static final DeferredItem<BlockItem> NFC_READER_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(NFC_READER_BLOCK);

    public static final DeferredItem<ModuleItem> RADAR_MODULE_ITEM =
            ITEMS.registerItem("radar_module", ModuleItem::new, new Item.Properties().stacksTo(1));

    public static final DeferredItem<ModuleItem> CREATIVE_RADAR_MODULE_ITEM =
            ITEMS.registerItem("creative_radar_module", ModuleItem::new, new Item.Properties().stacksTo(1));

    public static final DeferredItem<ModuleItem> CRYPTO_MODULE_ITEM =
            ITEMS.registerItem("crypto_module", ModuleItem::new, new Item.Properties().stacksTo(1));

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

    public static final Supplier<BlockEntityType<SocketBlockEntity>> SOCKET_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "socket",
            () -> new BlockEntityType<>(
                    SocketBlockEntity::new,
                    Set.of(SOCKET_BLOCK.get()),
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

    public static final Supplier<BlockEntityType<CryptoBlockEntity>> CRYPTO_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "crypto",
            () -> new BlockEntityType<>(
                    CryptoBlockEntity::new,
                    Set.of(CRYPTO_BLOCK.get()),
                    null
            )
    );

    public static final Supplier<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Neoperipheral.MODID + ".tab"))
            .icon(() -> new ItemStack(RADAR_BLOCK_ITEM.asItem()))
            .displayItems((params, output) -> {
                output.accept(RADAR_BLOCK_ITEM.get());
                output.accept(CREATIVE_RADAR_BLOCK_ITEM.get());
                output.accept(SOCKET_BLOCK_ITEM.get());
                output.accept(RADAR_MODULE_ITEM.get());
                output.accept(CREATIVE_RADAR_MODULE_ITEM.get());
                output.accept(NFC_MASTER_BLOCK_ITEM.get());
                output.accept(NFC_READER_BLOCK_ITEM.get());
                output.accept(CRYPTO_BLOCK_ITEM.get());
                output.accept(NFC_CARD_ITEM.get());
                output.accept(CRYPTO_MODULE_ITEM.get());
            })
            .build()
    );

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(PeripheralCapability.get(), RADAR_BLOCK_ENTITY.get(), RadarBlockEntity::getPeripheral);
        event.registerBlockEntity(PeripheralCapability.get(), CREATIVE_RADAR_BLOCK_ENTITY.get(), CreativeRadarBlockEntity::getPeripheral);
        event.registerBlockEntity(PeripheralCapability.get(), SOCKET_BLOCK_ENTITY.get(), SocketBlockEntity::getPeripheral);
        event.registerBlockEntity(PeripheralCapability.get(), NFC_MASTER_BLOCK_ENTITY.get(), NfcMasterBlockEntity::getPeripheral);
        event.registerBlockEntity(PeripheralCapability.get(), NFC_READER_BLOCK_ENTITY.get(), NfcReaderBlockEntity::getPeripheral);
        event.registerBlockEntity(PeripheralCapability.get(), CRYPTO_BLOCK_ENTITY.get(), CryptoBlockEntity::getPeripheral);

        ModuleLookup.register(RADAR_MODULE_ITEM.get(), RadarModule::new);
        ModuleLookup.register(CREATIVE_RADAR_MODULE_ITEM.get(), CreativeRadarModule::new);
        ModuleLookup.register(CRYPTO_MODULE_ITEM.get(), CryptoModule::new);
    }


}
