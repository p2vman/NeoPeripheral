package io.p2vman.neoperipheral.integration.synaxis.peripheral;

import com.verr1.synaxis.content.blocks.motor.AbstractDynamicMotorBlockEntity;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public class DynamicMotorPeripheral implements IPeripheral {
    private final AbstractDynamicMotorBlockEntity entity;

    public DynamicMotorPeripheral(final @Nullable Direction direction, final AbstractDynamicMotorBlockEntity entity) {
        this.entity = entity;
    }

    @Override
    public String getType() {
        return "dynamic_motor";
    }

    @Override
    public boolean equals(IPeripheral iPeripheral) {
        return iPeripheral instanceof DynamicMotorPeripheral;
    }

    @LuaFunction(mainThread = true)
    public void setGainP(double p) {
        entity.setGainP(p);
    }

    @LuaFunction(mainThread = true)
    public void setGainI(double i) {
        entity.setGainI(i);
    }

    @LuaFunction(mainThread = true)
    public void setGainD(double d) {
        entity.setGainD(d);
    }

    @LuaFunction(mainThread = true)
    public void setPID(double p, double i, double d) {
        entity.setGainP(p);
        entity.setGainI(i);
        entity.setGainD(d);
    }

    @LuaFunction(mainThread = true)
    public double getGainP() {
        return entity.gainP();
    }

    @LuaFunction(mainThread = true)
    public double getGainI() {
        return entity.gainI();
    }

    @LuaFunction(mainThread = true)
    public double getGainD() {
        return entity.gainD();
    }

    @LuaFunction(mainThread = true)
    public void setTarget(double target) {
        entity.setTarget(target);
    }

    @LuaFunction(mainThread = true, value = "getTarget")
    public double GetTarget() {
        return entity.target();
    }

    @LuaFunction(mainThread = true)
    public void setMode(boolean mode) {
        entity.setAngleMode(mode);
    }

    @LuaFunction(mainThread = true)
    public boolean getMode() {
        return entity.angleMode();
    }

    @LuaFunction(mainThread = true)
    public void setGravityCompensation(boolean enable) {
        entity.setEliminateGravity(enable);
    }

    @LuaFunction(mainThread = true)
    public boolean getGravityCompensation() {
        return entity.eliminateGravity();
    }
}
