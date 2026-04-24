package io.p2vman.neoperipheral.block;

import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.block.entity.SocketBlockEntity;
import io.p2vman.neoperipheral.item.ModuleItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.Containers;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SocketBlock extends Block implements EntityBlock {
    public SocketBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof SocketBlockEntity socketBe)) return InteractionResult.PASS;

        int slot = pickSlot(pos, hitResult);

        if (player.isCrouching()) {
            ItemStack removed = socketBe.removeModuleItem(slot);
            if (!removed.isEmpty()) {
                if (!player.addItem(removed)) player.drop(removed, false);
            }
        } else {
            ItemStack removed = socketBe.removeModuleItem(slot);
            if (!removed.isEmpty()) {
                if (player.getMainHandItem().isEmpty()) {
                    player.setItemInHand(InteractionHand.MAIN_HAND, removed);
                } else if (!player.addItem(removed)) {
                    player.drop(removed, false);
                }
            }
        }

        level.sendBlockUpdated(pos, state, state, 3);
        return InteractionResult.CONSUME;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) return ItemInteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof SocketBlockEntity socketBe)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        Item item = stack.getItem();
        if (!(item instanceof ModuleItem)) return  ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;


        int slot = pickSlot(pos, hitResult);
        ItemStack current = socketBe.getModuleItem(slot);

        if (current.isEmpty()) {
            socketBe.setModuleItem(slot, stack.copyWithCount(1));
            if (!player.getAbilities().instabuild) stack.shrink(1);

            level.sendBlockUpdated(pos, state, state, 3);
            return ItemInteractionResult.CONSUME;
        }

        if (player.isCrouching()) {
            ItemStack removed = socketBe.removeModuleItem(slot);
            socketBe.setModuleItem(slot, stack.copyWithCount(1));
            if (!player.getAbilities().instabuild) stack.shrink(1);

            if (!player.addItem(removed)) player.drop(removed, false);
            level.sendBlockUpdated(pos, state, state, 3);
            return ItemInteractionResult.CONSUME;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private static int pickSlot(BlockPos pos, BlockHitResult hitResult) {
        double lx = hitResult.getLocation().x - pos.getX();
        double lz = hitResult.getLocation().z - pos.getZ();
        int sx = lx >= 0.5 ? 1 : 0;
        int sz = lz >= 0.5 ? 1 : 0;
        return sx + (sz << 1);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return ModRegistry.SOCKET_BLOCK_ENTITY.get().create(blockPos, blockState);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return List.of(new ItemStack(ModRegistry.SOCKET_BLOCK.get()));
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof SocketBlockEntity socketBe && !level.isClientSide) {
                NonNullList<ItemStack> items = socketBe.getModuleItems();
                for (ItemStack stack : items) {
                    if (!stack.isEmpty()) Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
                }
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter p_60579_, BlockPos p_60580_) {
        return Shapes.empty();
    }
}
