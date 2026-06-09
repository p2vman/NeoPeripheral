package io.p2vman.neoperipheral.integration.synaxis.peripheral;

import com.verr1.synaxis.content.blocks.camera.CameraBlockEntity;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.lua.Table;
import io.p2vman.neoperipheral.mixin.addon.synaxis.CameraBlockEntityAccessor;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public class CameraPeripheral implements IPeripheral {
    private final CameraBlockEntity entity;

    public CameraPeripheral(final @Nullable Direction direction, final CameraBlockEntity entity) {
        this.entity = entity;
    }

    @Override
    public String getType() {
        return "synaxis_camera";
    }

    @Override
    public boolean equals(IPeripheral iPeripheral) {
        return iPeripheral instanceof CameraPeripheral;
    }

    @LuaFunction(mainThread = true)
    public final double getPitch() {
        return this.entity.localPitch();
    }

    @LuaFunction(mainThread = true)
    public final double getYaw() {
        return this.entity.localYaw();
    }

    @LuaFunction(mainThread = true)
    public final double getDistance() {
        return this.entity.latestDistance();
    }

    @LuaFunction(mainThread = true)
    public final boolean isRayEnabled() {
        return this.entity.rayEnabled();
    }

    @LuaFunction(mainThread = true)
    public final void setRayEnabled(final boolean enabled) {
        ((CameraBlockEntityAccessor) entity).setRayEnabled(enabled);
    }

    @LuaFunction(mainThread = true)
    public final double rayRange() {
        return this.entity.rayRange();
    }

    @LuaFunction(mainThread = true)
    public final void setRayRange(final double range) {
        ((CameraBlockEntityAccessor) entity).setRayRange(range);
    }

    @LuaFunction(mainThread = true)
    public final Table collect() {
        var data = new Table();
        data.put("distance", this.getDistance());
        data.put("pitch", this.getPitch());
        data.put("yaw", this.getYaw());
        data.put("rayEnabled", this.isRayEnabled());
        data.put("rayRange", this.getDistance());
        return data;
    }
}
