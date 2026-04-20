package io.p2vman.neoperipheral.block;

import io.p2vman.neoperipheral.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CreativeRadarBlock extends RadarBlock {
    public CreativeRadarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return ModRegistry.CREATIVE_RADAR_BLOCK_ENTITY.get().create(blockPos, blockState);
    }
}
