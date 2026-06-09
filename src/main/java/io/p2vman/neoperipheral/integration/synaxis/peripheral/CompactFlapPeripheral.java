package io.p2vman.neoperipheral.integration.synaxis.peripheral;

import com.verr1.synaxis.content.blocks.flap.CompactFlapBlockEntity;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.Direction;

import javax.annotation.Nullable;

public class CompactFlapPeripheral implements IPeripheral {
    private final CompactFlapBlockEntity entity;

    public CompactFlapPeripheral(final @Nullable Direction direction, final CompactFlapBlockEntity entity) {
        this.entity = entity;
    }

    @Override
    public String getType() {
        return "compact_flap";
    }

    @Override
    public boolean equals(IPeripheral iPeripheral) {
        return iPeripheral instanceof CompactFlapPeripheral;
    }

    @LuaFunction(mainThread = true)
    public final void setAngle(final double angle) {
        this.entity.setAngle(angle);
    }

    @LuaFunction(mainThread = true)
    public final double getAngle() {
        return this.entity.angle();
    }

    @LuaFunction(mainThread = true)
    public final void setTilt(final double tilt) {
        this.entity.setTilt(tilt);
    }

    @LuaFunction(mainThread = true)
    public final double getTilt() {
        return this.entity.tilt();
    }

    @LuaFunction(mainThread = true)
    public final void assemble() {
        this.entity.assemble();
    }

    @LuaFunction(mainThread = true)
    public final void disassemble() {
        this.entity.disassemble();
    }
}
