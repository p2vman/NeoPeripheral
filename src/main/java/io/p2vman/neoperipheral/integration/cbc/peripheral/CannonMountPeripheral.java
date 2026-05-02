package io.p2vman.neoperipheral.integration.cbc.peripheral;

import com.simibubi.create.content.contraptions.AssemblyException;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.integration.cbc.MountedAutocannonContraptionProxy;
import io.p2vman.neoperipheral.mixin.addon.cbc.AbstractMountedCannonContraptionAccessor;
import io.p2vman.neoperipheral.mixin.addon.cbc.CannonMountBlockEntityAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import rbasamoyai.createbigcannons.cannon_control.cannon_mount.CannonMountBlockEntity;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;

public class CannonMountPeripheral implements IPeripheral {
    private final CannonMountBlockEntity mount;

    public CannonMountPeripheral(CannonMountBlockEntity mount) {
        this.mount = mount;
    }

    @Override
    public String getType() {
        return "cannon_mount";
    }

    @Override
    public boolean equals(IPeripheral other) {
        if (other instanceof CannonMountPeripheral o) return this.mount == o.mount;
        return false;
    }

    @LuaFunction(mainThread = true)
    public final boolean assemble() throws LuaException {
        if (this.mount.isRunning()) return false;
        try {
            ((CannonMountBlockEntityAccessor) mount).Assemble();
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
    public final double getYaw() {
        return ((CannonMountBlockEntityAccessor) mount).getYaw();
    }
    @LuaFunction
    public final double getPitch() {
        return ((CannonMountBlockEntityAccessor) mount).getPitch();
    }

    @LuaFunction(mainThread = true)
    public final void setYaw(double yaw) {
        mount.setYaw((float) yaw);
    }
    @LuaFunction(mainThread = true)
    public final void setPitch(double pitch) {
        mount.setPitch((float) pitch);
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
