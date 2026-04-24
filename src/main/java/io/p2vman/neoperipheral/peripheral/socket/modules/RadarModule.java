package io.p2vman.neoperipheral.peripheral.socket.modules;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.companion.math.BoundingBox3d;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import io.p2vman.neoperipheral.Config;
import io.p2vman.neoperipheral.lua.Table;
import io.p2vman.neoperipheral.lua.TableArray;
import io.p2vman.neoperipheral.peripheral.SocketPeripheral;
import io.p2vman.neoperipheral.peripheral.socket.AbstractModule;
import io.p2vman.neoperipheral.peripheral.socket.Module;
import io.p2vman.neoperipheral.peripheral.socket.ModuleExtensions;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class RadarModule extends AbstractModule {
    private final boolean creative;
    private final SocketPeripheral peripheral;

    public RadarModule(ItemStack itemStack, SocketPeripheral peripheral) {
        this(itemStack, peripheral, false);
    }
    public RadarModule(ItemStack itemStack, SocketPeripheral peripheral, boolean creative) {
        this.creative = creative;
        this.peripheral = peripheral;
    }

    @Override
    public void onConnect() {
        super.onConnect();
    }

    @Override
    public void onDisconnect() {
        super.onDisconnect();
    }

    @Override
    public void onSave(CompoundTag tag, HolderLookup.Provider provider) {

    }

    @Override
    public void onLoad(CompoundTag tag, HolderLookup.Provider provider) {

    }

    @Override
    public void onModuleConnect(Module module, int slot) {

    }

    @Override
    public void onModuleDisconnect(Module module, int slot) {

    }

    @LuaFunction(value = {"isCreative", "creative"})
    public MethodResult isCreative() throws LuaException {
        ModuleExtensions.checkModuleEnabled(this);
        return MethodResult.of(creative);
    }

    @Override
    public String getType() {
        return "neo_radar";
    }

    @LuaFunction(value = {"scanForSubLevels", "ScanForSubLevels"}, mainThread = true)
    public MethodResult scanForSubLevels(IArguments arguments) throws LuaException {
        ModuleExtensions.checkModuleEnabled(this);
        var radius = this.creative ? arguments.optInt(0, 16) : Math.max(16, Math.min(Config._RADAR_RANGE_LIMIT, arguments.optInt(0, Config._RADAR_DEFAULT_RANGE)));
        var sub_levels = new TableArray();
        var increment = 0;

        var absolute = arguments.optBoolean(1, false) && creative;

        var source = this.peripheral.getSource();
        var _pos = Sable.HELPER.projectOutOfSubLevel(source.getLevel(), source.getPos().getCenter());
        var it = Sable.HELPER.getAllIntersecting(source.getLevel(), new BoundingBox3d(_pos.x - radius, _pos.y - radius, _pos.z - radius, _pos.x + radius, _pos.y + radius, _pos.z + radius)).iterator();
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
