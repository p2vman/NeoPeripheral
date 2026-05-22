package io.p2vman.neoperipheral.integration.synaxis.peripheral;

import com.verr1.synaxis.content.blocks.kinetic.resistor.KineticResistorBlockEntity;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public class KineticResistorPeripheral implements IPeripheral {
    private final KineticResistorBlockEntity entity;
    public KineticResistorPeripheral(@Nullable Direction direction, KineticResistorBlockEntity entity) {
        this.entity = entity;
    }

    @Override
    public String getType() {
        return "kinetic_resistor";
    }

    @Override
    public boolean equals(IPeripheral iPeripheral) {
        return iPeripheral instanceof KineticResistorPeripheral;
    }

    @LuaFunction(mainThread = true)
    public double getRatio() {
        return entity.ratio();
    }

    @LuaFunction(mainThread = true)
    public void setRatio(double ratio) {
        entity.setRatio(ratio);
    }
}
