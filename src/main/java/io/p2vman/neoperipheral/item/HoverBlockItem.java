package io.p2vman.neoperipheral.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class HoverBlockItem extends BlockItem {
    private final ResourceLocation hvname;
    public HoverBlockItem(Block block, Properties properties, ResourceLocation hvname) {
        super(block, properties);
        this.hvname = hvname;
    }

    @Override
    public void appendHoverText(ItemStack p_40572_, TooltipContext p_339655_, List<Component> components, TooltipFlag p_40575_) {
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable("full_hover."+this.hvname.getNamespace()+"."+this.hvname.getPath()));
        } else {
            components.add(Component.translatable("hover."+this.hvname.getNamespace()+"."+this.hvname.getPath()));
        }
        super.appendHoverText(p_40572_, p_339655_, components, p_40575_);
    }
}
