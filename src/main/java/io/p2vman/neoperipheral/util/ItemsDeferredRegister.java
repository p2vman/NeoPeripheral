package io.p2vman.neoperipheral.util;

import io.p2vman.neoperipheral.item.HoverBlockItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemsDeferredRegister extends DeferredRegister.Items {
    protected ItemsDeferredRegister(String namespace) {
        super(namespace);
    }

    public DeferredItem<BlockItem> registerSimpleHoverBlockItem(String name, Supplier<? extends Block> block, Item.Properties properties) {
        return this.register(name, ((key) -> new HoverBlockItem(block.get(), properties, ResourceLocation.fromNamespaceAndPath(this.getNamespace(), name))));
    }

    public DeferredItem<BlockItem> registerSimpleHoverBlockItem(String name, Supplier<? extends Block> block) {
        return this.registerSimpleHoverBlockItem(name, block, new Item.Properties());
    }

    public DeferredItem<BlockItem> registerSimpleHoverBlockItem(Holder<Block> block, Item.Properties properties) {
        Objects.requireNonNull(block);
        return this.registerSimpleHoverBlockItem(block.unwrapKey().orElseThrow().location().getPath(), block::value, properties);
    }

    public DeferredItem<BlockItem> registerSimpleHoverBlockItem(Holder<Block> block) {
        return this.registerSimpleHoverBlockItem(block, new Item.Properties());
    }

    public static ItemsDeferredRegister create(String namespace) {
        return new ItemsDeferredRegister(namespace);
    }
}
