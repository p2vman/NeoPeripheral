package io.p2vman.neoperipheral.mixin.addon.synaxis;

import com.verr1.synaxis.content.blocks.motor.AbstractDynamicMotorBlockEntity;
import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.integration.IExternalPeripheralHolder;
import io.p2vman.neoperipheral.integration.synaxis.peripheral.DynamicMotorPeripheral;
import io.p2vman.neoperipheral.util.PeripheralLazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractDynamicMotorBlockEntity.class)
public class AbstractDynamicMotorBlockEntityMixin implements IExternalPeripheralHolder {
    @Unique
    private PeripheralLazy<DynamicMotorPeripheral, AbstractDynamicMotorBlockEntity> neoperipheral$peripheral;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void neoperipheral$init(BlockEntityType<?> type, BlockPos pos, BlockState state, CallbackInfo ci) {
        neoperipheral$peripheral = PeripheralLazy.<DynamicMotorPeripheral, AbstractDynamicMotorBlockEntity>of(DynamicMotorPeripheral::new);
    }

    @Override
    public @Nullable IPeripheral getPeripheral(@Nullable Direction direction) {
        return neoperipheral$peripheral.get(direction, (AbstractDynamicMotorBlockEntity) (Object) this);
    }
}
