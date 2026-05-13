package io.p2vman.neoperipheral.block;

import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.block.entity.SableEngineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SableEngineBlock extends Block implements EntityBlock {
    public SableEngineBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return ModRegistry.SABLE_ENGINE_BLOCK_ENTITY.get().create(blockPos, blockState);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return List.of(new ItemStack(this));
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return (Level var1, BlockPos var2, BlockState var3, T var4) -> {
            if (var4 instanceof SableEngineBlockEntity engine) {
                engine.tick();
            }
        };
    }
}
