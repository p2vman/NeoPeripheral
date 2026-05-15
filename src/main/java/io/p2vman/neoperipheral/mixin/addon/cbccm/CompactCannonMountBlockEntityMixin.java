package io.p2vman.neoperipheral.mixin.addon.cbccm;

import com.cubester.cbc_compact_mount.content.CompactCannonMountBlockEntity;
import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.integration.cbc.CannonMountBlockEntityProxy;
import io.p2vman.neoperipheral.util.PeripheralLazy;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CompactCannonMountBlockEntity.class)
public class CompactCannonMountBlockEntityMixin implements CannonMountBlockEntityProxy {
    private IPeripheral neoperipheral$Peripheral;

    @Override
    public IPeripheral neoperipheral$getPeripheral(Direction direction, PeripheralLazy.PeripheralFactory<IPeripheral, BlockEntity> factory) {
        if (neoperipheral$Peripheral == null) {
            neoperipheral$Peripheral = factory.createPeripheral(direction, (BlockEntity) (Object) this);
        }
        return neoperipheral$Peripheral;
    }
}
