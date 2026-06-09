package io.p2vman.neoperipheral.integration.synaxis.peripheral;

import com.verr1.synaxis.content.blocks.anchor.AnchorBlockEntity;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import io.p2vman.neoperipheral.lua.Table;
import io.p2vman.neoperipheral.mixin.addon.synaxis.AnchorBlockEntityAccessor;
import net.minecraft.core.Direction;

import javax.annotation.Nullable;

public class AnchorPeripheral implements IPeripheral {
    private final AnchorBlockEntity entity;

    public AnchorPeripheral(final @Nullable Direction direction, final AnchorBlockEntity entity) {
        this.entity = entity;
    }

    @Override
    public String getType() {
        return "synaxis_anchor";
    }

    @Override
    public boolean equals(IPeripheral iPeripheral) {
        return iPeripheral instanceof AnchorPeripheral;
    }

    @LuaFunction
    public final void setAirResistance(final double airResistance) {
        ((AnchorBlockEntityAccessor) entity).setAirResistance(airResistance);
    }

    @LuaFunction(mainThread = true)
    public final double getAirResistance() {
        return this.entity.airResistance();
    }

    @LuaFunction(mainThread = true)
    public final void setExtraGravity(final double extraGravity) {
        ((AnchorBlockEntityAccessor) entity).setExtraGravity(extraGravity);
    }

    @LuaFunction(mainThread = true)
    public final double getExtraGravity() {
        return this.entity.extraGravity();
    }

    @LuaFunction(mainThread = true)
    public final void setRotationalResistance(final double rotationalResistance) {
        ((AnchorBlockEntityAccessor) entity).setRotationalResistance(rotationalResistance);
    }

    @LuaFunction(mainThread = true)
    public final double getRotationalResistance() {
        return this.entity.rotationalResistance();
    }

    @LuaFunction(mainThread = true)
    public final Table collect() {
        var tab = new Table();
        tab.put("air_resistance", this.getAirResistance());
        tab.put("extra_gravity", this.getExtraGravity());
        tab.put("rotational_resistance", this.getRotationalResistance());
        return tab;
    }
}
