package io.p2vman.neoperipheral.integration.create;

import dan200.computercraft.api.peripheral.PeripheralCapability;
import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.integration.Integration;
import io.p2vman.neoperipheral.integration.create.block.LinkedControllerHubBlock;
import io.p2vman.neoperipheral.integration.create.block.entity.LinkedControllerHubBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Set;
import java.util.function.Supplier;

@Integration(modid = "create")
public class CreateIntegration {

    public static final DeferredBlock<LinkedControllerHubBlock> LINKED_CONTROLLER_HUB_BLOCK =
            ModRegistry.BLOCKS.register("linked_controller_hub", () -> new LinkedControllerHubBlock(BlockBehaviour.Properties.of()));

    public static final DeferredItem<BlockItem> LINKED_CONTROLLER_HUB_BLOCK_ITEM = ModRegistry.ITEMS.registerSimpleBlockItem(LINKED_CONTROLLER_HUB_BLOCK);

    public static final Supplier<BlockEntityType<LinkedControllerHubBlockEntity>> LINKED_CONTROLLER_HUB_BLOCK_ENTITY = ModRegistry.BLOCK_ENTITY_TYPES.register(
            "linked_controller_hub",
            () -> new BlockEntityType<>(
                    LinkedControllerHubBlockEntity::new,
                    Set.of(LINKED_CONTROLLER_HUB_BLOCK.get()),
                    null
            )
    );

    public CreateIntegration(IEventBus bus) {
        bus.addListener(CreateIntegration::registerCapabilities);
    }



    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                PeripheralCapability.get(),
                LINKED_CONTROLLER_HUB_BLOCK_ENTITY.get(),
                LinkedControllerHubBlockEntity::getPeripheral
        );
    }
}
