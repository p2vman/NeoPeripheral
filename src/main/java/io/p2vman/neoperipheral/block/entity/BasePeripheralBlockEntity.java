package io.p2vman.neoperipheral.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.IPrefSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public abstract class BasePeripheralBlockEntity extends BlockEntity implements IPrefSource {
    private String label = null;
    public BasePeripheralBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Nullable
    public abstract IPeripheral getPeripheral(@Nullable Direction direction);

    @Override
    protected void saveAdditional(CompoundTag p_187471_, HolderLookup.Provider p_323635_) {
        super.saveAdditional(p_187471_, p_323635_);
        if (label != null) {
            p_187471_.putString("label", label);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag p_338466_, HolderLookup.Provider p_338445_) {
        super.loadAdditional(p_338466_, p_338445_);
        if (p_338466_.contains("label", Tag.TAG_STRING)) {
            label = p_338466_.getString("label");
        }
    }

    public void setLabel(String label) {
        this.label = label;
        setChanged();
    }

    public String getLabel() {
        return label;
    }

    public boolean hasLabel() {
        return label != null;
    }
}
