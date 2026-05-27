package io.p2vman.neoperipheral.integration.synaxis.peripheral;

import com.verr1.synaxis.content.blocks.jet.JetBlockEntity;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.lua.Table;
import net.minecraft.core.Direction;

public class JetPeripheral implements IPeripheral {
    private final JetBlockEntity entity;
    public JetPeripheral(Direction direction, JetBlockEntity jetBlockEntity) {
        this.entity = jetBlockEntity;
    }

    @Override
    public String getType() {
        return "jet";
    }

    @LuaFunction(mainThread = true)
    public void setTrust(double trust) {
        this.entity.setThrust(trust);
    }

    @LuaFunction(mainThread = true)
    public double getTrust() {
        return this.entity.thrust();
    }

    @LuaFunction(mainThread = true)
    public void setHorizontalAngle(double horizontalAngle) {
        this.entity.setHorizontalAngle(horizontalAngle);
    }

    @LuaFunction(mainThread = true)
    public double getHorizontalAngle() {
        return this.entity.horizontalAngle();
    }

    @LuaFunction(mainThread = true)
    public void setVerticalAngle(double verticalAngle) {
        this.entity.setVerticalAngle(verticalAngle);
    }

    @LuaFunction(mainThread = true)
    public double getVerticalAngle() {
        return this.entity.verticalAngle();
    }

    @LuaFunction(mainThread = true)
    public Table collect() {
        var tab = new Table();
        tab.put("thrust", this.entity.thrust());
        tab.put("horizontalAngle", this.entity.horizontalAngle());
        tab.put("verticalAngle", this.entity.verticalAngle());
        return tab;
    }

    @Override
    public boolean equals(IPeripheral iPeripheral) {
        return iPeripheral instanceof JetPeripheral;
    }
}
