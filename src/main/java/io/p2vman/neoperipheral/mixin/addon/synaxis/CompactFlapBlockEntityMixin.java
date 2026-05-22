package io.p2vman.neoperipheral.mixin.addon.synaxis;

import com.verr1.synaxis.content.blocks.flap.CompactFlapBlockEntity;
import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.integration.IExternalPeripheralHolder;
import io.p2vman.neoperipheral.integration.synaxis.peripheral.CompactFlapPeripheral;
import io.p2vman.neoperipheral.util.PeripheralLazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CompactFlapBlockEntity.class)
public class CompactFlapBlockEntityMixin implements IExternalPeripheralHolder {
    private PeripheralLazy<CompactFlapPeripheral, CompactFlapBlockEntity> neo$peripheral;
    @Inject(method = "<init>(Lnet/minecraft/world/level/block/entity/BlockEntityType;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", at = @At("TAIL"))
    private void neo$init(BlockEntityType<?> type, BlockPos pos, BlockState state, CallbackInfo ci) {
        this.neo$peripheral = PeripheralLazy.<CompactFlapPeripheral, CompactFlapBlockEntity>of(CompactFlapPeripheral::new);
    }
    @Override
    public @Nullable IPeripheral getPeripheral(@Nullable Direction direction) {
        return neo$peripheral.get(direction, (CompactFlapBlockEntity) (Object) this);
    }
}
