package io.p2vman.neoperipheral;

import com.mojang.logging.LogUtils;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import dan200.computercraft.api.pocket.IPocketUpgrade;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.upgrades.UpgradeBase;
import dan200.computercraft.api.upgrades.UpgradeData;
import dan200.computercraft.api.upgrades.UpgradeType;
import dan200.computercraft.shared.util.DataComponentUtil;
import io.p2vman.neoperipheral.block.EntityRadarBlock;
import io.p2vman.neoperipheral.block.NfcMasterBlock;
import io.p2vman.neoperipheral.block.NfcReaderBlock;
import io.p2vman.neoperipheral.block.entity.EntityRadarBlockEntity;
import io.p2vman.neoperipheral.block.entity.NfcMasterBlockEntity;
import io.p2vman.neoperipheral.block.entity.NfcReaderBlockEntity;
import io.p2vman.neoperipheral.item.NfcCardItem;
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

    public static final DeferredBlock<NfcMasterBlock> NFC_MASTER_BLOCK =
            BLOCKS.register("nfc_master", () -> new NfcMasterBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<NfcReaderBlock> NFC_READER_BLOCK =
            BLOCKS.register("nfc_reader", () -> new NfcReaderBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<EntityRadarBlock> ENTITY_RADAR_BLOCK =
            BLOCKS.register("entity_radar",  () -> new EntityRadarBlock(BlockBehaviour.Properties.of()));

    public static final DeferredItem<BlockItem> NFC_MASTER_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(NFC_MASTER_BLOCK);
    public static final DeferredItem<BlockItem> NFC_READER_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(NFC_READER_BLOCK);
    public static final DeferredItem<BlockItem> ENTITY_RADAR_BLOCK_ITEM = ITEMS.registerSimpleHoverBlockItem(ENTITY_RADAR_BLOCK);

    public static final DeferredItem<NfcCardItem> NFC_CARD_ITEM =
            ITEMS.registerItem("nfc_card", NfcCardItem::new, new Item.Properties().stacksTo(1));

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


    public static boolean isOurUpgrade(Holder.Reference<? extends UpgradeBase> upgrade) {
        return upgrade.key().location().getNamespace().equals(Neoperipheral.MODID);
    }

    public static final Supplier<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Neoperipheral.MODID + ".tab"))
            .icon(() -> new ItemStack(ENTITY_RADAR_BLOCK_ITEM.asItem()))
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
        event.registerBlockEntity(cap, NFC_MASTER_BLOCK_ENTITY.get(), NfcMasterBlockEntity::getPeripheral);
        event.registerBlockEntity(cap, NFC_READER_BLOCK_ENTITY.get(), NfcReaderBlockEntity::getPeripheral);
        event.registerBlockEntity(cap, ENTITY_RADAR_BLOCK_ENTITY.get(), EntityRadarBlockEntity::getPeripheral);
    }
}
