package io.p2vman.neoperipheral.integration.synaxis.peripheral;

import com.verr1.synaxis.content.blocks.motor.AbstractDynamicMotorBlockEntity;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.lua.Table;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public class DynamicMotorPeripheral implements IPeripheral {
    private final AbstractDynamicMotorBlockEntity entity;

    public DynamicMotorPeripheral(final @Nullable Direction direction, final AbstractDynamicMotorBlockEntity entity) {
        this.entity = entity;
    }

    @Override
    public String getType() {
        return "synaxis_dynamic_motor";
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

    @LuaFunction(mainThread = true)
    public Table collect() {
        var data = new Table();
        data.put("target", entity.target());
        data.put("gainP", entity.gainP());
        data.put("gainI", entity.gainI());
        data.put("gainD", entity.gainD());
        data.put("angleMode", entity.angleMode());
        data.put("eliminateGravity", entity.eliminateGravity());
        return data;
    }
}
