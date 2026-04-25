package io.p2vman.neoperipheral.block;

import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.block.entity.NfcMasterBlockEntity;
import io.p2vman.neoperipheral.item.NfcCardItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NfcMasterBlock extends Block implements EntityBlock {
    public static final BooleanProperty HAS_CARD =
            BooleanProperty.create("has_card");

    public static final DirectionProperty FACING =
            BlockStateProperties.HORIZONTAL_FACING;

    public NfcMasterBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HAS_CARD, false));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return ModRegistry.NFC_MASTER_BLOCK_ENTITY.get().create(blockPos, blockState);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return List.of(new ItemStack(this));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HAS_CARD);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING,
                        context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING,
                rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                            Player player, BlockHitResult hit) {

        if (level.isClientSide) return InteractionResult.SUCCESS;

        ItemStack held = player.getItemInHand(InteractionHand.MAIN_HAND);

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof NfcMasterBlockEntity entity)) {
            return InteractionResult.PASS;
        }
        if (!entity.isEmpty()) {
            player.addItem(entity.getCard());
            entity.setCard(ItemStack.EMPTY);
            level.setBlock(pos, state.setValue(HAS_CARD, false), 3);
            return InteractionResult.CONSUME;
        }

        if (held.getItem() instanceof NfcCardItem) {
            entity.setCard(held.copy());
            held.shrink(1);
            level.setBlock(pos, state.setValue(HAS_CARD, true), 3);
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }
}
