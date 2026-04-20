package io.p2vman.neoperipheral;

import com.mojang.logging.LogUtils;
import io.p2vman.neoperipheral.block.CreativeRadarBlock;
import io.p2vman.neoperipheral.block.RadarBlock;
import io.p2vman.neoperipheral.block.entity.CreativeRadarBlockEntity;
import io.p2vman.neoperipheral.block.entity.RadarBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

    public static final DeferredItem<BlockItem> RADAR_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(RADAR_BLOCK);

    public static final DeferredItem<BlockItem> CREATIVE_RADAR_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(CREATIVE_RADAR_BLOCK);

    public static final Supplier<BlockEntityType<RadarBlockEntity>> RADAR_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "radar_block_entity",
            // The block entity type.
            () -> new BlockEntityType<>(
                    // The supplier to use for constructing the block entity instances.
                    RadarBlockEntity::new,
                    // An optional value that, when true, only allows players with OP permissions
                    // to load NBT data (e.g. placing a block item)
                    Set.of(RADAR_BLOCK.get()),
                    // A vararg of blocks that can have this block entity.
                    // This assumes the existence of the referenced blocks as DeferredBlock<Block>s.
                    null
            )
    );

    public static final Supplier<BlockEntityType<CreativeRadarBlockEntity>> CREATIVE_RADAR_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "creative_radar_block_entity",
            // The block entity type.
            () -> new BlockEntityType<>(
                    // The supplier to use for constructing the block entity instances.
                    CreativeRadarBlockEntity::new,
                    // An optional value that, when true, only allows players with OP permissions
                    // to load NBT data (e.g. placing a block item)
                    Set.of(CREATIVE_RADAR_BLOCK.get()),
                    // A vararg of blocks that can have this block entity.
                    // This assumes the existence of the referenced blocks as DeferredBlock<Block>s.
                    null
            )
    );


    public static final Supplier<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Neoperipheral.MODID + ".tab"))
            .icon(() -> new ItemStack(RADAR_BLOCK_ITEM.asItem()))
            .displayItems((params, output) -> {
                output.accept(RADAR_BLOCK_ITEM.get());
                output.accept(CREATIVE_RADAR_BLOCK_ITEM.get());
            })
            .build()
    );
}
