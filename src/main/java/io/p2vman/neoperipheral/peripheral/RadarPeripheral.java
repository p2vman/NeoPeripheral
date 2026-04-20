package io.p2vman.neoperipheral.peripheral;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IPeripheral;
import dev.ryanhcode.sable.ActiveSableCompanion;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.companion.math.BoundingBox3d;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.lua.Table;
import io.p2vman.neoperipheral.lua.TableArray;
import org.jetbrains.annotations.Nullable;

public class RadarPeripheral implements IPeripheral {
    private final boolean creative;
    private final IPrefSource source;
    public RadarPeripheral(IPrefSource source, boolean creative) {
        this.source = source;
        this.creative = creative;
    }

    @Override
    public String getType() {
        return "neo_radar";
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return iPeripheral == this;
    }

    @LuaFunction(value = {"isCreative", "creative"}, mainThread = true)
    public MethodResult isCreative() throws LuaException {
        return MethodResult.of(creative);
    }

    @LuaFunction(value = {"scanForSubLevels", "ScanForSubLevels"}, mainThread = true)
    public MethodResult scanForSubLevels(IArguments arguments) throws LuaException {
        var radius = Math.max(16, Math.min(2048, arguments.optInt(0, 1024)));
        var sub_levels = new TableArray();
        var increment = 0;

        var pos = Sable.HELPER.projectOutOfSubLevel(this.source.getLevel(), this.source.getPos().getCenter());
        var it = Sable.HELPER.getAllIntersecting(this.source.getLevel(), new BoundingBox3d(pos.x - radius, pos.y - radius, pos.z - radius, pos.x + radius, pos.y + radius, pos.z + radius)).iterator();
        while (it.hasNext()) {
            var subLevel = (ServerSubLevel) it.next();
            var sub = new Table();

            sub.put("id", subLevel.getUniqueId().toString());

            sub_levels.put(increment++, sub);
        }

        return MethodResult.of(sub_levels);
    }
}
