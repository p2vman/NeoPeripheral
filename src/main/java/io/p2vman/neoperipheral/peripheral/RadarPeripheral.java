package io.p2vman.neoperipheral.peripheral;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IPeripheral;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.companion.math.BoundingBox3d;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import io.p2vman.neoperipheral.Config;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.lua.Table;
import io.p2vman.neoperipheral.lua.TableArray;
import org.jetbrains.annotations.Nullable;

public class RadarPeripheral extends BasePeripheral {
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

    @LuaFunction(value = {"isCreative", "creative"})
    public MethodResult isCreative() throws LuaException {
        return MethodResult.of(creative);
    }

    @LuaFunction(value = {"scanForSubLevels", "ScanForSubLevels"}, mainThread = true)
    public MethodResult scanForSubLevels(IArguments arguments) throws LuaException {
        var radius = this.creative ? arguments.optInt(0, 16) : Math.max(16, Math.min(Config._RADAR_RANGE_LIMIT, arguments.optInt(0, 1024)));
        var sub_levels = new TableArray();
        var increment = 0;

        var absolute = arguments.optBoolean(1, false) && creative;

        var _pos = Sable.HELPER.projectOutOfSubLevel(this.source.getLevel(), this.source.getPos().getCenter());
        var it = Sable.HELPER.getAllIntersecting(this.source.getLevel(), new BoundingBox3d(_pos.x - radius, _pos.y - radius, _pos.z - radius, _pos.x + radius, _pos.y + radius, _pos.z + radius)).iterator();
        while (it.hasNext()) {
            var subLevel = (ServerSubLevel) it.next();
            var sub = new Table();

            var uuid = subLevel.getUniqueId();
            sub.put("id1", uuid.getLeastSignificantBits());
            sub.put("id2", uuid.getMostSignificantBits());

            var pose = subLevel.logicalPose();
            var pos = pose.position();
            if (absolute) {
                sub.put("x", pos.x);
                sub.put("y", pos.y);
                sub.put("z", pos.z);
            } else {
                sub.put("x", _pos.x - pos.x);
                sub.put("y", _pos.y - pos.y);
                sub.put("z", _pos.z - pos.z);
            }

            var quat = pose.orientation();
            sub.put("q_x", quat.x);
            sub.put("q_y", quat.y);
            sub.put("q_z", quat.z);
            sub.put("q_w", quat.w);

            sub_levels.put(increment++, sub);
        }

        return MethodResult.of(sub_levels);
    }


}
