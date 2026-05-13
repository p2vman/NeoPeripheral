package io.p2vman.neoperipheral.integration.create.block;

import com.simibubi.create.AllItems;
import edn.stratodonut.drivebywire.WireSounds;
import io.p2vman.neoperipheral.ModComponents;
import io.p2vman.neoperipheral.component.HubLinkComponent;
import io.p2vman.neoperipheral.integration.create.CreateIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LinkedControllerHubBlock extends Block implements EntityBlock {
    public LinkedControllerHubBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return CreateIntegration.LINKED_CONTROLLER_HUB_BLOCK_ENTITY.get().create(blockPos, blockState);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return List.of(new ItemStack(this));
    }

    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        if (!AllItems.LINKED_CONTROLLER.isIn(itemStack)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else {
            if (!level.isClientSide()) {
                itemStack.set(ModComponents.HUB_LINK, new HubLinkComponent(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                level.playSound((Player)null, blockPos, (SoundEvent) WireSounds.PLUG_IN.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                player.displayClientMessage(Component.literal("Controller connected!"), true);
            }

            return ItemInteractionResult.SUCCESS;
        }
    }
}
