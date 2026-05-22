package io.p2vman.neoperipheral.mixin.addon.synaxis;

import com.verr1.synaxis.content.blocks.kinetic.resistor.KineticResistorBlockEntity;
import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.integration.IExternalPeripheralHolder;
import io.p2vman.neoperipheral.integration.synaxis.peripheral.KineticResistorPeripheral;
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

@Mixin(KineticResistorBlockEntity.class)
public class KineticResistorBlockEntityMixin implements IExternalPeripheralHolder {
    @Unique
    private PeripheralLazy<KineticResistorPeripheral, KineticResistorBlockEntity> neoperipheral$peripheral;

    @Inject(method = "<init>(Lnet/minecraft/world/level/block/entity/BlockEntityType;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", at = @At("TAIL"))
    private void init(BlockEntityType<?> type, BlockPos pos, BlockState state, CallbackInfo ci) {
        this.neoperipheral$peripheral = PeripheralLazy.<KineticResistorPeripheral, KineticResistorBlockEntity>of(KineticResistorPeripheral::new);
    }

    @Override
    public @Nullable IPeripheral getPeripheral(@Nullable Direction direction) {
        return neoperipheral$peripheral.get(direction, (KineticResistorBlockEntity) (Object) this);
    }
}
