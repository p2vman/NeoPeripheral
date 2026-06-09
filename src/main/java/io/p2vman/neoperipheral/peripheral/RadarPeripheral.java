package io.p2vman.neoperipheral.peripheral;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.companion.math.BoundingBox3d;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import io.p2vman.neoperipheral.Config;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.lua.Table;
import io.p2vman.neoperipheral.lua.TableArray;
import io.p2vman.neoperipheral.lua.UUIDTable;

import java.util.Random;

public class RadarPeripheral extends BasePeripheral {
    private final Random random = new Random();
    private final boolean creative;
    public RadarPeripheral(IPrefSource source, boolean creative) {
        super(source);
        this.creative = creative;
    }

    @Override
    public String getType() {
        return "neo_radar";
    }

    @LuaFunction(value = {"isCreative", "creative"})
    public final MethodResult isCreative() throws LuaException {
        return MethodResult.of(creative);
    }

    @LuaFunction(value = {"scanForSubLevels", "ScanForSubLevels"}, mainThread = true)
    public final MethodResult scanForSubLevels(IArguments arguments) throws LuaException {
        var radius = this.creative ? arguments.optInt(0, 16) : Math.max(16, Math.min(Config._RADAR_RANGE_LIMIT, arguments.optInt(0, Config._RADAR_DEFAULT_RANGE)));
        var sub_levels = new TableArray();
        var increment = 1;

        var absolute = arguments.optBoolean(1, false) && creative;

        var _pos = Sable.HELPER.projectOutOfSubLevel(this.source.getLevel(), this.source.getPos().getCenter());
        var it = Sable.HELPER.getAllIntersecting(this.source.getLevel(), new BoundingBox3d(_pos.x - radius, _pos.y - radius, _pos.z - radius, _pos.x + radius, _pos.y + radius, _pos.z + radius)).iterator();
        while (it.hasNext()) {
            var subLevel = (ServerSubLevel) it.next();
            var sub = new Table();

            sub.put("id", new UUIDTable(subLevel.getUniqueId()));
            var name = subLevel.getName();
            if (name != null) sub.put("name", name);
            var pose = subLevel.logicalPose();
            var pos = pose.position();
            var dx = _pos.x - pos.x;
            var dy = _pos.y - pos.y;
            var dz = _pos.z - pos.z;
            var dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (absolute) {
                sub.put("x", pos.x);
                sub.put("y", pos.y);
                sub.put("z", pos.z);
                sub.put("distance", dist);
            } else {
                var err = dist/(Math.pow(Math.PI, 3));
                sub.put("x", (_pos.x - pos.x) + (random.nextDouble() * 2 - 1) * err);
                sub.put("y", (_pos.y - pos.y) + (random.nextDouble() * 2 - 1) * err);
                sub.put("z", (_pos.z - pos.z) + (random.nextDouble() * 2 - 1) * err);
                sub.put("distance", dist + (random.nextDouble() * 2 - 1) * err);
            }

            //SELECT x,y,z,distance WHERE distance > 16^2
            var quat = pose.orientation();
            sub.put("q_x", quat.x);
            sub.put("q_y", quat.y);
            sub.put("q_z", quat.z);
            sub.put("q_w", quat.w);

            sub_levels.put(increment++, sub);
        }

        return MethodResult.of(sub_levels);
    }

    @LuaFunction(mainThread = true)
    public final String getLabel() {
        return source.getLabel();
    }

    @LuaFunction(mainThread = true)
    public final void setLabel(String label) {
        source.setLabel(label);
    }
}
