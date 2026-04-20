package io.p2vman.neoperipheral.block;

import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.block.entity.RadarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RadarBlock extends Block implements EntityBlock {
    public RadarBlock(Properties properties) {
        super(properties);
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return ModRegistry.RADAR_BLOCK_ENTITY.get().create(blockPos, blockState);
    }
}
