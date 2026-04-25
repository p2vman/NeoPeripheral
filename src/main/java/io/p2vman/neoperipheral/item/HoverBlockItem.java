package io.p2vman.neoperipheral.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class HoverBlockItem extends BlockItem {
    private final IHoverAppender appender;
    public HoverBlockItem(Block block, Properties properties, IHoverAppender appender) {
        super(block, properties);
        this.appender = appender;
    }

    @Override
    public void appendHoverText(ItemStack p_40572_, TooltipContext p_339655_, List<Component> p_40574_, TooltipFlag p_40575_) {
        this.appender.appendHoverText(p_40572_, p_339655_, p_40574_, p_40575_);
        super.appendHoverText(p_40572_, p_339655_, p_40574_, p_40575_);
    }

    public interface IHoverAppender {
            void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag);
    }
}
