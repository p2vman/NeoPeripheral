package io.p2vman.neoperipheral.util;

import io.p2vman.neoperipheral.item.HoverBlockItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
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

    public DeferredItem<BlockItem> registerSimpleHoverBlockItem(String name, Supplier<? extends Block> block, Item.Properties properties, HoverBlockItem.IHoverAppender appender) {
        return this.register(name, (Function)((key) -> new HoverBlockItem((Block)block.get(), properties, appender)));
    }

    public DeferredItem<BlockItem> registerSimpleHoverBlockItem(String name, Supplier<? extends Block> block, HoverBlockItem.IHoverAppender appender) {
        return this.registerSimpleHoverBlockItem(name, block, new Item.Properties(), appender);
    }

    public DeferredItem<BlockItem> registerSimpleHoverBlockItem(Holder<Block> block, Item.Properties properties, HoverBlockItem.IHoverAppender appender) {
        String var10001 = ((ResourceKey)block.unwrapKey().orElseThrow()).location().getPath();
        Objects.requireNonNull(block);
        return this.registerSimpleHoverBlockItem(var10001, block::value, properties, appender);
    }

    public DeferredItem<BlockItem> registerSimpleHoverBlockItem(Holder<Block> block, HoverBlockItem.IHoverAppender appender) {
        return this.registerSimpleHoverBlockItem(block, new Item.Properties(), appender);
    }

    public static final ItemsDeferredRegister create(String namespace) {
        return new ItemsDeferredRegister(namespace);
    }
}
