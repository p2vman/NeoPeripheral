package io.p2vman.neoperipheral.integration.cbccm.peripheral;

import com.cubester.cbc_compact_mount.content.CompactCannonMountBlockEntity;
import com.simibubi.create.content.contraptions.AssemblyException;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.Config;
import io.p2vman.neoperipheral.integration.cbc.MountedAutocannonContraptionProxy;
import io.p2vman.neoperipheral.mixin.addon.cbc.AbstractMountedCannonContraptionAccessor;
import io.p2vman.neoperipheral.mixin.addon.cbccm.CompactCannonMountBlockEntityAccessor;
import io.p2vman.neoperipheral.util.CallLimiter;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;

import java.util.Set;

public class CompactCannonMountPeripheral implements IPeripheral {
    private final CompactCannonMountBlockEntity mount;
    private final CallLimiter callLimiter;

    public CompactCannonMountPeripheral(Direction direction, BlockEntity mount) {
        this.mount = (CompactCannonMountBlockEntity) mount;
        this.callLimiter = new CallLimiter.RingCallLimiter(Config._CHEAT_CANNON_MAX_FIRE_RATE, Config._CHEAT_CANNON_RATE_WINDOW);
    }

    @Override
    public String getType() {
        return "compact_mount";
    }

    @Override
    public Set<String> getAdditionalTypes() {
        return Set.of("neo_compact_mount", "neo_mount");
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        if (other instanceof CompactCannonMountPeripheral o) return this.mount == o.mount;
        return false;
    }

    @LuaFunction(mainThread = true)
    public final boolean assemble() throws LuaException {
        if (this.mount.isRunning()) return false;
        try {
            ((CompactCannonMountBlockEntityAccessor) mount).Assemble();
        } catch (AssemblyException e) {
            throw new LuaException("Failed assemble.");
        }
        PitchOrientedContraptionEntity contraption = mount.getContraption();
        if (contraption != null) {
            ((AbstractMountedCannonContraption) contraption.getContraption())
                    .onRedstoneUpdate(
                            (ServerLevel) mount.getLevel(),
                            contraption,
                            false,
                            0,
                            mount
                    );
        }
        return true;
    }

    @LuaFunction(mainThread = true)
    public final void fire() throws LuaException {
        PitchOrientedContraptionEntity contraption = mount.getContraption();
        if (contraption == null) throw new LuaException("Cannon is not assembled");
        if (!(mount.getLevel() instanceof ServerLevel serverLevel))
            throw new LuaException("Cannot fire");
        if (!callLimiter.tryCall(contraption.getServer().getTickCount())) return;

        var con = contraption.getContraption();
        if (con instanceof MountedAutocannonContraptionProxy proxy) {
            proxy.setNeoperipheral$cc_call(true);
        }
        if (con instanceof AbstractMountedCannonContraptionAccessor con2) {
            con2.FireShot(serverLevel, contraption);
        }
    }

    @LuaFunction(mainThread = true)
    public final boolean disassemble() {
        if (!this.mount.isRunning()) return false;
        mount.disassemble();
        return true;
    }

    @LuaFunction
    public final boolean isRunning() {
        return this.mount.isRunning();
    }

    @LuaFunction
    public final double getPitch() {
        return ((CompactCannonMountBlockEntityAccessor) mount).getPitch();
    }

    @LuaFunction(mainThread = true)
    public final void setPitch(double pitch) {
        ((CompactCannonMountBlockEntityAccessor) mount).setPitch((float) pitch);
    }

    @LuaFunction(mainThread = true)
    public final int getX() {
        return mount.getControllerBlockPos().getX();
    }
    @LuaFunction(mainThread = true)
    public final int getY() {
        return mount.getControllerBlockPos().getY();
    }
    @LuaFunction(mainThread = true)
    public final int getZ() {
        return mount.getControllerBlockPos().getZ();
    }

    @LuaFunction(mainThread = true)
    public final String getDirection() {
        return mount.getControllerState()
                .getValue(BlockStateProperties.HORIZONTAL_FACING)
                .toString();
    }
}
